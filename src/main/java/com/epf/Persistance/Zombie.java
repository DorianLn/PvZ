package com.epf.Persistance;

public class Zombie {
    private Integer idZombie;
    private String nom;
    private int pointDeVie;
    private double attaqueParSeconde;
    private int degatAttaque;
    private double vitesseDeplacement;
    private String cheminImage;
    private Integer idMap;

    // Premier constructeur pour la création
    public Zombie(String nom, int pointDeVie, double attaqueParSeconde, int degatAttaque, double vitesseDeplacement, String cheminImage, Integer idMap) {
        this.nom = nom;
        this.pointDeVie = pointDeVie;
        this.attaqueParSeconde = attaqueParSeconde;
        this.degatAttaque = degatAttaque;
        this.vitesseDeplacement = vitesseDeplacement;
        this.cheminImage = cheminImage;
        this.idMap = idMap;
    }

    // Deuxième constructeur pour les opérations (mise à jour..)
    public Zombie(Integer idZombie, String nom, int pointDeVie, double attaqueParSeconde, int degatAttaque, double vitesseDeplacement, String cheminImage, Integer idMap) {
        this.idZombie = idZombie;
        this.nom = nom;
        this.pointDeVie = pointDeVie;
        this.attaqueParSeconde = attaqueParSeconde;
        this.degatAttaque = degatAttaque;
        this.vitesseDeplacement = vitesseDeplacement;
        this.cheminImage = cheminImage;
        this.idMap = idMap;
    }

    public Integer getIdZombie() {
        return idZombie;
    }

    public void setIdZombie(Integer idZombie) {
        this.idZombie = idZombie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPointDeVie() {
        return pointDeVie;
    }

    public void setPointDeVie(int pointDeVie) {
        this.pointDeVie = pointDeVie;
    }

    public double getAttaqueParSeconde() {
        return attaqueParSeconde;
    }

    public void setAttaqueParSeconde(double attaqueParSeconde) {
        this.attaqueParSeconde = attaqueParSeconde;
    }

    public int getDegatAttaque() {
        return degatAttaque;
    }

    public void setDegatAttaque(int degatAttaque) {
        this.degatAttaque = degatAttaque;
    }

    public double getVitesseDeplacement() {
        return vitesseDeplacement;
    }

    public void setVitesseDeplacement(double vitesseDeplacement) {
        this.vitesseDeplacement = vitesseDeplacement;
    }

    public String getCheminImage() {
        return cheminImage;
    }

    public void setCheminImage(String cheminImage) {
        this.cheminImage = cheminImage;
    }

    public Integer getIdMap() {
        return idMap;
    }

    public void setIdMap(Integer idMap) {
        this.idMap = idMap;
    }

    @Override
    public String toString() {
        return "Zombie [idZombie=" + idZombie + ", nom=" + nom + ", pointDeVie=" + pointDeVie +
                ", attaqueParSeconde=" + attaqueParSeconde + ", degatAttaque=" + degatAttaque +
                ", vitesseDeplacement=" + vitesseDeplacement + ", cheminImage=" + cheminImage +
                ", idMap=" + idMap + "]";
    }
}
