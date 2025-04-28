package com.epf.Controller;

import com.epf.Persistance.Map;
import com.epf.Persistance.Zombie;
import com.epf.Service.MapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Classe contrôleur pour les maps, utilisant l'architecture REST afin de pouvoir utiliser
// des requêtes http pour communiquer avec le front, via des endpoints

@RestController
@RequestMapping("/maps")
public class MapController {

    private final MapService mapService;

    //Constructeur du contrôleur avec injection du service
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    // Première validation, on vérifie si le format des données est valide.
    @GetMapping("/validation")
    public ResponseEntity<String> validateFormat() {
        return ResponseEntity.ok("Format valide");
    }

    // On récupère la liste des maps et on la convertit en liste de DTOs en utilisant un stream
    @GetMapping
    public List<MapDTO> getAllMaps() {
        return mapService.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // On récupère une map par son identifiant.
    @GetMapping("/{id}")
    public ResponseEntity<MapDTO> getMapById(@PathVariable int id) {
        Optional<Map> map = mapService.findById(id);
        return map.map(value -> ResponseEntity.ok(convertToDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    // On crée une nouvelle map, ajout d'une gestion d'excéption
    @PostMapping
    public ResponseEntity<Void> createMap(@RequestBody MapDTO dto) {
        try {
            mapService.create(convertToEntity(dto));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // On met à jour une map existante ou on en crée une nouvelle si elle n'existe pas, ajout d'une gestion d'exception
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMap(@PathVariable int id, @RequestBody MapDTO dto) {
        try {
            dto.setId_map(id);
            Map map = convertToEntity(dto);
            
            Optional<Map> existingMap = mapService.findById(id);
            if (!existingMap.isPresent()) {
                mapService.create(map);
            } else {
                mapService.update(map);
            }
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // On supprime une map par son identifiant et on vérifie les contraintes de clé étrangère avec les zombies.

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMap(@PathVariable int id) {
        try {
            Optional<Map> map = mapService.findById(id);
            if (!map.isPresent()) {
                return ResponseEntity.noContent().build();
            }

            try {
                mapService.delete(map.get());
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                if (e.getMessage() != null && e.getMessage().contains("foreign key constraint")) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Création d'une méthode pour convertir une entité Map en DTO, ce qui est essentiel pour communiquer avec le front
    private MapDTO convertToDTO(Map map) {
        MapDTO dto = new MapDTO();
        dto.setId_map(map.getIdMap());
        dto.setLigne(map.getLigne());
        dto.setColonne(map.getColonne());
        dto.setChemin_image(map.getCheminImage());
        return dto;
    }

    //Création d'une méthode pour convertir un dto Map en entité, ce qui est essentiel pour communiquer avec la bdd
    private Map convertToEntity(MapDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("DTO ne peut pas être nul");
        }
        
        int ligne = dto.getLigne() <= 0 ? 5 : dto.getLigne();
        int colonne = dto.getColonne() <= 0 ? 9 : dto.getColonne();
        String cheminImage = dto.getChemin_image() == null || dto.getChemin_image().trim().isEmpty() 
            ? "/images/maps/default.png" 
            : dto.getChemin_image();
        
        return new Map(
            dto.getId_map(),
            ligne,
            colonne,
            cheminImage
        );
    }
}
