package sprada.bluemedia.task.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import sprada.bluemedia.task.dto.AmountDto;
import sprada.bluemedia.task.dto.FundDto;
import sprada.bluemedia.task.dto.InvestmentDto;
import sprada.bluemedia.task.dto.RequestDto;
import sprada.bluemedia.task.enums.FundKindEnum;
import sprada.bluemedia.task.enums.InvestmentTypeEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExcessServiceTest {

    @Mock
    private Map<InvestmentTypeEnum, InvestmentDto> investmentDtoMap;

    @InjectMocks
    private ExcessService excessService;


    @Test
    public void calcExcessReturnNoExcess() {
        //given
        RequestDto requestDto = new RequestDto();
        requestDto.setAmount(BigDecimal.valueOf(1000));
        requestDto.setInvestmentType(InvestmentTypeEnum.BEZPIECZNY);
        List<FundDto> fundList = new ArrayList<>();
        fundList.add(new FundDto(1, "test1", FundKindEnum.POLSKIE));
        fundList.add(new FundDto(2, "test2", FundKindEnum.POLSKIE));
        fundList.add(new FundDto(3, "test3", FundKindEnum.PIENIEZNE));
        requestDto.setFunds(fundList);

        when(investmentDtoMap.get(InvestmentTypeEnum.BEZPIECZNY)).thenReturn(new InvestmentDto(0.2, 0.7, 0.1));

        //when
        AmountDto result = excessService.calcExcess(requestDto);

        //then
        assertEquals(BigDecimal.valueOf(0), result.getExcess());
        assertEquals(BigDecimal.valueOf(1000), result.getAmount());
    }

    @Test
    public void calcExcessReturnValue() {
        //given
        RequestDto requestDto = new RequestDto();
        requestDto.setAmount(BigDecimal.valueOf(1001));
        requestDto.setInvestmentType(InvestmentTypeEnum.ZROWNOWAZONY);
        List<FundDto> fundList = new ArrayList<>();
        fundList.add(new FundDto(1, "test1", FundKindEnum.POLSKIE));
        fundList.add(new FundDto(2, "test2", FundKindEnum.POLSKIE));
        fundList.add(new FundDto(3, "test3", FundKindEnum.POLSKIE));
        fundList.add(new FundDto(4, "test4", FundKindEnum.PIENIEZNE));
        requestDto.setFunds(fundList);

        when(investmentDtoMap.get(InvestmentTypeEnum.ZROWNOWAZONY)).thenReturn(new InvestmentDto(0.4, 0.4, 0.2));

        //when
        AmountDto result = excessService.calcExcess(requestDto);

        //then
        assertEquals(BigDecimal.valueOf(1), result.getExcess());
        assertEquals(BigDecimal.valueOf(1000), result.getAmount());

    }
}