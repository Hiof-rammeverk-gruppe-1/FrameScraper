import java.io.IOException;

/**
 * ConceptScraper is a abstract class to let the predefined
 * classes use the methods from Scraper with correct inheriting.
 * @author Mathias Jarbekk
 * @author Thomas Johannessen
 * @author Joakim Jensen
 * @author Michal Kowalski
 * @version 1.0
 */

public abstract class ConceptScraper {

    private Scraper scraper;

    /**
     * Constructor for ConceptScraper, which is used by the predefined classes to access the Scraper
     * @param URL String with the URL. This is forwarded to the Scraper
     *           and instantiates a new object from the webpage.
     * @throws IOException Throws if the string sent in could not be processed as intended.
     */
    public ConceptScraper(String URL) throws IOException {
        scraper = new Scraper(URL);
    }

    /**
     * Used to access the Scraper, and apply the Scrapers methods to the objects from ShopScraper, StockScraper
     * and for user instantiated objects.
     * @return A object of the Scraper class which all the Scrapers methods can be applied to.
     */
    public Scraper getScraper(){
        return scraper;
    }
}
