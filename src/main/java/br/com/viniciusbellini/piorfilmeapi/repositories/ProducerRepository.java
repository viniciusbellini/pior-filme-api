package br.com.viniciusbellini.piorfilmeapi.repositories;

import br.com.viniciusbellini.piorfilmeapi.models.ProducerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<ProducerModel, Long> {
    ProducerModel findByName(String name);
}
