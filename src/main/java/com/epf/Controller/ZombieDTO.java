package com.epf.Controller;

public class ZombieDTO {
    private Integer id_zombie;
    private String nom;
    private int point_de_vie;
    private double attaque_par_seconde;
    private int degat_attaque;
    private double vitesse_de_deplacement;
    private String chemin_image;
    private Integer id_map;

    public ZombieDTO() {}

    public ZombieDTO(Integer id_zombie, String nom, int point_de_vie, double attaque_par_seconde,
                    int degat_attaque, double vitesse_de_deplacement, String chemin_image, Integer id_map) {
        this.id_zombie = id_zombie;
        this.nom = nom;
        this.point_de_vie = point_de_vie;
        this.attaque_par_seconde = attaque_par_seconde;
        this.degat_attaque = degat_attaque;
        this.vitesse_de_deplacement = vitesse_de_deplacement;
        this.chemin_image = chemin_image;
        this.id_map = id_map;
    }

    // Getters
    public Integer getId_zombie() { return id_zombie; }
    public String getNom() { return nom; }
    public int getPoint_de_vie() { return point_de_vie; }
    public double getAttaque_par_seconde() { return attaque_par_seconde; }
    public int getDegat_attaque() { return degat_attaque; }
    public double getVitesse_de_deplacement() { return vitesse_de_deplacement; }
    public String getChemin_image() { return chemin_image; }
    public Integer getId_map() { return id_map; }

    // Setters
    public void setId_zombie(Integer id_zombie) { this.id_zombie = id_zombie; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPoint_de_vie(int point_de_vie) { this.point_de_vie = point_de_vie; }
    public void setAttaque_par_seconde(double attaque_par_seconde) { this.attaque_par_seconde = attaque_par_seconde; }
    public void setDegat_attaque(int degat_attaque) { this.degat_attaque = degat_attaque; }
    public void setVitesse_de_deplacement(double vitesse_de_deplacement) { this.vitesse_de_deplacement = vitesse_de_deplacement; }
    public void setChemin_image(String chemin_image) { this.chemin_image = chemin_image; }
    public void setId_map(Integer id_map) { this.id_map = id_map; }

    @Override
    public String toString() {
        return "ZombieDTO{" +
                "id_zombie=" + id_zombie +
                ", nom='" + nom + '\'' +
                ", point_de_vie=" + point_de_vie +
                ", attaque_par_seconde=" + attaque_par_seconde +
                ", degat_attaque=" + degat_attaque +
                ", vitesse_de_deplacement=" + vitesse_de_deplacement +
                ", chemin_image='" + chemin_image + '\'' +
                ", id_map=" + id_map +
                '}';
    }
}
