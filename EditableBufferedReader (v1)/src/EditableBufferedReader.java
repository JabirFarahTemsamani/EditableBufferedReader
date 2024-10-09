
import java.io.*;

public class EditableBufferedReader extends BufferedReader {

    private InputStreamReader in;
    private int cursor;
    private boolean insMod;
    private String buffer;  //NOU

    public EditableBufferedReader(InputStreamReader in) {
        super(in);

        this.cursor = -1;  // No hi ha cap element a l'array estem fora
        this.insMod = false;
        this.buffer = ""; //NOU

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
    public String readLine() throws IOException {  // ARREGLAR JAAAAAAAAAAAAA
        setRaw();

        char inChar;

        while (true) {
            inChar = (char) read();
            switch (inChar) {
                case 13: // Enter
                    unsetRaw();
                    return buffer; //editBuff.toString();  //NOU
                case 4: // Control+D -> EOT (End Of Transmission)
                    unsetRaw();
                    System.exit(1);
                    break;
                case 27: // ^ // lee el esc la seuqncia de escape
                    this.escapeSequence(buffer);  // NOU ara li passo el string

                    break;
                case 127: // ascii de la tecla return 
                    if (this.cursor > -1) { // si es 0 ya me vale
                        this.buffer = this.buffer.substring(0, this.cursor) + this.buffer.substring(this.cursor + 1);  // poner cursor iniciar des de 0 modificar i poner a funcionar bien esta funcion
                        this.cursor--; // baja una posicion
                    }
                    break;
                default:
                    if (!this.insMod) {

                        if (this.cursor == -1) {  // si estoy en la posicion inicial del cursor
                            this.buffer = inChar + this.buffer;
                        } else {
                            this.buffer = this.buffer.substring(0, this.cursor + 1) + (char) inChar + this.buffer.substring(this.cursor + 1); // me pongo atras justo encima añado 
                        }
                        this.cursor++; // sube una posicion
                    } else {
                        if (this.cursor < this.buffer.length() - 1) { // no vull que estigi en la última posició
                            this.buffer = this.buffer.substring(0, this.cursor + 1) + (char) inChar + this.buffer.substring(this.cursor + 2);
                        }
                    }
                    break;
            }
            System.out.print("\033[2K\033[2;1H"); // borra la segona fila i posa el cursos a l'inici
            System.out.print(buffer);
            System.out.print("\033[2;" + (this.cursor + 2) + "H"); // mantenim el cursor on el teniem
        }
    }

    void escapeSequence(String buffer) throws IOException {
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
                System.out.print("\033[1D"); // 1 pos a la esquerra
                this.cursor--; // s'an de cambiar pel valor que les mouuuuuuuuuuu
                break;
            case 'C': // -> // flecha derecha "C" son esos numeros en asci
                if (this.cursor >= (this.buffer.length() - 1)) { // no ens volem sortir del string // amb un == tmb val
                    break;
                }
                System.out.print("\033[1C"); // mou a la esquerra
                this.cursor++;  // mouuuu
                break;
            case 'H':  // inici
                cursor = -1; // lo pongo en la última posicion  // moulo allà
                System.out.print("\033[2;" + (this.cursor + 2) + "H"); // ho poso a la columna 1
                break;
            case 'F':  // final
                cursor = (this.buffer.length() - 1); // ultimo indice del string  // 
                System.out.print("\033[2;" + (this.cursor + 2) + "H");
                break;
            case '3':
                if (this.read() == '~') {  // squencia es [3~
                    if (!(this.cursor >= buffer.length() - 1) && !(buffer.isEmpty())) { // en -1 si el string no esta vacio podemos eliminar lo que haya
                        //No puedes estar en el ultimo indice del string 
                        this.buffer = this.buffer.substring(0, this.cursor + 1) + this.buffer.substring(this.cursor + 2);  // delete al carater de delante
                        this.cursor--; // baja una posicion
                    }
                }
                break;
            case '2':
                if (this.read() == '~') {  // squencia es [2~ // tecla insert
                    this.insMod = !this.insMod;
                }
                break;
            default:
                break;
        }
    }

}
