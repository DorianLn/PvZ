package com.epf.Persistance;

public enum Effet {
    NORMAL, SLOW_LOW, SLOW_MEDIUM, SLOW_STOP;

    public static Effet fromString(String text) {
        if (text == null) {
            return NORMAL;
        }
        
        // On va convertir le texte en format compatible avec l'enum
        String enumFormat = text.toUpperCase().replace(" ", "_");
        
        try {
            return valueOf(enumFormat);
        } catch (IllegalArgumentException e) {
            return NORMAL;
        }
    }
    
    @Override
    public String toString() {
        // Et on convertit l'enum en format compatible avec la BDD
        return this.name().toLowerCase().replace("_", " ");
    }
}
