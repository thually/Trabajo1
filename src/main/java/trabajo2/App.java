package trabajo2;
import org.jgrapht.Graph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


public class App extends Application {

    public static Graph<Object, DefaultEdge> sistemaZoo = new SimpleGraph<>(DefaultEdge.class);
    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("ingresar"), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        quemaDeInstancias();

        launch();
    }

    public static void quemaDeInstancias(){
        Animal animal1 = new Animal(101,"delfin", 9, "carnivoro");
        Animal animal2 = new Animal(102,"mapache", 7, "carnivoro");
        Animal animal3 = new Animal(103,"león", 8, "carnivoro");
        Animal.animalesPorID.put(animal1.id, animal1);
        Animal.animalesPorID.put(animal2.id, animal1);
        Animal.animalesPorID.put(animal3.id, animal1);
        Animal.animalesPorAgresividad.put(animal1.nivelAgresividad, new LinkedList<>()); Animal.animalesPorAgresividad.get(animal1.nivelAgresividad).add(animal1);
        Animal.animalesPorAgresividad.put(animal2.nivelAgresividad, new LinkedList<>()); Animal.animalesPorAgresividad.get(animal2.nivelAgresividad).add(animal2);
        Animal.animalesPorAgresividad.put(animal3.nivelAgresividad, new LinkedList<>()); Animal.animalesPorAgresividad.get(animal3.nivelAgresividad).add(animal3);
        Animal.animalesPorEspecie.put(animal1.especie, new LinkedList<>()); Animal.animalesPorEspecie.get(animal1.especie).add(animal1);
        Animal.animalesPorEspecie.put(animal2.especie, new LinkedList<>()); Animal.animalesPorEspecie.get(animal2.especie).add(animal2);
        Animal.animalesPorEspecie.put(animal3.especie, new LinkedList<>()); Animal.animalesPorEspecie.get(animal3.especie).add(animal3);
        sistemaZoo.addVertex(animal1);
        sistemaZoo.addVertex(animal2);
        sistemaZoo.addVertex(animal3);

        Habitat habitat1 = new Habitat(201, 32.2, "pedregoso", "arboles", "reja");
        Habitat habitat2 = new Habitat(202, 42.2, "arenoso", "arbustos", "vidrio");
        Habitat habitat3 = new Habitat(203, 12.2, "desertico", "escasa", "malla");
        Habitat.habitatsPorID.put(habitat1.id, habitat1);
        Habitat.habitatsPorID.put(habitat2.id, habitat2);
        Habitat.habitatsPorID.put(habitat3.id, habitat3);
        Habitat.habitatsPorSuelo.put(habitat1.tipoSuelo, new LinkedList<>()); Habitat.habitatsPorSuelo.get(habitat1.tipoSuelo).add(habitat1);
        Habitat.habitatsPorSuelo.put(habitat2.tipoSuelo, new LinkedList<>()); Habitat.habitatsPorSuelo.get(habitat2.tipoSuelo).add(habitat2);
        Habitat.habitatsPorSuelo.put(habitat3.tipoSuelo, new LinkedList<>()); Habitat.habitatsPorSuelo.get(habitat3.tipoSuelo).add(habitat3);
        Habitat.habitatsPorTemperatura.put(habitat1.temperatura, new LinkedList<>()); Habitat.habitatsPorTemperatura.get(habitat1.temperatura).add(habitat1);
        Habitat.habitatsPorTemperatura.put(habitat2.temperatura, new LinkedList<>()); Habitat.habitatsPorTemperatura.get(habitat2.temperatura).add(habitat2);
        Habitat.habitatsPorTemperatura.put(habitat3.temperatura, new LinkedList<>()); Habitat.habitatsPorTemperatura.get(habitat3.temperatura).add(habitat3);
        sistemaZoo.addVertex(habitat1);
        sistemaZoo.addVertex(habitat2);
        sistemaZoo.addVertex(habitat3);

        Tecnico tecnico1 = new Tecnico(301, 1500000, "guardia", "08:00", "18:00");
        Tecnico tecnico2 = new Tecnico(302, 2000000, "guía", "07:00", "17:00");
        Tecnico tecnico3 = new Tecnico(303, 2500000, "mantenimiento", "06:00", "16:00");
        Tecnico.tecnicosPorID.put(tecnico1.cedula, tecnico1);
        Tecnico.tecnicosPorID.put(tecnico2.cedula, tecnico2);
        Tecnico.tecnicosPorID.put(tecnico3.cedula, tecnico3);
        Tecnico.tecnicosPorArea.put(tecnico1.area, new LinkedList<>()); Tecnico.tecnicosPorArea.get(tecnico1.area).add(tecnico1);
        Tecnico.tecnicosPorArea.put(tecnico2.area, new LinkedList<>()); Tecnico.tecnicosPorArea.get(tecnico2.area).add(tecnico2);
        Tecnico.tecnicosPorArea.put(tecnico3.area, new LinkedList<>()); Tecnico.tecnicosPorArea.get(tecnico3.area).add(tecnico3);
        Tecnico.tecnicosPorSueldo.put(tecnico1.sueldo, new LinkedList<>()); Tecnico.tecnicosPorSueldo.get(tecnico1.sueldo).add(tecnico1);
        Tecnico.tecnicosPorSueldo.put(tecnico2.sueldo, new LinkedList<>()); Tecnico.tecnicosPorSueldo.get(tecnico2.sueldo).add(tecnico2);
        Tecnico.tecnicosPorSueldo.put(tecnico3.sueldo, new LinkedList<>()); Tecnico.tecnicosPorSueldo.get(tecnico3.sueldo).add(tecnico3);
        sistemaZoo.addVertex(tecnico1);
        sistemaZoo.addVertex(tecnico2);
        sistemaZoo.addVertex(tecnico3);

        sistemaZoo.addEdge(habitat1, animal1);
        sistemaZoo.addEdge(habitat1, animal2);
        sistemaZoo.addEdge(habitat1, tecnico1);
        sistemaZoo.addEdge(habitat2, animal3);
        sistemaZoo.addEdge(habitat2, tecnico2);
        sistemaZoo.addEdge(habitat2, tecnico3);
        sistemaZoo.addEdge(habitat2, tecnico1);

        Usuario usuario1 = new Usuario("123", "Alejo", "Salazar", "alsalazarm", "hola");
        usuarios.add(usuario1);

    }
}