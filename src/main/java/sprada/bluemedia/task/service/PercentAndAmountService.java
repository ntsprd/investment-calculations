package sprada.bluemedia.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sprada.bluemedia.task.dto.*;
import sprada.bluemedia.task.enums.FundKindEnum;
import sprada.bluemedia.task.enums.InvestmentTypeEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
public class PercentAndAmountService {

    private final Map<InvestmentTypeEnum, InvestmentDto> investmentDtoMap;

    private static final RoundingMode DOWN = RoundingMode.DOWN;

    private static final BigDecimal ZERO = new BigDecimal(0).setScale(4, DOWN);

    @Autowired
    public PercentAndAmountService(Map<InvestmentTypeEnum, InvestmentDto> investmentDtoMap) {
        this.investmentDtoMap = investmentDtoMap;
    }

    Map<Integer, CalcDto> calcPercentAndAmountForFund(RequestDto dto, FundKindEnum fundKind, AmountDto amountDto) {
        Map<FundKindEnum, Long> fundMap = countFunds(dto.getFunds());
        BigDecimal calculatedPercent = findPercentForFundKind(investmentDtoMap.get(dto.getInvestmentType()), fundKind);
        Long fundCount = fundMap.get(fundKind);
        BigDecimal percent = calculatedPercent.divide(new BigDecimal(fundCount), 4, DOWN);
        List<FundDto> funds = getFundsForKind(dto.getFunds(), fundKind);
        BigDecimal subtractedValue = getSubtractedValueForPercent(calculatedPercent, percent, fundCount);
        Map<Integer, CalcDto> percent4Fund = setPercentAndAmount(funds, amountDto, percent);
        updatePercentAndAmount(funds, subtractedValue, fundKind, percent4Fund, amountDto);

        return percent4Fund;
    }

    private Map<FundKindEnum, Long> countFunds(List<FundDto> funds) {
        return funds
                .stream()
                .collect(groupingBy(FundDto::getFundKind, counting()));
    }

    private BigDecimal findPercentForFundKind(InvestmentDto investmentDto, FundKindEnum fund) {
        switch (fund) {
            case POLSKIE:
                return investmentDto.getPolskie();
            case ZAGRANICZNE:
                return investmentDto.getZagraniczny();
            case PIENIEZNE:
                return investmentDto.getPieniezny();
            default:
                throw new IllegalArgumentException("Invalid FundKindEnum: " + fund.name());
        }
    }

    private HashMap<Integer, CalcDto> setPercentAndAmount(List<FundDto> funds, AmountDto dto, BigDecimal percent) {
        HashMap<Integer, CalcDto> percentForFund = new HashMap<>();
        funds.forEach(i -> percentForFund.put(i.getId(), CalcDto.builder()
                .percent(percent)
                .amount(percent.multiply(dto.getAmount()).setScale(2, RoundingMode.HALF_DOWN))
                .build()));
        return percentForFund;
    }

    private BigDecimal getSubtractedValueForPercent(BigDecimal calculatedPercent, BigDecimal percent, Long fundCount) {
        return calculatedPercent.subtract(percent.multiply(BigDecimal.valueOf(fundCount)));

    }

    private void updatePercentAndAmount(List<FundDto> funds, BigDecimal subValue, FundKindEnum fundKind, Map<Integer, CalcDto> percentAndAmount4Id, AmountDto dto) {
        Optional.of(subValue)
                .filter(i -> !ZERO.equals(i))
                .map(i -> funds.stream().filter(j -> j.getFundKind().equals(fundKind)).findFirst())
                .flatMap(Function.identity())
                .ifPresent(fund ->
                        updatePercentAndAmountForId(subValue, percentAndAmount4Id, fund, dto));
    }

    private void updatePercentAndAmountForId(BigDecimal subValue, Map<Integer, CalcDto> percentAndAmount4Id, FundDto fund, AmountDto dto) {
        CalcDto currentCalcDto = percentAndAmount4Id.get(fund.getId());
        BigDecimal currentPercent = currentCalcDto.getPercent().add(subValue);
        currentCalcDto.setAmount(dto.getAmount().multiply(currentPercent).setScale(2, RoundingMode.HALF_UP));
        currentCalcDto.setPercent(currentPercent.setScale(4, RoundingMode.HALF_UP));
        percentAndAmount4Id.put(fund.getId(), currentCalcDto);
    }

    private List<FundDto> getFundsForKind(List<FundDto> funds, FundKindEnum fundKind) {
        return funds.stream()
                .filter(x -> x.getFundKind().equals(fundKind))
                .collect(Collectors.toList());
    }
}
