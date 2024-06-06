package daniel.cabrera.mp03pp03fitxer.controllers;

import daniel.cabrera.mp03pp03fitxer.classes.User;
import daniel.cabrera.mp03pp03fitxer.classes.Utils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static daniel.cabrera.mp03pp03fitxer.controllers.AdminControllerTest.codiBarrasCopiat;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest extends ApplicationTest {

    private static UserController userController;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/daniel/cabrera/mp03pp03fitxer/formularis/user.fxml"));
        Parent root = loader.load();
        userController = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
    }

    @Test
    void fitxar() {
        // Aquí debes escribir el texto en los campos de entrada necesarios para el método
        // Por ejemplo, si el método necesita un nombre de usuario y una contraseña, debes proporcionarlos
        clickOn("#codiBarresTextField").write(codiBarrasCopiat);

        // Luego, puedes simular un clic en el botón que llama al método que estás probando
        clickOn("#fitxarButton");

        // Cerrar la ventana de alerta
        push(javafx.scene.input.KeyCode.ENTER);

        // Finalmente, verifica que el método funcionó como se esperaba
        // Por ejemplo, podrías verificar que se ha creado un nuevo usuario en la base de datos
        Utils utils = new Utils();
        List<User> usuaris = utils.llegeixFitxerBinariComUser(".data/administradors.dat");
        User usuariTest = null;
        if (usuaris == null) {
            usuaris = new ArrayList<>();
        }
        for (User u : usuaris) {
            if (u.getCodiBarra().equals(codiBarrasCopiat)) {
                usuariTest = u;
            }
        }
        if (usuariTest == null) {
            fail("No s'ha trobat l'usuari creat");
        } else {
            assertTrue(true);
        }
        assertEquals(codiBarrasCopiat, usuariTest.getCodiBarra());
    }
}