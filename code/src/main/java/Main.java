import Scraper.Exceptions.ParseException;
import Scraper.Scraper;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Scraper sc = Scraper.buildScraperWithWebUrl("https://webscraper.io/test-sites/e-commerce/allinone/phones");

//        Scraper sc = Scraper.buildScraperWithFile("DummyHTMLForScraper.html");


//        Scraper sc = Scraper.buildScraperWithString("<html lang=\"en\"><body><h1 id=\"header1\">Hello world</h1><p>This is my world</p></body></html>");

        sc.printBeautyfull();

    }
}
