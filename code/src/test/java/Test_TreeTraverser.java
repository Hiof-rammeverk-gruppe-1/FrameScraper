import HTMLString.HTMLToString;
import Scraper.Exceptions.ParseException;
import Scraper.Scraper;
import Scraper.Element;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class Test_TreeTraverser {
    String siteContent = "<!DOCTYPE html> <body id=\"hi\" class=\"class1\">Hei jeg hedder dorte <img id=\"bestebildet\" class=\"bestebildene\" src=\"img_girl.jpg\" alt=\"Girl in a jacket\" width=\"500\" height=\"600\"> <video id=\"bestevideoen\" class=\"bestevideoene\" src=\"video_girl.mp4\" alt=\"Girl in a jacket\" width=\"500\" height=\"600\"></video><p lang=\"no\" id=\"para\" class=\"testClass\">yo who<a src=\"blabal\" target=\"_blank\" class=\"testClass\" href=\"https://www.w3schools.com/\" >https://www.w3schools.com/</a><p>yo mama</p></p>og min mor er borte <a href=\"https://www.test.com/\"> Testloink2 </a><img class=\"bestebildene\" src=\"www.google.com/hjelp/img_boy.jpg\" alt=\"Girl in an jacket\" width=\"500\" height=\"600\"> <video src=\"www.google.com/hjelp/video_boy.wma\" alt=\"Boy in a jacket\" width=\"500\" height=\"600\"></video> <h1 id=\"header1\">chIld, of CHIld.</h1></body>";
    Scraper sc = new Scraper(siteContent, true);
    Element rootNode = sc.getRoot();

    public Test_TreeTraverser() throws ParseException {
    }

    @Test
    public void createsTreeCorrectly() {
        /*String expectedOutput = "Element{tag='body', attributes=[id, class], stringChildren=[Hei jeg hedder dorte ], nodeChildren= \n\t" +
                "[Element{tag='img', attributes=[id, class, src, alt, width, height], stringChildren=[ ], nodeChildren= \n\t" +
                "[Element{tag='video', attributes=[id, class, src, alt, width, height], stringChildren=[og min mor er borte ], nodeChildren= \n\t" +
                "[Element{tag='p', attributes=[lang, id, class], stringChildren=[yo who], nodeChildren= \n\t" +
                "[Element{tag='a', attributes=[src, target, class, href], stringChildren=[https://www.w3schools.com/], nodeChildren= \n\t" +
                "[]}, Element{tag='p', attributes=[], stringChildren=[yo mama], nodeChildren= \n\t" +
                "[]}]}, Element{tag='a', attributes=[href], stringChildren=[ Testloink2 ], nodeChildren= \n\t" +
                "[]}, Element{tag='img', attributes=[class, src, alt, width, height], stringChildren=[ ], nodeChildren= \n\t" +
                "[Element{tag='video', attributes=[src, alt, width, height], stringChildren=[ ], nodeChildren= \n\t" +
                "[Element{tag='h1', attributes=[id], stringChildren=[chIld, of CHIld.], nodeChildren= \n\t" +
                "[]}]}, Element{tag='', attributes=[body], stringChildren=[], nodeChildren= \n\t" +
                "[]}]}]}]}]}";
                */
        String expectedOutput = "Scraper.Element{tag='body', attributes={class=class1, id=hi}, attributeNames=[id, class], nodeChildren=[Scraper.Element{tag='img', attributes={height=600, width=500, class=bestebildene, alt=Girl in a jacket, src=img_girl.jpg, id=bestebildet}, attributeNames=[id, class, src, alt, width, height], nodeChildren=[], stringChildren=[]}, Scraper.Element{tag='video', attributes={height=600, width=500, class=bestevideoene, alt=Girl in a jacket, src=video_girl.mp4, id=bestevideoen}, attributeNames=[id, class, src, alt, width, height], nodeChildren=[], stringChildren=[]}, Scraper.Element{tag='p', attributes={class=testClass, lang=no, id=para}, attributeNames=[lang, id, class], nodeChildren=[Scraper.Element{tag='a', attributes={href=https://www.w3schools.com/, class=testClass, target=_blank, src=blabal}, attributeNames=[src, target, class, href], nodeChildren=[], stringChildren=[https://www.w3schools.com/]}, Scraper.Element{tag='p', attributes={}, attributeNames=[], nodeChildren=[], stringChildren=[yo mama]}], stringChildren=[yo who]}, Scraper.Element{tag='a', attributes={href=https://www.test.com/}, attributeNames=[href], nodeChildren=[], stringChildren=[Testloink2 ]}, Scraper.Element{tag='img', attributes={height=600, width=500, class=bestebildene, alt=Girl in an jacket, src=www.google.com/hjelp/img_boy.jpg}, attributeNames=[class, src, alt, width, height], nodeChildren=[], stringChildren=[]}, Scraper.Element{tag='video', attributes={height=600, width=500, alt=Boy in a jacket, src=www.google.com/hjelp/video_boy.wma}, attributeNames=[src, alt, width, height], nodeChildren=[], stringChildren=[]}, Scraper.Element{tag='h1', attributes={id=header1}, attributeNames=[id], nodeChildren=[], stringChildren=[chIld, of CHIld.]}], stringChildren=[Hei jeg hedder dorte , og min mor er borte ]}";
        String actual = rootNode.toString();
        assertEquals(expectedOutput, actual);
    }

    @Test
    public void requestURLReturnsStringCorrectly() {
        String expected = "<!DOCTYPE html> <!--[if lt IE 7]> <html class=\"no-js lt-ie9 lt-ie8 lt-ie7\"> <![endif]--> <!--[if IE 7]> <html class=\"no-js lt-ie9 lt-ie8\"> <![endif]--> <!--[if IE 8]> <html class=\"no-js lt-ie9\"> <![endif]--> <!--[if gt IE 8]><!--> <html class=\"no-js\"> <!--<![endif]--> <head> <meta charset=\"utf-8\"> <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"> <title>Web Scraper Testing Ground</title> <meta name=\"description\" content=\"\"> <meta name=\"viewport\" content=\"width=device-width\"> <link rel=\"stylesheet\" href=\"css/normalize.css\"> <link rel=\"stylesheet\" href=\"css/main.css\"> <link rel=\"shortcut icon\" href=\"/img/logo.png\" type=\"image/x-icon\"> <script src=\"js/vendor/modernizr-2.6.1.min.js\"></script> <script src=\"js/vendor/jquery-1.9.1.min.js\"></script> <script src=\"js/vendor/jquery-ui-1.10.2.min.js\"></script> <script src=\"js/plugins.js\"></script> <script src=\"js/main.js\"></script> <link rel=\"stylesheet\" href=\"css/QapTcha.jquery.css\" /> <script src=\"js/QapTcha.jquery.js\"></script> <link rel=\"stylesheet\" href=\"fancy-captcha/captcha.css\" /> <script src=\"fancy-captcha/jquery.captcha.js\"></script> </head> <body> <script type=\"text/javascript\"> var _gaq = _gaq || []; _gaq.push(['_setAccount', 'UA-4436411-8']); _gaq.push(['_setDomainName', 'extract-web-data.com']); _gaq.push(['_trackPageview']); (function() { var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true; ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js'; var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s); })(); </script> <!--[if lt IE 7]> <p class=\"chromeframe\">You are using an outdated browser. <a href=\"http://browsehappy.com/\">Upgrade your browser today</a> or <a href=\"http://www.google.com/chromeframe/?redirect=true\">install Google Chrome Frame</a> to better experience this site.</p> <![endif]--> <div id=\"topbar\"></div> <a href=\"index.html\" style=\"text-decoration: none\"> <div id=\"title\">WEB SCRAPER TESTING GROUND</div> <div id=\"logo\"></div> </a> <div id=\"content\"> <h1>AJAX</h1> <div id=\"caseinfo\">Today many websites load their content using <a href=\"https://en.wikipedia.org/wiki/Ajax_(programming)\" target=\"_blank\">AJAX</a> (Asynchronous JavaScript and XML). This often greatly improves user experience but also may become a stumbling block for some web scrapers. At the same time a good web scraper should be able to parse all major data formats that are used in AJAX technology: <a href=\"http://en.wikipedia.org/wiki/HTML\" target=\"_blank\">HTML</a>, <a href=\"http://en.wikipedia.org/wiki/XML\" target=\"_blank\">XML</a> and <a href=\"http://en.wikipedia.org/wiki/JSON\" target=\"_blank\">JSON</a>.</p> <p>You may use this test to check scraper's ability to:</p> <ol> <li>Receive HTML via AJAX and parse it</li> <li>Receive XML via AJAX and parse it</li> <li>Receive JSON via AJAX and parse it</li> </ol> <p>How does it work:</p> <ul> <li>The browser receives three lists of three names through AJAX in three different formats: HTML, XML and JSON</li> <li>HTML data is received automatically as the page is loaded</li> <li>To receive XML and JSON data you need to click to a corresponding link</li> <li>The scraper should be able to extract all nine names</li> </ul> </div> <hr/> <div id=\"case_ajax\"> <h3>HTML</h3> <div id=\"ajaxHtml\" class=\"case\"></div> <h3>XML</h3> <div id=\"ajaxXml\" class=\"case\"><a href=\"#\" onclick=\"getXml ();return false;\">Click to get XML through AJAX</a></div> <h3>JSON</h3> <div id=\"ajaxJson\" class=\"case\"><a href=\"#\" onclick=\"getJson ();return false;\">Click to get JSON through AJAX</a></div> </div> <br/><br/><br/> <script> $(document).ready (function (){ $(\"#ajaxHtml\").load (\"ajax?mode=html\"); }); function getXml () { $.get('ajax?mode=xml', function(data) { xmlDoc = $.parseXML(data); xml = $(xmlDoc); var html = \"<ul>\"; xml.find(\"name\").each (function () {html += \"<li>\"+$(this).text()+\"</li>\";}); $(\"#ajaxXml\").html (html+\"</ul>\"); }); } function getJson () { $.getJSON('ajax?mode=json', function(data) { var html = \"<ul>\"; $.each(data.names, function(key, val) {html += \"<li>\"+val+\"</li>\";}); $(\"#ajaxJson\").html (html+\"</ul>\"); }); } </script> </div> </body> </html> ";
        String actual = HTMLToString.requestHTMLWithUrl("http://testing-ground.webscraping.pro/ajax.html");
        assertEquals(expected, actual);

    }

    @Test
    public void returnsCorrectWebsiteContentString() {
        assertEquals(siteContent, sc.getWebsiteContent());
    }

    @Test
    public void TagContentFromUserRequestedHtmlTagReturnedCorrectlyAsString() {
        String[] expectedArray = {"yo who", "yo mama"};
        ArrayList<String> actualArray = sc.getContentFromTagAsString("p");

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void TagContentFromUserRequestedHtmlTagReturnedCorrectlyAsNode() {
        ArrayList<Element> actualArray = sc.getContentFromTagAsNode("p");
        String expected = "yo who";
        String actual = actualArray.get(0).getStringChildren().get(0);
        assertEquals(expected, actual);

        expected = "yo mama";
        actual = actualArray.get(1).getStringChildren().get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void IdContentFromUserRequestedHtmlIDReturnedCorrectlyAsString() {
        String[] expectedArray = {"chIld, of CHIld."};
        ArrayList<String> actualArray = sc.getContentFromIdAsString("header1");

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void IdContentFromUserRequestedHtmlIDReturnedCorrectlyAsNode() {
        String[] expectedArray = {"chIld, of CHIld."};
        ArrayList<Element> actualArray = sc.getContentFromIdAsNode("header1");

        for(int i = 0; i < actualArray.size(); i++) {
            assertEquals(expectedArray[i], actualArray.get(i).getStringChildren().get(0));
        }
    }

    @Test
    public void ClassContentFromUserRequestedHtmlClassReturnedCorrectlyAsString() {
        String[] expectedArray = {"yo who", "https://www.w3schools.com/"};
        ArrayList<String> actualArray = sc.getContentFromClassAsString("testClass");

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void ClassContentFromUserRequestedHtmlClassReturnedCorrectlyAsNode() {
        String[] expectedArray = {"yo who", "https://www.w3schools.com/"};
        ArrayList<Element> actualArray = sc.getContentFromClassAsNode("testClass");

        for(int i = 0; i < actualArray.size(); i++)
            assertEquals(expectedArray[i], actualArray.get(i).getStringChildren().get(0));
    }

    @Test
    public void returnsLinksFromSiteCorrectlyAsString() {
        String[] expectedArray = {"https://www.w3schools.com/", "https://www.test.com/"};
        ArrayList<String> actualArray = sc.getLinksInPageAsString();

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void returnsLinksFromSiteCorrectlyAsNode() {
        String[] expectedArray = {"https://www.w3schools.com/", "https://www.test.com/"};
        ArrayList<Element> nodeArray = sc.getLinksInPageAsNode();
        ArrayList<String> actualArray = new ArrayList<>();

        for (Element element : nodeArray)
            actualArray.add(element.getAttributes().get("href"));

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
    public void returnsTrueIfSiteContainsStringFromUserAsString() {
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
    public void getsAllImageLinksFromSiteAsString() {
        String[] expectedArray = {"img_girl.jpg", "www.google.com/hjelp/img_boy.jpg"};
        ArrayList<String> actualArray = sc.getAllImagesFromPageAsString();

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void getsAllImageLinksFromSiteAsNode() {
        String[] expectedArray = {"img_girl.jpg", "www.google.com/hjelp/img_boy.jpg"};
        ArrayList<Element> nodeArray = sc.getAllImagesFromPageAsNode();
        ArrayList<String> actualArray = new ArrayList<>();

        for (Element element : nodeArray)
            actualArray.add(element.getAttributes().get("src"));

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void getsSpecificImageByIdFromSiteAsString() {
        String expected = "img_girl.jpg";
        String actual = sc.getImageByIdAsString("bestebildet");

        assertEquals(expected, actual);
    }

    @Test
    public void getSpecificImageByIdFromSiteAsNode() {
        String expectedImage = "img_girl.jpg";
        Element actualNode = sc.getImageByIdAsNode("bestebildet");

        assertEquals(expectedImage, actualNode.getAttributes().get("src"));
    }

    @Test
    public void getsSpecificImagesByClassNameFromSiteAsString() {
        String[] expectedArray = {"img_girl.jpg", "www.google.com/hjelp/img_boy.jpg"};
        ArrayList<String> actualArray = sc.getImageByClassAsString("bestebildene");

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void getsSpecificImagesByClassNameFromSiteAsNode() {
        String[] expectedArray = {"img_girl.jpg", "www.google.com/hjelp/img_boy.jpg"};
        ArrayList<Element> nodeArray = sc.getImageByClassAsNode("bestebildene");
        ArrayList<String> actualArray = new ArrayList<>();

        for (Element element : nodeArray)
            actualArray.add(element.getAttributes().get("src"));

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void getsAllVideosFromSiteCorrectlyAsString() {
        String[] expectedArray = {"video_girl.mp4", "www.google.com/hjelp/video_boy.wma"};
        ArrayList<String> actualArray = sc.getAllVideosFromPageAsString();

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void getsAllVideosFromSiteCorrectlyAsNode() {
        String[] expectedArray = {"video_girl.mp4", "www.google.com/hjelp/video_boy.wma"};
        ArrayList<Element> nodeArray = sc.getAllVideosFromPageAsNode();
        ArrayList<String> actualArray = new ArrayList<>();

        for (Element element : nodeArray)
            actualArray.add(element.getAttributes().get("src"));

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void getsSpecificVideoByIdFromSiteAsString() {
        String expected = "video_girl.mp4";
        String actual = sc.getVideoByIdAsString("bestevideoen");

        assertEquals(expected, actual);
    }

    @Test
    public void getsSpecificVideoByIdFromSiteAsNode() {
        String expected = "video_girl.mp4";
        Element actualNode = sc.getVideoByIdAsNode("bestevideoen");

        assertEquals(expected, actualNode.getAttributes().get("src"));
    }

    @Test
    public void getsSpecificVideosByClassNameFromSiteAsString() {
        String[] expectedArray = {"video_girl.mp4"};
        ArrayList<String> actualArray = sc.getVideoByClassAsString("bestevideoene");

        assertArrayEquals(expectedArray, actualArray.toArray());
    }

    @Test
    public void getsSpecificVideosByClassNameFromSiteAsNode() {
        String[] expectedArray = {"video_girl.mp4"};
        ArrayList<Element> nodeArray = sc.getVideoByClassAsNode("bestevideoene");
        ArrayList<String> actualArray = new ArrayList<>();

        for (Element element : nodeArray)
            actualArray.add(element.getAttributes().get("src"));

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
