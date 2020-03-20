import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Cliente {
	private int menu(){
		System.out.println("Menu Login\nIngrese una opcion");
		System.out.println("1)Iniciar Session");
		System.out.println("2)Salir\n<------------------------->");
		while (True) {
			Integer opcion = Integer.parseInt((new Scanner(System.in)).nextLine());
			switch (opcion) {
				case 1:
					return 1;
				case 0:
					System.out.println("Adios u.u");
					System.exit(1);
				default:
					System.out.println("Opcion no disponible");
			}
		}
	}
	public static void main(String[] args) {
		//se obtiene el servidor
		String servidor = args[0];
		//se obtiene el puerto de conexion
		int puerto = Integer.parseInt(args[1]);
		Cliente aux = new Cliente();
		System.out.println("Enviando peticion a: "+servidor+"\nPor el puerto: "+puerto);
		try{
			//Abre el socket
			Socket socket = new Socket(servidor,puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter salida = new PrintWriter( new OutputStreamWriter(socket.getOutputStream() ),true );
			
			int opcion = aux.menu();
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
