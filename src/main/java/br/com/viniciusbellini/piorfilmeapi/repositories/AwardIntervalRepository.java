package br.com.viniciusbellini.piorfilmeapi.repositories;

import br.com.viniciusbellini.piorfilmeapi.models.AwardIntervalModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AwardIntervalRepository extends JpaRepository<AwardIntervalModel, Long> {
}
