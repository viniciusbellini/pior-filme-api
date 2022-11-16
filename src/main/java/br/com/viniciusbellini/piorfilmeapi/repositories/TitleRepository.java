package br.com.viniciusbellini.piorfilmeapi.repositories;

import br.com.viniciusbellini.piorfilmeapi.models.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {
    Title findByName(String name);
}
