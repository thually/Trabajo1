package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.io.IOException;

public class busquedaSueldoController {
    @FXML
    private TextField sueldoTextField;
    @FXML
    private Label textResult;
    @FXML
    private Label warningMessageGeneral;
    @FXML
    private void buscar(ActionEvent event) throws IOException {
        textResult.setText("");
        warningMessageGeneral.setText("");
        if(sueldoTextField.getText().equals("")){
            warningMessageGeneral.setText("No puede estar vacio el sueldo");
            return;
        }
        textResult.setText("");
        try{
            Double.parseDouble(sueldoTextField.getText().trim());
        }catch (Exception e){
            warningMessageGeneral.setText("El sueldo tiene que ser un numero");
            return;
        }

        textResult.setText("");
        if(Tecnico.tecnicosPorSueldo.containsKey(Double.parseDouble(sueldoTextField.getText()))){
            textResult.setText("\n"+Tecnico.tecnicosPorSueldo.get(Double.parseDouble(sueldoTextField.getText())));
        }
        else{
            textResult.setText("¡No se encontro el tecnico!");
        }
    }

    @FXML
    private void volver() throws IOException {
        App.setRoot("atributosTecnico");
    }
}