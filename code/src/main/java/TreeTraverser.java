import java.util.ArrayList;

public class TreeTraverser {
    private static ArrayList<String> tagArray = new ArrayList<String>();
    private static ArrayList<String> idArray = new ArrayList<String>();
    private static ArrayList<String> classArray = new ArrayList<String>();


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
}
