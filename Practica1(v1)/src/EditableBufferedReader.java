
import java.io.*;

public class EditableBufferedReader extends BufferedReader {

    
    private int pos;
    private boolean insMod;
    private String buffer;  

    public EditableBufferedReader(InputStreamReader in) {
        super(in);
        this.pos = -1;  // No hi ha cap element a l'array estem fora
        this.insMod = false;
        this.buffer = "";

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
        int charCode = super.read();
        return charCode;
    }



    @Override
    public String readLine() throws IOException { 
        
        setRaw();

        char inChar;

        while (true) {

            inChar = (char) read();

            switch (inChar) {

                default:

                if (!this.insMod) {

                    if (this.pos == -1) { 
                        this.buffer = inChar + this.buffer;
                    } else {
                        this.buffer = this.buffer.substring(0, this.pos + 1) + (char) inChar + this.buffer.substring(this.pos + 1); 
                    }
                    this.pos++;
                
                } else {
                    if (this.pos < this.buffer.length() - 1) {
                        this.buffer = this.buffer.substring(0, this.pos + 1) + (char) inChar + this.buffer.substring(this.pos + 2);
                    }
                }

                break;

                case 127: // Return

                    if (this.pos > -1) {
                        this.buffer = this.buffer.substring(0, this.pos) + this.buffer.substring(this.pos + 1);  
                        this.pos--;
                    }

                break;

                case 27: //ESC
                
                this.seqEsc(buffer);

                break;
                
                
                case 13: // Enter
                  
                    unsetRaw();
                    return buffer;  
            }

            System.out.print("\033[2K\033[2;1H"); // \033[2K :borra fila 2 // \033[2;1H : cursor fila 2 col 1
            System.out.print(buffer);
            System.out.print("\033[2;" + (this.pos + 2) + "H"); 
        }
    }

    void seqEsc(String buffer) throws IOException {

        char inChar = (char) this.read();

        if (inChar != 91) {  // [
            return;
        }

        inChar = (char) this.read();

        switch (inChar) {

            case 68 : // <- "D"

                if (this.pos < 0) {
                    break;
                }
                System.out.print("\033[1D");
                this.pos--;
                
            break;
            
            case 67 : // -> "C"

                if (this.pos >= (this.buffer.length() - 1)) {
                    break;
                }
                System.out.print("\033[1C");
                this.pos++; 

                break;

            case 72:  // inicio

                pos = -1;
                System.out.print("\033[2;" + (this.pos + 2) + "H"); // fila 2 columna 1

                break;

            case 70 :  // final

                pos = (this.buffer.length() - 1); 
                System.out.print("\033[2;" + (this.pos + 2) + "H");
            
            break;
            
            case 51 : //supr

                if (this.read() == 126) {  
                    if (!(this.pos >= buffer.length() - 1) && !(buffer.isEmpty())) {
                        this.buffer = this.buffer.substring(0, this.pos + 1) + this.buffer.substring(this.pos + 2);
                        this.pos--;
                    }
                }

            break;
            
            case 50 : //insert

                if (this.read() == 126) { 
                    this.insMod = !this.insMod;
                }
            
            break;
            
            default:
            break;
        }
    }

}
