/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mypackage.editablebufferedreader;

import com.sun.jna.*;

public interface CLibrary extends Library {
    
  CLibrary INSTANCE = Native.load("c", CLibrary.class);
    // Método para ejecutar comandos del sistema
    void system(String command);
}
