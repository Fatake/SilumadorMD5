import java.net.*;
import java.io.*;

public class ClienteEco {
	public static void main(String[] args) {
		//se obtiene el servidor
		String servidor = args[0];
		//se obtiene el puerto de conexion
		int puerto = Integer.parseInt(args[1]);
		//String servidor = "localhost";
		//int puerto = 9999;
		try{
			Socket socket = new Socket(servidor,puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter salida = new PrintWriter( new OutputStreamWriter(socket.getOutputStream() ),true );
			salida.println("prueba de eco");
			System.out.println(entrada.readLine());
			salida.println("bye");
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
