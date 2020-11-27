package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.jgrapht.alg.drawing.FRLayoutAlgorithm2D;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class busquedaTemperaturaController {
    @FXML
    private RadioButton radioButtonAtributo;
    @FXML
    private ListView<String> habitats;
    @FXML
    private TextField tipoSueloTextField;
    @FXML
    private TextField temperaturaTextField;
    @FXML
    private Label warning;
    @FXML
    public Label textResult;

    private void rutaBuscarHabitat(ActionEvent event) throws IOException {
        warning.setText("");
        if(temperaturaTextField.getText().equals("")){
            warning.setText("El atributo no puede estar vacio");
            return;
        }
        try {
            Integer.parseInt(tipoSueloTextField.getText().trim());
        } catch (Exception e){
            warning.setText("El tipo de suelo debe ser una palabra");
            return;
        }

        if(Habitat.habitatsPorTemperatura.containsKey(tipoSueloTextField.getText())){

        }
    }

}


