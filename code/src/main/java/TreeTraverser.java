import java.util.ArrayList;

public class TreeTraverser {
    private static ArrayList<String> tagArray = new ArrayList<String>();
    private static ArrayList<String> idArray = new ArrayList<String>();
    private static ArrayList<String> classArray = new ArrayList<String>();
    private static ArrayList<String> linksArray = new ArrayList<String>();
    private static boolean containsVar = false;


    public static void traversingGetContentFromTag(SoupNode node, String tag){

        if (node.getTag().equals(tag)){
            for(int i = 0; i < node.getStringChildren().size(); i++) {
                tagArray.add(node.getStringChildren().get(i));
            }
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetContentFromTag(node.getNodeChildren().get(i),tag);
        }
    }

    public static String[] getTagArray() {
        return  tagArray.toArray(new String[0]);
    }

    public static void traversingGetContentFromId(SoupNode node, String id) {

        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(id)) {
                for(int i = 0; i < node.getStringChildren().size(); i++) {
                    idArray.add(node.getStringChildren().get(i));
                }
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetContentFromId(node.getNodeChildren().get(i), id);
        }
    }

    public static ArrayList<String> getIdArray() {
        return idArray;
    }

    public static void traversingGetContentFromClass(SoupNode node, String className) {
        if (node.getAttributeNames().contains("class")){
            if (node.getAttributes().get("class").equals(className)) {
                for(int i = 0; i < node.getStringChildren().size(); i++) {
                    classArray.add(node.getStringChildren().get(i));
                }
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetContentFromClass(node.getNodeChildren().get(i), className);
        }
    }

    public static ArrayList<String> getClassArray() {
        return classArray;
    }

    public static void traversingGetLinksInPage(SoupNode node){
        String tag = "a";

        if (node.getTag().equals(tag)){
            linksArray.add(node.getAttributes().get("href"));
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetLinksInPage(node.getNodeChildren().get(i));
        }
    }

    public static ArrayList<String> getLinksArray() {
        return linksArray;
    }

    public static Boolean traversingContains(SoupNode node, String searchString) {

        for (int j = 0; j < node.getStringChildren().size(); j++){
            if (node.getStringChildren().get(j).contains(searchString)) {
                containsVar = true;
                break;
            }
        }

        if (!containsVar)
            for (int i = 0; i < node.getNodeChildren().size(); i++) {
                traversingContains(node.getNodeChildren().get(i), searchString);
            }
        return containsVar;
    }

    public static Boolean traversingContainsCaseInSensetive(SoupNode node, String searchString) {

        String SS = searchString.toLowerCase();

        for (int j = 0; j < node.getStringChildren().size(); j++){
            if (node.getStringChildren().get(j).toLowerCase().contains(SS)) {
                containsVar = true;
                break;
            }
        }

        if (!containsVar)
            for (int i = 0; i < node.getNodeChildren().size(); i++) {
                traversingContainsCaseInSensetive(node.getNodeChildren().get(i), searchString);
            }
        return containsVar;
    }
}
