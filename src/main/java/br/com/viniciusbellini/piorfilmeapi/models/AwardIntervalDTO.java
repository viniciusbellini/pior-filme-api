package br.com.viniciusbellini.piorfilmeapi.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class AwardIntervalDTO {

    private List<AwardIntervalModel> min;
    private List<AwardIntervalModel> max;

    public static AwardIntervalDTO transformToDTO(List<AwardIntervalModel> min, List<AwardIntervalModel> max) {
        return new AwardIntervalDTO(min, max);
    }
}
