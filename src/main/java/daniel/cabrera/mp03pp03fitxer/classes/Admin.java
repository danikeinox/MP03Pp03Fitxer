package daniel.cabrera.mp03pp03fitxer.classes;

public class Admin {

    // <editor-fold desc="Propietats">
    private String nom;
    private String contrasenya;

    //</editor-fold>

    // <editor-fold desc="Constructors">

    // No-argument constructor
    public Admin() {

    }

    public Admin(String nom, String contrasenya) {
        this.nom = nom;
        this.contrasenya = contrasenya;
    }

    //</editor-fold>

    // <editor-fold desc="Getters i setters">

    // Getters i setters

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    //</editor-fold>

}