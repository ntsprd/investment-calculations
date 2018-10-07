package sprada.bluemedia.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sprada.bluemedia.task.enums.InvestmentTypeEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {

    List<FundDto> funds;

    @NotNull
    InvestmentTypeEnum investmentType;

    @NotNull
    @Min(0)
    BigDecimal amount;
}
