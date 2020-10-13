import org.json.simple.*;
import org.json.simple.parser.*;

import java.util.*;
import java.io.*;

public class SistemaZoologico {

    public static Usuario usuarioActual = null;
    public static Scanner input = new Scanner(System.in);
    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    public static ArrayList<Habitat> habitats = new ArrayList<>();
    public static ArrayList<Animal> animales = new ArrayList<>();
    public static ArrayList<Bioma> biomas = new ArrayList<>();
    public static ArrayList<Profesional> profesionales = new ArrayList<>();
    public static ArrayList<Tecnico> tecnicos = new ArrayList<>();
    public static ArrayList<ZooAmigo> zooAmigos = new ArrayList<>();
    public static ArrayList<Zoologico> zoologicos = new ArrayList<>();
    public static Comparator<Zoologico>[] comparadoresZoologico = new Comparator[4];
    public static Comparator<Bioma>[] comparadoresBioma = new Comparator[6];
    public static Comparator<Habitat>[] comparadoresHabitat = new Comparator[6];
    public static Comparator<Animal>[] comparadoresAnimal = new Comparator[6];
    public static Comparator<Tecnico>[] comparadoresTecnico = new Comparator[5];
    public static Comparator<Profesional>[] comparadoresProfesional = new Comparator[6];
    public static Comparator<ZooAmigo>[] comparadoresZooAmigo = new Comparator[3];

    public static void main(String[] args) {

         Zoologico zoo1 = new Zoologico("1", "aaaa", "bbbb", "pppp"); zoologicos.add(zoo1);
        Zoologico zoo2 = new Zoologico("2", "aaaa", "bbbb", "pppp"); zoologicos.add(zoo2);
        Zoologico zoo3 = new Zoologico("3", "aaaa", "bbbb", "pppp"); zoologicos.add(zoo3);
        Habitat hab1 = new Habitat(10, "rrrr", "oooo", "wwww"); habitats.add(hab1);
        Habitat hab2 = new Habitat(11, "rrrr", "oooo", "wwww"); habitats.add(hab2);
        Habitat hab3 = new Habitat(12, "rrrr", "oooo", "wwww"); habitats.add(hab3);
        Profesional pro1 = new Profesional(101, "xxx", "1100", "2000"); profesionales.add(pro1);
        Profesional pro2 = new Profesional(102, "xxx", "1100", "2000"); profesionales.add(pro2);
        Profesional pro3 = new Profesional(103, "xxx", "1100", "2000"); profesionales.add(pro3);
        Bioma bio1 = new Bioma(20, 312.321, "eeee", "YYYY"); biomas.add(bio1);
        Bioma bio2 = new Bioma(21, 312.321, "eeee", "YYYY"); biomas.add(bio2);

        Bioma bio3 = new Bioma(22, 312.321, "eeee", "YYYY"); biomas.add(bio3);
        Tecnico tec1 = new Tecnico(201, "xxx", "1100", "2000"); tecnicos.add(tec1);
        Tecnico tec2 = new Tecnico(202, "xxx", "1100", "2000"); tecnicos.add(tec2);
        Tecnico tec3 = new Tecnico(203, "xxx", "1100", "2000"); tecnicos.add(tec3);
        Animal ani1 = new Animal(30, "vvvv", 4, "uuuuu"); animales.add(ani1);
        Animal ani2 = new Animal(31, "vvvv", 4, "uuuuu"); animales.add(ani2);
        Animal ani3 = new Animal(32, "vvvv", 4, "uuuuu"); animales.add(ani3);
        ZooAmigo zooA1 = new ZooAmigo(40, "qqqq","133121"); zooAmigos.add(zooA1);
        ZooAmigo zooA2 = new ZooAmigo(41, "qqqq","133121"); zooAmigos.add(zooA2);
        ZooAmigo zooA3 = new ZooAmigo(42, "qqqq","133121"); zooAmigos.add(zooA3);


        comparadoresZoologico[0] = new zNitComparator();
        comparadoresZoologico[1] = new zNombreComparator();
        comparadoresZoologico[2] = new zSiglasComparator();
        comparadoresZoologico[3] = new zCiudadComparator();
        comparadoresBioma[0] = new bIDComparator();
        comparadoresBioma[1] = new bTemperaturaComparator();
        comparadoresBioma[2] = new bHumedadComparator();
        comparadoresBioma[3] = new bTipoComparator();
        comparadoresHabitat[0] = new hIDComparator();
        comparadoresHabitat[1] = new hTipoSueloComparator();
        comparadoresHabitat[2] = new hVegetacionComparator();
        comparadoresHabitat[3] = new hTipoJaulaComparator();
        comparadoresAnimal[0] = new aIDComparator();
        comparadoresAnimal[1] = new aEspecieComparator();
        comparadoresAnimal[2] = new aNivelAgresividadComparator();
        comparadoresAnimal[3] = new aAlimentacionComparator();
        comparadoresTecnico[0] = new tCedulaComparator();
        comparadoresTecnico[1] = new tAreaComparator();
        comparadoresTecnico[2] = new tHoraInicioComparator();
        comparadoresTecnico[3] = new tHoraSalidaComparator();
        comparadoresProfesional[0] = new pCedulaComparator();
        comparadoresProfesional[1] = new pAreaComparator();
        comparadoresProfesional[2] = new pHoraInicioComparator();
        comparadoresProfesional[3] = new pHoraSalidaComparator();
        comparadoresZooAmigo[0] = new zACedulaComparator();
        comparadoresZooAmigo[1] = new zANombreComparator();
        comparadoresZooAmigo[2] = new zATelefonoComparator();

        cargarUsuario(); //Llama a metodo para cargar todos los usuarios registrados desde src/database/usuarios.json
        label:
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Este es el sistema de Zoologicos. \nPor favor, selecione alguna de las siguientes opciones: ");
            System.out.println("1. Ingresar.");
            System.out.println("2. Registrarse. \n");
            String opcion = input.next();
            System.out.println();
            switch (opcion) {
                case "1":
                    if (usuarios.isEmpty()) {
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

        System.out.println("\nBienvenido, " + usuarioActual.Nombre + "...");
        label:
        while (true) {
            System.out.println("----------------------------------");
            System.out.println("Este es el Menu Principal. \nPor favor, selecione alguna de las siguientes opciones: \n");
            System.out.println("1. Administrar.");
            System.out.println("2. Busqueda.");
            System.out.println("3. Diagnostico de inconsistencias.");
            System.out.println("4. Guardar.");
            System.out.println("0. Salir y cancelar.");
            String opcion = input.next();
            System.out.println();
            switch (opcion) {
                case "1":
                    administrar();
                    break;
                case "2":
                    busqueda();
                    break;
                case "3":
                    diagnosticoDeInconsistencias();
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

    public static void cargarUsuario() {
        File newFile = new File("src/database/usuarios.json");
        if (newFile.length() == 0) {
            System.out.println("MENSAJE: aún no hay ningún usuario registrado\n"); //Revisa si el archico usuarios.json esta vacio
            return;
        }

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src/database/usuarios.json")) {
            Object obj = jsonParser.parse(reader);

            JSONArray usuariosJava = (JSONArray) obj; // lee el JSONArray del archivo
            usuariosJava.forEach(usu -> parseUsuarioObj((JSONObject) usu)); //por cada usuarioJSON del JSONArray, lo convierte a JAVAusuario y lo agrega a la lista de usuarios


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void parseUsuarioObj(JSONObject jsonObject) {
        Usuario nuevoUsu = new Usuario(jsonObject);
        usuarios.add(nuevoUsu);
    }

    public static void ingresar() {
        label:
        while (true) {
            System.out.println("-------------------------------------");
            System.out.println("Por favor ingrese documento o correo:");
            String id = input.next();
            System.out.println("Por favor ingrese su contrasenna:");
            String contrasenna = input.next();

            for (Usuario usuario : usuarios) { //Evalua si el documento o correo ingresados coincide con la contrasenna
                if (usuario.Documento.equals(id) || usuario.Correo.equals(id)) {
                    if (usuario.Contrasenna.equals(contrasenna)) {
                        usuarioActual = usuario;
                        break label;
                    }
                }
            }
            if (usuarioActual == null) {
                System.out.println("ERROR: los datos suministrados no coinciden con ninguno de los usuarios registrados");
            }
            System.out.println("-------------------------");
        }
    }

    public static void registrar() {
        //Dentro de este metodo se guardan los usuarios en usuarios.json.
        System.out.println("-------------------------");
        System.out.print("Ingrese documento del nuevo usuario: ");
        String documento = input.next();
        if (documento.charAt(0) == '-') {
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
            if (usuario.Documento.equals(documento) || usuario.Correo.equals(correo)) { //Evalua si ya existe un usuario registrado con el documento o correo ingresados
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

    public static void administrar() {
        label:
        while (true) {
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
            switch (opcion) {
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

    public static String CRUDclases(int i) {
        int opcion = i - 1;
        String accion;
        String[] clases = new String[]{"Zoologico", "Bioma", "Habitat", "Animal", "Tecnico", "Profesional", "ZooAmigo"};

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

    public static void CRUDzoologico(int opcion) {
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (zoologicos.isEmpty()) {
                System.out.println("Aun no se encuentran zoologicos registrados");
                return;
            }
        }
        switch (accion) {
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
                eliminarZoologico();
                break;
        }
    }

    public static void crearZoologico() {
        System.out.print("Ingrese NIT del nuevo zoologico: ");
        String nuevoNIT = input.next();
        for (Zoologico zoologico : zoologicos) {
            if (zoologico.nit.replace(".", "").equals(nuevoNIT.replace(".", ""))) {
                System.out.println("ERROR: Ya existe un zoologico registrado con este NIT.");
                return;
            }
        }
        System.out.print("Ingrese el nombre del nuevo zoologico: ");
        String nuevoNombre = input.next();
        System.out.print("Ingrese las siglas del nuevo zoologico: ");
        String nuevoSiglas = input.next();
        for (Zoologico zoologico : zoologicos) {
            if (zoologico.siglas.equalsIgnoreCase(nuevoSiglas)) {
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

    public static void editarZoologico() {
        Zoologico zoologico = null;
        while (true) {
            String opcion;
            System.out.println("------------------------------------------");
            System.out.println("Estas son las claves unicas del zoologico: \n");
            System.out.println("1. Seleccionar por NIT");
            System.out.println("2. Seleccionar por Siglas\n");
            opcion = input.next();
            if (!opcion.equals("1") && !opcion.equals("2")) {
                System.out.println("Opcion invalida");
                return;
            }
            if (opcion.equals("1")) {
                System.out.print("Ingrese NIT: ");
                String opcion2 = input.next();
                for (Zoologico zoo : zoologicos) {
                    if (zoo.nit.replace(".", "").equals(opcion2.replace(".", ""))) {
                        zoologico = zoo;
                    }
                }
            }
            else {
                System.out.print("Ingrese siglas: ");
                String opcion2 = input.next();
                for (Zoologico zoo : zoologicos) {
                    if (zoo.siglas.equalsIgnoreCase(opcion2)) {
                        zoologico = zoo;
                    }
                }
            }
            if (zoologico == null) {
                System.out.println("No se encontro zoologico con estas especificaciones.");
            } else break;
        }

        input.nextLine();
        System.out.println("\nNIT: " + zoologico.nit);
        String nuevoNIT = input.nextLine();

        System.out.println("Nombre: " + zoologico.nombre);
        String nuevoNombre = input.nextLine();

        System.out.println("Siglas: " + zoologico.siglas);
        String nuevoSiglas = input.nextLine();

        System.out.println("Ciudad: " + zoologico.ciudad);
        String nuevoCiudad = input.nextLine();


        while (true) {
            System.out.print("Desea guardar?:[Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        if (nuevoNIT.isEmpty()) {
                        } else zoologico.nit = nuevoNIT;
                        if (nuevoNombre.isEmpty()) {
                        } else zoologico.nombre = nuevoNombre;
                        if (nuevoSiglas.isEmpty()) {
                        } else zoologico.siglas = nuevoSiglas;
                        if (nuevoCiudad.isEmpty()) {
                        } else zoologico.ciudad = nuevoCiudad;
                    case "N":
                        break;
                }
                break;
            } else {
                System.out.println("Opcion invalida\n");
            }
        }
        //Continua con editar relaciones...
        while (true) {
            System.out.print("\nDesea editar las relaciones de este zoologico? [Y/N] : ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                if ("Y".equals(option.toUpperCase())) {
                    break;
                } else return;
            }
            System.out.println("Opcion invalida");
        }

        label:
        while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. Zoologico - Biomas.");
            System.out.println("2. Zoologico - Profesionales.");
            System.out.println("3. Zoologico - ZooAmigo.");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion) {
                case 1:
                    if (biomas.isEmpty()) {
                        System.out.println("Aun no hay biomas registrados");
                        return;
                    }
                    System.out.println("\nEstos son los biomas disponibles:\n");
                    for (Bioma bioma : biomas) {
                        System.out.println(bioma);
                    }
                    Bioma biomaNuevo = null;
                    while (true) {
                        System.out.print("\nIngrese el ID del bioma con el que quiere asociar este zoologico: ");
                        int id = input.nextInt();
                        for (Bioma bioma : biomas) {
                            if (bioma.id == id) {
                                biomaNuevo = bioma;
                                break;
                            }
                        }
                        if (biomaNuevo == null) {
                            System.out.println("\nID no coincide con ningun bioma");
                        } else break;
                    }
                    /*Revisa si el bioma actual ya esta relacionado con algun zoologico.
                     * si es así, elimina dicha relacion y despues crea la nueva*/
                    for (Zoologico zoo : zoologicos) {
                        Bioma finalBiomaNuevo = biomaNuevo;
                        zoo.biomas.removeIf(bio -> (bio.id == finalBiomaNuevo.id));
                    }
                    zoologico.setBiomas(biomaNuevo, zoologico);
                    System.out.println("\nMENSAJE: Zoologico y bioma relacionados correctamente");
                    break;

                case 2:
                    if (profesionales.isEmpty()) {
                        System.out.println("Aun no hay profesionales registrados");
                        return;
                    }
                    System.out.println("\nEstos son los profesionales disponibles:\n");
                    for (Profesional profesional : profesionales) {
                        System.out.println(profesional);
                    }
                    Profesional proNuevo = null;
                    while (true) {
                        System.out.print("\nIngrese la cedula del profesional con el que quiere asociar este zoologico [Solo el numero, sin espacios ni puntos]: ");
                        int cedula = input.nextInt();
                        for (Profesional profesional : profesionales) {
                            if (profesional.cedula == cedula) {
                                proNuevo = profesional;
                                break;
                            }
                        }
                        if (proNuevo == null) {
                            System.out.println("\nLa cedula no coincide con ningun profesional");
                        } else break;
                    }

                    for (Zoologico zoo : zoologicos) {
                        Profesional finalProNuevo = proNuevo;
                        zoo.profesionales.removeIf(pro -> (pro.cedula == finalProNuevo.cedula));
                    }
                    zoologico.setProfesional(proNuevo, zoologico);
                    System.out.println("\nMENSAJE: Zoologico y profesional relacionados correctamente");
                    break;

                case 3:
                    if (zooAmigos.isEmpty()) {
                        System.out.println("Aun no hay ZooAmigos registrados");
                        return;
                    }
                    System.out.println("\nEstos son los zooAmigos disponibles:\n");
                    for (ZooAmigo zooAmigo : zooAmigos) {
                        System.out.println(zooAmigo);
                    }
                    ZooAmigo nuevoZooAmigo = null;
                    while (true) {
                        System.out.print("\nIngrese la cedula del ZooAmigo con el que quiere asociar este zoologico [Solo el numero, sin espacios ni puntos]: ");
                        int cedulaZA = input.nextInt();
                        for (ZooAmigo zooAmigo : zooAmigos) {
                            if (zooAmigo.cedula == cedulaZA) {
                                nuevoZooAmigo = zooAmigo;
                                break;
                            }
                        }
                        if (nuevoZooAmigo == null) {
                            System.out.println("\nLa cedula no coincide con ningun ZooAmigo");
                        } else break;
                    }

                    for (Zoologico zoo : zoologicos) {
                        ZooAmigo finalZANuevo = nuevoZooAmigo;
                        zoo.zooAmigos.removeIf(za -> (za.cedula == finalZANuevo.cedula));
                    }
                    zoologico.setZooAmigo(nuevoZooAmigo, zoologico);
                    System.out.println("\nMENSAJE: Zoologico y zooAmigo relacionados correctamente");
                    break;
                case 0:
                    break label;
            }
        }

        System.out.println("------------------------------------------");
    }

    public static void eliminarZoologico() {
        Zoologico zoologico = null;
        while (true) {
            String opcion;
            System.out.println("------------------------------------------");
            System.out.println("Estas son las claves unicas del zoologico: \n");
            System.out.println("1. Seleccionar por NIT");
            System.out.println("2. Seleccionar por Siglas\n");
            opcion = input.next();
            if (!opcion.equals("1") && !opcion.equals("2")) {
                System.out.println("Opcion invalida");
                return;
            }
            if (opcion.equals("1")) {
                System.out.print("Ingrese NIT: ");
                String opcion2 = input.next();
                for (Zoologico zoo : zoologicos) {
                    if (zoo.nit.replace(".", "").equals(opcion2.replace(".", ""))) {
                        zoologico = zoo;
                    }
                }
            } else {
                System.out.print("Ingrese siglas: ");
                String opcion2 = input.next();
                for (Zoologico zoo : zoologicos) {
                    if (zoo.siglas.equalsIgnoreCase(opcion2)) {
                        zoologico = zoo;
                    }
                }
            }
            if (zoologico == null) {
                System.out.println("No se encontro zoologico con estas especificaciones.");
            } else break;
        }

        while (true) {
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        break;
                    case "N":
                        return;
                }
                break;
            } else {
                System.out.println("Opcion invalida\n");
            }
        }

        for (Profesional profesional : profesionales) {
            if (profesional.zoologico != null && profesional.zoologico.nit.replace(".", "").equals(zoologico.nit.replace(".", ""))) {
                profesional.zoologico = null;
            }
        }
        for (ZooAmigo zooAmigo : zooAmigos) {
            if (zooAmigo.zoologico != null && zooAmigo.zoologico.nit.replace(".", "").equals(zoologico.nit.replace(".", ""))) {
                zooAmigo.zoologico = null;
            }
        }
        for (Bioma bioma : biomas) {
            if (bioma.zoologico != null && bioma.zoologico.nit.replace(".", "").equals(zoologico.nit.replace(".", ""))) {
                bioma.zoologico = null;
            }
        }
        zoologicos.remove(zoologico);

        System.out.println("El zoologico se ha eliminado correctamente");
    }

    public static void CRUDbioma(int opcion) {
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (biomas.isEmpty()) {
                System.out.println("Aun no se encuentran biomas registrados");
                return;
            }
        }
        switch (accion) {
            case "1":
                for (Bioma bioma : biomas) {
                    System.out.println(bioma);
                }
                break;
            case "2":
                crearBioma();
                break;
            case "3":
                editarBioma();
                break;
            case "4":
                eliminarBioma();
                break;
        }
    }

    public static void crearBioma() {
        System.out.print("Ingrese ID del nuevo bioma: ");
        int nuevoID = input.nextInt();
        for (Bioma bioma : biomas) {
            if (bioma.id == nuevoID) {
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

    public static void editarBioma() {
        Bioma bioma = null;
        while (true) {
            String opcion;
            System.out.println("------------------------------------------");
            System.out.println("Esta es la clave unica del bioma: \n");
            System.out.println("1. Seleccionar por ID\n");
            opcion = input.next();
            if (!opcion.equals("1")) {
                System.out.println("Opcion invalida");
                return;
            }
            System.out.print("Ingrese ID: ");
            int idBio = input.nextInt();
            for (Bioma bioma1 : biomas) {
                if (bioma1.id == idBio) {
                    bioma = bioma1;
                }
            }
            if (bioma == null) {
                System.out.println("No se encontro bioma con estas especificaciones.");
            } else break;
        }

        input.nextLine();
        System.out.println("\nID: " + bioma.id);
        String nuevoID1 = input.nextLine();

        System.out.println("Temperatura: " + bioma.temperatura);
        String nuevaTemp1 = input.nextLine();

        System.out.println("Humedad: " + bioma.humedad);
        String nuevoHume = input.nextLine();

        System.out.println("Tipo: " + bioma.tipo);
        String nuevoTipo = input.nextLine();

        while (true) {
            System.out.print("Desea guardar?:[Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        if (nuevoID1.isEmpty()) {
                        } else {
                            bioma.id = Integer.parseInt(nuevoID1);
                        }
                        if (nuevaTemp1.isEmpty()) {
                        } else {
                            bioma.temperatura = Double.parseDouble(nuevaTemp1);
                        }
                        if (nuevoHume.isEmpty()) {
                        } else bioma.humedad = nuevoHume;
                        if (nuevoTipo.isEmpty()) {
                        } else bioma.tipo = nuevoTipo;
                    case "N":
                        break;
                }
                break;
            } else {
                System.out.println("Opcion invalida\n");
            }
        }
        //Continua con editar relaciones...
        while (true) {
            System.out.print("\nDesea editar las relaciones de este bioma? [Y/N] : ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                if ("Y".equals(option.toUpperCase())) {
                    break;
                } else return;
            }
            System.out.println("Opcion invalida");
        }

        label:
        while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. Bioma - Zoologico.");
            System.out.println("2. Bioma - Profesionales.");
            System.out.println("3. Bioma - Habitats.");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion) {
                case 1:
                    if (zoologicos.isEmpty()) {
                        System.out.println("Aun no hay zoologicos registrados");
                        return;
                    }
                    System.out.println("\nEstos son los zoologicos disponibles:\n");
                    for (Zoologico zoologico : zoologicos) {
                        System.out.println(zoologico);
                    }
                    Zoologico ZooNuevo = null;
                    while (true) {
                        System.out.print("\nIngrese el NIT del zoologico con el que quiere asociar este bioma: ");
                        String nit = input.next();
                        for (Zoologico zoologico : zoologicos) {
                            if (zoologico.nit.replace(".", "").equals(nit.replace(".", ""))) {
                                ZooNuevo = zoologico;
                                break;
                            }
                        }
                        if (ZooNuevo == null) {
                            System.out.println("\nNIT no coincide con ningun zoologico");
                        } else break;
                    }

                    for (Zoologico zoo : zoologicos) {
                        Bioma finalBiomaNuevo = bioma;
                        zoo.biomas.removeIf(bio -> (bio.id == finalBiomaNuevo.id));
                    }
                    bioma.setZoo(ZooNuevo, bioma);
                    System.out.println("\nMENSAJE: Bioma y zoologico relacionados correctamente");
                    break;

                case 2:
                    if (profesionales.isEmpty()) {
                        System.out.println("Aun no hay profesionales registrados");
                        return;
                    }
                    System.out.println("\nEstos son los profesionales disponibles:\n");
                    for (Profesional profesional : profesionales) {
                        System.out.println(profesional);
                    }
                    Profesional proNuevo = null;
                    while (true) {
                        System.out.print("\nIngrese la cedula del profesional con el que quiere asociar este bioma [Solo el numero, sin espacios ni puntos]: ");
                        int cedula = input.nextInt();
                        for (Profesional profesional : profesionales) {
                            if (profesional.cedula == cedula) {
                                proNuevo = profesional;
                                break;
                            }
                        }
                        if (proNuevo == null) {
                            System.out.println("\nLa cedula no coincide con ningun profesionak");
                        } else break;
                    }

                    Profesional finalProNuevo = proNuevo;
                    bioma.profesionales.removeIf(pro -> (pro.cedula == finalProNuevo.cedula));
                    Bioma finalBioma = bioma;
                    proNuevo.biomas.removeIf(bio -> (bio.id == finalBioma.id));

                    bioma.setProfesional(proNuevo, bioma);
                    System.out.println("\nMENSAJE: Bioma y profesional relacionados correctamente");
                    break;

                case 3:
                    if (habitats.isEmpty()) {
                        System.out.println("Aun no hay habitats registrados");
                        return;
                    }
                    System.out.println("\nEstos son los habitats disponibles:\n");
                    for (Habitat habitat : habitats) {
                        System.out.println(habitat);
                    }
                    Habitat nuevoHabitat = null;
                    while (true) {
                        System.out.print("\nIngrese el ID del habitat con el que quiere asociar este bioma: ");
                        int IDHab = input.nextInt();
                        for (Habitat habitat : habitats) {
                            if (habitat.id == IDHab) {
                                nuevoHabitat = habitat;
                                break;
                            }
                        }
                        if (nuevoHabitat == null) {
                            System.out.println("\nEl ID no coincide con ningun habitat");
                        } else break;
                    }

                    for (Bioma bio : biomas) {
                        Habitat finalHabNuevo = nuevoHabitat;
                        bio.habitats.removeIf(hab -> (hab.id == finalHabNuevo.id));
                    }
                    bioma.setHabitat(nuevoHabitat, bioma);
                    System.out.println("\nMENSAJE: Bioma y habitat relacionados correctamente");
                    break;
                case 0:
                    break label;
            }
        }
        System.out.println("------------------------------------------");
    }

    public static void eliminarBioma() {
        Bioma bioma = null;
        while (true) {
            String opcion;
            System.out.println("------------------------------------------");
            System.out.println("Estas son las claves unicas del bioma: \n");
            System.out.println("1. Seleccionar por ID\n");
            opcion = input.next();
            if (!opcion.equals("1")) {
                System.out.println("Opcion invalida");
                return;
            }
            if (opcion.equals("1")) {
                System.out.print("Ingrese ID: ");
                int id = input.nextInt();
                for (Bioma bio : biomas) {
                    if (bio.id == id) {
                        bioma = bio;
                    }
                }
            }
            if (bioma == null) {
                System.out.println("No se encontro bioma con estas especificaciones.");
            } else break;
        }

        while (true) {
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        break;
                    case "N":
                        return;
                }
                break;
            } else {
                System.out.println("Opcion invalida\n");
            }
        }

        for (Zoologico zoologico : zoologicos) {
            Bioma finalBioma = bioma;
            zoologico.biomas.removeIf(bio -> bio.id == finalBioma.id);

        }
        for (Profesional profesional : profesionales) {
            Bioma finalBioma = bioma;
            profesional.biomas.removeIf(bio -> bio.id == finalBioma.id);

        }
        for (Habitat habitat : habitats) {
            if (habitat.bioma != null && habitat.bioma.id == bioma.id) {
                habitat.bioma = null;
            }
        }
        biomas.remove(bioma);

        System.out.println("El bioma se ha eliminado correctamente");
    }

    public static void CRUDhabitat(int opcion) {
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (habitats.isEmpty()) {
                System.out.println("Aun no se encuentran habitats registrados");
                return;
            }
        }
        switch (accion) {
            case "1":
                for (Habitat habitat : habitats) {
                    System.out.println(habitat);
                }
                break;
            case "2":
                crearHabitat();
                break;
            case "3":
                editarHabitat();
                break;
            case "4":
                eliminarHabitat();
                break;
        }
    }

    public static void crearHabitat() {
        System.out.print("Ingrese ID del nuevo habitat: ");
        int nuevoID = input.nextInt();
        for (Habitat habitat : habitats) {
            if (habitat.id == nuevoID) {
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

    public static void editarHabitat() {
        Habitat habitat = null;
        while (true) {
            String opcion;
            System.out.println("------------------------------------------");
            System.out.println("Esta es la clave unica del habitat: \n");
            System.out.println("1. Seleccionar por ID\n");
            opcion = input.next();
            if (!opcion.equals("1")) {
                System.out.println("Opcion invalida");
                return;
            }
            System.out.print("Ingrese ID: ");
            int idHab = input.nextInt();
            for (Habitat hab : habitats) {
                if (hab.id == idHab) {
                    habitat = hab;
                }
            }
            if (habitat == null) {
                System.out.println("No se encontro habitat con estas especificaciones.");
            } else break;
        }

        input.nextLine();
        System.out.println("\nID: " + habitat.id);
        String nuevoID1 = input.nextLine();

        System.out.println("Tipo de suelo: " + habitat.tipoSuelo);
        String nuevoSuelo = input.nextLine();

        System.out.println("Vegetacion: " + habitat.vegetacion);
        String nuevaVege = input.nextLine();

        System.out.println("Tipo de jaula: " + habitat.tipoJaula);
        String nuevaJau = input.nextLine();

        while (true) {
            System.out.print("Desea guardar?:[Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        if (nuevoID1.isEmpty()) {
                        } else {
                            habitat.id = Integer.parseInt(nuevoID1);
                        }
                        if (nuevoSuelo.isEmpty()) {
                        } else habitat.tipoSuelo = nuevoSuelo;
                        if (nuevaVege.isEmpty()) {
                        } else habitat.vegetacion = nuevaVege;
                        if (nuevaJau.isEmpty()) {
                        } else habitat.tipoJaula = nuevaJau;
                    case "N":
                        break;
                }
                break;
            } else {
                System.out.println("Opcion invalida\n");
            }
        }
        //Continua con editar relaciones...
        while (true) {
            System.out.print("\nDesea editar las relaciones de este habitat? [Y/N] : ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                if ("Y".equals(option.toUpperCase())) {
                    break;
                } else return;
            }
            System.out.println("Opcion invalida");
        }

        label:
        while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. Habitat - Bioma.");
            System.out.println("2. Habitat - Tecnico.");
            System.out.println("3. Habitat - Animal.");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion) {
                case 1:
                    if (biomas.isEmpty()) {
                        System.out.println("Aun no hay biomas registrados");
                        return;
                    }
                    System.out.println("\nEstos son los biomas disponibles:\n");
                    for (Bioma bio : biomas) {
                        System.out.println(bio);
                    }
                    Bioma BioNuevo = null;
                    while (true) {
                        System.out.print("\nIngrese el ID del bioma con el que quiere asociar este bioma: ");
                        int id = input.nextInt();
                        for (Bioma bio : biomas) {
                            if (bio.id == id) {
                                BioNuevo = bio;
                                break;
                            }
                        }
                        if (BioNuevo == null) {
                            System.out.println("\nID no coincide con ningun bioma");
                        } else break;
                    }

                    for (Bioma bio : biomas) {
                        Habitat finalHabNuevo = habitat;
                        bio.habitats.removeIf(hab -> (hab.id == finalHabNuevo.id));
                    }
                    habitat.setBioma(BioNuevo, habitat);
                    System.out.println("\nMENSAJE: Habitat y bioma relacionados correctamente");
                    break;

                case 2:
                    if (tecnicos.isEmpty()) {
                        System.out.println("Aun no hay tecnicos registrados");
                        return;
                    }
                    System.out.println("\nEstos son los tecnicos disponibles:\n");
                    for (Tecnico tecnico : tecnicos) {
                        System.out.println(tecnico);
                    }
                    Tecnico tecNuevo = null;
                    while (true) {
                        System.out.print("\nIngrese la cedula del tecnico con el que quiere asociar este tecnico [Solo el numero, sin espacios ni puntos]: ");
                        int cedula = input.nextInt();
                        for (Tecnico tec : tecnicos) {
                            if (tec.cedula == cedula) {
                                tecNuevo = tec;
                                break;
                            }
                        }
                        if (tecNuevo == null) {
                            System.out.println("\nLa cedula no coincide con ningun tecnico");
                        } else break;
                    }

                    Tecnico finalTecNuevo = tecNuevo;
                    habitat.tecnicos.removeIf(tec -> (tec.cedula == finalTecNuevo.cedula));
                    Habitat finalHabitat = habitat;
                    tecNuevo.habitats.removeIf(hab -> (hab.id == finalHabitat.id));

                    habitat.setTecnico(tecNuevo, habitat);
                    System.out.println("\nMENSAJE: Habitat y tecnico relacionados correctamente");
                    break;

                case 3:
                    if (animales.isEmpty()) {
                        System.out.println("Aun no hay animales registrados");
                        return;
                    }
                    System.out.println("\nEstos son los animales disponibles:\n");
                    for (Animal animal : animales) {
                        System.out.println(animal);
                    }
                    Animal nuevoAnimal = null;
                    while (true) {
                        System.out.print("\nIngrese el ID del animal con el que quiere asociar este habitat: ");
                        int IDAni = input.nextInt();
                        for (Animal animal : animales) {
                            if (animal.id == IDAni) {
                                nuevoAnimal = animal;
                                break;
                            }
                        }
                        if (nuevoAnimal == null) {
                            System.out.println("\nEl ID no coincide con ningun animal");
                        } else break;
                    }

                    for (Habitat hab : habitats) {
                        Animal finalAniNuevo = nuevoAnimal;
                        hab.animales.removeIf(ani -> (ani.id == finalAniNuevo.id));
                    }
                    habitat.setAni(nuevoAnimal, habitat);
                    System.out.println("\nMENSAJE: Habitat y animal relacionados correctamente");
                    break;
                case 0:
                    break label;
            }
        }
        System.out.println("------------------------------------------");
    }

    public static void eliminarHabitat() {
        Habitat habitat = null;
        while (true) {
            String opcion;
            System.out.println("------------------------------------------");
            System.out.println("Estas son las claves unicas del habitat: \n");
            System.out.println("1. Seleccionar por ID\n");
            opcion = input.next();
            if (!opcion.equals("1")) {
                System.out.println("Opcion invalida");
                return;
            }
            if (opcion.equals("1")) {
                System.out.print("Ingrese ID: ");
                int id = input.nextInt();
                for (Habitat hab : habitats) {
                    if (hab.id == id) {
                        habitat = hab;
                    }
                }
            }
            if (habitat == null) {
                System.out.println("No se encontro habitat con estas especificaciones.");
            } else break;
        }

        while (true) {
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        break;
                    case "N":
                        return;
                }
                break;
            } else {
                System.out.println("Opcion invalida\n");
            }
        }

        for (Bioma bioma : biomas) {
            Habitat finalHab = habitat;
            bioma.habitats.removeIf(hab -> hab.id == finalHab.id);

        }
        for (Tecnico tecnico : tecnicos) {
            Habitat finalHab = habitat;
            tecnico.habitats.removeIf(hab -> hab.id == finalHab.id);

        }
        for (Animal animal : animales) {
            if (animal.habitat != null && animal.habitat.id == habitat.id) {
                animal.habitat = null;
            }
        }
        habitats.remove(habitat);

        System.out.println("El habitat se ha eliminado correctamente");
    }

    ////////////////////////////////////////////////
    ///////////////////////////////////////////////
    ///////////////////////////////////////////////

    public static void CRUDanimal(int opcion) {
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (animales.isEmpty()) {
                System.out.println("Aun no se encuentran animales registrados");
                return;
            }
        }
        switch (accion) {
            case "1":
                for (Animal animal : animales) {
                    System.out.println(animal);
                }
                break;
            case "2":
                crearAnimal();
                break;
            case "3":
                editarAnimal();
                break;
            case "4":
                eliminarAnimal();
                break;
        }
    }

    public static void crearAnimal() {
        System.out.println("            _   _\n" +
                "           (.)_(.)\n" +
                "        _ (   _   ) _\n" +
                "       / \\/`-----'\\/ \\\n" +
                "     __\\ ( (     ) ) /__\n" +
                "     )   /\\ \\._./ /\\   (\n" +
                "      )_/ /|\\   /|\\ \\_(");
        System.out.println();
        System.out.println("Ingrese el ID: ");
        int id = input.nextInt();
        if (id < 0) {
            System.out.println("ID Invalido");
            return;
        }
        for (Animal animal :
                animales) {
            if (animal.id == id) {
                System.out.println("ERROR: Ya existe un animal registrado con este ID.");
                return;
            }
        }
        if (id < 0) {
            System.out.println("El ID es incorrecto");
            return;
        }
        System.out.println("Ingrese la especie: Ej: Leon,Foca,etc");
        String especie = input.next();
        System.out.println("Ingrese el nivel de agresiviad: Del 1 al 5");
        int agresividad = input.nextInt();
        if (agresividad < 1 || 5 < agresividad) {
            System.out.println("Nivel de agresividad invalido");
            return;
        }
        System.out.println("Ingrese la alimentación del animal: Carnivoro, Hervivoro, Omnivoro");
        String alimentacion = input.next();
        Animal animalNuevo = new Animal(id, especie, agresividad, alimentacion);
        animales.add(animalNuevo);
        System.out.println("\nMENSAJE: Nuevo animal registrado con exito.\n" +
                "IMPORTANTE: Si desea fijar las relaciones del nuevo animal, debe ingresar a la opcion 'Editar' del menu anterior.");
    }

    public static void editarAnimal() {
        Animal animal = null;

        while (true) {
            System.out.println("------------------------------------------");
            System.out.println("Ingrese el ID del animal que desea editar: ");
            int id = input.nextInt();
            if (id < 0) {
                System.out.println("Opcion invalida");
                return;
            }
            for (Animal animalT :
                    animales) {
                if (animalT.id == id) {
                    animal = animalT;
                }
            }
            if (animal == null) {
                System.out.println("No se encontro un animal con el ID ingresado");
            } else break;
        }
        input.nextLine();
        System.out.println("\nID: " + animal.id);
        String nuevoID = input.nextLine();

        System.out.println("Nombre: " + animal.especie);
        String nuevoEspecie = input.nextLine();

        System.out.println("Nivel de agresividad: " + animal.nivelAgresividad);
        String nuevonivelAgresividad = input.nextLine();

        System.out.println("Alimentacion: " + animal.alimentacion);
        String nuevoAlimentacion = input.nextLine();

        while (true) {
            System.out.print("Desea guardar?:[Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        if (nuevoID.isEmpty()) {
                        } else animal.id = Integer.parseInt(nuevoID);
                        if (nuevoEspecie.isEmpty()) {
                        } else animal.especie = nuevoEspecie;
                        if (nuevonivelAgresividad.isEmpty()) {
                        } else animal.nivelAgresividad = Integer.parseInt(nuevonivelAgresividad);
                        if (nuevoAlimentacion.isEmpty()) {
                        } else animal.alimentacion = nuevoAlimentacion;
                    case "N":
                        break;
                }
                break;
            } else {
                System.out.println("Respuesta invalida");
            }
        }
        //Continua con editar relaciones...
        while (true) {
            System.out.print("\nDesea editar las relaciones de este animal? [Y/N] : ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                if ("Y".equals(option.toUpperCase())) {
                    break;
                } else return;
            }
            System.out.println("Opcion invalida");
        }

        label:
        while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. Animal - Habitat");
            System.out.println("2. Animal - ZooAmigo.");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion) {
                case 1:
                    if (habitats.isEmpty()) {
                        System.out.println("Aun no hay habitat registrados");
                        return;
                    }
                    System.out.println("\nEstos son los habitats disponibles:\n");
                    for (Habitat habitat : habitats) {
                        System.out.println(habitat);
                    }
                    Habitat habitatNuevo = null;
                    while (true) {
                        System.out.print("\nIngrese el ID del habitat con el que quiere asociar este animal: ");
                        int id = input.nextInt();
                        for (Habitat habitat : habitats) {
                            if (habitat.id == id) {
                                habitatNuevo = habitat;
                                break;
                            }
                        }
                        if (habitatNuevo == null) {
                            System.out.println("\nID no coincide con ningun habitat");
                        } else break;
                    }
                    if (habitatNuevo.animales.contains(animal)) {
                        System.out.println("\n El Animal ya se encuentra asociado con este Habitat");
                        return;
                    }
                    for (Habitat habitat :
                            habitats) {
                        habitat.animales.remove(animal);
                    }
                    Animal.setHabitats(habitatNuevo, animal);
                    System.out.println("\n MENSAJE: Animal y habitat se han relacionado correctamente");

                    break;
                case 2:
                    if (zooAmigos.isEmpty()) {
                        System.out.println("Aun no hay zooAmigos registrados");
                        return;
                    }
                    System.out.println("\nEstos son los ZooAmigos disponibles:\n");
                    for (ZooAmigo zooAmigo : zooAmigos) {
                        System.out.println(zooAmigo);
                    }
                    ZooAmigo zooAmigoNuevo = null;
                    while (true) {
                        System.out.print("\nIngrese la cedula del zoooAmigo con el que quiere asociar este animal: ");
                        int cedula = input.nextInt();
                        for (ZooAmigo zooAmigo : zooAmigos) {
                            if (zooAmigo.cedula == cedula) {
                                zooAmigoNuevo = zooAmigo;
                                break;
                            }
                        }
                        if (zooAmigoNuevo == null) {
                            System.out.println("\nID no coincide con ningun habitat");
                        } else break;
                    }
                    if (zooAmigoNuevo.animales.contains(animal)) {
                        System.out.println("Este ZooAmigo ya está relacionado con este animal");
                        return;
                    }
                    for (ZooAmigo zooAmigo :
                            zooAmigos) {
                        zooAmigo.animales.remove(animal);
                    }

                    Animal.setZooAmigo(zooAmigoNuevo, animal);
                    System.out.println("\n MENSAJE: Animal y ZooAmigo se han relacionado correctamente");

                    break;
                case 0:
                    break label;
            }
        }

        System.out.println("------------------------------------------");


        System.out.println("------------------------------------------");

    }

    public static void eliminarAnimal() {
        System.out.println("Ingrese el ID del animal que desea eliminar: ");
        int id = input.nextInt();
        if (id < 0) {
            System.out.println("ID invalido");
            return;
        }
        while (true) {
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        Boolean eliminar = animales.removeIf(animal -> animal.id == id); // Elimina el animal por ID, devuelve True o False

                        if (eliminar == false) {
                            System.out.println("ERROR: El animal no se encuentra registrado");
                            return;
                        }
                        for (Habitat habitat :
                                habitats) {
                            habitat.animales.removeIf(animal -> animal.id == id); //Elimando la relación con Habitat
                        }
                        for (ZooAmigo zooAmigo :
                                zooAmigos) {
                            zooAmigo.animales.removeIf(animal -> animal.id == id); //Elimando la relación con ZooAmigo
                        }
                        System.out.println("El animal se ha eliminado correctamente");
                        break;
                    case "N":
                        return;
                }
                break;
            } else {
                System.out.println("Opcion invalida\n");
            }
        }
    }

    public static void CRUDtecnico(int opcion) {
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (tecnicos.isEmpty()) {
                System.out.println("Aun no se encuentran tecnicos registrados");
                return;
            }
        }
        switch (accion) {
            case "1":
                for (Tecnico tecnico : tecnicos) {
                    System.out.println(tecnico);
                }
                break;
            case "2":
                crearTecnico();
                break;
            case "3":
                editarTecnico();
                break;
            case "4":
                eliminarTecnico();
                break;
        }
    }

    public static void crearTecnico() {
        System.out.println("Ingrese la cedula del tecnico: ");
        int cedula = input.nextInt();
        if (cedula < 0) {
            System.out.println("La cedula es incorrecta");
            return;
        }
        for (Tecnico tecnico :
                tecnicos) {
            if (tecnico.cedula == cedula) {
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
        Tecnico tecnico = new Tecnico(cedula, area, horaInicio, horaSalida);
        tecnicos.add(tecnico);
        System.out.println("\nMENSAJE: Nuevo tecnico registrado con exito.\n" +
                "IMPORTANTE: Si desea fijar las relaciones del nuevo tecnico, debe ingresar a la opcion 'Editar' del menu anterior.");
    }

    public static void editarTecnico() {
        Tecnico tecnico = null;

        while (true) {
            System.out.println("------------------------------------------");
            System.out.println("Ingrese el ID del tecnico que desea editar: ");
            int cedula = input.nextInt();
            if (cedula < 0) {
                System.out.println("Opcion invalida");
                return;
            }
            for (Tecnico tecnico1 :
                    tecnicos) {
                if (tecnico1.cedula == cedula) {
                    tecnico = tecnico1;
                }
            }
            if (tecnico == null) {
                System.out.println("No se encontro un tecnico con el ID ingresado");
            } else break;
        }
        input.nextLine();
        System.out.println("\nCedula: " + tecnico.cedula);
        String nuevoCedula = input.nextLine();

        System.out.println("Area : " + tecnico.area);
        String nuevoArea = input.nextLine();

        System.out.println("Hora Inicio: " + tecnico.horaInicio);
        String nuevoHoraInicio = input.nextLine();

        System.out.println("Hora Salida: " + tecnico.horaSalida);
        String nuevoHoraSalida = input.nextLine();

        while (true) {
            System.out.print("Desea guardar?:[Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        if (nuevoCedula.isEmpty()) {
                        } else tecnico.cedula = Integer.parseInt(nuevoCedula);
                        if (nuevoArea.isEmpty()) {
                        } else tecnico.area = nuevoArea;
                        if (nuevoHoraInicio.isEmpty()) {
                        } else tecnico.horaInicio = nuevoHoraInicio;
                        if (nuevoHoraSalida.isEmpty()) {
                        } else tecnico.horaSalida = nuevoHoraSalida;
                    case "N":
                        break;
                }
                break;
            } else {
                System.out.println("Respuesta invalida");
            }
        }
        //Continua con editar relaciones...
        while (true) {
            System.out.print("\nDesea editar las relaciones de este tecnico? [Y/N] : ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                if ("Y".equals(option.toUpperCase())) {
                    break;
                } else return;
            }
            System.out.println("Opcion invalida");
        }

        label:
        while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. Tecnico - Habitat");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion) {
                case 1:
                    if (habitats.isEmpty()) {
                        System.out.println("Aun no hay habitat registrados");
                        return;
                    }
                    System.out.println("\nEstos son los habitats disponibles:\n");
                    for (Habitat habitat : habitats) {
                        System.out.println(habitat);
                    }
                    Habitat habitatNuevo = null;
                    while (true) {
                        System.out.print("\nIngrese el ID del habitat con el que quiere asociar este tecnico: ");
                        int id = input.nextInt();
                        for (Habitat habitat : habitats) {
                            if (habitat.id == id) {
                                habitatNuevo = habitat;
                                break;
                            }
                        }
                        if (habitatNuevo == null) {
                            System.out.println("\nID no coincide con ningun habitat");
                        } else break;
                    }


                    Habitat finalHabitatNuevo = habitatNuevo;
                    tecnico.habitats.removeIf(habitat -> habitat.id == finalHabitatNuevo.id);

                    Tecnico finalTecnico = tecnico;
                    habitatNuevo.tecnicos.removeIf(tecnico1 -> tecnico1.cedula == finalTecnico.cedula);


                    Tecnico.setHabitats(habitatNuevo, tecnico);
                    System.out.println("\n MENSAJE: Tecnico y habitat se han relacionado correctamente");

                    break;
                case 0:
                    break label;
            }
        }

        System.out.println("------------------------------------------");


        System.out.println("------------------------------------------");
    }

    public static void eliminarTecnico() {
        System.out.println("Ingrese la cedula del tecnico que desea eliminar: ");
        int cedula = input.nextInt();
        if (cedula < 0) {
            System.out.println("Cedula invalida");
            return;
        }
        while (true) {
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        boolean eliminar = tecnicos.removeIf(tecnico -> tecnico.cedula == cedula); // Elimina el animal por ID, devuelve True o False
                        if (!eliminar) {
                            System.out.println("ERROR: El Tecnico no se encuentra registrado");
                            return;
                        }
                        for (Habitat habitat :
                                habitats) {
                            habitat.tecnicos.removeIf(tecnico -> tecnico.cedula == cedula); //Elimando la relación con Habitat
                        }
                        System.out.println("El tecnico se ha eliminado correctamente");
                        break;
                    case "N":
                        return;
                }
                break;
            } else {
                System.out.println("Opcion invalida\n");
            }
        }

    }

    public static void CRUDprofesional(int opcion) {
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (profesionales.isEmpty()) {
                System.out.println("Aun no se encuentran profesionales registrados");
                return;
            }
        }
        switch (accion) {
            case "1":
                for (Profesional profesional : profesionales) {
                    System.out.println(profesional);
                }
                break;
            case "2":
                crearProfesional();
                break;
            case "3":
                editarProfesional();
                break;
            case "4":
                eliminarProfesional();
                break;
        }
    }

    public static void crearProfesional() {
        System.out.println("Ingrese la cedula del profesional: ");
        int cedula = input.nextInt();
        if (cedula < 0) {
            System.out.println("La cedula es incorrecta");
            return;
        }
        for (Profesional profesional :
                profesionales) {
            if (profesional.cedula == cedula) {
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
        Profesional profesionalNuevo = new Profesional(cedula, area, horaInicio, horaSalida);
        profesionales.add(profesionalNuevo);
        System.out.println("\nMENSAJE: Nuevo profesional registrado con exito.\n" +
                "IMPORTANTE: Si desea fijar las relaciones del nuevo profesional, debe ingresar a la opcion 'Editar' del menu anterior.");
    }

    public static void editarProfesional() {
        Profesional profesional = null;

        while (true) {
            System.out.println("------------------------------------------");
            System.out.println("Ingrese la cedula del profesional que desea editar: ");
            int cedula = input.nextInt();
            if (cedula < 0) {
                System.out.println("Opcion invalida");
                return;
            }
            for (Profesional profesional1 :
                    profesionales) {
                if (profesional1.cedula == cedula) {
                    profesional = profesional1;
                }
            }
            if (profesional == null) {
                System.out.println("No se encontro un animal con el ID ingresado");
            } else break;
        }
        input.nextLine();
        System.out.println("\nCedula: " + profesional.cedula);
        String nuevoCedula = input.nextLine();

        System.out.println("Area : " + profesional.area);
        String nuevoArea = input.nextLine();

        System.out.println("Hora Inicio: " + profesional.horaInicio);
        String nuevoHoraInicio = input.nextLine();

        System.out.println("Hora Salida: " + profesional.horaSalida);
        String nuevoHoraSalida = input.nextLine();

        while (true) {
            System.out.print("Desea guardar?:[Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        if (nuevoCedula.isEmpty()) {
                        } else profesional.cedula = Integer.parseInt(nuevoCedula);
                        if (nuevoArea.isEmpty()) {
                        } else profesional.area = nuevoArea;
                        if (nuevoHoraInicio.isEmpty()) {
                        } else profesional.horaInicio = nuevoHoraInicio;
                        if (nuevoHoraSalida.isEmpty()) {
                        } else profesional.horaSalida = nuevoHoraSalida;
                    case "N":
                        break;
                }
                break;
            } else {
                System.out.println("Respuesta invalida");
            }
        }
        //Continua con editar relaciones...
        while (true) {
            System.out.print("\nDesea editar las relaciones de este profesional? [Y/N] : ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                if ("Y".equals(option.toUpperCase())) {
                    break;
                } else return;
            }
            System.out.println("Opcion invalida");
        }

        label:
        while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. Profesional - bioma");
            System.out.println("2. Profesional - Zoologico");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion) {
                case 1:
                    if (biomas.isEmpty()) {
                        System.out.println("Aun no hay biomas registrados");
                        return;
                    }
                    System.out.println("\nEstos son los biomas disponibles:\n");
                    for (Bioma bioma : biomas) {
                        System.out.println(bioma);
                    }
                    Bioma biomaNuevo = null;
                    while (true) {
                        System.out.print("\nIngrese el ID del bioma con el que quiere asociar este tecnico: ");
                        int id = input.nextInt();
                        for (Bioma bioma : biomas) {
                            if (bioma.id == id) {
                                biomaNuevo = bioma;
                                break;
                            }
                        }
                        if (biomaNuevo == null) {
                            System.out.println("\nID no coincide con ningun bioma");
                        } else break;
                    }
                    if (biomaNuevo.profesionales.contains(profesional)) {
                        System.out.println("El Bioma y el Profesional ya están relacionados");
                        return;
                    }
                    Bioma finalBiomaNuevo = biomaNuevo;
                    profesional.biomas.removeIf(bioma -> bioma.id == finalBiomaNuevo.id);


                    Profesional finalProfesional = profesional;
                    biomaNuevo.profesionales.removeIf(profesional1 -> profesional1.cedula == finalProfesional.cedula);

                    Profesional.setBiomas(biomaNuevo, profesional);
                    System.out.println("\n MENSAJE: Zoologico y el Profesional se han relacionado correctamente");


                    break;
                case 2:
                    System.out.println("\nEstos son los zoologicos disponibles:\n");
                    for (Zoologico zoologico1 : zoologicos) {
                        System.out.println(zoologico1);
                    }
                    Zoologico zoologico = null;
                    String option;
                    while (true) {
                        System.out.println("------------------------------------------");
                        System.out.println("Estas son las claves unicas del zoologico: \n");
                        System.out.println("1. Seleccionar por NIT");
                        System.out.println("2. Seleccionar por Siglas\n");
                        option = input.next();
                        if (!option.equals("1") && !option.equals("2")) {
                            System.out.println("Opcion invalida");
                            return;
                        }
                        if (option.equals("1")) {
                            System.out.print("Ingrese NIT: ");
                            String opcion2 = input.next();
                            for (Zoologico zoo : zoologicos) {
                                if (zoo.nit.replace(".", "").equals(opcion2.replace(".", ""))) {
                                    zoologico = zoo;
                                }
                            }
                        } else {
                            System.out.print("Ingrese siglas: ");
                            String opcion2 = input.next();
                            for (Zoologico zoo : zoologicos) {
                                if (zoo.siglas.equalsIgnoreCase(opcion2)) {
                                    zoologico = zoo;
                                }
                            }
                        }
                        if (zoologico == null) {
                            System.out.println("No se encontro zoologico con estas especificaciones.");
                        } else break;
                    }
                    if (zoologico.profesionales.contains(profesional)) {
                        System.out.println("El Zoologico y el Profesional ya están relacionados");
                        return;
                    }
                    for (Zoologico zoologico1 : zoologicos) {
                        zoologico1.profesionales.remove(profesional);
                    }
                    Profesional.setZoologicos(zoologico, profesional);
                    System.out.println("\n MENSAJE: Zoologico y el Profesional se han relacionado correctamente");
                    break;
                case 0:
                    break label;
            }
        }

        System.out.println("------------------------------------------");


        System.out.println("------------------------------------------");
    }

    public static void eliminarProfesional() {
        System.out.println("Ingrese la cedula del profesional que desea eliminar: ");
        int cedula = input.nextInt();
        if (cedula < 0) {
            System.out.println("Cedula invalida");
            return;
        }
        while (true) {
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        boolean eliminar = profesionales.removeIf(profesional -> profesional.cedula == cedula); // Elimina el profesional por cedula, devuelve True o False
                        if (!eliminar) {
                            System.out.println("ERROR: El Tecnico no se encuentra registrado");
                            return;
                        }
                        for (Bioma bioma :
                                biomas) {
                            bioma.profesionales.removeIf(profesional -> profesional.cedula == cedula); //Elimando la relación con Bioma
                        }
                        for (Zoologico zoologico :
                                zoologicos) {
                            zoologico.profesionales.removeIf(profesional -> profesional.cedula == cedula); //Elimando la relación con Bioma
                        }
                        System.out.println("El profesional se ha eliminado correctamente");
                        break;
                    case "N":
                        return;
                }
                break;
            } else {
                System.out.println("Opcion invalida\n");
            }
        }

    }

    public static void CRUDzooamigo(int opcion) {
        String accion = CRUDclases(opcion);
        if (accion.equals("1") || accion.equals("3") || accion.equals("4")) {
            if (zooAmigos.isEmpty()) {
                System.out.println("Aun no se encuentran zooAmigos registrados");
                return;
            }
        }
        switch (accion) {
            case "1":
                for (ZooAmigo zooAmigo : zooAmigos) {
                    System.out.println(zooAmigo);
                }
                break;
            case "2":
                crearZooAmigo();
                break;
            case "3":
                editarZooAmigo();
                break;
            case "4":
                eliminarZooAmigo();
                break;
        }
    }

    public static void crearZooAmigo() {
        System.out.println("Ingrese la cedula del ZooAmigo: ");
        int cedula = input.nextInt();
        if (cedula < 0) {
            System.out.println("La cedula es incorrecta");
            return;
        }
        for (ZooAmigo zooAmigo :
                zooAmigos) {
            if (zooAmigo.cedula == cedula) {
                System.out.println("La cedula ya existe en el sistema");
                return;
            }
        }
        System.out.println("Ingrese el nombre del ZooAmigo");
        String nombre = input.next();
        System.out.println("Ingrese el telefono del ZooAmigo");
        String telefono = input.next();
        ZooAmigo zooAmigo = new ZooAmigo(cedula, nombre, telefono);
        zooAmigos.add(zooAmigo);
        System.out.println("\nMENSAJE: Nuevo ZooAmigo registrado con exito.\n" +
                "IMPORTANTE: Si desea fijar las relaciones del nuevo ZooAmigo, debe ingresar a la opcion 'Editar' del menu anterior.");
    }

    public static void editarZooAmigo() {
        ZooAmigo zooAmigo = null;

        while (true) {
            System.out.println("------------------------------------------");
            System.out.println("Ingrese la cedula del zooAmigo que desea editar: ");
            int cedula = input.nextInt();
            if (cedula < 0) {
                System.out.println("Opcion invalida");
                return;
            }
            for (ZooAmigo zooAmigo1 :
                    zooAmigos) {
                if (zooAmigo1.cedula == cedula) {
                    zooAmigo = zooAmigo1;
                }
            }
            if (zooAmigo == null) {
                System.out.println("No se encontro un ZooAmigo con el ID ingresado");
            } else break;
        }
        input.nextLine();
        System.out.println("\nCedula: " + zooAmigo.cedula);
        String nuevoCedula = input.nextLine();

        System.out.println("Nombre : " + zooAmigo.nombre);
        String nuevoNombre = input.nextLine();

        System.out.println("Telefono : " + zooAmigo.telefono);
        String nuevoTelefono = input.nextLine();

        while (true) {
            System.out.print("Desea guardar?:[Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        if (nuevoCedula.isEmpty()) {
                        } else zooAmigo.cedula = Integer.parseInt(nuevoCedula);
                        if (nuevoNombre.isEmpty()) {
                        } else zooAmigo.nombre = nuevoNombre;
                        if (nuevoTelefono.isEmpty()) {
                        } else zooAmigo.telefono = nuevoTelefono;
                    case "N":
                        break;
                }
                break;
            } else {
                System.out.println("Respuesta invalida");
            }
        }
        //Continua con editar relaciones...
        while (true) {
            System.out.print("\nDesea editar las relaciones de este ZooAmigo? [Y/N] : ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                if ("Y".equals(option.toUpperCase())) {
                    break;
                } else return;
            }
            System.out.println("Opcion invalida");
        }

        label:
        while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. ZooAmigo - Animal");
            System.out.println("2. ZooAmigo - Zoologico");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion) {
                case 1:
                    if (animales.isEmpty()) {
                        System.out.println("Aun no hay animales registrados");
                        return;
                    }
                    System.out.println("\nEstos son los animales disponibles:\n");
                    for (Animal animal : animales) {
                        System.out.println(animal);
                    }
                    Animal animalNuevo = null;
                    while (true) {
                        System.out.print("\nIngrese el ID del animal con el que quiere asociar este ZooAmigo: ");
                        int id = input.nextInt();
                        for (Animal animal : animales) {
                            if (animal.id == id) {
                                animalNuevo = animal;
                                break;
                            }
                        }
                        if (animalNuevo == null) {
                            System.out.println("\nID no coincide con ningun Animal");
                        } else break;
                    }

                    for (Animal animal1 :
                            animales) {
                        animal1.zooAmigo = null;
                    }
                    zooAmigo.setAnimal(animalNuevo, zooAmigo);
                    System.out.println("\n MENSAJE: ZooAmigo y Animal se han relacionado correctamente");

                    break;
                case 2:
                    if (zoologicos.isEmpty()) {
                        System.out.println("Aun no hay Zoologicos registrados");
                        return;
                    }
                    System.out.println("\nEstos son los zoologicos disponibles:\n");
                    for (Zoologico zoologico : zoologicos) {
                        System.out.println(zoologico);
                    }
                    Zoologico zoologico = null;
                    while (true) {
                        String option;
                        System.out.println("------------------------------------------");
                        System.out.println("Estas son las claves unicas del zoologico: \n");
                        System.out.println("1. Seleccionar por NIT");
                        System.out.println("2. Seleccionar por Siglas\n");
                        option = input.next();
                        if (!option.equals("1") && !option.equals("2")) {
                            System.out.println("Opcion invalida");
                            return;
                        }
                        if (option.equals("1")) {
                            System.out.print("Ingrese NIT: ");
                            String opcion2 = input.next();
                            for (Zoologico zoo : zoologicos) {
                                if (zoo.nit.replace(".", "").equals(opcion2.replace(".", ""))) {
                                    zoologico = zoo;
                                }
                            }
                        } else {
                            System.out.print("Ingrese siglas: ");
                            String opcion2 = input.next();
                            for (Zoologico zoo : zoologicos) {
                                if (zoo.siglas.equalsIgnoreCase(opcion2)) {
                                    zoologico = zoo;
                                }
                            }
                        }
                        if (zoologico == null) {
                            System.out.println("No se encontro zoologico con estas especificaciones.");
                        } else break;
                    }
                    for (Zoologico zoo :
                            zoologicos) {
                        if (zoo.zooAmigos.contains(zooAmigo)) {
                            zoo.zooAmigos.remove(zooAmigo);
                        }
                    }
                    zooAmigo.setZoologico(zoologico, zooAmigo);
                    System.out.println("\n MENSAJE: ZooAmigo y Zoologico se han relacionado correctamente");

                    break;
                case 0:
                    break label;
            }
        }

        System.out.println("------------------------------------------");


        System.out.println("------------------------------------------");
    }

    public static void eliminarZooAmigo() {
        System.out.println("Ingrese la cedula del ZooAmigo que desea eliminar: ");
        int cedula = input.nextInt();
        if (cedula < 0) {
            System.out.println("Cedula invalida");
            return;
        }
        while (true) {
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")) {
                switch (option.toUpperCase()) {
                    case "Y":
                        boolean eliminar = zooAmigos.removeIf(zooAmigo -> zooAmigo.cedula == cedula); // Elimina el profesional por cedula, devuelve True o False
                        if (!eliminar) {
                            System.out.println("ERROR: El ZooAmigo no se encuentra registrado");
                            return;
                        }

                        for (Animal animal :
                                animales) {
                            if (animal.zooAmigo != null && animal.zooAmigo.cedula == cedula) {
                                animal.zooAmigo = null;
                            }
                        }
                        for (Zoologico zoologico :
                                zoologicos) {
                            zoologico.zooAmigos.removeIf(zooAmigo -> zooAmigo.cedula == cedula); //Elimando la relación con Bioma
                        }
                        System.out.println("El ZooAmigo se ha eliminado correctamente");
                        break;
                    case "N":
                        return;
                }
                break;
            } else {
                System.out.println("Opcion invalida\n");
            }
        }
    }

    public static void busqueda() {
        String option;
        label:
        while (true) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, selecione la clase que desea buscar: \n");
            System.out.println("1. Zoologico.");
            System.out.println("2. Bioma.");
            System.out.println("3. Habitat.");
            System.out.println("4. Animal.");
            System.out.println("5. Tecnico.");
            System.out.println("6. Profesional.");
            System.out.println("7. ZooAmigo.");
            System.out.println("0. Regresar a Menu Principal.");
            option = input.next();
            System.out.println();
            switch (option) {
                case "1":
                    if (zoologicos.size() == 0) {
                        System.out.println("No se encuentran zoologicos registrados");
                    } else {
                        busquedaZoologico();
                    }
                    break;
                case "2":
                    if (biomas.size() == 0) {
                        System.out.println("No se encuentran biomas registrados");
                    } else {
                        busquedaBioma();
                    }
                    break;
                case "3":
                    if (habitats.size() == 0) {
                        System.out.println("No se encuentran habitats registrados");
                    } else {
                        busquedaHabitat();
                    }
                    break;
                case "4":
                    if (animales.size() == 0) {
                        System.out.println("No se encuentran animales registrados");
                    } else {
                        busquedaAnimal();
                    }
                    break;
                case "5":
                    if (tecnicos.size() == 0) {
                        System.out.println("No se encuentran tecnicos registrados");
                    } else {
                        busquedaTecnico();
                    }
                    break;
                case "6":
                    if (profesionales.size() == 0) {
                        System.out.println("No se encuentran profesionales registrados");
                    } else {
                        busquedaProfesional();
                    }
                    break;
                case "7":
                    if (zooAmigos.size() == 0) {
                        System.out.println("No se encuentran ZooAmigos registrados");
                    } else {
                        busquedaZooamigo();
                    }
                    break;
                case "0":
                    break label;
            }
            System.out.println("----------------------------------");
        }
    }

    public static void busquedaZoologico() {
        String option;
        label:
        while (true) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
            System.out.println("1. Nit.");
            System.out.println("2. Nombre.");
            System.out.println("3. Siglas.");
            System.out.println("4. Ciudad.");
            System.out.println("5. Mostrar todos los zoologicos.");
            System.out.println("0. Regresar al menu anterior.");
            option = input.next();
            System.out.println();
            switch (option) {
                case "1":
                    String option1;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar puntos.");
                    option1 = input.next();
                    if (!option1.equals("1") && !option1.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option1.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String nitBus = input.next();
                        nitZoologico(nitBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String nitBus = input.next();
                        nitZoologico( nitBus);
                        break;
                    }
                    break;
                case "2":
                    String option2;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option2 = input.next();
                    if (!option2.equals("1") && !option2.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option2.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String nombreBus = input.next();
                        nombreZoologico(nombreBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String nombreBus = input.next();
                        nombreZoologico(nombreBus);
                        break;
                    }
                    break;
                case "3":
                    String option3;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option3 = input.next();
                    if (!option3.equals("1") && !option3.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option3.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String siglasBus = input.next();
                        siglasZoologico(siglasBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String siglasBus = input.next();
                        siglasZoologico(siglasBus);
                        break;
                    }
                    break;
                case "4":
                    String option4;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option4 = input.next();
                    if (!option4.equals("1") && !option4.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option4.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String ciudadBus = input.next();
                        ciudadZoologico(ciudadBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String ciudadBus = input.next();
                        ciudadZoologico(ciudadBus);
                        break;
                    }
                    break;
                case "5":
                    mostarTodosZoologicos();
                    break;
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void nitZoologico(String nitBus) {
        ArrayList<Zoologico> nitZoo = new ArrayList<>(zoologicos);
        for (Zoologico zoologico : zoologicos) {
            if (nitBus.equals(zoologico.nit) || nitBus.equals(zoologico.nit.replace(".",""))) {
                nitZoo.add(zoologico);
            }
            else {
                System.out.println("El nit no se encuentra registrado");
                return;
            }
        }

        Zoologico zoo;
        int i = 0;
        for (Zoologico zoologico : nitZoo) {
            if (nitBus.equals(zoologico.nit)) {
                i++;
                System.out.println(i + ". " + zoologico);
            }//Menu para editar o eliminar
            String accion;
            System.out.println("-----------------------------------------");
            System.out.println("\nIndique la accion que desea realizar:\n");
            System.out.println("1. Editar ");
            System.out.println("2. Eliminar ");
            System.out.println("0. Regresar al menú anterior" + "\n");
            accion = input.next();
            System.out.println("----------------------------------");

            //Codigo para editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zoologico que desea editar: \n");
                eleccion = input.nextInt();
                zoo = nitZoo.get(eleccion - 1);

                input.nextLine();
                System.out.println("NIT: " + zoo.nit);
                String nuevoNIT = input.nextLine();
                if (zoologico.nit.replace(".", "").equals(nuevoNIT)) {
                    System.out.println("ERROR: Ya existe un zoologico registrado con este NIT.");
                    return;
                }
                System.out.println("Nombre: " + zoo.nombre);
                String nuevoNombre = input.nextLine();

                System.out.println("Siglas: " + zoo.siglas);
                String nuevoSiglas = input.nextLine();
                if (zoologico.siglas.equalsIgnoreCase(nuevoSiglas)) {
                    System.out.println("ERROR: Ya existe un zoologico registrado con estas siglas.");
                    return;
                }

                System.out.println("Ciudad: " + zoo.ciudad);
                String nuevoCiudad = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoNIT.isEmpty()) {
                                } else zoo.nit = nuevoNIT;
                                if (nuevoNombre.isEmpty()) {
                                } else zoo.nombre = nuevoNombre;
                                if (nuevoSiglas.isEmpty()) {
                                } else zoo.siglas = nuevoSiglas;
                                if (nuevoCiudad.isEmpty()) {
                                } else zoo.ciudad = nuevoCiudad;
                                System.out.println("Zoologico editado exitosamente");
                                break;

                            case "N":
                                 break;
                        }return;
                    }else{
                        System.out.println("Opcion invalida\n");
                    }
                    return;
                }
            }//Codigo para eliminar
            else if(accion.equals("2")){
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zoologico que desea eliminar: \n");
                eleccion = input.nextInt();
                zoo = nitZoo.get(eleccion - 1);
                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    }else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Profesional profesional : profesionales) {
                    if (profesional.zoologico != null && profesional.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                        profesional.zoologico = null;
                    }
                }
                for (ZooAmigo zooAmigo : zooAmigos) {
                    if (zooAmigo.zoologico != null && zooAmigo.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                        zooAmigo.zoologico = null;
                    }
                }
                for (Bioma bioma : biomas) {
                    if (bioma.zoologico != null && bioma.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                        bioma.zoologico = null;
                    }
                }
                zoologicos.remove(zoo);

                System.out.println("El zoologico se ha eliminado correctamente");
                return;
            }
            //Regresar al menu anterior
            else{
                return;
            }
        }
    }
    public static void nombreZoologico(String nombreBus) {
        //Crear lista para no afectar la principal y que se agreguen los elementos que cumplen las condiciones
        ArrayList<Zoologico> nombreZoo = new ArrayList<>();
        for (Zoologico zoologico : zoologicos) {
            if (nombreBus.equals(zoologico.nombre) || nombreBus.equals(zoologico.nombre.toLowerCase())) {
                nombreZoo.add(zoologico);
            }else{
                System.out.println("El nombre no se encuentra registrado");
                return;
            }
        }
        Zoologico zoo;
        int i=0;
        //Menu para ordenar
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Nit.");
        System.out.println("2. Siglas.");
        System.out.println("3. Ciudad.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();

        //Ordenar por nit
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();

                //Ordenar ascendentemente
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    nombreZoo.sort((comparadoresZoologico[0]));
                    for (Zoologico zoologico : nombreZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                }
                //Ordenar descendentemente
                else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    nombreZoo.sort(Collections.reverseOrder(comparadoresZoologico[0]));
                    for (Zoologico zoologico : nombreZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            //Ordenar por siglas
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                //Ordenar ascendentemente
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    nombreZoo.sort((comparadoresZoologico[2]));
                    for (Zoologico zoologico : nombreZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                }
                //Organizar descedente
                else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    nombreZoo.sort(Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : nombreZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                //Menu ascendente - descendete
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();

                //organizar ascendente
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    nombreZoo.sort((comparadoresZoologico[3]));
                    for (Zoologico zoologico : nombreZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                }
                //organizar descendente
                else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    nombreZoo.sort(Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : nombreZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }//Menu para editar o eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");
        for(Zoologico zoologico: nombreZoo) {
            //Codigo para editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zoologico que desea editar: \n");
                eleccion = input.nextInt();
                zoo = nombreZoo.get(eleccion - 1);
                input.nextLine();

                System.out.println("NIT: " + zoo.nit);
                String nuevoNIT = input.nextLine();
                if (zoologico.nit.replace(".", "").equals(nuevoNIT)) {
                    System.out.println("ERROR: Ya existe un zoologico registrado con este NIT.");
                    return;
                }
                System.out.println("Nombre: " + zoo.nombre);
                String nuevoNombre = input.nextLine();

                System.out.println("Siglas: " + zoo.siglas);
                String nuevoSiglas = input.nextLine();
                if (zoologico.siglas.equalsIgnoreCase(nuevoSiglas)) {
                    System.out.println("ERROR: Ya existe un zoologico registrado con estas siglas.");
                    return;
                }

                System.out.println("Ciudad: " + zoo.ciudad);
                String nuevoCiudad = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoNIT.isEmpty()) {
                                } else zoo.nit = nuevoNIT;
                                if (nuevoNombre.isEmpty()) {
                                } else zoo.nombre = nuevoNombre;
                                if (nuevoSiglas.isEmpty()) {
                                } else zoo.siglas = nuevoSiglas;
                                if (nuevoCiudad.isEmpty()) {
                                } else zoo.ciudad = nuevoCiudad;
                                System.out.println("Zoologico editado exitosamente");
                                break;

                            case "N":
                                break;
                        }return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }return;
                }
            }//Codigo para eliminar
            else if(accion.equals("2")){
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zoologico que desea eliminar: \n");
                eleccion = input.nextInt();
                zoo = nombreZoo.get(eleccion - 1);
                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Profesional profesional : profesionales) {
                    if (profesional.zoologico != null && profesional.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                        profesional.zoologico = null;
                    }
                }
                for (ZooAmigo zooAmigo : zooAmigos) {
                    if (zooAmigo.zoologico != null && zooAmigo.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                        zooAmigo.zoologico = null;
                    }
                }
                for (Bioma bioma : biomas) {
                    if (bioma.zoologico != null && bioma.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                        bioma.zoologico = null;
                    }
                }
                zoologicos.remove(zoo);

                System.out.println("El zoologico se ha eliminado correctamente");
                return;
            }//Regresar al menu anterior
            else{
                return;
            }
        }
    }
    public static void siglasZoologico(String siglasBus) {
        ArrayList<Zoologico> siglasZoo = new ArrayList<>(zoologicos);
        for (Zoologico zoologico : zoologicos) {
            if (siglasBus.equals(zoologico.siglas) || siglasBus.equals(zoologico.siglas.toLowerCase())) {
                siglasZoo.add(zoologico);
            }else{
                System.out.println("Las siglas no se encuentran registradas");
                return;
            }
        }
        Zoologico zoo;
        int i = 0;
        for (Zoologico zoologico : siglasZoo) {
            if (siglasBus.equals(zoologico.siglas)) {
                i++;
                System.out.println(i + ". " + zoologico);
            }//Menu editar - eliminar
            String accion;
            System.out.println("-----------------------------------------");
            System.out.println("\nIndique la accion que desea realizar:\n");
            System.out.println("1. Editar ");
            System.out.println("2. Eliminar ");
            System.out.println("0. Regresar al menú anterior" + "\n");
            accion = input.next();
            System.out.println("----------------------------------");

            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zoologico que desea editar: \n");
                eleccion = input.nextInt();
                zoo = siglasZoo.get(eleccion - 1);
                input.nextLine();

                System.out.println("NIT: " + zoo.nit);
                String nuevoNIT = input.nextLine();
                if (zoologico.nit.replace(".", "").equals(nuevoNIT)) {
                    System.out.println("ERROR: Ya existe un zoologico registrado con este NIT.");
                    return;
                }
                System.out.println("Nombre: " + zoo.nombre);
                String nuevoNombre = input.nextLine();

                System.out.println("Siglas: " + zoo.siglas);
                String nuevoSiglas = input.nextLine();
                if (zoologico.siglas.equalsIgnoreCase(nuevoSiglas)) {
                    System.out.println("ERROR: Ya existe un zoologico registrado con estas siglas.");
                    return;
                }

                System.out.println("Ciudad: " + zoo.ciudad);
                String nuevoCiudad = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoNIT.isEmpty()) {
                                } else zoo.nit = nuevoNIT;
                                if (nuevoNombre.isEmpty()) {
                                } else zoo.nombre = nuevoNombre;
                                if (nuevoSiglas.isEmpty()) {
                                } else zoo.siglas = nuevoSiglas;
                                if (nuevoCiudad.isEmpty()) {
                                } else zoo.ciudad = nuevoCiudad;
                                System.out.println("Zoologico editado exitosamente");
                                break;

                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                    return;
                }
            }
            //Codigo para eliminar
            else if(accion.equals("2")) {
                    int eleccion;
                    System.out.println("------------------------------------------");
                    System.out.println("Escoja el zoologico que desea eliminar: \n");
                    eleccion = input.nextInt();
                    zoo = siglasZoo.get(eleccion - 1);

                    while (true) {
                        System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                        String optionE = input.next();
                        if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                            switch (optionE.toUpperCase()) {
                                case "Y":
                                    break;
                                case "N":
                                    return;
                            }
                            break;
                        } else {
                            System.out.println("Opcion invalida\n");
                        }
                    }

                    for (Profesional profesional : profesionales) {
                        if (profesional.zoologico != null && profesional.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                            profesional.zoologico = null;
                        }
                    }
                    for (ZooAmigo zooAmigo : zooAmigos) {
                        if (zooAmigo.zoologico != null && zooAmigo.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                            zooAmigo.zoologico = null;
                        }
                    }
                    for (Bioma bioma : biomas) {
                        if (bioma.zoologico != null && bioma.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                            bioma.zoologico = null;
                        }
                    }
                    zoologicos.remove(zoo);

                    System.out.println("El zoologico se ha eliminado correctamente");
                    return;
                }//Regresar
                else{
                return;
            }
        }
    }
    public static void ciudadZoologico(String ciudadBus) {
        ArrayList<Zoologico> ciudadZoo = new ArrayList<>();
        //Crear lista para no afectar la principal y que se agreguen los elementos que cumplen las condiciones
        for (Zoologico zoologico : zoologicos) {
            if (ciudadBus.equals(zoologico.ciudad) || ciudadBus.equals(zoologico.ciudad.toLowerCase())) {
                ciudadZoo.add(zoologico);
            }else{
                System.out.println("La ciudad no se encuentra registrada");
                return;
            }
        }
        Zoologico zoo;
        int i = 0;
        //Menu para ordenar
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Nit.");
        System.out.println("2. Nombre.");
        System.out.println("3. Siglas.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();

        //Organizar x nit
        switch (option) {
            case "1":
                //Menu ordenar ascendente - descendente
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();

                //Organizar ascendente
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    ciudadZoo.sort((comparadoresZoologico[0]));
                    for (Zoologico zoologico : ciudadZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                }//Organizar descendente
                else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    ciudadZoo.sort(Collections.reverseOrder(comparadoresZoologico[0]));
                    for (Zoologico zoologico : ciudadZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    ciudadZoo.sort((comparadoresZoologico[1]));
                    for (Zoologico zoologico : ciudadZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    ciudadZoo.sort(Collections.reverseOrder(comparadoresZoologico[1]));
                    for (Zoologico zoologico : ciudadZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            //Organizar x siglas
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    ciudadZoo.sort((comparadoresZoologico[2]));
                    for (Zoologico zoologico : ciudadZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    ciudadZoo.sort(Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : ciudadZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }//Menu para editar o eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");
        for (Zoologico zoologico : zoologicos) {
            //Codigo para editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zoologico que desea editar: \n");
                eleccion = input.nextInt();
                zoo = ciudadZoo.get(eleccion-1);

                input.nextLine();
                System.out.println("\nNIT: " + zoo.nit);
                String nuevoNIT = input.nextLine();
                if (zoologico.nit.replace(".", "").equals(nuevoNIT)) {
                    System.out.println("ERROR: Ya existe un zoologico registrado con este NIT.");
                    return;
                }
                System.out.println("Nombre: " + zoo.nombre);
                String nuevoNombre = input.nextLine();

                System.out.println("Siglas: " + zoo.siglas);
                String nuevoSiglas = input.nextLine();
                if (zoologico.siglas.equalsIgnoreCase(nuevoSiglas)) {
                    System.out.println("ERROR: Ya existe un zoologico registrado con estas siglas.");
                    return;
                }
                System.out.println("Ciudad: " + zoo.ciudad);
                String nuevoCiudad = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoNIT.isEmpty()) {
                                } else zoo.nit = nuevoNIT;
                                if (nuevoNombre.isEmpty()) {
                                } else zoo.nombre = nuevoNombre;
                                if (nuevoSiglas.isEmpty()) {
                                } else zoo.siglas = nuevoSiglas;
                                if (nuevoCiudad.isEmpty()) {
                                } else zoo.ciudad = nuevoCiudad;
                                System.out.println("Zoologico editado exitosamente");
                                break;
                            case "N":
                                break;
                        }return;
                    }
                    else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo para eliminar
            else if(accion.equals("2")){
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zoologico que desea eliminar: \n");
                eleccion = input.nextInt();
                zoo = ciudadZoo.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
                for (Profesional profesional : profesionales) {
                    if (profesional.zoologico != null && profesional.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                        profesional.zoologico = null;
                    }
                }
                for (ZooAmigo zooAmigo : zooAmigos) {
                    if (zooAmigo.zoologico != null && zooAmigo.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                        zooAmigo.zoologico = null;
                    }
                }
                for (Bioma bioma : biomas) {
                    if (bioma.zoologico != null && bioma.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                        bioma.zoologico = null;
                    }
                }
                zoologicos.remove(zoo);

                System.out.println("El zoologico se ha eliminado correctamente");
                return;
            }//else que denota la opcion cero para devolverse
            else{
                return;
            }
        }
    }
    public static void mostarTodosZoologicos() {
        ArrayList<Zoologico> copiaZoo = new ArrayList<>(zoologicos);
        Zoologico zoo;
        int i=0;
        int eleccion;
        //menu para ordenar x atributo
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Nit.");
        System.out.println("2. Nombre.");
        System.out.println("3. Siglas.");
        System.out.println("4. Ciudad.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();

        //Menu organizar x nit ascendente - descendente
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();

                //Organizar x nit ascendente
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaZoo.sort((comparadoresZoologico[0]));
                    for (Zoologico zoologico : copiaZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                }
                //Organizar x nit descendente
                else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaZoo.sort(Collections.reverseOrder(comparadoresZoologico[0]));
                    for (Zoologico zoologico : copiaZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();

                //Organizar ascendente
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaZoo.sort((comparadoresZoologico[1]));
                    for (Zoologico zoologico : copiaZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                }
                //Organizar descendente
                else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaZoo.sort(Collections.reverseOrder(comparadoresZoologico[1]));
                    for (Zoologico zoologico : copiaZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();

                //Organizar ascendente
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaZoo.sort((comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                }
                //Organizar descendente
                else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaZoo.sort(Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            //Menu organizar x ciudad ascendente - descendente
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();

                //Organizar ascendente
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaZoo.sort((comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                }
                //Organizar descendente
                else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaZoo.sort(Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaZoo) {
                        i++;
                        System.out.println(i + ". " + zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar " );
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for(Zoologico zoologico: zoologicos){
            //Codigo editar
            if (accion.equals("1")) {
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zoologico que desea editar: \n");
                eleccion = input.nextInt();
                zoo = copiaZoo.get(eleccion - 1);

                input.nextLine();
                System.out.println("NIT: " + zoo.nit);
                String nuevoNIT = input.nextLine();
                if (zoologico.nit.replace(".", "").equals(nuevoNIT)) {
                    System.out.println("ERROR: Ya existe un zoologico registrado con este NIT.");
                    return;
                }

                System.out.println("Nombre: " + zoo.nombre);
                String nuevoNombre = input.nextLine();

                System.out.println("Siglas: " + zoo.siglas);
                String nuevoSiglas = input.nextLine();
                if (zoologico.siglas.equalsIgnoreCase(nuevoSiglas)) {
                    System.out.println("ERROR: Ya existe un zoologico registrado con estas siglas.");
                    return;
                }

                System.out.println("Ciudad: " + zoo.ciudad);
                String nuevoCiudad = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoNIT.isEmpty()) {
                                } else zoo.nit = nuevoNIT;
                                if (nuevoNombre.isEmpty()) {
                                } else zoo.nombre = nuevoNombre;
                                if (nuevoSiglas.isEmpty()) {
                                } else zoo.siglas = nuevoSiglas;
                                if (nuevoCiudad.isEmpty()) {
                                } else zoo.ciudad = nuevoCiudad;
                            case "N":
                                break;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo para eliminar
            else if(accion.equals("2")){
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zoologico que desea eliminar: \n");
                eleccion = input.nextInt();
                zoo = copiaZoo.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
                for (Profesional profesional : profesionales) {
                    if (profesional.zoologico != null && profesional.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                        profesional.zoologico = null;
                    }
                }
                for (ZooAmigo zooAmigo : zooAmigos) {
                    if (zooAmigo.zoologico != null && zooAmigo.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                        zooAmigo.zoologico = null;
                    }
                }
                for (Bioma bioma : biomas) {
                    if (bioma.zoologico != null && bioma.zoologico.nit.replace(".", "").equals(zoo.nit.replace(".", ""))) {
                        bioma.zoologico = null;
                    }
                }
                zoologicos.remove(zoo);

                System.out.println("El zoologico se ha eliminado correctamente");
                return;
            }//Regresar
            else{
                return;
            }
        }
    }

    public static void busquedaBioma() {
        String option;
        label:
        while (true) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
            System.out.println("1. ID.");
            System.out.println("2. Temperatura.");
            System.out.println("3. Humedad.");
            System.out.println("4. Tipo.");
            System.out.println("5. Mostrar todos los biomas.");
            System.out.println("0. Regresar al menu anterior.");
            option = input.next();
            System.out.println();
            switch (option) {
                case "1":
                    String option1;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor maximo.");
                    System.out.println("3. Valor minimo.");
                    System.out.println("4. Rango.");
                    option1 = input.next();
                    if (!option1.equals("1") && !option1.equals("2") && !option1.equals("3") && !option1.equals("4")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option1.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idBioma(1, idBus, 0);
                        break;
                    } else if (option1.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idBioma(2, idBus, 0);
                        break;
                    } else if (option1.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idBioma(3, idBus, 0);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int idBusMin = input.nextInt();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        int idBusMax = input.nextInt();
                        idBioma(4, idBusMin, idBusMax);
                        break;
                    }
                    break;
                case "2":
                    String option2;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor maximo.");
                    System.out.println("3. Valor minimo.");
                    System.out.println("4. Rango.");
                    option2 = input.next();
                    if (!option2.equals("1") && !option2.equals("2") && !option2.equals("3") && !option2.equals("4")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option2.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        double temperaturaBus = input.nextDouble();
                        temperaturaBioma(1, temperaturaBus, 0);
                        break;
                    } else if (option2.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        double temperaturaBus = input.nextDouble();
                        temperaturaBioma(2, temperaturaBus, 0);
                        break;
                    } else if (option2.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        double temperaturaBus = input.nextDouble();
                        temperaturaBioma(3, temperaturaBus, 0);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        double temperaturaBusMin = input.nextDouble();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        double temperaturaBusMax = input.nextDouble();
                        temperaturaBioma(4, temperaturaBusMin, temperaturaBusMax);
                        break;
                    }
                    break;
                case "3":
                    String option3;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option3 = input.next();
                    if (!option3.equals("1") && !option3.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option3.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String humedadBus = input.next();
                        humedadBioma(humedadBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String humedadBus = input.next();
                        humedadBioma(humedadBus);
                        break;
                    }
                    break;
                case "4":
                    String option4;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option4 = input.next();
                    if (!option4.equals("1") && !option4.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option4.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tipoBus = input.next();
                        tipoBioma(tipoBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tipoBus = input.next();
                        tipoBioma(tipoBus);
                        break;
                    }
                    break;
                case "5":
                    mostrarTodosBiomas();
                    break;
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void idBioma(int opcionValor, int idBusMin, int idBusMax) {
        Bioma bio;
        int i = 0;
        int eleccion;
        //Crear listas para no afectar la principal y que se agreguen los elementos que cumplen las condiciones
        ArrayList<Bioma> idBiomaGen = new ArrayList<>();
        //Valor exacto
        if (opcionValor == 1) {
            for (Bioma bioma : biomas) {
                if (idBusMin == bioma.id) {
                    idBiomaGen.add(bioma);

                }else{
                    System.out.println("El ID no se encuentra registrado");
                    return;
                }
            }
        }
        //Valor maximo
        else if (opcionValor == 2) {
            for (Bioma bioma : biomas) {
                if (idBusMin >= bioma.id) {
                    idBiomaGen.add(bioma);
                }else{
                    System.out.println("El ID no se encuentra registrado");
                    return;
                }
            }
        }
        //Valor minimo
        else if (opcionValor == 3) {
            for (Bioma bioma : biomas) {
                if (idBusMin <= bioma.id) {
                    idBiomaGen.add(bioma);
                }else{
                    System.out.println("El ID no se encuentra registrado");
                    return;
                }
            }
        }
        //Rango
        else {
            for (Bioma bioma : biomas) {
                if (idBusMin <= bioma.id && bioma.id <= idBusMax) {
                    idBiomaGen.add(bioma);
                }else{
                    System.out.println("El ID no se encuentra registrado");
                    return;
                }
            }
        }
        //Menu organizar id x atributo
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Temperatura.");
        System.out.println("2. Humedad.");
        System.out.println("3. Tipo de bioma.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();

        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                //Organizar ascendente
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    idBiomaGen.sort(comparadoresBioma[0]);
                    for (Bioma bioma : idBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                }
                //Organizar x temperatura descendente
                else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    idBiomaGen.sort(Collections.reverseOrder(comparadoresBioma[0]));
                    for (Bioma bioma : idBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            //Menu organizar x temperatura ascendente - descendente
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                //Organizar ascendente
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    idBiomaGen.sort(comparadoresBioma[1]);
                    for (Bioma bioma : idBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                }
                //Organizar x temperatura descendente
                else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    idBiomaGen.sort(Collections.reverseOrder(comparadoresBioma[1]));
                    for (Bioma bioma : idBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            //Menu organizar x humedad ascendente - descendente
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                //Organizar ascendente
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    idBiomaGen.sort(comparadoresBioma[2]);
                    for (Bioma bioma : idBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                }
                //Organizar x temperatura descendente
                else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    idBiomaGen.sort(Collections.reverseOrder(comparadoresBioma[2]));
                    for (Bioma bioma : idBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        //Menu editar-eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar " );
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for(Bioma bioma: biomas){
            //Codigo editar
            if (accion.equals("1")) {
                System.out.println("------------------------------------------");
                System.out.println("Escoja el bioma que desea editar: \n");
                eleccion = input.nextInt();
                bio = idBiomaGen.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + bio.id);
                String nuevoID = input.nextLine();
                if (nuevoID.equals(Integer.toString(bioma.id))){
                    System.out.println("ERROR: Ya existe un bioma registrado con este ID.");
                    return;
                }

                System.out.println("Temperatura: " + bio.temperatura);
                String nuevaTemp = input.nextLine();

                System.out.println("Humedad: " + bio.humedad);
                String nuevoHume = input.nextLine();

                System.out.println("Tipo: " + bio.tipo);
                String nuevoTipo = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID.isEmpty()) {
                                } else bio.id = Integer.parseInt(nuevoID);
                                if (nuevaTemp.isEmpty()) {
                                } else bio.temperatura = Double.parseDouble(nuevaTemp);
                                if (nuevoHume.isEmpty()) {
                                } else bio.humedad = nuevoHume;
                                if (nuevoTipo.isEmpty()) {
                                } else bio.tipo = nuevoTipo;
                                System.out.println("Bioma editado exitosamente");
                                break;

                            case "N":
                                break;
                        }return;
                    }
                    else {
                        System.out.println("Opcion invalida\n");
                    }return;
                }
            }//Codigo eliminar
            else if(accion.equals("2")){
                System.out.println("------------------------------------------");
                System.out.println("Escoja el bioma que desea eliminar: \n");
                eleccion = input.nextInt();
                bio = idBiomaGen.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
                for (Zoologico zoologico : zoologicos) {
                    zoologico.biomas.removeIf(bioma1 -> bioma1.id == bio.id);
                }
                for (Profesional profesional : profesionales) {
                    profesional.biomas.removeIf(bioma1 -> bioma1.id == bio.id);
                }
                for (Habitat habitat : habitats) {
                    if (habitat.bioma != null && habitat.bioma.id == bioma.id) {
                        habitat.bioma = null;
                    }
                }
                biomas.remove(bio);

                System.out.println("El bioma se ha eliminado correctamente");
                return;

            }else return;
        }
    }
    public static void temperaturaBioma(int opcionValor, double temperaturaBusMin, double temperaturaBusMax) {
        Bioma bio;
        int i=0;
        //Crear listas para no afectar la principal y que se agreguen los elementos que cumplen las condiciones
        ArrayList<Bioma> tempBiomaGen = new ArrayList<>();
        //Valor exacto
        if (opcionValor == 1) {
            for (Bioma bioma : biomas) {
                if (temperaturaBusMin == bioma.temperatura) {
                    tempBiomaGen.add(bioma);
                }else{
                    System.out.println("La temperatura no se encuentra registrada");
                    return;
                }
            }
        }
        //Valor maximo
        else if (opcionValor == 2) {
            for (Bioma bioma : biomas) {
                if (temperaturaBusMin >= bioma.temperatura) {
                    tempBiomaGen.add(bioma);
                }else{
                    System.out.println("La temperatura no se encuentra registrada");
                    return;
                }
            }
        }
        //Valor minimo
        else if (opcionValor == 3) {
            for (Bioma bioma : biomas) {
                if (temperaturaBusMin <= bioma.temperatura) {
                    tempBiomaGen.add(bioma);
                }else{
                    System.out.println("La temperatura no se encuentra registrada");
                    return;
                }
            }
        }
        //Rango
        else {
            for (Bioma bioma : biomas) {
                if (temperaturaBusMin <= bioma.temperatura && bioma.temperatura <= temperaturaBusMax) {
                    tempBiomaGen.add(bioma);
                }else{
                    System.out.println("La temperatura no se encuentra registrada");
                    return;
                }
            }
        }
        //Menu de atributo a organizar
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Temperatura.");
        System.out.println("3. Humedad.");
        System.out.println("4. Tipo de bioma.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();

        //Menu orgnizar x id ascendente - descendente
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();

                //Organizar ascendente
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    tempBiomaGen.sort(comparadoresBioma[0]);
                    for (Bioma bioma : tempBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                }

                //organizar descendente
                else if (option2.equals("2")) {
                    //valor exacto
                    System.out.println("Descendente");
                    tempBiomaGen.sort(Collections.reverseOrder(comparadoresBioma[0]));
                    for (Bioma bioma : tempBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.print("Opcion incorrecta");
                    return;
                }
                break;
            //Menu organizar x temperatura ascendente - descendente
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                //Organizar ascendente
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    tempBiomaGen.sort((comparadoresBioma[1]));
                    for (Bioma bioma : tempBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                }
                //Organizar descendente
                else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    tempBiomaGen.sort(Collections.reverseOrder(comparadoresBioma[1]));
                    for (Bioma bioma : tempBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            //Menu organizar x humedad ascendente - descendente
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    tempBiomaGen.sort((comparadoresBioma[2]));
                    for (Bioma bioma : tempBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    tempBiomaGen.sort(Collections.reverseOrder(comparadoresBioma[2]));
                    for (Bioma bioma : tempBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    tempBiomaGen.sort((comparadoresBioma[3]));
                    for (Bioma bioma : tempBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    tempBiomaGen.sort(Collections.reverseOrder(comparadoresBioma[3]));
                    for (Bioma bioma : tempBiomaGen) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Bioma bioma : tempBiomaGen) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el bioma que desea editar: \n");
                eleccion = input.nextInt();
                bio = tempBiomaGen.get(eleccion - 1);
                input.nextLine();

                System.out.println("\nID: " + bio.id);
                String nuevoID1 = input.nextLine();
                if (nuevoID1.equals(Integer.toString(bioma.id))) {
                    System.out.println("ERROR: Ya existe un bioma registrado con este ID.");
                    return;
                }
                System.out.println("Temperatura: " + bio.temperatura);
                String nuevaTemp1 = input.nextLine();
                System.out.println("Humedad: " + bio.humedad);
                String nuevoHume = input.nextLine();

                System.out.println("Tipo: " + bio.tipo);
                String nuevoTipo = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID1.isEmpty()) {
                                } else {bio.id = Integer.parseInt(nuevoID1);
                                }if (nuevaTemp1.isEmpty()) {
                                } else {bio.temperatura = Double.parseDouble(nuevaTemp1);
                                }if (nuevoHume.isEmpty()) {
                                } else bio.humedad = nuevoHume;
                                if (nuevoTipo.isEmpty()) {
                                } else bio.tipo = nuevoTipo;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if(accion.equals("2")){
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el bioma que desea eliminar: \n");
                eleccion = input.nextInt();
                bio = tempBiomaGen.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    }else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Zoologico zoologico : zoologicos) {
                    zoologico.biomas.removeIf(bioma1 -> bioma1.id == bio.id);
                }
                for (Profesional profesional : profesionales) {
                    profesional.biomas.removeIf(bioma1 -> bioma1.id == bioma.id);
                }
                for (Habitat habitat : habitats) {
                    if (habitat.bioma != null && habitat.bioma.id == bioma.id) {
                        habitat.bioma = null;
                    }
                }
                biomas.remove(bio);

                System.out.println("El bioma se ha eliminado correctamente");
                return;
            }else return;
        }
    }
    public static void humedadBioma(String humedadBus) {
        Bioma bio;
        int i =0;
        ArrayList<Bioma> humedadBio = new ArrayList<>();
        for (Bioma bioma : biomas) {
            if (humedadBus.equals(bioma.humedad) || humedadBus.equals(bioma.humedad.toLowerCase())) {
                humedadBio.add(bioma);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Temperatura.");
        System.out.println("3. Tipo.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    humedadBio.sort((comparadoresBioma[0]));
                    for (Bioma bioma : humedadBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    humedadBio.sort(Collections.reverseOrder(comparadoresBioma[0]));
                    for (Bioma bioma : humedadBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    humedadBio.sort((comparadoresBioma[1]));
                    for (Bioma bioma : humedadBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    humedadBio.sort(Collections.reverseOrder(comparadoresBioma[1]));
                    for (Bioma bioma : humedadBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    humedadBio.sort((comparadoresBioma[3]));
                    for (Bioma bioma : humedadBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    humedadBio.sort(Collections.reverseOrder(comparadoresBioma[3]));
                    for (Bioma bioma : humedadBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        //Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Bioma bioma : humedadBio) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el bioma que desea editar: \n");
                eleccion = input.nextInt();
                bio = humedadBio.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + bio.id);
                String nuevoID = input.nextLine();
                if (nuevoID.equals(Integer.toString(bioma.id))) {
                    System.out.println("ERROR: Ya existe un bioma registrado con este ID.");
                    return;
                }

                System.out.println("Temperatura: " + bio.temperatura);
                String nuevaTemp1 = input.nextLine();

                System.out.println("Humedad: " + bio.humedad);
                String nuevoHume = input.nextLine();

                System.out.println("Tipo: " + bio.tipo);
                String nuevoTipo = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID.isEmpty()) {
                                } else { bio.id = Integer.parseInt(nuevoID);
                                }if (nuevaTemp1.isEmpty()) {
                                } else { bio.temperatura = Double.parseDouble(nuevaTemp1);
                                }if (nuevoHume.isEmpty()) {
                                } else bio.humedad = nuevoHume;
                                if (nuevoTipo.isEmpty()) {
                                } else bio.tipo = nuevoTipo;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el bioma que desea eliminar: \n");
                eleccion = input.nextInt();
                bio = humedadBio.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Zoologico zoologico : zoologicos) {
                    zoologico.biomas.removeIf(bioma1 -> bioma1.id == bio.id);
                }

                for (Profesional profesional : profesionales) {
                    profesional.biomas.removeIf(bioma1 -> bioma1.id == bioma.id);
                }

                for (Habitat habitat : habitats) {
                    if (habitat.bioma != null && habitat.bioma.id == bioma.id) {
                        habitat.bioma = null;
                    }
                }
                biomas.remove(bio);

                System.out.println("El bioma se ha eliminado correctamente");
                return;
            }else {
                return;
            }
        }
    }
    public static void tipoBioma(String tipoBus) {
        Bioma bio;
        int i=0;
        ArrayList<Bioma> tipoBio = new ArrayList<>();
        for (Bioma bioma : biomas) {
            if (tipoBus.equals(bioma.humedad) || tipoBus.equals(bioma.humedad.toLowerCase())) {
                tipoBio.add(bioma);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Temperatura.");
        System.out.println("3. Humedad.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    tipoBio.sort((comparadoresBioma[0]));
                    for (Bioma bioma : tipoBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    tipoBio.sort(Collections.reverseOrder(comparadoresBioma[0]));
                    for (Bioma bioma : tipoBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    tipoBio.sort((comparadoresBioma[1]));
                    for (Bioma bioma : tipoBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    tipoBio.sort(Collections.reverseOrder(comparadoresBioma[1]));
                    for (Bioma bioma : tipoBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    tipoBio.sort((comparadoresBioma[2]));
                    for (Bioma bioma : tipoBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    tipoBio.sort(Collections.reverseOrder(comparadoresBioma[2]));
                    for (Bioma bioma : tipoBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");
        for (Bioma bioma : tipoBio) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el bioma que desea editar: \n");
                eleccion = input.nextInt();
                bio = tipoBio.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + bio.id);
                String nuevoID1 = input.nextLine();
                if (nuevoID1.equals(Integer.toString(bioma.id))) {
                    System.out.println("ERROR: Ya existe un bioma registrado con este ID.");
                    return;
                }

                System.out.println("Temperatura: " + bio.temperatura);
                String nuevaTemp1 = input.nextLine();

                System.out.println("Humedad: " + bio.humedad);
                String nuevoHume = input.nextLine();

                System.out.println("Tipo: " + bio.tipo);
                String nuevoTipo = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID1.isEmpty()) {
                                } else { bio.id = Integer.parseInt(nuevoID1);
                                }if (nuevaTemp1.isEmpty()) {
                                } else { bio.temperatura = Double.parseDouble(nuevaTemp1);
                                }if (nuevoHume.isEmpty()) {
                                } else bio.humedad = nuevoHume;
                                if (nuevoTipo.isEmpty()) {
                                } else bio.tipo = nuevoTipo;
                            case "N":
                                break;
                        }return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el bioma que desea eliminar: \n");
                eleccion = input.nextInt();
                bio = tipoBio.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Zoologico zoologico : zoologicos) {
                    zoologico.biomas.removeIf(bioma1 -> bioma1.id == bio.id);
                }
                for (Profesional profesional : profesionales) {
                    profesional.biomas.removeIf(bioma1 -> bioma1.id == bioma.id);
                }
                for (Habitat habitat : habitats) {
                    if (habitat.bioma != null && habitat.bioma.id == bioma.id) {
                        habitat.bioma = null;
                    }
                }
                biomas.remove(bio);

                System.out.println("El bioma se ha eliminado correctamente");
                return;
            }else {
                return;
            }
        }
    }
    public static void mostrarTodosBiomas() {
        Bioma bio;
        int i=0;
        ArrayList<Bioma> copiaBio = new ArrayList<>(biomas);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Id.");
        System.out.println("2. Temperatura.");
        System.out.println("3. Humedad.");
        System.out.println("4. Tipo de bioma.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaBio.sort((comparadoresBioma[0]));
                    for (Bioma bioma : copiaBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaBio.sort(Collections.reverseOrder(comparadoresBioma[0]));
                    for (Bioma bioma : copiaBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaBio.sort((comparadoresBioma[1]));
                    for (Bioma bioma : copiaBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaBio.sort(Collections.reverseOrder(comparadoresBioma[1]));
                    for (Bioma bioma : copiaBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaBio.sort((comparadoresBioma[2]));
                    for (Bioma bioma : copiaBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaBio.sort(Collections.reverseOrder(comparadoresBioma[2]));
                    for (Bioma bioma : copiaBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaBio.sort((comparadoresBioma[3]));
                    for (Bioma bioma : copiaBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaBio.sort(Collections.reverseOrder(comparadoresBioma[3]));
                    for (Bioma bioma : copiaBio) {
                        i++;
                        System.out.println(i + ". " + bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");
        //Codigo editar
        for (Bioma bioma : copiaBio) {
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el bioma que desea editar: \n");
                eleccion = input.nextInt();
                bio = copiaBio.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + bio.id);
                String nuevoID1 = input.nextLine();
                if (nuevoID1.equals(Integer.toString(bioma.id))) {
                    System.out.println("ERROR: Ya existe un bioma registrado con este ID.");
                    return;
                }
                System.out.println("Temperatura: " + bio.temperatura);
                String nuevaTemp1 = input.nextLine();

                System.out.println("Humedad: " + bio.humedad);
                String nuevoHume = input.nextLine();

                System.out.println("Tipo: " + bio.tipo);
                String nuevoTipo = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID1.isEmpty()) {
                                } else { bio.id = Integer.parseInt(nuevoID1);
                                }if (nuevaTemp1.isEmpty()) {
                                } else { bio.temperatura = Double.parseDouble(nuevaTemp1);
                                }if (nuevoHume.isEmpty()) {
                                } else bio.humedad = nuevoHume;
                                if (nuevoTipo.isEmpty()) {
                                } else bio.tipo = nuevoTipo;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el bioma que desea eliminar: \n");
                eleccion = input.nextInt();
                bio = copiaBio.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
                for (Zoologico zoologico : zoologicos) {
                    zoologico.biomas.removeIf(bioma1 -> bioma1.id == bio.id);
                }
                for (Profesional profesional : profesionales) {
                    profesional.biomas.removeIf(bioma1 -> bioma1.id == bioma.id);
                }
                for (Habitat habitat : habitats) {
                    if (habitat.bioma != null && habitat.bioma.id == bioma.id) {
                        habitat.bioma = null;
                    }
                }
                biomas.remove(bio);

                System.out.println("El bioma se ha eliminado correctamente");
                return;
            } else return;
        }
    }

    public static void busquedaHabitat() {
        String option;
        label:
        while (true) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
            System.out.println("1. ID.");
            System.out.println("2. Tipo de suelo.");
            System.out.println("3. Vegetacion.");
            System.out.println("4. Tipo de jaula.");
            System.out.println("5. Mostrar todos los habitats.");
            System.out.println("0. Regresar al menu anterior.");
            option = input.next();
            System.out.println();
            switch (option) {
                case "1":
                    String option1;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor maximo.");
                    System.out.println("3. Valor minimo.");
                    System.out.println("4. Rango.");
                    option1 = input.next();
                    if (!option1.equals("1") && !option1.equals("2") && !option1.equals("3") && !option1.equals("4")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option1.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idHabitat(1, idBus, 0);
                        break;
                    } else if (option1.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idHabitat(2, idBus, 0);
                        break;
                    } else if (option1.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idHabitat(3, idBus, 0);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int idBusMin = input.nextInt();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        int idBusMax = input.nextInt();
                        idHabitat(4, idBusMin, idBusMax);
                        break;
                    }
                    break;
                case "2":
                    String option2;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option2 = input.next();
                    if (!option2.equals("1") && !option2.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option2.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tipoSueloBus = input.next();
                        tipoSueloHabitat(tipoSueloBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tipoSueloBus = input.next();
                        tipoSueloHabitat(tipoSueloBus);
                        break;
                    }
                    break;
                case "3":
                    String option3;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option3 = input.next();
                    if (!option3.equals("1") && !option3.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option3.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String vegetacionBus = input.next();
                        vegetacionHabitat(vegetacionBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String vegetacionBus = input.next();
                        vegetacionHabitat(vegetacionBus);
                        break;
                    }
                    break;
                case "4":
                    String option4;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option4 = input.next();
                    if (!option4.equals("1") && !option4.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option4.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tipoJaulaBus = input.next();
                        tipoSueloHabitat(tipoJaulaBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tipoJaulaBus = input.next();
                        tipoJaulaHabitat(tipoJaulaBus);
                        break;
                    }
                    break;
                case "5":
                    mostrarTodosHabitats();
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void idHabitat(int opcionValor, int idBusMin, int idBusMax) {
        Habitat hab;
        int i = 0;
        ArrayList<Habitat> idHabitatGen = new ArrayList<>();
        if (opcionValor == 1) {
            for (Habitat habitat : habitats) {
                if (idBusMin == habitat.id) {
                    idHabitatGen.add(habitat);
                }
            }
        } else if (opcionValor == 2) {
            for (Habitat habitat : habitats) {
                if (idBusMin >= habitat.id) {
                    idHabitatGen.add(habitat);
                }
            }
        } else if (opcionValor == 3) {
            for (Habitat habitat : habitats) {
                if (idBusMin <= habitat.id) {
                    idHabitatGen.add(habitat);
                }
            }
        } else {
            for (Habitat habitat : habitats) {
                if (idBusMin <= habitat.id && habitat.id <= idBusMax) {
                    idHabitatGen.add(habitat);
                }
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Tipo de suelo.");
        System.out.println("3. Vegetacion.");
        System.out.println("4. Tipo de jaula.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    idHabitatGen.sort((comparadoresHabitat[0]));
                    for (Habitat habitat : idHabitatGen) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    if (opcionValor == 1) {
                        System.out.println("Descendente");
                        idHabitatGen.sort(Collections.reverseOrder(comparadoresHabitat[0]));
                        for (Habitat habitat : idHabitatGen) {
                            i++;
                            System.out.println(i + ". " + habitat);
                        }
                    } else {
                        System.out.println("Opcion incorrecta");
                        return;
                    }
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    idHabitatGen.sort((comparadoresHabitat[1]));
                    for (Habitat habitat : idHabitatGen) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    idHabitatGen.sort(Collections.reverseOrder(comparadoresHabitat[1]));
                    for (Habitat habitat : idHabitatGen) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    idHabitatGen.sort((comparadoresHabitat[2]));
                    for (Habitat habitat : idHabitatGen) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }

                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    idHabitatGen.sort(Collections.reverseOrder(comparadoresHabitat[2]));
                    for (Habitat habitat : idHabitatGen) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }

                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    idHabitatGen.sort((comparadoresHabitat[3]));
                    for (Habitat habitat : idHabitatGen) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }

                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    idHabitatGen.sort(Collections.reverseOrder(comparadoresHabitat[3]));
                    for (Habitat habitat : idHabitatGen) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }

                } else {
                    System.out.println("Opcion invalida");
                    return;
                }
                break;
            default:
                return;
        }
        //Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        //Codigo editar
        for (Habitat habitat : idHabitatGen) {
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el habitat que desea editar: \n");
                eleccion = input.nextInt();
                hab = idHabitatGen.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + hab.id);
                String nuevoID1 = input.nextLine();
                if (nuevoID1.equals(Integer.toString(habitat.id))) {
                    System.out.println("ERROR: Ya existe un habitat registrado con este ID.");
                    return;
                }

                System.out.println("Tipo de suelo: " + hab.tipoSuelo);
                String nuevoSuelo = input.nextLine();

                System.out.println("Vegetacion: " + hab.vegetacion);
                String nuevaVege = input.nextLine();

                System.out.println("Tipo de jaula: " + hab.tipoJaula);
                String nuevaJau = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID1.isEmpty()) {
                                } else {
                                    habitat.id = Integer.parseInt(nuevoID1);
                                }
                                if (nuevoSuelo.isEmpty()) {
                                } else hab.tipoSuelo = nuevoSuelo;
                                if (nuevaVege.isEmpty()) {
                                } else hab.vegetacion = nuevaVege;
                                if (nuevaJau.isEmpty()) {
                                } else hab.tipoJaula = nuevaJau;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el habitat que desea eliminar: \n");
                eleccion = input.nextInt();
                hab = idHabitatGen.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Bioma bioma : biomas) {
                    bioma.habitats.removeIf(habitat1 -> habitat1.id == hab.id);

                }
                for (Tecnico tecnico : tecnicos) {
                    tecnico.habitats.removeIf(habitat1 -> habitat1.id == hab.id);

                }
                for (Animal animal : animales) {
                    if (animal.habitat != null && animal.habitat.id == hab.id) {
                        animal.habitat = null;
                    }
                }
                habitats.remove(hab);

                System.out.println("El habitat se ha eliminado correctamente");
                return;
            } else return;
        }
    }
    public static void tipoSueloHabitat(String tipoSueloBus) {
        Habitat hab;
        int i=0;
        ArrayList<Habitat> tipoSueloHab = new ArrayList<>();
        for (Habitat habitat : habitats) {
            if (tipoSueloBus.equals(habitat.tipoSuelo) || tipoSueloBus.equals(habitat.tipoSuelo.toLowerCase())) {
                tipoSueloHab.add(habitat);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Vegetacion.");
        System.out.println("3. Tipo de jaula.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    tipoSueloHab.sort((comparadoresHabitat[0]));
                    for (Habitat habitat : tipoSueloHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    tipoSueloHab.sort(Collections.reverseOrder(comparadoresHabitat[0]));
                    for (Habitat habitat : tipoSueloHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    tipoSueloHab.sort((comparadoresHabitat[2]));
                    for (Habitat habitat : tipoSueloHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    tipoSueloHab.sort(Collections.reverseOrder(comparadoresHabitat[2]));
                    for (Habitat habitat : tipoSueloHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    tipoSueloHab.sort((comparadoresHabitat[3]));
                    for (Habitat habitat : tipoSueloHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    tipoSueloHab.sort(Collections.reverseOrder(comparadoresHabitat[3]));
                    for (Habitat habitat : tipoSueloHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Habitat habitat : tipoSueloHab) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el habitat que desea editar: \n");
                eleccion = input.nextInt();
                hab = tipoSueloHab.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + hab.id);
                String nuevoID1 = input.nextLine();
                if (nuevoID1.equals(Integer.toString(habitat.id))) {
                    System.out.println("ERROR: Ya existe un habitat registrado con este ID.");
                    return;
                }

                System.out.println("Tipo de suelo: " + hab.tipoSuelo);
                String nuevoSuelo = input.nextLine();

                System.out.println("Vegetacion: " + hab.vegetacion);
                String nuevaVege = input.nextLine();

                System.out.println("Tipo de jaula: " + hab.tipoJaula);
                String nuevaJau = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID1.isEmpty()) {
                                } else {hab.id = Integer.parseInt(nuevoID1);
                                }if (nuevoSuelo.isEmpty()) {
                                } else hab.tipoSuelo = nuevoSuelo;
                                if (nuevaVege.isEmpty()) {
                                } else hab.vegetacion = nuevaVege;
                                if (nuevaJau.isEmpty()) {
                                } else hab.tipoJaula = nuevaJau;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el habitat que desea eliminar: \n");
                eleccion = input.nextInt();
                hab = tipoSueloHab.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Bioma bioma : biomas) {
                    bioma.habitats.removeIf(habitat1 -> habitat1.id == habitat.id);

                }
                for (Tecnico tecnico : tecnicos) {
                    tecnico.habitats.removeIf(habitat1 -> habitat1.id == habitat.id);

                }
                for (Animal animal : animales) {
                    if (animal.habitat != null && animal.habitat.id == habitat.id) {
                        animal.habitat = null;
                    }
                }
                habitats.remove(hab);

                System.out.println("El habitat se ha eliminado correctamente");
                return;
            } else return;
        }
    }
    public static void vegetacionHabitat(String vegetacionBus) {
        Habitat hab;
        int i=0;
        ArrayList<Habitat> vegetacionHab = new ArrayList<>();
        for (Habitat habitat : habitats) {
            if (vegetacionBus.equals(habitat.vegetacion) || vegetacionBus.equals(habitat.vegetacion.toLowerCase())) {
                vegetacionHab.add(habitat);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Tipo de suelo.");
        System.out.println("3. Vegetacion.");
        System.out.println("4. Tipo de jaula.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    vegetacionHab.sort((comparadoresHabitat[0]));
                    for (Habitat habitat : vegetacionHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    vegetacionHab.sort(Collections.reverseOrder(comparadoresHabitat[0]));
                    for (Habitat habitat : vegetacionHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    vegetacionHab.sort((comparadoresHabitat[1]));
                    for (Habitat habitat : vegetacionHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    vegetacionHab.sort(Collections.reverseOrder(comparadoresHabitat[1]));
                    for (Habitat habitat : vegetacionHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    vegetacionHab.sort((comparadoresHabitat[3]));
                    for (Habitat habitat : vegetacionHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    vegetacionHab.sort(Collections.reverseOrder(comparadoresHabitat[3]));
                    for (Habitat habitat : vegetacionHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Habitat habitat : vegetacionHab) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el habitat que desea editar: \n");
                eleccion = input.nextInt();
                hab = vegetacionHab.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + hab.id);
                String nuevoID1 = input.nextLine();
                if (nuevoID1.equals(Integer.toString(habitat.id))) {
                    System.out.println("ERROR: Ya existe un habitat registrado con este ID.");
                    return;
                }

                System.out.println("Tipo de suelo: " + hab.tipoSuelo);
                String nuevoSuelo = input.nextLine();

                System.out.println("Vegetacion: " + hab.vegetacion);
                String nuevaVege = input.nextLine();

                System.out.println("Tipo de jaula: " + hab.tipoJaula);
                String nuevaJau = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID1.isEmpty()) {
                                } else {hab.id = Integer.parseInt(nuevoID1);
                                }if (nuevoSuelo.isEmpty()) {
                                } else hab.tipoSuelo = nuevoSuelo;
                                if (nuevaVege.isEmpty()) {
                                } else hab.vegetacion = nuevaVege;
                                if (nuevaJau.isEmpty()) {
                                } else hab.tipoJaula = nuevaJau;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el habitat que desea eliminar: \n");
                eleccion = input.nextInt();
                hab = vegetacionHab.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Bioma bioma : biomas) {
                    bioma.habitats.removeIf(habitat1 -> habitat1.id == habitat.id);
                }
                for (Tecnico tecnico : tecnicos) {
                    tecnico.habitats.removeIf(habitat1 -> habitat1.id == habitat.id);
                }
                for (Animal animal : animales) {
                    if (animal.habitat != null && animal.habitat.id == habitat.id) {
                        animal.habitat = null;
                    }
                }
                habitats.remove(hab);

                System.out.println("El habitat se ha eliminado correctamente");
            } else return;
        }
    }
    public static void tipoJaulaHabitat(String tipoJaulaBus) {
        Habitat hab;
        int i=0;
        ArrayList<Habitat> tipoJaulaHab = new ArrayList<>();
        for (Habitat habitat : habitats) {
            if (tipoJaulaBus.equals(habitat.tipoJaula) || tipoJaulaBus.equals(habitat.tipoJaula.toLowerCase())) {
                tipoJaulaHab.add(habitat);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Tipo de suelo.");
        System.out.println("3. Vegetacion.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    tipoJaulaHab.sort((comparadoresHabitat[0]));
                    for (Habitat habitat : tipoJaulaHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    tipoJaulaHab.sort(Collections.reverseOrder(comparadoresHabitat[0]));
                    for (Habitat habitat : tipoJaulaHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    tipoJaulaHab.sort((comparadoresHabitat[1]));
                    for (Habitat habitat : tipoJaulaHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    tipoJaulaHab.sort(Collections.reverseOrder(comparadoresHabitat[1]));
                    for (Habitat habitat : tipoJaulaHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    tipoJaulaHab.sort((comparadoresHabitat[2]));
                    for (Habitat habitat : tipoJaulaHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    tipoJaulaHab.sort(Collections.reverseOrder(comparadoresHabitat[2]));
                    for (Habitat habitat : tipoJaulaHab) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Habitat habitat : tipoJaulaHab) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el habitat que desea editar: \n");
                eleccion = input.nextInt();
                hab = tipoJaulaHab.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + hab.id);
                String nuevoID1 = input.nextLine();
                if (nuevoID1.equals(Integer.toString(habitat.id))) {
                    System.out.println("ERROR: Ya existe un habitat registrado con este ID.");
                    return;
                }

                System.out.println("Tipo de suelo: " + hab.tipoSuelo);
                String nuevoSuelo = input.nextLine();

                System.out.println("Vegetacion: " + hab.vegetacion);
                String nuevaVege = input.nextLine();

                System.out.println("Tipo de jaula: " + hab.tipoJaula);
                String nuevaJau = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID1.isEmpty()) {
                                } else { hab.id = Integer.parseInt(nuevoID1);
                                }if (nuevoSuelo.isEmpty()) {
                                } else hab.tipoSuelo = nuevoSuelo;
                                if (nuevaVege.isEmpty()) {
                                } else hab.vegetacion = nuevaVege;
                                if (nuevaJau.isEmpty()) {
                                } else hab.tipoJaula = nuevaJau;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el habitat que desea eliminar: \n");
                eleccion = input.nextInt();
                hab = tipoJaulaHab.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Bioma bioma : biomas) {
                    bioma.habitats.removeIf(habitat1 -> habitat1.id == habitat.id);

                }
                for (Tecnico tecnico : tecnicos) {
                    tecnico.habitats.removeIf(habitat1 -> habitat1.id == habitat.id);
                }
                for (Animal animal : animales) {
                    if (animal.habitat != null && animal.habitat.id == habitat.id) {
                        animal.habitat = null;
                    }
                }
                habitats.remove(hab);

                System.out.println("El habitat se ha eliminado correctamente");
            } else return;
        }
    }
    public static void mostrarTodosHabitats() {
        Habitat hab;
        int i = 0;
        ArrayList<Habitat> copiaHabitat = new ArrayList<>(habitats);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Id.");
        System.out.println("2. Tipo de suelo.");
        System.out.println("3. Vegetacion.");
        System.out.println("4. Tipo de jaula.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaHabitat.sort((comparadoresHabitat[0]));
                    for (Habitat habitat : copiaHabitat) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaHabitat.sort(Collections.reverseOrder(comparadoresHabitat[0]));
                    for (Habitat habitat : copiaHabitat) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaHabitat.sort((comparadoresHabitat[1]));
                    for (Habitat habitat : copiaHabitat) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaHabitat.sort(Collections.reverseOrder(comparadoresHabitat[1]));
                    for (Habitat habitat : copiaHabitat) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaHabitat.sort((comparadoresHabitat[2]));
                    for (Habitat habitat : copiaHabitat) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaHabitat.sort(Collections.reverseOrder(comparadoresHabitat[2]));
                    for (Habitat habitat : copiaHabitat) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaHabitat.sort((comparadoresHabitat[3]));
                    for (Habitat habitat : copiaHabitat) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaHabitat.sort(Collections.reverseOrder(comparadoresHabitat[3]));
                    for (Habitat habitat : copiaHabitat) {
                        i++;
                        System.out.println(i + ". " + habitat);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");
        for (Habitat habitat : copiaHabitat) {
        //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el habitat que desea editar: \n");
                eleccion = input.nextInt();
                hab = copiaHabitat.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + hab.id);
                String nuevoID1 = input.nextLine();
                if (nuevoID1.equals(Integer.toString(habitat.id))){
                    System.out.println("ERROR: Ya existe un habitat registrado con este ID.");
                    return;
                }

                System.out.println("Tipo de suelo: " + hab.tipoSuelo);
                String nuevoSuelo = input.nextLine();

                System.out.println("Vegetacion: " + hab.vegetacion);
                String nuevaVege = input.nextLine();

                System.out.println("Tipo de jaula: " + hab.tipoJaula);
                String nuevaJau = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID1.isEmpty()) {
                                } else { hab.id = Integer.parseInt(nuevoID1);
                                }if (nuevoSuelo.isEmpty()) {
                                } else hab.tipoSuelo = nuevoSuelo;
                                if (nuevaVege.isEmpty()) {
                                } else hab.vegetacion = nuevaVege;
                                if (nuevaJau.isEmpty()) {
                                } else hab.tipoJaula = nuevaJau;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el habitat que desea eliminar: \n");
                eleccion = input.nextInt();
                hab = copiaHabitat.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                        case "Y":
                            break;
                        case "N":
                            return;
                        }
                        break;
                    }else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Bioma bioma : biomas) {
                    bioma.habitats.removeIf(habitat1 -> habitat1.id == habitat.id);
                }
                for (Tecnico tecnico : tecnicos) {
                    tecnico.habitats.removeIf(habitat1 -> habitat1.id == habitat.id);
                }
                for (Animal animal : animales) {
                    if (animal.habitat != null && animal.habitat.id == habitat.id) {
                        animal.habitat = null;
                    }
                }
                habitats.remove(hab);

                System.out.println("El habitat se ha eliminado correctamente");
            }else return;
        }
    }

    public static void busquedaAnimal() {
        String option;
        label:
        while (true) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
            System.out.println("1. ID.");
            System.out.println("2. Especie.");
            System.out.println("3. Nivel de agresividad.");
            System.out.println("4. Alimentacion.");
            System.out.println("5. Mostrar todos los habitats.");
            System.out.println("0. Regresar al menu anterior.");
            System.out.println();
            option = input.next();
            System.out.println();
            switch (option) {
                case "1":
                    String option1;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor maximo.");
                    System.out.println("3. Valor minimo.");
                    System.out.println("4. Rango.");
                    option1 = input.next();
                    if (!option1.equals("1") && !option1.equals("2") && !option1.equals("3") && !option1.equals("4")) {
                        System.out.println("Opcion incorrecta");
                    }
                    else if (option1.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idAnimal(1, idBus, 0);
                        break;
                    } else if (option1.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idAnimal(2, idBus, 0);
                        break;
                    } else if (option1.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idAnimal(3, idBus, 0);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int idBusMin = input.nextInt();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        int idBusMax = input.nextInt();
                        idAnimal(4, idBusMin, idBusMax);
                        break;
                    }
                    break;
                case "2":
                    String option2;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option2 = input.next();
                    if (!option2.equals("1") && !option2.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option2.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String especieBus = input.next();
                        especieAnimal(especieBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String especieBus = input.next();
                        especieAnimal(especieBus);
                        break;
                    }
                    break;
                case "3":
                    String option3;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor maximo.");
                    System.out.println("3. Valor minimo.");
                    System.out.println("4. Rango.");
                    option3 = input.next();
                    if (!option3.equals("1") && !option3.equals("2") && !option3.equals("3") && !option3.equals("4")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option3.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int nivelAgresividadBus = input.nextInt();
                        nivelAgresividadAnimal(1, nivelAgresividadBus, 0);
                        break;
                    } else if (option3.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int nivelAgresividadBus = input.nextInt();
                        nivelAgresividadAnimal(2, nivelAgresividadBus, 0);
                        break;
                    } else if (option3.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int nivelAgresividadBus = input.nextInt();
                        nivelAgresividadAnimal(3, nivelAgresividadBus, 0);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int nivelAgresividadBusMin = input.nextInt();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        int nivelAgresividadBusMax = input.nextInt();
                        nivelAgresividadAnimal(4, nivelAgresividadBusMin, nivelAgresividadBusMax);
                        break;
                    }
                    break;
                case "4":
                    String option4;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option4 = input.next();
                    if (!option4.equals("1") && !option4.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option4.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String alimentacionBus = input.next();
                        alimentacionAnimal(alimentacionBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String alimentacionBus = input.next();
                        alimentacionAnimal(alimentacionBus);
                        break;
                    }
                    break;
                case "5":
                    mostrarTodosAnimales();
                    break;
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void idAnimal(int opcionValor, int idBusMin, int idBusMax) {
        Animal ani;
        int i =0;
        ArrayList<Animal> idAnimalGen = new ArrayList<>();
        if (opcionValor == 1) {
            for (Animal animal : animales) {
                if (idBusMin == animal.id) {
                    idAnimalGen.add(animal);
                }
            }
        }
        else if (opcionValor == 2) {
            for (Animal animal : animales) {
                if (idBusMin >= animal.id) {
                    idAnimalGen.add(animal);
                }
            }
        }
        else if (opcionValor == 3) {
            for (Animal animal : animales) {
                if (idBusMin <= animal.id) {
                    idAnimalGen.add(animal);
                }
            }
        }
        else {
            for (Animal animal : animales) {
                if (idBusMin <= animal.id && animal.id <= idBusMax) {
                    idAnimalGen.add(animal);
                }
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Especie.");
        System.out.println("3. Nivel de agresividad.");
        System.out.println("4. Alimentacion.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    idAnimalGen.sort((comparadoresAnimal[0]));
                    for (Animal animal : idAnimalGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    idAnimalGen.sort(Collections.reverseOrder(comparadoresAnimal[0]));
                    for (Animal animal : idAnimalGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }

                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    idAnimalGen.sort((comparadoresAnimal[1]));
                    for (Animal animal : idAnimalGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }

                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    idAnimalGen.sort(Collections.reverseOrder(comparadoresAnimal[1]));
                    for (Animal animal : idAnimalGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }

                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    idAnimalGen.sort((comparadoresAnimal[2]));
                    for (Animal animal : idAnimalGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    idAnimalGen.sort(Collections.reverseOrder(comparadoresAnimal[2]));
                    for (Animal animal : idAnimalGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }

                } else {
                    System.out.println("Opcion invalida");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    idAnimalGen.sort((comparadoresAnimal[3]));
                    for (Animal animal : idAnimalGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    idAnimalGen.sort(Collections.reverseOrder(comparadoresAnimal[3]));
                    for (Animal animal : idAnimalGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }

                }
                break;
            default:
                return;
        }
         //Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Animal animal : idAnimalGen) {
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el animal que desea editar: \n");
                eleccion = input.nextInt();
                ani = idAnimalGen.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + ani.id);
                String nuevoID = input.nextLine();
                if(nuevoID.equals(Integer.toString(animal.id))){
                    System.out.println("ERROR: El ID ya se encuentra registrado");
                }

                System.out.println("Nombre: " + ani.especie);
                String nuevoEspecie = input.nextLine();

                System.out.println("Nivel de agresividad: " + ani.nivelAgresividad);
                String nuevonivelAgresividad = input.nextLine();

                System.out.println("Alimentacion: " + ani.alimentacion);
                String nuevoAlimentacion = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID.isEmpty()) {
                                } else ani.id = Integer.parseInt(nuevoID);
                                if (nuevoEspecie.isEmpty()) {
                                } else ani.especie = nuevoEspecie;
                                if (nuevonivelAgresividad.isEmpty()) {
                                } else ani.nivelAgresividad = Integer.parseInt(nuevonivelAgresividad);
                                if (nuevoAlimentacion.isEmpty()) {
                                } else ani.alimentacion = nuevoAlimentacion;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el animal que desea eliminar: \n");
                eleccion = input.nextInt();
                ani = idAnimalGen.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Habitat habitat : habitats) {
                    habitat.animales.removeIf(animal1 -> animal1.id == ani.id);
                }
                for (ZooAmigo zooAmigo : zooAmigos) {
                    zooAmigo.animales.removeIf(animal1 -> animal1.id == ani.id);
                }

                animales.remove(ani);

                System.out.println("El animal se ha eliminado correctamente");
                return;
            } else return;
        }
    }
    public static void especieAnimal(String especieBus) {
        Animal ani;
        int i=0;
        ArrayList<Animal> especieAnimal = new ArrayList<>();
        for (Animal animal : animales) {
            if (especieBus.equals(animal.especie) || especieBus.equals(animal.especie.toLowerCase())) {
                especieAnimal.add(animal);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Nivel de agresividad.");
        System.out.println("3. Alimentacion.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    especieAnimal.sort((comparadoresAnimal[0]));
                    for (Animal animal : especieAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    especieAnimal.sort(Collections.reverseOrder(comparadoresAnimal[0]));
                    for (Animal animal : especieAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    especieAnimal.sort((comparadoresAnimal[2]));
                    for (Animal animal : especieAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    especieAnimal.sort(Collections.reverseOrder(comparadoresAnimal[2]));
                    for (Animal animal : especieAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    especieAnimal.sort((comparadoresAnimal[3]));
                    for (Animal animal : especieAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    especieAnimal.sort(Collections.reverseOrder(comparadoresAnimal[3]));
                    for (Animal animal : especieAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Animal animal : especieAnimal) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el animal que desea editar: \n");
                eleccion = input.nextInt();
                ani = especieAnimal.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + ani.id);
                String nuevoID = input.nextLine();
                if(nuevoID.equals(Integer.toString(animal.id))){
                    System.out.println("ERROR: Ya existe un animal registrado con este ID.");
                    return;
                }

                System.out.println("Nombre: " + ani.especie);
                String nuevoEspecie = input.nextLine();

                System.out.println("Nivel de agresividad: " + ani.nivelAgresividad);
                String nuevonivelAgresividad = input.nextLine();

                System.out.println("Alimentacion: " + ani.alimentacion);
                String nuevoAlimentacion = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID.isEmpty()) {
                                } else ani.id = Integer.parseInt(nuevoID);
                                if (nuevoEspecie.isEmpty()) {
                                } else ani.especie = nuevoEspecie;
                                if (nuevonivelAgresividad.isEmpty()) {
                                } else ani.nivelAgresividad = Integer.parseInt(nuevonivelAgresividad);
                                if (nuevoAlimentacion.isEmpty()) {
                                } else ani.alimentacion = nuevoAlimentacion;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el animal que desea eliminar: \n");
                eleccion = input.nextInt();
                ani = especieAnimal.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Habitat habitat : habitats) {
                    Animal finalAni = ani;
                    habitat.animales.removeIf(animal1 -> animal1.id == finalAni.id);
                }
                for (ZooAmigo zooAmigo : zooAmigos) {
                    Animal finalAni = ani;
                    zooAmigo.animales.removeIf(animal1 -> animal1.id == finalAni.id);
                }
                animales.remove(ani);

                System.out.println("El animal se ha eliminado correctamente");
            } else return;
        }
    }
    public static void nivelAgresividadAnimal(int opcionValor, int nivelAgresividadBusMin, int nivelAgresividadMax) {
        Animal ani;
        int i = 0;
        ArrayList<Animal> nivelAgrGen = new ArrayList<>();
        if (opcionValor == 1) {
            for (Animal animal : animales) {
                if (nivelAgresividadBusMin == animal.nivelAgresividad) {
                    nivelAgrGen.add(animal);
                }
            }
        } else if (opcionValor == 2) {
            for (Animal animal : animales) {
                if (nivelAgresividadBusMin >= animal.nivelAgresividad) {
                    nivelAgrGen.add(animal);
                }
            }
        } else if (opcionValor == 3) {
            for (Animal animal : animales) {
                if (nivelAgresividadBusMin <= animal.nivelAgresividad) {
                    nivelAgrGen.add(animal);
                }
            }
        } else {
            for (Animal animal : animales) {
                if (nivelAgresividadBusMin <= animal.nivelAgresividad && animal.nivelAgresividad <= nivelAgresividadMax) {
                    nivelAgrGen.add(animal);
                }
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Especie.");
        System.out.println("3. Nivel de agresividad.");
        System.out.println("4. Alimentacion.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    nivelAgrGen.sort((comparadoresAnimal[0]));
                    for (Animal animal : nivelAgrGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    nivelAgrGen.sort(Collections.reverseOrder(comparadoresAnimal[0]));
                    for (Animal animal : nivelAgrGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion invalida");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    nivelAgrGen.sort((comparadoresAnimal[1]));
                    for (Animal animal : nivelAgrGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    nivelAgrGen.sort(Collections.reverseOrder(comparadoresAnimal[1]));
                    for (Animal animal : nivelAgrGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion invalida");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    nivelAgrGen.sort((comparadoresAnimal[2]));
                    for (Animal animal : nivelAgrGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    nivelAgrGen.sort(Collections.reverseOrder(comparadoresAnimal[2]));
                    for (Animal animal : nivelAgrGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    nivelAgrGen.sort((comparadoresAnimal[3]));
                    for (Animal animal : nivelAgrGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    nivelAgrGen.sort(Collections.reverseOrder(comparadoresAnimal[3]));
                    for (Animal animal : nivelAgrGen) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Animal animal : nivelAgrGen) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el animal que desea editar: \n");
                eleccion = input.nextInt();
                ani = nivelAgrGen.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + ani.id);
                String nuevoID = input.nextLine();
                if(nuevoID.equals(Integer.toString(animal.id))){
                    System.out.println("ERROR: Ya existe un animal registrado con este NIT");
                    return;
                }

                System.out.println("Nombre: " + ani.especie);
                String nuevoEspecie = input.nextLine();

                System.out.println("Nivel de agresividad: " + ani.nivelAgresividad);
                String nuevonivelAgresividad = input.nextLine();

                System.out.println("Alimentacion: " + ani.alimentacion);
                String nuevoAlimentacion = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID.isEmpty()) {
                                } else ani.id = Integer.parseInt(nuevoID);
                                if (nuevoEspecie.isEmpty()) {
                                } else ani.especie = nuevoEspecie;
                                if (nuevonivelAgresividad.isEmpty()) {
                                } else ani.nivelAgresividad = Integer.parseInt(nuevonivelAgresividad);
                                if (nuevoAlimentacion.isEmpty()) {
                                } else ani.alimentacion = nuevoAlimentacion;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el animal que desea eliminar: \n");
                eleccion = input.nextInt();
                ani = nivelAgrGen.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Habitat habitat : habitats) {
                    habitat.animales.removeIf(animal1 -> animal1.id == ani.id);
                }
                for (ZooAmigo zooAmigo : zooAmigos) {
                    zooAmigo.animales.removeIf(animal1 -> animal1.id == ani.id);
                }

                animales.remove(ani);

                System.out.println("El animal se ha eliminado correctamente");
                return;
            } else return;
        }
    }
    public static void alimentacionAnimal(String alimentacionBus) {
        Animal ani;
        ArrayList<Animal> alimentacionAni = new ArrayList<>();
        int i=0;
        for (Animal animal : animales) {
            if (alimentacionBus.equals(animal.especie) || alimentacionBus.equals(animal.especie.toLowerCase())) {
                alimentacionAni.add(animal);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Especie.");
        System.out.println("3. Nivel de agresividad.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    alimentacionAni.sort((comparadoresAnimal[0]));
                    for (Animal animal : alimentacionAni) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    alimentacionAni.sort(Collections.reverseOrder(comparadoresAnimal[0]));
                    for (Animal animal : alimentacionAni) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    alimentacionAni.sort((comparadoresAnimal[1]));
                    for (Animal animal : alimentacionAni) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    alimentacionAni.sort(Collections.reverseOrder(comparadoresAnimal[1]));
                    for (Animal animal : alimentacionAni) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    alimentacionAni.sort((comparadoresAnimal[2]));
                    for (Animal animal : alimentacionAni) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    alimentacionAni.sort(Collections.reverseOrder(comparadoresAnimal[2]));
                    for (Animal animal : alimentacionAni) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Animal animal : alimentacionAni) {
        //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el animal que desea editar: \n");
                eleccion = input.nextInt();
                ani = alimentacionAni.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + ani.id);
                String nuevoID = input.nextLine();
                if(nuevoID.equals(Integer.toString(animal.id))){
                    System.out.println("ERROR: El ID ya se encuentra registrado");
                }

                System.out.println("Nombre: " + ani.especie);
                String nuevoEspecie = input.nextLine();

                System.out.println("Nivel de agresividad: " + ani.nivelAgresividad);
                String nuevonivelAgresividad = input.nextLine();

                System.out.println("Alimentacion: " + ani.alimentacion);
                String nuevoAlimentacion = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID.isEmpty()) {
                                } else ani.id = Integer.parseInt(nuevoID);
                                if (nuevoEspecie.isEmpty()) {
                                } else ani.especie = nuevoEspecie;
                                if (nuevonivelAgresividad.isEmpty()) {
                                } else ani.nivelAgresividad = Integer.parseInt(nuevonivelAgresividad);
                                if (nuevoAlimentacion.isEmpty()) {
                                } else ani.alimentacion = nuevoAlimentacion;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

            }else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el animal que desea eliminar: \n");
                eleccion = input.nextInt();
                ani = alimentacionAni.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Habitat habitat : habitats) {
                    habitat.animales.removeIf(animal1 -> animal1.id == ani.id);

                }
                for (ZooAmigo zooAmigo : zooAmigos) {
                    zooAmigo.animales.removeIf(animal1 -> animal1.id == ani.id);

                }

                animales.remove(ani);

                System.out.println("El animal se ha eliminado correctamente");
                return;
            }else return;
        }
    }
    public static void mostrarTodosAnimales() {
        Animal ani;
        int i=0;
        ArrayList<Animal> copiaAnimal = new ArrayList<>(animales);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Id.");
        System.out.println("2. Especie.");
        System.out.println("3. Nivel de agresividad.");
        System.out.println("4. Alimentacion.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaAnimal.sort((comparadoresAnimal[0]));
                    for (Animal animal : copiaAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaAnimal.sort(Collections.reverseOrder(comparadoresAnimal[0]));
                    for (Animal animal : copiaAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaAnimal.sort((comparadoresAnimal[1]));
                    for (Animal animal : copiaAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaAnimal.sort(Collections.reverseOrder(comparadoresAnimal[1]));
                    for (Animal animal : copiaAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaAnimal.sort((comparadoresAnimal[2]));
                    for (Animal animal : copiaAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaAnimal.sort(Collections.reverseOrder(comparadoresAnimal[2]));
                    for (Animal animal : copiaAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaAnimal.sort((comparadoresAnimal[3]));
                    for (Animal animal : copiaAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaAnimal.sort(Collections.reverseOrder(comparadoresAnimal[3]));
                    for (Animal animal : copiaAnimal) {
                        i++;
                        System.out.println(i + ". " + animal);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Animal animal : copiaAnimal) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el animal que desea editar: \n");
                eleccion = input.nextInt();
                ani = copiaAnimal.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nID: " + ani.id);
                String nuevoID = input.nextLine();
                if(nuevoID.equals(Integer.toString(animal.id))){
                    System.out.println("ERROR: El id ya se encuentra regustrado");
                }

                System.out.println("Nombre: " + ani.especie);
                String nuevoEspecie = input.nextLine();

                System.out.println("Nivel de agresividad: " + ani.nivelAgresividad);
                String nuevonivelAgresividad = input.nextLine();

                System.out.println("Alimentacion: " + ani.alimentacion);
                String nuevoAlimentacion = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoID.isEmpty()) {
                                } else ani.id = Integer.parseInt(nuevoID);
                                if (nuevoEspecie.isEmpty()) {
                                } else ani.especie = nuevoEspecie;
                                if (nuevonivelAgresividad.isEmpty()) {
                                } else ani.nivelAgresividad = Integer.parseInt(nuevonivelAgresividad);
                                if (nuevoAlimentacion.isEmpty()) {
                                } else ani.alimentacion = nuevoAlimentacion;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }
            //Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el animal que desea eliminar: \n");
                eleccion = input.nextInt();
                ani = copiaAnimal.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Habitat habitat : habitats) {
                    habitat.animales.removeIf(animal1 -> animal1.id == ani.id);

                }
                for (ZooAmigo zooAmigo : zooAmigos) {
                    zooAmigo.animales.removeIf(animal1 -> animal1.id == ani.id);

                }

                animales.remove(ani);

                System.out.println("El animal se ha eliminado correctamente");
                return;
            } else return;
        }
    }

    public static void busquedaTecnico() {
        String option;
        label:
        while (true) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
            System.out.println("1. Cedula.");
            System.out.println("2. Area.");
            System.out.println("3. Hora inicio.");
            System.out.println("4. Hora salida.");
            System.out.println("5. Mostrar todos los zoologicos.");
            System.out.println("0. Regresar al menu anterior.");
            option = input.next();
            System.out.println();
            switch (option) {
                case "1":
                    String option1;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor maximo.");
                    System.out.println("3. Valor minimo.");
                    System.out.println("4. Rango.");
                    option1 = input.next();
                    if (!option1.equals("1") && !option1.equals("2") && !option1.equals("3") && !option1.equals("4")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option1.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaTecnico(1, cedulaBus, 0);
                        break;
                    } else if (option1.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaTecnico(2, cedulaBus, 0);
                        break;
                    } else if (option1.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaTecnico(3, cedulaBus, 0);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int cedulaBusMin = input.nextInt();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        int cedulaBusMax = input.nextInt();
                        cedulaTecnico(4, cedulaBusMin, cedulaBusMax);
                        break;
                    }
                    break;
                case "2":
                    String option2;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option2 = input.next();
                    if (!option2.equals("1") && !option2.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option2.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String areaBus = input.next();
                        areaTecnico(areaBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String areaBus = input.next();
                        areaTecnico(areaBus);
                        break;
                    }
                    break;
                case "3":
                    String option3;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor maximo.");
                    System.out.println("3. Valor minimo.");
                    System.out.println("4. Rango.");
                    option3 = input.next();
                    if (!option3.equals("1") && !option3.equals("2") && !option3.equals("3") && !option3.equals("4")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option3.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaInicioBus = input.next();
                        horaInicioTecnico(1, horaInicioBus, "0");
                        break;
                    } else if (option3.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaInicioBus = input.next();
                        horaInicioTecnico(2, horaInicioBus, "0");
                        break;
                    } else if (option3.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaInicioBus = input.next();
                        horaInicioTecnico(3, horaInicioBus, "0");
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        String horaInicioBusMin = input.next();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        String horaInicioBusMax = input.next();
                        horaInicioTecnico(4, horaInicioBusMin, horaInicioBusMax);
                        break;
                    }
                    break;
                case "4":
                    String option4;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor maximo.");
                    System.out.println("3. Valor minimo.");
                    System.out.println("4. Rango.");
                    option4 = input.next();
                    if (!option4.equals("1") && !option4.equals("2") && !option4.equals("3") && !option4.equals("4")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option4.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaSalidaBus = input.next();
                        horaSalidaTecnico(1, horaSalidaBus, "1");
                        break;
                    } else if (option4.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaSalidaBus = input.next();
                        horaSalidaTecnico(2, horaSalidaBus, "0");
                        break;
                    } else if (option4.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaSalidaBus = input.next();
                        horaSalidaTecnico(3, horaSalidaBus, "0");
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        String horaSalidaBusMin = input.next();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        String horaSalidaBusMax = input.next();
                        horaSalidaTecnico(4, horaSalidaBusMin, horaSalidaBusMax);
                        break;
                    }
                    break;
                case "5":
                    mostrarTodosTecnicos();
                    break;
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void cedulaTecnico(int opcionValor, int cedulaBusMin, int cedulaBusMax) {
        Tecnico tec;
        int i = 0;
        ArrayList<Tecnico> cedulaTecnicoGen = new ArrayList<>();
        if (opcionValor == 1) {
            for (Tecnico tecnico : tecnicos) {
                if (cedulaBusMin == tecnico.cedula) {
                    cedulaTecnicoGen.add(tecnico);
                }
            }
        } else if (opcionValor == 2) {
            for (Tecnico tecnico : tecnicos) {
                if (cedulaBusMin >= tecnico.cedula) {
                    cedulaTecnicoGen.add(tecnico);
                }
            }
        } else if (opcionValor == 3) {
            for (Tecnico tecnico : tecnicos) {
                if (cedulaBusMin <= tecnico.cedula) {
                    cedulaTecnicoGen.add(tecnico);
                }
            }
        } else {
            for (Tecnico tecnico : tecnicos) {
                if (cedulaBusMin <= tecnico.cedula && tecnico.cedula <= cedulaBusMax) {
                    cedulaTecnicoGen.add(tecnico);
                }
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora de inicio.");
        System.out.println("4. Hora de salida.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    cedulaTecnicoGen.sort((comparadoresTecnico[0]));
                    for (Tecnico tecnico : cedulaTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    cedulaTecnicoGen.sort(Collections.reverseOrder(comparadoresTecnico[0]));
                    for (Tecnico tecnico : cedulaTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    cedulaTecnicoGen.sort((comparadoresTecnico[1]));
                    for (Tecnico tecnico : cedulaTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    cedulaTecnicoGen.sort(Collections.reverseOrder(comparadoresTecnico[1]));
                    for (Tecnico tecnico : cedulaTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    cedulaTecnicoGen.sort((comparadoresTecnico[2]));
                    for (Tecnico tecnico : cedulaTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    cedulaTecnicoGen.sort(Collections.reverseOrder(comparadoresTecnico[2]));
                    for (Tecnico tecnico : cedulaTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    cedulaTecnicoGen.sort((comparadoresTecnico[3]));
                    for (Tecnico tecnico : cedulaTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    cedulaTecnicoGen.sort(Collections.reverseOrder(comparadoresTecnico[3]));
                    for (Tecnico tecnico : cedulaTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        //Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Tecnico tecnico : cedulaTecnicoGen) {
        //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el tecnico que desea editar: \n");
                eleccion = input.nextInt();
                tec = cedulaTecnicoGen.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + tec.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(tecnico.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Area : " + tec.area);
                String nuevoArea = input.nextLine();

                System.out.println("Hora Inicio: " + tec.horaInicio);
                String nuevoHoraInicio = input.nextLine();

                System.out.println("Hora Salida: " + tec.horaSalida);
                String nuevoHoraSalida = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else tec.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoArea.isEmpty()) {
                                } else tec.area = nuevoArea;
                                if (nuevoHoraInicio.isEmpty()) {
                                } else tec.horaInicio = nuevoHoraInicio;
                                if (nuevoHoraSalida.isEmpty()) {
                                } else tec.horaSalida = nuevoHoraSalida;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el tecnico que desea eliminar: \n");
                eleccion = input.nextInt();
                tec = cedulaTecnicoGen.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Habitat habitat : habitats) {
                    habitat.tecnicos.removeIf(tecnico1 -> tecnico1.cedula == tec.cedula);
                }

                tecnicos.remove(tec);

                System.out.println("El tecnico se ha eliminado correctamente");
                return;
            }else return;
        }
    }
    public static void areaTecnico(String areaBus) {
        Tecnico tec;
        int i=0;
        ArrayList<Tecnico> areaTec = new ArrayList<>();
        for (Tecnico tecnico : tecnicos) {
            if (areaBus.equals(tecnico.area) || areaBus.equals(tecnico.area.toLowerCase())) {
                areaTec.add(tecnico);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Hora inicio.");
        System.out.println("3. Hora salida.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    areaTec.sort((comparadoresTecnico[0]));
                    for (Tecnico tecnico : areaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    areaTec.sort(Collections.reverseOrder(comparadoresTecnico[0]));
                    for (Tecnico tecnico : areaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    areaTec.sort((comparadoresTecnico[2]));
                    for (Tecnico tecnico : areaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    areaTec.sort(Collections.reverseOrder(comparadoresTecnico[2]));
                    for (Tecnico tecnico : areaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    areaTec.sort((comparadoresTecnico[3]));
                    for (Tecnico tecnico : areaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    areaTec.sort(Collections.reverseOrder(comparadoresTecnico[3]));
                    for (Tecnico tecnico : areaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Tecnico tecnico : areaTec) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el tecnico que desea editar: \n");
                eleccion = input.nextInt();
                tec = areaTec.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + tec.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(tecnico.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Area : " + tec.area);
                String nuevoArea = input.nextLine();

                System.out.println("Hora Inicio: " + tec.horaInicio);
                String nuevoHoraInicio = input.nextLine();

                System.out.println("Hora Salida: " + tec.horaSalida);
                String nuevoHoraSalida = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else tec.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoArea.isEmpty()) {
                                } else tec.area = nuevoArea;
                                if (nuevoHoraInicio.isEmpty()) {
                                } else tec.horaInicio = nuevoHoraInicio;
                                if (nuevoHoraSalida.isEmpty()) {
                                } else tec.horaSalida = nuevoHoraSalida;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el tecnico que desea eliminar: \n");
                eleccion = input.nextInt();
                tec = areaTec.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Habitat habitat : habitats) {
                    habitat.tecnicos.removeIf(tecnico1 -> tecnico1.cedula == tec.cedula);
                }

                tecnicos.remove(tec);

                System.out.println("El tecnico se ha eliminado correctamente");
                return;
            } else return;
        }
    }
    public static void horaInicioTecnico(int opcionValor, String horaInicioBusMin, String horaInicioBusMax) {
        Tecnico tec;
        int i=0;
        ArrayList<Tecnico> horaInicioTecnicoGen = new ArrayList<>();
        if (opcionValor == 1) {
            for (Tecnico tecnico : tecnicos) {
                int horaInicio = Integer.parseInt(tecnico.horaInicio.replace(":", ""));
                int horaInicioB = Integer.parseInt(horaInicioBusMin.replace(":", ""));
                if (horaInicioB == horaInicio) {
                    horaInicioTecnicoGen.add(tecnico);
                }
            }
        }
        else if (opcionValor == 2) {
            for (Tecnico tecnico : tecnicos) {
                int horaInicio = Integer.parseInt(tecnico.horaInicio.replace(":", ""));
                int horaInicioB = Integer.parseInt(horaInicioBusMin.replace(":", ""));
                if (horaInicioB >= horaInicio) {
                    horaInicioTecnicoGen.add(tecnico);
                }
            }
        }
        else if (opcionValor == 3) {
            for (Tecnico tecnico : tecnicos) {
                int horaInicio = Integer.parseInt(tecnico.horaInicio.replace(":", ""));
                int horaInicioB = Integer.parseInt(horaInicioBusMin.replace(":", ""));
                if (horaInicioB <= horaInicio) {
                    horaInicioTecnicoGen.add(tecnico);
                }
            }
        }
        else {
            for (Tecnico tecnico : tecnicos) {
                int horaInicio = Integer.parseInt(tecnico.horaInicio.replace(":", ""));
                int horaInicioBMin = Integer.parseInt(horaInicioBusMin.replace(":", ""));
                int horaInicioBMax = Integer.parseInt(horaInicioBusMax.replace(":", ""));
                if (horaInicioBMin <= horaInicio && horaInicioBMax <= horaInicio) {
                    horaInicioTecnicoGen.add(tecnico);
                }
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaInicioTecnicoGen.sort((comparadoresTecnico[0]));
                    for (Tecnico tecnico : horaInicioTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaInicioTecnicoGen.sort(Collections.reverseOrder(comparadoresTecnico[0]));
                    for (Tecnico tecnico : horaInicioTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaInicioTecnicoGen.sort((comparadoresTecnico[1]));
                    for (Tecnico tecnico : horaInicioTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaInicioTecnicoGen.sort(Collections.reverseOrder(comparadoresTecnico[1]));
                    for (Tecnico tecnico : horaInicioTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaInicioTecnicoGen.sort((comparadoresTecnico[2]));
                    for (Tecnico tecnico : horaInicioTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaInicioTecnicoGen.sort(Collections.reverseOrder(comparadoresTecnico[2]));
                    for (Tecnico tecnico : horaInicioTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaInicioTecnicoGen.sort((comparadoresTecnico[3]));
                    for (Tecnico tecnico : horaInicioTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaInicioTecnicoGen.sort(Collections.reverseOrder(comparadoresTecnico[3]));
                    for (Tecnico tecnico : horaInicioTecnicoGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }//Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Tecnico tecnico : horaInicioTecnicoGen) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el tecnico que desea editar: \n");
                eleccion = input.nextInt();
                tec = horaInicioTecnicoGen.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + tec.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(tecnico.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Area : " + tec.area);
                String nuevoArea = input.nextLine();

                System.out.println("Hora Inicio: " + tec.horaInicio);
                String nuevoHoraInicio = input.nextLine();

                System.out.println("Hora Salida: " + tec.horaSalida);
                String nuevoHoraSalida = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else tec.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoArea.isEmpty()) {
                                } else tec.area = nuevoArea;
                                if (nuevoHoraInicio.isEmpty()) {
                                } else tec.horaInicio = nuevoHoraInicio;
                                if (nuevoHoraSalida.isEmpty()) {
                                } else tec.horaSalida = nuevoHoraSalida;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            } else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el tecnico que desea eliminar: \n");
                eleccion = input.nextInt();
                tec = horaInicioTecnicoGen.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Habitat habitat : habitats) {
                    habitat.tecnicos.removeIf(tecnico1 -> tecnico1.cedula == tec.cedula);

                }

                tecnicos.remove(tec);

                System.out.println("El tecnico se ha eliminado correctamente");
                return;
            } else return;
        }
    }
    public static void horaSalidaTecnico(int opcionValor, String horaSalidaBusMin, String horaInicioBusMax) {
        Tecnico tec;
        int i = 0;
        ArrayList<Tecnico> horaSalidaGen = new ArrayList<>();
        if (opcionValor == 1) {
            for (Tecnico tecnico : tecnicos) {
                int horaSalida = Integer.parseInt(tecnico.horaSalida.replace(":", ""));
                int horaSalidaB = Integer.parseInt(horaSalidaBusMin.replace(":", ""));
                if (horaSalidaB == horaSalida) {
                    horaSalidaGen.add(tecnico);
                }
            }
        } else if (opcionValor == 2) {
            for (Tecnico tecnico : tecnicos) {
                int horaSalida = Integer.parseInt(tecnico.horaSalida.replace(":", ""));
                int horaSalidaB = Integer.parseInt(horaSalidaBusMin.replace(":", ""));
                if (horaSalidaB >= horaSalida) {
                    horaSalidaGen.add(tecnico);
                }
            }
        } else if (opcionValor == 3) {
            for (Tecnico tecnico : tecnicos) {
                int horaSalida = Integer.parseInt(tecnico.horaSalida.replace(":", ""));
                int horaSalidaB = Integer.parseInt(horaSalidaBusMin.replace(":", ""));
                if (horaSalidaB <= horaSalida) {
                    horaSalidaGen.add(tecnico);
                }
            }
        } else {
            for (Tecnico tecnico : tecnicos) {
                int horaSalida = Integer.parseInt(tecnico.horaSalida.replace(":", ""));
                int horaSalidaBMin = Integer.parseInt(horaSalidaBusMin.replace(":", ""));
                int horaSalidaBMax = Integer.parseInt(horaInicioBusMax.replace(":", ""));
                if (horaSalidaBMin <= horaSalida && horaSalidaBMax <= horaSalida) {
                    horaSalidaGen.add(tecnico);
                }
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaSalidaGen.sort((comparadoresTecnico[0]));
                    for (Tecnico tecnico : horaSalidaGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaSalidaGen.sort(Collections.reverseOrder(comparadoresTecnico[0]));
                    for (Tecnico tecnico : horaSalidaGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaSalidaGen.sort((comparadoresTecnico[1]));
                    for (Tecnico tecnico : horaSalidaGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaSalidaGen.sort(Collections.reverseOrder(comparadoresTecnico[1]));
                    for (Tecnico tecnico : horaSalidaGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaSalidaGen.sort((comparadoresTecnico[2]));
                    for (Tecnico tecnico : horaSalidaGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaSalidaGen.sort(Collections.reverseOrder(comparadoresTecnico[2]));
                    for (Tecnico tecnico : horaSalidaGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaSalidaGen.sort((comparadoresTecnico[3]));
                    for (Tecnico tecnico : horaSalidaGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaSalidaGen.sort(Collections.reverseOrder(comparadoresTecnico[3]));
                    for (Tecnico tecnico : horaSalidaGen) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }//Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Tecnico tecnico : horaSalidaGen) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el tecnico que desea editar: \n");
                eleccion = input.nextInt();
                tec = horaSalidaGen.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + tec.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(tecnico.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Area : " + tec.area);
                String nuevoArea = input.nextLine();

                System.out.println("Hora Inicio: " + tec.horaInicio);
                String nuevoHoraInicio = input.nextLine();

                System.out.println("Hora Salida: " + tec.horaSalida);
                String nuevoHoraSalida = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else tec.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoArea.isEmpty()) {
                                } else tec.area = nuevoArea;
                                if (nuevoHoraInicio.isEmpty()) {
                                } else tec.horaInicio = nuevoHoraInicio;
                                if (nuevoHoraSalida.isEmpty()) {
                                } else tec.horaSalida = nuevoHoraSalida;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el tecnico que desea eliminar: \n");
                eleccion = input.nextInt();
                tec = horaSalidaGen.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
                for (Habitat habitat : habitats) {
                    habitat.tecnicos.removeIf(tecnico1 -> tecnico1.cedula == tec.cedula);
                }

                tecnicos.remove(tec);

                System.out.println("El tecnico se ha eliminado correctamente");
                return;
            } else return;
        }
    }
    public static void mostrarTodosTecnicos() {
        Tecnico tec;
        int i =0;
        ArrayList<Tecnico> copiaTec = new ArrayList<>(tecnicos);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaTec.sort((comparadoresTecnico[0]));
                    for (Tecnico tecnico : copiaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaTec.sort(Collections.reverseOrder(comparadoresTecnico[0]));
                    for (Tecnico tecnico : copiaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaTec.sort((comparadoresTecnico[1]));
                    for (Tecnico tecnico : copiaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaTec.sort(Collections.reverseOrder(comparadoresTecnico[1]));
                    for (Tecnico tecnico : copiaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else System.out.println("Opcion incorrecta");
                return;

            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaTec.sort((comparadoresTecnico[2]));
                    for (Tecnico tecnico : copiaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaTec.sort(Collections.reverseOrder(comparadoresTecnico[2]));
                    for (Tecnico tecnico : copiaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else System.out.println("Opcion incorrecta");
                return;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaTec.sort((comparadoresTecnico[3]));
                    for (Tecnico tecnico : copiaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaTec.sort(Collections.reverseOrder(comparadoresTecnico[3]));
                    for (Tecnico tecnico : copiaTec) {
                        i++;
                        System.out.println(i + ". " + tecnico);
                    }
                } else System.out.println("Opcion incorrecta");
                return;

            default:
                return;
        }
        //Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

//Codigo editar
        for (Tecnico tecnico : copiaTec) {
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el tecnico que desea editar: \n");
                eleccion = input.nextInt();
                tec = copiaTec.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + tec.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(tecnico.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Area : " + tec.area);
                String nuevoArea = input.nextLine();

                System.out.println("Hora Inicio: " + tec.horaInicio);
                String nuevoHoraInicio = input.nextLine();

                System.out.println("Hora Salida: " + tec.horaSalida);
                String nuevoHoraSalida = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else tec.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoArea.isEmpty()) {
                                } else tec.area = nuevoArea;
                                if (nuevoHoraInicio.isEmpty()) {
                                } else tec.horaInicio = nuevoHoraInicio;
                                if (nuevoHoraSalida.isEmpty()) {
                                } else tec.horaSalida = nuevoHoraSalida;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el tecnico que desea eliminar: \n");
                eleccion = input.nextInt();
                tec = copiaTec.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
                for (Habitat habitat : habitats) {
                    habitat.tecnicos.removeIf(tecnico1 -> tecnico1.cedula == tec.cedula);
                }

                tecnicos.remove(tec);

                System.out.println("El tecnico se ha eliminado correctamente");
                return;
            } else return;
        }
    }

    public static void busquedaProfesional() {
        String option;
        label:
        while (true) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
            System.out.println("1. Cedula.");
            System.out.println("2. Area.");
            System.out.println("3. Hora inicio.");
            System.out.println("4. Hora salida.");
            System.out.println("5. Mostrar todos los profesionales.");
            System.out.println("0. Regresar al menu anterior.");
            option = input.next();
            System.out.println();
            switch (option) {
                case "1":
                    String option1;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor maximo.");
                    System.out.println("3. Valor minimo.");
                    System.out.println("4. Rango.");
                    option1 = input.next();
                    if (!option1.equals("1") && !option1.equals("2") && !option1.equals("3") && !option1.equals("4")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option1.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaProfesional(1, cedulaBus, 0);
                        break;
                    } else if (option1.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaProfesional(2, cedulaBus, 0);
                        break;
                    } else if (option1.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaProfesional(3, cedulaBus, 0);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int cedulaBusMin = input.nextInt();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        int cedulaBusMax = input.nextInt();
                        cedulaProfesional(4, cedulaBusMin, cedulaBusMax);
                        break;
                    }
                    break;
                case "2":
                    String option2;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option2 = input.next();
                    if (!option2.equals("1") && !option2.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option2.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String areaBus = input.next();
                        areaProfesional(areaBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String areaBus = input.next();
                        areaProfesional(areaBus);
                        break;
                    }
                    break;
                case "3":
                    String option3;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor maximo.");
                    System.out.println("3. Valor minimo.");
                    System.out.println("4. Rango.");
                    option3 = input.next();
                    if (!option3.equals("1") && !option3.equals("2") && !option3.equals("3") && !option3.equals("4")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option3.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaInicioBus = input.next();
                        horaInicioProfesional(1, horaInicioBus, "0");
                        break;
                    } else if (option3.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaInicioBus = input.next();
                        horaInicioProfesional(2, horaInicioBus, "0");
                        break;
                    } else if (option3.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaInicioBus = input.next();
                        horaInicioProfesional(3, horaInicioBus, "0");
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        String horaInicioBusMin = input.next();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        String horaInicioBusMax = input.next();
                        horaInicioTecnico(4, horaInicioBusMin, horaInicioBusMax);
                        break;
                    }
                    break;
                case "4":
                    String option4;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor maximo.");
                    System.out.println("3. Valor minimo.");
                    System.out.println("4. Rango.");
                    option4 = input.next();
                    if (!option4.equals("1") && !option4.equals("2") && !option4.equals("3") && !option4.equals("4")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option4.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaSalidaBus = input.next();
                        horaSalidaProfesional(1, horaSalidaBus, "0");
                        break;
                    } else if (option4.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaSalidaBus = input.next();
                        horaSalidaProfesional(2, horaSalidaBus, "0");
                        break;
                    } else if (option4.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaSalidaBus = input.next();
                        horaSalidaProfesional(3, horaSalidaBus, "0");
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        String horaSalidaBusMin = input.next();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        String horaSalidaBusMax = input.next();
                        horaSalidaProfesional(4, horaSalidaBusMin, horaSalidaBusMax);
                        break;
                    }
                    break;
                case "5":
                    mostrarTodosProfesionales();
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void cedulaProfesional(int opcionValor, int cedulaBusMin, int cedulaBusMax) {
        Profesional pro;
        int i=0;
        ArrayList<Profesional> cedulaProfesionalGen = new ArrayList<>();
        if (opcionValor == 1) {
            for (Profesional profesional : profesionales) {
                if (cedulaBusMin == profesional.cedula) {
                    cedulaProfesionalGen.add(profesional);
                }
            }
        }
        else if (opcionValor == 2) {
            for (Profesional profesional : profesionales) {
                if (cedulaBusMin >= profesional.cedula) {
                    cedulaProfesionalGen.add(profesional);
                }
            }
        }
        else if (opcionValor == 3) {
            for (Profesional profesional : profesionales) {
                if (cedulaBusMin <= profesional.cedula) {
                    cedulaProfesionalGen.add(profesional);
                }
            }
        }
        else {
            for (Profesional profesional : profesionales) {
                if (cedulaBusMin <= profesional.cedula && profesional.cedula <= cedulaBusMax) {
                    cedulaProfesionalGen.add(profesional);
                }
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora de inicio.");
        System.out.println("4. Hora de salida.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    cedulaProfesionalGen.sort((comparadoresProfesional[0]));
                    for (Profesional profesional : cedulaProfesionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    cedulaProfesionalGen.sort(Collections.reverseOrder(comparadoresProfesional[0]));
                    for (Profesional profesional : cedulaProfesionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    cedulaProfesionalGen.sort((comparadoresProfesional[1]));
                    for (Profesional profesional : cedulaProfesionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    cedulaProfesionalGen.sort(Collections.reverseOrder(comparadoresProfesional[1]));
                    for (Profesional profesional : cedulaProfesionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    cedulaProfesionalGen.sort((comparadoresProfesional[2]));
                    for (Profesional profesional : cedulaProfesionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    cedulaProfesionalGen.sort(Collections.reverseOrder(comparadoresProfesional[2]));
                    for (Profesional profesional : cedulaProfesionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    cedulaProfesionalGen.sort((comparadoresProfesional[3]));
                    for (Profesional profesional : cedulaProfesionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    cedulaProfesionalGen.sort(Collections.reverseOrder(comparadoresProfesional[3]));
                    for (Profesional profesional : cedulaProfesionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        //Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Profesional profesional : cedulaProfesionalGen) {
//Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el profesional que desea editar: \n");
                eleccion = input.nextInt();
                pro = cedulaProfesionalGen.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + pro.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(profesional.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Area : " + pro.area);
                String nuevoArea = input.nextLine();

                System.out.println("Hora Inicio: " + pro.horaInicio);
                String nuevoHoraInicio = input.nextLine();

                System.out.println("Hora Salida: " + pro.horaSalida);
                String nuevoHoraSalida = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else pro.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoArea.isEmpty()) {
                                } else pro.area = nuevoArea;
                                if (nuevoHoraInicio.isEmpty()) {
                                } else pro.horaInicio = nuevoHoraInicio;
                                if (nuevoHoraSalida.isEmpty()) {
                                } else pro.horaSalida = nuevoHoraSalida;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }
            else if(accion.equals("2")){
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el profesional que desea eliminar: \n");
                eleccion = input.nextInt();
                pro = cedulaProfesionalGen.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                            break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
                for (Zoologico zoologico : zoologicos) {
                    zoologico.profesionales.removeIf(profesional1 -> profesional1.cedula == pro.cedula);
                }

                for (Bioma bioma : biomas) {
                    bioma.profesionales.removeIf(profesional1 -> profesional1.cedula == pro.cedula);
                }

                profesionales.remove(pro);

                System.out.println("El profesional se ha eliminado correctamente");
                return;
            }else return;
        }
    }
    public static void areaProfesional(String areaBus) {
        Profesional pro;
        int i=0;
        ArrayList<Profesional> areaPro = new ArrayList<>();
        for (Profesional profesional : profesionales) {
            if (areaBus.equals(profesional.area) || areaBus.equals(profesional.area.toLowerCase())) {
                areaPro.add(profesional);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    areaPro.sort((comparadoresProfesional[0]));
                    for (Profesional profesional : areaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    areaPro.sort(Collections.reverseOrder(comparadoresProfesional[0]));
                    for (Profesional profesional : areaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    areaPro.sort((comparadoresProfesional[1]));
                    for (Profesional profesional : areaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    areaPro.sort(Collections.reverseOrder(comparadoresProfesional[1]));
                    for (Profesional profesional : areaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    areaPro.sort((comparadoresProfesional[2]));
                    for (Profesional profesional : areaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    areaPro.sort(Collections.reverseOrder(comparadoresProfesional[2]));
                    for (Profesional profesional : areaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    areaPro.sort((comparadoresProfesional[3]));
                    for (Profesional profesional : areaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    areaPro.sort(Collections.reverseOrder(comparadoresProfesional[3]));
                    for (Profesional profesional : areaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        //Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Profesional profesional : areaPro) {
//Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el profesional que desea editar: \n");
                eleccion = input.nextInt();
                pro = areaPro.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + pro.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(profesional.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Area : " + pro.area);
                String nuevoArea = input.nextLine();

                System.out.println("Hora Inicio: " + pro.horaInicio);
                String nuevoHoraInicio = input.nextLine();

                System.out.println("Hora Salida: " + pro.horaSalida);
                String nuevoHoraSalida = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else pro.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoArea.isEmpty()) {
                                } else pro.area = nuevoArea;
                                if (nuevoHoraInicio.isEmpty()) {
                                } else pro.horaInicio = nuevoHoraInicio;
                                if (nuevoHoraSalida.isEmpty()) {
                                } else pro.horaSalida = nuevoHoraSalida;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el profesional que desea eliminar: \n");
                eleccion = input.nextInt();
                pro = areaPro.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
                for (Zoologico zoologico : zoologicos) {
                    zoologico.profesionales.removeIf(profesional1 -> profesional1.cedula == pro.cedula);
                }

                for (Bioma bioma : biomas) {
                    bioma.profesionales.removeIf(profesional1 -> profesional1.cedula == pro.cedula);
                }

                profesionales.remove(pro);

                System.out.println("El profesional se ha eliminado correctamente");
                return;
            } else return;
        }
    }
    public static void horaInicioProfesional(int opcionValor, String horaInicioBusMin, String horaInicioBusMax) {
        Profesional pro;
        int i=0;
        ArrayList<Profesional> horaInicioProfecionalGen = new ArrayList<>();
        if (opcionValor == 1) {
            for (Profesional profesional : profesionales) {
                int horaInicio = Integer.parseInt(profesional.horaInicio.replace(":", ""));
                int horaInicioB = Integer.parseInt(horaInicioBusMin.replace(":", ""));
                if (horaInicioB == horaInicio) {
                    horaInicioProfecionalGen.add(profesional);
                }
            }
        }
        else if (opcionValor == 2) {
            for (Profesional profesional : profesionales) {
                int horaInicio = Integer.parseInt(profesional.horaInicio.replace(":", ""));
                int horaInicioB = Integer.parseInt(horaInicioBusMin.replace(":", ""));
                if (horaInicioB >= horaInicio) {
                    horaInicioProfecionalGen.add(profesional);
                }
            }
        }
        else if (opcionValor == 3) {
            for (Profesional profesional : profesionales) {
                int horaInicio = Integer.parseInt(profesional.horaInicio.replace(":", ""));
                int horaInicioB = Integer.parseInt(horaInicioBusMin.replace(":", ""));
                if (horaInicioB <= horaInicio) {
                    horaInicioProfecionalGen.add(profesional);
                }
            }
        }
        else {
            for (Profesional profesional : profesionales) {
                int horaInicio = Integer.parseInt(profesional.horaInicio.replace(":", ""));
                int horaInicioBMin = Integer.parseInt(horaInicioBusMin.replace(":", ""));
                int horaInicioBMax = Integer.parseInt(horaInicioBusMax.replace(":", ""));
                if (horaInicioBMin <= horaInicio && horaInicioBMax <= horaInicio) {
                    horaInicioProfecionalGen.add(profesional);
                }
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaInicioProfecionalGen.sort((comparadoresProfesional[0]));
                    for (Profesional profesional : horaInicioProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaInicioProfecionalGen.sort(Collections.reverseOrder(comparadoresProfesional[0]));
                    for (Profesional profesional : horaInicioProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaInicioProfecionalGen.sort((comparadoresProfesional[1]));
                    for (Profesional profesional : horaInicioProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaInicioProfecionalGen.sort(Collections.reverseOrder(comparadoresProfesional[1]));
                    for (Profesional profesional : horaInicioProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaInicioProfecionalGen.sort((comparadoresProfesional[2]));
                    for (Profesional profesional : horaInicioProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaInicioProfecionalGen.sort(Collections.reverseOrder(comparadoresProfesional[2]));
                    for (Profesional profesional : horaInicioProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaInicioProfecionalGen.sort((comparadoresProfesional[3]));
                    for (Profesional profesional : horaInicioProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaInicioProfecionalGen.sort(Collections.reverseOrder(comparadoresProfesional[3]));
                    for (Profesional profesional : horaInicioProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }//Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

//Codigo editar
        for (Profesional profesional : horaInicioProfecionalGen) {
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el profesional que desea editar: \n");
                eleccion = input.nextInt();
                pro = horaInicioProfecionalGen.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + pro.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(profesional.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Area : " + pro.area);
                String nuevoArea = input.nextLine();

                System.out.println("Hora Inicio: " + pro.horaInicio);
                String nuevoHoraInicio = input.nextLine();

                System.out.println("Hora Salida: " + pro.horaSalida);
                String nuevoHoraSalida = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else pro.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoArea.isEmpty()) {
                                } else pro.area = nuevoArea;
                                if (nuevoHoraInicio.isEmpty()) {
                                } else pro.horaInicio = nuevoHoraInicio;
                                if (nuevoHoraSalida.isEmpty()) {
                                } else pro.horaSalida = nuevoHoraSalida;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el profesional que desea eliminar: \n");
                eleccion = input.nextInt();
                pro = horaInicioProfecionalGen.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
                for (Zoologico zoologico : zoologicos) {
                    zoologico.profesionales.removeIf(profesional1 -> profesional1.cedula == pro.cedula);
                }

                for (Bioma bioma : biomas) {
                    bioma.profesionales.removeIf(profesional1 -> profesional1.cedula == pro.cedula);
                }

                profesionales.remove(pro);

                System.out.println("El profesional se ha eliminado correctamente");
                return;
            } else return;
        }
    }
    public static void horaSalidaProfesional(int opcionValor, String horaSalidaBusMin, String horaInicioBusMax) {
        Profesional pro;
        int i=0;
        ArrayList<Profesional> horaSalidaProfecionalGen = new ArrayList<>();
        if (opcionValor == 1) {
            for (Profesional profesional : profesionales) {
                int horaSalida = Integer.parseInt(profesional.horaSalida.replace(":", ""));
                int horaSalidaB = Integer.parseInt(horaSalidaBusMin.replace(":", ""));
                if (horaSalidaB == horaSalida) {
                    horaSalidaProfecionalGen.add(profesional);
                }
            }
        }
        else if (opcionValor == 2) {
            for (Profesional profesional : profesionales) {
                int horaSalida = Integer.parseInt(profesional.horaSalida.replace(":", ""));
                int horaSalidaB = Integer.parseInt(horaSalidaBusMin.replace(":", ""));
                if (horaSalidaB >= horaSalida) {
                    horaSalidaProfecionalGen.add(profesional);
                }
            }
        }
        else if (opcionValor == 3) {
            for (Profesional profesional : profesionales) {
                int horaSalida = Integer.parseInt(profesional.horaSalida.replace(":", ""));
                int horaSalidaB = Integer.parseInt(horaSalidaBusMin.replace(".", ""));
                if (horaSalidaB <= horaSalida) {
                    horaSalidaProfecionalGen.add(profesional);
                }
            }
        }
        else {
            for (Profesional profesional : profesionales) {
                int horaSalida = Integer.parseInt(profesional.horaSalida.replace(":", ""));
                int horaSalidaBMin = Integer.parseInt(horaSalidaBusMin.replace(".", ""));
                int horaSalidaBMax = Integer.parseInt(horaInicioBusMax.replace(".", ""));
                if (horaSalidaBMin <= horaSalida && horaSalidaBMax <= horaSalida) {
                    horaSalidaProfecionalGen.add(profesional);
                }
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaSalidaProfecionalGen.sort((comparadoresProfesional[0]));
                    for (Profesional profesional : horaSalidaProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaSalidaProfecionalGen.sort(Collections.reverseOrder(comparadoresProfesional[0]));
                    for (Profesional profesional : horaSalidaProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaSalidaProfecionalGen.sort((comparadoresProfesional[1]));
                    for (Profesional profesional : horaSalidaProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaSalidaProfecionalGen.sort(Collections.reverseOrder(comparadoresProfesional[1]));
                    for (Profesional profesional : horaSalidaProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaSalidaProfecionalGen.sort((comparadoresProfesional[2]));
                    for (Profesional profesional : horaSalidaProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaSalidaProfecionalGen.sort(Collections.reverseOrder(comparadoresProfesional[2]));
                    for (Profesional profesional : horaSalidaProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    horaSalidaProfecionalGen.sort((comparadoresProfesional[3]));
                    for (Profesional profesional : horaSalidaProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    horaSalidaProfecionalGen.sort(Collections.reverseOrder(comparadoresProfesional[3]));
                    for (Profesional profesional : horaSalidaProfecionalGen) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }//Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

//Codigo editar
        for (Profesional profesional : horaSalidaProfecionalGen) {
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el profesional que desea editar: \n");
                eleccion = input.nextInt();
                pro = horaSalidaProfecionalGen.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + pro.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(profesional.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Area : " + pro.area);
                String nuevoArea = input.nextLine();

                System.out.println("Hora Inicio: " + pro.horaInicio);
                String nuevoHoraInicio = input.nextLine();

                System.out.println("Hora Salida: " + pro.horaSalida);
                String nuevoHoraSalida = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else pro.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoArea.isEmpty()) {
                                } else pro.area = nuevoArea;
                                if (nuevoHoraInicio.isEmpty()) {
                                } else pro.horaInicio = nuevoHoraInicio;
                                if (nuevoHoraSalida.isEmpty()) {
                                } else pro.horaSalida = nuevoHoraSalida;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el profesional que desea eliminar: \n");
                eleccion = input.nextInt();
                pro = horaSalidaProfecionalGen.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
                for (Zoologico zoologico : zoologicos) {
                    zoologico.profesionales.removeIf(profesional1 -> profesional1.cedula == pro.cedula);
                }

                for (Bioma bioma : biomas) {
                    bioma.profesionales.removeIf(profesional1 -> profesional1.cedula == pro.cedula);
                }

                profesionales.remove(pro);

                System.out.println("El profesional se ha eliminado correctamente");
                return;
            } else return;
        }
    }
    public static void mostrarTodosProfesionales(){
        Profesional pro;
        int i=0;
        ArrayList<Profesional> copiaPro = new ArrayList<>(profesionales);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaPro.sort((comparadoresProfesional[0]));
                    for (Profesional profesional : copiaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaPro.sort(Collections.reverseOrder(comparadoresProfesional[0]));
                    for (Profesional profesional : copiaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaPro.sort((comparadoresProfesional[1]));
                    for (Profesional profesional : copiaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaPro.sort(Collections.reverseOrder(comparadoresProfesional[1]));
                    for (Profesional profesional : copiaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaPro.sort((comparadoresProfesional[2]));
                    for (Profesional profesional : copiaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaPro.sort(Collections.reverseOrder(comparadoresProfesional[2]));
                    for (Profesional profesional : copiaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaPro.sort((comparadoresProfesional[3]));
                    for (Profesional profesional : copiaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaPro.sort(Collections.reverseOrder(comparadoresProfesional[3]));
                    for (Profesional profesional : copiaPro) {
                        i++;
                        System.out.println(i + ". " + profesional);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (Profesional profesional : copiaPro) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el profesional que desea editar: \n");
                eleccion = input.nextInt();
                pro = copiaPro.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + pro.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(profesional.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Area : " + pro.area);
                String nuevoArea = input.nextLine();

                System.out.println("Hora Inicio: " + pro.horaInicio);
                String nuevoHoraInicio = input.nextLine();

                System.out.println("Hora Salida: " + pro.horaSalida);
                String nuevoHoraSalida = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else pro.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoArea.isEmpty()) {
                                } else pro.area = nuevoArea;
                                if (nuevoHoraInicio.isEmpty()) {
                                } else pro.horaInicio = nuevoHoraInicio;
                                if (nuevoHoraSalida.isEmpty()) {
                                } else pro.horaSalida = nuevoHoraSalida;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el profesional que desea eliminar: \n");
                eleccion = input.nextInt();
                pro = copiaPro.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
                for (Zoologico zoologico : zoologicos) {
                    zoologico.profesionales.removeIf(profesional1 -> profesional1.cedula == pro.cedula);
                }

                for (Bioma bioma : biomas) {
                    bioma.profesionales.removeIf(profesional1 -> profesional1.cedula == pro.cedula);
                }

                profesionales.remove(pro);

                System.out.println("El profesional se ha eliminado correctamente");
                return;
            } else return;
        }
    }

    public static void busquedaZooamigo() {
        String option;
        label:
        while (true) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
            System.out.println("1. Cedula.");
            System.out.println("2. Nombre.");
            System.out.println("3. Telefono.");
            System.out.println("4. Mostrar todos los ZooAmigos");
            System.out.println("0. Regresar al menu anterior.");
            option = input.next();
            System.out.println();
            switch (option) {
                case "1":
                    String option1;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor maximo.");
                    System.out.println("3. Valor minimo.");
                    System.out.println("4. Rango.");
                    option1 = input.next();
                    if (!option1.equals("1") && !option1.equals("2") && !option1.equals("3") && !option1.equals("4")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option1.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaZooAmigo(1, cedulaBus, 0);
                        break;
                    } else if (option1.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaZooAmigo(2, cedulaBus, 0);
                        break;
                    } else if (option1.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaZooAmigo(3, cedulaBus, 0);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int cedulaBusMin = input.nextInt();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        int cedulaBusMax = input.nextInt();
                        cedulaZooAmigo(4, cedulaBusMin, cedulaBusMax);
                        break;
                    }
                    break;
                case "2":
                    String option2;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option2 = input.next();
                    if (!option2.equals("1") && !option2.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option2.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String nombreBus = input.next();
                        nombreZooAmigo(nombreBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String nombreBus = input.next();
                        nombreZooAmigo(nombreBus);
                        break;
                    }
                    break;
                case "3":
                    String option3;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor maximo.");
                    System.out.println("3. Valor minimo.");
                    System.out.println("4. Rango.");
                    option3 = input.next();
                    if (!option3.equals("1") && !option3.equals("2") && !option3.equals("3") && !option3.equals("4")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option3.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int telefonoBus = input.nextInt();
                        telefonoZooAmigo(1, telefonoBus, 0);
                        break;
                    } else if (option3.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int telefonoBus = input.nextInt();
                        telefonoZooAmigo(2, telefonoBus, 0);
                        break;
                    } else if (option3.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int telefonoBus = input.nextInt();
                        telefonoZooAmigo(3, telefonoBus, 0);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int telefonoBusMin = input.nextInt();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        int telefonoBusMax = input.nextInt();
                        telefonoZooAmigo(4, telefonoBusMin, telefonoBusMax);
                        break;
                    }
                    break;
                case "4":
                    mostrarTodosZooAmigos();
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void cedulaZooAmigo(int opcionValor, int cedulaBusMin, int cedulaBusMax) {
        ZooAmigo zooA;
        int i = 0;
        ArrayList<ZooAmigo> cedulaZooAmigoGen = new ArrayList<>();
        if (opcionValor == 1) {
            for (ZooAmigo zooAmigo : zooAmigos) {
                if (cedulaBusMin == zooAmigo.cedula) {
                    cedulaZooAmigoGen.add(zooAmigo);
                }
            }
        } else if (opcionValor == 2) {
            for (ZooAmigo zooAmigo : zooAmigos) {
                if (cedulaBusMin >= zooAmigo.cedula) {
                    cedulaZooAmigoGen.add(zooAmigo);
                }
            }
        } else if (opcionValor == 3) {
            for (ZooAmigo zooAmigo : zooAmigos) {
                if (cedulaBusMin <= zooAmigo.cedula) {
                    cedulaZooAmigoGen.add(zooAmigo);
                }
            }
        } else {
            for (ZooAmigo zooAmigo : zooAmigos) {
                if (cedulaBusMin <= zooAmigo.cedula && zooAmigo.cedula <= cedulaBusMax) {
                    cedulaZooAmigoGen.add(zooAmigo);
                }
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Nombre.");
        System.out.println("3. Telefono.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    cedulaZooAmigoGen.sort((comparadoresZooAmigo[0]));
                    for (ZooAmigo zooAmigo : cedulaZooAmigoGen) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                }else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    cedulaZooAmigoGen.sort(Collections.reverseOrder(comparadoresZooAmigo[0]));
                    for (ZooAmigo zooAmigo : cedulaZooAmigoGen) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    cedulaZooAmigoGen.sort((comparadoresZooAmigo[1]));
                    for (ZooAmigo zooAmigo : cedulaZooAmigoGen) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    cedulaZooAmigoGen.sort(Collections.reverseOrder(comparadoresZooAmigo[1]));
                    for (ZooAmigo zooAmigo : cedulaZooAmigoGen) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    cedulaZooAmigoGen.sort((comparadoresZooAmigo[2]));
                    for (ZooAmigo zooAmigo : cedulaZooAmigoGen) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                }else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    cedulaZooAmigoGen.sort(Collections.reverseOrder(comparadoresZooAmigo[2]));
                    for (ZooAmigo zooAmigo : cedulaZooAmigoGen) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "4":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    cedulaZooAmigoGen.sort((comparadoresZooAmigo[3]));
                    for (ZooAmigo zooAmigo : cedulaZooAmigoGen) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    cedulaZooAmigoGen.sort(Collections.reverseOrder(comparadoresZooAmigo[3]));
                    for (ZooAmigo zooAmigo : cedulaZooAmigoGen) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        //Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (ZooAmigo zooAmigo : cedulaZooAmigoGen) {
        //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zooAmigo que desea editar: \n");
                eleccion = input.nextInt();
                zooA = cedulaZooAmigoGen.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + zooA.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(zooAmigo.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Nombre : " + zooA.nombre);
                String nuevoNombre = input.nextLine();

                System.out.println("Telefono : " + zooA.telefono);
                String nuevoTelefono = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else zooA.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoNombre.isEmpty()) {
                                } else zooA.nombre = nuevoNombre;
                                if (nuevoTelefono.isEmpty()) {
                                } else zooA.telefono = nuevoTelefono;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zooAmigo que desea eliminar: \n");
                eleccion = input.nextInt();
                zooA = cedulaZooAmigoGen.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Animal animal : animales) {
                    if (animal.zooAmigo != null && animal.zooAmigo.cedula == zooA.cedula) {
                        animal.zooAmigo = null;
                    }
                }

                for (Zoologico zoologico : zoologicos) {
                    ZooAmigo finalZooA = zooA;
                    zoologico.zooAmigos.removeIf(zooAmigo1 -> zooAmigo1.cedula == finalZooA.cedula);
                }

                zooAmigos.remove(zooA);

                System.out.println("El zooAmigo se ha eliminado correctamente");
            }else return;
        }
    }
    public static void nombreZooAmigo(String nombreBus) {
        ZooAmigo zooA;
        int i=0;
        ArrayList<ZooAmigo> nombreZA = new ArrayList<>();
        for (ZooAmigo zooAmigo : zooAmigos) {
            if (nombreBus.equals(zooAmigo.nombre) || nombreBus.equals(zooAmigo.nombre.toLowerCase())) {
                nombreZA.add(zooAmigo);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Telefono.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")) {
                System.out.println("Ascendente");
                nombreZA.sort((comparadoresZooAmigo[0]));
                for (ZooAmigo zooAmigo : nombreZA) {
                    i++;
                    System.out.println(i+". "+zooAmigo);
                }
            }
            else if (option2.equals("2")) {
                System.out.println("Descendente");
                nombreZA.sort(Collections.reverseOrder(comparadoresZooAmigo[0]));
                for (ZooAmigo zooAmigo : nombreZA) {
                    i++;
                    System.out.println(i+". "+zooAmigo);
                }
            }
            else {
                System.out.println("Opcion incorrecta");
                return;
            }
        }
        else if (option.equals("2")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")) {
                System.out.println("Ascendente");
                nombreZA.sort((comparadoresZooAmigo[3]));
                for (ZooAmigo zooAmigo : nombreZA) {
                    i++;
                    System.out.println(i+". "+zooAmigo);
                }
            }
            else if (option2.equals("2")) {
                System.out.println("Descendente");
                nombreZA.sort(Collections.reverseOrder(comparadoresZooAmigo[3]));
                for (ZooAmigo zooAmigo : nombreZA) {
                    i++;
                    System.out.println(i+". "+zooAmigo);
                }
            }
            else {
                System.out.println("Opcion incorrecta");
                return;
            }
        }
        else {
            return;
        }//Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        //Codigo editar
        for (ZooAmigo zooAmigo : nombreZA) {
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zooAmigo que desea editar: \n");
                eleccion = input.nextInt();
                zooA = nombreZA.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + zooA.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(zooAmigo.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Nombre : " + zooA.nombre);
                String nuevoNombre = input.nextLine();

                System.out.println("Telefono : " + zooA.telefono);
                String nuevoTelefono = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else zooA.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoNombre.isEmpty()) {
                                } else zooA.nombre = nuevoNombre;
                                if (nuevoTelefono.isEmpty()) {
                                } else zooA.telefono = nuevoTelefono;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zooAmigo que desea eliminar: \n");
                eleccion = input.nextInt();
                zooA = nombreZA.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Animal animal : animales) {
                    if (animal.zooAmigo != null && animal.zooAmigo.cedula == zooA.cedula) {
                        animal.zooAmigo = null;
                    }
                }

                for (Zoologico zoologico : zoologicos) {
                    zoologico.zooAmigos.removeIf(zooAmigo1 -> zooAmigo1.cedula == zooA.cedula);
                }

                zooAmigos.remove(zooA);

                System.out.println("El zooAmigo se ha eliminado correctamente");
                return;
            } else return;
        }
    }
    public static void telefonoZooAmigo(int opcionValor, int telefonoBusMin, int telefonoBusMax) {
        ZooAmigo zooA;
        int i=0;
        ArrayList<ZooAmigo> telefonoZooAmigoGen = new ArrayList<>();
        if (opcionValor == 1) {
            for (ZooAmigo zooAmigo : zooAmigos) {
                int telefono = Integer.parseInt(zooAmigo.telefono);
                if (telefonoBusMin == telefono) {
                    telefonoZooAmigoGen.add(zooAmigo);
                }
            }
        }
        else if (opcionValor == 2) {
            for (ZooAmigo zooAmigo : zooAmigos) {
                int telefono = Integer.parseInt(zooAmigo.telefono);
                if (telefonoBusMin >= telefono) {
                    telefonoZooAmigoGen.add(zooAmigo);
                }
            }
        }
        else if (opcionValor == 3) {
            for (ZooAmigo zooAmigo : zooAmigos) {
                int telefono = Integer.parseInt(zooAmigo.telefono);
                if (telefonoBusMin <= telefono) {
                    telefonoZooAmigoGen.add(zooAmigo);
                }
            }
        }
        else {
            for (ZooAmigo zooAmigo : zooAmigos) {
                int telefono = Integer.parseInt(zooAmigo.telefono);
                if (telefonoBusMin <= telefono && telefono <= telefonoBusMax) {
                    telefonoZooAmigoGen.add(zooAmigo);
                }
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Nombre.");
        System.out.println("3. Telefono.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    telefonoZooAmigoGen.sort((comparadoresZooAmigo[0]));
                    for (ZooAmigo zooAmigo : telefonoZooAmigoGen) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                }else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    telefonoZooAmigoGen.sort(Collections.reverseOrder(comparadoresZooAmigo[0]));
                    for (ZooAmigo zooAmigo : telefonoZooAmigoGen) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    telefonoZooAmigoGen.sort((comparadoresZooAmigo[1]));
                    for (ZooAmigo zooAmigo : telefonoZooAmigoGen) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    telefonoZooAmigoGen.sort(Collections.reverseOrder(comparadoresZooAmigo[1]));
                    for (ZooAmigo zooAmigo : telefonoZooAmigoGen) {
                            i++;
                            System.out.println(i + ". " + zooAmigo);
                        }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    telefonoZooAmigoGen.sort((comparadoresZooAmigo[2]));
                    for (ZooAmigo zooAmigo : telefonoZooAmigoGen) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                }else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    telefonoZooAmigoGen.sort(Collections.reverseOrder(comparadoresZooAmigo[2]));
                    for (ZooAmigo zooAmigo : telefonoZooAmigoGen) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        //Menu editar - eliminar
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (ZooAmigo zooAmigo : telefonoZooAmigoGen) {
        //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zooAmigo que desea editar: \n");
                eleccion = input.nextInt();
                zooA = telefonoZooAmigoGen.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + zooA.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(zooAmigo.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Nombre : " + zooA.nombre);
                String nuevoNombre = input.nextLine();

                System.out.println("Telefono : " + zooA.telefono);
                String nuevoTelefono = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else zooA.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoNombre.isEmpty()) {
                                } else zooA.nombre = nuevoNombre;
                                if (nuevoTelefono.isEmpty()) {
                                } else zooA.telefono = nuevoTelefono;
                            case "N":
                                break;
                        }return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if(accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zooAmigo que desea eliminar: \n");
                eleccion = input.nextInt();
                zooA = telefonoZooAmigoGen.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Animal animal : animales) {
                    if (animal.zooAmigo != null && animal.zooAmigo.cedula == zooA.cedula) {
                        animal.zooAmigo = null;
                    }
                }

                for (Zoologico zoologico : zoologicos) {
                    zoologico.zooAmigos.removeIf(zooAmigo1 -> zooAmigo1.cedula == zooA.cedula);
                }

                zooAmigos.remove(zooA);

                System.out.println("El zooAmigo se ha eliminado correctamente");
                return;
            }
        }
    }
    public static void mostrarTodosZooAmigos() {
        ZooAmigo zooA;
        int i=0;
        ArrayList<ZooAmigo> copiaZooAmigo = new ArrayList<>(zooAmigos);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea ordenar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Nombre.");
        System.out.println("3. Telefono.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        switch (option) {
            case "1":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaZooAmigo.sort((comparadoresZooAmigo[0]));
                    for (ZooAmigo zooAmigo : copiaZooAmigo) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaZooAmigo.sort(Collections.reverseOrder(comparadoresZooAmigo[0]));
                    for (ZooAmigo zooAmigo : copiaZooAmigo) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "2":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaZooAmigo.sort((comparadoresZooAmigo[1]));
                    for (ZooAmigo zooAmigo : copiaZooAmigo) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaZooAmigo.sort(Collections.reverseOrder(comparadoresZooAmigo[1]));
                    for (ZooAmigo zooAmigo : copiaZooAmigo) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            case "3":
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")) {
                    System.out.println("Ascendente");
                    copiaZooAmigo.sort((comparadoresZooAmigo[2]));
                    for (ZooAmigo zooAmigo : copiaZooAmigo) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    copiaZooAmigo.sort(Collections.reverseOrder(comparadoresZooAmigo[2]));
                    for (ZooAmigo zooAmigo : copiaZooAmigo) {
                        i++;
                        System.out.println(i + ". " + zooAmigo);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                    return;
                }
                break;
            default:
                return;
        }
        String accion;
        System.out.println("-----------------------------------------");
        System.out.println("\nIndique la accion que desea realizar:\n");
        System.out.println("1. Editar ");
        System.out.println("2. Eliminar ");
        System.out.println("0. Regresar al menú anterior" + "\n");
        accion = input.next();
        System.out.println("----------------------------------");

        for (ZooAmigo zooAmigo : copiaZooAmigo) {
            //Codigo editar
            if (accion.equals("1")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zooAmigo que desea editar: \n");
                eleccion = input.nextInt();
                zooA = copiaZooAmigo.get(eleccion - 1);

                input.nextLine();
                System.out.println("\nCedula: " + zooA.cedula);
                String nuevoCedula = input.nextLine();
                if(nuevoCedula.equals(Integer.toString(zooAmigo.cedula))){
                    System.out.println("ERROR: La cedula ya se encuentra registrada");
                }

                System.out.println("Nombre : " + zooA.nombre);
                String nuevoNombre = input.nextLine();

                System.out.println("Telefono : " + zooA.telefono);
                String nuevoTelefono = input.nextLine();

                while (true) {
                    System.out.print("Desea guardar?:[Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                if (nuevoCedula.isEmpty()) {
                                } else zooA.cedula = Integer.parseInt(nuevoCedula);
                                if (nuevoNombre.isEmpty()) {
                                } else zooA.nombre = nuevoNombre;
                                if (nuevoTelefono.isEmpty()) {
                                } else zooA.telefono = nuevoTelefono;
                            case "N":
                                break;
                        }
                        return;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }
            }//Codigo eliminar
            else if (accion.equals("2")) {
                int eleccion;
                System.out.println("------------------------------------------");
                System.out.println("Escoja el zooAmigo que desea eliminar: \n");
                eleccion = input.nextInt();
                zooA = copiaZooAmigo.get(eleccion - 1);

                while (true) {
                    System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
                    String optionE = input.next();
                    if (optionE.equalsIgnoreCase("Y") || optionE.equalsIgnoreCase("N")) {
                        switch (optionE.toUpperCase()) {
                            case "Y":
                                break;
                            case "N":
                                return;
                        }
                        break;
                    } else {
                        System.out.println("Opcion invalida\n");
                    }
                }

                for (Animal animal : animales) {
                    if (animal.zooAmigo != null && animal.zooAmigo.cedula == zooA.cedula) {
                        animal.zooAmigo = null;
                    }
                }

                for (Zoologico zoologico : zoologicos) {
                    zoologico.zooAmigos.removeIf(zooAmigo1 -> zooAmigo1.cedula == zooA.cedula);
                }

                zooAmigos.remove(zooA);

                System.out.println("El zooAmigo se ha eliminado correctamente");
                return;
            } else return;
        }
    }

    public static void diagnosticoDeInconsistencias(){
        System.out.println("Zoologicos: ");
        for(Zoologico zoologico : zoologicos){
            if(zoologico.biomas.isEmpty() && zoologico.profesionales.isEmpty() && zoologico.zooAmigos.isEmpty()){
                System.out.println("El zoologico con NIT " + zoologico.nit + " no tiene asociado ningún bioma, profesional o zooAmigo.");
            }
            if(zoologico.biomas.isEmpty() && zoologico.profesionales.isEmpty() && !zoologico.zooAmigos.isEmpty()){
                System.out.println("El zoologico con NIT " + zoologico.nit + " no tiene asociado ningún bioma o profesional.");

            }
            if(zoologico.biomas.isEmpty() && !zoologico.profesionales.isEmpty() && zoologico.zooAmigos.isEmpty()){
                System.out.println("El zoologico con NIT " + zoologico.nit + " no tiene asociado ningún bioma o zooAmigo.");

            }
            if(zoologico.biomas.isEmpty() && !zoologico.profesionales.isEmpty() && !zoologico.zooAmigos.isEmpty()){
                System.out.println("El zoologico con NIT " + zoologico.nit + " no tiene asociado ningún bioma.");

            }
            if(!zoologico.biomas.isEmpty() && zoologico.profesionales.isEmpty() && !zoologico.zooAmigos.isEmpty()){
                System.out.println("El zoologico con NIT " + zoologico.nit + " no tiene asociado ningún profesional.");

            }
            if(!zoologico.biomas.isEmpty() && zoologico.profesionales.isEmpty() && zoologico.zooAmigos.isEmpty()){
                System.out.println("El zoologico con NIT " + zoologico.nit + " no tiene asociado ningún zooAmigo o profesional.");

            }
            if(!zoologico.biomas.isEmpty() && !zoologico.profesionales.isEmpty() && zoologico.zooAmigos.isEmpty()){
                System.out.println("El zoologico con NIT " + zoologico.nit + " no tiene asociado ningún zooAmigo.");

            }
            else if (!zoologico.biomas.isEmpty() && !zoologico.profesionales.isEmpty() && !zoologico.zooAmigos.isEmpty()) {
                System.out.println("El zoologico con NIT" + zoologico.nit + " tiene al menos un bioma, profesional y zooAigo asociado");
            }
        }
        System.out.println("");

        System.out.println("Biomas: ");
        for(Bioma bioma : biomas) {
            if (bioma.zoologico == null && bioma.profesionales.isEmpty() && bioma.habitats.isEmpty()) {
                System.out.println("El bioma con ID " + bioma.id + " no tiene zoologicos, profesionales, ni habitats asociados");
            }
            if (bioma.zoologico == null && bioma.profesionales.isEmpty() && !bioma.habitats.isEmpty()) {
                System.out.println("El bioma con ID " + bioma.id + " no tiene zoologicos, ni profesionales asociados");
            }
            if (bioma.zoologico == null && !bioma.profesionales.isEmpty() && bioma.habitats.isEmpty()) {
                System.out.println("El bioma con ID " + bioma.id + " no tiene zoologicos, ni habitats asociados");
            }
            if (bioma.zoologico == null && !bioma.profesionales.isEmpty() && !bioma.habitats.isEmpty()) {
                System.out.println("El bioma con ID " + bioma.id + " no tiene zoologicos asociados");
            }
            if (bioma.zoologico != null && bioma.profesionales.isEmpty() && bioma.habitats.isEmpty()) {
                System.out.println("El bioma con ID " + bioma.id + " no tiene profesionales, ni habitats asociados");
            }
            if (bioma.zoologico != null && bioma.profesionales.isEmpty() && !bioma.habitats.isEmpty()) {
                System.out.println("El bioma con ID " + bioma.id + " no tiene profesionales asociados");
            }
            if (bioma.zoologico != null && !bioma.profesionales.isEmpty() && bioma.habitats.isEmpty()) {
                System.out.println("El bioma con ID " + bioma.id + " no tiene habitats asociados");
            }
            if (bioma.zoologico != null && !bioma.profesionales.isEmpty() && !bioma.habitats.isEmpty()) {
                System.out.println("El bioma con ID " + bioma.id + " tiene al menos un zoologico, profesional y habitat asociado");
            }
        }
        System.out.println("");

        System.out.println("Habitats: ");
        for(Habitat habitat : habitats){
            if(habitat.bioma == null && habitat.animales.isEmpty() && habitat.tecnicos.isEmpty()){
                System.out.println("El habitat con ID " + habitat.id + " no tiene asociado ningún bioma, animal o tecnico");
            }

            if(habitat.bioma == null && habitat.animales.isEmpty() && !habitat.tecnicos.isEmpty()){
                System.out.println("El habitat con ID " + habitat.id + " no tiene asociado ningún bioma o animal");
            }

            if(habitat.bioma == null && !habitat.animales.isEmpty() && habitat.tecnicos.isEmpty()){
                System.out.println("El habitat con ID " + habitat.id + " no tiene asociado ningún bioma o tecnico");
            }

            if(habitat.bioma == null && !habitat.animales.isEmpty() && !habitat.tecnicos.isEmpty()){
                System.out.println("El habitat con ID " + habitat.id + " no tiene asociado ningún bioma");
            }

            if(habitat.bioma != null && habitat.animales.isEmpty() && habitat.tecnicos.isEmpty()){
                System.out.println("El habitat con ID " + habitat.id + " no tiene asociado ningún animal o tecnico");
            }

            if(habitat.bioma != null && habitat.animales.isEmpty() && !habitat.tecnicos.isEmpty()){
                System.out.println("El habitat con ID " + habitat.id + " no tiene asociado ningún animal");
            }

            if(habitat.bioma != null && !habitat.animales.isEmpty() && habitat.tecnicos.isEmpty()){
                System.out.println("El habitat con ID " + habitat.id + " no tiene asociado ningún tecnico");
            }

            if(habitat.bioma != null && !habitat.animales.isEmpty() && !habitat.tecnicos.isEmpty()){
                System.out.println("El habitat con ID " + habitat.id + " tiene asociado al menos un bioma, animal y tecnico");
            }
        }
        System.out.println("");

        System.out.println("Animales: ");
        for(Animal animal : animales){
            if(animal.habitat==null && animal.zooAmigo == null){
                System.out.println("El animal con ID " + animal.id + " no tiene asociado ningún habitat o zooAmigo");
            }
            if(animal.habitat==null && animal.zooAmigo != null){
                System.out.println("El animal con ID " + animal.id + " no tiene asociado ningún habitat");
            }
            if(animal.habitat != null && animal.zooAmigo == null){
                System.out.println("El animal con ID " + animal.id + " no tiene asociado ningún zooAmigo");
            }
            if(animal.habitat!=null && animal.zooAmigo != null){
                System.out.println("El animal con ID " + animal.id + " tiene asociado al menos un habitat y zooAmigo");
            }
        }
        System.out.println("");

        System.out.println("Tecnicos: ");
        for(Tecnico tecnico : tecnicos){
            if(tecnico.habitats.isEmpty()){
                System.out.println("El tecnico con cedula # " + tecnico.cedula + " no tiene ningún habitat asociado");
            }
            else System.out.println("El tecnico con cedula # " + tecnico.cedula + " tiene por lo menos un habitat asociado");

        }
        System.out.println("");

        System.out.println("Profesionales: ");
        for(Profesional profesional : profesionales){
            if(profesional.zoologico == null && profesional.biomas.isEmpty()){
                System.out.println("El profesional con cedula # " + profesional.cedula + " no tiene zoologicos o biomas asociados");
            }
            if(profesional.zoologico == null && !profesional.biomas.isEmpty()){
                System.out.println("El profesional con cedula # " + profesional.cedula + " no tiene zoologicos asociados");
            }
            if(profesional.zoologico != null && profesional.biomas.isEmpty()){
                System.out.println("El profesional con cedula # " + profesional.cedula + " no tiene biomas asociados");
            }
            if(profesional.zoologico != null && !profesional.biomas.isEmpty()){
                System.out.println("El profesional con cedula # " + profesional.cedula + " tiene al menos un zoologico y bioma asociado");
            }
        }
        System.out.println("");

        System.out.println("ZooAmigos: ");
        for(ZooAmigo zooAmigo : zooAmigos){
            if(zooAmigo.zoologico == null && zooAmigo.animales.isEmpty()){
                System.out.println("El zooAmigo con cedula # " + zooAmigo.cedula + " no tiene ningun zoologico o animal asociado");
            }
            if(zooAmigo.zoologico == null && !zooAmigo.animales.isEmpty()){
                System.out.println("El zooAmigo con cedula # " + zooAmigo.cedula + " no tiene ningun zoologico asociado");
            }
            if(zooAmigo.zoologico != null && zooAmigo.animales.isEmpty()){
                System.out.println("El zooAmigo con cedula # " + zooAmigo.cedula + " no tiene ningun animal asociado");
            }
            if(zooAmigo.zoologico != null && !zooAmigo.animales.isEmpty()){
                System.out.println("El zooAmigo con cedula # " + zooAmigo.cedula + " tiene al menos un zoologico y animal asociado");
            }
        }
        System.out.println("");
    }

}
