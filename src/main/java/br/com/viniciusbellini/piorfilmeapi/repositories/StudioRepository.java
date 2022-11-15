package br.com.viniciusbellini.piorfilmeapi.repositories;

import br.com.viniciusbellini.piorfilmeapi.models.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {

    Studio findByName(String name);
}
