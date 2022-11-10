package br.com.viniciusbellini.piorfilmeapi.models;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "TITLE")
public class TitleModel implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "release_year")
    private String year;
    private boolean winner;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "title_producer",
            joinColumns = @JoinColumn(name = "title_id"),
            inverseJoinColumns = @JoinColumn(name = "producer_id")
    )
    List<ProducerModel> producers;
    @ManyToMany
    @JoinTable(
            name = "title_studio",
            joinColumns = @JoinColumn(name = "title_id"),
            inverseJoinColumns = @JoinColumn(name = "studio_id")
    )
    List<StudioModel> studios;

    public void setWinner(String winner) {
        this.winner = winner != null && winner.equals("yes");
    }

    @Override
    public int compareTo(Object o) {
        TitleModel other = (TitleModel) o;
        return this.year.compareTo(other.year);
    }
}
