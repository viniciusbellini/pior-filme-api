package br.com.desafiotexoit.models;


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
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "TITLE")
public class Title implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String name;
    @Column(name = "release_year")
    @NotBlank
    private String year;
    private boolean winner;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "title_producer",
            joinColumns = @JoinColumn(name = "title_id"),
            inverseJoinColumns = @JoinColumn(name = "producer_id")
    )
    List<Producer> producers;
    @ManyToMany
    @JoinTable(
            name = "title_studio",
            joinColumns = @JoinColumn(name = "title_id"),
            inverseJoinColumns = @JoinColumn(name = "studio_id")
    )
    List<Studio> studios;


    public void setWinner(String winner) {
        this.winner = winner != null && winner.equals("yes");
    }

    @Override
    public int compareTo(Object o) {
        Title other = (Title) o;
        return this.year.compareTo(other.year);
    }
    public Integer getYear() {
        return Integer.parseInt(this.year);
    }
}
