package edu.escuelaing.arep.ejercicio2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

//Browser
public class Ejercicio2{

    public static void main(String[] args) throws MalformedURLException {
        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
        String entradaTeclado = entradaEscaner.nextLine(); // Invocamos un método sobre un objeto Scanner
        System.out.println ("Entrada recibida por teclado es: \"" + entradaTeclado +"\"");
        URL url = new URL(entradaTeclado);
        try (BufferedReader reader= new BufferedReader(new InputStreamReader(url.openStream()))) {
            File res = new File("resultado.html");
            FileWriter f = new FileWriter(res, true);
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null){
                //System.out.println(inputLine);
                f.write(inputLine);
            }
            f.close();
        }catch (IOException x){ 
            System.err.println(x);
        }
        entradaEscaner.close();
    }
}