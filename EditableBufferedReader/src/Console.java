
public class Console {

    public EditableBufferedReader editBuffRea;

    public Console(EditableBufferedReader editBuffRea) {
        this.editBuffRea=editBuffRea;
    }
    
    

    public void updateView(Line line) {
        System.out.print("\033[2K\033[2;1H"); // borra la segona fila em sembla.
        System.out.print(line);
        System.out.print("\033[2;" + (editBuffRea.cursor+2) + "H"); // fila 2 columna que yo elijo // sumo 2 para que cuadre el valor con donde quiero que este
    }

    public void abort() {
        System.out.print("\033[3;1H"); // Posa el cursor a la 3a linia
        System.out.print("\033[31m"); // Canvia el color a vermell per mostrar l'error
        System.out.print("Aborted");
        System.out.print("\033[0m"); // Torna al color per defecte
        System.out.print("\033[4;1H"); // Coloca el cursor a la 4a línia
    }

 
}
