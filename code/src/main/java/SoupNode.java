import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class SoupNode {
    private String tag;
    private String full_tag;

    private Dictionary<String, Object> attributes = new Hashtable();

    private SoupNode parent;
    private ArrayList<SoupNode> nodeChildren = new ArrayList<>();
    private ArrayList<String> stringChildren = new ArrayList<>();

    public SoupNode(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFull_tag() {
        return full_tag;
    }

    public void setFull_tag(String full_tag) {
        this.full_tag = full_tag;
    }

    public Dictionary<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Dictionary<String, Object> attributes) {
        this.attributes = attributes;
    }

    public SoupNode getParent() {
        return parent;
    }

    public void setParent(SoupNode parent) {
        this.parent = parent;
    }

    public ArrayList<SoupNode> getNodeChildren() {
        return nodeChildren;
    }

    public void setNodeChildren(ArrayList<SoupNode> nodeChildren) {
        this.nodeChildren = nodeChildren;
    }

    public ArrayList<String> getStringChildren() {
        return stringChildren;
    }

    public void setStringChildren(ArrayList<String> stringChildren) {
        this.stringChildren = stringChildren;
    }
}
