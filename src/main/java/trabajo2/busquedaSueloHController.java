package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.io.IOException;

public class busquedaSueloHController{
    @FXML
    private TextField sueloHTextField;
    @FXML
    private Label textResult;
    @FXML
    private Label warningMessageGeneral;
    @FXML
    private void buscar(ActionEvent event) throws IOException {
        warningMessageGeneral.setText("");
        if(sueloHTextField.getText().equals("")){
            warningMessageGeneral.setText("No puede estar vacio el tipo de suelo");
            return;
        }

        try{
            sueloHTextField.getText();
        }catch (Exception e){
            warningMessageGeneral.setText("El tipo de suelo no puede ser un número");
            return;
        }


        if(Habitat.habitatsPorSuelo.containsKey(sueloHTextField.getText())){
            textResult.setText("\n"+Habitat.habitatsPorSuelo.get(sueloHTextField.getText()));
        }else{
            textResult.setText("¡No se encontro el resultado!");
        }
    }

    @FXML
    private void volver() throws IOException {
        App.setRoot("atributosHabitat");
    }
}