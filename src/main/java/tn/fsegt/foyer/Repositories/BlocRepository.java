package tn.fsegt.foyer.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.fsegt.foyer.Entities.Bloc;
import tn.fsegt.foyer.Entities.Foyer;
import java.util.Optional;

@Repository
public interface BlocRepository extends JpaRepository<Bloc, Long> {

    Optional<Bloc> findByNomBloc(String nomBloc);

    boolean existsByNomBloc(String nomBloc);
}
