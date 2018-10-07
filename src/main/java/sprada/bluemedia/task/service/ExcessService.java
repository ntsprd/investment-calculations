package sprada.bluemedia.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sprada.bluemedia.task.dto.AmountDto;
import sprada.bluemedia.task.dto.InvestmentDto;
import sprada.bluemedia.task.dto.RequestDto;
import sprada.bluemedia.task.enums.InvestmentTypeEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
public class ExcessService {

    private final Map<InvestmentTypeEnum, InvestmentDto> investmentDtoMap;

    private static final RoundingMode DOWN = RoundingMode.DOWN;

    @Autowired
    public ExcessService(Map<InvestmentTypeEnum, InvestmentDto> investmentDtoMap) {
        this.investmentDtoMap = investmentDtoMap;
    }

    AmountDto calcExcess(RequestDto dto) {
        BigDecimal sum = calcAmountForInvestmentType(dto);
        BigDecimal subtract = dto.getAmount().subtract(sum);

        return new AmountDto(subtract, sum);
    }

    private BigDecimal calcAmountForInvestmentType(RequestDto dto) {
        return investmentDtoMap
                .get(dto.getInvestmentType())
                .getAllValues()
                .stream()
                .map(i -> i.multiply(dto.getAmount()).setScale(0, DOWN))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
