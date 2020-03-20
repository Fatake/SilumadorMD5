import java.io.*;
import java.net.*;
import java.util.ArrayList;

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
		try{
			entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			while (true){
				int indexUser = 0;
				String str = entrada.readLine();
				String aux[] = str.split(",");
				if (aux[0].startsWith("us")) {//Recibe Usuaro
					System.out.println("Usuario "+aux[1]+" Recibido\nBuscando...");
					indexUser = buscaUsuario(aux[1]);
					if (indexUser == -1) {
						System.out.println("Usuario no Encontrado");
					}else{
						System.out.println("Usuario Encontrado");
					}
				}

				System.out.println("-> " + str);
				salida.println(str);
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
}

