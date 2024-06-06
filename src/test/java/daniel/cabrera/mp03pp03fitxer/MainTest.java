package daniel.cabrera.mp03pp03fitxer;

import daniel.Cabrera.Fitxers.Fitxers;
import daniel.cabrera.mp03pp03fitxer.controllers.AdminControllerTest;
import daniel.cabrera.mp03pp03fitxer.controllers.UserControllerTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MainTest {

    Fitxers f = new Fitxers();

    @Test
    void eliminarFitxers() throws IOException {
        if (f.existeix(".data/administradors.dat")) {
            f.eliminarFitxerDirectori(".data/administradors.dat");
        }
        if (f.existeix(".data/usuaris.dat")) {
            f.eliminarFitxerDirectori(".data/usuaris.dat");
        }
        if (!f.existeix(".data/administradors.dat")) {
            assert true;
        }
        if (!f.existeix(".data/usuaris.dat")) {
            assert true;
        }
    }

    @Nested
    class UserControllerTests extends UserControllerTest {
        // Aquí se heredan todos los tests de UserControllerTest
    }
    @Nested
    class AdminControllerTests extends AdminControllerTest {
        // Aquí se heredan todos los tests de AdminControllerTest

    }
}