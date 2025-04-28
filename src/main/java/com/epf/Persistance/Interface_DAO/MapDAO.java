package com.epf.Persistance.Interface_DAO;


import com.epf.Persistance.Map;
import java.util.List;
import java.util.Optional;
import com.epf.Persistance.Zombie;

public interface MapDAO {

    // On crée une carte
    Map create(Map map);

    // On récupère toutes les cartes
    List<Map> findAll();

    // On récupère une carte par son ID
    Optional<Map> findById(int id);

    // On met à jour une carte
    Map update(Map map);

    // On supprime une carte
    void delete(Map map);

    // On récupère tous les zombies d'une carte
    List<Zombie> findZombiesByMap(int mapId);
}

