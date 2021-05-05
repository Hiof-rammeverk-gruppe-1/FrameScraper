package Scraper;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
/**
 * Element: an object representing a individual element from the html source.
 * The object contains information of what content is nested within it: other html-elements or text.
 * The object also hold information about the element itself, being what tag it is and what attributes it hold, including values to the attributes. This information is used in particular when searching after elements
 * @author Joakim Jensen
 * @version 1.0
 * @see Scraper
 */
public final class Element {
    private String tag;
//    private String full_tag;


    private Dictionary<String, String> attributes = new Hashtable();

    private ArrayList<String> attributeNames = new ArrayList<>();

    private ArrayList<Element> elementChildren = new ArrayList<>();
    private ArrayList<String> stringChildren = new ArrayList<>();

    public Element() {
    }

    public Element(String tag) {
        this.tag = tag;
    }

    public void addElementChild(Element child){
        this.elementChildren.add(child);
    }

    public void addStringChild(String text){
        this.getStringChildren().add(text);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Dictionary<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Dictionary<String, String> attributes) {
        this.attributes = attributes;
    }


    public ArrayList<Element> getElementChildren() {
        return elementChildren;
    }

    public void setElementChildren(ArrayList<Element> elementChildren) {
        this.elementChildren = elementChildren;
    }

    public ArrayList<String> getStringChildren() {
        return stringChildren;
    }

    public void setStringChildren(ArrayList<String> stringChildren) {
        this.stringChildren = stringChildren;
    }

    public ArrayList<String> getAttributeNames() {
        return attributeNames;
    }


    @Override
    public String toString() {
        return "Scraper.Element{" +
                "tag='" + tag + '\'' +
                ", attributes=" + attributes +
                ", attributeNames=" + attributeNames +
                ", elementChildren=" + elementChildren +
                ", stringChildren=" + stringChildren +
                '}';
    }
}
