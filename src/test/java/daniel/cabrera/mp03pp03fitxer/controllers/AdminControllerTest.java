package daniel.cabrera.mp03pp03fitxer.controllers;

import daniel.cabrera.mp03pp03fitxer.classes.User;
import daniel.cabrera.mp03pp03fitxer.classes.Utils;
import daniel.cabrera.mp03pp03fitxer.controllers.AdminController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdminControllerTest extends ApplicationTest {

    private static AdminController adminController;

    public static String codiBarrasCopiat;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/daniel/cabrera/mp03pp03fitxer/formularis/admin.fxml"));
        Parent root = loader.load();
        adminController = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
    }

    @Test
    public void testCrearUsuari() throws IOException, UnsupportedFlavorException {
        // Aquí debes escribir el texto en los campos de entrada necesarios para el método
        // Por ejemplo, si el método necesita un nombre de usuario y una contraseña, debes proporcionarlos
        clickOn("#usuariNouUsuariTextField").write("testUser");
        clickOn("#nomNouUsuariTextField").write("testName");
        clickOn("#cognomsNouUsuariTextField").write("testSurname");
        clickOn("#dniNouUsuariTextField").write("testDNI");
        clickOn("#contrasenyaNouUsuariPasswordField").write("testPassword");
        clickOn("#emailNouUsuariTextField").write("testEmail");

        // Luego, puedes simular un clic en el botón que llama al método que estás probando
        clickOn("#crearUsuariButton", MouseButton.PRIMARY);

        // Cerrar la ventana de alerta
        push(KeyCode.ENTER);

        // Finalmente, verifica que el método funcionó como se esperaba
        // Por ejemplo, podrías verificar que se ha creado un nuevo usuario en la base de datos

        clickOn("#botoCopiar", MouseButton.PRIMARY);
        // Obtenir el codi de barres copiat al portapapers
        codiBarrasCopiat = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().getData(java.awt.datatransfer.DataFlavor.stringFlavor).toString();
        System.out.println("Usuari test creat correctament. Codi de barras: " + codiBarrasCopiat);

        // verificar que el usuario se ha creado correctamente comprobando el código de barras

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

    @Test
    public void testIniciarSessio() throws IOException, UnsupportedFlavorException {
        // Similar al método anterior, escribe el texto en los campos de entrada y simula un clic en el botón
        clickOn("#nomUsuariTextField").write("testUser");
        clickOn("#contrasenyaPasswordField").write("testPassword");
        clickOn("#iniciarSessio", MouseButton.PRIMARY);

        // Verifica que el método funcionó como se esperaba
        System.out.println(AdminController.getNomUsuari());
        if (AdminController.getNomUsuari().equals("testUser")) {
            assertTrue(true);
        } else {
            fail("No s'ha pogut iniciar sessió amb l'usuari testUser");
        }

    }
}