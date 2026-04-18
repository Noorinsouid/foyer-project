package com.hebergement.repository;

import com.hebergement.entity.Chambre;
import com.hebergement.entity.Chambre.TypeChambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {

    // Unicité numéro chambre
    boolean existsByNumeroChambre(String numeroChambre);
    Optional<Chambre> findByNumeroChambre(String numeroChambre);

    // Toutes les chambres d'un bloc
    List<Chambre> findByBlocId(Long blocId);

    // Chambres par type
    List<Chambre> findByTypeChambre(TypeChambre type);

    // Nombre de chambres par type
    long countByTypeChambre(TypeChambre type);

    // Chambres libres (places encore disponibles)
    @Query("SELECT c FROM Chambre c WHERE " +
           "(c.typeChambre = 'SIMPLE' AND c.placesOccupees < 1) OR " +
           "(c.typeChambre = 'DOUBLE' AND c.placesOccupees < 2) OR " +
           "(c.typeChambre = 'TRIPLE' AND c.placesOccupees < 3)")
    List<Chambre> findChambresLibres();

    // Chambres libres dans un bloc précis
    @Query("SELECT c FROM Chambre c WHERE c.bloc.id = :blocId AND (" +
           "(c.typeChambre = 'SIMPLE' AND c.placesOccupees < 1) OR " +
           "(c.typeChambre = 'DOUBLE' AND c.placesOccupees < 2) OR " +
           "(c.typeChambre = 'TRIPLE' AND c.placesOccupees < 3))")
    List<Chambre> findChambresLibresByBlocId(@Param("blocId") Long blocId);
}
