package br.com.viniciusbellini.piorfilmeapi.repositories;

import br.com.viniciusbellini.piorfilmeapi.models.StudioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudioRepository extends JpaRepository<StudioModel, Long> {

    StudioModel findByName(String name);
}
