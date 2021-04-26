package HTMLString;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class HTMLToString {

    public static String requestHTMLWithUrl(String Url){

        //Instantiating the URL class
        java.net.URL url = null;
        try {
            url = new URL(Url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //Retrieving the contents of the specified page
        Scanner sc = null;
        try {
            sc = new Scanner(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Instantiating the StringBuffer class to hold the result
        StringBuffer sb = new StringBuffer();

        while(sc.hasNext()) {
            sb.append(sc.next() + " ");
            //System.out.println(sc.next());
        }

        //Retrieving the String from the String Buffer object
        return sb.toString();
    }

    public static String readHTMLFromFile(String path){
        String fileContent = "";

        try {
            Scanner scanner = new Scanner(new File(path));
            StringBuilder sb = new StringBuilder();



            while(scanner.hasNext())
                sb.append(scanner.next()).append(" ");
            fileContent = sb.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileContent;
    }


}
