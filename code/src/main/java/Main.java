import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Main {
    public static void main(String[] args) throws IOException, Exception {
        Scraper sr = new Scraper("https://www.multicom.no/");

        System.out.println(sr.getRoot());

        System.out.println();
        sr.printBeautyfull();




    }
}
