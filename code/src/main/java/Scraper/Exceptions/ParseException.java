package Scraper.Exceptions;

import Scraper.Element;

public class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Element node){
        super(message + " building node is " + node);
    }
}
