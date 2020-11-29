package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

public class VerAnimalController implements Initializable {

    @FXML
    private ListView<String> animales;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrarAnimal");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Object obj : App.sistemaZoo.vertexSet()){
            String info = "";
            if (obj instanceof Animal){
                Animal aux = (Animal) obj;
                info = aux.toString() + "\n" + "Está relacionado con:\n";
                Set<DefaultEdge> relaciones = App.sistemaZoo.edgesOf(obj);
                if (relaciones.isEmpty()){
                    info = info + "No tiene relaciones registradas\n";
                } else {
                    ArrayList<Object> objAdyacentes = new ArrayList<>(Graphs.neighborListOf(App.sistemaZoo, aux));
                    for (Object object : objAdyacentes){
                        info = info + object.toString()+ "\n";
                    }
                }
                animales.getItems().add(info);
            }
        }
    }
}
