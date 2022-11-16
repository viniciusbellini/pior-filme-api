package br.com.desafiotexoit.repositories;

import br.com.desafiotexoit.models.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {
    Title findByName(String name);
}
