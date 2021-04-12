import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

public class Main {
    public static void main(String[] args) throws IOException {
        Scraper sr = new Scraper("https://www.multicom.no/");
        System.out.println(sr.getRoot());

        //System.out.println("getContentFromTag: "+ Arrays.toString(sr.getContentFromTag("h1")));
        //System.out.println("getContentFromId: "+ sr.getContentFromId("header1"));
        //System.out.println("getContentFromClass: "+ sr.getContentFromClass("class1"));
        //System.out.println("getLinksInPage: "+ sr.getLinksInPage());
        //System.out.println("contains: " + sr.contains("hild"));
        //System.out.println("contains CaseInSensetive: " + sr.containsCaseSensetive("Child"));


    }
/*<body id="hi" class="class1">
    Hei jeg hedder dorte
    <p lang="no" id="para">
    yo who
    <p>
            yo mama
            </p>
    </p>
    og min mor er borte
            <h1 id="header1">
            child of child
    </h1>
</body>*/
}



