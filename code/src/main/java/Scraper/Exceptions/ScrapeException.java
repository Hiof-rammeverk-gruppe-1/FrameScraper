package Scraper.Exceptions;

public class ScrapeException extends Exception {
        public ScrapeException(String message) {
            super(message);
        }

        public ScrapeException(){
            super("The item you were looking for could not be found.");
        }
    }
