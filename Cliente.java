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
	private static String encriptar(String s) throws UnsupportedEncodingException{
        return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));
    }
    
    private static String desencriptar(String s) throws UnsupportedEncodingException{
        byte[] decode = Base64.getDecoder().decode(s.getBytes());
        return new String(decode, "utf-8");
    }
	public static void main(String[] args) {
		//se obtiene el servidor
		String servidor = args[0];
		//se obtiene el puerto de conexion
		int puerto = Integer.parseInt(args[1]);
		Cliente aux = new Cliente();
		String mensajeAleatorio;
		Mezclador mes = new Mezclador();
		MD5 gen = new MD5();
		String textoMezclado = "";

		System.out.println("Conectando a: "+servidor+"\nPuerto: "+puerto+"\n");
		try{
			Usuario user = aux.menu();
			//Abre el socket
			Socket socket = new Socket(servidor,puerto);

			//Habilita Escuchadores de entrada y salida
			BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter salida = new PrintWriter( new OutputStreamWriter(socket.getOutputStream() ),true );
			
			clearScreen();
			//Envia Usuario
			salida.println(encriptar("us,"+user.getName()));
			
			//Recibe mensaje Aleatorio
			mensajeAleatorio = desencriptar(entrada.readLine());
			String str[] = mensajeAleatorio.split(",");
			if (str[0].equals("un")) {
				System.out.println("Usuario no registrado u.u");
				socket.close();
				System.exit(1);
			}
			mensajeAleatorio = str[1];

			//Mezcla el mensaje
			textoMezclado = mes.mezcla(mensajeAleatorio, user.getPass());

			//Genera MD5 y envia
			String md5cli = gen.getMD5(textoMezclado);
			salida.println(encriptar("md,"+md5cli));

			String confirma = desencriptar(entrada.readLine());
			if (confirma.equals("cn")) {
				System.out.println("Conectado con Exito n.n");
			}else{
				System.out.println("Contraseña Incorrecta");
			}

			//Envia mensaje de Salida
			salida.println(encriptar("fn"));

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
