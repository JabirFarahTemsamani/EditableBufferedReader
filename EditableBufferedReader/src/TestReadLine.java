
import java.io.*;

public class TestReadLine {

    public static void main(String[] args) {

        BufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in)); // a lo mejor hay que cambiar classe a EditableBufferedReader
        String str = null;

        try {
            
            str = in.readLine();
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print("\033[32m"); // Canvia el color a verd per mostrar el resultat final
        System.out.println("\n\033[1Gline is: " + str);
        System.out.print("\033[1G\033[0m"); // Canvia de línia, es posa a la primera posició i torna al color per defecte
    }

}
