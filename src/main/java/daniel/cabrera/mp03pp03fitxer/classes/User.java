package daniel.cabrera.mp03pp03fitxer.classes;

import java.io.Serializable;

public class User extends Persona implements Serializable {

    // <editor-fold desc="Propietats">

    // Explicitly declare serialVersionUID
    private static final long serialVersionUID = 1L;

    //</editor-fold>

    // <editor-fold desc="Constructors">

    // No-argument constructor
    public User() {}

    // Constructor con parámetros
    public User(String nombre, String apellidos, String codigoBarra, String DNI, String contrasena, String email, String usuario, boolean admin, boolean entrada, String fechaHora) {
        super(nombre, apellidos, codigoBarra, DNI, contrasena, email, usuario, admin, entrada, fechaHora);
    }

    //</editor-fold>
    
}