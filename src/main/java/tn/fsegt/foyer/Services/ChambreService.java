package com.hebergement.service;

import com.hebergement.entity.Bloc;
import com.hebergement.entity.Chambre;
import com.hebergement.entity.Chambre.TypeChambre;
import com.hebergement.repository.BlocRepository;
import com.hebergement.repository.ChambreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChambreService {

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private BlocRepository blocRepository;

    // Afficher toutes les chambres
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }

    // Afficher une chambre par id
    public Chambre getChambreById(Long id) {
        return chambreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chambre introuvable avec l'id: " + id));
    }

    // Ajouter une chambre
    public Chambre createChambre(Chambre chambre) {
        if (chambreRepository.existsByNumeroChambre(chambre.getNumeroChambre())) {
            throw new RuntimeException("Le numéro '" + chambre.getNumeroChambre() + "' est déjà utilisé.");
        }
        chambre.setPlacesOccupees(0);
        return chambreRepository.save(chambre);
    }

    // Modifier une chambre
    public Chambre updateChambre(Long id, Chambre details) {
        Chambre chambre = getChambreById(id);
        if (!chambre.getNumeroChambre().equals(details.getNumeroChambre())
                && chambreRepository.existsByNumeroChambre(details.getNumeroChambre())) {
            throw new RuntimeException("Ce numéro de chambre est déjà utilisé.");
        }
        chambre.setNumeroChambre(details.getNumeroChambre());
        chambre.setTypeChambre(details.getTypeChambre());
        return chambreRepository.save(chambre);
    }

    // Supprimer une chambre
    public void deleteChambre(Long id) {
        chambreRepository.delete(getChambreById(id));
    }

    // Affecter une chambre à un bloc
    public Chambre affecterChambreABloc(Long chambreId, Long blocId) {
        Chambre chambre = getChambreById(chambreId);
        Bloc bloc = blocRepository.findById(blocId)
                .orElseThrow(() -> new RuntimeException("Bloc introuvable avec l'id: " + blocId));
        chambre.setBloc(bloc);
        return chambreRepository.save(chambre);
    }

    // Chambres d'un bloc
    public List<Chambre> getChambresByBloc(Long blocId) {
        return chambreRepository.findByBlocId(blocId);
    }

    // Chambres libres (non réservées / avec places disponibles)
    public List<Chambre> getChambresLibres() {
        return chambreRepository.findChambresLibres();
    }

    // Nombre de chambres par type
    public Map<String, Long> getNombreParType() {
        return Map.of(
            "SIMPLE", chambreRepository.countByTypeChambre(TypeChambre.SIMPLE),
            "DOUBLE", chambreRepository.countByTypeChambre(TypeChambre.DOUBLE),
            "TRIPLE", chambreRepository.countByTypeChambre(TypeChambre.TRIPLE)
        );
    }

    // Vérifier si une chambre est disponible (utilisé par le module Réservation)
    public boolean isChambreDisponible(Long chambreId) {
        return getChambreById(chambreId).isDisponible();
    }

    // Occuper une place (appelé par le module Réservation lors d'une réservation)
    public void occuperPlace(Long chambreId) {
        Chambre chambre = getChambreById(chambreId);
        if (chambre.isPleine()) {
            throw new RuntimeException("La chambre " + chambre.getNumeroChambre() + " est complète.");
        }
        chambre.setPlacesOccupees(chambre.getPlacesOccupees() + 1);
        chambreRepository.save(chambre);
    }

    // Libérer une place (appelé par le module Réservation lors d'une annulation)
    public void libererPlace(Long chambreId) {
        Chambre chambre = getChambreById(chambreId);
        if (chambre.getPlacesOccupees() > 0) {
            chambre.setPlacesOccupees(chambre.getPlacesOccupees() - 1);
            chambreRepository.save(chambre);
        }
    }
}
