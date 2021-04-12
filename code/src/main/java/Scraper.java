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
    public Scraper(String URL) throws IOException {
        this.URL = URL;
//        System.out.println(request(URL));
//        createTree(request(URL));

        //System.out.println("<body id=\"hi\" class=\"class1\">Hei jeg hedder dorte<p lang=\"no\" id=\"para\">yo who<a href=\"https://www.w3schools.com/\" target=\"_blank\">Visit W3Schools!</a><p>yo mama</p></p>og min mor er borte<a href=\"https://www.test.com/\" </a><h1 id=\"header1\">child of child.</h1></body>");
        createTree("<body id=\"hi\" class=\"class1\">Hei jeg hedder dorte <p lang=\"no\" id=\"para\">yo who<a src=\"blabal\" target=\"_blank\" href=\"https://www.w3schools.com/\" >https://www.w3schools.com/</a><p>yo mama</p></p>og min mor er borte <a href=\"https://www.test.com/\"> Testloink2 </a><h1 id=\"header1\">chIld, of CHIld.</h1></body>");

//        System.out.println("<body id=\"hi\" class=\"class1\"><!--<p lang=\"no\" id=\"para\">asdqwe</p></body>-->");
//        createTree("<body id=\"hi\" class=\"class1\"><!--<p lang=\"no\" id=\"para\">asdqwe</p></body>-->");

    }



    private void createTree(String html){

        String tag = "";
        String attKey = "";
        String attValue = "";
        String stringContent = "";

        boolean readTag = false;
        boolean readAttKey = false;
        boolean readAttValue = false;
        boolean lookForLastQuote = false;
        boolean isComment = false;

        SoupNode buildingNode = new SoupNode();
        Stack<SoupNode> parentStack = new Stack<>();

        for(int i = 0; i < html.length(); i++){
            char ch = html.charAt(i);

            //ignore mode
            //TODO: DOCTYPE problem
            if (isComment){
                if (ch == '>' && html.charAt(i-1) == '-' && html.charAt(i-2) == '-'){
                    isComment = false;
                }
                continue;
            }

            // to go into ignore/comment mode
            if (ch == '<'){
                if (html.charAt(i+1) == '!')
                    isComment = true;
                else{
                    readTag = true;

                    if (!parentStack.isEmpty() && !stringContent.isEmpty()){
                        parentStack.peek().addStringChild(stringContent);
                        stringContent = "";
                    }

                }

                continue;
            }

            //  when tag is to be ended
            if (ch == '>'){
                if (readTag && tag.isEmpty());
//                        throw new Exception("There exist and empty tag in this html");
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

//                this.nodes.add(buildingNode);

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
                if (isIgnoreContentTag(parentStack.peek().getTag()))
                    continue;
                else{
                    stringContent += ch;
                }
            }

            //read tag
            if (readTag) {
                if (Character.isLetter(ch) || Character.isDigit(ch))
                    tag += ch;
                else {
                    if (tag.isEmpty() && ch == '/'){
                        String tagEnded = parentStack.pop().getTag();
                        if (html.substring(i + 1, i + 1 + tagEnded.length()).equals(tagEnded)){
                            i = i + 1 + tagEnded.length();

                            readTag = false;
                            continue;
                        }

                        //TODO: EXCEPTION class
//                        else
//                            throw new Exception("Ended tag doesn't fit current parent");
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
            }
//            read key value
            else if(readAttValue){
                //see first quotes "
                if(ch == '\"' && !lookForLastQuote){
                    lookForLastQuote = true;
                    continue;
                }
                else{
//                    throw new Exception("Missing quotes after attributtes");
                }
                //build value string untill we see see last quotes "
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

    //TODO: implement this function
    private boolean isSingletonTag(String tag){
        return false;
    }

    //TODO: implement
    private boolean isIgnoreContentTag(String tag){
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
     * @return The content is returned as Strings in String[]
     */
    public String[] getContentFromTag(String tag){

        TreeTraverser.traversingGetContentFromTag(root, tag);

        return TreeTraverser.getTagArray();
    }

    /**
     *
     * @param id The id of an element which we want to get the content from
     * @return returns the content as String in String[]
     */
    public ArrayList<String> getContentFromId(String id){

        TreeTraverser.traversingGetContentFromId(root,id);

        return TreeTraverser.getIdArray();
    }

    /**
     *
     * @param className The class name of the element which we want to get the content of
     * @return returns the content of the given class as strings in String[]
     */
    public ArrayList<String> getContentFromClass(String className){

        TreeTraverser.traversingGetContentFromClass(root,className);

        return TreeTraverser.getClassArray();
    }

    /**
     *
     * @return the links as String in ArrayList
     */
    public ArrayList<String> getLinksInPage(){

        TreeTraverser.traversingGetLinksInPage(root);

        return TreeTraverser.getLinksArray();
    }

    /**
     *
     * @param searchString string we want to see if exist in the HTML code
     * @return returns boolean based on if the String was found or not. True for was found and false for was not found.
     */
    public boolean contains(String searchString){

        Boolean contains = TreeTraverser.traversingContains(root, searchString);
        return contains;
    }

    public boolean containsCaseSensetive(String searchString){
        Boolean contains = TreeTraverser.traversingContainsCaseInSensetive(root, searchString);
        return contains;
    }

    /**
     *
     * @return all found classes as strings in String[]
     */
    public String[] getClassesInPage(){
        return null;
    }

    /**
     *
     * @return all found id's as strings in String[]
     */
    public String[] getIdsInPage(){
        return null;
    }

    public SoupNode getRoot() {
        return root;
    }
}
