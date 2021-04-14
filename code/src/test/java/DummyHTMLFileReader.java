import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DummyHTMLFileReader {
    private String path;
    private String fileContent;
    public DummyHTMLFileReader() {
        this.path = "src/test/java/DummyHTMLForScraper.html";
        readFile();
    }

    private void readFile() {
        try {
            Scanner scanner = new Scanner(new File(path));
            StringBuilder sb = new StringBuilder();
            while(scanner.hasNext())
                sb.append(scanner.next()).append(" ");
            fileContent = sb.toString();
            System.out.println(fileContent);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getFileContent() {
        return fileContent;
    }
}
