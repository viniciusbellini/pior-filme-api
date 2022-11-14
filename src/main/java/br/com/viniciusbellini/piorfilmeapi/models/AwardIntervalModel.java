package br.com.viniciusbellini.piorfilmeapi.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@EqualsAndHashCode
public class AwardIntervalModel implements Comparable {

    @Column(nullable = false)
    private String producer;
    @Column(name = "intervalYear", nullable = false)
    private Integer interval;
    @Column(nullable = false)
    private Integer previousWin;
    @Column(nullable = false)
    private Integer followingWin;

    @Override
    public int compareTo(Object o) {
        AwardIntervalModel other = (AwardIntervalModel) o;
        return this.producer.compareTo(other.producer)
                + this.interval.compareTo(other.interval);
    }
}