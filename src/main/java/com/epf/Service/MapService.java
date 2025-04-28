package com.epf.Service;

import com.epf.Persistance.MapDAOImp;
import com.epf.Persistance.Map;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Classe basée sur l'interface Service_MAPDAO, pour communiquer avec la couche Persitance
// Récupération des différentes données avec la méthode CRUD, c'est aussi ici que devrait se faire la logique métier de la map

@Service
public class MapService implements Service_MapDAO {

    private final MapDAOImp mapDAO;

    public MapService(MapDAOImp mapDAO) {
        this.mapDAO = mapDAO;
    }

    @Override
    public Map create(Map map) {
        return mapDAO.create(map);
    }

    @Override
    public Map update(Map map) {
        return mapDAO.update(map);
    }

    @Override
    public void delete(Map map) {
        mapDAO.delete(map);
    }

    @Override
    public Optional<Map> findById(int id) {
        return mapDAO.findById(id);
    }

    @Override
    public List<Map> findAll() {
        return mapDAO.findAll();
    }
}
