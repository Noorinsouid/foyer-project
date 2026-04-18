package com.hebergement.repository;

import com.hebergement.entity.Bloc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlocRepository extends JpaRepository<Bloc, Long> {

    Optional<Bloc> findByNomBloc(String nomBloc);

    boolean existsByNomBloc(String nomBloc);
}
