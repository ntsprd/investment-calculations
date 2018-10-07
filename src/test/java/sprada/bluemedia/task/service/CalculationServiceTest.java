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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalculationServiceTest {

    @Mock
    private ExcessService excessService;

    @Mock
    private PercentAndAmountService percentAndAmountService;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private CalculationService calculationService;

    @Test
    public void calculateInvestment() {
        //given
        RequestDto requestDto = new RequestDto();
        requestDto.setAmount(BigDecimal.valueOf(1000));
        requestDto.setInvestmentType(InvestmentTypeEnum.BEZPIECZNY);
        List<FundDto> fundList = new ArrayList<>();
        fundList.add(new FundDto(1, "test1", FundKindEnum.POLSKIE));
        fundList.add(new FundDto(2, "test2", FundKindEnum.POLSKIE));
        fundList.add(new FundDto(3, "test3", FundKindEnum.PIENIEZNE));
        requestDto.setFunds(fundList);

        doNothing().when(validationService).validateInput(requestDto);

        when(excessService.calcExcess(eq(requestDto))).thenReturn(new AmountDto(BigDecimal.valueOf(0), BigDecimal.valueOf(1000)));
        CalcDto calcDto = new CalcDto(BigDecimal.valueOf(0.3).setScale(4), BigDecimal.valueOf(300).setScale(2));
        Map<Integer, CalcDto> percentAndAmountMapPolskie = new HashMap<>();
        percentAndAmountMapPolskie.put(1, calcDto);
        percentAndAmountMapPolskie.put(2, calcDto);
        CalcDto calcDto1 = new CalcDto(BigDecimal.valueOf(0.2).setScale(4), BigDecimal.valueOf(200).setScale(2));
        Map<Integer, CalcDto> percentAndAmountMapPieniezne = new HashMap<>();
        percentAndAmountMapPieniezne.put(3, calcDto1);

        when(percentAndAmountService.calcPercentAndAmountForFund(eq(requestDto), eq(FundKindEnum.POLSKIE), any(AmountDto.class))).thenReturn(percentAndAmountMapPolskie);
        when(percentAndAmountService.calcPercentAndAmountForFund(eq(requestDto), eq(FundKindEnum.PIENIEZNE), any(AmountDto.class))).thenReturn(percentAndAmountMapPieniezne);

        //when
        ResultDto result = calculationService.calculateInvestment(requestDto);

        //then

        assertEquals(BigDecimal.valueOf(0), result.getExcess());
        assertEquals(3, result.getFundResultList().size());
        assertEquals("300.00 PLN", result.getFundResultList().get(1).getAmount());
        assertEquals("30.00 %", result.getFundResultList().get(1).getPercent());

    }
}