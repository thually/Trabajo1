package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.io.IOException;

public class busquedaEspecieController{
    @FXML
    private TextField especieTextField;
    @FXML
    private Label textResult;
    @FXML
    private Label warningMessageGeneral;
    @FXML
    private void buscar(ActionEvent event) throws IOException {
        textResult.setText("");
        warningMessageGeneral.setText("");
        if(especieTextField.getText().equals("")){
            warningMessageGeneral.setText("No puede estar vacio el tipo de especie");
            return;
        }
        textResult.setText("");
        try{
            Integer.parseInt(especieTextField.getText().trim());
            warningMessageGeneral.setText("La especie del animal no puede ser un número");
            return;
        }
        catch (Exception e){
        }

        textResult.setText("");
        if(Animal.animalesPorEspecie.containsKey(especieTextField.getText())){
            textResult.setText("\n"+Animal.animalesPorEspecie.get(especieTextField.getText()));

        }else{
            textResult.setText("¡No se encontro el resultado!");
        }



    }

    @FXML
    private void volver() throws IOException {
        App.setRoot("atributosAnimal");
    }
}
