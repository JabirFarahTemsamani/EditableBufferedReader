package com.mypackage.editablebufferedreader;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.IOException;
/**
 *
 * @author jabir
 */
public class EditableBufferedReader extends BufferedReader {

    public EditableBufferedReader(Reader in) {
        super(in);
    }

     public void setRaw() {
        // Aquí se implementará el código para cambiar a modo raw
        try {
        Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty raw -echo"});
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    // Método para pasar a modo cooked
    public void unsetRaw() {
        // Aquí se implementará el código para volver a modo cooked
        try {
        Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty cooked echo"});
        } catch (IOException e) {
        e.printStackTrace();
    }
    }
    // Leer el siguiente carácter o símbolo (tecla de edición)
    @Override
    public int read() throws IOException {
        // Implementación de la lectura de caracteres y teclas
        return super.read(); // Esto será modificado
    }

    // Leer una línea con capacidad de edición
    @Override
    public String readLine() throws IOException {
        // Implementación de la lectura y edición de línea
        return super.readLine(); // Esto también será modificado
    }
    
}
