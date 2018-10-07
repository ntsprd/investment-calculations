package sprada.bluemedia.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sprada.bluemedia.task.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculationService {

    private static final String PLN = " PLN";
    private static final String PERCENT = " %";
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    private final ExcessService excessService;

    private final PercentAndAmountService percentAndAmountService;

    private final ValidationService validationService;

    @Autowired
    public CalculationService(ExcessService excessService, PercentAndAmountService percentAndAmountService, ValidationService validationService) {
        this.excessService = excessService;
        this.percentAndAmountService = percentAndAmountService;
        this.validationService = validationService;
    }

    public ResultDto calculateInvestment(RequestDto requestDto) {
        validationService.validateInput(requestDto);

        AmountDto amountDto = excessService.calcExcess(requestDto);

        List<FundResultDto> resultList = requestDto.getFunds().stream().map(i -> {
            CalcDto calculatedFund = percentAndAmountService.calcPercentAndAmountForFund(requestDto, i.getFundKind(), amountDto).get(i.getId());
            return new FundResultDto(i, calculatedFund.getAmount() + PLN, calculatedFund.getPercent().multiply(ONE_HUNDRED).setScale(2, RoundingMode.UNNECESSARY) + PERCENT);
        }).collect(Collectors.toList());

        return new ResultDto(resultList, amountDto.getExcess());
    }


}
