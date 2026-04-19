package tn.fsegt.foyer.RestControllers;

import tn.fsegt.foyer.Entities.Reservation;
import tn.fsegt.foyer.Services.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
@RequestMapping("/reservation")
public class ReservationRestController {

    @Autowired
    IReservationService reservationService;

    @PostMapping("/ajouterReservationEtAssignerAChambreEtAEtudiant")
    public Reservation ajouterReservation(
            @RequestParam Long numChambre,
            @RequestParam long cin) {
        return reservationService
                .ajouterReservationEtAssignerAChambreEtAEtudiant(numChambre, cin);
    }

    @GetMapping("/getReservationParAnneeUniversitaire")
    public long getReservationParAnnee(
            @RequestParam Date debutAnnee,
            @RequestParam Date finAnnee) {
        return reservationService
                .getReservationParAnneeUniversitaire(debutAnnee, finAnnee);
    }

    @DeleteMapping("/annulerReservation")
    public String annulerReservation(
            @RequestParam long cinEtudiant) {
        Reservation r = reservationService.annulerReservation(cinEtudiant);
        return "La réservation " + r.getIdReservation() + " est annulée avec succès";
    }
}