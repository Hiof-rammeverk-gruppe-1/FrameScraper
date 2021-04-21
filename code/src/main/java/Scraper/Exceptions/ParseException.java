package Scraper.Exceptions;

import Scraper.SoupNode;

public class ParseException extends Exception {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, SoupNode node){
        super(message + " building node is " + node);
    }
}
