import java.io.IOException;

public abstract class ConceptScraper {
    private Scraper scraper;

    public ConceptScraper(String URL) throws IOException {
        scraper = new Scraper(URL);
    }
    
    public Scraper getScraper(){
        return scraper;
    }
}
