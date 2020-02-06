package edu.escuelaing.arep.reto1;

import java.net.*;
import java.io.*;

public class HttpServer {

    private int puerto;
    private Socket clientSocket;
    private ServerSocket servSocket;
    private String serverMessage;

    /**
     * Class constructor
     * 
     * @throws IOException
     */
    public HttpServer() {
        this.puerto = getPort();
        try {
            this.servSocket = new ServerSocket(this.puerto);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * This method starts the server
	 */
	public void startServer() {
		while(true) {
			try {
                this.clientSocket=this.servSocket.accept();
				
				BufferedReader entrada= new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
				
				String path=this.getRequest(entrada);
				
                OutputStream socketClient=clientSocket.getOutputStream();
                
				if("/webPage".equals(path)) {
					webPage(socketClient);
				}
				else {
					pageNotFound(socketClient);
				}	
				this.clientSocket.close();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
		}
	}
	
	/**
	 * Method to attend users requests
	 * @param entrada User request
	 * @return The path of the request (web, image, ...)
	 */
	public String getRequest(BufferedReader entrada) {
		
		boolean notExit=true;
		String path=null;
		
		try {
			while((this.serverMessage=entrada.readLine())!=null && notExit) {
				
				if(this.serverMessage.contains("GET")) {
					String[]dir=this.serverMessage.split(" ");
					path=dir[1];
					notExit=false;
				}
			}
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return path;
	}
	
	
	/**
	 * Method to attend the client web request
	 * @param socketClient ClientSocket OutputStream
	 */
	private void webPage (OutputStream socketClient) {
        PrintWriter res=new PrintWriter(socketClient, true);
        String outputLine="HTTP/1.1 200 \r\n\r\n<html><head><title>HTML con Imagen</title></head>"
        +"<body style = \"background-color:rgb(150,206,209);\"><h1><center> HttpServer </center></h1>"
        +"<div align = \"center\"><h2 style = \"color:darkred;\"> Imagen Iron Man </h2>"
        +"<img src = \"https://vignette.wikia.nocookie.net/disney/images/9/96/Iron-Man-AOU-Render.png/revision/latest?cb=20180410032118&path-prefix=es\" width = \"500px\" height = \"500px\"></div></html></body>";
        setOutput(res,outputLine,socketClient);
	}
    
	
	/**
	 * Method to handle wrong client requests
	 * @param socketClient
	 */
	private void pageNotFound(OutputStream socketClient) {

        PrintWriter res = new PrintWriter(socketClient, true);
        String outputLine="HTTP/1.1 404 \r\n\r\n<html><head><title>Pagina No Encontrada</title></head>"
        +"<body><center><h1> 404 </h1></center> <div align = \"center\"><h2> ERROR: Pagina No encontrada </h2></div></body></html>";
        setOutput(res,outputLine,socketClient);
    }
	
	
	/**
	 * Method that give the response to the client request
	 * @param res Response writer
	 * @param outputLine String response (page)
	 */
	private void setOutput(PrintWriter res, String outputLine, OutputStream socketClient) {
		res.println(outputLine);
        res.flush();
        res.close();

        try {
			socketClient.flush();
			socketClient.close();
		} 
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Method to close the server socket
	 */
	public void closeServerSocket() {
		try {
			this.servSocket.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to establish the port
	 * @return The port
	 */
	public static int getPort() {
        if (System.getenv("PORT") != null) {
            return new Integer(System.getenv("PORT"));
        }
        return 35000; 
    }

	/**
	 * Method that starts the program
	 * @param args No args
	 */
	public static void main(String args[]) {
		HttpServer server=new HttpServer();
		System.out.println("Iniciando HttpServer");
		server.startServer();
		server.closeServerSocket();
	}
	
}