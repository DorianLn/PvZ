package com.epf.Persistance;

import com.epf.Persistance.Interface_DAO.PlanteDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

// Classe pour les DAO de l'entité Plante implémentée à partir de l'interface PlanteDAO
// Utilisation de la méthode CRUD avec la bdd et JdbcTemplate pour les différentes opérations de bdd

@Repository
public class PlanteDAOImp implements PlanteDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public PlanteDAOImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // On crée une nouvelle plante dans la base de données.
    @Override
    public Plante create(Plante plante) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO Plante (nom, point_de_vie, attaque_par_seconde, degat_attaque, cout, soleil_par_seconde, effet, chemin_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, plante.getNom());
            ps.setInt(2, plante.getPointDeVie());
            ps.setDouble(3, plante.getAttaqueParSeconde());
            ps.setInt(4, plante.getDegatAttaque());
            ps.setInt(5, plante.getCout());
            ps.setDouble(6, plante.getSoleilParSeconde());
            ps.setString(7, plante.getEffet().toString());
            ps.setString(8, plante.getCheminImage());
            return ps;
        }, keyHolder);

        plante.setIdPlante(keyHolder.getKey().intValue());
        return plante;
    }

    // On récupère toutes les plantes de la base de données
    @Override
    public List<Plante> findAll() {
        String sql = "SELECT * FROM Plante";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            String effetString = rs.getString("effet");
            Effet effet = Effet.fromString(effetString);
            return new Plante(
                rs.getInt("id_plante"),
                rs.getString("nom"),
                rs.getInt("point_de_vie"),
                rs.getDouble("attaque_par_seconde"),
                rs.getInt("degat_attaque"),
                rs.getInt("cout"),
                rs.getDouble("soleil_par_seconde"),
                effet,
                rs.getString("chemin_image")
            );
        });
    }

    // On récupère une plante par son ID.

    @Override
    public Optional<Plante> findById(int id) {
        try {
            String sql = "SELECT * FROM Plante WHERE id_plante = ?";
            Plante plante = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                String effetString = rs.getString("effet");
                Effet effet = Effet.fromString(effetString);
                return new Plante(
                    rs.getInt("id_plante"),
                    rs.getString("nom"),
                    rs.getInt("point_de_vie"),
                    rs.getDouble("attaque_par_seconde"),
                    rs.getInt("degat_attaque"),
                    rs.getInt("cout"),
                    rs.getDouble("soleil_par_seconde"),
                    effet,
                    rs.getString("chemin_image")
                );
            }, id);
            return Optional.ofNullable(plante);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // On met à jour une plante existante.
    @Override
    public Plante update(Plante plante) {
        String sql = "UPDATE Plante SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, degat_attaque = ?, cout = ?, soleil_par_seconde = ?, effet = ?, chemin_image = ? WHERE id_plante = ?";
        int rowsAffected = jdbcTemplate.update(sql, 
            plante.getNom(), 
            plante.getPointDeVie(), 
            plante.getAttaqueParSeconde(), 
            plante.getDegatAttaque(),
            plante.getCout(), 
            plante.getSoleilParSeconde(), 
            plante.getEffet().toString(), 
            plante.getCheminImage(), 
            plante.getIdPlante()
        );
        if (rowsAffected == 0) {
            throw new RuntimeException("Plante not found with id: " + plante.getIdPlante());
        }
        return plante;
    }

    // On supprime une plante de la base de données.
    @Override
    public void delete(Plante plante) {
        String sql = "DELETE FROM Plante WHERE id_plante = ?";
        int rowsAffected = jdbcTemplate.update(sql, plante.getIdPlante());
        if (rowsAffected == 0) {
            throw new RuntimeException("Plante not found with id: " + plante.getIdPlante());
        }
    }
}