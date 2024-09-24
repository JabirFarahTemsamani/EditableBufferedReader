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
    BufferedReader in = new EditableBufferedReader(new InputStreamReader(System.in));
    String str = null;
    try {
      str = in.readLine();
    } catch (IOException e) { e.printStackTrace(); }
    System.out.println("\nline is: " + str);
  }
}