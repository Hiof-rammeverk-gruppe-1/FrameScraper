package Scraper;


import java.util.ArrayList;

public final class TreeTraverser {
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
    private static ArrayList<Element> tagNodeArray = new ArrayList<>();
    private static ArrayList<Element> idNodeArray = new ArrayList<>();
    private static ArrayList<Element> classNodeArray = new ArrayList<>();
    private static ArrayList<Element> linksNodeArray = new ArrayList<>();
    private static ArrayList<Element> ImgNodeArray = new ArrayList<>();
    private static ArrayList<Element> ImgClassNodeArray = new ArrayList<>();
    private static ArrayList<Element> VideoNodeArray = new ArrayList<>();
    private static ArrayList<Element> VideoClassNodeArray = new ArrayList<>();
    private static ArrayList<Element> ContainsNodeArray = new ArrayList<>();
    private static ArrayList<Element> ContainsCaseInSensitiveNodeArray = new ArrayList<>();
    private static ArrayList<Element> AttributeContentNodeArray = new ArrayList<>();
    private static boolean containsVar = false;
    private static String srcString;
    private static Element srcNode;
    private static ArrayList<String> AttributeIdContentStringArray = new ArrayList<>();
    private static String returnSrcString = "";
    private static String returnImgSrcString = "";



    public static void traversingGetContentFromTagAsString(Element node, String tag){

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
        ArrayList<String> arr = new ArrayList<>(tagStringArray);
        tagStringArray.clear();
        return arr;
    }

    public static void traversingGetContentFromIdAsString(Element node, String id) {

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
        ArrayList<String> arr = new ArrayList<>(idStringArray);
        idStringArray.clear();
        return arr;
    }

    public static void traversingGetContentFromClassAsString(Element node, String className) {
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
        ArrayList<String> arr = new ArrayList<>(classStringArray);
        classStringArray.clear();
        return arr;
    }


    public static void traversingGetLinksInPageAsString(Element node, String url){

        String tag = "a";

        if (node.getTag().equals(tag)){
            if (node.getAttributeNames().contains("href")) {
                char start = node.getAttributes().get("href").charAt(0);
                if (start == '/' || start == '#')
                    linksStringArray.add(url + node.getAttributes().get("href"));
                else
                    linksStringArray.add(node.getAttributes().get("href"));
            }
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetLinksInPageAsString(node.getNodeChildren().get(i), url);
        }
    }

    public static ArrayList<String> getLinksStringArray() {
        ArrayList<String> arr = new ArrayList<>(linksStringArray);
        linksStringArray.clear();
        return arr;
    }

    public static Boolean traversingContainsAsBoolean(Element node, String searchString) {

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
        Boolean returnContainsVar = containsVar;
        containsVar = false;
        return returnContainsVar;
    }

    public static Boolean traversingContainsCaseInSensetiveAsBoolean(Element node, String searchString) {

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

        Boolean returnContainsVar = containsVar;
        containsVar = false;
        return returnContainsVar;
    }

    public static void traversingGetAllImagesFromPageAsString(Element node) {
        String tag = "img";

        if (node.getTag().equals(tag)){
            ImgStringArray.add(node.getAttributes().get("src"));
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetAllImagesFromPageAsString(node.getNodeChildren().get(i));
        }
    }

    public static ArrayList<String> getImgStringArray() {
        ArrayList<String> arr = new ArrayList<>(ImgStringArray);
        ImgStringArray.clear();
        return arr;
    }

    public static String traversingGetImageByIdAsString(Element node, String pictureId) {

        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(pictureId)) {
                srcString = node.getAttributes().get("src");

                returnImgSrcString = srcString;
                srcString = "";
                return returnImgSrcString;
                }
            }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetImageByIdAsString(node.getNodeChildren().get(i), pictureId);
        }

        return returnImgSrcString;
    }

    public static void traversingGetImageByClassAsString(Element node, String pictureClass) {

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
        ArrayList<String> arr = new ArrayList<>(ImgClassStringArray);
        ImgClassStringArray.clear();
        return arr;
    }

    public static void traversingGetAllVideosFromPageAsString(Element node) {
        String tag = "video";

        if (node.getTag().equals(tag)){
            if (!node.getNodeChildren().isEmpty()){
                if (node.getNodeChildren().get(0).getTag().equals("source")){
                    VideoStringArray.add(node.getNodeChildren().get(0).getAttributes().get("src"));
                }
            }
            else
                VideoStringArray.add(node.getAttributes().get("src"));
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetAllVideosFromPageAsString(node.getNodeChildren().get(i));
        }
    }

    public static ArrayList<String> getVideoStringArray() {
        ArrayList<String> arr = new ArrayList<>(VideoStringArray);
        VideoStringArray.clear();
        return arr;
    }

    public static String traversingGetVideoByIdAsString(Element node, String videoId) {
        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(videoId)) {
                if (!node.getNodeChildren().isEmpty()) {
                    if (node.getNodeChildren().get(0).getTag().equals("source")) {
                        srcString = node.getNodeChildren().get(0).getAttributes().get("src");
                        returnSrcString = srcString;
                        srcString = "";
                        return returnSrcString;
                    }
                }
                else {
                    srcString = node.getAttributes().get("src");
                    returnSrcString = srcString;
                    srcString = "";
                    return returnSrcString;

                }
            }
        }
        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetVideoByIdAsString(node.getNodeChildren().get(i), videoId);
        }

        return returnSrcString;
    }

    public static void traversingGetVideoByClassAsString(Element node, String videoClass) {
        if (node.getAttributeNames().contains("class")){
            if (node.getAttributes().get("class").equals(videoClass)) {
                if (!node.getNodeChildren().isEmpty()) {
                    if (node.getNodeChildren().get(0).getTag().equals("source")) {
                        VideoClassStringArray.add(node.getNodeChildren().get(0).getAttributes().get("src"));
                    }
                }
                else
                    VideoClassStringArray.add(node.getAttributes().get("src"));
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetVideoByClassAsString(node.getNodeChildren().get(i), videoClass);
        }
    }

    public static ArrayList<String> getVideoClassStringArray() {
        ArrayList<String> arr = new ArrayList<>(VideoClassStringArray);
        VideoClassStringArray.clear();
        return arr;
    }


    public static void traversingGetClassesInPage(Element node) {
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
        ArrayList<String> arr = new ArrayList<>(AllClassArray);
        AllClassArray.clear();
        return arr;
    }

    public static void traversingGetIdsInPage(Element node) {
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
        ArrayList<String> arr = new ArrayList<>(AllIdArray);
        AllIdArray.clear();
        return arr;
    }


    public static void traversingGetContentFromTagAsNode(Element node, String tag){

        if (node.getTag().equals(tag)){
            for(int i = 0; i < node.getStringChildren().size(); i++) {
                tagNodeArray.add(node);
            }
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetContentFromTagAsNode(node.getNodeChildren().get(i),tag);
        }
    }


    public static ArrayList<Element> getTagNodeArray() {
        ArrayList<Element> arr = new ArrayList<>(tagNodeArray);
        tagNodeArray.clear();
        return arr;

    }

    public static void traversingGetContentFromIdAsNode(Element node, String id) {

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


    public static ArrayList<Element> getIdNodeArray() {
        ArrayList<Element> arr = new ArrayList<>(idNodeArray);
        idNodeArray.clear();
        return arr;

    }

    public static void traversingGetContentFromClassAsNode(Element node, String className) {
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


    public static ArrayList<Element> getClassNodeArray() {
        ArrayList<Element> arr = new ArrayList<>(classNodeArray);
        classNodeArray.clear();
        return arr;

    }

    public static void traversingGetLinksInPageAsNode(Element node){
        String tag = "a";

        if (node.getTag().equals(tag)){
            linksNodeArray.add(node);
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetLinksInPageAsNode(node.getNodeChildren().get(i));
        }
    }


    public static ArrayList<Element> getLinksNodeArray() {
        ArrayList<Element> arr = new ArrayList<>(linksNodeArray);
        linksNodeArray.clear();
        return arr;

    }

    public static void traversingContainsAsNode(Element node, String searchString) {

        for (int j = 0; j < node.getStringChildren().size(); j++){
            if (node.getStringChildren().get(j).contains(searchString)) {
                ContainsNodeArray.add(node);
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingContainsAsNode(node.getNodeChildren().get(i), searchString);
        }
    }


    public static ArrayList<Element> getContainsNodeArray() {
        ArrayList<Element> arr = new ArrayList<>(ContainsNodeArray);
        ContainsNodeArray.clear();
        return arr;

    }

    public static void traversingContainsCaseInSensetiveAsNode(Element node, String searchString) {

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


    public static ArrayList<Element> getContainsCaseInSensitiveNodeArray() {
        ArrayList<Element> arr = new ArrayList<>(ContainsCaseInSensitiveNodeArray);
        ContainsCaseInSensitiveNodeArray.clear();
        return arr;

    }

    public static void traversingGetAllImagesFromPageAsNode(Element node) {
        String tag = "img";

        if (node.getTag().equals(tag)){
            ImgNodeArray.add(node);
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetAllImagesFromPageAsNode(node.getNodeChildren().get(i));
        }
    }


    public static ArrayList<Element> getImgNodeArray() {
        ArrayList<Element> arr = new ArrayList<>(ImgNodeArray);
        ImgNodeArray.clear();
        return arr;

    }

    public static Element traversingGetImageByIdAsNode(Element node, String pictureId) {

        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(pictureId)) {
                srcNode = node;
                Element returnSrcNode = srcNode;
                srcString = null;
                return returnSrcNode;
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetImageByIdAsNode(node.getNodeChildren().get(i), pictureId);
        }
        Element returnSrcNode = srcNode;
        srcNode = null;
        return returnSrcNode;
    }

    public static void traversingGetImageByClassAsNode(Element node, String pictureClass) {

        if (node.getAttributeNames().contains("class")){
            if (node.getAttributes().get("class").equals(pictureClass)) {
                ImgClassNodeArray.add(node);
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetImageByClassAsNode(node.getNodeChildren().get(i), pictureClass);
        }
    }


    public static ArrayList<Element> getImgClassNodeArray() {
        ArrayList<Element> arr = new ArrayList<>(ImgClassNodeArray);
        ImgClassNodeArray.clear();
        return arr;

    }

    public static void traversingGetAllVideosFromPageAsNode(Element node) {
        String tag = "video";

        if (node.getTag().equals(tag)){
                VideoNodeArray.add(node);
        }
        for (int i=0; i<node.getNodeChildren().size(); i++){
            traversingGetAllVideosFromPageAsNode(node.getNodeChildren().get(i));
        }
    }


    public static ArrayList<Element> getVideoNodeArray() {
        ArrayList<Element> arr = new ArrayList<>(VideoNodeArray);
        VideoNodeArray.clear();
        return arr;

    }

    public static Element traversingGetVideoByIdAsNode(Element node, String videoId) {
        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(videoId)) {
                srcNode = node;
                Element returnSrcNode = srcNode;
                srcNode = null;
                return returnSrcNode;
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetVideoByIdAsNode(node.getNodeChildren().get(i), videoId);
        }

        Element returnSrcNode = srcNode;
        srcNode = null;
        return returnSrcNode;
    }




    public static void traversingGetVideoByClassAsNode(Element node, String videoClass) {
        if (node.getAttributeNames().contains("class")){
            if (node.getAttributes().get("class").equals(videoClass)) {
                VideoClassNodeArray.add(node);
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetVideoByClassAsNode(node.getNodeChildren().get(i), videoClass);
        }
    }


    public static ArrayList<Element> getVideoClassNodeArray() {
        ArrayList<Element> arr = new ArrayList<>(VideoClassNodeArray);
        VideoClassNodeArray.clear();
        return arr;
    }

    public static void traversingGetAttributeContentWithTagAndNameAsNode(Element node, String tag, String attribute) {

        if (node.getTag().contains(tag)){
            if (node.getAttributeNames().contains(attribute)) {
                AttributeContentNodeArray.add(node);
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetAttributeContentWithTagAndNameAsNode(node.getNodeChildren().get(i), tag, attribute);
        }
    }


    public static ArrayList<Element> getAttributeContentNodeArray() {
        ArrayList<Element> arr = new ArrayList<>(AttributeContentNodeArray);
        AttributeContentNodeArray.clear();
        return arr;

    }

    public static void traversingGetAttributeContentWithTagAndNameAsString(Element node, String tag, String attribute) {
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
        ArrayList<String> arr = new ArrayList<>(AttributeContentStringArray);
        AttributeContentStringArray.clear();
        return arr;
    }

    public static void traversingGetAttributeContentWithIdAndNameAsString(Element node, String id, String attribute) {
        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(id)) {
                 if (node.getAttributeNames().contains(attribute)) {
                     AttributeIdContentStringArray.add(node.getAttributes().get(attribute));
                 }
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetAttributeContentWithIdAndNameAsString(node.getNodeChildren().get(i), id, attribute);
        }
    }

    public static ArrayList<String> getAttributeIdContentStringArray() {
        return AttributeIdContentStringArray;
    }
}
