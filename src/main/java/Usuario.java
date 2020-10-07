import org.json.simple.*;

public class Usuario {
    public String Documento;
    public String Nombre;
    public String Apellido;
    public String Correo;
    public String Contrasenna;

    public Usuario(String documento, String nombre, String apellido, String correo, String contrasenna) {
        Documento = documento;
        Nombre = nombre;
        Apellido = apellido;
        Correo = correo;
        Contrasenna = contrasenna;
    }

    public Usuario(JSONObject toJavaObj){
        JSONObject usuario = (JSONObject) toJavaObj.get("usuario");
        this.Documento = (String) usuario.get("cedula");
        this.Nombre = (String) usuario.get("nombre");
        this.Apellido = (String) usuario.get("apellido");
        this.Correo = (String) usuario.get("correo");
        this.Contrasenna = (String) usuario.get("contrasenna");
    }

    public JSONObject toJSONObj(){
        JSONObject usuarioDetials = new JSONObject();
        usuarioDetials.put("cedula", Documento);
        usuarioDetials.put("nombre", Nombre);
        usuarioDetials.put("apellido", Apellido);
        usuarioDetials.put("correo", Correo);
        usuarioDetials.put("contrasenna", Contrasenna);


        JSONObject usuarioObj = new JSONObject();
        usuarioObj.put("usuario", usuarioDetials);

        return usuarioObj;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Documento='" + Documento + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Apellido='" + Apellido + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Contrasenna='" + Contrasenna + '\'' +
                '}';
    }
}
