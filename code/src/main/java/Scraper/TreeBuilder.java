package Scraper;

import Scraper.Exceptions.ParseException;

import java.util.Stack;

public final class TreeBuilder {

    private static int index;

    private static Stack<SoupNode> parentStack;

    protected static SoupNode createTree(String html) throws ParseException {

        parentStack = new Stack<>();

        boolean isComment = false;
        boolean isIgnoreable = false;
        boolean isDoctype = false;

        SoupNode root = null;
        SoupNode buildingNode;

        for(index = 0; index < html.length(); index++){
            char ch = html.charAt(index);

            // ignore content of tag mode
            if (isIgnoreable)
                if (!(ch == '<' && html.charAt(index+1) == '/'))
                    continue;
                else
                    isIgnoreable = false;

            //comment mode
            if (isComment){
                if (ch == '>' && html.charAt(index-1) == '-' && html.charAt(index-2) == '-'){
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
                if (html.charAt(index+1) == '!') {
                    if (html.charAt(index + 2) == '-' && html.charAt(index + 3) == '-')
                        isComment = true;
                    else
                        isDoctype = true;
                }
                else if (html.charAt(index+1) == '/'){
                    readTail(html);
                }
                else{
                    buildingNode = readHead(html);

                    if (root == null)
                        root = buildingNode;

                    if (!parentStack.isEmpty())
                        parentStack.peek().addNodeChild(buildingNode);

                    if (!isSingletonTag(buildingNode.getTag())){
                        parentStack.push(buildingNode);
                    }

                    if (isIgnoreableContentTag(buildingNode.getTag()))
                        isIgnoreable = true;

                }
                continue;
            }

            //read string children if there is a parent to contain it
            if (!parentStack.isEmpty()){
                readStringChild(html);
            }
        }

        return root;
    }

    private static void readTail(String html) throws ParseException {
        index += 2;

        String tag = "";

        char ch = html.charAt(index);
        while (ch != '>'){
            tag += ch;

            index++;
            ch = html.charAt(index);
        }

        SoupNode parent = parentStack.pop();
        if (!parent.getTag().equals(tag))
            throw new ParseException("tail tag does match the match parent tag=" + tag + " parentnode= " + parent);
    }

    private static void readStringChild(String html){
        String str = "";

        char ch = html.charAt(index);

        while (ch != '<'){
            if (!(ch == '\n' || ch == '\t' || (str.isEmpty() && ch == ' ')))
                str += ch;

            index++;
            ch = html.charAt(index);
        }

        if (!parentStack.isEmpty())
            if (!str.isEmpty())
                parentStack.peek().addStringChild(str);

        index--;
    }

    private static SoupNode readHead(String html) throws ParseException {
        char ch = html.charAt(index);
        if (ch != '<')
            throw new ParseException("This is no time to read head! Not <");

        SoupNode buildingNode = new SoupNode();

        index++;
        boolean readTag = true;
        boolean readAttKey = false;
        boolean readAttValue = false;

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
                        throw new ParseException("Missing quotes after attributtes for key=" + attKey + " breaking char=" + ch, buildingNode);
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

        if (buildingNode.getTag() == null){
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
