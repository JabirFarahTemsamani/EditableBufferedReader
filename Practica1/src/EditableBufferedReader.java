
import java.io.*;

public class EditableBufferedReader extends BufferedReader {

    private InputStreamReader in;
    protected int pos;
    protected boolean insMod;

    private Console view;
    private Line controller;
    

    public EditableBufferedReader(InputStreamReader in) {

        super(in);
        this.pos = -1; 
        this.insMod = false;

        this.view = new Console(this);
        this.controller = new Line(this);
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

                if(!this.insMod){
                    controller.addChar(inChar);
                    view.updateView(controller);
                }else{
                    controller.insertChar(inChar);
                    view.updateView(controller);
                }

                break;

                case 127: // Return

                    controller.delete();
                    view.updateView(controller);

                break;

                case 27: // ESC

                    controller.seqEsc();
                    view.updateView(controller);
                
                break;

                case 13: // Enter

                    unsetRaw();
                    return controller.toString();
                
            }
        }
    }

 

}
