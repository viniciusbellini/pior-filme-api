package br.com.viniciusbellini.piorfilmeapi.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "TB_PREMIACAO")
public class PremiacaoModel implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String YEAR_COLUMN = "award_year";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = YEAR_COLUMN, nullable = false)
    private String year;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String studios;
    @Column(nullable = false)
    private String producers;
    @Column(nullable = false)
    private boolean winner;

    public UUID getId() {
        return id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public String getProducers() {
        return producers;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner != null && winner.equals("yes");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PremiacaoModel premiacao = (PremiacaoModel) o;
        return Objects.equals(id, premiacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

