package br.com.viniciusbellini.piorfilmeapi.repository;

import br.com.viniciusbellini.piorfilmeapi.model.Premiacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiacaoRepository extends JpaRepository<Premiacao, Long> {
}
