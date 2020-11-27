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
        warningMessageGeneral.setText("");
        if(sueldoTextField.getText().equals("")){
            warningMessageGeneral.setText("No puede estar vacio el sueldo");
            return;
        }

        try{
            Double.parseDouble(sueldoTextField.getText().trim());
        }catch (Exception e){
            warningMessageGeneral.setText("El sueldo tiene que ser un numero");
            return;
        }


        if(Tecnico.tecnicosPorSueldo.containsKey(Double.parseDouble(sueldoTextField.getText()))){
            textResult.setText("\n"+Tecnico.tecnicosPorSueldo.get(Double.parseDouble(sueldoTextField.getText())));
            return;
        }
        else{
            textResult.setText("¡No se encontro el tecnico!");
            return;
        }
    }

    @FXML
    private void volver() throws IOException {
        App.setRoot("atributosTecnico");
    }
}