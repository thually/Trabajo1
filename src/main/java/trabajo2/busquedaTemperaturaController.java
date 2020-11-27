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
    private TextField temperaturaTextField;
    @FXML
    private Label warning;
    @FXML
    public Label textResult;

    private void buscar(ActionEvent event) throws IOException {
        warning.setText("");
        if(temperaturaTextField.getText().equals("")){
            warning.setText("El atributo no puede estar vacio");
            return;
        }
        try {
            Integer.parseInt(temperaturaTextField.getText().trim());
        } catch (Exception e){
            warning.setText("La temperatura debe ser un número");
            return;
        }

        if(Habitat.habitatsPorTemperatura.containsKey(Double.parseDouble(temperaturaTextField.getText()))){
            textResult.setText("\n"+Habitat.habitatsPorTemperatura.get(Double.parseDouble(temperaturaTextField.getText())));
        }else{
            textResult.setText("¡No se encontro el resultado!");
        }
    }
    @FXML
    private void volver() throws IOException {
        App.setRoot("atributosHabitat");
    }

}


