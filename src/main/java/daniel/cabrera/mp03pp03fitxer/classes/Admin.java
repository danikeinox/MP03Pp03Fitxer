package daniel.cabrera.mp03pp03fitxer.classes;

public class Admin {
    private String nom;
    private String contrasenya;

    public Admin(String nom, String contrasenya) {
        this.nom = nom;
        this.contrasenya = contrasenya;
    }

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
}