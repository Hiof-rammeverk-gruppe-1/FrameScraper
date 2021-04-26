import HTMLString.HTMLString;
import Scraper.Exceptions.ParseException;
import Scraper.Scraper;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
//        Scraper sc = Scraper.buildScraperWithWebUrl("https://webscraper.io/test-sites/e-commerce/allinone");

        Scraper sc = Scraper.buildScraperWithFile("DummyHTMLForScraper.html");

        sc.printBeautyfull();

    }
}
