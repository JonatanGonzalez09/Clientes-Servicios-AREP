package edu.escuelaing.arep.reto1;

import java.net.*;
import java.io.*;

public class HttpServer {

	private int puerto;
	private Socket clientSocket;
	private static ServerSocket serverSocket;
	private String mensaje;

	/**
	 * Clase constructor
	 * 
	 * @throws IOException
	 */
	public HttpServer() {
		this.puerto = getPort();
		try {
			HttpServer.serverSocket = new ServerSocket(this.puerto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para empezar a correr el servidor
	 */
	public void run() {
		while (true) {
			try {
				this.clientSocket = HttpServer.serverSocket.accept();

				BufferedReader entrada = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

				String path = this.Solicitud(entrada);

				OutputStream socketClient = clientSocket.getOutputStream();

				if ("/webPage".equals(path)) {
					webPage(socketClient);
				} else {
					pageNotFound(socketClient);
				}
				this.clientSocket.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
		}
	}

	/**
	 * 
	 * @param rutaRequerida la ruta que desea solicitar el usuario.
	 * @return retorna la ruta completa para resolver el direccionamiento
	 */
	public String Solicitud(BufferedReader rutaRequerida) {
		boolean fail = true;
		String rutaCompl = null;
		try {
			while ((this.mensaje = rutaRequerida.readLine()) != null && fail) {

				if (this.mensaje.contains("GET")) {
					String[] dir = this.mensaje.split(" ");
					rutaCompl = dir[1];
					fail = false;
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return rutaCompl;
	}

	/**
	 * Metodo para resolver las solicitudes del cliente
	 * @param socketClient 
	 */
	private void webPage(OutputStream socketClient) {
		PrintWriter res = new PrintWriter(socketClient, true);
		String outputLine = "HTTP/1.1 200 \r\n\r\n<html><head><title>HTML con Imagen</title></head>"
				+ "<body style = \"background-color:rgb(150,206,209);\"><h1><center> HttpServer </center></h1>"
				+ "<div align = \"center\"><h2 style = \"color:darkred;\"> Imagen Iron Man </h2>"
				+ "<img src = \"https://vignette.wikia.nocookie.net/disney/images/9/96/Iron-Man-AOU-Render.png/revision/latest?cb=20180410032118&path-prefix=es\" width = \"500px\" height = \"500px\"></div></html></body>";
		setOutput(res, outputLine, socketClient);
	}

	/**
	 * Metodo para cuando las solicitudes del cliente no se encuentran. 
	 * 
	 * @param socketClient
	 */
	private void pageNotFound(OutputStream socketClient) {
		PrintWriter res = new PrintWriter(socketClient, true);
		String outputLine = "HTTP/1.1 404 \r\n\r\n<html><head><title>Pagina No Encontrada</title></head>"
				+ "<body><center><h1> 404 </h1></center> <div align = \"center\"><h2> ERROR: Pagina No encontrada </h2></div></body></html>";
		setOutput(res, outputLine, socketClient);
	}

	/**
	 * Metodo para mostrar las solicitudes al cliente.
	 * @param res Las respuestas que se deben enviar.
	 * @param outputLine La pagina que se debe mostrar.
	 * @param socketClient 
	 */
	private void setOutput(PrintWriter res, String outputLine, OutputStream socketClient) {
		res.println(outputLine);
		res.flush();
		res.close();

		try {
			socketClient.flush();
			socketClient.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Metodo que establece el puerto para la conexion.
	 * @return el puerto 35000
	 */
	public static int getPort() {
		if (System.getenv("PORT") != null) {
			return new Integer(System.getenv("PORT"));
		}
		return 35000;
	}

	/**
	 * Programa principal
	 * @param args
	 */
	public static void main(String args[]) {
		HttpServer server = new HttpServer();
		System.out.println("Iniciando HttpServer");
		server.run();
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}