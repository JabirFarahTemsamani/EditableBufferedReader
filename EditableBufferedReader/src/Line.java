
public class Line  {  // cambiar el nombre a EditLine o Edit

    public String str;  // Esta bien string aqui al final es único oque edita // es el mismo line todo el rato
    public EditableBufferedReader editBuffRea; // le paso el bufered reader parq eu pueda editar su atribito cursor.


    public Line(EditableBufferedReader editBuffRea) {
        this.str = "";
        this.editBuffRea = editBuffRea; 
    }


    public String delete() { 
        if (editBuffRea.cursor > -1) { // si es 0 ya me vale
            str = str.substring(0, editBuffRea.cursor - 1) + str.substring(editBuffRea.cursor + 1);  // poner cursor iniciar des de 0 modificar i poner a funcionar bien esta funcion
        }
        editBuffRea.cursor--; // baja una posicion
        return str; // quiero modificar des de aqui tambien el valor de cursor o al menos intentarlo.
    }

    public String addChar(char c) {
        
        
        if (editBuffRea.cursor == -1) {  // si estoy en la posicion inicial del cursor
            str = c + str;
        }else{
            str = str.substring(0, editBuffRea.cursor+1) + (char) c + str.substring(editBuffRea.cursor + 1); // me pongo atras justo encima añado 
        }
        editBuffRea.cursor++; // sube una posicion
        
        return str;
    }

    @Override
    public String toString() {
        return this.str;
    }

}
