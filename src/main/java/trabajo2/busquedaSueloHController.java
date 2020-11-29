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
        textResult.setText("");
        warningMessageGeneral.setText("");
        if(sueloHTextField.getText().equals("")){
            warningMessageGeneral.setText("No puede estar vacio el tipo de suelo");
            return;
        }

        textResult.setText("");
        try{
            Integer.parseInt(sueloHTextField.getText().trim());
            warningMessageGeneral.setText("La especie del animal no puede ser un número");
            return;
        }
        catch (Exception e){
        }

        textResult.setText("");
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