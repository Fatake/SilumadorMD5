import java.net.*;
import java.io.*;

public class Cliente {
	public static void main(String[] args) {
		//se obtiene el servidor
		String servidor = args[0];
		//se obtiene el puerto de conexion
		int puerto = Integer.parseInt(args[1]);
		System.out.println("Enviando peticion a: "+servidor+"\nPor el puerto: "+puerto);
		try{
			Socket socket = new Socket(servidor,puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter salida = new PrintWriter( new OutputStreamWriter(socket.getOutputStream() ),true );
			salida.println("Prueba de envio de mensaje");
			System.out.println(entrada.readLine());
			salida.println("Adios");
			socket.close();
		}
		catch(UnknownHostException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
