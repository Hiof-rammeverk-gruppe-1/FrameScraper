import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class Test_LowLevelScraper {
    SoupNode rootNode;
    Scraper sc;

    String siteContent = "<body id=\"hi\" class=\"class1\">Hei jeg hedder dorte <img id=\"bestebildet\" class=\"bestebildene\" src=\"img_girl.jpg\" alt=\"Girl in a jacket\" width=\"500\" height=\"600\"> <video id=\"bestevideoen\" class=\"bestevideoene\" src=\"video_girl.mp4\" alt=\"Girl in a jacket\" width=\"500\" height=\"600\"><p lang=\"no\" id=\"para\">yo who<a src=\"blabal\" target=\"_blank\" href=\"https://www.w3schools.com/\" >https://www.w3schools.com/</a><p>yo mama</p></p>og min mor er borte <a href=\"https://www.test.com/\"> Testloink2 </a><img class=\"bestebildene\" src=\"www.google.com/hjelp/img_boy.jpg\" alt=\"Girl in an jacket\" width=\"500\" height=\"600\"> <video src=\"www.google.com/hjelp/video_boy.wma\" alt=\"Boy in a jacket\" width=\"500\" height=\"600\"> <h1 id=\"header1\">chIld, of CHIld.</h1></body>";

    @Test
    public void createsTreeCorrectly() {
        sc = new Scraper(siteContent, true);
        rootNode = sc.getRoot();
        String expectedOutput = "SoupNode{tag='body', attributes=[id, class], stringChildren=[Hei jeg hedder dorte ], nodeChildren= \n\t" +
                "[SoupNode{tag='img', attributes=[id, class, src, alt, width, height], stringChildren=[ ], nodeChildren= \n\t" +
                "[SoupNode{tag='video', attributes=[id, class, src, alt, width, height], stringChildren=[og min mor er borte ], nodeChildren= \n\t" +
                "[SoupNode{tag='p', attributes=[lang, id], stringChildren=[yo who], nodeChildren= \n\t" +
                "[SoupNode{tag='a', attributes=[src, target, href], stringChildren=[https://www.w3schools.com/], nodeChildren= \n\t" +
                "[]}, SoupNode{tag='p', attributes=[], stringChildren=[yo mama], nodeChildren= \n\t" +
                "[]}]}, SoupNode{tag='a', attributes=[href], stringChildren=[ Testloink2 ], nodeChildren= \n\t" +
                "[]}, SoupNode{tag='img', attributes=[class, src, alt, width, height], stringChildren=[ ], nodeChildren= \n\t" +
                "[SoupNode{tag='video', attributes=[src, alt, width, height], stringChildren=[ ], nodeChildren= \n\t" +
                "[SoupNode{tag='h1', attributes=[id], stringChildren=[chIld, of CHIld.], nodeChildren= \n\t" +
                "[]}]}, SoupNode{tag='', attributes=[body], stringChildren=[], nodeChildren= \n\t" +
                "[]}]}]}]}]}";
        String actual = rootNode.toString();
        assertEquals(expectedOutput, actual);
    }

    @Test
    public void requestURLReturnsStringCorrectly() {
        fail("Not implemented yet");
    }

    @Test
    public void returnsCorrectWebsiteContentString() {
        fail("Not implemented yet");
    }

    @Test
    public void TagContentFromUserRequestedHtmlTagReturnedCorrectly() {
        fail("Not implemented yet");
    }

    @Test
    public void IdContentFromUserRequestedHtmlIDReturnedCorrectly() {
        fail("Not implemented yet");
    }

    @Test
    public void ClassContentFromUserRequestedHtmlClassReturnedCorrectly() {
        fail("Not implemented yet");
    }

    @Test
    public void returnsLinksFromSiteCorrectly() {
        fail("Not implemented yet");
    }

    @Test
    public void returnsTrueIfSiteContainsCaseSensitiveStringFromUser() {
        fail("Not implemented yet");
    }

    @Test
    public void returnsFalseIfSiteDoesNotContainCaseSensitiveStringFromUser() {
        fail("Not implemented yet");
    }

    @Test
    public void returnsTrueIfSiteContainsStringFromUser() {
        fail("Not implemented yet");
    }

    @Test
    public void returnsFalseIfSiteDoesNotConatainStringFromUser() {
        fail("Not implemented yet");
    }

    @Test
    public void getsAllImagineLinksFromSite() {
        fail("Not implemented yet");
    }

    @Test
    public void getsSpecificImageByIdFromSite() {
        fail("Not implemented yet");
    }

    @Test
    public void getsSpecificImagesByClassNameFromSite() {
        fail("Not implemented yet");
    }

    @Test
    public void getsAllVideosFromSiteCorrectly() {
        fail("Not implemented yet");
    }

    @Test
    public void getsSpecificVideoByIdFromSite() {
        fail("Not implemented yet");
    }

    @Test
    public void getsSpecificVideosByClassNameFromSite() {
        fail("Not implemented yet");
    }

    @Test
    public void returnsAllClassesInSiteCorrectly() {
        fail("Not implemented yet");
    }

    @Test
    public void returnsAllIdsInSiteCorrectly() {
        fail("Not implemented yet");
    }
}

