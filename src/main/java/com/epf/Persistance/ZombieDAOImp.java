package com.epf.Persistance;

import com.epf.Persistance.Interface_DAO.ZombieDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;


// Classe pour les DAO de l'entité Zombie implémentée à partir de l'interface ZombieDAO
// Utilisation de la méthode CRUD avec la bdd et JdbcTemplate pour les différentes opérations de bdd
@Repository
public class ZombieDAOImp implements ZombieDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public ZombieDAOImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // On crée un nouveau zombie dans la base de données, tout en vérifiant l'existence de la map associée si un id_map est fourni.
    // Implémentation d'une gestion d'exception
    @Override
    public Zombie create(Zombie zombie) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO Zombie (nom, point_de_vie, attaque_par_seconde, degat_attaque, vitesse_de_deplacement, chemin_image, id_map) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            if (zombie.getIdMap() != null) {
                String checkMapSql = "SELECT COUNT(*) FROM Map WHERE id_map = ?";
                Integer count = jdbcTemplate.queryForObject(checkMapSql, Integer.class, zombie.getIdMap());
                if (count == null || count == 0) {
                    zombie.setIdMap(null);
                }
            }

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, zombie.getNom());
                ps.setInt(2, zombie.getPointDeVie());
                ps.setDouble(3, zombie.getAttaqueParSeconde());
                ps.setInt(4, zombie.getDegatAttaque());
                ps.setDouble(5, zombie.getVitesseDeplacement());
                ps.setString(6, zombie.getCheminImage());
                if (zombie.getIdMap() != null) {
                    ps.setInt(7, zombie.getIdMap());
                } else {
                    ps.setNull(7, java.sql.Types.INTEGER);
                }
                return ps;
            }, keyHolder);

            Number key = keyHolder.getKey();
            if (key != null) {
                zombie.setIdZombie(key.intValue());
            } else {
                throw new RuntimeException("Failed to retrieve generated ID");
            }
            return zombie;
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("foreign key constraint")) {
                message = "La map spécifiée n'existe pas dans la base de données";
            }
            throw new RuntimeException("Error creating zombie: " + message, e);
        }
    }

    // on récupère tous les zombies de la base de données.
    @Override
    public List<Zombie> findAll() {
        String sql = "SELECT * FROM Zombie";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Zombie(
            rs.getInt("id_zombie"),
            rs.getString("nom"),
            rs.getInt("point_de_vie"),
            rs.getDouble("attaque_par_seconde"),
            rs.getInt("degat_attaque"),
            rs.getDouble("vitesse_de_deplacement"),
            rs.getString("chemin_image"),
            rs.getObject("id_map") != null ? rs.getInt("id_map") : null
        ));
    }

    // on récupère un zombie grâce à son ID.
    @Override
    public Optional<Zombie> findById(int id) {
        try {
            String sql = "SELECT * FROM Zombie WHERE id_zombie = ?";
            Zombie zombie = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Zombie(
                rs.getInt("id_zombie"),
                rs.getString("nom"),
                rs.getInt("point_de_vie"),
                rs.getDouble("attaque_par_seconde"),
                rs.getInt("degat_attque"),
                rs.getDouble("vitesse_de_deplacement"),
                rs.getString("chemin_image"),
                rs.getObject("id_map") != null ? rs.getInt("id_map") : null
            ), id);
            return Optional.ofNullable(zombie);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // On met à jour un zombie existant
    @Override
    public Zombie update(Zombie zombie) {
        String sql = "UPDATE Zombie SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, degat_attaque = ?, vitesse_de_deplacement = ?, chemin_image = ?, id_map = ? WHERE id_zombie = ?";
        int rowsAffected = jdbcTemplate.update(sql, 
            zombie.getNom(), 
            zombie.getPointDeVie(), 
            zombie.getAttaqueParSeconde(),
            zombie.getDegatAttaque(), 
            zombie.getVitesseDeplacement(), 
            zombie.getCheminImage(), 
            zombie.getIdMap(), 
            zombie.getIdZombie()
        );
        if (rowsAffected == 0) {
            throw new RuntimeException("Zombie not found with id: " + zombie.getIdZombie());
        }
        return zombie;
    }

    // On supprime un zombie de la base de données.
    @Override
    public void delete(Zombie zombie) {
        String sql = "DELETE FROM Zombie WHERE id_zombie = ?";
        int rowsAffected = jdbcTemplate.update(sql, zombie.getIdZombie());
        if (rowsAffected == 0) {
            throw new RuntimeException("Zombie not found with id: " + zombie.getIdZombie());
        }
    }

    // On récupère tous les zombies associés à une map spécifique.
    @Override
    public List<Zombie> findByMapId(int mapId) {
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
