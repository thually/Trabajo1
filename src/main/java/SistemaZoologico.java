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
    public static ArrayList<Zoologico> copiaHabitat = new ArrayList<>();
    public static Comparator<Zoologico>[] comparadoresZoologico = new Comparator[4];
    public static Comparator<Bioma>[] comparadoresBioma = new Comparator[6];
    public static Comparator<Habitat>[] comparadoresHabitat = new Comparator[6];
    public static Comparator<Animal>[] comparadoresAnimal = new Comparator[6];
    public static Comparator<Tecnico>[] comparadoresTecnico = new Comparator[5];
    public static Comparator<Profesional>[] comparadoresProfesional = new Comparator[6];
    public static Comparator<ZooAmigo>[] comparadoresZooAmigo = new Comparator[3];

    ArrayList<Bioma> copiaBio = new ArrayList<>(biomas);
    ArrayList<Zoologico> copiaAnimal = new ArrayList<>(copiaHabitat);

    public static void main(String[] args) {

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
            if (copiaHabitat.isEmpty()) {
                System.out.println("Aun no se encuentran zoologicos registrados");
                return;
            }
        }
        switch (accion) {
            case "1":
                for (Zoologico zoologico : copiaHabitat) {
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
        for (Zoologico zoologico : copiaHabitat) {
            if (zoologico.nit.replace(".", "").equals(nuevoNIT.replace(".", ""))) {
                System.out.println("ERROR: Ya existe un zoologico registrado con este NIT.");
                return;
            }
        }
        System.out.print("Ingrese el nombre del nuevo zoologico: ");
        String nuevoNombre = input.next();
        System.out.print("Ingrese las siglas del nuevo zoologico: ");
        String nuevoSiglas = input.next();
        for (Zoologico zoologico : copiaHabitat) {
            if (zoologico.siglas.equalsIgnoreCase(nuevoSiglas)) {
                System.out.println("ERROR: Ya existe un zoologico registrado con estas siglas.");
                return;
            }
        }
        System.out.print("Ingrese la ciudad del nuevo zoologico: ");
        String nuevaCiudad = input.next();

        Zoologico nuevoZoo = new Zoologico(nuevoNIT, nuevoNombre, nuevoSiglas, nuevaCiudad);
        copiaHabitat.add(nuevoZoo);
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
                for (Zoologico zoo : copiaHabitat) {
                    if (zoo.nit.replace(".", "").equals(opcion2.replace(".", ""))) {
                        zoologico = zoo;
                    }
                }
            } else {
                System.out.print("Ingrese siglas: ");
                String opcion2 = input.next();
                for (Zoologico zoo : copiaHabitat) {
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
                    for (Zoologico zoo : copiaHabitat) {
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

                    for (Zoologico zoo : copiaHabitat) {
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

                    for (Zoologico zoo : copiaHabitat) {
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
                for (Zoologico zoo : copiaHabitat) {
                    if (zoo.nit.replace(".", "").equals(opcion2.replace(".", ""))) {
                        zoologico = zoo;
                    }
                }
            } else {
                System.out.print("Ingrese siglas: ");
                String opcion2 = input.next();
                for (Zoologico zoo : copiaHabitat) {
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
        copiaHabitat.remove(zoologico);

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
                    if (copiaHabitat.isEmpty()) {
                        System.out.println("Aun no hay zoologicos registrados");
                        return;
                    }
                    System.out.println("\nEstos son los zoologicos disponibles:\n");
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                    Zoologico ZooNuevo = null;
                    while (true) {
                        System.out.print("\nIngrese el NIT del zoologico con el que quiere asociar este bioma: ");
                        String nit = input.next();
                        for (Zoologico zoologico : copiaHabitat) {
                            if (zoologico.nit.replace(".", "").equals(nit.replace(".", ""))) {
                                ZooNuevo = zoologico;
                                break;
                            }
                        }
                        if (ZooNuevo == null) {
                            System.out.println("\nNIT no coincide con ningun zoologico");
                        } else break;
                    }

                    for (Zoologico zoo : copiaHabitat) {
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

        for (Zoologico zoologico : copiaHabitat) {
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
                    for (Zoologico zoologico1 : copiaHabitat) {
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
                            for (Zoologico zoo : copiaHabitat) {
                                if (zoo.nit.replace(".", "").equals(opcion2.replace(".", ""))) {
                                    zoologico = zoo;
                                }
                            }
                        } else {
                            System.out.print("Ingrese siglas: ");
                            String opcion2 = input.next();
                            for (Zoologico zoo : copiaHabitat) {
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
                    for (Zoologico zoologico1 : copiaHabitat) {
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
                                copiaHabitat) {
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
                    if (copiaHabitat.isEmpty()) {
                        System.out.println("Aun no hay Zoologicos registrados");
                        return;
                    }
                    System.out.println("\nEstos son los zoologicos disponibles:\n");
                    for (Zoologico zoologico : copiaHabitat) {
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
                            for (Zoologico zoo : copiaHabitat) {
                                if (zoo.nit.replace(".", "").equals(opcion2.replace(".", ""))) {
                                    zoologico = zoo;
                                }
                            }
                        } else {
                            System.out.print("Ingrese siglas: ");
                            String opcion2 = input.next();
                            for (Zoologico zoo : copiaHabitat) {
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
                            copiaHabitat) {
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
                                copiaHabitat) {
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
                    if (copiaHabitat.size() == 0) {
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
            System.out.println("5. Bioma.");
            System.out.println("6. Profesional.");
            System.out.println("7. ZooAmigo.");
            System.out.println("8. Mostrar todos los zoologicos.");
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
                        nitZoologico(1, nitBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String nitBus = input.next();
                        nitZoologico(2, nitBus);
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
                        nombreZoologico(1, nombreBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String nombreBus = input.next();
                        nombreZoologico(2, nombreBus);
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
                        siglasZoologico(1, siglasBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String siglasBus = input.next();
                        siglasZoologico(2, siglasBus);
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
                        ciudadZoologico(1, ciudadBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String ciudadBus = input.next();
                        ciudadZoologico(2, ciudadBus);
                        break;
                    }
                    break;
                case "5":
                    String option5;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option5 = input.next();
                    if (!option5.equals("1") && !option5.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option5.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String biomaBus = input.next();
                        biomaZoologico(1, biomaBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String biomaBus = input.next();
                        biomaZoologico(2, biomaBus);
                        break;
                    }
                    break;
                case "6":
                    String option6;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option6 = input.next();
                    if (!option6.equals("1") && !option6.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option6.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String profesionalBus = input.next();
                        profesionalZoologico(1, profesionalBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String profesionalBus = input.next();
                        profesionalZoologico(2, profesionalBus);
                        break;
                    }
                    break;
                case "7":
                    String option7;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option7 = input.next();
                    if (!option7.equals("1") && !option7.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    } else if (option7.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String zooAmigoBus = input.next();
                        zooAmigoZoologico(1, zooAmigoBus);
                        break;
                    } else {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String zooAmigoBus = input.next();
                        zooAmigoZoologico(2, zooAmigoBus);
                        break;
                    }
                    break;
                case "8":
                    String option8;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
                    System.out.println("1. Nit.");
                    System.out.println("2. Nombre.");
                    System.out.println("3. Siglas.");
                    System.out.println("4. Ciudad.");
                    System.out.println("5. Bioma.");
                    System.out.println("6. Profesional.");
                    System.out.println("7. ZooAmigo.");
                    System.out.println("0. Regresar al menu anterior.");
                    option8 = input.next();
                    System.out.println();
                    break;
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void nitZoologico(int opcionValor, String nitBus) {
        ArrayList<Zoologico> copiaZoo = new ArrayList<>();
        if (opcionValor == 1) {
            for (Zoologico zoologico : copiaZoo) {
                if (nitBus.equals(zoologico.nit)) {
                    System.out.println(zoologico);
                }
            }
            return;

        } else if (opcionValor == 2) {
            for (Zoologico zoologico : copiaZoo) {
                if (nitBus.equals(zoologico.nit.replace(".", ""))) {
                    System.out.println(zoologico);
                }
            }
            return;

        }

    }
    public static void nombreZoologico(int opcionValor, String nombre) {
        ArrayList<Zoologico> copiaZoo = new ArrayList<>();
        ArrayList<Zoologico> nombreZoo = new ArrayList<>();
        for (Zoologico zoologico : copiaZoo) {
            if (nombre.equals(zoologico.nombre) || nombre.equals(zoologico.nombre.toLowerCase())) {
                nombreZoo.add(zoologico);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Nombre.");
        System.out.println("2. Siglas.");
        System.out.println("3. Ciudad.");
        System.out.println("4. Bioma.");
        System.out.println("5. Profesional.");
        System.out.println("6. ZooAmigo.");
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
                Collections.sort(nombreZoo, (comparadoresZoologico[0]));
                for (Zoologico zoologico : nombreZoo) {
                    System.out.println(zoologico);
                }
            } else if (option2.equals("2")) {
                System.out.println("Descendente");
                Collections.sort(nombreZoo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : nombreZoo) {
                    System.out.println(zoologico);
                }
            } else {
                System.out.println("Opcion incorrecta");
            }
        } else if (option.equals("2")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(nombreZoo, Collections.reverseOrder(comparadoresZoologico[1]));
                    for (Zoologico zoologico : nombreZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(nombreZoo, Collections.reverseOrder(comparadoresZoologico[1]));
                    for (Zoologico zoologico : nombreZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("3")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(nombreZoo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : nombreZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(nombreZoo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : nombreZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("4")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(nombreZoo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : nombreZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(nombreZoo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : nombreZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("5")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(nombreZoo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : nombreZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(nombreZoo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : nombreZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("6")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(nombreZoo, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : nombreZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(nombreZoo, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : nombreZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("7")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(nombreZoo, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : nombreZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(nombreZoo, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : nombreZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else {
            return;
        }
    }
    public static void siglasZoologico(int opcionValor, String siglas) {
        ArrayList<Zoologico> copiaZoo = new ArrayList<>();
        ArrayList<Zoologico> siglasZoo = new ArrayList<>();
        for (Zoologico zoologico : copiaZoo) {
            if (siglas.equals(zoologico.siglas) || siglas.equals(zoologico.siglas.toLowerCase())) {
                siglasZoo.add(zoologico);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Nombre.");
        System.out.println("3. Siglas.");
        System.out.println("4. Ciudad.");
        System.out.println("5. Bioma.");
        System.out.println("6. Profesional.");
        System.out.println("7. ZooAmigo.");
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
                Collections.sort(copiaZoo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZoo) {
                    System.out.println(zoologico);
                }
            } else if (option2.equals("2")) {
                System.out.println("Descendente");
            } else {
                System.out.println("Opcion incorrecta");
            }
        } else if (option.equals("2")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")) {
                System.out.println("Ascendente");
                Collections.sort(siglasZoo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : siglasZoo) {
                    System.out.println(zoologico);
                }
            } else if (option2.equals("2")) {
                System.out.println("Descendente");
                Collections.sort(siglasZoo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : siglasZoo) {
                    ArrayList<Zoologico> reverse = new ArrayList<>(siglasZoo);

                    System.out.println(zoologico);
                }
            } else {
                System.out.println("Opcion incorrecta");
            }
        } else if (option.equals("3")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(siglasZoo, (comparadoresZoologico[2]));
                    for (Zoologico zoologico : siglasZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(siglasZoo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : siglasZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("4")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(siglasZoo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : siglasZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(siglasZoo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : siglasZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("5")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(siglasZoo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : siglasZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(siglasZoo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : siglasZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("6")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(siglasZoo, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : siglasZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(siglasZoo, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : siglasZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("7")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(siglasZoo, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : siglasZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(siglasZoo, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : siglasZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else {
            return;
        }
    }
    public static void ciudadZoologico(int opcionValor, String ciudad){
        ArrayList<Zoologico> copiaZoo = new ArrayList<>();
        ArrayList<Zoologico> ciudadZoo = new ArrayList<>();
        for (Zoologico zoologico : copiaZoo) {
            if (ciudad.equals(zoologico.ciudad) || ciudad.equals(zoologico.ciudad.toLowerCase())) {
                ciudadZoo.add(zoologico);
            }
        }

        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Nit.");
        System.out.println("2. Nombre.");
        System.out.println("3. Siglas.");
        System.out.println("4. Ciudad.");
        System.out.println("5. Bioma.");
        System.out.println("6. Profesional.");
        System.out.println("7. ZooAmigo.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaZoo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZoo) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }
    }
    public static void biomaZoologico(int opcionValor, String bioma){
        ArrayList<Zoologico> copiaZoo = new ArrayList<>(copiaHabitat);
        ArrayList<Zoologico> biomaZoo = new ArrayList<>();
        for (Zoologico zoologico : copiaZoo) {
            if (bioma.equals(zoologico.biomas) || bioma.equals(zoologico.biomas)) {
                biomaZoo.add(zoologico);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Nombre.");
        System.out.println("3. Siglas.");
        System.out.println("4. Ciudad.");
        System.out.println("5. Bioma.");
        System.out.println("6. Profesional.");
        System.out.println("7. ZooAmigo.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaZoo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZoo) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(biomaZoo, Collections.reverseOrder(comparadoresZoologico[1]));
                for (Zoologico zoologico : biomaZoo) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(biomaZoo, Collections.reverseOrder(comparadoresZoologico[1]));
                for (Zoologico zoologico : biomaZoo) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(biomaZoo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : biomaZoo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(biomaZoo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : biomaZoo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(biomaZoo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : biomaZoo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(biomaZoo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : biomaZoo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();

                if (option2.equals("1")){
                    System.out.println("Ascendente");


                    Collections.sort(biomaZoo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : biomaZoo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(biomaZoo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : biomaZoo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(biomaZoo, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : biomaZoo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(biomaZoo, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : biomaZoo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("7")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(biomaZoo, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : biomaZoo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(biomaZoo, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : biomaZoo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void profesionalZoologico(int opcionValor, String profesional) {
        ArrayList<Zoologico> copiaZoo = new ArrayList<>(copiaHabitat);
        ArrayList<Zoologico> profesionalZoo = new ArrayList<>();
        for (Zoologico zoologico : copiaZoo) {
            if (profesional.equals(zoologico.profesionales) || profesional.equals(zoologico.profesionales)) {
                profesionalZoo.add(zoologico);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Nombre.");
        System.out.println("3. Siglas.");
        System.out.println("4. Ciudad.");
        System.out.println("5. Bioma.");
        System.out.println("6. Profesional.");
        System.out.println("7. ZooAmigo.");
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
                Collections.sort(copiaZoo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZoo) {
                    System.out.println(zoologico);
                }
            } else if (option2.equals("2")) {
                System.out.println("Descendente");
            } else {
                System.out.println("Opcion incorrecta");
            }
        } else if (option.equals("2")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")) {
                System.out.println("Ascendente");
                Collections.sort(profesionalZoo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : profesionalZoo) {
                    System.out.println(zoologico);
                }
            } else if (option2.equals("2")) {
                System.out.println("Descendente");
                Collections.sort(profesionalZoo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : profesionalZoo) {
                    System.out.println(zoologico);
                }
            } else {
                System.out.println("Opcion incorrecta");
            }
        } else if (option.equals("3")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(profesionalZoo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : profesionalZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(profesionalZoo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : profesionalZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("4")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(profesionalZoo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : profesionalZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(profesionalZoo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : profesionalZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("5")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(profesionalZoo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : profesionalZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(profesionalZoo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : profesionalZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("6")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(profesionalZoo, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : profesionalZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(profesionalZoo, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : profesionalZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("7")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(profesionalZoo, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : profesionalZoo) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(profesionalZoo, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : profesionalZoo) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else {
            return;
        }

    }
    public static void zooAmigoZoologico(int opcionValor, String zooAmigo){
        ArrayList<Zoologico> copiaZoo = new ArrayList<>(copiaHabitat);
        ArrayList<Zoologico> zooAmigoZoo = new ArrayList<>();
        for (Zoologico zoologico : copiaZoo) {
            if (zooAmigo.equals(zoologico.siglas) || zooAmigo.equals(zoologico.zooAmigos)) {
                zooAmigoZoo.add(zoologico);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. Nombre.");
        System.out.println("3. Siglas.");
        System.out.println("4. Ciudad.");
        System.out.println("5. Bioma.");
        System.out.println("6. Profesional.");
        System.out.println("7. ZooAmigo.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaZoo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZoo) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(zooAmigoZoo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : zooAmigoZoo) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(zooAmigoZoo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : zooAmigoZoo) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(zooAmigoZoo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : zooAmigoZoo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(zooAmigoZoo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : zooAmigoZoo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(zooAmigoZoo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : zooAmigoZoo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(zooAmigoZoo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : zooAmigoZoo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(zooAmigoZoo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : zooAmigoZoo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(zooAmigoZoo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : zooAmigoZoo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(zooAmigoZoo, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : zooAmigoZoo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(zooAmigoZoo, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : zooAmigoZoo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("7")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(zooAmigoZoo, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : zooAmigoZoo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(zooAmigoZoo, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : zooAmigoZoo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
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
            System.out.println("5. Zoologico.");
            System.out.println("6. Profesional.");
            System.out.println("7. Habitat.");
            System.out.println("8. Mostrar todos los biomas.");
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
                    }else if(option1.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus= input.nextInt();
                        idBioma(1, idBus, 0);
                        break;
                    }else if(option1.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idBioma(2, idBus, 0);
                        break;
                    }else if(option1.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idBioma(3, idBus, 0);
                        break;
                    }
                    else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int idBusMin= input.nextInt();
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
                    }else if(option2.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        double temperaturaBus= input.nextDouble();
                        temperaturaBioma(1, temperaturaBus, 0);
                        break;
                    }else if(option2.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        double temperaturaBus = input.nextDouble();
                        temperaturaBioma(2, temperaturaBus, 0);
                        break;
                    }else if(option2.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        double temperaturaBus = input.nextDouble();
                        temperaturaBioma(3, temperaturaBus, 0);
                        break;
                    }
                    else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        double temperaturaBusMin= input.nextDouble();
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
                    }else if(option3.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String humedadBus= input.next();
                        humedadBioma(1, humedadBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String humedadBus= input.next();
                        humedadBioma(2, humedadBus);
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
                    }else if(option4.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tipoBus= input.next();
                        tipoBioma(1, tipoBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tipoBus= input.next();
                        tipoBioma(2, tipoBus);
                        break;
                    }
                    break;
                case "5":
                    String option5;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option5 = input.next();
                    if (!option5.equals("1") && !option5.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    }else if(option5.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String zooBus = input.next();
                        zoologicoBioma(1,zooBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String zooBus= input.next();
                        zoologicoBioma(2,zooBus);
                        break;
                    }
                    break;
                case "6":
                    String option6;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option6 = input.next();
                    if (!option6.equals("1") && !option6.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    }else if(option6.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String profesionalBus= input.next();
                        profesionalBioma(1, profesionalBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String profesionalBus= input.next();
                        profesionalBioma(2, profesionalBus);
                        break;
                    }
                    break;
                case "7":
                    String option7;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option7 = input.next();
                    if (!option7.equals("1") && !option7.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    }else if(option7.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String habitatBus= input.next();
                        habitatBioma(1, habitatBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String habitatBus= input.next();
                        habitatBioma(2, habitatBus);
                        break;
                    }
                    break;
                case "8":
                    String option8;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
                    System.out.println("1. ID.");
                    System.out.println("2. Temperatura.");
                    System.out.println("3. Humedad.");
                    System.out.println("4. Tipo.");
                    System.out.println("5. Zoologico.");
                    System.out.println("6. Profesional.");
                    System.out.println("7. Habitat.");
                    System.out.println("0. Regresar al menu anterior.");
                    option8 = input.next();
                    System.out.println();
                    break;
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void idBioma(int opcionValor, int idBusMin, int idBusMax){
        ArrayList<Bioma> copiaBioma = new ArrayList<>();
        if (opcionValor==1){
            for(Bioma bioma : copiaBioma){
                if(idBusMin == 0){
                    System.out.println(bioma);
                }
            }return;

        }else if(opcionValor==2){
            for (Bioma bioma: copiaBioma){
                if(idBusMin==0){
                    System.out.println( bioma);
                }
            }return;

        }
    }
    public static void temperaturaBioma(int opcionValor, double temperaturaBusMin, double temperaturaBusMax) {
        ArrayList<Bioma> copiaBioma = new ArrayList<>();
        ArrayList<Bioma> temperaturaBioma = new ArrayList<>();
        for (Bioma bioma : copiaBioma) {
            if (temperaturaBusMin <= bioma.temperatura || temperaturaBusMax >= bioma.temperatura || temperaturaBusMin == bioma.temperatura || temperaturaBusMax == bioma.temperatura ) {
                temperaturaBioma.add(bioma);
            }
        }
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Temperatura.");
        System.out.println("3. Humedad.");
        System.out.println("4. Tipo.");
        System.out.println("5. Zoologico.");
        System.out.println("6. Profesional.");
        System.out.println("7. Habitat.");
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
                Collections.sort(copiaBioma, comparadoresBioma[0]);
                for (Bioma bioma : copiaBioma) {
                    System.out.println(bioma);
                }
            } else if (option2.equals("2")) {
                System.out.println("Descendente");
            } else {
                System.out.println("Opcion incorrecta");
            }
        } else if (option.equals("2")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")) {
                System.out.println("Ascendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresBioma[1]));
                for (Bioma bioma : copiaBioma) {
                    System.out.println(bioma);
                }
            } else if (option2.equals("2")) {
                System.out.println("Descendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresBioma[1]));
                for (Bioma bioma : copiaBioma) {
                    System.out.println(bioma);
                }
            } else {
                System.out.println("Opcion incorrecta");
            }
        } else if (option.equals("3")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresBioma[2]));
                    for (Bioma bioma : copiaBioma) {
                        System.out.println(bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresBioma[2]));
                    for (Bioma bioma : copiaBioma) {
                        System.out.println(bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        } else if (option.equals("4")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresBioma[3]));
                    for (Bioma bioma : copiaBioma) {
                        System.out.println(bioma);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresBioma[3]));
                    for (Bioma bioma : copiaBioma) {
                        System.out.println(bioma);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        }
    }
    public static void humedadBioma(int opcionValor, String humedadBus){
        ArrayList<Zoologico> copiaBioma = new ArrayList<>(copiaHabitat);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Temperatura.");
        System.out.println("3. Humedad.");
        System.out.println("4. Tipo.");
        System.out.println("5. Zoologico.");
        System.out.println("6. Profesional.");
        System.out.println("7. Habitat.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("7")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void tipoBioma(int opcionValor, String tipoBus){
        ArrayList<Zoologico> copiaBioma = new ArrayList<>(copiaHabitat);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Temperatura.");
        System.out.println("3. Humedad.");
        System.out.println("4. Tipo.");
        System.out.println("5. Zoologico.");
        System.out.println("6. Profesional.");
        System.out.println("7. Habitat.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("7")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void zoologicoBioma(int opcionValor, String zoologicoBus){
        ArrayList<Zoologico> copiaBioma = new ArrayList<>(copiaHabitat);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Temperatura.");
        System.out.println("3. Humedad.");
        System.out.println("4. Tipo.");
        System.out.println("5. Zoologico.");
        System.out.println("6. Profesional.");
        System.out.println("7. Habitat.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("7")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void profesionalBioma(int opcionValor, String profesionalBus){
        ArrayList<Zoologico> copiaBioma = new ArrayList<>(copiaHabitat);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Temperatura.");
        System.out.println("3. Humedad.");
        System.out.println("4. Tipo.");
        System.out.println("5. Zoologico.");
        System.out.println("6. Profesional.");
        System.out.println("7. Habitat.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("7")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void habitatBioma(int opcionValor, String habitatBus){
        ArrayList<Zoologico> copiaBioma = new ArrayList<>(copiaHabitat);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Temperatura.");
        System.out.println("3. Humedad.");
        System.out.println("4. Tipo.");
        System.out.println("5. Zoologico.");
        System.out.println("6. Profesional.");
        System.out.println("7. Habitat.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaBioma) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("7")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaBioma, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaBioma) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
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
            System.out.println("5. Animal.");
            System.out.println("6. Bioma.");
            System.out.println("7. Tecnico.");
            System.out.println("8. Mostrar todos los habitats.");
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
                    }else if(option1.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus= input.nextInt();
                        idHabitat(1, idBus, 0);
                        break;
                    }else if(option1.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idHabitat(2, idBus, 0);
                        break;
                    }else if(option1.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idHabitat(3, idBus, 0);
                        break;
                    }
                    else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int idBusMin= input.nextInt();
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
                    }else if(option2.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tipoSueloBus= input.next();
                        tipoSueloHabitat(1, tipoSueloBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tipoSueloBus= input.next();
                        tipoSueloHabitat(2, tipoSueloBus);
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
                    }else if(option3.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String vegetacionBus= input.next();
                        vegetacionHabitat(1, vegetacionBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String vegetacionBus= input.next();
                        vegetacionHabitat(2, vegetacionBus);
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
                    }else if(option4.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tipoJaulaBus= input.next();
                        tipoSueloHabitat(1, tipoJaulaBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tipoJaulaBus= input.next();
                        tipoJaulaHabitat(2, tipoJaulaBus);
                        break;
                    }
                    break;
                case "5":
                    String option5;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option5 = input.next();
                    if (!option5.equals("1") && !option5.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    }else if(option5.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String animalBus = input.next();
                        animalHabitat(1, animalBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String animalBus= input.next();
                        animalHabitat(2,animalBus);
                        break;
                    }
                    break;
                case "6":
                    String option6;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option6 = input.next();
                    if (!option6.equals("1") && !option6.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    }else if(option6.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String biomaBus= input.next();
                        biomaHabitat(1, biomaBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String biomaBus= input.next();
                        biomaHabitat(2, biomaBus);
                        break;
                    }
                    break;
                case "7":
                    String option7;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option7 = input.next();
                    if (!option7.equals("1") && !option7.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    }else if(option7.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tecnicoBus= input.next();
                        tecnicoHabitat(1, tecnicoBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String tecnicoBus= input.next();
                        tecnicoHabitat(2, tecnicoBus);
                        break;
                    }
                    break;
                case "8":
                    String option8;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
                    System.out.println("1. ID.");
                    System.out.println("2. Tipo de suelo.");
                    System.out.println("3. Vegetacion.");
                    System.out.println("4. Tipo de jaula.");
                    System.out.println("5. Animal.");
                    System.out.println("6. Bioma.");
                    System.out.println("7. Tecnico.");
                    System.out.println("0. Regresar al menu anterior.");
                    option8 = input.next();
                    System.out.println();
                    break;
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void idHabitat(int opcionValor, int idBusMin, int idBusMax){
        ArrayList<Zoologico> copiaHabitat = new ArrayList<>(SistemaZoologico.copiaHabitat);
        if (opcionValor==1){
            for(Zoologico zoologico : copiaHabitat){
                if(idBusMin==0){
                        System.out.println(zoologico);
                }
            }return;

        }else if(opcionValor==2){
            for (Zoologico zoologico: copiaHabitat){
                if(idBusMin==0){
                        System.out.println( zoologico);
                }
            }return;

        }
    }
    public static void tipoSueloHabitat(int opcionValor, String tipoSueloBus){
        ArrayList<Zoologico> copiaHabitat = new ArrayList<>(SistemaZoologico.copiaHabitat);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Tipo de suelo.");
        System.out.println("3. Vegetacion.");
        System.out.println("4. Tipo de jaula.");
        System.out.println("5. Animal.");
        System.out.println("6. Bioma.");
        System.out.println("7. Tecnico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("7")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void vegetacionHabitat(int opcionValor, String vegetacionBus){
        ArrayList<Zoologico> copiaHabitat = new ArrayList<>(SistemaZoologico.copiaHabitat);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Tipo de suelo.");
        System.out.println("3. Vegetacion.");
        System.out.println("4. Tipo de jaula.");
        System.out.println("5. Animal.");
        System.out.println("6. Bioma.");
        System.out.println("7. Tecnico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(SistemaZoologico.copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : SistemaZoologico.copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(SistemaZoologico.copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : SistemaZoologico.copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(SistemaZoologico.copiaHabitat, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : SistemaZoologico.copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(SistemaZoologico.copiaHabitat, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : SistemaZoologico.copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(SistemaZoologico.copiaHabitat, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : SistemaZoologico.copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(SistemaZoologico.copiaHabitat, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : SistemaZoologico.copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(SistemaZoologico.copiaHabitat, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : SistemaZoologico.copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(SistemaZoologico.copiaHabitat, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : SistemaZoologico.copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(SistemaZoologico.copiaHabitat, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : SistemaZoologico.copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(SistemaZoologico.copiaHabitat, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : SistemaZoologico.copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("7")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(SistemaZoologico.copiaHabitat, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : SistemaZoologico.copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(SistemaZoologico.copiaHabitat, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : SistemaZoologico.copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void tipoJaulaHabitat(int opcionValor, String tipoJaulaBus){
        ArrayList<Zoologico> copiaHabitat = new ArrayList<>(SistemaZoologico.copiaHabitat);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Tipo de suelo.");
        System.out.println("3. Vegetacion.");
        System.out.println("4. Tipo de jaula.");
        System.out.println("5. Animal.");
        System.out.println("6. Bioma.");
        System.out.println("7. Tecnico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("7")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void animalHabitat(int opcionValor, String animalBus){
        ArrayList<Zoologico> copiaHabitat = new ArrayList<>(SistemaZoologico.copiaHabitat);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Tipo de suelo.");
        System.out.println("3. Vegetacion.");
        System.out.println("4. Tipo de jaula.");
        System.out.println("5. Animal.");
        System.out.println("6. Bioma.");
        System.out.println("7. Tecnico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("7")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void biomaHabitat(int opcionValor, String habitatBus){
        ArrayList<Zoologico> copiaHabitat = new ArrayList<>(SistemaZoologico.copiaHabitat);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Tipo de suelo.");
        System.out.println("3. Vegetacion.");
        System.out.println("4. Tipo de jaula.");
        System.out.println("5. Animal.");
        System.out.println("6. Bioma.");
        System.out.println("7. Tecnico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("7")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void tecnicoHabitat(int opcionValor, String tecnicoBus){
        ArrayList<Zoologico> copiaHabitat = new ArrayList<>(SistemaZoologico.copiaHabitat);
        String option;
        String option2;
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Tipo de suelo.");
        System.out.println("3. Vegetacion.");
        System.out.println("4. Tipo de jaula.");
        System.out.println("5. Animal.");
        System.out.println("6. Bioma.");
        System.out.println("7. Tecnico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaHabitat) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("7")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaHabitat, Collections.reverseOrder(comparadoresZoologico[6]));
                    for (Zoologico zoologico : copiaHabitat) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
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
            System.out.println("5. Habitat.");
            System.out.println("6. Zooamigo.");
            System.out.println("7. Mostrar todos los habitats.");
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
                    }else if(option1.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus= input.nextInt();
                        idAnimal(1, idBus, 0);
                        break;
                    }else if(option1.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idAnimal(2, idBus, 0);
                        break;
                    }else if(option1.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int idBus = input.nextInt();
                        idAnimal(3, idBus, 0);
                        break;
                    }
                    else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int idBusMin= input.nextInt();
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
                    }else if(option2.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String especieBus= input.next();
                        especieAnimal(1, especieBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String especieBus= input.next();
                        especieAnimal(2, especieBus);
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
                    }else if(option3.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int nivelAgresividadBus= input.nextInt();
                        nivelAgresividadAnimal(1, nivelAgresividadBus, 0);
                        break;
                    }else if(option3.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int nivelAgresividadBus = input.nextInt();
                        nivelAgresividadAnimal(2, nivelAgresividadBus, 0);
                        break;
                    }else if(option3.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int nivelAgresividadBus = input.nextInt();
                        nivelAgresividadAnimal(3, nivelAgresividadBus, 0);
                        break;
                    }
                    else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int nivelAgresividadBusMin= input.nextInt();
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
                    }else if(option4.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String alimentacionBus= input.next();
                        alimentacionAnimal(1, alimentacionBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String alimentacionBus= input.next();
                        alimentacionAnimal(2, alimentacionBus);
                        break;
                    }
                    break;
                case "5":
                    String option5;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option5 = input.next();
                    if (!option5.equals("1") && !option5.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    }else if(option5.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String habitatBus = input.next();
                        habitatAnimal(1,habitatBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String habitatBus= input.next();
                        habitatAnimal(2,habitatBus);
                        break;
                    }
                    break;
                case "6":
                    String option6;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option6 = input.next();
                    if (!option6.equals("1") && !option6.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    }else if(option6.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String zooAmigoBus= input.next();
                        zooAmigoAnimal(1, zooAmigoBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String zooAmigoBus= input.next();
                        zooAmigoAnimal(2, zooAmigoBus);
                        break;
                    }
                    break;
                case "7":
                    String option7;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
                    System.out.println("1. ID.");
                    System.out.println("2. Especie.");
                    System.out.println("3. Nivel de agresividad.");
                    System.out.println("4. Alimentacion.");
                    System.out.println("5. Habitat.");
                    System.out.println("6. Zooamigo.");
                    System.out.println("0. Regresar al menu anterior.");
                    option7 = input.next();
                    System.out.println();
                    break;
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void idAnimal(int opcionValor, int idAnimalMin, int idBusMax){
        ArrayList<Zoologico> copiaAnimal = new ArrayList<>(copiaHabitat);
        if (opcionValor==1){
            for(Zoologico zoologico : copiaAnimal){
                if(idAnimalMin==(0)){
                        System.out.println(zoologico);
                }
            }return;

        }else if(opcionValor==2){
            for (Zoologico zoologico: copiaAnimal){
                if(idAnimalMin==(0)){
                        System.out.println( zoologico);
                }
            }return;

        }
    }
    public static void especieAnimal(int opcionValor, String especieBus){
        String option;
        String option2;
        ArrayList<Zoologico> copiaAnimal = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Especie.");
        System.out.println("3. Nivel de agresividad.");
        System.out.println("4. Alimentacion.");
        System.out.println("5. Habitat.");
        System.out.println("6. Zooamigo.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void nivelAgresividadAnimal(int opcionValor, int nivelAgresividadBusMin, int nivelAgresividadMax){
        String option;
        String option2;
        ArrayList<Zoologico> copiaAnimal = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Especie.");
        System.out.println("3. Nivel de agresividad.");
        System.out.println("4. Alimentacion.");
        System.out.println("5. Habitat.");
        System.out.println("6. Zooamigo.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void alimentacionAnimal(int opcionValor, String alimentacionBus){
        String option;
        String option2;
        ArrayList<Zoologico> copiaAnimal = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Especie.");
        System.out.println("3. Nivel de agresividad.");
        System.out.println("4. Alimentacion.");
        System.out.println("5. Habitat.");
        System.out.println("6. Zooamigo.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void habitatAnimal(int opcionValor, String habitatBus){
        String option;
        String option2;
        ArrayList<Zoologico> copiaAnimal = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Especie.");
        System.out.println("3. Nivel de agresividad.");
        System.out.println("4. Alimentacion.");
        System.out.println("5. Habitat.");
        System.out.println("6. Zooamigo.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void zooAmigoAnimal(int opcionValor, String zooAmigoAnimal){
        String option;
        String option2;
        ArrayList<Zoologico> copiaAnimal = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. ID.");
        System.out.println("2. Especie.");
        System.out.println("3. Nivel de agresividad.");
        System.out.println("4. Alimentacion.");
        System.out.println("5. Habitat.");
        System.out.println("6. Zooamigo.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaAnimal) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")) {
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
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
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                } else if (option2.equals("2")) {
                    System.out.println("Descendente");
                    Collections.sort(copiaAnimal, Collections.reverseOrder(comparadoresZoologico[5]));
                    for (Zoologico zoologico : copiaAnimal) {
                        System.out.println(zoologico);
                    }
                } else {
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
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
            System.out.println("5. Habitat.");
            System.out.println("6. Mostrar todos los zoologicos.");
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
                    }else if(option1.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus= input.nextInt();
                        cedulaTecnico(1, cedulaBus, 0);
                        break;
                    }else if(option1.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaTecnico(2, cedulaBus, 0);
                        break;
                    }else if(option1.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaTecnico(3, cedulaBus, 0);
                        break;
                    }
                    else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int cedulaBusMin= input.nextInt();
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
                    }else if(option2.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String areaBus= input.next();
                        areaTecnico(1, areaBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String areaBus= input.next();
                        areaTecnico(2, areaBus);
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
                    }else if(option3.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaInicioBus= input.next();
                        horaInicioTecnico(1, horaInicioBus, "0");
                        break;
                    }else if(option3.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaInicioBus = input.next();
                        horaInicioTecnico(2, horaInicioBus, "0");
                        break;
                    }else if(option3.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaInicioBus = input.next();
                        horaInicioTecnico(3, horaInicioBus, "0");
                        break;
                    }
                    else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        String horaInicioBusMin= input.next();
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
                    }else if(option4.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaSalidaBus= input.next();
                        horaSalidaTecnico(1, horaSalidaBus,"1");
                        break;
                    }else if(option4.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaSalidaBus = input.next();
                        horaSalidaTecnico(2, horaSalidaBus, "0");
                        break;
                    }else if(option4.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaSalidaBus = input.next();
                        horaSalidaTecnico(3, horaSalidaBus, "0");
                        break;
                    }
                    else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        String horaSalidaBusMin= input.next();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        String horaSalidaBusMax = input.next();
                        horaSalidaTecnico(4, horaSalidaBusMin, horaSalidaBusMax);
                        break;
                    }
                    break;
                case "5":
                    String option5;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option5 = input.next();
                    if (!option5.equals("1") && !option5.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    }else if(option5.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String habitatBus = input.next();
                        habitatTecnico(1,habitatBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String habitatBus= input.next();
                        habitatTecnico(2,habitatBus);
                        break;
                    }
                    break;
                case "6":
                    String option6;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
                    System.out.println("1. Cedula.");
                    System.out.println("2. Ares.");
                    System.out.println("3. Hora inicio.");
                    System.out.println("4. Hora salida.");
                    System.out.println("5. Habitat.");
                    System.out.println("6. Mostrar todos los zoologicos.");
                    option6 = input.next();
                    System.out.println();
                    break;
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void cedulaTecnico(int opcionValor, int cedulaBusMin, int cedulaBusMax){
        ArrayList<Zoologico> copiaTecnico = new ArrayList<>(copiaHabitat);
        if (opcionValor==1){
            for(Zoologico zoologico : copiaTecnico){
                if(cedulaBusMin==0){
                        System.out.println(zoologico);
                }
            }return;

        }else if(opcionValor==2){
            for (Zoologico zoologico: copiaTecnico){
                if(cedulaBusMin==(0)){
                        System.out.println( zoologico);
                }
            }return;

        }
    }
    public static void areaTecnico(int opcionValor, String areaBus){
        String option;
        String option2;
        ArrayList<Zoologico> copiaTecnico = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Ares.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("5. Habitat.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaTecnico) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaTecnico) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaTecnico) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void horaInicioTecnico(int opcionValor, String horaInicioBusMin, String horaInicioBusMax){
        String option;
        String option2;
        ArrayList<Zoologico> copiaTecnico = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Ares.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("5. Habitat.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaTecnico) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaTecnico) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaTecnico) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void horaSalidaTecnico(int opcionValor, String horaSalidaBusMin, String horaInicioBusMax){
        String option;
        String option2;
        ArrayList<Zoologico> copiaTecnico = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Ares.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("5. Habitat.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaTecnico) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaTecnico) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaTecnico) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void habitatTecnico(int opcionValor, String habitatBus){
        String option;
        String option2;
        ArrayList<Zoologico> copiaTecnico = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Ares.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("5. Habitat.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaTecnico) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaTecnico) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaTecnico) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaTecnico, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaTecnico) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
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
            System.out.println("5. Bioma.");
            System.out.println("6. Zoologico.");
            System.out.println("7. Mostrar todos los profesionales.");
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
                    }else if(option1.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus= input.nextInt();
                        cedulaProfesional(1, cedulaBus, 0);
                        break;
                    }else if(option1.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaProfesional(2, cedulaBus, 0);
                        break;
                    }else if(option1.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaProfesional(3, cedulaBus, 0);
                        break;
                    }
                    else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int cedulaBusMin= input.nextInt();
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
                    }else if(option2.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String areaBus= input.next();
                        areaProfesional(1, areaBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String areaBus= input.next();
                        areaProfesional(2, areaBus);
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
                    }else if(option3.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaInicioBus= input.next();
                        horaInicioProfesional(1, horaInicioBus, "0");
                        break;
                    }else if(option3.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaInicioBus = input.next();
                        horaInicioProfesional(2, horaInicioBus, "0");
                        break;
                    }else if(option3.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaInicioBus = input.next();
                        horaInicioProfesional(3, horaInicioBus, "0");
                        break;
                    }
                    else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        String horaInicioBusMin= input.next();
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
                    }else if(option4.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaSalidaBus= input.next();
                        horaSalidaProfesional(1, horaSalidaBus, "0");
                        break;
                    }else if(option4.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaSalidaBus = input.next();
                        horaSalidaProfesional(2, horaSalidaBus, "0");
                        break;
                    }else if(option4.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String horaSalidaBus = input.next();
                        horaSalidaProfesional(3, horaSalidaBus, "0");
                        break;
                    }
                    else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        String horaSalidaBusMin= input.next();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        String horaSalidaBusMax = input.next();
                        horaSalidaProfesional(4, horaSalidaBusMin, horaSalidaBusMax);
                        break;
                    }
                    break;
                case "5":
                    String option5;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option5 = input.next();
                    if (!option5.equals("1") && !option5.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    }else if(option5.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String biomaBus = input.next();
                        biomaProfesional(1,biomaBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String biomaBus= input.next();
                        biomaProfesional(2,biomaBus);
                        break;
                    }
                    break;
                case "6":
                    String option6;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option6 = input.next();
                    if (!option6.equals("1") && !option6.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    }else if(option6.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String profesionalBus= input.next();
                        zoologicoProfesional(1, profesionalBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String profesionalBus= input.next();
                        zoologicoProfesional(2, profesionalBus);
                        break;
                    }
                    break;
                case "7":
                    String option7;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
                    System.out.println("1. Cedula.");
                    System.out.println("2. Area.");
                    System.out.println("3. Hora inicio.");
                    System.out.println("4. Hora salida.");
                    System.out.println("5. Bioma.");
                    System.out.println("6. Zoologico.");
                    System.out.println("0. Regresar al menu anterior.");
                    option7 = input.next();
                    System.out.println();
                    break;
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void cedulaProfesional(int opcionValor, int cedulaBusMin, int cedulaBusMax){
        ArrayList<Zoologico> copiaProfesional = new ArrayList<>(copiaHabitat);
        if (opcionValor==1){
            for(Zoologico zoologico : copiaProfesional){
                if(cedulaBusMin==0){
                        System.out.println(zoologico);
                }
            }return;

        }else if(opcionValor==2){
            for (Zoologico zoologico: copiaProfesional){
                if(cedulaBusMin==(0)){
                        System.out.println( zoologico);
                }
            }return;

        }
    }
    public static void areaProfesional(int opcionValor, String areaBus){
        String option;
        String option2;
        ArrayList<Zoologico> copiaProfesional = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("5. Bioma.");
        System.out.println("6. Zoologico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void horaInicioProfesional(int opcionValor, String horaInicioBusMin, String horaInicioBusMax){
        String option;
        String option2;
        ArrayList<Zoologico> copiaProfesional = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("5. Bioma.");
        System.out.println("6. Zoologico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void horaSalidaProfesional(int opcionValor, String horaSalidaBusMin, String horaInicioBusMax){
        String option;
        String option2;
        ArrayList<Zoologico> copiaProfesional = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("5. Bioma.");
        System.out.println("6. Zoologico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void biomaProfesional(int opcionValor, String biomaBus){
        String option;
        String option2;
        ArrayList<Zoologico> copiaProfesional = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("5. Bioma.");
        System.out.println("6. Zoologico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void zoologicoProfesional(int opcionValor, String zoologicoBus){
        String option;
        String option2;
        ArrayList<Zoologico> copiaProfesional = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Area.");
        System.out.println("3. Hora inicio.");
        System.out.println("4. Hora salida.");
        System.out.println("5. Bioma.");
        System.out.println("6. Zoologico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaProfesional) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("6")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaProfesional, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaProfesional) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
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
            System.out.println("4. Animal.");
            System.out.println("5. Zoologico.");
            System.out.println("6. Mostrar todos los ZooAmigos");
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
                    }else if(option1.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus= input.nextInt();
                        cedulaZooAmigo(1, cedulaBus, 0);
                        break;
                    }else if(option1.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaZooAmigo(2, cedulaBus, 0);
                        break;
                    }else if(option1.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        int cedulaBus = input.nextInt();
                        cedulaZooAmigo(3, cedulaBus, 0);
                        break;
                    }
                    else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        int cedulaBusMin= input.nextInt();
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
                    }else if(option2.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String nombreBus= input.next();
                        nombreZooAmigo(1, nombreBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String nombreBus= input.next();
                        nombreZooAmigo(2, nombreBus);
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
                    }else if(option3.equals("1")){
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String telefonoBus= input.next();
                        telefonoZooAmigo(1, telefonoBus, "0");
                        break;
                    }else if(option3.equals("2")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String telefonoBus = input.next();
                        telefonoZooAmigo(2, telefonoBus, "0");
                        break;
                    }else if(option3.equals("3")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String telefonoBus = input.next();
                        telefonoZooAmigo(3, telefonoBus, "0");
                        break;
                    }
                    else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor minimo que desea buscar: \n");
                        String telefonoBusMin= input.next();
                        System.out.println("Por favor, ingrese el valor maximo que desea buscar: \n");
                        String telefonoBusMax = input.next();
                        telefonoZooAmigo(4, telefonoBusMin, telefonoBusMax);
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
                    }else if(option4.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String animalBus= input.next();
                        animalZooAmigo(1, animalBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String animalBus= input.next();
                        animalZooAmigo(2, animalBus);
                        break;
                    }
                    break;
                case "5":
                    String option5;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, selecione el valor que desea buscar: \n");
                    System.out.println("1. Valor exacto.");
                    System.out.println("2. Valor sin considerar mayusculas.");
                    option5 = input.next();
                    if (!option5.equals("1") && !option5.equals("2")) {
                        System.out.println("Opcion incorrecta");
                    }else if(option5.equals("1")) {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String zoologicoBus = input.next();
                        zoologicoZooAmigo(1,zoologicoBus);
                        break;
                    }else{
                        System.out.println("--------------------------------------------------");
                        System.out.println("Por favor, ingrese el valor que desea buscar: \n");
                        String zoologicoBus= input.next();
                        zoologicoZooAmigo(2,zoologicoBus);
                        break;
                    }
                    break;
                case "6":
                    String option6;
                    System.out.println("--------------------------------------------------");
                    System.out.println("Por favor, seleccione el atributo por el que desea ordenar: \n");
                    System.out.println("1. Cedula.");
                    System.out.println("2. Nombre.");
                    System.out.println("3. Telefono.");
                    System.out.println("4. Animal.");
                    System.out.println("5. Zoologico.");
                    System.out.println("0. Regresar al menu anterior.");
                    option6 = input.next();
                    System.out.println();
                    break;
                case "0":
                    break label;
            }
            System.out.println("--------------------------------------------------");
        }
    }
    public static void cedulaZooAmigo(int opcionValor, int cedulaBusMin, int cedulaBusMax){
        ArrayList<Zoologico> copiaZooAmigo = new ArrayList<>(copiaHabitat);
        if (opcionValor==1){
            for(Zoologico zoologico : copiaZooAmigo){
                if(cedulaBusMin==(0)){
                        System.out.println(zoologico);
                }
            }return;

        }else if(opcionValor==2){
            for (Zoologico zoologico: copiaZooAmigo){
                if(cedulaBusMin==0){
                        System.out.println( zoologico);
                }
            }return;

        }
    }
    public static void nombreZooAmigo(int opcionValor, String nombreBus){
        String option;
        String option2;
        ArrayList<Zoologico> copiaZooAmigo = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Nombre.");
        System.out.println("3. Telefono.");
        System.out.println("4. Animal.");
        System.out.println("5. Zoologico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZooAmigo) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZooAmigo) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZooAmigo) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void telefonoZooAmigo(int opcionValor, String telefonoBusMin, String telefonoBusMax){
        String option;
        String option2;
        ArrayList<Zoologico> copiaZooAmigo = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Nombre.");
        System.out.println("3. Telefono.");
        System.out.println("4. Animal.");
        System.out.println("5. Zoologico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZooAmigo) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZooAmigo) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZooAmigo) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void animalZooAmigo(int opcionValor, String animalBus){
        String option;
        String option2;
        ArrayList<Zoologico> copiaZooAmigo = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Nombre.");
        System.out.println("3. Telefono.");
        System.out.println("4. Animal.");
        System.out.println("5. Zoologico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZooAmigo) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZooAmigo) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZooAmigo) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }
    public static void zoologicoZooAmigo(int opcionValor, String zoologicoBus){
        String option;
        String option2;
        ArrayList<Zoologico> copiaZooAmigo = new ArrayList<>(copiaHabitat);
        System.out.println("--------------------------------------------------");
        System.out.println("Por favor, seleccione el atributo que desea buscar: \n");
        System.out.println("1. Cedula.");
        System.out.println("2. Nombre.");
        System.out.println("3. Telefono.");
        System.out.println("4. Animal.");
        System.out.println("5. Zoologico.");
        System.out.println("0. Regresar al menu anterior.");
        option = input.next();
        System.out.println();
        if (option.equals("1")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZooAmigo) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("2")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option2.equals("1")){
                System.out.println("Ascendente");
                Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZooAmigo) {
                    System.out.println(zoologico);
                }
            }else if (option2.equals("2")){
                System.out.println("Descendente");
                Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[0]));
                for (Zoologico zoologico : copiaZooAmigo) {
                    System.out.println(zoologico);
                }
            }else{
                System.out.println("Opcion incorrecta");
            }
        }else if (option.equals("3")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[2]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("4")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[3]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else if (option.equals("5")){
            System.out.println("--------------------------------------------------");
            System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
            System.out.println("1. Ascendente.");
            System.out.println("2. Descendente.");
            option2 = input.next();
            System.out.println();
            if (option.equals("1")){
                System.out.println("--------------------------------------------------");
                System.out.println("Por favor, seleccione la forma en la que lo desea ordenar: \n");
                System.out.println("1. Ascendente.");
                System.out.println("2. Descendente.");
                option2 = input.next();
                System.out.println();
                if (option2.equals("1")){
                    System.out.println("Ascendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else if (option2.equals("2")){
                    System.out.println("Descendente");
                    Collections.sort(copiaZooAmigo, Collections.reverseOrder(comparadoresZoologico[4]));
                    for (Zoologico zoologico : copiaZooAmigo) {
                        System.out.println(zoologico);
                    }
                }else{
                    System.out.println("Opcion incorrecta");
                }
            }
        }else{
            return;
        }
    }

    public static void mostrarBusqueda() {
        ArrayList<Zoologico> copiaZoo = new ArrayList<>(copiaHabitat);
        if (copiaZoo.size() == 0){
            System.out.println("No hay zoologicos registrados");
        }
        System.out.println("Lista de zoologicos: ");
        for (Zoologico zoologico : copiaZoo){
            System.out.println(zoologico);
        }
    }


}
