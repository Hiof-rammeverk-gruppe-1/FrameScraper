import Scraper.Scraper;

import java.io.IOException;


// denne klassen kommer ikke til å være med i endelige innlevering
public class Main {
    public static void main(String[] args) throws IOException, Exception {
        Scraper sr = new Scraper("https://webscraper.io/test-sites/e-commerce/allinone");

        sr.printBeautyfull();

    }
}



