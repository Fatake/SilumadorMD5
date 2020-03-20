import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/*
 * Clase Lector Archivo
 */
public class LectorArchivo {
    private ArrayList<String> contenidoArchivo = new ArrayList<String>();
    public LectorArchivo() { };
    /*
     * LeerArchivo Guarda en forma de String todas las lineas
     * leidas del archivo en el atributo contenidoArchivo
     * recibe:
     * * String fileName
     */
    private boolean leerArchivo(String fileName){
        File archivo = new File (fileName);
        FileReader fr = null;
        BufferedReader br = null;
        String linea;

        // Lectura del fichero
        try {
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // hacer una lectura comoda (disponer del metodo readLine()).
            while ((linea = br.readLine()) != null){
                this.contenidoArchivo.add(linea);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{                    
               if( null != fr ){   
                  fr.close();     
               }                  
            }catch (Exception e2){ 
               e2.printStackTrace();
               return false;
            }
        }//Fin lectura archivo
        return true;
    }

    /*
     * procesaArchivo 
     * convietiendo los campos a floats
     * Retorna:
     * ArrayList<Float[]>
     */
    public ArrayList<Usuario> procesaArchivo(String fileName){
        if (leerArchivo(fileName) == false) {
            return null;
        }
        if (contenidoArchivo.isEmpty()) {
            return null;
        }
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        for (int i = 0; i < contenidoArchivo.size(); i++) {
            String aux = contenidoArchivo.get(i);
            String aux2[] = aux.split(",");
            usuarios.add(new Usuario(aux2[0], aux2[1]));
        }
        contenidoArchivo = null;
        return usuarios;
    }
}
