import Scraper.Scraper;

import java.io.IOException;

import Scraper.TreeBuilder;


// denne klassen kommer ikke til å være med i endelige innlevering
public class Main {
    public static void main(String[] args) throws IOException, Exception {

        Scraper sr = new Scraper("https://webscraper.io/test-sites/e-commerce/allinone");

        sr.printBeautyfull();



        //System.out.println(sr.getRoot());

        //System.out.println();
        //sr.printBeautyfull();

/*
        //returns string/Array of Strings
        System.out.println("getContentFromTag: "+ sr.getContentFromTagAsString("h1"));
        System.out.println("getContentFromId: "+ sr.getContentFromIdAsString("header1"));
        System.out.println("getContentFromClass: "+ sr.getContentFromClassAsString("class1"));
        System.out.println("getLinksInPage: "+ sr.getLinksInPageAsString());
        System.out.println("getAllImagesFromPage : " + sr.getAllImagesFromPageAsString());
        System.out.println("getImageById : " + sr.getImageByIdAsString("bestebildet"));
        System.out.println("getImageByClass : " + sr.getImageByClassAsString("bestebildene"));
        System.out.println("getAllVideosFromPage : " + sr.getAllVideosFromPageAsString());
        System.out.println("getVideoById : " + sr.getVideoByIdAsString("bestevideoen"));
        System.out.println("getVideoByClass : " + sr.getVideoByClassAsString("bestevideoene"));
        System.out.println("getClassesInPage : " + sr.getClassesInPage());
        System.out.println("getIdsInPage : " + sr.getIdsInPage());

        //returns boolean
        System.out.println("contains: " + sr.containsAsBoolean("hild"));
        System.out.println("contains CaseInSensetive: " + sr.containsCaseInSensetiveAsBoolean("Child"));

        //return nodes
        System.out.println("getContentFromTag: "+ sr.getContentFromTagAsNode("h1"));
        System.out.println("getContentFromId: "+ sr.getContentFromIdAsNode("header1"));
        System.out.println("getContentFromClass: "+ sr.getContentFromClassAsNode("class1"));
        System.out.println("getLinksInPage: "+ sr.getLinksInPageAsNode());
        System.out.println("contains: " + sr.containsAsNode("hild"));
        System.out.println("contains CaseInSensetive: " + sr.containsCaseInSensetiveAsNode("Child"));
        System.out.println("getAllImagesFromPage : " + sr.getAllImagesFromPageAsNode());
        System.out.println("getImageById : " + sr.getImageByIdAsNode("bestebildet"));
        System.out.println("getImageByClass : " + sr.getImageByClassAsNode("bestebildene"));
        System.out.println("getAllVideosFromPage : " + sr.getAllVideosFromPageAsNode());
        System.out.println("getVideoById : " + sr.getVideoByIdAsNode("bestevideoen"));
        System.out.println("getVideoByClass : " + sr.getVideoByClassAsNode("bestevideoene"));
        System.out.println("contains: " + sr.containsAsNode("hild"));
        System.out.println("contains CaseInSensetive: " + sr.containsCaseInSensetiveAsNode("Child"));
*/


    }
}



