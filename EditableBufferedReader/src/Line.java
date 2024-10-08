
public class Line  {  // cambiar el nombre a EditLine o Edit

    protected String str;  // Esta bien string aqui al final es único oque edita // es el mismo line todo el rato
    private EditableBufferedReader editBuffRea; // le paso el bufered reader parq eu pueda editar su atribito cursor.


    public Line(EditableBufferedReader editBuffRea) {
        this.str = "";
        this.editBuffRea = editBuffRea; 
    }


    public String delete() { 
        if (editBuffRea.cursor > -1) { // si es 0 ya me vale
            str = str.substring(0, editBuffRea.cursor) + str.substring(editBuffRea.cursor + 1);  // poner cursor iniciar des de 0 modificar i poner a funcionar bien esta funcion
            editBuffRea.cursor--; // baja una posicion
        }
        
        return str; 
    }

    public String supr(){  // falta per acabar

        if (!(editBuffRea.cursor >= str.length()-1) &&!(str.isEmpty()) ) { // en -1 si el string no esta vacio podemos eliminar lo que haya
            //No puedes estar en el ultimo indice del string 
            str = str.substring(0, editBuffRea.cursor+1) + str.substring(editBuffRea.cursor + 2);  // delete al carater de delante
            editBuffRea.cursor--; // baja una posicion
        }
        
        return str; 

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
