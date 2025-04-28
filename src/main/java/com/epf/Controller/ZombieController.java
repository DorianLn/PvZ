package com.epf.Controller;

import com.epf.Persistance.Zombie;
import com.epf.Service.ZombieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


// Classe contrôleur pour les zombies, utilisant l'architecture REST afin de pouvoir utiliser
// des requêtes http pour communiquer avec le front, via des endpoints

@RestController
@RequestMapping("/zombies")
public class ZombieController {

    private final ZombieService zombieService;

    //Constructeur du contrôleur avec injection du service.
    @Autowired
    public ZombieController(ZombieService zombieService) {
        this.zombieService = zombieService;
    }

    // Première validation, on vérifie si le format des données est valide.
    @GetMapping("/validation")
    public ResponseEntity<String> validateFormat() {
        return ResponseEntity.ok("Format valide");
    }

    // On récupère la liste des zombies et on la convertit en liste de DTOs en utilisant un stream
    @GetMapping
    public List<ZombieDTO> getAllZombies() {
        return zombieService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // On récupère un zombie par son identifiant.
    @GetMapping("/{id}")
    public ResponseEntity<ZombieDTO> getZombieById(@PathVariable int id) {
        Optional<Zombie> zombie = zombieService.findById(id);
        return zombie.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // On crée un nouveau zombie, ajout d'une gestion d'exception
    @PostMapping
    public ResponseEntity<Void> createZombie(@RequestBody ZombieDTO dto) {
        try {
            Zombie zombie = convertToEntity(dto);
            zombieService.create(zombie);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // On met à jour un zombie existant ou en crée un nouveau s'il n'existe pas, ajout d'une gestion d'exception
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateZombie(@PathVariable int id, @RequestBody ZombieDTO dto) {
        try {
            dto.setId_zombie(id);
            Zombie zombie = convertToEntity(dto);
            
            Optional<Zombie> existingZombie = zombieService.findById(id);
            if (!existingZombie.isPresent()) {
                zombieService.create(zombie);
            } else {
                zombieService.update(zombie);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // On supprime un zombie par son identifiant.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZombie(@PathVariable int id) {
        try {
            Optional<Zombie> zombie = zombieService.findById(id);
            if (!zombie.isPresent()) {
                return ResponseEntity.noContent().build();
            }
            zombieService.delete(zombie.get());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Création d'une méthode pour convertir une entité Zombie en DTO, ce qui est essentiel pour communiquer avec le front
    private ZombieDTO convertToDTO(Zombie zombie) {
        ZombieDTO dto = new ZombieDTO();
        dto.setId_zombie(zombie.getIdZombie());
        dto.setNom(zombie.getNom());
        dto.setPoint_de_vie(zombie.getPointDeVie());
        dto.setAttaque_par_seconde(zombie.getAttaqueParSeconde());
        dto.setDegat_attaque(zombie.getDegatAttaque());
        dto.setVitesse_de_deplacement(zombie.getVitesseDeplacement());
        dto.setChemin_image(zombie.getCheminImage());
        dto.setId_map(zombie.getIdMap());
        return dto;
    }

    //Création d'une méthode pour convertir un dto Zombie en entité, ce qui est essentiel pour communiquer avec la bdd
    private Zombie convertToEntity(ZombieDTO dto) {
        if (dto.getId_zombie() == null) {
            return new Zombie(
                dto.getNom(),
                dto.getPoint_de_vie(),
                dto.getAttaque_par_seconde(),
                dto.getDegat_attaque(),
                dto.getVitesse_de_deplacement(),
                dto.getChemin_image(),
                dto.getId_map()
            );
        } else {
            return new Zombie(
                dto.getId_zombie(),
                dto.getNom(),
                dto.getPoint_de_vie(),
                dto.getAttaque_par_seconde(),
                dto.getDegat_attaque(),
                dto.getVitesse_de_deplacement(),
                dto.getChemin_image(),
                dto.getId_map()
            );
        }
    }
}
