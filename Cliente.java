import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Cliente {
	public static void clearScreen() {  
		System.out.print("\033[H\033[2J");  
		System.out.flush();  
	}  
	private Usuario menu(){
		Scanner scaner = new Scanner(System.in);
		System.out.println("\tMenu Login\nIngrese una opcion");
		System.out.println("1)Iniciar Session");
		System.out.println("2)Salir\n<------------------------->");
		while (true) {
			Integer opcion = scaner.nextInt();
			switch (opcion) {
				case 1:
					clearScreen();
					System.out.print("Ingrese usuario\n->");
					String nombre = scaner.nextLine();
					nombre = scaner.nextLine();
					System.out.print("Ingrese Contraseña\n->");
					String password = scaner.nextLine();
					scaner.close();
					Usuario user = new Usuario(nombre, password);
					return user;
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
		String mensajeAleatorio;
		Mezclador mes = new Mezclador();
		String textoMezclado;

		System.out.println("Conectando a: "+servidor+"\nPuerto: "+puerto+"\n");
		try{
			Usuario user = aux.menu();
			//Abre el socket
			Socket socket = new Socket(servidor,puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter salida = new PrintWriter( new OutputStreamWriter(socket.getOutputStream() ),true );
			clearScreen();
			//Envia Usuario
			salida.println("us,"+user.getName());
			
			//Recibe mensaje Aleatorio
			
			mensajeAleatorio = entrada.readLine();
			String str[] = mensajeAleatorio.split(",");
			mensajeAleatorio = str[1];

			textoMezclado = mes.mezcla(mensajeAleatorio, user.getPass());

			System.out.println("Mensaje aleatorio: "+mensajeAleatorio+"\n");
			System.out.println("Mesclado: "+textoMezclado);
			//Envia mensaje de Salida
			salida.println("fn");
			//Termina coneccion
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
