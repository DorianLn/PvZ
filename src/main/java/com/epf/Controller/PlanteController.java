package com.epf.Controller;

import com.epf.Persistance.Effet;
import com.epf.Persistance.Plante;
import com.epf.Service.PlanteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Classe contrôleur pour les plantes, utilisant l'architecture REST afin de pouvoir utiliser
// des requêtes http pour communiquer avec le front, via des endpoints

@RestController
@RequestMapping("/plantes")
public class PlanteController {

    private final PlanteService planteService;

    //Constructeur du contrôleur avec injection du service
    public PlanteController(PlanteService planteService) {
        this.planteService = planteService;
    }

    // Première validation, on vérifie si le format des données est valide.
    @GetMapping("/validation")
    public ResponseEntity<String> validateFormat() {
        return ResponseEntity.ok("Format valide");
    }

    // On récupère la liste des plantes et on la convertit en liste de DTOs en utilisant un stream
    @GetMapping
    public List<PlanteDTO> getAllPlantes() {
        return planteService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // On récupère une plante par son identifiant.
    @GetMapping("/{id}")
    public ResponseEntity<PlanteDTO> getPlanteById(@PathVariable int id) {
        Optional<Plante> plante = planteService.findById(id);
        return plante.map(p -> ResponseEntity.ok(convertToDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    // On crée une nouvelle plante, ajout d'une gestion d'excéption
    @PostMapping
    public ResponseEntity<Void> createPlante(@RequestBody PlanteDTO dto) {
        try {
            planteService.create(convertToEntity(dto));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // On met à jour une plante existante ou on en crée une nouvelle si elle n'existe pas, ajout d'une gestion d'exception
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePlante(@PathVariable int id, @RequestBody PlanteDTO dto) {
        try {
            dto.setId_plante(id);
            Plante plante = convertToEntity(dto);
            
            Optional<Plante> existingPlante = planteService.findById(id);
            if (!existingPlante.isPresent()) {
                planteService.create(plante);
            } else {
                planteService.update(plante);
            }
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // On supprime une plante par son identifiant.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlante(@PathVariable int id) {
        try {
            Optional<Plante> plante = planteService.findById(id);
            if (!plante.isPresent()) {
                return ResponseEntity.noContent().build();
            }
            planteService.delete(plante.get());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Création d'une méthode pour convertir une entité Plante en DTO, ce qui est essentiel pour communiquer avec le front
    private PlanteDTO convertToDTO(Plante plante) {
        PlanteDTO dto = new PlanteDTO();
        dto.setId_plante(plante.getIdPlante());
        dto.setNom(plante.getNom());
        dto.setPoint_de_vie(plante.getPointDeVie());
        dto.setAttaque_par_seconde(plante.getAttaqueParSeconde());
        dto.setDegat_attaque(plante.getDegatAttaque());
        dto.setCout(plante.getCout());
        dto.setSoleil_par_seconde(plante.getSoleilParSeconde());
        dto.setEffet(plante.getEffet().toString());
        dto.setChemin_image(plante.getCheminImage());
        return dto;
    }

    //Création d'une méthode pour convertir un dto Plante en entité, ce qui est essentiel pour communiquer avec la bdd
    private Plante convertToEntity(PlanteDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("DTO ne peut pas être nul");
        }

        // On initialise les valeurs par défaut, en cas de manque de donnée
        String nom = dto.getNom() != null ? dto.getNom() : "Plante par défaut";
        int pointDeVie = dto.getPoint_de_vie() <= 0 ? 100 : dto.getPoint_de_vie();
        double attaqueParSeconde = dto.getAttaque_par_seconde() < 0 ? 1.0 : dto.getAttaque_par_seconde();
        int degatAttaque = dto.getDegat_attaque() < 0 ? 10 : dto.getDegat_attaque();
        int cout = dto.getCout() <= 0 ? 100 : dto.getCout();
        double soleilParSeconde = dto.getSoleil_par_seconde() < 0 ? 0.0 : dto.getSoleil_par_seconde();
        String cheminImage = dto.getChemin_image() == null || dto.getChemin_image().trim().isEmpty() 
            ? "/images/plantes/default.png" 
            : dto.getChemin_image();
        

        Effet effet = Effet.NORMAL;
        if (dto.getEffet() != null) {
            try {
                effet = Effet.fromString(dto.getEffet());
            } catch (IllegalArgumentException e) {
                System.out.println("Valeur d'effet non valide, utilisation de NORMAL par défaut");
            }
        }

        return new Plante(
            dto.getId_plante(),
            nom,
            pointDeVie,
            attaqueParSeconde,
            degatAttaque,
            cout,
            soleilParSeconde,
            effet,
            cheminImage
        );
    }
}
