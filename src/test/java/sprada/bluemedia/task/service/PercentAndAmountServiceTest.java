package sprada.bluemedia.task.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import sprada.bluemedia.task.dto.*;
import sprada.bluemedia.task.enums.FundKindEnum;
import sprada.bluemedia.task.enums.InvestmentTypeEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PercentAndAmountServiceTest {

    @Mock
    private Map<InvestmentTypeEnum, InvestmentDto> investmentDtoMap;

    @InjectMocks
    private PercentAndAmountService percentAndAmountService;

    @Test
    public void calcPercentAndAmountForFundEqualValues() {
        //given
        RequestDto requestDto = setRequestDto();
        when(investmentDtoMap.get(InvestmentTypeEnum.BEZPIECZNY)).thenReturn(new InvestmentDto(0.6, 0.2, 0.2));
        AmountDto amount = new AmountDto(BigDecimal.valueOf(0), BigDecimal.valueOf(1000));

        //when
        Map<Integer, CalcDto> result = percentAndAmountService.calcPercentAndAmountForFund(requestDto, FundKindEnum.POLSKIE, amount);

        //then
        CalcDto calcDto = new CalcDto(BigDecimal.valueOf(0.3).setScale(4), BigDecimal.valueOf(300).setScale(2));

        assertEquals(2, result.size());
        assertEquals(calcDto.getPercent(), result.get(2).getPercent());
        assertEquals(calcDto.getAmount(), result.get(2).getAmount());


    }

    @Test
    public void calcPercentAndAmountForFundNoEqualValues() {
        //given
        RequestDto requestDto = setRequestDto();
        when(investmentDtoMap.get(InvestmentTypeEnum.BEZPIECZNY)).thenReturn(new InvestmentDto(0.2, 0.2, 0.6));
        AmountDto amount = new AmountDto(BigDecimal.valueOf(0), BigDecimal.valueOf(1000));

        //when
        Map<Integer, CalcDto> result = percentAndAmountService.calcPercentAndAmountForFund(requestDto, FundKindEnum.ZAGRANICZNE, amount);

        //then
        CalcDto calcDtoFor5Id = new CalcDto(BigDecimal.valueOf(0.0666).setScale(4), BigDecimal.valueOf(66.60).setScale(2));
        CalcDto calcDtoFor4Id = new CalcDto(BigDecimal.valueOf(0.0668).setScale(4), BigDecimal.valueOf(66.80).setScale(2));

        assertEquals(3, result.size());
        assertEquals(calcDtoFor5Id.getPercent(), result.get(5).getPercent());
        assertEquals(calcDtoFor5Id.getAmount(), result.get(5).getAmount());
        assertEquals(calcDtoFor4Id.getPercent(), result.get(4).getPercent());
        assertEquals(calcDtoFor4Id.getAmount(), result.get(4).getAmount());


    }

    @Test
    public void calcPercentAndAmountForFundKindPieniezne() {
        //given
        RequestDto requestDto = setRequestDto();
        when(investmentDtoMap.get(InvestmentTypeEnum.BEZPIECZNY)).thenReturn(new InvestmentDto(0.6, 0.2, 0.2));
        AmountDto amount = new AmountDto(BigDecimal.valueOf(0), BigDecimal.valueOf(1000));

        //when
        Map<Integer, CalcDto> result = percentAndAmountService.calcPercentAndAmountForFund(requestDto, FundKindEnum.PIENIEZNE, amount);

        //then
        CalcDto calcDto = new CalcDto(BigDecimal.valueOf(0.2).setScale(4), BigDecimal.valueOf(200).setScale(2));

        assertEquals(1, result.size());
        assertEquals(calcDto.getPercent(), result.get(3).getPercent());
        assertEquals(calcDto.getAmount(), result.get(3).getAmount());


    }

    private RequestDto setRequestDto() {
        RequestDto requestDto = new RequestDto();
        requestDto.setAmount(BigDecimal.valueOf(1000));
        requestDto.setInvestmentType(InvestmentTypeEnum.BEZPIECZNY);
        List<FundDto> fundList = new ArrayList<>();
        fundList.add(new FundDto(1, "test1", FundKindEnum.POLSKIE));
        fundList.add(new FundDto(2, "test2", FundKindEnum.POLSKIE));
        fundList.add(new FundDto(3, "test3", FundKindEnum.PIENIEZNE));
        fundList.add(new FundDto(4, "test4", FundKindEnum.ZAGRANICZNE));
        fundList.add(new FundDto(5, "test5", FundKindEnum.ZAGRANICZNE));
        fundList.add(new FundDto(6, "test6", FundKindEnum.ZAGRANICZNE));
        requestDto.setFunds(fundList);

        return requestDto;
    }
}