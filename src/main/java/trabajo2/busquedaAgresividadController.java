package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.io.IOException;

public class busquedaAgresividadController {
    @FXML
    private TextField agresividadTextField;
    @FXML
    private Label textResult;
    @FXML
    private Label warningMessageGeneral;
    @FXML
    private void buscar(ActionEvent event) throws IOException {
        warningMessageGeneral.setText("");
        if(agresividadTextField.getText().equals("")){
            warningMessageGeneral.setText("No puede estar vacio el nivel de agresividad");
            return;
        }

        try{
            Integer.parseInt(agresividadTextField.getText().trim());
        }catch (Exception e){
            warningMessageGeneral.setText("El nivel de agresividad tiene que ser un numero");
            return;
        }


        if(Animal.animalesPorAgresividad.containsKey(Integer.parseInt(agresividadTextField.getText()))){
            textResult.setText("\n"+Animal.animalesPorAgresividad.get(Integer.parseInt(agresividadTextField.getText())));
        }else{
            textResult.setText("¡No se encontro el animal!");
        }
    }

    @FXML
    private void volver() throws IOException {
        App.setRoot("atributosAnimal");
    }
}
