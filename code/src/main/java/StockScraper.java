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

public class StockScraper extends ConceptScraper {

    /**
     * Constructor for a Stock scraper
     * @param url Link to target site for scraping
     * @throws IOException Throws an IO Exception whenever user input is crashing with an expected string value
     */
    public StockScraper(String url) throws IOException {
        super(url);
    }

    /**
     * Searches the HTML code of site sent as constructor parameter and returns information about the Stock
     * @param stockTicker Short (often 3 letter) code used to identify Stocks on the international market
     * @return A string array containing stock title, stock code, price,
     * @see StockScraper#StockScraper(String url)
     */
    public String[] getStockInfo(String stockTicker){
        return null;
    }
    /**
     * Searches the HTML code of site sent as constructor parameter and returns information about the Stock
     * @param stockPrice The price of the stock at the time of purchase
     * @param numberOfStocks The number of stocks you wish to gague the return
     * @return Double value of the change in stock price of the given stock
     * @see StockScraper#StockScraper(String url)
     */
    public Double getStockReturn(String stockTicker, int stockPrice, int numberOfStocks){
        return null;
    }

    /**
     * Searches the HTML code of site sent as constructor parameter and returns information about the Stock
     * @return Double value of the precentile change in stock price of the given stock
     * @see StockScraper#StockScraper(String url)
     */
    public Double getStockReturnPrcent(String stockTicker, int stockPrice, int numberOfStocks){
        return null;
    }


}