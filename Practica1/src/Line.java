import java.io.IOException;

public class Line  {

    private EditableBufferedReader editBuffRea;
    protected String str;  
    
    public Line(EditableBufferedReader editBuffRea) {
        this.str = "";
        this.editBuffRea = editBuffRea; 
    }


    public void delete() { 

        if (editBuffRea.pos > -1) {
            str = str.substring(0, editBuffRea.pos) + str.substring(editBuffRea.pos + 1);
            editBuffRea.pos--;
        }  

    }

    public void supr(){

        if (!(editBuffRea.pos >= str.length()-1) &&!(str.isEmpty()) ) {
            str = str.substring(0, editBuffRea.pos+1) + str.substring(editBuffRea.pos + 2);
            editBuffRea.pos--;
        }
        
    }

    public void addChar(char c) {
        
        
        if (editBuffRea.pos == -1) {
            str = c + str;
        }else{
            str = str.substring(0, editBuffRea.pos+1) + (char) c + str.substring(editBuffRea.pos + 1);
        }
        editBuffRea.pos++;
       
    }


    public void insertChar(char c){

        if(editBuffRea.pos < str.length()-1 ){
            str = str.substring(0,editBuffRea.pos+1) + (char) c + str.substring(editBuffRea.pos+2);
        }

    }

    void seqEsc() throws IOException {

        char inChar = (char) editBuffRea.read();

        if (inChar != 91) {
            return;
        }

        inChar = (char) editBuffRea.read();

        switch (inChar) {

            case 68:  // <- "D"

                if (editBuffRea.pos < 0) {
                    break;
                }
                editBuffRea.pos--;
            
            break;
            
            case 67 : // -> "C"

                if (editBuffRea.pos >= (this.str.length() - 1)) {
                    break;  
                }

                editBuffRea.pos++;
            
            break;
            
            case 72 : // inicio

            editBuffRea.pos = -1;
            
            break;
            
            case 70 : // final

            editBuffRea.pos = (this.str.length() - 1);
            
            break;

            case 51 :  //supr

                if (editBuffRea.read() == 126) { 
                    this.supr();
                }
            
            break;

            case 50 :  //insert

            if (editBuffRea.read() == 126) { 
                editBuffRea.insMod=!editBuffRea.insMod;
            }

            break;    
    
        }
    }

    @Override
    public String toString() {
        return this.str;
    }

}
