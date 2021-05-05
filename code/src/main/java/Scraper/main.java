package Scraper;

public class main {
    public static void main(String[] args){
        /*Scraper sc = Scraper.buildWithString("<!DOCTYPE html> <body id=\"hi\" class=\"class1\">Hei jeg hedder dorte <img id=\"bestebildet\" class=\"bestebildene\" src=\"img_girl.jpg\" alt=\"Girl in a jacket\" width=\"500\" height=\"600\"> <video id=\"bestevideoen\" class=\"bestevideoene\" src=\"video_girl.mp4\" alt=\"Girl in a jacket\" width=\"500\" height=\"600\"></video><p lang=\"no\" id=\"para\" class=\"testClass\">yo who<a src=\"blabal\" target=\"_blank\" class=\"testClass\" href=\"https://www.w3schools.com/\" >https://www.w3schools.com/</a><p>yo mama</p></p>og min mor er borte <a href=\"https://www.test.com/\"> Testloink2 </a><img class=\"bestebildene\" src=\"www.google.com/hjelp/img_boy.jpg\" alt=\"Girl in an jacket\" width=\"500\" height=\"600\"> <video src=\"www.google.com/hjelp/video_boy.wma\" alt=\"Boy in a jacket\" width=\"500\" height=\"600\"></video> <h1 id=\"header1\">chIld, of CHIld.</h1></body>");
        System.out.println(sc.getVideoByIdAsNode("bestebildet"));
        System.out.println(sc.getImageByIdAsNode("bestebildet"));*/

        Scraper sc = Scraper.buildWithString("<html><body><h1>Hello world</h1><p>This is the text-content of the HTML-p-tag</p><p>Text2</p></body></html>");

        System.out.println(sc.getContentFromTagAsString("p"));
    }
}
