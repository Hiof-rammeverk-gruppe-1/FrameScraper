package Scraper;

import Scraper.Exceptions.ParseException;
import HTMLString.HTMLToString;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Shopscraper class representing a specific instantiation of a Scraper.ConceptScraper
 * @author Mathias Jarbekk
 * @author Thomas Johannessen
 * @author Joakim Jensen
 * @author Michal Kowalski
 * @version 1.0
 * @see ConceptScraper
 */
public class Scraper {
    private final static int FILE = 0;
    private final static int WEBURL = 1;
    private final static int STRING = 2;

    private String url;
    private String websiteContent;
    private SoupNode root = null;



    private ArrayList<SoupNode> nodes = new ArrayList<>();

    /**
     * Constructor for a shop scraper
     * @param URL Link to target site for scraping
     * @throws IOException Throws an IO Exception whenever user input is crashing with an expected string value
     */
    public Scraper(String URL) throws IOException, ParseException {
        this.url = URL;
        websiteContent = HTMLToString.requestHTMLWithUrl(URL);
        this.root = TreeBuilder.createTree(websiteContent);
    }

    public Scraper(String siteContent, boolean test) throws ParseException {
        websiteContent = siteContent;
        TreeBuilder.createTree(siteContent);
    }

    // nye Scraper konstruktør
    private Scraper (String source, int sourceType){

        try {
            if (sourceType == FILE){
                this.websiteContent = HTMLToString.readHTMLFromFile(source);
                this.url = source;
                this.root = TreeBuilder.createTree(this.websiteContent);
            }
            else if(sourceType == WEBURL){
                this.websiteContent = HTMLToString.requestHTMLWithUrl(source);
                this.url = source;
                this.root = TreeBuilder.createTree(this.websiteContent);
            }
            else if (sourceType == STRING){
                this.websiteContent = source;
                this.root = TreeBuilder.createTree(source);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    /**
     * A methode for getting the content from a specific HTML-tag in the webpage
     * @param path: Path to the html-file
     * @return Scraper object build from file
     * example: Scraper sc = Scraper.buildScraperWithFile("index.html");
     */
    public static Scraper buildScraperWithFile(String path){
        return new Scraper(path, FILE);
    }

    /**
     * A methode for getting the content from a specific HTML-tag in the webpage
     * @param url: WebUrl to the website that is to be scraped
     * @return Scraper object build from the websites html-source
     * example: Scraper sc = Scraper.buildScraperWithWebUrl("https://webscraper.io/test-sites/e-commerce/allinone");
     */
    public static Scraper buildScraperWithWebUrl(String url){
        return new Scraper(url, WEBURL);
    }

    /**
     * A methode for getting the content from a specific HTML-tag in the webpage
     * @param html: A string which contains html
     * @return Scraper object build from the websites html-source
     * example: Scraper sc = Scraper.buildScraperWithString("<html lang="en"><body><h1 id="header1">Hello world</h1><p>This is my world</p></body></html>");
     */
    public static Scraper buildScraperWithString(String html){
        return new Scraper(html, STRING);
    }

    /**
     *
     * @return The url as a String
     */
    public String getUrl() {
        return url;
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

    /**
     * A methode for getting the content from a attribute of a tag as Node
     * @param tag name of the tag you want to search for
     * @param attribute name of the attribute you want to search for
     * @return a list with the content of the attribute in the tag as Node in an Arraylist of Nodes
     */
    public ArrayList<SoupNode> getAttributeContentWithTagAndNameAsNode(String tag, String attribute){
        TreeTraverser.traversingGetAttributeContentWithTagAndNameAsNode(root, tag,attribute);
        return TreeTraverser.getAttributeContentNodeArray();
    }

    /**
     * A methode for getting the content from a attribute of a tag as String
     * @param tag name of the tag you want to search for
     * @param attribute name of the attribute you want to search for
     * @return a list with the content of the attribute in the tag as String in an Arraylist of Strings
     */
    public ArrayList<String> getAttributeContentWithTagAndNameAsString(String tag, String attribute){
        TreeTraverser.traversingGetAttributeContentWithTagAndNameAsString(root, tag,attribute);
        return TreeTraverser.getAttributeContentStringArray();
    }



    public SoupNode getRoot() {
        return root;
    }

    public void printBeautyfull(){
        printBeautyfull(root, 0);
    }

    public void printBeautyfull(SoupNode node, int nTabs){

        if (node != null){
            String tabs = "";

            for (int i = 0; i < nTabs; i++){
                tabs += '\t';
            }

            String str = tabs +
                    "Scraper.SoupNode{" +
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
