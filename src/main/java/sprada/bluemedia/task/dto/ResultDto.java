package sprada.bluemedia.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class ResultDto {
    private List<FundResultDto> fundResultList;
    private BigDecimal excess;
}
