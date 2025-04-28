package com.epf.Service;

import com.epf.Persistance.Zombie;
import java.util.List;
import java.util.Optional;

public interface Service_ZombieDAO {
    Zombie create(Zombie zombie);
    Zombie update(Zombie zombie);
    void delete(Zombie zombie);
    Optional<Zombie> findById(int id);
    List<Zombie> findAll();
    List<Zombie> findByMapId(int mapId);
}
