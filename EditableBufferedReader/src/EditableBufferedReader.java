
import java.io.*;

public class EditableBufferedReader extends BufferedReader {

    private InputStreamReader in;
    private Console out;
    private Line buffer; // crea una line tal cual creo
    protected  int cursor; // ya veo donde lo uso 

    public EditableBufferedReader(InputStreamReader in) {
        super(in);
        this.out = new Console(this);
        this.cursor = -1;  //**************** */
        this.buffer = new Line(this);
    }

    public void setRaw() {
        try {
            String[] command = {"/bin/sh", "-c", "stty raw -echo </dev/tty"};
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unsetRaw() {
        try {
            String[] command = {"/bin/sh", "-c", "stty cooked echo </dev/tty"};
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int read() throws IOException {
        int newChar = super.read();
        return newChar;
    }

    @Override
    public String readLine() throws IOException {
        setRaw();

        System.out.print("\033[4L"); // insetra 4 lineas vacias hacia abajo en el terminal
        Line buffer = new Line(this);
        char newChar;
        //int cursor = 1; // cursor esta en la clase EditableBufferedReader /********************* */

        do {
            newChar = (char) read();
            int prova = newChar;
            // System.out.println((int) newChar);
            switch (newChar) {
                case (char) 13: // indica que se ha presionado enter
                    unsetRaw();
                    return buffer.toString(); // devolbemos lo escito
                case (char) 4: // Control+D -> EOT (End Of Transmission)
                    unsetRaw();
                    out.updateView(buffer);
                    out.abort();
                    System.exit(1);
                case (char) 27: // ^ // lee el esc la seuqncia de escape
                    controlSequence(buffer);
                    break;
                default:
                    buffer.addChar(newChar);
                    out.updateView(buffer);
            }
        } while (true);
    }


    public void controlSequence(Line buffer) throws IOException {
        char newchar = (char) read();
        switch (newchar) {
            case (char) 91: // [ // indica eso que es lo que sigue despues de un ecape en una sequencia de escape
                escapeSequence(buffer);
                break;

            default:
                break;
        }
    }

    void escapeSequence(Line buffer) throws IOException {
        char newchar = (char) read();
        int prova2 = newchar;
        switch (newchar) {
            case 68: // <- // flecha izquierda "D" son esos numeros en asci
                this.cursor--;
                System.out.print("\033[1D");
                break;
            case 67: // -> // flecha derecha "C" son esos numeros en asci
                this.cursor++;
                System.out.print("\033[1C");
                break;
            default:
                break;
        }
    }


}

