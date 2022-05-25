package br.com.zup.edu.cadastrodebilhetes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BilheteRepository extends JpaRepository<Bilhete, Long> {

    Optional<Bilhete> findBySorteio_id(Long idSorteio);

    boolean existsByHashCelularAndNumeroAndSorteio_id(String celular, Integer numero, Long idSorteio);
}
