package br.com.desafiotexoit.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class AwardIntervalDTO {

    private List<AwardInterval> min;
    private List<AwardInterval> max;

    public static AwardIntervalDTO transformToDTO(List<AwardInterval> min, List<AwardInterval> max) {
        return new AwardIntervalDTO(min, max);
    }
}
