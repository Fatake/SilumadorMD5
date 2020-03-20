import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.security.SecureRandom;
import java.math.BigInteger;

public class Servidor {  
	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	public static void main(String[] args) {
		Servidor aux = new Servidor();
		ServerSocket socketServer = null;
		Socket socketDespachador = null;
		LectorArchivo lec = new LectorArchivo();
		if ((aux.usuarios = lec.procesaArchivo("usuarios.dat"))== null) {
			System.out.println("Error al leer usuarios.dat");
			System.exit(-1);
		}
		aux.imprimeUsuarios();
		try {
			socketServer = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("Escuchando: " + socketServer);
        
		while(true){
			try {
				socketDespachador = socketServer.accept();
				System.out.println("Nueva conexion aceptada: " + socketDespachador);
				//Escucha Peticiones
				new GestorPeticion(socketDespachador,aux.usuarios).start();
				socketDespachador = null;
			} 
			catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	private void imprimeUsuarios(){
		System.out.println("Usuarios en el Servidor\n<------------------------>");
		for (Usuario usuario : usuarios) {
			System.out.println(usuario.toString());
		}
		System.out.println("<------------------------>\n");
	}
}

class GestorPeticion extends Thread {
	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	BufferedReader entrada = null;
	PrintWriter salida = null;
	Socket socket;

	public GestorPeticion(Socket socket,ArrayList<Usuario> usuarios){
		this.socket = socket;
		this.usuarios = usuarios;
	}

	public void run(){
		System.out.print("\033[H\033[2J");  
		System.out.flush();
		System.out.println("\n\n<----------------->"); 
		Usuario user;
		int indexUser = 0;
		String textoAleatorio = "";
		String textoMezclado  = "";
		try{
			entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			while (true){
				//Lee lo que se reciba en el Socket
				String str = entrada.readLine();
				//Separa lo que se lee
				String aux[] = str.split(",");
				if (aux[0].startsWith("us")) {//Recibe Usuaro
					System.out.println("Usuario "+aux[1]+" Recibido\nBuscando...");
					indexUser = buscaUsuario(aux[1]);
					user = usuarios.get(indexUser);
					if (indexUser == -1) {
						System.out.println("Usuario no Encontrado");
					}else{
						Mezclador mes = new Mezclador();
						System.out.println("Usuario Encontrado");
						textoAleatorio = generaTexto();
						System.out.println("Texto Generado:\n"+textoAleatorio+"\n");
						//Envia texto Aleatorio
						salida.println("ms,"+textoAleatorio);
						textoMezclado = mes.mezcla(textoAleatorio, user.getPass());
						System.out.println("Texto Mezclado:\n"+textoMezclado+"\n");
					}
				}else if (aux[0].startsWith("md")) {//Recibe md
					MD5 gen = new MD5();
					String md5cli = aux[1];
					String md5ser = gen.getMD5(textoMezclado);
					System.out.println("MD5Cli:\n"+md5cli+"\n");
					System.out.println("MD5Ser:\n"+md5ser+"\n");
				}

				System.out.println("-> " + str);
				if(str.equals("fn")){
					System.out.println("Cerrando Coneccion");
					break;
				}
			}
			
			salida.close();
			entrada.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private int buscaUsuario(String userName){
		int posicion = -1;
		for (int i = 0; i < usuarios.size(); i++) {
			Usuario user = usuarios.get(i);
			String nombre = user.getName();
			if (nombre.equals(userName)) {
				posicion = i;
				break;
			}
		}
		return posicion;
	}

	private String generaTexto(){
		SecureRandom random = new SecureRandom();
 		String text = new BigInteger(586, random).toString(32);
 		return text;
	}
}

