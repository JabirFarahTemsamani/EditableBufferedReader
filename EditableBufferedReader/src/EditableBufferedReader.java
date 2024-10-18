
import java.io.*;

public class EditableBufferedReader extends BufferedReader {

    private InputStreamReader in;
    private Console out;
    private Line editBuff;
    protected int cursor;
    private boolean insMod;

    public EditableBufferedReader(InputStreamReader in) {
        super(in);
        this.out = new Console(this);
        this.editBuff = new Line(this);
        this.cursor = -1;  // No hi ha cap element a l'array estem fora
        this.insMod = false;

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
    public int read() throws IOException {  // El demana l'enunciat
        int inChar = super.read();
        return inChar;
    }

    @Override
    public String readLine() throws IOException {
        setRaw();

        Line editBuff = new Line(this);
        char inChar;

        while (true) {
            inChar = (char) read();
            switch (inChar) {
                case 13: // Enter
                    unsetRaw();
                    return editBuff.toString();
                case 4: // Control+D -> EOT (End Of Transmission)
                    unsetRaw();
                    out.updateView(editBuff);
                    out.abort();
                    System.exit(1);
                    break;
                case 27: // ^ // lee el esc la seuqncia de escape
                    //controlSequence(editBuff);
                    this.escapeSequence(editBuff);
                    out.updateView(editBuff);
                    break;
                case 127: // ascii de la tecla return 
                    editBuff.delete();
                    out.updateView(editBuff);
                    break;
                default:
                    if(!this.insMod){
                        editBuff.addChar(inChar);
                        out.updateView(editBuff);
                    }else{
                        editBuff.insertChar(inChar);
                        out.updateView(editBuff);
                    }
                    break;
            }
        }
    }

    void escapeSequence(Line editBuff) throws IOException {
        char inChar = (char) this.read();
        if (inChar != '[') {    // Nomes si es una aquencia de escape
            return;
        }
        inChar = (char) this.read();

        switch (inChar) {
            case 'D': // <- // fletxa esquerra "D" : 68
                if (this.cursor < 0) { // no ens volem sortir del string // amb un == tmb valdria
                    break;
                }
                this.cursor--;
                break;
            case 'C': // -> // flecha derecha "C" son esos numeros en asci
                if (this.cursor >= (editBuff.str.length() - 1)) { // no ens volem sortir del string // amb un == tmb val
                    break;  
                }
                this.cursor++;
                break;
            case 'H':
                cursor = -1; // lo pongo en la última posicion
                break;
            case 'F':
                cursor = (editBuff.str.length() - 1); // ultimo indice del string
                break;
            case '3':
                if (this.read() == '~') {  // squencia es [3~
                    editBuff.supr();
                }
                break;
            case '2':
            if (this.read() == '~') {  // squencia es [2~ // tecla insert
                this.insMod=!this.insMod;
            }
                break;    
            default:
                break;
        }
    }

}
