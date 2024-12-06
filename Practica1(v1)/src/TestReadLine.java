
import java.io.*;

public class TestReadLine {

    public static void main(String[] args) {

        BufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
        String str = null;

        try {
            
            str = in.readLine();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n\033[1Gline is: " + str);
        System.out.print("\033[1G");
    }

}
