package Scraper;


import java.util.ArrayList;

public class TreeTraverser {
    private static ArrayList<String> tagStringArray = new ArrayList<String>();
    private static ArrayList<String> idStringArray = new ArrayList<String>();
    private static ArrayList<String> classStringArray = new ArrayList<String>();
    private static ArrayList<String> linksStringArray = new ArrayList<String>();
    private static ArrayList<String> ImgStringArray = new ArrayList<String>();
    private static ArrayList<String> ImgClassStringArray = new ArrayList<String>();
    private static ArrayList<String> VideoStringArray = new ArrayList<String>();
    private static ArrayList<String> VideoClassStringArray = new ArrayList<String>();
    private static ArrayList<String> AllIdArray = new ArrayList<String>();
    private static ArrayList<String> AllClassArray = new ArrayList<String>();
    private static ArrayList<String> AttributeContentStringArray = new ArrayList<>();
    private static ArrayList<SoupNode> tagNodeArray = new ArrayList<>();
    private static ArrayList<SoupNode> idNodeArray = new ArrayList<>();
    private static ArrayList<SoupNode> classNodeArray = new ArrayList<>();
    private static ArrayList<SoupNode> linksNodeArray = new ArrayList<>();
    private static ArrayList<SoupNode> ImgNodeArray = new ArrayList<>();
    private static ArrayList<SoupNode> ImgClassNodeArray = new ArrayList<>();
    private static ArrayList<SoupNode> VideoNodeArray = new ArrayList<>();
    private static ArrayList<SoupNode> VideoClassNodeArray = new ArrayList<>();
    private static ArrayList<SoupNode> ContainsNodeArray = new ArrayList<>();
    private static ArrayList<SoupNode> ContainsCaseInSensitiveNodeArray = new ArrayList<>();
    private static ArrayList<SoupNode> AttributeContentNodeArray = new ArrayList<>();
    private static boolean containsVar = false;
    private static String srcString;
    private static SoupNode srcNode;


    public static void traversingGetContentFromTagAsString(SoupNode node, String tag){

        if (node.getTag().equals(tag)){
            for(int i = 0; i < node.getStringChildren().size(); i++) {
                tagStringArray.add(node.getStringChildren().get(i));
            }
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetContentFromTagAsString(node.getNodeChildren().get(i),tag);
        }
    }

    public static ArrayList<String> getTagStringArray() {
        return tagStringArray;
    }

    public static void traversingGetContentFromIdAsString(SoupNode node, String id) {

        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(id)) {
                for(int i = 0; i < node.getStringChildren().size(); i++) {
                    idStringArray.add(node.getStringChildren().get(i));
                }
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetContentFromIdAsString(node.getNodeChildren().get(i), id);
        }
    }

    public static ArrayList<String> getIdStringArray() {
        return idStringArray;
    }

    public static void traversingGetContentFromClassAsString(SoupNode node, String className) {
        if (node.getAttributeNames().contains("class")){
            if (node.getAttributes().get("class").equals(className)) {
                for(int i = 0; i < node.getStringChildren().size(); i++) {
                    classStringArray.add(node.getStringChildren().get(i));
                }
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetContentFromClassAsString(node.getNodeChildren().get(i), className);
        }
    }

    public static ArrayList<String> getClassStringArray() {
        return classStringArray;
    }

    public static void traversingGetLinksInPageAsString(SoupNode node){
        String tag = "a";

        if (node.getTag().equals(tag)){
            linksStringArray.add(node.getAttributes().get("href"));
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetLinksInPageAsString(node.getNodeChildren().get(i));
        }
    }

    public static ArrayList<String> getLinksStringArray() {
        return linksStringArray;
    }

    public static Boolean traversingContainsAsBoolean(SoupNode node, String searchString) {

        for (int j = 0; j < node.getStringChildren().size(); j++){
            if (node.getStringChildren().get(j).contains(searchString)) {
                containsVar = true;
                break;
            }
        }

        if (!containsVar)
            for (int i = 0; i < node.getNodeChildren().size(); i++) {
                traversingContainsAsBoolean(node.getNodeChildren().get(i), searchString);
            }
        return containsVar;
    }

    public static Boolean traversingContainsCaseInSensetiveAsBoolean(SoupNode node, String searchString) {

        String SS = searchString.toLowerCase();

        for (int j = 0; j < node.getStringChildren().size(); j++){
            if (node.getStringChildren().get(j).toLowerCase().contains(SS)) {
                containsVar = true;
                break;
            }
        }

        if (!containsVar)
            for (int i = 0; i < node.getNodeChildren().size(); i++) {
                traversingContainsCaseInSensetiveAsBoolean(node.getNodeChildren().get(i), searchString);
            }
        return containsVar;
    }

    public static void traversingGetAllImagesFromPageAsString(SoupNode node) {
        String tag = "img";

        if (node.getTag().equals(tag)){
            ImgStringArray.add(node.getAttributes().get("src"));
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetAllImagesFromPageAsString(node.getNodeChildren().get(i));
        }
    }

    public static ArrayList<String> getImgStringArray() {
        return ImgStringArray;
    }

    public static String traversingGetImageByIdAsString(SoupNode node, String pictureId) {

        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(pictureId)) {
                srcString = node.getAttributes().get("src");
                }
            }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetImageByIdAsString(node.getNodeChildren().get(i), pictureId);
        }
        return srcString;
    }

    public static void traversingGetImageByClassAsString(SoupNode node, String pictureClass) {

        if (node.getAttributeNames().contains("class")){
            if (node.getAttributes().get("class").equals(pictureClass)) {
                ImgClassStringArray.add(node.getAttributes().get("src"));
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetImageByClassAsString(node.getNodeChildren().get(i), pictureClass);
        }
    }

    public static ArrayList<String> getImgClassStringArray() {
        return ImgClassStringArray;
    }

    public static void traversingGetAllVideosFromPageAsString(SoupNode node) {
        String tag = "video";

        if (node.getTag().equals(tag)){
            VideoStringArray.add(node.getAttributes().get("src"));
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetAllVideosFromPageAsString(node.getNodeChildren().get(i));
        }
    }

    public static ArrayList<String> getVideoStringArray() {
        return VideoStringArray;
    }

    public static String traversingGetVideoByIdAsString(SoupNode node, String videoId) {
        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(videoId)) {
                srcString = node.getAttributes().get("src");
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetVideoByIdAsString(node.getNodeChildren().get(i), videoId);
        }
        return srcString;
    }

    public static void traversingGetVideoByClassAsString(SoupNode node, String videoClass) {
        if (node.getAttributeNames().contains("class")){
            if (node.getAttributes().get("class").equals(videoClass)) {
                VideoClassStringArray.add(node.getAttributes().get("src"));
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetVideoByClassAsString(node.getNodeChildren().get(i), videoClass);
        }
    }

    public static ArrayList<String> getVideoClassStringArray() {
        return VideoClassStringArray;
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


    public static void traversingGetContentFromTagAsNode(SoupNode node, String tag){

        if (node.getTag().equals(tag)){
            for(int i = 0; i < node.getStringChildren().size(); i++) {
                tagNodeArray.add(node);
            }
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetContentFromTagAsNode(node.getNodeChildren().get(i),tag);
        }
    }

    public static ArrayList<SoupNode> getTagNodeArray() {
        return tagNodeArray;
    }

    public static void traversingGetContentFromIdAsNode(SoupNode node, String id) {

        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(id)) {
                for(int i = 0; i < node.getStringChildren().size(); i++) {
                    idNodeArray.add(node);
                }
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetContentFromIdAsNode(node.getNodeChildren().get(i), id);
        }
    }

    public static ArrayList<SoupNode> getIdNodeArray() {
        return idNodeArray;
    }

    public static void traversingGetContentFromClassAsNode(SoupNode node, String className) {
        if (node.getAttributeNames().contains("class")){
            if (node.getAttributes().get("class").equals(className)) {
                for(int i = 0; i < node.getStringChildren().size(); i++) {
                    classNodeArray.add(node);
                }
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetContentFromClassAsNode(node.getNodeChildren().get(i), className);
        }
    }

    public static ArrayList<SoupNode> getClassNodeArray() {
        return classNodeArray;
    }

    public static void traversingGetLinksInPageAsNode(SoupNode node){
        String tag = "a";

        if (node.getTag().equals(tag)){
            linksNodeArray.add(node);
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetLinksInPageAsNode(node.getNodeChildren().get(i));
        }
    }

    public static ArrayList<SoupNode> getLinksNodeArray() {
        return linksNodeArray;
    }

    public static void traversingContainsAsNode(SoupNode node, String searchString) {

        for (int j = 0; j < node.getStringChildren().size(); j++){
            if (node.getStringChildren().get(j).contains(searchString)) {
                ContainsNodeArray.add(node);
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingContainsAsNode(node.getNodeChildren().get(i), searchString);
        }
    }

    public static ArrayList<SoupNode> getContainsNodeArray() {
        return ContainsNodeArray;
    }

    public static void traversingContainsCaseInSensetiveAsNode(SoupNode node, String searchString) {

        String SS = searchString.toLowerCase();

        for (int j = 0; j < node.getStringChildren().size(); j++){
            if (node.getStringChildren().get(j).toLowerCase().contains(SS)) {
                ContainsCaseInSensitiveNodeArray.add(node);
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingContainsCaseInSensetiveAsNode(node.getNodeChildren().get(i), searchString);
        }
    }

    public static ArrayList<SoupNode> getContainsCaseInSensitiveNodeArray() {
        return ContainsCaseInSensitiveNodeArray;
    }

    public static void traversingGetAllImagesFromPageAsNode(SoupNode node) {
        String tag = "img";

        if (node.getTag().equals(tag)){
            ImgNodeArray.add(node);
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetAllImagesFromPageAsNode(node.getNodeChildren().get(i));
        }
    }

    public static ArrayList<SoupNode> getImgNodeArray() {
        return ImgNodeArray;
    }

    public static SoupNode traversingGetImageByIdAsNode(SoupNode node, String pictureId) {

        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(pictureId)) {
                srcNode = node;
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetImageByIdAsNode(node.getNodeChildren().get(i), pictureId);
        }
        return srcNode;
    }

    public static void traversingGetImageByClassAsNode(SoupNode node, String pictureClass) {

        if (node.getAttributeNames().contains("class")){
            if (node.getAttributes().get("class").equals(pictureClass)) {
                ImgClassNodeArray.add(node);
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetImageByClassAsNode(node.getNodeChildren().get(i), pictureClass);
        }
    }

    public static ArrayList<SoupNode> getImgClassNodeArray() {
        return ImgClassNodeArray;
    }

    public static void traversingGetAllVideosFromPageAsNode(SoupNode node) {
        String tag = "video";

        if (node.getTag().equals(tag)){
            VideoNodeArray.add(node);
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetAllVideosFromPageAsNode(node.getNodeChildren().get(i));
        }
    }

    public static ArrayList<SoupNode> getVideoNodeArray() {
        return VideoNodeArray;
    }

    public static SoupNode traversingGetVideoByIdAsNode(SoupNode node, String videoId) {
        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(videoId)) {
                srcNode = node;
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetVideoByIdAsNode(node.getNodeChildren().get(i), videoId);
        }
        return srcNode;
    }

    public static void traversingGetVideoByClassAsNode(SoupNode node, String videoClass) {
        if (node.getAttributeNames().contains("class")){
            if (node.getAttributes().get("class").equals(videoClass)) {
                VideoClassNodeArray.add(node);
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetVideoByClassAsNode(node.getNodeChildren().get(i), videoClass);
        }
    }

    public static ArrayList<SoupNode> getVideoClassNodeArray() {
        return VideoClassNodeArray;
    }

    public static void traversingGetAttributeContentWithTagAndNameAsNode(SoupNode node, String tag, String attribute) {

        if (node.getTag().contains(tag)){
            if (node.getAttributeNames().contains(attribute)) {
                AttributeContentNodeArray.add(node);
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetAttributeContentWithTagAndNameAsNode(node.getNodeChildren().get(i), tag, attribute);
        }
    }

    public static ArrayList<SoupNode> getAttributeContentNodeArray() {
        return AttributeContentNodeArray;
    }

    public static void traversingGetAttributeContentWithTagAndNameAsString(SoupNode node, String tag, String attribute) {
        if (node.getTag().contains(tag)){
            if (node.getAttributeNames().contains(attribute)) {
                AttributeContentStringArray.add(node.getAttributes().get(attribute));
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetAttributeContentWithTagAndNameAsString(node.getNodeChildren().get(i), tag, attribute);
        }
    }

    public static ArrayList<String> getAttributeContentStringArray() {
        return AttributeContentStringArray;
    }
}
