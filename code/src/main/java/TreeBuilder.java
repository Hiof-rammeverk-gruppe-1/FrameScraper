import java.util.Stack;


//TODO: there seems to be an issue with attributtes that dont have any value / has quickfix // maybe introduced new bug?
//TODO: issue with attribute reading

//TODO: REFACTOR
//         - making it more clear when a tag is ended
//TODO: if a tag is not ended?
public class TreeBuilder {

    private static int index = 0;

    private static String tag = "";
    private static String attKey = "";
    private static String attValue = "";
    private static String stringContent = "";

    private static boolean readTag = false;
    private static boolean readAttKey = false;
    private static boolean readAttValue = false;
    private static boolean lookForLastQuote = false;
    private static boolean isComment = false;
    private static boolean isIgnoreable = false;
    private static boolean isDoctype = false;

    private static Stack<SoupNode> parentStack = new Stack<>();

    public static SoupNode createTree(String html) throws ParseException {

        SoupNode root = null;
        SoupNode buildingNode = new SoupNode();

        for(int i = 0; i < html.length(); i++){
            char ch = html.charAt(i);

            // ignore content of tag mode
            if (isIgnoreable)
                if (!(ch == '<' && html.charAt(i+1) == '/'))
                    continue;
                else
                    isIgnoreable = false;

            //comment mode
            if (isComment){
                if (ch == '>' && html.charAt(i-1) == '-' && html.charAt(i-2) == '-'){
                    isComment = false;
                }
                continue;
            }

            // if doctypemode alternative ignoremode
            if (isDoctype){
                if (ch == '>')
                    isDoctype = false;
                continue;
            }


            // to go into ignore/comment mode
            if (ch == '<'){
                if (html.charAt(i+1) == '!')
                    if (html.charAt(i+2) == '-' && html.charAt(i+3) == '-')
                        isComment = true;
                    else
                        isDoctype = true;

                else{
                    readTag = true;

                    // buildingnode = readHead(buildingnode, html);

                    if (!parentStack.isEmpty() && !stringContent.isEmpty()){
                        parentStack.peek().addStringChild(stringContent);
                        stringContent = "";
                    }

                }

                continue;
            }

            //  when head of tag is to be ended
            if (ch == '>'){
                if (readTag && tag.isEmpty())
                    throw new ParseException("There exist an empty tag in this html", buildingNode);
                else if (readTag)
                    buildingNode.setTag(tag);
                else if (readAttKey && !(attKey.isEmpty())){
                    buildingNode.getAttributeNames().add(attKey);

                    buildingNode.getAttributes().put(attKey, "");
                }

                //add nodes
                if (root == null)
                    root = buildingNode;

                if (!parentStack.isEmpty())
                    parentStack.peek().addNodeChild(buildingNode);

                if (!isSingletonTag(buildingNode.getTag())){
                    parentStack.push(buildingNode);
                }

                if (isIgnoreableContentTag(buildingNode.getTag()))
                    isIgnoreable = true;


                // reset locals
                tag = "";
                attKey = "";
                attValue = "";

                readTag = false;
                readAttKey = false;
                readAttValue = false;
                lookForLastQuote = false;
                isComment = false;

                buildingNode = new SoupNode();

                continue;
            }

            // read String content
            if (!(readTag || readAttKey || readAttValue) && !parentStack.isEmpty()){
                stringContent += ch;
            }

            //read tag
            if (readTag) {
                if (Character.isLetter(ch) || Character.isDigit(ch))
                    tag += ch;
                else {
                    if (tag.isEmpty() && ch == '/'){

                        SoupNode parentToEnd = parentStack.pop();
                        String tagEnded = parentToEnd.getTag();

                        if (html.substring(i + 1, i + 1 + tagEnded.length()).equals(tagEnded)){
                            i = i + 1 + tagEnded.length();

                            readTag = false;
                            continue;
                        }
                        else
                            throw new ParseException("Ended tag doesn't fit current parent", parentToEnd);
                    }


                    readTag = false;
                    readAttKey = true;
                    buildingNode.setTag(tag);
                    tag = "";
                    continue;
                }
            }
//            read key
            else if (readAttKey){
                if (Character.isLetter(ch))
                    attKey += ch;
                else if(ch == '='){
                    if(!attKey.isEmpty()){
                        readAttKey = false;
                        readAttValue = true;
                    }
                }
                //TODO: quick fix for solo attributtes
                else if(ch == ' ' && !attKey.isEmpty()){
                    buildingNode.getAttributeNames().add(attKey);
                    buildingNode.getAttributes().put(attKey, "");

                    attKey = "";
                }
            }
//            read key value
            else if(readAttValue){
                //see first quotes "
                if (!lookForLastQuote){
                    if (ch == '\"'){
                        lookForLastQuote = true;
                        continue;
                    }
                    else
                        throw new ParseException("Missing quotes after attributtes", buildingNode);
                }
//                if(ch == '\"' && !lookForLastQuote){
//                    lookForLastQuote = true;
//                    continue;
//                }
//                else if(ch != '\"' && !lookForLastQuote){
//                    throw new ParseException("Missing quotes after attributtes");
//                }
                //build value string untill we see see last quotes "
                if(lookForLastQuote){
                    if (ch == '\"'){

                        buildingNode.getAttributeNames().add(attKey);

                        buildingNode.getAttributes().put(attKey, attValue);

                        readAttValue = false;
                        readAttKey = true;
                        lookForLastQuote = false;

                        attKey = "";
                        attValue = "";

                        continue;
                    }
                    attValue += ch;
                }

            }
        }

        return root;
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(readHead("<iframe class=\"one-cookie-banner-frame visible\" id=\"cookie-banner-718c36023bf70cb8158c7432766c5334\" src=\"about:blank\" frameborder=\"0\" data-name=\"Cookie Banner\" style=\"height: 134px;\" controlls async>", new SoupNode()));
    }

    public static SoupNode readHead(String html, SoupNode buildingNode) throws ParseException {
        char ch = html.charAt(index);
        if (ch != '<')
            throw new ParseException("This is no time to read head! Not <");

        index++;
        readTag = true;

        int quotes = 0;

        String tag = "";
        String attKey = "";
        String attValue = "";
        
        ch = html.charAt(index);
        while (ch != '>'){

            if (readTag){
                if (ch != ' ')
                    tag += ch;
                else {
                    if (!tag.isEmpty()){
                        buildingNode.setTag(tag);

                        readAttKey = true;
                        readTag = false;
                    }
                }
            }
            else if (readAttKey){
                // when attribute name is to end
                if (ch == '='){
                    readAttKey = false;
                    readAttValue = true;
                }
                //for solo attributes
                else if (ch == ' '){
                    if (!attKey.isEmpty()){

                        buildingNode.getAttributeNames().add(attKey);
                        buildingNode.getAttributes().put(attKey, "");

                        attKey = "";
                    }
                }
                else
                    attKey += ch;
            }
            else if (readAttValue){
                if (quotes < 1){
                    if (ch != '\"')
                        throw new ParseException("Missing quotes after attributtes", buildingNode);
                    quotes++;
                }
                else if (quotes == 1){
                    if (ch != '\"'){
                        attValue += ch;
                    }
                    else{
                        buildingNode.getAttributeNames().add(attKey);
                        buildingNode.getAttributes().put(attKey, attValue);

                        attKey = "";
                        attValue = "";

                        readAttKey = true;
                        readAttValue = false;

                        quotes = 0;
                    }
                }
            }


            index++;
            ch = html.charAt(index);
        }

        if (buildingNode.getTag().isEmpty()){
            if (tag.isEmpty())
                throw new ParseException("Empty tag is detected");
            else
                buildingNode.setTag(tag);
        }

        if (!attKey.isEmpty()){
            buildingNode.getAttributeNames().add(attKey);
            buildingNode.getAttributes().put(attKey, "");
        }



        return buildingNode;
    }

    private static boolean isSingletonTag(String tag){
        String[] singletons = {"area", "base", "br", "col", "embed", "hr", "img", "input", "link", "meta", "param", "source", "track", "wbr", "command", "keygen", "menuitem"};

        for (int i = 0; i < singletons.length; i++)
            if (tag.equals(singletons[i]))
                return true;
        return false;
    }

    private static boolean isIgnoreableContentTag(String tag){
        String[] ignoreableTags = {"script", "style"};

        for (int i = 0; i < ignoreableTags.length; i++){
            if (tag.equals(ignoreableTags[i]))
                return true;
        }
        return false;
    }
}
