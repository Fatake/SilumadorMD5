import java.io.*;
import java.net.*;

public class ServidorEco {  
	public static void main(String[] args) {
		ServerSocket ss = null;
		Socket s = null;

		try {
			ss = new ServerSocket(9999);
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("Escuchando en el puerto 9999: " + ss);
        
		while(true){
			try {
				s = ss.accept();
				System.out.println("Nueva conexion aceptada: " + s);
				new GestorPeticion(s).start();
				s = null;
			} 
			catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		/*
		try {
			s = ss.accept();
			System.out.println("Nueva conexion aceptada: " + s);
			new GestorPeticion(s).start();
			s = null;
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		*/
		
	}
}


class GestorPeticion extends Thread {
	BufferedReader entrada = null;
	PrintWriter salida = null;
	Socket s;

	public GestorPeticion(Socket s){
		this.s = s;
	}

	public void run(){ 
		try{
			entrada = new BufferedReader(new InputStreamReader(s.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
      
			while (true){  
				String str = entrada.readLine();
				System.out.println("Recibo: " + str);
				salida.println(str);
				if(str.equals("bye")) break;
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
