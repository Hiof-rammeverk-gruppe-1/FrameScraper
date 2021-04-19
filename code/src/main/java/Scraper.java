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
     * A methode for getting the content from a specific HTML-tag in the webpage
     * @param tag The tag from which we want to see the content of
     * @return The content is returned as Strings in an Arraylist of Strings
     */
    public ArrayList<String> getContentFromTagAsString(String tag){
            TreeTraverser.traversingGetContentFromTagAsString(root, tag);
        return TreeTraverser.getTagStringArray();
    }

    /**
     * A methode for getting the content from a specific HTML-tag in the webpage
     * @param tag The tag from which we want to see the content of
     * @return The content is returned as Nodes in an Arraylist of Nodes
     */
    public ArrayList<SoupNode> getContentFromTagAsNode(String tag){
            TreeTraverser.traversingGetContentFromTagAsNode(root, tag);
        return TreeTraverser.getTagNodeArray();
    }

    /**
     * A methode for getting the content from a id in the webpage
     * @param id The id of an element which we want to get the content from
     * @return The content is returned as Strings in a Arraylist of Strings
     */
    public ArrayList<String> getContentFromIdAsString(String id){
            TreeTraverser.traversingGetContentFromIdAsString(root,id);
        return TreeTraverser.getIdStringArray();
    }

    /**
     * A methode for getting the content from a id in the webpage
     * @param id The id of an element which we want to get the content from
     * @return The content is returned as Nodes in a Arraylist of Nodes
     */
    public ArrayList<SoupNode> getContentFromIdAsNode(String id){
            TreeTraverser.traversingGetContentFromIdAsNode(root,id);
        return TreeTraverser.getIdNodeArray();
    }

    /**
     * A methode for getting the content from a class in the webpage
     * @param className The class name of the element which we want to get the content of
     * @return returns the content of the given class as strings in an Arraylist of Strings
     */
    public ArrayList<String> getContentFromClassAsString(String className){
            TreeTraverser.traversingGetContentFromClassAsString(root,className);
        return TreeTraverser.getClassStringArray();
    }

    /**
     * A methode for getting the content from a class in the webpage
     * @param className The class name of the element which we want to get the content of
     * @return returns the content of the given class as Nodes in an Arraylist of Nodes
     */
    public ArrayList<SoupNode> getContentFromClassAsNode(String className){
            TreeTraverser.traversingGetContentFromClassAsNode(root,className);
        return TreeTraverser.getClassNodeArray();
    }

    /**
     *A methode for getting all links (<a-tags) in the webpage
     * @return the links as String in an Arraylist of Strings
     */
    public ArrayList<String> getLinksInPageAsString(){
            TreeTraverser.traversingGetLinksInPageAsString(root);
        return TreeTraverser.getLinksStringArray();
    }

    /**
     *A methode for getting all links (<a-tags) in the webpage
     * @return the links as Nodes in an Arraylist of Nodes
     */
    public ArrayList<SoupNode> getLinksInPageAsNode(){
            TreeTraverser.traversingGetLinksInPageAsNode(root);
        return TreeTraverser.getLinksNodeArray();
    }

    /**
     * A methode for seaching if a webpage contains a string. this methode IS CASE SENSETIVE.
     * @param searchString string we want to see if exist in the HTML code. IS case sensetive
     * @return returns boolean based on if the String was found or not. True for was found and false for was not found.
     */
    public boolean containsAsBoolean(String searchString){
            Boolean contains = TreeTraverser.traversingContainsAsBoolean(root, searchString);
        return contains;
    }

    /**
     * A methode for seaching if a webpage contains a string. this methode IS CASE SENSETIVE.
     * @param searchString string we want to see if exist in the HTML code. IS case sensetive
     * @return returns all nodes where it the search string was found in an arraylist of nodes
     */
    public ArrayList<SoupNode> containsAsNode(String searchString){
        TreeTraverser.traversingContainsAsNode(root, searchString);
        return TreeTraverser.getContainsNodeArray();
    }

    /**
     * A methode for seaching if a webpage contains a string.
     * @param searchString string we want to see if exist in the HTML code. IS NOT case sensetive
     * @return returns boolean based on if the String was found or not. True for was found and false for was not found.
     */
    public boolean containsCaseInSensetiveAsBoolean(String searchString){
        Boolean contains = TreeTraverser.traversingContainsCaseInSensetiveAsBoolean(root, searchString);
        return contains;
    }


    /**
     * A methode for seaching if a webpage contains a string.
     * @param searchString string we want to see if exist in the HTML code. IS NOT case sensetive
     * @return returns all nodes where it the search string was found in an arraylist of nodes
     */
    public ArrayList<SoupNode> containsCaseInSensetiveAsNode(String searchString){
            TreeTraverser.traversingContainsCaseInSensetiveAsNode(root, searchString);
        return TreeTraverser.getContainsCaseInSensitiveNodeArray();
    }


    /**
     * A methode for getting all Images from the webpage
     * @return the source for the images as String in an Arraylist of Strings
     */
    public ArrayList<String> getAllImagesFromPageAsString(){
            TreeTraverser.traversingGetAllImagesFromPageAsString(root);
        return TreeTraverser.getImgStringArray();
    }

    /**
     * A methode for getting all Images from the webpage
     * @return the source for the images as Nodes in an Arraylist of Nodes
     */
    public ArrayList<SoupNode> getAllImagesFromPageAsNode(){
        TreeTraverser.traversingGetAllImagesFromPageAsNode(root);
        return TreeTraverser.getImgNodeArray();
    }

    /**
     * A methode for getting a Image with that id from the webpage
     * @param pictureId id as String we want to get the picture from
     * @return the source for the image as String
     */
    public String getImageByIdAsString(String pictureId){
        return TreeTraverser.traversingGetImageByIdAsString(root, pictureId);
    }

    /**
     * A methode for getting a Image with that id from the webpage
     * @param pictureId id as String we want to get the picture from
     * @return the source for the image as Nodes in an Arraylist of Nodes
     */
    public SoupNode getImageByIdAsNode(String pictureId){
        return TreeTraverser.traversingGetImageByIdAsNode(root, pictureId);
    }

    /**
     * A methode for getting all the Images with that classname from the webpage
     * @param pictureClass class as String we want to get the pictures from
     * @return the source for the images as String in an Arraylist of Strings
     */
    public ArrayList<String> getImageByClassAsString(String pictureClass){
        TreeTraverser.traversingGetImageByClassAsString(root, pictureClass);
        return TreeTraverser.getImgClassStringArray();
    }

    /**
     * A methode for getting all the Images with that classname from the webpage
     * @param pictureClass class as String we want to get the pictures from
     * @return the source for the images as Nodes in an Arraylist of Nodes
     */
    public ArrayList<SoupNode> getImageByClassAsNode(String pictureClass){
        TreeTraverser.traversingGetImageByClassAsNode(root, pictureClass);
        return TreeTraverser.getImgClassNodeArray();
    }


    /**
     *A methode for getting all Videos from the webpage
     * @return the source for the videos as String in an Arraylist of Strings
     */
    public ArrayList<String> getAllVideosFromPageAsString(){
        TreeTraverser.traversingGetAllVideosFromPageAsString(root);
        return TreeTraverser.getVideoStringArray();
    }

    /**
     *A methode for getting all Videos from the webpage
     * @return the source for the videos as Nodes in an Arraylist of Nodes
     */
    public ArrayList<SoupNode> getAllVideosFromPageAsNode(){
        TreeTraverser.traversingGetAllVideosFromPageAsNode(root);
        return TreeTraverser.getVideoNodeArray();
    }

    /**
     * A methode for getting a Videos with that id from the webpage
     * @param videoId id as String we want to get the video from
     * @return the source for the video as String in a ArrayList of strings
     */
    public String getVideoByIdAsString(String videoId){
        return TreeTraverser.traversingGetVideoByIdAsString(root, videoId);
    }

    /**
     * A methode for getting a Videos with that id from the webpage
     * @param videoId id as String we want to get the video from
     * @return the source for the video as Nods in a Arraylist of Nodes
     */
    public SoupNode getVideoByIdAsNode(String videoId){
        return TreeTraverser.traversingGetVideoByIdAsNode(root, videoId);
    }

    /**
     * A methode for getting all the Videos with that classname from the webpage
     * @param videoClass class as String we want to get the video from
     * @return the source for the videos as String in an Arraylist of Strings
     */
    public ArrayList<String> getVideoByClassAsString(String videoClass){
        TreeTraverser.traversingGetVideoByClassAsString(root, videoClass);
        return TreeTraverser.getVideoClassStringArray();
    }

    /**
     * A methode for getting all the Videos with that classname from the webpage
     * @param videoClass class as String we want to get the video from
     * @return the source for the videos as Node in an Arraylist of Nodes
     */
    public ArrayList<SoupNode> getVideoByClassAsNode(String videoClass){
        TreeTraverser.traversingGetVideoByClassAsNode(root, videoClass);
        return TreeTraverser.getVideoClassNodeArray();
    }

    /**
     * A methode for getting all the HTML classes for the webpage
     * @return all found classes as strings in an Arraylist of Strings
     */
    public ArrayList<String> getClassesInPage(){
        TreeTraverser.traversingGetClassesInPage(root);
        return TreeTraverser.getAllClassArray();
    }

    /**
     * A methode for getting all the HTML id's for the webpage
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
