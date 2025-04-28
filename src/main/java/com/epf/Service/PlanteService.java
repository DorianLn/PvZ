package com.epf.Service;

import com.epf.Persistance.PlanteDAOImp;
import com.epf.Persistance.Plante;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Classe basée sur l'interface Service_PlanteDAO, pour communiquer avec la couche Persitance
// Récupération des différentes données avec la méthode CRUD, c'est aussi ici que devrait se faire la logique métier des plantes

@Service
public class PlanteService implements Service_PlanteDAO {

    private final PlanteDAOImp planteDAO;

    public PlanteService(PlanteDAOImp planteDAO) {
        this.planteDAO = planteDAO;
    }

    @Override
    public Plante create(Plante plante) {
        return planteDAO.create(plante);
    }

    @Override
    public Plante update(Plante plante) {
        return planteDAO.update(plante);
    }

    @Override
    public void delete(Plante plante) {
        planteDAO.delete(plante);
    }

    @Override
    public Optional<Plante> findById(int id) {
        return planteDAO.findById(id);
    }

    @Override
    public List<Plante> findAll() {
        return planteDAO.findAll();
    }
}
