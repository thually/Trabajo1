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

    public static void main(String[] args) {

        cargarSistema();
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
            label2 : switch (opcion){
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
                    guardar();
                    break;
                case "0":
                    while (true){
                        System.out.println("MENSAJE: si sale se perderan los cambios sin guardar");
                        System.out.print("Esta seguro que desea salir?: [Y/N] ");
                        String option = input.next();
                        if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")){
                            switch (option.toUpperCase()){
                                case "Y":
                                    break label;
                                case "N":
                                    break label2;
                            }
                            break;
                        }
                        else {
                            System.out.println("Opcion invalida\n");
                        }
                    }
            }
            System.out.println("----------------------------------");
        }

    }

    private static void cargarSistema() {
        JSONParser jsonParser = new JSONParser();

        File animales = new File("src/database/animales.json");
        if (animales.length() != 0){
            try (FileReader reader = new FileReader("src/database/animales.json")) {
                Object obj = jsonParser.parse(reader);

                JSONArray animalesJava = (JSONArray) obj;
                animalesJava.forEach(ani -> parseAnimalObj( (JSONObject) ani));

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        File biomas = new File("src/database/biomas.json");
        if (biomas.length() != 0){
            try (FileReader reader = new FileReader("src/database/biomas.json")) {
                Object obj = jsonParser.parse(reader);

                JSONArray biomasJava = (JSONArray) obj;
                biomasJava.forEach(bio -> parseBiomaObj( (JSONObject) bio));

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        File habitats = new File("src/database/habitats.json");
        if (habitats.length() != 0){
            try (FileReader reader = new FileReader("src/database/habitats.json")) {
                Object obj = jsonParser.parse(reader);

                JSONArray habitatsJava = (JSONArray) obj;
                habitatsJava.forEach(hab -> parseHabitatObj( (JSONObject) hab));

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        File profesionales = new File("src/database/profesionales.json");
        if (profesionales.length() != 0){
            try (FileReader reader = new FileReader("src/database/profesionales.json")) {
                Object obj = jsonParser.parse(reader);

                JSONArray profesionalesJava = (JSONArray) obj;
                profesionalesJava.forEach(pro -> parseProfesionalesObj( (JSONObject) pro));

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        File tecnicos = new File("src/database/tecnicos.json");
        if (tecnicos.length() != 0){
            try (FileReader reader = new FileReader("src/database/tecnicos.json")) {
                Object obj = jsonParser.parse(reader);

                JSONArray tecnicosJava = (JSONArray) obj;
                tecnicosJava.forEach(tec -> parseTecnicoObj( (JSONObject) tec));

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        File zooAmigo = new File("src/database/zooamigos.json");
        if (zooAmigo.length() != 0){
            try (FileReader reader = new FileReader("src/database/zooamigos.json")) {
                Object obj = jsonParser.parse(reader);

                JSONArray zooAmigosJava = (JSONArray) obj;
                zooAmigosJava.forEach(zooA -> parseZooAmigoObj( (JSONObject) zooA));

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

        File zoologicos = new File("src/database/zoologicos.json");
        if (zoologicos.length() != 0){
            try (FileReader reader = new FileReader("src/database/zoologicos.json")) {
                Object obj = jsonParser.parse(reader);

                JSONArray zoologicosJava = (JSONArray) obj;
                zoologicosJava.forEach(zoo -> parseZoologicoObj( (JSONObject) zoo));

            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
        
        fijarRelaciones();
    }

    private static void fijarRelaciones() {
        for (Zoologico zoo : zoologicos){
            if (!zoo.IDBios.equals("NA")) {
                String[] idBiomas = zoo.IDBios.split(" ");
                for (String id : idBiomas){
                    int ID = Integer.parseInt(id);
                    for (Bioma bio : biomas){
                        if (bio.id == ID){
                            zoo.setBiomas(bio,zoo);
                        }
                    }
                }
            }
            if (!zoo.IDPros.equals("NA")) {
                String[] idProfesionales = zoo.IDPros.split(" ");
                for (String id : idProfesionales){
                    int ID = Integer.parseInt(id);
                    for (Profesional pro : profesionales){
                        if (pro.cedula == ID){
                            zoo.setProfesional(pro,zoo);
                        }
                    }
                }
            }
            if (!zoo.IDZooA.equals("NA")) {
                String[] idZooAmigos = zoo.IDZooA.split(" ");
                for (String id : idZooAmigos){
                    int ID = Integer.parseInt(id);
                    for (ZooAmigo zooA : zooAmigos){
                        if (zooA.cedula == ID){
                            zoo.setZooAmigo(zooA,zoo);
                        }
                    }
                }
            }
        }
        for (Bioma bio : biomas) {
            if (!bio.IDPros.equals("NA")) {
                String[] idProfesionales = bio.IDPros.split(" ");
                for (String id : idProfesionales) {
                    int ID = Integer.parseInt(id);
                    for (Profesional pro : profesionales) {
                        if (pro.cedula == ID) {
                            bio.setProfesional(pro, bio);
                        }
                    }
                }
            }
            if (!bio.IDHab.equals("NA")) {
                String[] idHabitats = bio.IDHab.split(" ");
                for (String id : idHabitats) {
                    int ID = Integer.parseInt(id);
                    for (Habitat hab : habitats) {
                        if (hab.id == ID) {
                            bio.setHabitat(hab, bio);
                        }
                    }
                }
            }
        }
        for (Habitat hab : habitats) {
            if (!hab.IDTecs.equals("NA")) {
                String[] idTecnicos = hab.IDTecs.split(" ");
                for (String id : idTecnicos) {
                    int ID = Integer.parseInt(id);
                    for (Tecnico tec : tecnicos) {
                        if (tec.cedula == ID) {
                            hab.setTecnico(tec, hab);
                        }
                    }
                }
            }
            if (!hab.IDAnis.equals("NA")) {
                String[] idAnimales = hab.IDAnis.split(" ");
                for (String id : idAnimales) {
                    int ID = Integer.parseInt(id);
                    for (Animal ani : animales) {
                        if (ani.id == ID) {
                            hab.setAni(ani, hab);
                        }
                    }
                }
            }
        }
        for (ZooAmigo zooA : zooAmigos){
            if (!zooA.IDAnis.equals("NA")) {
                String[] idAnimales = zooA.IDAnis.split(" ");
                for (String id : idAnimales) {
                    int ID = Integer.parseInt(id);
                    for (Animal ani : animales) {
                        if (ani.id == ID) {
                            zooA.setAnimal(ani, zooA);
                        }
                    }
                }
            }
        }
    }

    public static void parseAnimalObj( JSONObject jsonObject){
        Animal nuevoAni = new Animal(jsonObject);
        animales.add(nuevoAni);
    }
    public static void parseBiomaObj( JSONObject jsonObject){
        Bioma nuevoBio = new Bioma(jsonObject);
        biomas.add(nuevoBio);
    }
    public static void parseHabitatObj( JSONObject jsonObject){
        Habitat nuevoHab = new Habitat(jsonObject);
        habitats.add(nuevoHab);
    }
    public static void parseProfesionalesObj( JSONObject jsonObject){
        Profesional nuevoPro = new Profesional(jsonObject);
        profesionales.add(nuevoPro);
    }
    public static void parseTecnicoObj( JSONObject jsonObject){
        Tecnico nuevoTec = new Tecnico(jsonObject);
        tecnicos.add(nuevoTec);
    }
    public static void parseZooAmigoObj( JSONObject jsonObject){
        ZooAmigo nuevoZooAmi = new ZooAmigo(jsonObject);
        zooAmigos.add(nuevoZooAmi);
    }
    public static void parseZoologicoObj( JSONObject jsonObject){
        Zoologico nuevoZoo = new Zoologico(jsonObject);
        zoologicos.add(nuevoZoo);
    }

    private static void guardar() {
        JSONArray animalesJSON = new JSONArray();
        for (Animal animal : animales) {
            animalesJSON.add(animal.toJSONObj());
        }
        try (FileWriter file = new FileWriter("src/database/animales.json")) {

            file.write(animalesJSON.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray biomasJSON = new JSONArray();
        for (Bioma bioma : biomas) {
            biomasJSON.add(bioma.toJSONObj());
        }
        try (FileWriter file = new FileWriter("src/database/biomas.json")) {

            file.write(biomasJSON.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray habitatsJSON = new JSONArray();
        for (Habitat habitat : habitats) {
            habitatsJSON.add(habitat.toJSONObj());
        }
        try (FileWriter file = new FileWriter("src/database/habitats.json")) {

            file.write(habitatsJSON.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray profesionalesJSON = new JSONArray();
        for (Profesional profesional : profesionales) {
            profesionalesJSON.add(profesional.toJSONObj());
        }
        try (FileWriter file = new FileWriter("src/database/profesionales.json")) {

            file.write(profesionalesJSON.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray tecnicosJSON = new JSONArray();
        for (Tecnico tecnico : tecnicos) {
            tecnicosJSON.add(tecnico.toJSONObj());
        }
        try (FileWriter file = new FileWriter("src/database/tecnicos.json")) {

            file.write(tecnicosJSON.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray zooamigosJSON = new JSONArray();
        for (ZooAmigo zooAmigo : zooAmigos) {
            zooamigosJSON.add(zooAmigo.toJSONObj());
        }
        try (FileWriter file = new FileWriter("src/database/zooamigos.json")) {

            file.write(zooamigosJSON.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray zoologicosJSON = new JSONArray();
        for (Zoologico zoologico : zoologicos) {
            zoologicosJSON.add(zoologico.toJSONObj());
        }
        try (FileWriter file = new FileWriter("src/database/zoologicos.json")) {

            file.write(zoologicosJSON.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Guardado exitosamente");
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
                eliminarZoologico();
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
        Zoologico zoologico = null;
        while (true) {
            String opcion;
            System.out.println("------------------------------------------");
            System.out.println("Estas son las claves unicas del zoologico: \n");
            System.out.println("1. Seleccionar por NIT");
            System.out.println("2. Seleccionar por Siglas\n");
            opcion = input.next();
            if (!opcion.equals("1") && !opcion.equals("2")){
                System.out.println("Opcion invalida");
                return;
            }
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
            } else break;
        }

        input.nextLine();
        System.out.println("\nNIT: "+ zoologico.nit);
        String nuevoNIT = input.nextLine();
        if (!nuevoNIT.isEmpty()) {
            for (Zoologico zoo : zoologicos) {
                if (zoo.nit.replace(".", "").equals(nuevoNIT.replace(".", ""))){
                    System.out.println("ERROR: Ya existe un zoologico registrado con este NIT.");
                    return;
                }
            }
        }

        System.out.println("Nombre: "+ zoologico.nombre);
        String nuevoNombre = input.nextLine();

        System.out.println("Siglas: "+ zoologico.siglas);
        String nuevoSiglas = input.nextLine();
        if (!nuevoSiglas.isEmpty()) {
            for (Zoologico zoo : zoologicos) {
                if (zoo.siglas.equalsIgnoreCase(nuevoSiglas)){
                    System.out.println("ERROR: Ya existe un zoologico registrado con estas siglas.");
                    return;
                }
            }
        }

        System.out.println("Ciudad: "+ zoologico.ciudad);
        String nuevoCiudad = input.nextLine();


        while (true){
            System.out.print("Desea guardar?:[Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")){
                switch (option.toUpperCase()){
                    case "Y":
                        if (nuevoNIT.isEmpty()){}
                        else zoologico.nit = nuevoNIT;
                        if (nuevoNombre.isEmpty()){}
                        else zoologico.nombre = nuevoNombre;
                        if (nuevoSiglas.isEmpty()){}
                        else zoologico.siglas = nuevoSiglas;
                        if (nuevoCiudad.isEmpty()){}
                        else zoologico.ciudad = nuevoCiudad;
                    case "N":
                        break;
                }
                break;
            }
            else {
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

        label: while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. Zoologico - Biomas.");
            System.out.println("2. Zoologico - Profesionales.");
            System.out.println("3. Zoologico - ZooAmigo.");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion){
                case 1:
                    if (biomas.isEmpty()){
                        System.out.println("Aun no hay biomas registrados");
                        return;
                    }
                    System.out.println("\nEstos son los biomas disponibles:\n");
                    for (Bioma bioma : biomas) {
                        System.out.println(bioma);
                    }
                    Bioma biomaNuevo = null;
                    while (true){
                        System.out.print("\nIngrese el ID del bioma con el que quiere asociar este zoologico: ");
                        int id = input.nextInt();
                        for (Bioma bioma : biomas){
                            if (bioma.id == id){
                                biomaNuevo = bioma;
                                break;
                            }
                        }
                        if (biomaNuevo == null){
                            System.out.println("\nID no coincide con ningun bioma");
                        } else break;
                    }
                    /*Revisa si el bioma actual ya esta relacionado con algun zoologico.
                    * si es así, elimina dicha relacion y despues crea la nueva*/
                    for (Zoologico zoo : zoologicos) {
                        Bioma finalBiomaNuevo = biomaNuevo;
                        zoo.biomas.removeIf(bio -> (bio.id == finalBiomaNuevo.id)); // elimina relacion existente entre bioma y zoo
                    }
                    for (Profesional pro : biomaNuevo.profesionales){ //Si relaciono un biomaNuevo con zoologico, todos los prof. asociados a dicho bioma tendran que estar asociados al mismo zoologico
                        pro.zoologico = zoologico;
                    }
                    zoologico.setBiomas(biomaNuevo, zoologico);
                    System.out.println("\nMENSAJE: Zoologico y bioma relacionados correctamente");
                    break;

                case 2:
                    if (profesionales.isEmpty()){
                        System.out.println("Aun no hay profesionales registrados");
                        return;
                    }
                    System.out.println("\nEstos son los profesionales disponibles:\n");
                    for (Profesional profesional : profesionales) {
                        System.out.println(profesional);
                    }
                    Profesional proNuevo = null;
                    while (true){
                        System.out.print("\nIngrese la cedula del profesional con el que quiere asociar este zoologico [Solo el numero, sin espacios ni puntos]: ");
                        int cedula = input.nextInt();
                        for (Profesional profesional : profesionales){
                            if (profesional.cedula == cedula) {
                                proNuevo = profesional;
                                break;
                            }
                        }
                        if (proNuevo == null){
                            System.out.println("\nLa cedula no coincide con ningun profesionak");
                        } else break;
                    }

                    for (Zoologico zoo : zoologicos) {
                        Profesional finalProNuevo = proNuevo;
                        zoo.profesionales.removeIf(pro -> (pro.cedula == finalProNuevo.cedula));
                    }
                    Zoologico finalZoologico1 = zoologico;
                    proNuevo.biomas.removeIf(bio -> bio.zoologico != null && !bio.zoologico.nit.replace(".", "").equals(finalZoologico1.nit.replace(".", "")));
                    // Si asocio zoologico con proNuevo, voy a eliminar los biomas en los que trabaja proNuevo si estos NO pertenecen a zoologico.

                    zoologico.setProfesional(proNuevo, zoologico);
                    System.out.println("\nMENSAJE: Zoologico y profesional relacionados correctamente");
                    break;

                case 3:
                    if (zooAmigos.isEmpty()){
                        System.out.println("Aun no hay ZooAmigos registrados");
                        return;
                    }
                    System.out.println("\nEstos son los zooAmigos disponibles:\n");
                    for (ZooAmigo zooAmigo : zooAmigos) {
                        System.out.println(zooAmigo);
                    }
                    ZooAmigo nuevoZooAmigo = null;
                    while (true){
                        System.out.print("\nIngrese la cedula del ZooAmigo con el que quiere asociar este zoologico [Solo el numero, sin espacios ni puntos]: ");
                        int cedulaZA = input.nextInt();
                        for (ZooAmigo zooAmigo : zooAmigos){
                            if (zooAmigo.cedula == cedulaZA) {
                                nuevoZooAmigo = zooAmigo;
                                break;
                            }
                        }
                        if (nuevoZooAmigo == null){
                            System.out.println("\nLa cedula no coincide con ningun ZooAmigo");
                        } else break;
                    }

                    for (Zoologico zoo : zoologicos) {
                        ZooAmigo finalZANuevo = nuevoZooAmigo;
                        zoo.zooAmigos.removeIf(za -> (za.cedula == finalZANuevo.cedula));
                    }
                    Zoologico finalZoologico = zoologico;
                    nuevoZooAmigo.animales.removeIf(ani -> ani.habitat != null && ani.habitat.bioma != null && ani.habitat.bioma.zoologico != null &&
                            !ani.habitat.bioma.zoologico.nit.replace(".", "").equals(finalZoologico.nit.replace(".", "")));
                    // Si asocio zoologico con nuevoZooAmigo, voy a eliminar los animales con los que esta relacionado el zooamigo si estos NO pertenecen a zoologico.

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
            if (!opcion.equals("1") && !opcion.equals("2")){
                System.out.println("Opcion invalida");
                return;
            }
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
            } else break;
        }

        while (true){
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")){
                switch (option.toUpperCase()){
                    case "Y":
                        break;
                    case "N":
                        return;
                }
                break;
            }
            else {
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
                editarBioma();
                break;
            case "4":
                eliminarBioma();
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
    public static void editarBioma(){
        Bioma bioma = null;
        while (true) {
            String opcion;
            System.out.println("------------------------------------------");
            System.out.println("Esta es la clave unica del bioma: \n");
            System.out.println("1. Seleccionar por ID\n");
            opcion = input.next();
            if (!opcion.equals("1")){
                System.out.println("Opcion invalida");
                return;
            }
            System.out.print("Ingrese ID: ");
            int idBio = input.nextInt();
            for (Bioma bioma1 : biomas){
                if (bioma1.id == idBio){
                    bioma = bioma1;
                }
            }
            if (bioma == null){
                System.out.println("No se encontro bioma con estas especificaciones.");
            } else break;
        }

        input.nextLine();
        System.out.println("\nID: "+ bioma.id);
        String nuevoID1 = input.nextLine();
        if (!nuevoID1.isEmpty()) {
            for (Bioma bio : biomas) {
                if (bio.id == Integer.parseInt(nuevoID1)){
                    System.out.println("ERROR: Ya existe un bioma registrado con este ID.");
                    return;
                }
            }
        }

        System.out.println("Temperatura: "+ bioma.temperatura);
        String nuevaTemp1 = input.nextLine();

        System.out.println("Humedad: "+ bioma.humedad);
        String nuevoHume = input.nextLine();

        System.out.println("Tipo: "+ bioma.tipo);
        String nuevoTipo = input.nextLine();

        while (true){
            System.out.print("Desea guardar?:[Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")){
                switch (option.toUpperCase()){
                    case "Y":
                        if (nuevoID1.isEmpty()){}
                        else {
                            bioma.id = Integer.parseInt(nuevoID1);
                        }
                        if (nuevaTemp1.isEmpty()){}
                        else {
                            bioma.temperatura = Double.parseDouble(nuevaTemp1);
                        }
                        if (nuevoHume.isEmpty()){}
                        else bioma.humedad = nuevoHume;
                        if (nuevoTipo.isEmpty()){}
                        else bioma.tipo = nuevoTipo;
                    case "N":
                        break;
                }
                break;
            }
            else {
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

        label: while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. Bioma - Zoologico.");
            System.out.println("2. Bioma - Profesionales.");
            System.out.println("3. Bioma - Habitats.");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion){
                case 1:
                    if (zoologicos.isEmpty()){
                        System.out.println("Aun no hay zoologicos registrados");
                        return;
                    }
                    System.out.println("\nEstos son los zoologicos disponibles:\n");
                    for (Zoologico zoologico : zoologicos) {
                        System.out.println(zoologico);
                    }
                    Zoologico ZooNuevo = null;
                    while (true){
                        System.out.print("\nIngrese el NIT del zoologico con el que quiere asociar este bioma: ");
                        String nit = input.next();
                        for (Zoologico zoologico : zoologicos){
                            if (zoologico.nit.replace(".", "").equals(nit.replace(".", ""))){
                                ZooNuevo = zoologico;
                                break;
                            }
                        }
                        if (ZooNuevo == null){
                            System.out.println("\nNIT no coincide con ningun zoologico");
                        } else break;
                    }

                    for (Zoologico zoo : zoologicos) {
                        Bioma finalBiomaNuevo = bioma;
                        zoo.biomas.removeIf(bio -> (bio.id == finalBiomaNuevo.id));
                    }
                    for (Profesional pro : bioma.profesionales){ //Si relaciono un bioma con ZooNuevo, todos los prof. asociados a dicho bioma tendran que estar asociados al mismo zoologico
                        pro.zoologico = ZooNuevo;
                    }

                    bioma.setZoo(ZooNuevo, bioma);
                    System.out.println("\nMENSAJE: Bioma y zoologico relacionados correctamente");
                    break;

                case 2:
                    if (profesionales.isEmpty()){
                        System.out.println("Aun no hay profesionales registrados");
                        return;
                    }
                    System.out.println("\nEstos son los profesionales disponibles:\n");
                    for (Profesional profesional : profesionales) {
                        System.out.println(profesional);
                    }
                    Profesional proNuevo = null;
                    while (true){
                        System.out.print("\nIngrese la cedula del profesional con el que quiere asociar este bioma [Solo el numero, sin espacios ni puntos]: ");
                        int cedula = input.nextInt();
                        for (Profesional profesional : profesionales){
                            if (profesional.cedula == cedula) {
                                proNuevo = profesional;
                                break;
                            }
                        }
                        if (proNuevo == null){
                            System.out.println("\nLa cedula no coincide con ningun profesionak");
                        } else break;
                    }

                    Profesional finalProNuevo = proNuevo;
                    bioma.profesionales.removeIf(pro -> (pro.cedula == finalProNuevo.cedula));
                    Bioma finalBioma = bioma;
                    proNuevo.biomas.removeIf(bio -> (bio.id == finalBioma.id));

                    if (bioma.zoologico != null) proNuevo.zoologico = bioma.zoologico; // El nuevo zoologico de proNuevo sera el zoologico al que pertenece el bioma al que entrara a trabajar.
                    proNuevo.biomas.removeIf(bio -> bio.zoologico != null && !bio.zoologico.nit.replace(".", "").equals(finalBioma.zoologico.nit.replace(".", "")));
                    // Si asocio bioma con proNuevo, voy a eliminar los biomas en los que trabaja proNuevo si estos NO pertenecen al zoologico al que pertenece bioma.

                    bioma.setProfesional(proNuevo, bioma);
                    System.out.println("\nMENSAJE: Bioma y profesional relacionados correctamente");
                    break;

                case 3:
                    if (habitats.isEmpty()){
                        System.out.println("Aun no hay habitats registrados");
                        return;
                    }
                    System.out.println("\nEstos son los habitats disponibles:\n");
                    for (Habitat habitat : habitats) {
                        System.out.println(habitat);
                    }
                    Habitat nuevoHabitat = null;
                    while (true){
                        System.out.print("\nIngrese el ID del habitat con el que quiere asociar este bioma: ");
                        int IDHab = input.nextInt();
                        for (Habitat habitat : habitats){
                            if (habitat.id == IDHab) {
                                nuevoHabitat = habitat;
                                break;
                            }
                        }
                        if (nuevoHabitat == null){
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
            if (!opcion.equals("1")){
                System.out.println("Opcion invalida");
                return;
            }
            if (opcion.equals("1")){
                System.out.print("Ingrese ID: ");
                int id = input.nextInt();
                for (Bioma bio : biomas){
                    if (bio.id == id){
                        bioma = bio;
                    }
                }
            }
            if (bioma == null){
                System.out.println("No se encontro bioma con estas especificaciones.");
            } else break;
        }

        while (true){
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")){
                switch (option.toUpperCase()){
                    case "Y":
                        break;
                    case "N":
                        return;
                }
                break;
            }
            else {
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
    public static void editarHabitat() {
        Habitat habitat = null;
        while (true) {
            String opcion;
            System.out.println("------------------------------------------");
            System.out.println("Esta es la clave unica del habitat: \n");
            System.out.println("1. Seleccionar por ID\n");
            opcion = input.next();
            if (!opcion.equals("1")){
                System.out.println("Opcion invalida");
                return;
            }
            System.out.print("Ingrese ID: ");
            int idHab = input.nextInt();
            for (Habitat hab : habitats){
                if (hab.id == idHab){
                    habitat = hab;
                }
            }
            if (habitat == null){
                System.out.println("No se encontro habitat con estas especificaciones.");
            } else break;
        }

        input.nextLine();
        System.out.println("\nID: "+ habitat.id);
        String nuevoID1 = input.nextLine();
        if (!nuevoID1.isEmpty()) {
            for (Habitat hab : habitats) {
                if (hab.id == Integer.parseInt(nuevoID1)){
                    System.out.println("ERROR: Ya existe un habitat registrado con este ID.");
                    return;
                }
            }
        }

        System.out.println("Tipo de suelo: "+ habitat.tipoSuelo);
        String nuevoSuelo = input.nextLine();

        System.out.println("Vegetacion: "+ habitat.vegetacion);
        String nuevaVege = input.nextLine();

        System.out.println("Tipo de jaula: "+ habitat.tipoJaula);
        String nuevaJau = input.nextLine();

        while (true){
            System.out.print("Desea guardar?:[Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")){
                switch (option.toUpperCase()){
                    case "Y":
                        if (nuevoID1.isEmpty()){}
                        else {
                            habitat.id = Integer.parseInt(nuevoID1);
                        }
                        if (nuevoSuelo.isEmpty()){}
                        else habitat.tipoSuelo = nuevoSuelo;
                        if (nuevaVege.isEmpty()){}
                        else habitat.vegetacion = nuevaVege;
                        if (nuevaJau.isEmpty()){}
                        else habitat.tipoJaula = nuevaJau;
                    case "N":
                        break;
                }
                break;
            }
            else {
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

        label: while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. Habitat - Bioma.");
            System.out.println("2. Habitat - Tecnico.");
            System.out.println("3. Habitat - Animal.");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion){
                case 1:
                    if (biomas.isEmpty()){
                        System.out.println("Aun no hay biomas registrados");
                        return;
                    }
                    System.out.println("\nEstos son los biomas disponibles:\n");
                    for (Bioma bio : biomas) {
                        System.out.println(bio);
                    }
                    Bioma BioNuevo = null;
                    while (true){
                        System.out.print("\nIngrese el ID del bioma con el que quiere asociar este bioma: ");
                        int id = input.nextInt();
                        for (Bioma bio : biomas){
                            if (bio.id == id){
                                BioNuevo = bio;
                                break;
                            }
                        }
                        if (BioNuevo == null){
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
                    if (tecnicos.isEmpty()){
                        System.out.println("Aun no hay tecnicos registrados");
                        return;
                    }
                    System.out.println("\nEstos son los tecnicos disponibles:\n");
                    for (Tecnico tecnico : tecnicos) {
                        System.out.println(tecnico);
                    }
                    Tecnico tecNuevo = null;
                    while (true){
                        System.out.print("\nIngrese la cedula del tecnico con el que quiere asociar este tecnico [Solo el numero, sin espacios ni puntos]: ");
                        int cedula = input.nextInt();
                        for (Tecnico tec : tecnicos){
                            if (tec.cedula == cedula) {
                                tecNuevo = tec;
                                break;
                            }
                        }
                        if (tecNuevo == null){
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
                    if (animales.isEmpty()){
                        System.out.println("Aun no hay animales registrados");
                        return;
                    }
                    System.out.println("\nEstos son los animales disponibles:\n");
                    for (Animal animal : animales) {
                        System.out.println(animal);
                    }
                    Animal nuevoAnimal = null;
                    while (true){
                        System.out.print("\nIngrese el ID del animal con el que quiere asociar este habitat: ");
                        int IDAni = input.nextInt();
                        for (Animal animal : animales){
                            if (animal.id == IDAni) {
                                nuevoAnimal = animal;
                                break;
                            }
                        }
                        if (nuevoAnimal == null){
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
            if (!opcion.equals("1")){
                System.out.println("Opcion invalida");
                return;
            }
            if (opcion.equals("1")){
                System.out.print("Ingrese ID: ");
                int id = input.nextInt();
                for (Habitat hab : habitats){
                    if (hab.id == id){
                        habitat = hab;
                    }
                }
            }
            if (habitat == null){
                System.out.println("No se encontro habitat con estas especificaciones.");
            } else break;
        }

        while (true){
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")){
                switch (option.toUpperCase()){
                    case "Y":
                        break;
                    case "N":
                        return;
                }
                break;
            }
            else {
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
                editarAnimal();
                break;
            case "4":
                eliminarAnimal();
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
        System.out.println("Ingrese el ID: ");
        int id = input.nextInt();
        if (id<0){
            System.out.println("ID Invalido");
            return;
        }
        for (Animal animal :
                animales) {
            if (animal.id==id){
                System.out.println("ERROR: Ya existe un animal registrado con este ID.");
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
        
        if (!nuevoID.isEmpty()){
            for (Animal animal1 :
                    animales) {
                if (animal1.id == Integer.parseInt(nuevoID)) {
                    System.out.println("El ID INGRESADO YA ESTÁ EN USO");
                    return;
                }
            }
        }

        System.out.println("Especie: " + animal.especie);
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

        label: while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. Animal - Habitat");
            System.out.println("2. Animal - ZooAmigo.");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion){
                case 1:
                    if (habitats.isEmpty()){
                        System.out.println("Aun no hay habitat registrados");
                        return;
                    }
                    System.out.println("\nEstos son los habitats disponibles:\n");
                    for (Habitat habitat : habitats) {
                        System.out.println(habitat);
                    }
                    Habitat habitatNuevo = null;
                    while (true){
                        System.out.print("\nIngrese el ID del habitat con el que quiere asociar este animal: ");
                        int id = input.nextInt();
                        for (Habitat habitat : habitats){
                            if (habitat.id == id){
                                habitatNuevo = habitat;
                                break;
                            }
                        }
                        if (habitatNuevo == null){
                            System.out.println("\nID no coincide con ningun habitat");
                        } else break;
                    }
                    if (habitatNuevo.animales.contains(animal)){
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
                    if (zooAmigos.isEmpty()){
                        System.out.println("Aun no hay zooAmigos registrados");
                        return;
                    }
                    System.out.println("\nEstos son los ZooAmigos disponibles:\n");
                    for (ZooAmigo zooAmigo : zooAmigos) {
                        System.out.println(zooAmigo);
                    }
                    ZooAmigo zooAmigoNuevo = null;
                    while (true){
                        System.out.print("\nIngrese la cedula del zoooAmigo con el que quiere asociar este animal: ");
                        int cedula = input.nextInt();
                        for (ZooAmigo zooAmigo : zooAmigos){
                            if (zooAmigo.cedula == cedula){
                                zooAmigoNuevo = zooAmigo;
                                break;
                            }
                        }
                        if (zooAmigoNuevo == null){
                            System.out.println("\nID no coincide con ningun habitat");
                        } else break;
                    }
                    if (zooAmigoNuevo.animales.contains(animal)){
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
        int id = 0;
        while (true){
            System.out.println("Ingrese el ID del animal que desea eliminar: ");
            id = input.nextInt();
            if (id<0){
                System.out.println("ID invalido");
                return;
            }
            Animal animaltempo = null;
            for (Animal animal1 :
                    animales) {
                if (animal1.id==id){
                    animaltempo = animal1;
                }
            }
            if (animaltempo==null){
                System.out.println("El animal ingresado no existe");
            }
            else break;
        }

        while (true){
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")){
                switch (option.toUpperCase()){
                    case "Y":
                        int finalId2 = id;
                        Boolean eliminar = animales.removeIf(animal -> animal.id == finalId2); // Elimina el animal por ID, devuelve True o False

                        if (eliminar==false){
                            System.out.println("ERROR: El animal no se encuentra registrado");
                            return;
                        }
                        for (Habitat habitat :
                                habitats) {
                            int finalId = id;
                            habitat.animales.removeIf(animal -> animal.id == finalId); //Elimando la relación con Habitat
                        }
                        for (ZooAmigo zooAmigo :
                                zooAmigos) {
                            int finalId1 = id;
                            zooAmigo.animales.removeIf(animal -> animal.id == finalId1); //Elimando la relación con ZooAmigo
                        }
                        System.out.println("El animal se ha eliminado correctamente");
                        break;
                    case "N":
                        return;
                }
                break;
            }
            else {
                System.out.println("Opcion invalida\n");
            }
        }
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
                editarTecnico();
                break;
            case "4":
                eliminarTecnico();
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
    public static void editarTecnico(){
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

        if (!nuevoCedula.isEmpty()){
            for (Tecnico tecnico1 :
                    tecnicos) {
                if (tecnico1.cedula == Integer.parseInt(nuevoCedula)) {
                    System.out.println("LA CEDULA INGRESADA YA ESTÁ EN USO");
                    return;
                }
            }
        }

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

        label: while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. Tecnico - Habitat");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion){
                case 1:
                    if (habitats.isEmpty()){
                        System.out.println("Aun no hay habitat registrados");
                        return;
                    }
                    System.out.println("\nEstos son los habitats disponibles:\n");
                    for (Habitat habitat : habitats) {
                        System.out.println(habitat);
                    }
                    Habitat habitatNuevo = null;
                    while (true){
                        System.out.print("\nIngrese el ID del habitat con el que quiere asociar este tecnico: ");
                        int id = input.nextInt();
                        for (Habitat habitat : habitats){
                            if (habitat.id == id){
                                habitatNuevo = habitat;
                                break;
                            }
                        }
                        if (habitatNuevo == null){
                            System.out.println("\nID no coincide con ningun habitat");
                        } else break;
                    }


                    Habitat finalHabitatNuevo = habitatNuevo;
                    tecnico.habitats.removeIf(habitat -> habitat.id== finalHabitatNuevo.id);

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
    public static void eliminarTecnico(){
        int cedula =0;
        while (true){
            System.out.println("Ingrese la cedula del tecnico que desea eliminar: ");
            cedula = input.nextInt();
            if (cedula<0){
                System.out.println("Cedula invalida");
                return;
            }
            Tecnico tecnicotempo = null;
            for (Tecnico tecnico1 :
                    tecnicos) {
                if (tecnico1.cedula==cedula){
                    tecnicotempo=tecnico1;
                }
            }
            if (tecnicotempo==null){
                System.out.println("El Tecnico ingresado no existe");
            }else break;
        }

        while (true){
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")){
                switch (option.toUpperCase()){
                    case "Y":
                        int finalCedula = cedula;
                        boolean eliminar = tecnicos.removeIf(tecnico ->  tecnico.cedula== finalCedula); // Elimina el animal por ID, devuelve True o False
                        if (!eliminar){
                            System.out.println("ERROR: El Tecnico no se encuentra registrado");
                            return;
                        }
                        for (Habitat habitat :
                                habitats) {
                            int finalCedula1 = cedula;
                            habitat.tecnicos.removeIf(tecnico ->  tecnico.cedula== finalCedula1); //Elimando la relación con Habitat
                        }
                        System.out.println("El tecnico se ha eliminado correctamente");
                        break;
                    case "N":
                        return;
                }
                break;
            }
            else {
                System.out.println("Opcion invalida\n");
            }
        }

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
                editarProfesional();
                break;
            case "4":
                eliminarProfesional();
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
    public static void editarProfesional(){
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
                System.out.println("No se encontro un profesional con la cedula ingresada");
            } else break;
        }
        input.nextLine();
        System.out.println("\nCedula: " + profesional.cedula);
        String nuevoCedula = input.nextLine();

        if (!nuevoCedula.isEmpty()){
            for (Profesional profesional1 :
                    profesionales) {
                if (profesional1.cedula == Integer.parseInt(nuevoCedula)) {
                    System.out.println("LA CEDULA INGRESADA YA ESTÁ EN USO");
                    return;
                }
            }
        }

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

        label: while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. Profesional - bioma");
            System.out.println("2. Profesional - Zoologico");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion){
                case 1:
                    if (biomas.isEmpty()){
                        System.out.println("Aun no hay biomas registrados");
                        return;
                    }
                    System.out.println("\nEstos son los biomas disponibles:\n");
                    for (Bioma bioma : biomas) {
                        System.out.println(bioma);
                    }
                    Bioma biomaNuevo = null;
                    while (true){
                        System.out.print("\nIngrese el ID del bioma con el que quiere asociar este tecnico: ");
                        int id = input.nextInt();
                        for (Bioma bioma : biomas){
                            if (bioma.id == id){
                                biomaNuevo = bioma;
                                break;
                            }
                        }
                        if (biomaNuevo == null){
                            System.out.println("\nID no coincide con ningun bioma");
                        } else break;
                    }
                    if (biomaNuevo.profesionales.contains(profesional)){
                        System.out.println("El Bioma y el Profesional ya están relacionados");
                        return;
                    }
                    Bioma finalBiomaNuevo = biomaNuevo;
                    profesional.biomas.removeIf(bioma -> bioma.id== finalBiomaNuevo.id);


                    Profesional finalProfesional = profesional;
                    biomaNuevo.profesionales.removeIf(profesional1 -> profesional1.cedula == finalProfesional.cedula);

                    if (biomaNuevo.zoologico != null) profesional.zoologico = biomaNuevo.zoologico; //Asocio a profesional el zoologico al que pertenece biomaNuevo
                    Bioma finalBiomaNuevo1 = biomaNuevo;
                    profesional.biomas.removeIf(bio -> bio.zoologico != null && !bio.zoologico.nit.replace(".", "").equals(finalBiomaNuevo1.zoologico.nit.replace(".", "")));
                    // Si profesional entra a trabajar a biomaNuevo, estara relacionado al zoologico al que pertenece biomaNuevo.
                    // Ademas, debe parar de trabajar en biomas que no pertenezcan al zoologico al que trabajara actualmente.

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
                    if (zoologico.profesionales.contains(profesional)){
                        System.out.println("El Zoologico y el Profesional ya están relacionados");
                        return;
                    }
                    for (Zoologico zoologico1: zoologicos){
                        zoologico1.profesionales.remove(profesional);
                    }

                    Zoologico finalZoologico = zoologico;
                    profesional.biomas.removeIf(bio -> bio.zoologico != null && !bio.zoologico.nit.replace(".", "").equals(finalZoologico.nit.replace(".", "")));
                    // Si asocio zoologico con profesional, voy a eliminar los biomas en los que trabaja profesional si estos NO pertenecen al nuevo zoologico en el que trabajara profesional.


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
    public static void  eliminarProfesional(){
        int cedula =0;
        while (true){
            System.out.println("Ingrese la cedula del profesional que desea eliminar: ");
            cedula = input.nextInt();
            if (cedula<0){
                System.out.println("Cedula invalida");
                return;
            }
            Profesional profesionalTempo =null;
            for (Profesional profesional1 :
                    profesionales) {
                if (profesional1.cedula == cedula){
                    profesionalTempo = profesional1;
                }
            }
            if (profesionalTempo == null){
                System.out.println("El Profesional ingresado no existe");
            } else break;
        }

        while (true){
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")){
                switch (option.toUpperCase()){
                    case "Y":
                        int finalCedula = cedula;
                        boolean eliminar = profesionales.removeIf(profesional ->  profesional.cedula== finalCedula); // Elimina el profesional por cedula, devuelve True o False
                        if (!eliminar){
                            System.out.println("ERROR: El Tecnico no se encuentra registrado");
                            return;
                        }
                        for (Bioma bioma :
                                biomas) {
                            int finalCedula1 = cedula;
                            bioma.profesionales.removeIf(profesional ->  profesional.cedula== finalCedula1); //Elimando la relación con Bioma
                        }
                        for (Zoologico zoologico :
                                zoologicos) {
                            int finalCedula2 = cedula;
                            zoologico.profesionales.removeIf(profesional ->  profesional.cedula== finalCedula2); //Elimando la relación con Bioma
                        }
                        System.out.println("El profesional se ha eliminado correctamente");
                        break;
                    case "N":
                        return;
                }
                break;
            }
            else {
                System.out.println("Opcion invalida\n");
            }
        }

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
                editarZooAmigo();
                break;
            case "4":
                eliminarZooAmigo();
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
    public static void editarZooAmigo(){
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

        if (!nuevoCedula.isEmpty()){
            for (ZooAmigo zooAmigo1 :
                    zooAmigos) {
                if (zooAmigo1.cedula == Integer.parseInt(nuevoCedula)) {
                    System.out.println("LA CEDULA INGRESADA YA ESTÁ EN USO");
                    return;
                }
            }
        }

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

        label: while (true) {
            System.out.println("\nPor favor, selecione la relacion que desea editar: \n");
            System.out.println("1. ZooAmigo - Animal");
            System.out.println("2. ZooAmigo - Zoologico");
            System.out.println("0. Regresar a Menu Administrar.");

            int opcion = input.nextInt();
            System.out.println();
            switch (opcion){
                case 1:
                    if (animales.isEmpty()){
                        System.out.println("Aun no hay animales registrados");
                        return;
                    }
                    System.out.println("\nEstos son los animales disponibles:\n");
                    for (Animal animal : animales) {
                        System.out.println(animal);
                    }
                    Animal animalNuevo = null;
                    while (true){
                        System.out.print("\nIngrese el ID del animal con el que quiere asociar este ZooAmigo: ");
                        int id = input.nextInt();
                        for (Animal animal : animales){
                            if (animal.id == id){
                                animalNuevo = animal;
                                break;
                            }
                        }
                        if (animalNuevo == null){
                            System.out.println("\nID no coincide con ningun Animal");
                        } else break;
                    }

                    for (Animal animal1 :
                            animales) {
                        animal1.zooAmigo=null;
                    }
                    zooAmigo.setAnimal(animalNuevo, zooAmigo);
                    System.out.println("\n MENSAJE: ZooAmigo y Animal se han relacionado correctamente");

                    break;
                case 2:
                    if (zoologicos.isEmpty()){
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
                        if (!option.equals("1") && !option.equals("2")){
                            System.out.println("Opcion invalida");
                            return;
                        }
                        if (option.equals("1")){
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
    public static void eliminarZooAmigo(){
        int cedula = 0;
        while (true){
            System.out.println("Ingrese la cedula del ZooAmigo que desea eliminar: ");
            cedula = input.nextInt();
            if (cedula<0){
                System.out.println("Cedula invalida");
                return;
            }
            ZooAmigo zooAmigoTempo = null;
            for (ZooAmigo zooAmigo1 :
                    zooAmigos) {
                if (zooAmigo1.cedula == cedula) {
                    zooAmigoTempo = zooAmigo1;
                }
            }
            if (zooAmigoTempo==null){
                System.out.println("El ZooAmigo ingresado no exsite");
            } else break;
        }

        while (true){
            System.out.print("Esta seguro que desea eliminar?: [Y/N] ");
            String option = input.next();
            if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("N")){
                switch (option.toUpperCase()){
                    case "Y":
                        int finalCedula = cedula;
                        boolean eliminar = zooAmigos.removeIf(zooAmigo ->  zooAmigo.cedula== finalCedula); // Elimina el profesional por cedula, devuelve True o False
                        if (!eliminar){
                            System.out.println("ERROR: El ZooAmigo no se encuentra registrado");
                            return;
                        }

                        for (Animal animal :
                                animales) {
                            if (animal.zooAmigo!=null && animal.zooAmigo.cedula==cedula  ) {
                                animal.zooAmigo=null;
                            }
                        }
                        for (Zoologico zoologico :
                                zoologicos) {
                            int finalCedula1 = cedula;
                            zoologico.zooAmigos.removeIf(zooAmigo->  zooAmigo.cedula== finalCedula1); //Elimando la relación con Bioma
                        }
                        System.out.println("El ZooAmigo se ha eliminado correctamente");
                        break;
                    case "N":
                        return;
                }
                break;
            }
            else {
                System.out.println("Opcion invalida\n");
            }
        }
    }
}
