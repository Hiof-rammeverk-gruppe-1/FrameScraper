package Scraper;

import Scraper.Exceptions.ParseException;
import HTMLString.HTMLToString;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Mathias Jarbekk
 * @author Thomas Johannessen
 * @author Joakim Jensen
 * @author Michal Kowalski
 * @version 1.1
 */
public class Scraper {
    private final static int FILE = 0;
    private final static int WEBURL = 1;
    private final static int STRING = 2;

    private String url;
    private String websiteContent;
    private Element root = null;



    private ArrayList<Element> elements = new ArrayList<>();

    /**
     * Constructor for a scraper
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
        this.root = TreeBuilder.createTree(siteContent);
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
    public static Scraper buildWithFile(String path){
        return new Scraper(path, FILE);
    }

    /**
     * A methode for getting the content from a specific HTML-tag in the webpage
     * @param url: WebUrl to the website that is to be scraped
     * @return Scraper object build from the websites html-source
     * example: Scraper sc = Scraper.buildScraperWithWebUrl("https://webscraper.io/test-sites/e-commerce/allinone");
     */
    public static Scraper buildWithWebUrl(String url){
        return new Scraper(url, WEBURL);
    }

    /**
     * A methode for getting the content from a specific HTML-tag in the webpage
     * @param html: A string which contains html
     * @return Scraper object build from the websites html-source
     * example: Scraper sc = Scraper.buildScraperWithString("<html lang="en"><body><h1 id="header1">Hello world</h1><p>This is my world</p></body></html>");
     */
    public static Scraper buildWithString(String html){
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
     * A method for getting the content from a specific HTML-tag in the webpage
     * @param tag The tag from which we want to see the content of
     * @return The content is returned as Strings in an Arraylist of Strings
     */
    public ArrayList<String> getContentFromTagAsString(String tag){
            TreeTraverser.traversingGetContentFromTagAsString(root, tag);
        return TreeTraverser.getTagStringArray();
    }

    /**
     * Works like {@link Scraper#getContentFromTagAsString(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getContentFromTagAsString(String)
     */
    public ArrayList<String> getContentFromTagAsString(String tag, Element startElement){
        TreeTraverser.traversingGetContentFromTagAsString(startElement, tag);
        return TreeTraverser.getTagStringArray();
    }

    /**
     * A method for getting the content from a specific HTML-tag in the webpage
     * @param tag The tag from which we want to see the content of
     * @return The content is returned as Elements in an Arraylist of Elements
     */
    public ArrayList<Element> getContentFromTagAsElement(String tag){
            TreeTraverser.traversingGetContentFromTagAsElement(root, tag);
        return TreeTraverser.getTagElementArray();
    }

    /**
     * Works like {@link Scraper#getContentFromTagAsElement(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getContentFromTagAsElement(String)
     */
    public ArrayList<Element> getContentFromTagAsElement(String tag, Element startElement){
        TreeTraverser.traversingGetContentFromTagAsElement(startElement, tag);
        return TreeTraverser.getTagElementArray();
    }

    /**
     * A method for getting the content from a id in the webpage
     * @param id The id of an element which we want to get the content from
     * @return The content is returned as Strings in a Arraylist of Strings
     */
    public ArrayList<String> getContentFromIdAsString(String id){
            TreeTraverser.traversingGetContentFromIdAsString(root,id);
        return TreeTraverser.getIdStringArray();
    }

    /**
     * Works like {@link Scraper#getContentFromIdAsString(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getContentFromIdAsString(String)
     */
    public ArrayList<String> getContentFromIdAsString(String id, Element startElement){
        TreeTraverser.traversingGetContentFromIdAsString(startElement,id);
        return TreeTraverser.getIdStringArray();
    }

    /**
     * A method for getting the content from a id in the webpage
     * @param id The id of an element which we want to get the content from
     * @return The content is returned as Elements in a Arraylist of Elements
     */
    public ArrayList<Element> getContentFromIdAsElement(String id){
            TreeTraverser.traversingGetContentFromIdAsElement(root, id);
        return TreeTraverser.getIdElementArray();
    }

    /**
     * Works like {@link Scraper#getContentFromIdAsElement(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getContentFromIdAsElement(String)
     */
    public ArrayList<Element> getContentFromIdAsElement(String id, Element startElement){
        TreeTraverser.traversingGetContentFromIdAsElement(startElement, id);
        return TreeTraverser.getIdElementArray();
    }

    /**
     * A method for getting the content from a class in the webpage
     * @param className The class name of the element which we want to get the content of
     * @return returns the content of the given class as strings in an Arraylist of Strings
     */
    public ArrayList<String> getContentFromClassAsString(String className){
            TreeTraverser.traversingGetContentFromClassAsString(root,className);
        return TreeTraverser.getClassStringArray();
    }

    /**
     * Works like {@link Scraper#getContentFromClassAsString(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getContentFromClassAsString(String)
     */
    public ArrayList<String> getContentFromClassAsString(String className, Element startElement){
        TreeTraverser.traversingGetContentFromClassAsString(startElement, className);
        return TreeTraverser.getClassStringArray();
    }

    /**
     * A method for getting the content from a class in the webpage
     * @param className The class name of the element which we want to get the content of
     * @return returns the content of the given class as Elements in an Arraylist of Elements
     */
    public ArrayList<Element> getContentFromClassAsElement(String className){
            TreeTraverser.traversingGetContentFromClassAsElement(root, className);
        return TreeTraverser.getClassElementArray();
    }

    /**
     * Works like {@link Scraper#getContentFromClassAsElement(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getContentFromClassAsElement(String)
     */
    public ArrayList<Element> getContentFromClassAsElement(String className, Element startElement){
        TreeTraverser.traversingGetContentFromClassAsElement(startElement, className);
        return TreeTraverser.getClassElementArray();
    }

    /**
     *A method for getting all links (<a-tags) in the webpage
     * @return the links as String in an Arraylist of Strings
     */
    public ArrayList<String> getLinksInPageAsString(){
            TreeTraverser.traversingGetLinksInPageAsString(root,getUrl());
        return TreeTraverser.getLinksStringArray();
    }

    /**
     * Works like {@link Scraper#getLinksInPageAsString()} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getLinksInPageAsString()
     */

    public ArrayList<String> getLinksInPageAsString(Element startElement){
        TreeTraverser.traversingGetLinksInPageAsString(startElement,getUrl());
        return TreeTraverser.getLinksStringArray();
    }

    /**
     *A method for getting all links (<a-tags) in the webpage
     * @return the links as Elements in an Arraylist of Elements
     */
    public ArrayList<Element> getLinksInPageAsElement(){
            TreeTraverser.traversingGetLinksInPageAsElement(root);
        return TreeTraverser.getLinksElementArray();
    }

    /**
     * Works like {@link Scraper#getLinksInPageAsElement()} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getLinksInPageAsElement()
     */
    public ArrayList<Element> getLinksInPageAsElement(Element startElement){
        TreeTraverser.traversingGetLinksInPageAsElement(startElement);
        return TreeTraverser.getLinksElementArray();
    }

    /**
     * A method for seaching if a webpage contains a string. this methode IS CASE SENSETIVE.
     * @param searchString string we want to see if exist in the HTML code. IS case sensetive
     * @return returns boolean based on if the String was found or not. True for was found and false for was not found.
     */
    public boolean containsAsBoolean(String searchString){
            Boolean contains = TreeTraverser.traversingContainsAsBoolean(root, searchString);
        return contains;
    }

    /**
     * Works like {@link Scraper#containsAsBoolean(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#containsAsBoolean(String)
     */
    public boolean containsAsBoolean(String searchString, Element startElement){
        Boolean contains = TreeTraverser.traversingContainsAsBoolean(startElement, searchString);
        return contains;
    }

    /**
     * A method for seaching if a webpage contains a string. this methode IS CASE SENSETIVE.
     * @param searchString string we want to see if exist in the HTML code. IS case sensetive
     * @return returns all elements where it the search string was found in an arraylist of elements
     */
    public ArrayList<Element> containsAsElement(String searchString){
        TreeTraverser.traversingContainsAsElement(root, searchString);
        return TreeTraverser.getContainsElementArray();
    }

    /**
     * Works like {@link Scraper#containsAsElement(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#containsAsElement(String)
     */
    public ArrayList<Element> containsAsElement(String searchString, Element startElement){
        TreeTraverser.traversingContainsAsElement(startElement, searchString);
        return TreeTraverser.getContainsElementArray();
    }

    /**
     * A method for seaching if a webpage contains a string.
     * @param searchString string we want to see if exist in the HTML code. IS NOT case sensetive
     * @return returns boolean based on if the String was found or not. True for was found and false for was not found.
     */
    public boolean containsCaseInSensetiveAsBoolean(String searchString){
        Boolean contains = TreeTraverser.traversingContainsCaseInSensetiveAsBoolean(root, searchString);
        return contains;
    }

    /**
     * Works like {@link Scraper#containsCaseInSensetiveAsBoolean(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#containsCaseInSensetiveAsBoolean(String)
     */
    public boolean containsCaseInSensetiveAsBoolean(String searchString, Element startElement){
        Boolean contains = TreeTraverser.traversingContainsCaseInSensetiveAsBoolean(startElement, searchString);
        return contains;
    }

    /**
     * A method for seaching if a webpage contains a string.
     * @param searchString string we want to see if exist in the HTML code. IS NOT case sensetive
     * @return returns all elements where it the search string was found in an arraylist of elements
     */
    public ArrayList<Element> containsCaseInSensetiveAsElement(String searchString){
            TreeTraverser.traversingContainsCaseInSensetiveAsElement(root, searchString);
        return TreeTraverser.getContainsCaseInSensitiveElementArray();
    }

    /**
     * Works like {@link Scraper#containsCaseInSensetiveAsElement(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#containsCaseInSensetiveAsElement(String)
     */
    public ArrayList<Element> containsCaseInSensetiveAsElement(String searchString, Element startElement){
        TreeTraverser.traversingContainsCaseInSensetiveAsElement(startElement, searchString);
        return TreeTraverser.getContainsCaseInSensitiveElementArray();
    }

    /**
     * A method for getting all Images from the webpage
     * @return the source for the images as String in an Arraylist of Strings
     */
    public ArrayList<String> getAllImagesFromPageAsString(){
            TreeTraverser.traversingGetAllImagesFromPageAsString(root);
        return TreeTraverser.getImgStringArray();
    }

    /**
     * Works like {@link Scraper#getAllImagesFromPageAsString()} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getAllImagesFromPageAsString()
     */
    public ArrayList<String> getAllImagesFromPageAsString(Element startElement){
        TreeTraverser.traversingGetAllImagesFromPageAsString(startElement);
        return TreeTraverser.getImgStringArray();
    }

    /**
     * A method for getting all Images from the webpage
     * @return the source for the images as Elements in an Arraylist of Elements
     */
    public ArrayList<Element> getAllImagesFromPageAsElement(){
        TreeTraverser.traversingGetAllImagesFromPageAsElement(root);
        return TreeTraverser.getImgElementArray();
    }

    /**
     * Works like {@link Scraper#getAllImagesFromPageAsElement()} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getAllImagesFromPageAsElement()
     */
    public ArrayList<Element> getAllImagesFromPageAsElement(Element startElement){
        TreeTraverser.traversingGetAllImagesFromPageAsElement(startElement);
        return TreeTraverser.getImgElementArray();
    }

    /**
     * A method for getting a Image with that id from the webpage
     * @param pictureId id as String we want to get the picture from
     * @return the source for the image as String
     */
    public String getImageByIdAsString(String pictureId){
        return TreeTraverser.traversingGetImageByIdAsString(root, pictureId);
    }

    /**
     * Works like {@link Scraper#getImageByIdAsString(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getImageByIdAsString(String)
     */
    public String getImageByIdAsString(String pictureId, Element startElement){
        return TreeTraverser.traversingGetImageByIdAsString(startElement, pictureId);
    }

    /**
     * A method for getting a Image with that id from the webpage
     * @param pictureId id as String we want to get the picture from
     * @return the source for the image as Elements in an Arraylist of Elements
     */
    public Element getImageByIdAsElement(String pictureId){
        return TreeTraverser.traversingGetImageByIdAsElement(root, pictureId);
    }

    /**
     * Works like {@link Scraper#getImageByIdAsElement(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getImageByIdAsElement(String)
     */
    public Element getImageByIdAsElement(String pictureId, Element startElement){
        return TreeTraverser.traversingGetImageByIdAsElement(startElement, pictureId);
    }

    /**
     * A method for getting all the Images with that classname from the webpage
     * @param pictureClass class as String we want to get the pictures from
     * @return the source for the images as String in an Arraylist of Strings
     */
    public ArrayList<String> getImageByClassAsString(String pictureClass){
        TreeTraverser.traversingGetImageByClassAsString(root, pictureClass);
        return TreeTraverser.getImgClassStringArray();
    }

    /**
     * Works like {@link Scraper#getImageByClassAsString(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getImageByClassAsString(String)
     */
    public ArrayList<String> getImageByClassAsString(String pictureClass, Element startElement){
        TreeTraverser.traversingGetImageByClassAsString(startElement, pictureClass);
        return TreeTraverser.getImgClassStringArray();
    }

    /**
     * A method for getting all the Images with that classname from the webpage
     * @param pictureClass class as String we want to get the pictures from
     * @return the source for the images as Elements in an Arraylist of Elements
     */
    public ArrayList<Element> getImageByClassAsElement(String pictureClass){
        TreeTraverser.traversingGetImageByClassAsElement(root, pictureClass);
        return TreeTraverser.getImgClassElementArray();
    }

    /**
     * Works like {@link Scraper#getImageByClassAsElement(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getImageByClassAsElement(String)
     */
    public ArrayList<Element> getImageByClassAsElement(String pictureClass, Element startElement){
        TreeTraverser.traversingGetImageByClassAsElement(startElement, pictureClass);
        return TreeTraverser.getImgClassElementArray();
    }

    /**
     *A method for getting all Videos from the webpage
     * @return the source for the videos as String in an Arraylist of Strings
     */
    public ArrayList<String> getAllVideosFromPageAsString(){
        TreeTraverser.traversingGetAllVideosFromPageAsString(root);
        return TreeTraverser.getVideoStringArray();
    }

    /**
     * Works like {@link Scraper#getAllVideosFromPageAsString()} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getAllVideosFromPageAsString()
     */
    public ArrayList<String> getAllVideosFromPageAsString(Element startElement){
        TreeTraverser.traversingGetAllVideosFromPageAsString(startElement);
        return TreeTraverser.getVideoStringArray();
    }

    /**
     *A method for getting all Videos from the webpage
     * @return the source for the videos as Elements in an Arraylist of Elements
     */
    public ArrayList<Element> getAllVideosFromPageAsElement(){
        TreeTraverser.traversingGetAllVideosFromPageAsElement(root);
        return TreeTraverser.getVideoElementArray();
    }

    /**
     * Works like {@link Scraper#getAllVideosFromPageAsElement()} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getAllVideosFromPageAsElement()
     */
    public ArrayList<Element> getAllVideosFromPageAsElement(Element startElement){
        TreeTraverser.traversingGetAllVideosFromPageAsElement(startElement);
        return TreeTraverser.getVideoElementArray();
    }

    /**
     * A method for getting a Videos with that id from the webpage
     * @param videoId id as String we want to get the video from
     * @return the source for the video as String in a ArrayList of strings
     */
    public String getVideoByIdAsString(String videoId){
        return TreeTraverser.traversingGetVideoByIdAsString(root, videoId);
    }

    /**
     * Works like {@link Scraper#getVideoByIdAsString(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getVideoByIdAsString(String)
     */
    public String getVideoByIdAsString(String videoId, Element startElement){
        return TreeTraverser.traversingGetVideoByIdAsString(startElement, videoId);
    }

    /**
     * A method for getting a Videos with that id from the webpage
     * @param videoId id as String we want to get the video from
     * @return the source for the video as Nods in a Arraylist of Elements
     */
    public Element getVideoByIdAsElement(String videoId){
        return TreeTraverser.traversingGetVideoByIdAsElement(root, videoId);
    }

    /**
     * Works like {@link Scraper#getVideoByIdAsElement(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getVideoByIdAsElement(String)
     */
    public Element getVideoByIdAsElement(String videoId, Element startElement){
        return TreeTraverser.traversingGetVideoByIdAsElement(startElement, videoId);
    }

    /**
     * A method for getting all the Videos with that classname from the webpage
     * @param videoClass class as String we want to get the video from
     * @return the source for the videos as String in an Arraylist of Strings
     */
    public ArrayList<String> getVideoByClassAsString(String videoClass){
        TreeTraverser.traversingGetVideoByClassAsString(root, videoClass);
        return TreeTraverser.getVideoClassStringArray();
    }

    /**
     * Works like {@link Scraper#getVideoByClassAsString(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getVideoByClassAsString(String)
     */
    public ArrayList<String> getVideoByClassAsString(String videoClass, Element startElement){
        TreeTraverser.traversingGetVideoByClassAsString(startElement, videoClass);
        return TreeTraverser.getVideoClassStringArray();
    }

    /**
     * A method for getting all the Videos with that classname from the webpage
     * @param videoClass class as String we want to get the video from
     * @return the source for the videos as Element in an Arraylist of Elements
     */
    public ArrayList<Element> getVideoByClassAsElement(String videoClass){
        TreeTraverser.traversingGetVideoByClassAsElement(root, videoClass);
        return TreeTraverser.getVideoClassElementArray();
    }

    /**
     * Works like {@link Scraper#getVideoByClassAsElement(String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getVideoByClassAsElement(String)
     */
    public ArrayList<Element> getVideoByClassAsElement(String videoClass, Element startElement){
        TreeTraverser.traversingGetVideoByClassAsElement(startElement, videoClass);
        return TreeTraverser.getVideoClassElementArray();
    }

    /**
     * A method for getting all the HTML classes for the webpage
     * @return all found classes as strings in an Arraylist of Strings
     */
    public ArrayList<String> getClassesInPage(){
        TreeTraverser.traversingGetClassesInPage(root);
        return TreeTraverser.getAllClassArray();
    }

    /**
     * Works like {@link Scraper#getClassesInPage()} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getClassesInPage()
     */
    public ArrayList<String> getClassesInPage(Element startElement){
        TreeTraverser.traversingGetClassesInPage(startElement);
        return TreeTraverser.getAllClassArray();
    }

    /**
     * A method for getting all the HTML id's for the webpage
     * @return all found id's as strings in an Arraylist of Strings
     */
    public ArrayList<String> getIdsInPage(){
        TreeTraverser.traversingGetIdsInPage(root);
        return TreeTraverser.getAllIdsArray();
    }

    /**
     * Works like {@link Scraper#getIdsInPage()} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getIdsInPage()
     */
    public ArrayList<String> getIdsInPage(Element startElement){
        TreeTraverser.traversingGetIdsInPage(startElement);
        return TreeTraverser.getAllIdsArray();
    }

    /**
     * A method for getting the content from a attribute of a tag as Element
     * @param tag name of the tag you want to search for
     * @param attribute name of the attribute you want to search for
     * @return a list with the content of the attribute in the tag as Element in an Arraylist of Elements
     */
    public ArrayList<Element> getAttributeContentWithTagAndNameAsElement(String tag, String attribute){
        TreeTraverser.traversingGetAttributeContentWithTagAndNameAsElement(root, tag,attribute);
        return TreeTraverser.getAttributeContentElementArray();
    }

    /**
     * Works like {@link Scraper#getAttributeContentWithTagAndNameAsElement(String, String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getAttributeContentWithTagAndNameAsElement(String, String)
     */
    public ArrayList<Element> getAttributeContentWithTagAndNameAsElement(String tag, String attribute, Element startElement){
        TreeTraverser.traversingGetAttributeContentWithTagAndNameAsElement(startElement, tag,attribute);
        return TreeTraverser.getAttributeContentElementArray();
    }

    /**
     * A method for getting the content from a attribute of a tag as String
     * @param tag name of the tag you want to search for
     * @param attribute name of the attribute you want to search for
     * @return a list with the content of the attribute in the tag as String in an Arraylist of Strings
     */
    public ArrayList<String> getAttributeContentWithTagAndNameAsString(String tag, String attribute){
        TreeTraverser.traversingGetAttributeContentWithTagAndNameAsString(root, tag,attribute);
        return TreeTraverser.getAttributeContentStringArray();
    }

    /**
     * Works like {@link Scraper#getAttributeContentWithTagAndNameAsString(String, String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getAttributeContentWithTagAndNameAsString(String, String)
     */
    public ArrayList<String> getAttributeContentWithTagAndNameAsString(String tag, String attribute, Element startElement){
        TreeTraverser.traversingGetAttributeContentWithTagAndNameAsString(startElement, tag,attribute);
        return TreeTraverser.getAttributeContentStringArray();
    }

    /**
     * A method for getting the content from a attribute of a tag with the id, as String
     * @param id name of the tag you want to search for
     * @param attribute name of the attribute you want to search for
     * @return a list with the content of the attribute in the tag with the id, as String in an Arraylist of Strings
     */
    public ArrayList<String> getAttributeContentWithIdAndNameAsString(String id, String attribute){
        TreeTraverser.traversingGetAttributeContentWithIdAndNameAsString(root, id,attribute);
        return TreeTraverser.getAttributeIdContentStringArray();
    }

    /**
     * Works like {@link Scraper#getAttributeContentWithIdAndNameAsString(String, String)} except you can choose your own startelement
     * from anywhere in the HTML code tree
     *
     * @param startElement Element to start the search from.
     * @see Scraper#getAttributeContentWithIdAndNameAsString(String, String)
     */
    public ArrayList<String> getAttributeContentWithIdAndNameAsString(String id, String attribute, Element startElement){
        TreeTraverser.traversingGetAttributeContentWithIdAndNameAsString(startElement, id,attribute);
        return TreeTraverser.getAttributeIdContentStringArray();
    }



    public Element getRoot() {
        return root;
    }

    public void printBeautyfull(){
        printBeautyfull(root, 0);
    }

    public void printBeautyfull(Element element, int nTabs){

        if (element != null){
            String tabs = "";

            for (int i = 0; i < nTabs; i++){
                tabs += '\t';
            }

            String str = tabs +
                    "Scraper.Element{" +
                    "tag=\'" + element.getTag() + '\'' +
                    ", attributes=" + element.getAttributeNames().toString() +
                    ", textChildren=" + element.getStringChildren().toString() + '}';

            System.out.println(str);

            for (int i = 0; i < element.getElementChildren().size(); i++){
                printBeautyfull(element.getElementChildren().get(i), nTabs+1);
            }
        }
    }
    private static final class TreeBuilder {

        private static int index;

        private static Stack<Element> parentStack;

        protected static Element createTree(String html) throws ParseException {

            parentStack = new Stack<>();

            boolean isComment = false;
            boolean isIgnoreable = false;
            boolean isDoctype = false;

            Element root = null;
            Element buildingElement;

            for(index = 0; index < html.length(); index++){
                char ch = html.charAt(index);

                // ignore content of tag mode
                if (isIgnoreable)
                    if (!(ch == '<' && html.charAt(index+1) == '/'))
                        continue;
                    else
                        isIgnoreable = false;

                //comment mode
                if (isComment){
                    if (ch == '>' && html.charAt(index-1) == '-' && html.charAt(index-2) == '-'){
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
                    if (html.charAt(index+1) == '!') {
                        if (html.charAt(index + 2) == '-' && html.charAt(index + 3) == '-')
                            isComment = true;
                        else
                            isDoctype = true;
                    }
                    else if (html.charAt(index+1) == '/'){
                        readTail(html);
                    }
                    else{
                        buildingElement = readHead(html);

                        if (root == null)
                            root = buildingElement;

                        if (!parentStack.isEmpty())
                            parentStack.peek().addElementChild(buildingElement);

                        if (!isSingletonTag(buildingElement.getTag())){
                            parentStack.push(buildingElement);
                        }

                        if (isIgnoreableContentTag(buildingElement.getTag()))
                            isIgnoreable = true;

                    }
                    continue;
                }

                //read string children if there is a parent to contain it
                if (!parentStack.isEmpty()){
                    readStringChild(html);
                }
            }

            return root;
        }

        private static void readTail(String html) throws ParseException {
            index += 2;

            String tag = "";

            char ch = html.charAt(index);
            while (ch != '>'){
                tag += ch;

                index++;
                ch = html.charAt(index);
            }

            Element parent = parentStack.pop();
            if (!parent.getTag().equals(tag))
                throw new ParseException("tail tag does match the match parent tag=" + tag + " parentelement= " + parent);
        }

        private static void readStringChild(String html){
            String str = "";

            char ch = html.charAt(index);

            while (ch != '<'){
                if (!(ch == '\n' || ch == '\t' || (str.isEmpty() && ch == ' ')))
                    str += ch;

                index++;
                ch = html.charAt(index);
            }

            if (!parentStack.isEmpty())
                if (!str.isEmpty())
                    parentStack.peek().addStringChild(str);

            index--;
        }

        private static Element readHead(String html) throws ParseException {
            char ch = html.charAt(index);
            if (ch != '<')
                throw new ParseException("This is no time to read head! Not <");

            Element buildingElement = new Element();

            index++;
            boolean readTag = true;
            boolean readAttKey = false;
            boolean readAttValue = false;

            int quotes = 0;

            String tag = "";
            String attKey = "";
            String attValue = "";

            ch = html.charAt(index);
            while (ch != '>'){

                if (readTag){
                    if (ch != ' ')
                        tag += ch;
                    else {
                        if (!tag.isEmpty()){
                            buildingElement.setTag(tag);

                            readAttKey = true;
                            readTag = false;
                        }
                    }
                }
                else if (readAttKey){
                    // when attribute name is to end
                    if (ch == '='){
                        readAttKey = false;
                        readAttValue = true;
                    }
                    //for solo attributes
                    else if (ch == ' '){
                        if (!attKey.isEmpty()){

                            buildingElement.getAttributeNames().add(attKey);
                            buildingElement.getAttributes().put(attKey, "");

                            attKey = "";
                        }
                    }
                    else
                        attKey += ch;
                }
                else if (readAttValue){
                    if (quotes < 1){
                        if (ch != '\"')
                            throw new ParseException("Missing quotes after attributtes for key=" + attKey + " breaking char=" + ch, buildingElement);
                        quotes++;
                    }
                    else if (quotes == 1){
                        if (ch != '\"'){
                            attValue += ch;
                        }
                        else{
                            buildingElement.getAttributeNames().add(attKey);
                            buildingElement.getAttributes().put(attKey, attValue);

                            attKey = "";
                            attValue = "";

                            readAttKey = true;
                            readAttValue = false;

                            quotes = 0;
                        }
                    }
                }
                index++;
                ch = html.charAt(index);
            }

            if (buildingElement.getTag() == null){
                if (tag.isEmpty())
                    throw new ParseException("Empty tag is detected");
                else
                    buildingElement.setTag(tag);
            }



            if (!attKey.isEmpty()){
                buildingElement.getAttributeNames().add(attKey);
                buildingElement.getAttributes().put(attKey, "");
            }



            return buildingElement;
        }

        private static boolean isSingletonTag(String tag){
            String[] singletons = {"area", "base", "br", "col", "embed", "hr", "img", "input", "link", "meta", "param", "source", "track", "wbr", "command", "keygen", "menuitem"};

            for (int i = 0; i < singletons.length; i++)
                if (tag.equals(singletons[i]))
                    return true;
            return false;
        }

        private static boolean isIgnoreableContentTag(String tag){
            String[] ignoreableTags = {"script", "style"};

            for (int i = 0; i < ignoreableTags.length; i++){
                if (tag.equals(ignoreableTags[i]))
                    return true;
            }
            return false;
        }
    }

    private static final class TreeTraverser {
        private static ArrayList<String> tagStringArray = new ArrayList<String>();
        private static ArrayList<String> idStringArray = new ArrayList<String>();
        private static ArrayList<String> classStringArray = new ArrayList<String>();
        private static ArrayList<String> linksStringArray = new ArrayList<String>();
        private static ArrayList<String> ImgStringArray = new ArrayList<String>();
        private static ArrayList<String> ImgClassStringArray = new ArrayList<String>();
        private static ArrayList<String> VideoStringArray = new ArrayList<String>();
        private static ArrayList<String> VideoClassStringArray = new ArrayList<String>();
        private static ArrayList<String> AllIdArray = new ArrayList<String>();
        private static ArrayList<String> AllClassArray = new ArrayList<String>();
        private static ArrayList<String> AttributeContentStringArray = new ArrayList<>();
        private static ArrayList<Element> tagElementArray = new ArrayList<>();
        private static ArrayList<Element> idElementArray = new ArrayList<>();
        private static ArrayList<Element> classElementArray = new ArrayList<>();
        private static ArrayList<Element> linksElementArray = new ArrayList<>();
        private static ArrayList<Element> ImgElementArray = new ArrayList<>();
        private static ArrayList<Element> ImgClassElementArray = new ArrayList<>();
        private static ArrayList<Element> VideoElementArray = new ArrayList<>();
        private static ArrayList<Element> VideoClassElementArray = new ArrayList<>();
        private static ArrayList<Element> ContainsElementArray = new ArrayList<>();
        private static ArrayList<Element> ContainsCaseInSensitiveElementArray = new ArrayList<>();
        private static ArrayList<Element> AttributeContentElementArray = new ArrayList<>();
        private static boolean containsVar = false;
        private static String srcString;
        private static Element srcElement;
        private static ArrayList<String> AttributeIdContentStringArray = new ArrayList<>();
        private static String returnSrcString = "";
        private static String returnImgSrcString = "";



        public static void traversingGetContentFromTagAsString(Element element, String tag){

            if (element.getTag().equals(tag)){
                for(int i = 0; i < element.getStringChildren().size(); i++) {
                    tagStringArray.add(element.getStringChildren().get(i));
                }
            }
            for (int i=0; i<element.getElementChildren().size(); i++){
                traversingGetContentFromTagAsString(element.getElementChildren().get(i),tag);
            }
        }

        public static ArrayList<String> getTagStringArray() {
            ArrayList<String> arr = new ArrayList<>(tagStringArray);
            tagStringArray.clear();
            return arr;
        }

        public static void traversingGetContentFromIdAsString(Element element, String id) {

            if (element.getAttributeNames().contains("id")){
                if (element.getAttributes().get("id").equals(id)) {
                    for(int i = 0; i < element.getStringChildren().size(); i++) {
                        idStringArray.add(element.getStringChildren().get(i));
                    }
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetContentFromIdAsString(element.getElementChildren().get(i), id);
            }
        }

        public static ArrayList<String> getIdStringArray() {
            ArrayList<String> arr = new ArrayList<>(idStringArray);
            idStringArray.clear();
            return arr;
        }

        public static void traversingGetContentFromClassAsString(Element element, String className) {
            if (element.getAttributeNames().contains("class")){
                if (element.getAttributes().get("class").equals(className)) {
                    for(int i = 0; i < element.getStringChildren().size(); i++) {
                        classStringArray.add(element.getStringChildren().get(i));
                    }
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetContentFromClassAsString(element.getElementChildren().get(i), className);
            }
        }

        public static ArrayList<String> getClassStringArray() {
            ArrayList<String> arr = new ArrayList<>(classStringArray);
            classStringArray.clear();
            return arr;
        }


        public static void traversingGetLinksInPageAsString(Element element, String url){

            String tag = "a";

            if (element.getTag().equals(tag)){
                if (element.getAttributeNames().contains("href")) {
                    char start = element.getAttributes().get("href").charAt(0);
                    if (start == '/' || start == '#')
                        linksStringArray.add(url + element.getAttributes().get("href"));
                    else
                        linksStringArray.add(element.getAttributes().get("href"));
                }
            }
            for (int i=0; i<element.getElementChildren().size(); i++){
                traversingGetLinksInPageAsString(element.getElementChildren().get(i), url);
            }
        }

        public static ArrayList<String> getLinksStringArray() {
            ArrayList<String> arr = new ArrayList<>(linksStringArray);
            linksStringArray.clear();
            return arr;
        }

        public static Boolean traversingContainsAsBoolean(Element element, String searchString) {

            for (int j = 0; j < element.getStringChildren().size(); j++){
                if (element.getStringChildren().get(j).contains(searchString)) {
                    containsVar = true;
                    break;
                }
            }

            if (!containsVar)
                for (int i = 0; i < element.getElementChildren().size(); i++) {
                    traversingContainsAsBoolean(element.getElementChildren().get(i), searchString);
                }
            Boolean returnContainsVar = containsVar;
            containsVar = false;
            return returnContainsVar;
        }

        public static Boolean traversingContainsCaseInSensetiveAsBoolean(Element element, String searchString) {

            String SS = searchString.toLowerCase();

            for (int j = 0; j < element.getStringChildren().size(); j++){
                if (element.getStringChildren().get(j).toLowerCase().contains(SS)) {
                    containsVar = true;
                    break;
                }
            }

            if (!containsVar)
                for (int i = 0; i < element.getElementChildren().size(); i++) {
                    traversingContainsCaseInSensetiveAsBoolean(element.getElementChildren().get(i), searchString);
                }

            Boolean returnContainsVar = containsVar;
            containsVar = false;
            return returnContainsVar;
        }

        public static void traversingGetAllImagesFromPageAsString(Element element) {
            String tag = "img";

            if (element.getTag().equals(tag)){
                ImgStringArray.add(element.getAttributes().get("src"));
            }
            for (int i=0; i<element.getElementChildren().size(); i++){
                traversingGetAllImagesFromPageAsString(element.getElementChildren().get(i));
            }
        }

        public static ArrayList<String> getImgStringArray() {
            ArrayList<String> arr = new ArrayList<>(ImgStringArray);
            ImgStringArray.clear();
            return arr;
        }

        public static String traversingGetImageByIdAsString(Element element, String pictureId) {

            if (element.getAttributeNames().contains("id")){
                if (element.getAttributes().get("id").equals(pictureId)) {
                    srcString = element.getAttributes().get("src");

                    returnImgSrcString = srcString;
                    srcString = "";
                    return returnImgSrcString;
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetImageByIdAsString(element.getElementChildren().get(i), pictureId);
            }

            return returnImgSrcString;
        }

        public static void traversingGetImageByClassAsString(Element element, String pictureClass) {

            if (element.getAttributeNames().contains("class")){
                if (element.getAttributes().get("class").equals(pictureClass)) {
                    ImgClassStringArray.add(element.getAttributes().get("src"));
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetImageByClassAsString(element.getElementChildren().get(i), pictureClass);
            }
        }

        public static ArrayList<String> getImgClassStringArray() {
            ArrayList<String> arr = new ArrayList<>(ImgClassStringArray);
            ImgClassStringArray.clear();
            return arr;
        }

        public static void traversingGetAllVideosFromPageAsString(Element element) {
            String tag = "video";

            if (element.getTag().equals(tag)){
                if (!element.getElementChildren().isEmpty()){
                    if (element.getElementChildren().get(0).getTag().equals("source")){
                        VideoStringArray.add(element.getElementChildren().get(0).getAttributes().get("src"));
                    }
                }
                else
                    VideoStringArray.add(element.getAttributes().get("src"));
            }
            for (int i=0; i<element.getElementChildren().size(); i++){
                traversingGetAllVideosFromPageAsString(element.getElementChildren().get(i));
            }
        }

        public static ArrayList<String> getVideoStringArray() {
            ArrayList<String> arr = new ArrayList<>(VideoStringArray);
            VideoStringArray.clear();
            return arr;
        }

        public static String traversingGetVideoByIdAsString(Element element, String videoId) {
            if (element.getAttributeNames().contains("id")){
                if (element.getAttributes().get("id").equals(videoId)) {
                    if (!element.getElementChildren().isEmpty()) {
                        if (element.getElementChildren().get(0).getTag().equals("source")) {
                            srcString = element.getElementChildren().get(0).getAttributes().get("src");
                            returnSrcString = srcString;
                            srcString = "";
                            return returnSrcString;
                        }
                    }
                    else {
                        srcString = element.getAttributes().get("src");
                        returnSrcString = srcString;
                        srcString = "";
                        return returnSrcString;

                    }
                }
            }
            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetVideoByIdAsString(element.getElementChildren().get(i), videoId);
            }

            return returnSrcString;
        }

        public static void traversingGetVideoByClassAsString(Element element, String videoClass) {
            if (element.getAttributeNames().contains("class")){
                if (element.getAttributes().get("class").equals(videoClass)) {
                    if (!element.getElementChildren().isEmpty()) {
                        if (element.getElementChildren().get(0).getTag().equals("source")) {
                            VideoClassStringArray.add(element.getElementChildren().get(0).getAttributes().get("src"));
                        }
                    }
                    else
                        VideoClassStringArray.add(element.getAttributes().get("src"));
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetVideoByClassAsString(element.getElementChildren().get(i), videoClass);
            }
        }

        public static ArrayList<String> getVideoClassStringArray() {
            ArrayList<String> arr = new ArrayList<>(VideoClassStringArray);
            VideoClassStringArray.clear();
            return arr;
        }


        public static void traversingGetClassesInPage(Element element) {
            if (element.getAttributeNames().contains("class")){
                if (!AllClassArray.contains(element.getAttributes().get("class"))) {
                    AllClassArray.add(element.getAttributes().get("class"));
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetClassesInPage(element.getElementChildren().get(i));
            }
        }

        public static ArrayList<String> getAllClassArray() {
            ArrayList<String> arr = new ArrayList<>(AllClassArray);
            AllClassArray.clear();
            return arr;
        }

        public static void traversingGetIdsInPage(Element element) {
            if (element.getAttributeNames().contains("id")){
                if (!AllIdArray.contains(element.getAttributes().get("id"))) {
                    AllIdArray.add(element.getAttributes().get("id"));
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetIdsInPage(element.getElementChildren().get(i));
            }
        }

        public static ArrayList<String> getAllIdsArray() {
            ArrayList<String> arr = new ArrayList<>(AllIdArray);
            AllIdArray.clear();
            return arr;
        }


        public static void traversingGetContentFromTagAsElement(Element element, String tag){

            if (element.getTag().equals(tag)){
                tagElementArray.add(element);
            }
            for (int i=0; i<element.getElementChildren().size(); i++){
                traversingGetContentFromTagAsElement(element.getElementChildren().get(i),tag);
            }
        }


        public static ArrayList<Element> getTagElementArray() {
            ArrayList<Element> arr = new ArrayList<>(tagElementArray);
            tagElementArray.clear();
            return arr;

        }

        public static void traversingGetContentFromIdAsElement(Element element, String id) {

            if (element.getAttributeNames().contains("id")){
                if (element.getAttributes().get("id").equals(id)) {
                    idElementArray.add(element);
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetContentFromIdAsElement(element.getElementChildren().get(i), id);
            }
        }


        public static ArrayList<Element> getIdElementArray() {
            ArrayList<Element> arr = new ArrayList<>(idElementArray);
            idElementArray.clear();
            return arr;

        }

        public static void traversingGetContentFromClassAsElement(Element element, String className) {
            if (element.getAttributeNames().contains("class")){
                if (element.getAttributes().get("class").equals(className)) {
                    classElementArray.add(element);
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetContentFromClassAsElement(element.getElementChildren().get(i), className);
            }
        }


        public static ArrayList<Element> getClassElementArray() {
            ArrayList<Element> arr = new ArrayList<>(classElementArray);
            classElementArray.clear();
            return arr;

        }

        public static void traversingGetLinksInPageAsElement(Element element){
            String tag = "a";

            if (element.getTag().equals(tag)){
                linksElementArray.add(element);
            }
            for (int i=0; i<element.getElementChildren().size(); i++){
                traversingGetLinksInPageAsElement(element.getElementChildren().get(i));
            }
        }


        public static ArrayList<Element> getLinksElementArray() {
            ArrayList<Element> arr = new ArrayList<>(linksElementArray);
            linksElementArray.clear();
            return arr;

        }

        public static void traversingContainsAsElement(Element element, String searchString) {

            for (int j = 0; j < element.getStringChildren().size(); j++){
                if (element.getStringChildren().get(j).contains(searchString)) {
                    ContainsElementArray.add(element);
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingContainsAsElement(element.getElementChildren().get(i), searchString);
            }
        }


        public static ArrayList<Element> getContainsElementArray() {
            ArrayList<Element> arr = new ArrayList<>(ContainsElementArray);
            ContainsElementArray.clear();
            return arr;

        }

        public static void traversingContainsCaseInSensetiveAsElement(Element element, String searchString) {

            String SS = searchString.toLowerCase();

            for (int j = 0; j < element.getStringChildren().size(); j++){
                if (element.getStringChildren().get(j).toLowerCase().contains(SS)) {
                    ContainsCaseInSensitiveElementArray.add(element);
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingContainsCaseInSensetiveAsElement(element.getElementChildren().get(i), searchString);
            }
        }


        public static ArrayList<Element> getContainsCaseInSensitiveElementArray() {
            ArrayList<Element> arr = new ArrayList<>(ContainsCaseInSensitiveElementArray);
            ContainsCaseInSensitiveElementArray.clear();
            return arr;

        }

        public static void traversingGetAllImagesFromPageAsElement(Element element) {
            String tag = "img";

            if (element.getTag().equals(tag)){
                ImgElementArray.add(element);
            }
            for (int i=0; i<element.getElementChildren().size(); i++){
                traversingGetAllImagesFromPageAsElement(element.getElementChildren().get(i));
            }
        }


        public static ArrayList<Element> getImgElementArray() {
            ArrayList<Element> arr = new ArrayList<>(ImgElementArray);
            ImgElementArray.clear();
            return arr;

        }

        public static Element traversingGetImageByIdAsElement(Element element, String pictureId) {

            if (element.getAttributeNames().contains("id")){
                if (element.getAttributes().get("id").equals(pictureId)) {
                    srcElement = element;
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetImageByIdAsElement(element.getElementChildren().get(i), pictureId);
            }
            return srcElement;
        }

        public static void traversingGetImageByClassAsElement(Element element, String pictureClass) {

            if (element.getAttributeNames().contains("class")){
                if (element.getAttributes().get("class").equals(pictureClass)) {
                    ImgClassElementArray.add(element);
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetImageByClassAsElement(element.getElementChildren().get(i), pictureClass);
            }
        }


        public static ArrayList<Element> getImgClassElementArray() {
            ArrayList<Element> arr = new ArrayList<>(ImgClassElementArray);
            ImgClassElementArray.clear();
            return arr;

        }

        public static void traversingGetAllVideosFromPageAsElement(Element element) {
            String tag = "video";

            if (element.getTag().equals(tag)){
                VideoElementArray.add(element);
            }
            for (int i=0; i<element.getElementChildren().size(); i++){
                traversingGetAllVideosFromPageAsElement(element.getElementChildren().get(i));
            }
        }


        public static ArrayList<Element> getVideoElementArray() {
            ArrayList<Element> arr = new ArrayList<>(VideoElementArray);
            VideoElementArray.clear();
            return arr;

        }

        public static Element traversingGetVideoByIdAsElement(Element element, String videoId) {
            if (element.getAttributeNames().contains("id")){
                if (element.getAttributes().get("id").equals(videoId)) {
                    srcElement = element;
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetVideoByIdAsElement(element.getElementChildren().get(i), videoId);
            }
            return srcElement;
        }




        public static void traversingGetVideoByClassAsElement(Element element, String videoClass) {
            if (element.getAttributeNames().contains("class")){
                if (element.getAttributes().get("class").equals(videoClass)) {
                    VideoClassElementArray.add(element);
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetVideoByClassAsElement(element.getElementChildren().get(i), videoClass);
            }
        }


        public static ArrayList<Element> getVideoClassElementArray() {
            ArrayList<Element> arr = new ArrayList<>(VideoClassElementArray);
            VideoClassElementArray.clear();
            return arr;
        }

        public static void traversingGetAttributeContentWithTagAndNameAsElement(Element element, String tag, String attribute) {

            if (element.getTag().contains(tag)){
                if (element.getAttributeNames().contains(attribute)) {
                    AttributeContentElementArray.add(element);
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetAttributeContentWithTagAndNameAsElement(element.getElementChildren().get(i), tag, attribute);
            }
        }


        public static ArrayList<Element> getAttributeContentElementArray() {
            ArrayList<Element> arr = new ArrayList<>(AttributeContentElementArray);
            AttributeContentElementArray.clear();
            return arr;

        }

        public static void traversingGetAttributeContentWithTagAndNameAsString(Element element, String tag, String attribute) {
            if (element.getTag().contains(tag)){
                if (element.getAttributeNames().contains(attribute)) {
                    AttributeContentStringArray.add(element.getAttributes().get(attribute));
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetAttributeContentWithTagAndNameAsString(element.getElementChildren().get(i), tag, attribute);
            }
        }

        public static ArrayList<String> getAttributeContentStringArray() {
            ArrayList<String> arr = new ArrayList<>(AttributeContentStringArray);
            AttributeContentStringArray.clear();
            return arr;
        }

        public static void traversingGetAttributeContentWithIdAndNameAsString(Element element, String id, String attribute) {
            if (element.getAttributeNames().contains("id")){
                if (element.getAttributes().get("id").equals(id)) {
                    if (element.getAttributeNames().contains(attribute)) {
                        AttributeIdContentStringArray.add(element.getAttributes().get(attribute));
                    }
                }
            }

            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingGetAttributeContentWithIdAndNameAsString(element.getElementChildren().get(i), id, attribute);
            }
        }

        public static ArrayList<String> getAttributeIdContentStringArray() {
            return AttributeIdContentStringArray;
        }
    }

}

