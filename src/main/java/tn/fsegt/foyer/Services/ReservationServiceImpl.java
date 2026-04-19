package tn.fsegt.foyer.Services;

import tn.fsegt.foyer.Entities.*;
import tn.fsegt.foyer.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class ReservationServiceImpl implements IReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    EtudiantRepository etudiantRepository;
    @Autowired
    ChambreRepository chambreRepository;

    @Override
    public Reservation ajouterReservationEtAssignerAChambreEtAEtudiant(Long numChambre, long cin) {
        Chambre chambre = chambreRepository.findByNumeroChambre(String.valueOf(numChambre)).orElse(null);
        Etudiant etudiant = etudiantRepository.findByCin(cin);
        if (chambre == null || etudiant == null) return null;

        long nbRes = chambre.getReservations() != null ? chambre.getReservations().size() : 0;
        long max = chambre.getCapacite();
        if (nbRes >= max) return null;

        java.time.LocalDate now = java.time.LocalDate.now();
        int year = now.getYear() % 100;
        String annee;
        if (now.getMonthValue() <= 7) {
            annee = "20" + (year - 1) + "/20" + year;
        } else {
            annee = "20" + year + "/20" + (year + 1);
        }

        String nomBloc = chambre.getBloc() != null ? chambre.getBloc().getNomBloc() : "X";
        String idRes = annee + "-" + nomBloc + "-" + numChambre + "-" + cin;

        Reservation res = new Reservation();
        res.setIdReservation(idRes);
        res.setAnneeUniversitaire(new Date());
        res.setEstValide(true);
        res.getEtudiants().add(etudiant);
        res.setChambre(chambre);

        return reservationRepository.save(res);
    }

    @Override
    public long getReservationParAnneeUniversitaire(Date debutAnnee, Date finAnnee) {
        return reservationRepository.countByAnneeUniversitaireBetween(debutAnnee, finAnnee);
    }

    @Override
    public Reservation annulerReservation(long cinEtudiant) {
        Etudiant etudiant = etudiantRepository.findByCin(cinEtudiant);
        Reservation res = reservationRepository.findByEstValideAndEtudiantsContains(true, etudiant);
        res.setChambre(null);
        reservationRepository.delete(res);
        return res;
    }
}
