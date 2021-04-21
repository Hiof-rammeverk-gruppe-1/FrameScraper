package Scraper;


import java.util.ArrayList;

public class TreeTraverser {
    private static ArrayList<String> tagArray = new ArrayList<String>();
    private static ArrayList<String> idArray = new ArrayList<String>();
    private static ArrayList<String> classArray = new ArrayList<String>();
    private static ArrayList<String> linksArray = new ArrayList<String>();
    private static ArrayList<String> ImgArray = new ArrayList<String>();
    private static ArrayList<String> ImgClassArray = new ArrayList<String>();
    private static ArrayList<String> VideoArray = new ArrayList<String>();
    private static ArrayList<String> VideoClassArray = new ArrayList<String>();
    private static ArrayList<String> AllIdArray = new ArrayList<String>();
    private static ArrayList<String> AllClassArray = new ArrayList<String>();
    private static boolean containsVar = false;
    private static String src;


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

    public static ArrayList<String> getTagArray() {
        return tagArray;
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

    public static void traversingGetAllImagesFromPage(SoupNode node) {
        String tag = "img";

        if (node.getTag().equals(tag)){
            ImgArray.add(node.getAttributes().get("src"));
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetAllImagesFromPage(node.getNodeChildren().get(i));
        }
    }

    public static ArrayList<String> getImgArray() {
        return ImgArray;
    }

    public static String traversingGetImageById(SoupNode node, String pictureId) {

        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(pictureId)) {
                src = node.getAttributes().get("src");
                }
            }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetImageById(node.getNodeChildren().get(i), pictureId);
        }
        return src;
    }

    public static void traversingGetImageByClass(SoupNode node, String pictureClass) {

        if (node.getAttributeNames().contains("class")){
            if (node.getAttributes().get("class").equals(pictureClass)) {
                ImgClassArray.add(node.getAttributes().get("src"));
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetImageByClass(node.getNodeChildren().get(i), pictureClass);
        }
    }

    public static ArrayList<String> getImgClassArray() {
        return ImgClassArray;
    }

    public static void traversingGetAllVideosFromPage(SoupNode node) {
        String tag = "video";

        if (node.getTag().equals(tag)){
            VideoArray.add(node.getAttributes().get("src"));
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetAllVideosFromPage(node.getNodeChildren().get(i));
        }
    }

    public static ArrayList<String> getVideoArray() {
        return VideoArray;
    }

    public static String traversingGetVideoById(SoupNode node, String videoId) {
        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(videoId)) {
                src = node.getAttributes().get("src");
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetVideoById(node.getNodeChildren().get(i), videoId);
        }
        return src;
    }

    public static void traversingGetVideoByClass(SoupNode node, String videoClass) {
        if (node.getAttributeNames().contains("class")){
            if (node.getAttributes().get("class").equals(videoClass)) {
                VideoClassArray.add(node.getAttributes().get("src"));
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetVideoByClass(node.getNodeChildren().get(i), videoClass);
        }
    }

    public static ArrayList<String> getVideoClassArray() {
        return VideoClassArray;
    }

    public static void traversingGetClassesInPage(SoupNode node) {
        if (node.getAttributeNames().contains("class")){
            if (!AllClassArray.contains(node.getAttributes().get("class"))) {
                AllClassArray.add(node.getAttributes().get("class"));
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetClassesInPage(node.getNodeChildren().get(i));
        }
    }

    public static ArrayList<String> getAllClassArray() {
        return AllClassArray;
    }

    public static void traversingGetIdsInPage(SoupNode node) {
        if (node.getAttributeNames().contains("id")){
            if (!AllIdArray.contains(node.getAttributes().get("id"))) {
                AllIdArray.add(node.getAttributes().get("id"));
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetIdsInPage(node.getNodeChildren().get(i));
        }
    }

    public static ArrayList<String> getAllIdsArray() {
        return AllIdArray;
    }
}
