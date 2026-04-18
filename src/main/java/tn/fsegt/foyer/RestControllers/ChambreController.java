package com.hebergement.controller;

import com.hebergement.entity.Chambre;
import com.hebergement.service.ChambreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chambres")
@CrossOrigin(origins = "*")
public class ChambreController {

    @Autowired
    private ChambreService chambreService;

    // GET /api/chambres
    @GetMapping
    public ResponseEntity<List<Chambre>> getAllChambres() {
        return ResponseEntity.ok(chambreService.getAllChambres());
    }

    // GET /api/chambres/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Chambre> getChambreById(@PathVariable Long id) {
        return ResponseEntity.ok(chambreService.getChambreById(id));
    }

    // POST /api/chambres
    @PostMapping
    public ResponseEntity<Chambre> createChambre(@RequestBody Chambre chambre) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chambreService.createChambre(chambre));
    }

    // PUT /api/chambres/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Chambre> updateChambre(@PathVariable Long id, @RequestBody Chambre chambre) {
        return ResponseEntity.ok(chambreService.updateChambre(id, chambre));
    }

    // DELETE /api/chambres/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChambre(@PathVariable Long id) {
        chambreService.deleteChambre(id);
        return ResponseEntity.noContent().build();
    }

    // PUT /api/chambres/affecter-chambre?chambreId=X&blocId=Y
    @PutMapping("/affecter-chambre")
    public ResponseEntity<Chambre> affecterChambre(
            @RequestParam Long chambreId,
            @RequestParam Long blocId) {
        return ResponseEntity.ok(chambreService.affecterChambreABloc(chambreId, blocId));
    }

    // GET /api/chambres/non-reservees
    @GetMapping("/non-reservees")
    public ResponseEntity<List<Chambre>> getChambresNonReservees() {
        return ResponseEntity.ok(chambreService.getChambresLibres());
    }

    // GET /api/chambres/bloc/{blocId}
    @GetMapping("/bloc/{blocId}")
    public ResponseEntity<List<Chambre>> getChambresByBloc(@PathVariable Long blocId) {
        return ResponseEntity.ok(chambreService.getChambresByBloc(blocId));
    }

    // GET /api/chambres/stats/par-type
    @GetMapping("/stats/par-type")
    public ResponseEntity<Map<String, Long>> getNombreParType() {
        return ResponseEntity.ok(chambreService.getNombreParType());
    }

    // GET /api/chambres/{id}/disponible  → utilisé par le module Réservation
    @GetMapping("/{id}/disponible")
    public ResponseEntity<Map<String, Object>> verifierDisponibilite(@PathVariable Long id) {
        Chambre c = chambreService.getChambreById(id);
        return ResponseEntity.ok(Map.of(
            "chambreId",         c.getId(),
            "numeroChambre",     c.getNumeroChambre(),
            "typeChambre",       c.getTypeChambre(),
            "capacite",          c.getCapacite(),
            "placesOccupees",    c.getPlacesOccupees(),
            "placesDisponibles", c.getPlacesDisponibles(),
            "disponible",        c.isDisponible()
        ));
    }
}
