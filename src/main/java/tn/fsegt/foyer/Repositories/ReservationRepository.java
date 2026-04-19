package tn.fsegt.foyer.Repositories;

import tn.fsegt.foyer.Entities.Etudiant;
import tn.fsegt.foyer.Entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    Reservation findByEstValideAndEtudiantsContains(boolean estValide, Etudiant etudiant);
    long countByAnneeUniversitaireBetween(Date debut, Date fin);
}
