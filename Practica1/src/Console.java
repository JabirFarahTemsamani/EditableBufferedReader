
public class Console {

    public EditableBufferedReader editBuffRea;

    public Console(EditableBufferedReader editBuffRea) {
        this.editBuffRea=editBuffRea;
    }
    

    public void updateView(Line line) {
        System.out.print("\033[2K\033[2;1H"); // \033[2K borra linea // \033[2;1H fila 2
        System.out.print(line);
        System.out.print("\033[2;" + (editBuffRea.pos+2) + "H");
    }

 
}
