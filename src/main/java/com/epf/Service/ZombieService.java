package com.epf.Service;

import com.epf.Persistance.ZombieDAOImp;
import com.epf.Persistance.Zombie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Classe basée sur l'interface Service_ZombieDAO, pour communiquer avec la couche Persitance
// Récupération des différentes données avec la méthode CRUD, c'est aussi ici que devrait se faire la logique métier des zombies

@Service
public class ZombieService implements Service_ZombieDAO {

    private final ZombieDAOImp zombieDAO;
    private final JdbcTemplate jdbcTemplate;

    public ZombieService(ZombieDAOImp zombieDAO, JdbcTemplate jdbcTemplate) {
        this.zombieDAO = zombieDAO;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Méthode pour vérifier si la map associé au zombie existe
    private boolean validateMap(Integer mapId) {
        if (mapId == null) {
            return true;
        }
        String checkMapSql = "SELECT COUNT(*) FROM Map WHERE id_map = ?";
        Integer count = jdbcTemplate.queryForObject(checkMapSql, Integer.class, mapId);
        return count != null && count > 0;
    }

    @Override
    public Zombie create(Zombie zombie) {
        if (zombie.getIdMap() != null && !validateMap(zombie.getIdMap())) {
            zombie.setIdMap(null);
        }
        return zombieDAO.create(zombie);
    }

    @Override
    public Zombie update(Zombie zombie) {
        if (zombie.getIdMap() != null && !validateMap(zombie.getIdMap())) {
            Optional<Zombie> existing = zombieDAO.findById(zombie.getIdZombie());
            if (existing.isPresent()) {
                zombie.setIdMap(existing.get().getIdMap());
            } else {
                zombie.setIdMap(null);
            }
        }
        return zombieDAO.update(zombie);
    }

    @Override
    public void delete(Zombie zombie) {
        zombieDAO.delete(zombie);
    }

    @Override
    public Optional<Zombie> findById(int id) {
        return zombieDAO.findById(id);
    }

    @Override
    public List<Zombie> findAll() {
        return zombieDAO.findAll();
    }

    @Override
    public List<Zombie> findByMapId(int mapId) {
        return zombieDAO.findByMapId(mapId);
    }
}

