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
    public static ArrayList<Profesional> profesionales = new ArrayList<>();
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
                    administrar();
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
            System.out.println("-------------------------------------");
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

    public static void administrar(){
        label: while (true) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, selecione la clase que desea administar: \n");
            System.out.println("1. Zoologicos.");
            System.out.println("2. Bioma.");
            System.out.println("3. Habitat");
            System.out.println("4. Animal.");
            System.out.println("5. Tecnico.");
            System.out.println("6. Profesional.");
            System.out.println("7. ZooAmigo.");
            System.out.println("0. Regresar a Menu Principal.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion){
                case 1:
                    CRUDzoologico(opcion);
                    break;
                case 2:
                    CRUDbioma(opcion);
                    break;
                case 3:
                    CRUDhabitat(opcion);
                    break;
                case 4:
                    CRUDanimal(opcion);
                    break;
                case 5:
                    CRUDtecnico(opcion);
                    break;
                case 6:
                    CRUDprofesional(opcion);
                    break;
                case 7:
                    CRUDzooamigo(opcion);
                    break;
                case 0:
                    break label;
            }
            System.out.println("----------------------------------");
        }
    }

    public static String CRUDclases(int i){
        int opcion = i - 1;
        String accion;
        String[] clases = new String[] {"Zoologico", "Bioma", "Habitat", "Animal", "Tecnico", "Profesional", "ZooAmigo"};

        do {
            System.out.println("-----------------------------------------");
            System.out.println("\nIndique la accion que desea realizar:\n");
            System.out.println("1. Ver " + clases[opcion]);
            System.out.println("2. Crear " + clases[opcion]);
            System.out.println("3. Editar " + clases[opcion]);
            System.out.println("4. Eliminar " + clases[opcion] + "\n");

            accion = input.next();
            System.out.println("----------------------------------");
        } while (!accion.equals("1") && !accion.equals("2") && !accion.equals("3") && !accion.equals("4"));

        return accion;
    }

    public static void CRUDzoologico(int opcion){
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (zoologicos.isEmpty()){
                System.out.println("Aun no se encuentran zoologicos registrados");
                return;
            }
        }
        switch (accion){
            case "1":
                for (Zoologico zoologico : zoologicos) {
                    System.out.println(zoologico);
                }
                break;
            case "2":
                crearZoologico();
                break;
            case "3":
                editarZoologico();
                break;
            case "4":
                //eliminarZoologico();
                break;
        }
    }

    public static void crearZoologico(){
        System.out.print("Ingrese NIT del nuevo zoologico: ");
        String nuevoNIT = input.next();
        for (Zoologico zoologico : zoologicos) {
            if (zoologico.nit.replace(".", "").equals(nuevoNIT.replace(".", ""))){
                System.out.println("ERROR: Ya existe un zoologico registrado con este NIT.");
                return;
            }
        }
        System.out.print("Ingrese el nombre del nuevo zoologico: ");
        String nuevoNombre = input.next();
        System.out.print("Ingrese las siglas del nuevo zoologico: ");
        String nuevoSiglas = input.next();
        for (Zoologico zoologico : zoologicos) {
            if (zoologico.siglas.equalsIgnoreCase(nuevoSiglas)){
                System.out.println("ERROR: Ya existe un zoologico registrado con estas siglas.");
                return;
            }
        }
        System.out.print("Ingrese la ciudad del nuevo zoologico: ");
        String nuevaCiudad = input.next();

        Zoologico nuevoZoo = new Zoologico(nuevoNIT, nuevoNombre, nuevoSiglas, nuevaCiudad);
        zoologicos.add(nuevoZoo);
        System.out.println("\nMENSAJE: Nuevo zoologico registrado con exito.\n" +
                "IMPORTANTE: Si desea fijar las relaciones del nuevo zoologico, debe ingresar a la opcion 'Editar' del menu anterior.");
    }

    public static void editarZoologico(){

        System.out.println("------------------------------------------");
        System.out.println("Estas son las claves unicas del zoologico: \n");
        System.out.println("1. Seleccionar por NIT");
        System.out.println("2. Seleccionar por Siglas\n");
        String opcion = input.next();
        if (!opcion.equals("1") && !opcion.equals("2")){
            System.out.println("Opcion invalidad");
            return;
        }
        Zoologico zoologico = null;
        if (opcion.equals("1")){
            System.out.print("Ingrese NIT: ");
            String opcion2 = input.next();
            for (Zoologico zoo : zoologicos){
                if (zoo.nit.replace(".", "").equals(opcion2.replace(".", ""))){
                    zoologico = zoo;
                }
            }
        } else {
            System.out.print("Ingrese siglas: ");
            String opcion2 = input.next();
            for (Zoologico zoo : zoologicos){
                if (zoo.siglas.equalsIgnoreCase(opcion2)){
                    zoologico = zoo;
                }
            }
        }
        if (zoologico == null){
            System.out.println("No se encontro zoologico con estas especificaciones.");
            return;
        }

        System.out.println("NIT: "+ zoologico.nit);
        String nuevoNIT = input.nextLine();
        if (!nuevoNIT.equals("")){
            zoologico.nit = nuevoNIT;
        }
        input.nextLine();
        System.out.println("Nombre: "+ zoologico.nombre);
        String nuevoNombre = input.nextLine();
        if (!nuevoNombre.equals("")){
            zoologico.nombre = nuevoNombre;
        }
        input.nextLine();
        System.out.println("Siglas: "+ zoologico.siglas);
        String nuevoSiglas = input.nextLine();
        if (!nuevoSiglas.equals("")){
            zoologico.siglas = nuevoSiglas;
        }
        input.nextLine();
        System.out.println("Ciudad: "+ zoologico.siglas);
        String nuevoCiudad = input.nextLine();
        if (!nuevoCiudad.equals("")){
            zoologico.ciudad = nuevoCiudad;
        }
        input.nextLine();

        System.out.println("------------------------------------------");
    }

    public static void CRUDbioma(int opcion){
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (biomas.isEmpty()) {
                System.out.println("Aun no se encuentran biomas registrados");
                return;
            }
        }
        switch (accion){
            case "1":
                for (Bioma  bioma : biomas) {
                    System.out.println(bioma);
                } break;
            case "2":
                crearBioma();
                break;
            case "3":
                //editarBioma();
                break;
            case "4":
                //eliminarBioma();
                break;
        }
    }

    public static void crearBioma(){
        System.out.print("Ingrese ID del nuevo bioma: ");
        int nuevoID = input.nextInt();
        for (Bioma bioma : biomas) {
            if (bioma.id == nuevoID){
                System.out.println("ERROR: Ya existe un bioma registrado con este ID.");
                return;
            }
        }
        System.out.print("Ingrese la temperatura del nuevo bioma en C°: ");
        double nuevaTemp = input.nextDouble();
        System.out.print("Ingrese la humedad del nuevo bioma: ");
        String nuevaHum = input.next();
        System.out.print("Ingrese el tipo del nuevo bioma: ");
        String nuevoTipo = input.next();

        Bioma nuevoBio = new Bioma(nuevoID, nuevaTemp, nuevaHum, nuevoTipo);
        biomas.add(nuevoBio);

        System.out.println("\nMENSAJE: Nuevo bioma registrado con exito.\n" +
                "IMPORTANTE: Si desea fijar las relaciones del nuevo bioma, debe ingresar a la opcion 'Editar' del menu anterior.");
    }

    public static void CRUDhabitat(int opcion){
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (habitats.isEmpty()) {
                System.out.println("Aun no se encuentran habitats registrados");
                return;
            }
        }
        switch (accion){
            case "1":
                for (Habitat habitat : habitats) {
                    System.out.println(habitat);
                } break;
            case "2":
                crearHabitat();
                break;
            case "3":
                //editarHabitat();
                break;
            case "4":
                //eliminarHabitat();
                break;
        }
    }

    public static void crearHabitat() {
        System.out.print("Ingrese ID del nuevo habitat: ");
        int nuevoID = input.nextInt();
        for (Habitat habitat : habitats) {
            if (habitat.id == nuevoID){
                System.out.println("ERROR: Ya existe un habitat registrado con este ID.");
                return;
            }
        }
        System.out.print("Ingrese el tipo de nuevo habitat: ");
        String nuevoSuelo = input.next();
        System.out.print("Ingrese la vegetacion del nuevo habitat: ");
        String nuevaVeg = input.next();
        System.out.print("Ingrese el tipo de jaula nuevo habitat: ");
        String nuevoJau = input.next();

        Habitat nuevoHab = new Habitat(nuevoID, nuevoSuelo, nuevaVeg, nuevoJau);
        habitats.add(nuevoHab);

        System.out.println("\nMENSAJE: Nuevo habitat registrado con exito.\n" +
                "IMPORTANTE: Si desea fijar las relaciones del nuevo habitat, debe ingresar a la opcion 'Editar' del menu anterior.");
    }

    public static void CRUDanimal(int opcion){
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (animales.isEmpty()){
                System.out.println("Aun no se encuentran animales registrados");
                return;
            }
        }
        switch (accion){
            case "1":
                for (Animal animal : animales) {
                    System.out.println(animal);
                }
                break;
            case "2":
                crearAnimal();
                break;
            case "3":
                //editarAnimal();
                break;
            case "4":
                //eliminarAnimal();
                break;
        }
    }
    public static void crearAnimal(){
        System.out.println("            _   _\n" +
                "           (.)_(.)\n" +
                "        _ (   _   ) _\n" +
                "       / \\/`-----'\\/ \\\n" +
                "     __\\ ( (     ) ) /__\n" +
                "     )   /\\ \\._./ /\\   (\n" +
                "      )_/ /|\\   /|\\ \\_(");
        System.out.println();
        System.out.println("ERROR: Ya existe un animal registrado con este ID.");
        int id = input.nextInt();
        for (Animal animal :
                animales) {
            if (animal.id==id){
                System.out.println("El ID ya existe en el sistema");
                return;
            }
        }
        if (id<0){
            System.out.println("El ID es incorrecto");
            return;
        }
        System.out.println("Ingrese la especie: Ej: Leon,Foca,etc");
        String especie = input.next();
        System.out.println("Ingrese el nivel de agresiviad: Del 1 al 5");
        int agresividad = input.nextInt();
        if (agresividad<1 || 5<agresividad){
            System.out.println("Nivel de agresividad invalido");
            return;
        }
        System.out.println("Ingrese la alimentación del animal: Carnivoro, Hervivoro, Omnivoro");
        String alimentacion = input.next();
        Animal animalNuevo = new Animal(id,especie,agresividad,alimentacion);
        animales.add(animalNuevo);
        System.out.println("\nMENSAJE: Nuevo animal registrado con exito.\n" +
                "IMPORTANTE: Si desea fijar las relaciones del nuevo animal, debe ingresar a la opcion 'Editar' del menu anterior.");
    }

    public static void CRUDtecnico(int opcion){
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (tecnicos.isEmpty()){
                System.out.println("Aun no se encuentran tecnicos registrados");
                return;
            }
        }
        switch (accion){
            case "1":
                for (Tecnico tecnico : tecnicos) {
                    System.out.println(tecnico);
                }
                break;
            case "2":
                crearTecnico();
                break;
            case "3":
                //editarTecnico();
                break;
            case "4":
                //eliminarTecnico();
                break;
        }
    }

    public static void crearTecnico(){
        System.out.println("Ingrese la cedula del tecnico: ");
        int cedula = input.nextInt();
        if (cedula<0){
            System.out.println("La cedula es incorrecta");
            return;
        }
        for (Tecnico tecnico :
                tecnicos) {
            if (tecnico.cedula==cedula){
                System.out.println("La cedula ya existe en el sistema");
                return;
            }
        }
        System.out.println("Ingrese el area de trabajo: Aseo, Vigilante, Guia");
        String area = input.next();
        System.out.println("Ingrese la hora de entrada del tecnico: ej: 08:30");
        String horaInicio = input.next();
        System.out.println("Ingrese la hora de salida del tecnico: ej: 18:45");
        String horaSalida = input.next();
        Tecnico tecnico = new Tecnico(cedula,area,horaInicio,horaSalida);
        tecnicos.add(tecnico);
        System.out.println("\nMENSAJE: Nuevo tecnico registrado con exito.\n" +
                "IMPORTANTE: Si desea fijar las relaciones del nuevo tecnico, debe ingresar a la opcion 'Editar' del menu anterior.");
    }

    public static void CRUDprofesional(int opcion){
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (profesionales.isEmpty()){
                System.out.println("Aun no se encuentran profesionales registrados");
                return;
            }
        }
        switch (accion){
            case "1":
                for (Profesional profesional : profesionales) {
                    System.out.println(profesional);
                }
                break;
            case "2":
                crearProfesional();
                break;
            case "3":
                //editarProfesional();
                break;
            case "4":
                //eliminarProfesional();
                break;
        }
    }

    public static void crearProfesional(){
        System.out.println("Ingrese la cedula del profesional: ");
        int cedula = input.nextInt();
        if (cedula<0){
            System.out.println("La cedula es incorrecta");
            return;
        }
        for (Profesional profesional :
                profesionales) {
            if (profesional.cedula==cedula){
                System.out.println("La cedula ya existe en el sistema");
                return;
            }
        }
        System.out.println("Ingrese el area del profesional: Ej: Veterinaria, Administración");
        String area = input.next();
        System.out.println("Ingrese la hora de entrada del profesional: Ej: 09:10");
        String horaInicio = input.next();
        System.out.println("Ingrese la hora de salida del profesional: Ej: 19:21");
        String horaSalida = input.next();
        Profesional profesionalNuevo = new Profesional(cedula,area,horaInicio,horaSalida);
        profesionales.add(profesionalNuevo);
        System.out.println("\nMENSAJE: Nuevo profesional registrado con exito.\n" +
                "IMPORTANTE: Si desea fijar las relaciones del nuevo profesional, debe ingresar a la opcion 'Editar' del menu anterior.");
    }

    public static void CRUDzooamigo(int opcion){
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (zooAmigos.isEmpty()){
                System.out.println("Aun no se encuentran zooAmigos registrados");
                return;
            }
        }
        switch (accion){
            case "1":
                for (ZooAmigo zooAmigo : zooAmigos) {
                    System.out.println(zooAmigo);
                }
                break;
            case "2":
                crearZooAmigo();
                break;
            case "3":
                //editarZooAmigo();
                break;
            case "4":
                //eliminarZooAmigo();
                break;
        }
    }

    public static void crearZooAmigo(){
        System.out.println("Ingrese la cedula del ZooAmigo: ");
        int cedula = input.nextInt();
        if (cedula<0){
            System.out.println("La cedula es incorrecta");
            return;
        }
        for (ZooAmigo zooAmigo :
                zooAmigos) {
            if (zooAmigo.cedula==cedula) {
                System.out.println("La cedula ya existe en el sistema");
                return;
            }
        }
        System.out.println("Ingrese el nombre del ZooAmigo");
        String nombre = input.next();
        System.out.println("Ingrese el telefono del ZooAmigo");
        String telefono = input.next();
        ZooAmigo zooAmigo = new ZooAmigo(cedula,nombre,telefono);
        zooAmigos.add(zooAmigo);
        System.out.println("\nMENSAJE: Nuevo ZooAmigo registrado con exito.\n" +
                "IMPORTANTE: Si desea fijar las relaciones del nuevo ZooAmigo, debe ingresar a la opcion 'Editar' del menu anterior.");
    }
}
