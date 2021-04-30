package Scraper;

import Scraper.Exceptions.ParseException;

import java.io.IOException;
import java.util.Arrays;

public class TestMain {
    public static void main(String[] args) {
        try {
            Scraper sc = new Scraper("<!DOCTYPE html> <body id=\"hi\" class=\"class1\">Hei jeg hedder dorte <img id=\"bestebildet\" class=\"bestebildene\" src=\"img_girl.jpg\" alt=\"Girl in a jacket\" width=\"500\" height=\"600\"> <video id=\"bestevideoen\" class=\"bestevideoene\" src=\"video_girl.mp4\" alt=\"Girl in a jacket\" width=\"500\" height=\"600\"></video><p lang=\"no\" id=\"para\" class=\"testClass\">yo who<a src=\"blabal\" target=\"_blank\" class=\"testClass\" href=\"https://www.w3schools.com/\" >https://www.w3schools.com/</a><p>yo mama</p></p>og min mor er borte <a href=\"https://www.test.com/\"> Testloink2 </a><img class=\"bestebildene\" src=\"www.google.com/hjelp/img_boy.jpg\" alt=\"Girl in an jacket\" width=\"500\" height=\"600\"> <video src=\"www.google.com/hjelp/video_boy.wma\" alt=\"Boy in a jacket\" width=\"500\" height=\"600\"></video> <h1 id=\"header1\">chIld, of CHIld.</h1></body>", true);


            //System.out.println(sc.getLinksInPageAsString(sc.getContentFromIdAsNode("hi").get(0)));
            //System.out.println(sc.getVideoByClassAsNode("beste"));
            System.out.println(sc.getRoot());
            System.out.println(sc.getAllImagesFromPageAsString());
            System.out.println(sc.getAllImagesFromPageAsString());
            System.out.println(sc.getAllImagesFromPageAsString());


        } catch (ParseException e) {
            System.out.println("shit went boom");
        }
    }
}
