package com.mypackage.editablebufferedreader;


import com.mypackage.editablebufferedreader.EditableBufferedReader;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author jabir
 */
class TestReadLine {
  public static void main(String[] args) {
    EditableBufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in)); //Aqui es donde me deja escribir i coje lo que pongo
    in.setRaw(); // Ponemos en modo raw
    
    String str = null;
    try {
        
      str = in.readLine(); // el se queda aqui quiero hasta que acabas de introducir todo i luego ya funciona (coocked) activa metodo para leer todo hasta salto de linea o fin de linea
    
    } catch (IOException e) { 
    
        e.printStackTrace(); 
    
    }  finally {
        in.unsetRaw();
    }
    
    System.out.println("\nline is: " + str);
  }
}