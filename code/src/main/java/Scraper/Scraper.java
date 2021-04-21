package Scraper;

import Scraper.Exceptions.ParseException;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

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
        this.root = TreeBuilder.createTree(websiteContent);
    }

    public Scraper(String siteContent, boolean test) throws ParseException {
        websiteContent = siteContent;
        TreeBuilder.createTree(siteContent);
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
