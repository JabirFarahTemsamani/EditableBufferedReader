package com.mypackage.editablebufferedreader;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.IOException;
import com.sun.jna.*;

/**
 *
 * @author jabir
 */
public class EditableBufferedReader extends BufferedReader {

    private StringBuilder lineBuffer; // bufer para guardar todo
    private int cursorPosition; // para guardar posicion del cursor

    public EditableBufferedReader(Reader in) {
        super(in);
        lineBuffer = new StringBuilder();
        cursorPosition = 0;
    }

    public void setRaw() {
        // Aquí se implementará el código para cambiar a modo raw
        
        CLibrary.INSTANCE.system("stty raw -echo");
//        try {
//            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty raw -echo"}); //-echo 
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    // Método para pasar a modo cooked
    public void unsetRaw() {
        // Aquí se implementará el código para volver a modo cooked
         CLibrary.INSTANCE.system("stty cooked echo");
//        try {
//            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "stty cooked echo"});
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    // Leer el siguiente carácter o símbolo (tecla de edición)
    @Override
    public int read() throws IOException { // por cada caracter puesto activa la funcion read
        return super.read();
//
//        int ch = super.read();  // Lee un carácter
//
//        if (ch == 27) {  // Si es el carácter de escape (ESC) (Una vez ya has escrito o puesto todo, empieza a leer si lee un "ESC" entra
//
//            ch = super.read();  // Lee el siguiente carácter 
//
//            if (ch == '[') {  // Si es una secuencia de escape (mira si el siguiente es un "[" caracteristico de las seuqnecias de escape)
//
//                ch = super.read();  // Lee el siguiente carácter para identificar la tecla ( si ha llegado aqui es que es una sequencai de escape a ver cual)
//
//                switch (ch) {
//                    case 'A':
//                        return KEY_UP;    // Flecha hacia arriba
//                    case 'B':
//                        return KEY_DOWN;  // Flecha hacia abajo
//                    case 'C':
//                        return KEY_RIGHT; // Flecha hacia la derecha
//                    case 'D':
//                        moveCursorLeft();
//                        return -; // No añadimos nada al buffer
//                        
//                    // Añade más casos para otras teclas especiales si es necesario
//                    default:
//                        return ch;  // Devuelve cualquier otro carácter
//                }
//            }
//        }
//
//        return ch;  // Si no es una secuencia de escape, devuelve el carácter leído
    }

 

    // Leer una línea con capacidad de edición
    @Override
    public String readLine() throws IOException {

        int ch;

        while ((ch = this.read()) != '\n') { // Mientras no sea 'Enter'
            
        if (ch == 27) {  // Si es el carácter de escape (ESC) (Una vez ya has escrito o puesto todo, empieza a leer si lee un "ESC" entra

            ch = super.read();  // Lee el siguiente carácter 

            if (ch == '[') {  // Si es una secuencia de escape (mira si el siguiente es un "[" caracteristico de las seuqnecias de escape)

                ch = super.read();  // Lee el siguiente carácter para identificar la tecla ( si ha llegado aqui es que es una sequencai de escape a ver cual)

                switch (ch) {
//                    case 'A':
//                        return KEY_UP;    // Flecha hacia arriba
//                    case 'B':
//                        return KEY_DOWN;  // Flecha hacia abajo
                    case 'C':
                        moveCursorRight();
                    case 'D':
                        moveCursorLeft();
                    // Añade más casos para otras teclas especiales si es necesario
//                    default: return ch;  // Devuelve cualquier otro carácter     
                 }
                
            }

        }
         //System.out.print(lineBuffer.toString());  // Devuelve la línea completa /voy mostrando lo que hay 
        } 
        
        return lineBuffer.toString(); // Devuelve la línea completa
            
//            if (ch == KEY_LEFT) {
//                // Mueve el cursor a la izquierda
//                moveCursorLeft();
//            } else if (ch == KEY_RIGHT) {
//                // Mueve el cursor a la derecha
//                moveCursorRight();
//            } else if (ch == KEY_BACKSPACE) {
//                // Borra el último carácter del buffer
//                if (lineBuffer.length() > 0) {
//                    lineBuffer.deleteCharAt(lineBuffer.length() - 1);
//                }
//            } else {
//                // Añade el carácter al buffer de la línea
//                lineBuffer.append((char) ch);
//                System.out.print((char) ch); // Muestra el carácter en pantalla
//            }
       

        

        // Implementación de la lectura y edición de línea
        //return super.readLine(); // Esto también será modificado
    }
    
    
       private void moveCursorLeft() {
        if (cursorPosition > 0) {
            cursorPosition--;
            System.out.print("\033[D"); // ANSI escape code para mover el cursor a la izquierda
        }
    }
    
        private void moveCursorRight() {
        if (cursorPosition < lineBuffer.length()) {
            cursorPosition++;
            System.out.print("\033[C"); // ANSI escape code para mover el cursor a la derecha
        }
    }

}
