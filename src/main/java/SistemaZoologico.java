import org.json.simple.*;
import org.json.simple.parser.*;

import java.util.*;
import java.io.*;

public class SistemaZoologico {
    public static Usuario usuarioActual = null;
    public static Scanner input = new Scanner(System.in);
    public static ArrayList<Usuario> usuarios = new ArrayList<>();

    public static ArrayList<Animal> animales = new ArrayList<>();
    public static ArrayList<Bioma> biomas = new ArrayList<>();
    public static ArrayList<Habitat> habitats = new ArrayList<>();
    public static ArrayList<Profesional> profenales = new ArrayList<>();
    public static ArrayList<Tecnico> tecnicos = new ArrayList<>();
    public static ArrayList<ZooAmigo> zooAmigos = new ArrayList<>();
    public static ArrayList<Zoologico> zoologicos = new ArrayList<>();

    public static void main(String[] args) {
        cargarUsuario(); //Llama a metodo para cargar todos los usuarios registrados desde src/database/usuarios.json
        label: while (true) {
            System.out.println("----------------------------------");
            System.out.println("Este es el sistema de Zoologicos. \nPor favor, selecione alguna de las siguientes opciones: ");
            System.out.println("1. Ingresar.");
            System.out.println("2. Registrarse. \n");
            String opcion = input.next();
            System.out.println();
            switch (opcion){
                case "1":
                    if (usuarios.isEmpty()){
                        System.out.println("Aun no se encuentran usuarios registrados");
                        break;
                    }
                    ingresar(); //la unica forma de salir de este metodo, es que el usuario ingrese exitosamente.
                    break label;
                case "2":
                    registrar(); //Una vez se registre, se vuelve a preguntar si desea ingresar.
                    break;
            }
            System.out.println("----------------------------------\n");
        }

        //...continua el programa.
        //Menu principal

        System.out.println("\nBienvenido, "+ usuarioActual.Nombre+ "...");
        label: while (true) {
            System.out.println("----------------------------------");
            System.out.println("Este es el Menu Principal. \nPor favor, selecione alguna de las siguientes opciones: \n");
            System.out.println("1. Administrar.");
            System.out.println("2. Busqueda.");
            System.out.println("3. Diagnostico de inconsistencias.");
            System.out.println("4. Guardar.");
            System.out.println("0. Salir y cancelar.");
            String opcion = input.next();
            System.out.println();
            switch (opcion){
                case "1":
                    //administra();
                    break;
                case "2":
                    //busqueda();
                    break;
                case "3":
                    //diagnostico();
                    break;
                case "4":
                    //guardar();
                    break;
                case "0":
                    //salircancelar();
                    break label;
            }
            System.out.println("----------------------------------");
        }

    }

    public static void cargarUsuario(){
        File newFile = new File("src/database/usuarios.json");
        if (newFile.length() == 0) {
            System.out.println("MENSAJE: aún no hay ningún usuario registrado\n"); //Revisa si el archico usuarios.json esta vacio
            return;
        }

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src/database/usuarios.json")) {
            Object obj = jsonParser.parse(reader);

            JSONArray usuariosJava = (JSONArray) obj; // lee el JSONArray del archivo
            usuariosJava.forEach(usu -> parseUsuarioObj( (JSONObject) usu)); //por cada usuarioJSON del JSONArray, lo convierte a JAVAusuario y lo agrega a la lista de usuarios



        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void parseUsuarioObj( JSONObject jsonObject){
        Usuario nuevoUsu = new Usuario(jsonObject);
        usuarios.add(nuevoUsu);
    }

    public static void ingresar(){
        label: while (true){
            System.out.println("-------------------------");
            System.out.println("Por favor ingrese documento o correo:");
            String id = input.next();
            System.out.println("Por favor ingrese su contrasenna:");
            String contrasenna = input.next();

            for (Usuario usuario : usuarios) { //Evalua si el documento o correo ingresados coincide con la contrasenna
                if (usuario.Documento.equals(id) || usuario.Correo.equals(id)){
                    if (usuario.Contrasenna.equals(contrasenna)){
                        usuarioActual = usuario;
                        break label;
                    }
                }
            }
            if (usuarioActual == null){
                System.out.println("ERROR: los datos suministrados no coinciden con ninguno de los usuarios registrados");
            }
            System.out.println("-------------------------");
        }
    }

    public static void registrar(){
        //Dentro de este metodo se guardan los usuarios en usuarios.json.
        System.out.println("-------------------------");
        System.out.print("Ingrese documento del nuevo usuario: ");
        String documento = input.next();
        if (documento.charAt(0) == '-'){
            System.out.println("Documento invalido.");
        }
        System.out.print("Ingrese nombre del nuevo ususario: ");
        String nombre = input.next();
        System.out.print("Ingrese apellido del nuevo ususario: ");
        String apellido = input.next();
        System.out.print("Ingrese correo electronico: ");
        String correo = input.next();
        System.out.print("Ingrese su contrasenna: ");
        String contra = input.next();

        for (Usuario usuario : usuarios) {
            if (usuario.Documento.equals(documento) || usuario.Correo.equals(correo)){ //Evalua si ya existe un usuario registrado con el documento o correo ingresados
                System.out.println("\nERROR: ya existe un usuario con este documento o correo");
                return;
            }
        }

        Usuario nuevoUsuario = new Usuario(documento, nombre, apellido, correo, contra);
        usuarios.add(nuevoUsuario); //adiciona el nuevo usuario a la lista de usuarios
        System.out.println("\nNuevo registro realizado exitosamente.");


        JSONArray usuariosJSON = new JSONArray(); //Crea un JSONArray y usando el metodo para pasar de usuarioJAVA a usuario JSON los adiciona a un dicho array
        for (Usuario usuario : usuarios) {
            usuariosJSON.add(usuario.toJSONObj());
        }
        try (FileWriter file = new FileWriter("src/database/usuarios.json")) {

            file.write(usuariosJSON.toJSONString()); //guarda el JSONArray con los JSONusuarios en la carpeta del pc
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
