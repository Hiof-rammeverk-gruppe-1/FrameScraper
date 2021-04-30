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
        ArrayList<String> arr = new ArrayList<>(tagStringArray);
        tagStringArray.clear();
        return arr;
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
        ArrayList<String> arr = new ArrayList<>(idStringArray);
        idStringArray.clear();
        return arr;
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
        ArrayList<String> arr = new ArrayList<>(classStringArray);
        classStringArray.clear();
        return arr;
    }

    public static void traversingGetLinksInPageAsString(SoupNode node,String url){
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
        Boolean returnContainsVar = containsVar;
        containsVar = false;
        return returnContainsVar;
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

        Boolean returnContainsVar = containsVar;
        containsVar = false;
        return returnContainsVar;
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
        ArrayList<String> arr = new ArrayList<>(ImgStringArray);
        ImgStringArray.clear();
        return arr;
    }

    public static String traversingGetImageByIdAsString(SoupNode node, String pictureId) {

        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(pictureId)) {
                srcString = node.getAttributes().get("src");

                String returnSrcString = srcString;
                srcString = "";
                return returnSrcString;
                }
            }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetImageByIdAsString(node.getNodeChildren().get(i), pictureId);
        }

        String returnSrcString = srcString;
        srcString = "";
        return returnSrcString;
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
        ArrayList<String> arr = new ArrayList<>(ImgClassStringArray);
        ImgClassStringArray.clear();
        return arr;
    }

    public static void traversingGetAllVideosFromPageAsString(SoupNode node) {
        String tag = "video";

        if (node.getTag().equals(tag)){
            if (node.getNodeChildren().get(0).getTag().equals("source")){
                VideoStringArray.add(node.getNodeChildren().get(0).getAttributes().get("src"));
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

    public static String traversingGetVideoByIdAsString(SoupNode node, String videoId) {
        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(videoId)) {
                if (node.getNodeChildren().get(0).getTag().equals("source")){
                    srcString = node.getNodeChildren().get(0).getAttributes().get("src");
                    String returnSrcString = srcString;
                    srcString = "";
                    return returnSrcString;
                }
                else {
                    srcString = node.getAttributes().get("src");
                    String returnSrcString = srcString;
                    srcString = "";
                    return returnSrcString;
                }
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetVideoByIdAsString(node.getNodeChildren().get(i), videoId);
        }

        String returnSrcString = srcString;
        srcString = "";
        return returnSrcString;
    }

    public static void traversingGetVideoByClassAsString(SoupNode node, String videoClass) {
        if (node.getAttributeNames().contains("class")){
            if (node.getAttributes().get("class").equals(videoClass)) {
                if (node.getNodeChildren().get(0).getTag().equals("source")){
                    VideoClassStringArray.add(node.getNodeChildren().get(0).getAttributes().get("src"));
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
        ArrayList<String> arr = new ArrayList<>(AllClassArray);
        AllClassArray.clear();
        return arr;
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
        ArrayList<String> arr = new ArrayList<>(AllIdArray);
        AllIdArray.clear();
        return arr;
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
        ArrayList<SoupNode> arr = new ArrayList<>(tagNodeArray);
        tagNodeArray.clear();
        return arr;
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
        ArrayList<SoupNode> arr = new ArrayList<>(idNodeArray);
        idNodeArray.clear();
        return arr;
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
        ArrayList<SoupNode> arr = new ArrayList<>(classNodeArray);
        classNodeArray.clear();
        return arr;
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
        ArrayList<SoupNode> arr = new ArrayList<>(linksNodeArray);
        linksNodeArray.clear();
        return arr;
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
        ArrayList<SoupNode> arr = new ArrayList<>(ContainsNodeArray);
        ContainsNodeArray.clear();
        return arr;
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
        ArrayList<SoupNode> arr = new ArrayList<>(ContainsCaseInSensitiveNodeArray);
        ContainsCaseInSensitiveNodeArray.clear();
        return arr;
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
        ArrayList<SoupNode> arr = new ArrayList<>(ImgNodeArray);
        ImgNodeArray.clear();
        return arr;
    }

    public static SoupNode traversingGetImageByIdAsNode(SoupNode node, String pictureId) {

        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(pictureId)) {
                srcNode = node;
                SoupNode returnSrcNode = srcNode;
                srcString = null;
                return returnSrcNode;
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetImageByIdAsNode(node.getNodeChildren().get(i), pictureId);
        }
        SoupNode returnSrcNode = srcNode;
        srcString = null;
        return returnSrcNode;
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
        ArrayList<SoupNode> arr = new ArrayList<>(ImgClassNodeArray);
        ImgClassNodeArray.clear();
        return arr;
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
        ArrayList<SoupNode> arr = new ArrayList<>(VideoNodeArray);
        VideoNodeArray.clear();
        return arr;
    }

    public static SoupNode traversingGetVideoByIdAsNode(SoupNode node, String videoId) {
        if (node.getAttributeNames().contains("id")){
            if (node.getAttributes().get("id").equals(videoId)) {
                srcNode = node;
                SoupNode returnSrcNode = srcNode;
                srcNode = null;
                return returnSrcNode;
            }
        }

        for (int i = 0; i < node.getNodeChildren().size(); i++) {
            traversingGetVideoByIdAsNode(node.getNodeChildren().get(i), videoId);
        }

        SoupNode returnSrcNode = srcNode;
        srcNode = null;
        return returnSrcNode;
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
        ArrayList<SoupNode> arr = new ArrayList<>(VideoClassNodeArray);
        VideoClassNodeArray.clear();
        return arr;
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
        ArrayList<SoupNode> arr = new ArrayList<>(AttributeContentNodeArray);
        AttributeContentNodeArray.clear();
        return arr;
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
        ArrayList<String> arr = new ArrayList<>(AttributeContentStringArray);
        AttributeContentStringArray.clear();
        return arr;
    }
}
