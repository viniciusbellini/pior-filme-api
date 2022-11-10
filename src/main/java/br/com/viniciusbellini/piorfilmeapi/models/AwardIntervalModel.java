package br.com.viniciusbellini.piorfilmeapi.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Setter
@EqualsAndHashCode
@Table(name = "AWARD_INTERVAL")
public class AwardIntervalModel implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Column(nullable = false)
    private String producer;
    @Getter
    @Column(name = "intervalYear", nullable = false)
    private Integer interval;
    @Getter
    @Column(nullable = false)
    private Integer previousWin;
    @Getter
    @Column(nullable = false)
    private Integer followingWin;

    @Override
    public int compareTo(Object o) {
        AwardIntervalModel other = (AwardIntervalModel) o;
        return this.producer.compareTo(other.producer)
                + this.interval.compareTo(other.interval);
    }
}