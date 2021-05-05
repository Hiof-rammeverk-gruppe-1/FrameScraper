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
    private static ArrayList<Element> tagElementArray = new ArrayList<>();
    private static ArrayList<Element> idElementArray = new ArrayList<>();
    private static ArrayList<Element> classElementArray = new ArrayList<>();
    private static ArrayList<Element> linksElementArray = new ArrayList<>();
    private static ArrayList<Element> ImgElementArray = new ArrayList<>();
    private static ArrayList<Element> ImgClassElementArray = new ArrayList<>();
    private static ArrayList<Element> VideoElementArray = new ArrayList<>();
    private static ArrayList<Element> VideoClassElementArray = new ArrayList<>();
    private static ArrayList<Element> ContainsElementArray = new ArrayList<>();
    private static ArrayList<Element> ContainsCaseInSensitiveElementArray = new ArrayList<>();
    private static ArrayList<Element> AttributeContentElementArray = new ArrayList<>();
    private static boolean containsVar = false;
    private static String srcString;
    private static Element srcElement;
    private static ArrayList<String> AttributeIdContentStringArray = new ArrayList<>();
    private static String returnSrcString = "";
    private static String returnImgSrcString = "";



    public static void traversingGetContentFromTagAsString(Element element, String tag){

        if (element.getTag().equals(tag)){
            for(int i = 0; i < element.getStringChildren().size(); i++) {
                tagStringArray.add(element.getStringChildren().get(i));
            }
        }
        for (int i=0; i<element.getElementChildren().size(); i++){
            traversingGetContentFromTagAsString(element.getElementChildren().get(i),tag);
        }
    }

    public static ArrayList<String> getTagStringArray() {
        ArrayList<String> arr = new ArrayList<>(tagStringArray);
        tagStringArray.clear();
        return arr;
    }

    public static void traversingGetContentFromIdAsString(Element element, String id) {

        if (element.getAttributeNames().contains("id")){
            if (element.getAttributes().get("id").equals(id)) {
                for(int i = 0; i < element.getStringChildren().size(); i++) {
                    idStringArray.add(element.getStringChildren().get(i));
                }
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetContentFromIdAsString(element.getElementChildren().get(i), id);
        }
    }

    public static ArrayList<String> getIdStringArray() {
        ArrayList<String> arr = new ArrayList<>(idStringArray);
        idStringArray.clear();
        return arr;
    }

    public static void traversingGetContentFromClassAsString(Element element, String className) {
        if (element.getAttributeNames().contains("class")){
            if (element.getAttributes().get("class").equals(className)) {
                for(int i = 0; i < element.getStringChildren().size(); i++) {
                    classStringArray.add(element.getStringChildren().get(i));
                }
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetContentFromClassAsString(element.getElementChildren().get(i), className);
        }
    }

    public static ArrayList<String> getClassStringArray() {
        ArrayList<String> arr = new ArrayList<>(classStringArray);
        classStringArray.clear();
        return arr;
    }


    public static void traversingGetLinksInPageAsString(Element element, String url){

        String tag = "a";

        if (element.getTag().equals(tag)){
            if (element.getAttributeNames().contains("href")) {
                char start = element.getAttributes().get("href").charAt(0);
                if (start == '/' || start == '#')
                    linksStringArray.add(url + element.getAttributes().get("href"));
                else
                    linksStringArray.add(element.getAttributes().get("href"));
            }
        }
        for (int i=0; i<element.getElementChildren().size(); i++){
            traversingGetLinksInPageAsString(element.getElementChildren().get(i), url);
        }
    }

    public static ArrayList<String> getLinksStringArray() {
        ArrayList<String> arr = new ArrayList<>(linksStringArray);
        linksStringArray.clear();
        return arr;
    }

    public static Boolean traversingContainsAsBoolean(Element element, String searchString) {

        for (int j = 0; j < element.getStringChildren().size(); j++){
            if (element.getStringChildren().get(j).contains(searchString)) {
                containsVar = true;
                break;
            }
        }

        if (!containsVar)
            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingContainsAsBoolean(element.getElementChildren().get(i), searchString);
            }
        Boolean returnContainsVar = containsVar;
        containsVar = false;
        return returnContainsVar;
    }

    public static Boolean traversingContainsCaseInSensetiveAsBoolean(Element element, String searchString) {

        String SS = searchString.toLowerCase();

        for (int j = 0; j < element.getStringChildren().size(); j++){
            if (element.getStringChildren().get(j).toLowerCase().contains(SS)) {
                containsVar = true;
                break;
            }
        }

        if (!containsVar)
            for (int i = 0; i < element.getElementChildren().size(); i++) {
                traversingContainsCaseInSensetiveAsBoolean(element.getElementChildren().get(i), searchString);
            }

        Boolean returnContainsVar = containsVar;
        containsVar = false;
        return returnContainsVar;
    }

    public static void traversingGetAllImagesFromPageAsString(Element element) {
        String tag = "img";

        if (element.getTag().equals(tag)){
            ImgStringArray.add(element.getAttributes().get("src"));
        }
        for (int i=0; i<element.getElementChildren().size(); i++){
            traversingGetAllImagesFromPageAsString(element.getElementChildren().get(i));
        }
    }

    public static ArrayList<String> getImgStringArray() {
        ArrayList<String> arr = new ArrayList<>(ImgStringArray);
        ImgStringArray.clear();
        return arr;
    }

    public static String traversingGetImageByIdAsString(Element element, String pictureId) {

        if (element.getAttributeNames().contains("id")){
            if (element.getAttributes().get("id").equals(pictureId)) {
                srcString = element.getAttributes().get("src");

                returnImgSrcString = srcString;
                srcString = "";
                return returnImgSrcString;
                }
            }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetImageByIdAsString(element.getElementChildren().get(i), pictureId);
        }

        return returnImgSrcString;
    }

    public static void traversingGetImageByClassAsString(Element element, String pictureClass) {

        if (element.getAttributeNames().contains("class")){
            if (element.getAttributes().get("class").equals(pictureClass)) {
                ImgClassStringArray.add(element.getAttributes().get("src"));
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetImageByClassAsString(element.getElementChildren().get(i), pictureClass);
        }
    }

    public static ArrayList<String> getImgClassStringArray() {
        ArrayList<String> arr = new ArrayList<>(ImgClassStringArray);
        ImgClassStringArray.clear();
        return arr;
    }

    public static void traversingGetAllVideosFromPageAsString(Element element) {
        String tag = "video";

        if (element.getTag().equals(tag)){
            if (!element.getElementChildren().isEmpty()){
                if (element.getElementChildren().get(0).getTag().equals("source")){
                    VideoStringArray.add(element.getElementChildren().get(0).getAttributes().get("src"));
                }
            }
            else
                VideoStringArray.add(element.getAttributes().get("src"));
        }
        for (int i=0; i<element.getElementChildren().size(); i++){
            traversingGetAllVideosFromPageAsString(element.getElementChildren().get(i));
        }
    }

    public static ArrayList<String> getVideoStringArray() {
        ArrayList<String> arr = new ArrayList<>(VideoStringArray);
        VideoStringArray.clear();
        return arr;
    }

    public static String traversingGetVideoByIdAsString(Element element, String videoId) {
        if (element.getAttributeNames().contains("id")){
            if (element.getAttributes().get("id").equals(videoId)) {
                if (!element.getElementChildren().isEmpty()) {
                    if (element.getElementChildren().get(0).getTag().equals("source")) {
                        srcString = element.getElementChildren().get(0).getAttributes().get("src");
                        returnSrcString = srcString;
                        srcString = "";
                        return returnSrcString;
                    }
                }
                else {
                    srcString = element.getAttributes().get("src");
                    returnSrcString = srcString;
                    srcString = "";
                    return returnSrcString;

                }
            }
        }
        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetVideoByIdAsString(element.getElementChildren().get(i), videoId);
        }

        return returnSrcString;
    }

    public static void traversingGetVideoByClassAsString(Element element, String videoClass) {
        if (element.getAttributeNames().contains("class")){
            if (element.getAttributes().get("class").equals(videoClass)) {
                if (!element.getElementChildren().isEmpty()) {
                    if (element.getElementChildren().get(0).getTag().equals("source")) {
                        VideoClassStringArray.add(element.getElementChildren().get(0).getAttributes().get("src"));
                    }
                }
                else
                    VideoClassStringArray.add(element.getAttributes().get("src"));
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetVideoByClassAsString(element.getElementChildren().get(i), videoClass);
        }
    }

    public static ArrayList<String> getVideoClassStringArray() {
        ArrayList<String> arr = new ArrayList<>(VideoClassStringArray);
        VideoClassStringArray.clear();
        return arr;
    }


    public static void traversingGetClassesInPage(Element element) {
        if (element.getAttributeNames().contains("class")){
            if (!AllClassArray.contains(element.getAttributes().get("class"))) {
                AllClassArray.add(element.getAttributes().get("class"));
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetClassesInPage(element.getElementChildren().get(i));
        }
    }

    public static ArrayList<String> getAllClassArray() {
        ArrayList<String> arr = new ArrayList<>(AllClassArray);
        AllClassArray.clear();
        return arr;
    }

    public static void traversingGetIdsInPage(Element element) {
        if (element.getAttributeNames().contains("id")){
            if (!AllIdArray.contains(element.getAttributes().get("id"))) {
                AllIdArray.add(element.getAttributes().get("id"));
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetIdsInPage(element.getElementChildren().get(i));
        }
    }

    public static ArrayList<String> getAllIdsArray() {
        ArrayList<String> arr = new ArrayList<>(AllIdArray);
        AllIdArray.clear();
        return arr;
    }


    public static void traversingGetContentFromTagAsElement(Element element, String tag){

        if (element.getTag().equals(tag)){
            tagElementArray.add(element);
        }
        for (int i=0; i<element.getElementChildren().size(); i++){
            traversingGetContentFromTagAsElement(element.getElementChildren().get(i),tag);
        }
    }


    public static ArrayList<Element> getTagElementArray() {
        ArrayList<Element> arr = new ArrayList<>(tagElementArray);
        tagElementArray.clear();
        return arr;

    }

    public static void traversingGetContentFromIdAsElement(Element element, String id) {

        if (element.getAttributeNames().contains("id")){
            if (element.getAttributes().get("id").equals(id)) {
                idElementArray.add(element);
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetContentFromIdAsElement(element.getElementChildren().get(i), id);
        }
    }


    public static ArrayList<Element> getIdElementArray() {
        ArrayList<Element> arr = new ArrayList<>(idElementArray);
        idElementArray.clear();
        return arr;

    }

    public static void traversingGetContentFromClassAsElement(Element element, String className) {
        if (element.getAttributeNames().contains("class")){
            if (element.getAttributes().get("class").equals(className)) {
                classElementArray.add(element);
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetContentFromClassAsElement(element.getElementChildren().get(i), className);
        }
    }


    public static ArrayList<Element> getClassElementArray() {
        ArrayList<Element> arr = new ArrayList<>(classElementArray);
        classElementArray.clear();
        return arr;

    }

    public static void traversingGetLinksInPageAsElement(Element element){
        String tag = "a";

        if (element.getTag().equals(tag)){
            linksElementArray.add(element);
        }
        for (int i=0; i<element.getElementChildren().size(); i++){
            traversingGetLinksInPageAsElement(element.getElementChildren().get(i));
        }
    }


    public static ArrayList<Element> getLinksElementArray() {
        ArrayList<Element> arr = new ArrayList<>(linksElementArray);
        linksElementArray.clear();
        return arr;

    }

    public static void traversingContainsAsElement(Element element, String searchString) {

        for (int j = 0; j < element.getStringChildren().size(); j++){
            if (element.getStringChildren().get(j).contains(searchString)) {
                ContainsElementArray.add(element);
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingContainsAsElement(element.getElementChildren().get(i), searchString);
        }
    }


    public static ArrayList<Element> getContainsElementArray() {
        ArrayList<Element> arr = new ArrayList<>(ContainsElementArray);
        ContainsElementArray.clear();
        return arr;

    }

    public static void traversingContainsCaseInSensetiveAsElement(Element element, String searchString) {

        String SS = searchString.toLowerCase();

        for (int j = 0; j < element.getStringChildren().size(); j++){
            if (element.getStringChildren().get(j).toLowerCase().contains(SS)) {
                ContainsCaseInSensitiveElementArray.add(element);
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingContainsCaseInSensetiveAsElement(element.getElementChildren().get(i), searchString);
        }
    }


    public static ArrayList<Element> getContainsCaseInSensitiveElementArray() {
        ArrayList<Element> arr = new ArrayList<>(ContainsCaseInSensitiveElementArray);
        ContainsCaseInSensitiveElementArray.clear();
        return arr;

    }

    public static void traversingGetAllImagesFromPageAsElement(Element element) {
        String tag = "img";

        if (element.getTag().equals(tag)){
            ImgElementArray.add(element);
        }
        for (int i=0; i<element.getElementChildren().size(); i++){
            traversingGetAllImagesFromPageAsElement(element.getElementChildren().get(i));
        }
    }


    public static ArrayList<Element> getImgElementArray() {
        ArrayList<Element> arr = new ArrayList<>(ImgElementArray);
        ImgElementArray.clear();
        return arr;

    }

    public static Element traversingGetImageByIdAsElement(Element element, String pictureId) {

        if (element.getAttributeNames().contains("id")){
            if (element.getAttributes().get("id").equals(pictureId)) {
                srcElement = element;
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetImageByIdAsElement(element.getElementChildren().get(i), pictureId);
        }
        return srcElement;
    }

    public static void traversingGetImageByClassAsElement(Element element, String pictureClass) {

        if (element.getAttributeNames().contains("class")){
            if (element.getAttributes().get("class").equals(pictureClass)) {
                ImgClassElementArray.add(element);
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetImageByClassAsElement(element.getElementChildren().get(i), pictureClass);
        }
    }


    public static ArrayList<Element> getImgClassElementArray() {
        ArrayList<Element> arr = new ArrayList<>(ImgClassElementArray);
        ImgClassElementArray.clear();
        return arr;

    }

    public static void traversingGetAllVideosFromPageAsElement(Element element) {
        String tag = "video";

        if (element.getTag().equals(tag)){
                VideoElementArray.add(element);
        }
        for (int i=0; i<element.getElementChildren().size(); i++){
            traversingGetAllVideosFromPageAsElement(element.getElementChildren().get(i));
        }
    }


    public static ArrayList<Element> getVideoElementArray() {
        ArrayList<Element> arr = new ArrayList<>(VideoElementArray);
        VideoElementArray.clear();
        return arr;

    }

    public static Element traversingGetVideoByIdAsElement(Element element, String videoId) {
        if (element.getAttributeNames().contains("id")){
            if (element.getAttributes().get("id").equals(videoId)) {
                srcElement = element;
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetVideoByIdAsElement(element.getElementChildren().get(i), videoId);
        }
        return srcElement;
    }




    public static void traversingGetVideoByClassAsElement(Element element, String videoClass) {
        if (element.getAttributeNames().contains("class")){
            if (element.getAttributes().get("class").equals(videoClass)) {
                VideoClassElementArray.add(element);
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetVideoByClassAsElement(element.getElementChildren().get(i), videoClass);
        }
    }


    public static ArrayList<Element> getVideoClassElementArray() {
        ArrayList<Element> arr = new ArrayList<>(VideoClassElementArray);
        VideoClassElementArray.clear();
        return arr;
    }

    public static void traversingGetAttributeContentWithTagAndNameAsElement(Element element, String tag, String attribute) {

        if (element.getTag().contains(tag)){
            if (element.getAttributeNames().contains(attribute)) {
                AttributeContentElementArray.add(element);
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetAttributeContentWithTagAndNameAsElement(element.getElementChildren().get(i), tag, attribute);
        }
    }


    public static ArrayList<Element> getAttributeContentElementArray() {
        ArrayList<Element> arr = new ArrayList<>(AttributeContentElementArray);
        AttributeContentElementArray.clear();
        return arr;

    }

    public static void traversingGetAttributeContentWithTagAndNameAsString(Element element, String tag, String attribute) {
        if (element.getTag().contains(tag)){
            if (element.getAttributeNames().contains(attribute)) {
                AttributeContentStringArray.add(element.getAttributes().get(attribute));
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetAttributeContentWithTagAndNameAsString(element.getElementChildren().get(i), tag, attribute);
        }
    }

    public static ArrayList<String> getAttributeContentStringArray() {
        ArrayList<String> arr = new ArrayList<>(AttributeContentStringArray);
        AttributeContentStringArray.clear();
        return arr;
    }

    public static void traversingGetAttributeContentWithIdAndNameAsString(Element element, String id, String attribute) {
        if (element.getAttributeNames().contains("id")){
            if (element.getAttributes().get("id").equals(id)) {
                 if (element.getAttributeNames().contains(attribute)) {
                     AttributeIdContentStringArray.add(element.getAttributes().get(attribute));
                 }
            }
        }

        for (int i = 0; i < element.getElementChildren().size(); i++) {
            traversingGetAttributeContentWithIdAndNameAsString(element.getElementChildren().get(i), id, attribute);
        }
    }

    public static ArrayList<String> getAttributeIdContentStringArray() {
        return AttributeIdContentStringArray;
    }
}
