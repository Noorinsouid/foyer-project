package com.hebergement.service;

import com.hebergement.entity.Bloc;
import com.hebergement.repository.BlocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BlocService {

    @Autowired
    private BlocRepository blocRepository;

    // Afficher tous les blocs
    public List<Bloc> getAllBlocs() {
        return blocRepository.findAll();
    }

    // Afficher un bloc par id
    public Bloc getBlocById(Long id) {
        return blocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloc introuvable avec l'id: " + id));
    }

    // Ajouter un bloc
    public Bloc createBloc(Bloc bloc) {
        if (blocRepository.existsByNomBloc(bloc.getNomBloc())) {
            throw new RuntimeException("Un bloc avec le nom '" + bloc.getNomBloc() + "' existe déjà.");
        }
        return blocRepository.save(bloc);
    }

    // Modifier un bloc
    public Bloc updateBloc(Long id, Bloc details) {
        Bloc bloc = getBlocById(id);
        if (!bloc.getNomBloc().equals(details.getNomBloc())
                && blocRepository.existsByNomBloc(details.getNomBloc())) {
            throw new RuntimeException("Un bloc avec ce nom existe déjà.");
        }
        bloc.setNomBloc(details.getNomBloc());
        return blocRepository.save(bloc);
    }

    // Supprimer un bloc
    public void deleteBloc(Long id) {
        blocRepository.delete(getBlocById(id));
    }
}
