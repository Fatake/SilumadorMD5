public class Usuario {
    private String nombre;
    private String password;
    public Usuario (String nombre, String password){
        this.nombre = nombre;
        this.password = password;
    }
    public String getName(){
        return this.nombre;
    }
    public String getPass(){
        return this.password;
    }
    public String toString(){
        String aux = "Nombre: "+this.nombre+" Contraseña: "+this.password;
        return aux;
    }
}
