package com.epf.Persistance.Interface_DAO;
import com.epf.Persistance.Zombie;
import java.util.List;
import java.util.Optional;

public interface ZombieDAO {

    // On crée un zombie
    Zombie create(Zombie zombie);

    // On récupère tous les zombies
    List<Zombie> findAll();

    // On récupère un zombie par son ID
    Optional<Zombie> findById(int id);

    // On met à jour un zombie
    Zombie update(Zombie zombie);

    // On supprime un zombie
    void delete(Zombie zombie);

    // On récupère tous les zombies associés à une carte
    List<Zombie> findByMapId(int mapId);
}

