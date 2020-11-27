package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.io.IOException;

public class busquedaAreaController{
    @FXML
    private TextField areaTextField;
    @FXML
    private Label textResult;
    @FXML
    private Label warningMessageGeneral;
    @FXML
    private void buscar(ActionEvent event) throws IOException {
        warningMessageGeneral.setText("");
        if(areaTextField.getText().equals("")){
            warningMessageGeneral.setText("No puede estar vacio el tipo de especie");
            return;
        }

        if(Tecnico.tecnicosPorArea.containsKey(areaTextField.getText())){
            textResult.setText("\n"+Tecnico.tecnicosPorArea.get(areaTextField.getText()));
return;
        }else{
            textResult.setText("¡No se encontro el resultado!");
            return;
        }
    }

    @FXML
    private void volver() throws IOException {
        App.setRoot("atributosTecnico");
    }
}
