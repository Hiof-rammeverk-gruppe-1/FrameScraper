package Scraper.Exceptions;

import Scraper.Element;

public class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Element element){
        super(message + " building element is " + element);
    }
}
