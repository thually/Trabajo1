package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class verHabitatController implements Initializable {

    @FXML
    private ListView<String> habitats;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        App.setRoot("administrarHabitat");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Object obj : App.sistemaZoo.vertexSet()){
            String info = "";
            if (obj instanceof Habitat){
                Habitat aux = (Habitat) obj;
                info = aux.toString() + "\n" + "Está relacionado con:\n";
                Set<DefaultEdge> relaciones = App.sistemaZoo.edgesOf(obj);
                if (relaciones.isEmpty()){
                    info = info + "No tiene relaciones registradas\n";
                } else {
                    for (DefaultEdge edge : relaciones){
                        info = info + App.sistemaZoo.getEdgeTarget(edge).toString() + "\n";
                    }
                }
                habitats.getItems().add(info);
            }
        }
    }
}
