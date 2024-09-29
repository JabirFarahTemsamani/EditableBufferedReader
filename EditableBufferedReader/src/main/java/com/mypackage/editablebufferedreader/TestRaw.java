/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mypackage.editablebufferedreader;

/**
 *
 * @author jabir
 */
import java.io.*;

class TestRaw {
    public static void main(String[] args) {
        EditableBufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
        in.setRaw(); // Cambiar a modo raw

        System.out.println("Escribe algo (presiona 'q' para salir):");
        
        try {
            int c;
            while ((c = in.read()) != -1) { // Lee un carácter
                if (c == 'q') { // Si se presiona 'q', salir
                    break;
                }
                System.out.print((char) c); // Muestra el carácter leído
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //in.unsetRaw(); // Restablecer a modo cooked
        }

        System.out.println("\nSaliendo del modo raw.");
    }
}