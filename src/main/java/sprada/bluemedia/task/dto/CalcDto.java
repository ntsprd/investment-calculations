package sprada.bluemedia.task.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalcDto {
    private BigDecimal percent;
    private BigDecimal amount;
}
