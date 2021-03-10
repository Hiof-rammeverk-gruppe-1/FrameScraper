import java.io.IOException;
/**
 * Shopscraper class representing a specific instantiation of a ConceptScraper
 * @author Mathias Jarbekk
 * @author Thomas Johannessen
 * @author Joakim Jensen
 * @author Michal Kowalski
 * @version 1.0
 * @see ConceptScraper
 */
public class ShopScraper extends ConceptScraper{

    /**
     * Constructor for a shop scraper
     * @param url Link to target site for scraping
     * @throws IOException Throws an IO Exception whenever user input is crashing with an expected string value
     */
    public ShopScraper(String url) throws IOException {
        super(url);
    }

    /**
     * Searches the HTML code of site sent as constructor parameter and returns information about the product
     * @return A string array containing product title, itemcode, price, stock status, feedback score and product description
     * @see ShopScraper#ShopScraper(String url)
     */
    public String[] getProductInfo() {
        return null;
    }

    /**
     * Searches a category given as url parameter to constructor, returning every product in the given category
     * @return Double dimensional string array, containing different products as the first index, and product name,
     * itemcode, price, stock status and product description as the second index respectively.
     * @see ShopScraper#ShopScraper(String url)
     */
    public String[][] getCategoryProductInfo() {
        return null;
    }

    /**
     * Searches the HTML code of a site sent as constructor parameter and returns information about the product specifications.
     * @return A string array containing every single product specification found
     * @see ShopScraper#ShopScraper(String url)
     */
    public String[] getProductSpecifications() {
        return null;
    }

    /**
     * Searches the HTML code of a site sent as constructor parameter and returns product specification for a certain specification category
     * @param spec A string containing the name of the specification for a given product
     * @return A string array containing every product specification for a given specification category
     * @see ShopScraper#ShopScraper(String url)
     */
    public String[] getProductSpecification(String spec) {
        return null;
    }

    /**
     * Searches the HTML code of a site sent as constructor parameter and returns the average feedback grade
     * @return A double containing the average feedback grade
     * @see ShopScraper#ShopScraper(String url)
     */
    public Double getProductAverageRating() {
        return null;
    }

    /**
     * Searches the HTML code of a site sent as constructor parameter and returns every feedback comment given for the product
     * @return A string array containing every comment found on the page
     * @see ShopScraper#ShopScraper(String url)
     */
    public String[] getAProductsFeedbackComments() {
        return null;
    }
}
