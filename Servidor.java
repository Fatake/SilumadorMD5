import java.io.*;
import java.net.*;

public class Servidor {  
	public static void main(String[] args) {
		ServerSocket socketServer = null;
		Socket socketDespachador = null;

		try {
			socketServer = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("Escuchando en el puerto 9999: \n" + socketServer);
        
		while(true){
			try {
				socketDespachador = socketServer.accept();
				System.out.println("Nueva conexion aceptada: " + socketDespachador);
				//Escucha Peticiones
				new GestorPeticion(socketDespachador).start();
				socketDespachador = null;
			} 
			catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	
}

class GestorPeticion extends Thread {
	BufferedReader entrada = null;
	PrintWriter salida = null;
	Socket socket;

	public GestorPeticion(Socket socket){
		this.socket = socket;
	}

	public void run(){ 
		try{
			entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
	  
			while (true){  
				String str = entrada.readLine();
				System.out.println("Recibo: " + str);
				salida.println(str);
				if(str.equals("Adios")) break;
			}
			
			salida.close();
			entrada.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(-1);
		}
	}
}

