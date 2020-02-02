package edu.escuelaing.arep.ejercicio1;

import java.net.*;

class GFG { 
    public static void main(String args[]) 
    { 
        // url object 
        URL url = null; 
  
        try { 
            // create a URL 
            url = new URL("https://pegaso.escuelaing.edu.co/index.html?0=10&a=15#AREP"); 
            //url = new URL("http://campusvirtual.escuelaing.edu.co/moodle/"); 
  
            // get the Protocol 
            String Protocol = url.getProtocol(); 
            System.out.println("Protocol = " + Protocol); 

            // get the Autority 
            String Autority = url.getAuthority(); 
            System.out.println("Autority = " + Autority); 

            //getHost
            String  Host = url.getHost();
            System.out.println("Host = " + Host);

            //getPort
            int  Port = url.getPort();
            System.out.println("Port = " + Port);

            //getPath
            String  Path = url.getPath();
            System.out.println("Path = " + Path);

            //getQuery
            String Query = url.getQuery();
            System.out.println("Query = " + Query);

            //getFile
            String File = url.getFile();
            System.out.println("File = " + File);

            //getRef
            String Ref = url.getRef();
            System.out.println("Ref = " + Ref);

        } catch (Exception e){ 
            System.out.println(e); 
        } 
    } 
} 
