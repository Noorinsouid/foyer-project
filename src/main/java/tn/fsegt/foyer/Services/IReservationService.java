package tn.fsegt.foyer.Services;

import tn.fsegt.foyer.Entities.Reservation;
import java.util.Date;

public interface IReservationService {
    Reservation ajouterReservationEtAssignerAChambreEtAEtudiant(Long numChambre, long cin);
    long getReservationParAnneeUniversitaire(Date debutAnnee, Date finAnnee);
    Reservation annulerReservation(long cinEtudiant);
}
