package trabajo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ingresarController {
    @FXML
    private Label warning;

    @FXML
    private TextField usuarioTextField;

    @FXML
    private PasswordField contraTextField;

    public Button closeButton;
    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saludar(ActionEvent event) throws IOException { //Como será un método para la acción de presionar el botón, es ActionEvent. Si fuera un click es MouseEvent, si fuera presionar una tecla sería KeyEvent.
        warning.setVisible(false);

        String id = usuarioTextField.getText().trim();
        String contra = contraTextField.getText();
        if (id.isEmpty() || contra.isEmpty()) {
            warning.setText("MENSAJE: No puede haber campos vacíos");
            warning.setVisible(true);
            return;
        }

        boolean encontrado = false;
        for (Usuario usu : App.usuarios) { //Evalua si el documento o correo ingresados coincide con la contrasenna
            if (usu.Documento.equals(id) || usu.Correo.equals(id)){
                if (usu.Contrasenna.equals(contra)){
                    encontrado = true;
                    break;
                }
            }
        }
        if (!encontrado){
            warning.setText("ERROR: los datos suministrados no coinciden con ninguno de los usuarios registrados");
            warning.setVisible(true);
        } else {
            App.setRoot("menuPrincipal");
        }

    }

}
