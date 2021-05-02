import Scraper.Exceptions.ParseException;
import Scraper.Scraper;
import Scraper.SoupNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class Test_LowLevelScraper {
    String siteContent = "<!DOCTYPE html> <body id=\"hi\" class=\"class1\">Hei jeg hedder dorte <img id=\"bestebildet\" class=\"bestebildene\" src=\"img_girl.jpg\" alt=\"Girl in a jacket\" width=\"500\" height=\"600\"> <video id=\"bestevideoen\" class=\"bestevideoene\" src=\"video_girl.mp4\" alt=\"Girl in a jacket\" width=\"500\" height=\"600\"></video><p lang=\"no\" id=\"para\" class=\"testClass\">yo who<a src=\"blabal\" target=\"_blank\" class=\"testClass\" href=\"https://www.w3schools.com/\" >https://www.w3schools.com/</a><p>yo mama</p></p>og min mor er borte <a href=\"https://www.test.com/\"> Testloink2 </a><img class=\"bestebildene\" src=\"www.google.com/hjelp/img_boy.jpg\" alt=\"Girl in an jacket\" width=\"500\" height=\"600\"> <video src=\"www.google.com/hjelp/video_boy.wma\" alt=\"Boy in a jacket\" width=\"500\" height=\"600\"></video> <h1 id=\"header1\">chIld, of CHIld.</h1></body>";
    Scraper sc = new Scraper(siteContent, true);
    SoupNode rootNode = sc.getRoot();

    public Test_LowLevelScraper() throws ParseException {
    }

    @Test
    public void createsTreeCorrectly() {
        /*String expectedOutput = "SoupNode{tag='body', attributes=[id, class], stringChildren=[Hei jeg hedder dorte ], nodeChildren= \n\t" +
                "[SoupNode{tag='img', attributes=[id, class, src, alt, width, height], stringChildren=[ ], nodeChildren= \n\t" +
                "[SoupNode{tag='video', attributes=[id, class, src, alt, width, height], stringChildren=[og min mor er borte ], nodeChildren= \n\t" +
                "[SoupNode{tag='p', attributes=[lang, id, class], stringChildren=[yo who], nodeChildren= \n\t" +
                "[SoupNode{tag='a', attributes=[src, target, class, href], stringChildren=[https://www.w3schools.com/], nodeChildren= \n\t" +
                "[]}, SoupNode{tag='p', attributes=[], stringChildren=[yo mama], nodeChildren= \n\t" +
                "[]}]}, SoupNode{tag='a', attributes=[href], stringChildren=[ Testloink2 ], nodeChildren= \n\t" +
                "[]}, SoupNode{tag='img', attributes=[class, src, alt, width, height], stringChildren=[ ], nodeChildren= \n\t" +
                "[SoupNode{tag='video', attributes=[src, alt, width, height], stringChildren=[ ], nodeChildren= \n\t" +
                "[SoupNode{tag='h1', attributes=[id], stringChildren=[chIld, of CHIld.], nodeChildren= \n\t" +
                "[]}]}, SoupNode{tag='', attributes=[body], stringChildren=[], nodeChildren= \n\t" +
                "[]}]}]}]}]}";
                */
        String expectedOutput = "Scraper.SoupNode{tag='body', attributes={class=class1, id=hi}, attributeNames=[id, class], nodeChildren=[Scraper.SoupNode{tag='img', attributes={height=600, width=500, class=bestebildene, alt=Girl in a jacket, src=img_girl.jpg, id=bestebildet}, attributeNames=[id, class, src, alt, width, height], nodeChildren=[], stringChildren=[]}, Scraper.SoupNode{tag='video', attributes={height=600, width=500, class=bestevideoene, alt=Girl in a jacket, src=video_girl.mp4, id=bestevideoen}, attributeNames=[id, class, src, alt, width, height], nodeChildren=[], stringChildren=[]}, Scraper.SoupNode{tag='p', attributes={class=testClass, lang=no, id=para}, attributeNames=[lang, id, class], nodeChildren=[Scraper.SoupNode{tag='a', attributes={href=https://www.w3schools.com/, class=testClass, target=_blank, src=blabal}, attributeNames=[src, target, class, href], nodeChildren=[], stringChildren=[https://www.w3schools.com/]}, Scraper.SoupNode{tag='p', attributes={}, attributeNames=[], nodeChildren=[], stringChildren=[yo mama]}], stringChildren=[yo who]}, Scraper.SoupNode{tag='a', attributes={href=https://www.test.com/}, attributeNames=[href], nodeChildren=[], stringChildren=[Testloink2 ]}, Scraper.SoupNode{tag='img', attributes={height=600, width=500, class=bestebildene, alt=Girl in an jacket, src=www.google.com/hjelp/img_boy.jpg}, attributeNames=[class, src, alt, width, height], nodeChildren=[], stringChildren=[]}, Scraper.SoupNode{tag='video', attributes={height=600, width=500, alt=Boy in a jacket, src=www.google.com/hjelp/video_boy.wma}, attributeNames=[src, alt, width, height], nodeChildren=[], stringChildren=[]}, Scraper.SoupNode{tag='h1', attributes={id=header1}, attributeNames=[id], nodeChildren=[], stringChildren=[chIld, of CHIld.]}], stringChildren=[Hei jeg hedder dorte , og min mor er borte ]}";
        String actual = rootNode.toString();
        assertEquals(expectedOutput, actual);
    }

    @Test
    public void requestURLReturnsStringCorrectly() {
        //Temporary before test method idea
        fail("Not implemented yet");
    }

    @Test
    public void returnsCorrectWebsiteContentString() {
        assertEquals(siteContent, sc.getWebsiteContent());
    }

    @Test
    public void TagContentFromUserRequestedHtmlTagReturnedCorrectly() {
        String[] expectedArray = {"yo who", "yo mama"};
        ArrayList<String> actualArray = sc.getContentFromTagAsString("p");

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void IdContentFromUserRequestedHtmlIDReturnedCorrectly() {
        String[] expectedArray = {"chIld, of CHIld."};
        ArrayList<String> actualArray = sc.getContentFromIdAsString("header1");

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void ClassContentFromUserRequestedHtmlClassReturnedCorrectly() {
        String[] expectedArray = {"yo who", "https://www.w3schools.com/"};
        ArrayList<String> actualArray = sc.getContentFromClassAsString("testClass");

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void returnsLinksFromSiteCorrectly() {
        String[] expectedArray = {"https://www.w3schools.com/", "https://www.test.com/"};
        ArrayList<String> actualArray = sc.getLinksInPageAsString();

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void returnsTrueIfSiteContainsCaseSensitiveStringFromUser() {
        boolean expected = true;
        boolean actual = sc.containsAsBoolean("Hei");

        assertEquals(expected, actual);
    }

    @Test
    public void returnsFalseIfSiteDoesNotContainsCaseSensitiveStringFromUser() {
        boolean expected = false;
        boolean actual = sc.containsAsBoolean("sdhaesf");

        assertEquals(expected, actual);
    }

    @Test
    public void returnsTrueIfSiteContainsStringFromUser() {
        boolean expected = true;

        //Not a single string is in full caps-lock
        boolean actual = sc.containsCaseInSensetiveAsBoolean("HEI");

        assertEquals(expected, actual);
    }

    @Test
    public void returnsFalseIfSiteDoesNotConatainStringFromUser() {
        boolean expected = false;
        boolean actual = sc.containsCaseInSensetiveAsBoolean("sdhaesf");

        assertEquals(expected, actual);
    }

    @Test
    public void getsAllImagineLinksFromSite() {
        String[] expectedArray = {"img_girl.jpg", "www.google.com/hjelp/img_boy.jpg"};
        ArrayList<String> actualArray = sc.getAllImagesFromPageAsString();

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void getsSpecificImageByIdFromSite() {
        String expected = "img_girl.jpg";
        String actual = sc.getImageByIdAsString("bestebildet");

        assertEquals(expected, actual);
    }

    @Test
    public void getsSpecificImagesByClassNameFromSite() {
        String[] expectedArray = {"img_girl.jpg", "www.google.com/hjelp/img_boy.jpg"};
        ArrayList<String> actualArray = sc.getImageByClassAsString("bestebildene");

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void getsAllVideosFromSiteCorrectly() {
        String[] expectedArray = {"video_girl.mp4", "www.google.com/hjelp/video_boy.wma"};
        ArrayList<String> actualArray = sc.getAllVideosFromPageAsString();

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void getsSpecificVideoByIdFromSite() {
        String expected = "video_girl.mp4";
        String actual = sc.getVideoByIdAsString("bestevideoen");

        assertEquals(expected, actual);
    }

    @Test
    public void getsSpecificVideosByClassNameFromSite() {
        String[] expectedArray = {"video_girl.mp4"};
        ArrayList<String> actualArray = sc.getVideoByClassAsString("bestevideoene");

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void returnsAllClassesInSiteCorrectly() {
        String[] expectedArray = {"class1", "bestebildene", "bestevideoene", "testClass"};
        ArrayList<String> actualArray = sc.getClassesInPage();
        System.out.println(sc.getClassesInPage());
        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void returnsAllIdsInSiteCorrectly() {
        String[] expectedArray = {"hi", "bestebildet", "bestevideoen", "para", "header1"};
        ArrayList<String> actualArray = sc.getIdsInPage();

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void getsSpecificAttributeByTagAndAttributeNameFromSite() {
        String[] expectedArray = {"500","500"};
        ArrayList<String> actualArray = sc.getAttributeContentWithTagAndNameAsString("img","width");

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void getsSpecificAttributeByIdAndAttributeNameFromSite() {
        String[] expectedArray = {"Girl in a jacket"};
        ArrayList<String> actualArray = sc.getAttributeContentWithIdAndNameAsString("bestebildet","alt");

        assertArrayEquals(expectedArray, actualArray.toArray());
    }
}
