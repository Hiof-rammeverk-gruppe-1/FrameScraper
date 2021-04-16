import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Shopscraper class representing a specific instantiation of a ConceptScraper
 * @author Mathias Jarbekk
 * @author Thomas Johannessen
 * @author Joakim Jensen
 * @author Michal Kowalski
 * @version 1.0
 * @see ConceptScraper
 */
public class Scraper {
    private String URL;
    private String websiteContent;
    private SoupNode root = null;

    private ArrayList<SoupNode> nodes = new ArrayList<>();

    /**
     * Constructor for a shop scraper
     * @param URL Link to target site for scraping
     * @throws IOException Throws an IO Exception whenever user input is crashing with an expected string value
     */
    public Scraper(String URL) throws IOException, ParseException {
        this.URL = URL;
        websiteContent = request(URL);
        createTree(websiteContent);
    }

    public Scraper(String siteContent, boolean test) {
        websiteContent = siteContent;
        createTree(siteContent);
    }

    //TODO: REFACTOR
    private void createTree(String html) throws ParseException {

        String tag = "";
        String attKey = "";
        String attValue = "";
        String stringContent = "";

        boolean readTag = false;
        boolean readAttKey = false;
        boolean readAttValue = false;
        boolean lookForLastQuote = false;
        boolean isComment = false;
        boolean isIgnoreable = false;
        boolean isDoctype = false;

        SoupNode buildingNode = new SoupNode();
        Stack<SoupNode> parentStack = new Stack<>();

        for(int i = 0; i < html.length(); i++){
            char ch = html.charAt(i);

            // ignore content of tag mode
            if (isIgnoreable)
                if (!(ch == '<' && html.charAt(i+1) == '/'))
                    continue;
                else
                    isIgnoreable = false;

            //comment mode
            if (isComment){
                if (ch == '>' && html.charAt(i-1) == '-' && html.charAt(i-2) == '-'){
                    isComment = false;
                }
                continue;
            }

            // if doctypemode alternative ignoremode
            if (isDoctype){
                if (ch == '>')
                    isDoctype = false;
                continue;
            }


            // to go into ignore/comment mode
            if (ch == '<'){
                if (html.charAt(i+1) == '!')
                    if (html.charAt(i+2) == '-' && html.charAt(i+3) == '-')
                        isComment = true;
                    else
                        isDoctype = true;

                else{
                    readTag = true;

                    if (!parentStack.isEmpty() && !stringContent.isEmpty()){
                        parentStack.peek().addStringChild(stringContent);
                        stringContent = "";
                    }

                }

                continue;
            }

            //  when head of tag is to be ended
            if (ch == '>'){
                if (readTag && tag.isEmpty())
                        throw new ParseException("There exist an empty tag in this html", buildingNode);
                else if (readTag)
                    buildingNode.setTag(tag);
                else if (readAttKey && !(attKey.isEmpty())){
                    buildingNode.getAttributeNames().add(attKey);

                    buildingNode.getAttributes().put(attKey, "");
                }

                //add nodes
                if (this.root == null)
                    this.root = buildingNode;

                if (!parentStack.isEmpty())
                    parentStack.peek().addNodeChild(buildingNode);

                if (!isSingletonTag(buildingNode.getTag())){
                    parentStack.push(buildingNode);
                }

                if (isIgnoreableContentTag(buildingNode.getTag()))
                    isIgnoreable = true;


                // reset locals
                tag = "";
                attKey = "";
                attValue = "";

                readTag = false;
                readAttKey = false;
                readAttValue = false;
                lookForLastQuote = false;
                isComment = false;

                buildingNode = new SoupNode();

                continue;
            }

            // read String content
            if (!(readTag || readAttKey || readAttValue) && !parentStack.isEmpty()){
                stringContent += ch;
            }

            //read tag
            if (readTag) {
                if (Character.isLetter(ch) || Character.isDigit(ch))
                    tag += ch;
                else {
                    if (tag.isEmpty() && ch == '/'){

                        SoupNode parentToEnd = parentStack.pop();
                        String tagEnded = parentToEnd.getTag();

                        if (html.substring(i + 1, i + 1 + tagEnded.length()).equals(tagEnded)){
                            i = i + 1 + tagEnded.length();

                            readTag = false;
                            continue;
                        }
                        else
                            throw new ParseException("Ended tag doesn't fit current parent", parentToEnd);
                    }


                    readTag = false;
                    readAttKey = true;
                    buildingNode.setTag(tag);
                    tag = "";
                    continue;
                }
            }
//            read key
            else if (readAttKey){
                if (Character.isLetter(ch))
                    attKey += ch;
                else if(ch == '='){
                    if(!attKey.isEmpty()){
                        readAttKey = false;
                        readAttValue = true;
                    }
                }
                //TODO: quick fix for solo attributtes
                else if(ch == ' ' && !attKey.isEmpty()){
                    buildingNode.getAttributeNames().add(attKey);
                    buildingNode.getAttributes().put(attKey, "");

                    attKey = "";
                }
            }
//            read key value
            else if(readAttValue){
                //see first quotes "
                if (!lookForLastQuote){
                    if (ch == '\"'){
                        lookForLastQuote = true;
                        continue;
                    }
                    else
                        throw new ParseException("Missing quotes after attributtes", buildingNode);
                }

                if(lookForLastQuote){
                    if (ch == '\"'){

                        buildingNode.getAttributeNames().add(attKey);

                        buildingNode.getAttributes().put(attKey, attValue);

                        readAttValue = false;
                        readAttKey = true;
                        lookForLastQuote = false;

                        attKey = "";
                        attValue = "";

                        continue;
                    }
                    attValue += ch;
                }

            }
        }
    }

    private boolean isSingletonTag(String tag){
        String[] singletons = {"area", "base", "br", "col", "embed", "hr", "img", "input", "link", "meta", "param", "source", "track", "wbr", "command", "keygen", "menuitem"};

        for (int i = 0; i < singletons.length; i++)
            if (tag.equals(singletons[i]))
                return true;
        return false;
    }

    private boolean isIgnoreableContentTag(String tag){
        String[] ignoreableTags = {"script", "style"};

        for (int i = 0; i < ignoreableTags.length; i++){
            if (tag.equals(ignoreableTags[i]))
                return true;
        }
        return false;
    }

    private String request(String URL){

        //Instantiating the URL class
        URL url = null;
        try {
            url = new URL(URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //Retrieving the contents of the specified page
        Scanner sc = null;
        try {
            sc = new Scanner(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Instantiating the StringBuffer class to hold the result
        StringBuffer sb = new StringBuffer();

        while(sc.hasNext()) {
            sb.append(sc.next() + " ");
            //System.out.println(sc.next());
        }

        //Retrieving the String from the String Buffer object
        return sb.toString();
    }

    /**
     *
     * @return The url as a String
     */
    public String getURL() {
        return URL;
    }

    /**
     *
     * @return The website content as String
     */
    public String getWebsiteContent() {
        return websiteContent;
    }

    /**
     *
     * @param tag The tag from which we want to see the content of
     * @return The content is returned as Strings in an Arraylist of Strings
     */
    public ArrayList<String> getContentFromTag(String tag){

        TreeTraverser.traversingGetContentFromTag(root, tag);

        return TreeTraverser.getTagArray();
    }

    /**
     *
     * @param id The id of an element which we want to get the content from
     * @return The content is returned as Strings in a Arraylist of Strings
     */
    public ArrayList<String> getContentFromId(String id){

        TreeTraverser.traversingGetContentFromId(root,id);

        return TreeTraverser.getIdArray();
    }

    /**
     *
     * @param className The class name of the element which we want to get the content of
     * @return returns the content of the given class as strings in an Arraylist of Strings
     */
    public ArrayList<String> getContentFromClass(String className){

        TreeTraverser.traversingGetContentFromClass(root,className);

        return TreeTraverser.getClassArray();
    }

    /**
     *
     * @return the links as String in an Arraylist of Strings
     */
    public ArrayList<String> getLinksInPage(){

        TreeTraverser.traversingGetLinksInPage(root);

        return TreeTraverser.getLinksArray();
    }

    /**
     *
     * @param searchString string we want to see if exist in the HTML code. IS case sensetive
     * @return returns boolean based on if the String was found or not. True for was found and false for was not found.
     */
    public boolean contains(String searchString){

        Boolean contains = TreeTraverser.traversingContains(root, searchString);
        return contains;
    }

    /**
     *
     * @param searchString string we want to see if exist in the HTML code. IS NOT case sensetive
     * @return returns boolean based on if the String was found or not. True for was found and false for was not found.
     */
    public boolean containsCaseInSensetive(String searchString){
        Boolean contains = TreeTraverser.traversingContainsCaseInSensetive(root, searchString);
        return contains;
    }

    /**
     *
     * @return the source for the images as String in an Arraylist of Strings
     */
    public ArrayList<String> getAllImagesFromPage(){
            TreeTraverser.traversingGetAllImagesFromPage(root);
        return TreeTraverser.getImgArray();
    }

    /**
     * @param pictureId id as String we want to get the picture from
     * @return the source for the image as String
     */
    public String getImageById(String pictureId){
            String pic = TreeTraverser.traversingGetImageById(root, pictureId);
        return pic;
    }

    /**
     * @param pictureClass class as String we want to get the pictures from
     * @return the source for the images as String in an Arraylist of Strings
     */
    public ArrayList<String> getImageByClass(String pictureClass){
        TreeTraverser.traversingGetImageByClass(root, pictureClass);
        return TreeTraverser.getImgClassArray();
    }

    /**
     *
     * @return the source for the videos as String in an Arraylist of Strings
     */
    public ArrayList<String> getAllVideosFromPage(){
        TreeTraverser.traversingGetAllVideosFromPage(root);
        return TreeTraverser.getVideoArray();
    }

    /**
     * @param videoId id as String we want to get the video from
     * @return the source for the video as String
     */
    public String getVideoById(String videoId){
        String vid = TreeTraverser.traversingGetVideoById(root, videoId);
        return vid;
    }

    /**
     * @param videoClass class as String we want to get the video from
     * @return the source for the videos as String in an Arraylist of Strings
     */
    public ArrayList<String> getVideoByClass(String videoClass){
        TreeTraverser.traversingGetVideoByClass(root, videoClass);
        return TreeTraverser.getVideoClassArray();
    }

    /**
     *
     * @return all found classes as strings in an Arraylist of Strings
     */
    public ArrayList<String> getClassesInPage(){
        TreeTraverser.traversingGetClassesInPage(root);
        return TreeTraverser.getAllClassArray();
    }

    /**
     *
     * @return all found id's as strings in an Arraylist of Strings
     */
    public ArrayList<String> getIdsInPage(){
        TreeTraverser.traversingGetIdsInPage(root);
        return TreeTraverser.getAllIdsArray();
    }

    public SoupNode getRoot() {
        return root;
    }

    public void printBeautyfull(){
        printBeautyfull(root, 0);
    }

    private void printBeautyfull(SoupNode node, int nTabs){

        if (node != null){
            String tabs = "";

            for (int i = 0; i < nTabs; i++){
                tabs += '\t';
            }

            String str = tabs +
                    "SoupNode{" +
                    "tag=\'" + node.getTag() + '\'' +
                    ", attributes=" + node.getAttributeNames().toString() +
                    ", textChildren=" + node.getStringChildren().toString() + '}';

            System.out.println(str);

            for (int i = 0; i < node.getNodeChildren().size(); i++){
                printBeautyfull(node.getNodeChildren().get(i), nTabs+1);
            }
        }
    }
}
