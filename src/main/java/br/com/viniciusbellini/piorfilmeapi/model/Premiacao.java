package br.com.viniciusbellini.piorfilmeapi.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Premiacao implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String YEAR_COLUMN = "award_year";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = YEAR_COLUMN)
    private int year;
    private String title;
    private String studios;
    private String producers;
    private boolean winner;

    public Long getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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
        Premiacao premiacao = (Premiacao) o;
        return Objects.equals(id, premiacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

