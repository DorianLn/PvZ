package com.epf.Persistance.Interface_DAO;

import com.epf.Persistance.Plante;
import java.util.List;
import java.util.Optional;

public interface PlanteDAO {

    // On crée une plante
    Plante create(Plante plante);

    // On récupère toutes les plantes
    List<Plante> findAll();

    // On récupère une plante par son ID
    Optional<Plante> findById(int id);

    // On met à jour une plante
    Plante update(Plante plante);

    // On supprime une plante
    void delete(Plante plante);
}

