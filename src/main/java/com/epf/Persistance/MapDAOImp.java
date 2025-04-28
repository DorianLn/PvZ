package com.epf.Persistance;

import com.epf.Persistance.Interface_DAO.MapDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

//Classe pour les DAO de l'entité Map implémentée à partir de l'interface MapDAO
// Utilisation de la méthode CRUD avec la bdd et JdbcTemplate pour les différentes opérations de bdd
@Repository
public class MapDAOImp implements MapDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public MapDAOImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //On crée une nouvelle map dans la base de données
    @Override
    public Map create(Map map) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO Map (ligne, colonne, chemin_image) VALUES (?, ?, ?)";
        
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, map.getLigne());
            ps.setInt(2, map.getColonne());
            ps.setString(3, map.getCheminImage());
            return ps;
        }, keyHolder);

        if (rowsAffected > 0 && keyHolder.getKey() != null) {
            map.setIdMap(keyHolder.getKey().intValue());
        } else {
            throw new RuntimeException("Failed to create map: no ID was generated");
        }
        return map;
    }

    // On récupère toutes les maps de la base de données.
    @Override
    public List<Map> findAll() {
        String sql = "SELECT * FROM Map";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Map(
            rs.getInt("id_map"),
            rs.getInt("ligne"),
            rs.getInt("colonne"),
            rs.getString("chemin_image")
        ));
    }

    //On récupère une map par son ID.

    @Override
    public Optional<Map> findById(int id) {
        try {
            String sql = "SELECT * FROM Map WHERE id_map = ?";
            Map map = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Map(
                rs.getInt("id_map"),
                rs.getInt("ligne"),
                rs.getInt("colonne"),
                rs.getString("chemin_image")
            ), id);
            return Optional.ofNullable(map);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    //  on met à jour une map existante
    @Override
    public Map update(Map map) {
        String sql = "UPDATE Map SET ligne = ?, colonne = ?, chemin_image = ? WHERE id_map = ?";
        try {
            System.out.println("Updating map with id: " + map.getIdMap());
            System.out.println("ligne: " + map.getLigne());
            System.out.println("colonne: " + map.getColonne());
            System.out.println("chemin_image: " + map.getCheminImage());
            
            int rowsAffected = jdbcTemplate.update(sql, 
                map.getLigne(),
                map.getColonne(),
                map.getCheminImage(),
                map.getIdMap()
            );
            
            System.out.println("Rows affected: " + rowsAffected);
            
            if (rowsAffected == 0) {
                throw new RuntimeException("Map not found with id: " + map.getIdMap());
            }
            return map;
        } catch (Exception e) {
            throw e;
        }
    }

    // On supprime une map de la base de données
    @Override
    public void delete(Map map) {
        String sql = "DELETE FROM Map WHERE id_map = ?";
        int rowsAffected = jdbcTemplate.update(sql, map.getIdMap());
        if (rowsAffected == 0) {
            throw new RuntimeException("Map not found with id: " + map.getIdMap());
        }
    }

    @Override
    public List<Zombie> findZombiesByMap(int mapId) {
        String sql = "SELECT * FROM Zombie WHERE id_map = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Zombie(
            rs.getInt("id_zombie"),
            rs.getString("nom"),
            rs.getInt("point_de_vie"),
            rs.getDouble("attaque_par_seconde"),
            rs.getInt("degat_attaque"),
            rs.getDouble("vitesse_de_deplacement"),
            rs.getString("chemin_image"),
            rs.getInt("id_map")
        ), mapId);
    }
}
