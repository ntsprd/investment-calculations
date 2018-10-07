package sprada.bluemedia.task.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import sprada.bluemedia.task.dto.FundDto;
import sprada.bluemedia.task.dto.RequestDto;
import sprada.bluemedia.task.enums.ErrorEnum;
import sprada.bluemedia.task.enums.FundKindEnum;
import sprada.bluemedia.task.enums.InvestmentTypeEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ValidationServiceTest {


    @InjectMocks
    private ValidationService validationService;

    @Rule
    public final ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void validateInputEmptyRequest() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage(ErrorEnum.EMPTY_REQUEST.getValue());
        validationService.validateInput(null);
    }

    @Test
    public void validateInputEmptyInvestmentType() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage(ErrorEnum.EMPTY_INVESTMENT_TYPE.getValue());

        RequestDto requestDto = new RequestDto();
        requestDto.setAmount(BigDecimal.valueOf(1001));
        List<FundDto> fundList = new ArrayList<>();
        fundList.add(new FundDto(1, "test1", FundKindEnum.POLSKIE));
        requestDto.setFunds(fundList);

        validationService.validateInput(requestDto);

    }

    @Test
    public void validateInputAmountLessThanZero() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage(ErrorEnum.NEGATIVE_AMOUNT.getValue());

        RequestDto requestDto = new RequestDto();
        requestDto.setInvestmentType(InvestmentTypeEnum.BEZPIECZNY);
        requestDto.setAmount(BigDecimal.valueOf(-1001));
        List<FundDto> fundList = new ArrayList<>();
        fundList.add(new FundDto(1, "test1", FundKindEnum.POLSKIE));
        requestDto.setFunds(fundList);

        validationService.validateInput(requestDto);

    }

    @Test
    public void validateInputEmptyAmount() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage(ErrorEnum.EMPTY_AMOUNT.getValue());

        RequestDto requestDto = new RequestDto();
        requestDto.setInvestmentType(InvestmentTypeEnum.BEZPIECZNY);
        List<FundDto> fundList = new ArrayList<>();
        fundList.add(new FundDto(1, "test1", FundKindEnum.POLSKIE));
        requestDto.setFunds(fundList);

        validationService.validateInput(requestDto);

    }

    @Test
    public void validateInputEmptyFunds() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage(ErrorEnum.EMPTY_FUNDS.getValue());

        RequestDto requestDto = new RequestDto();
        requestDto.setInvestmentType(InvestmentTypeEnum.BEZPIECZNY);
        requestDto.setAmount(BigDecimal.valueOf(1001));

        validationService.validateInput(requestDto);
    }

    @Test
    public void validateInputEmptyFundId() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage(ErrorEnum.EMPTY_ID.getValue());

        RequestDto requestDto = new RequestDto();
        requestDto.setInvestmentType(InvestmentTypeEnum.BEZPIECZNY);
        requestDto.setAmount(BigDecimal.valueOf(1001));
        List<FundDto> fundList = new ArrayList<>();
        fundList.add(new FundDto(null, "test1", FundKindEnum.POLSKIE));
        requestDto.setFunds(fundList);

        validationService.validateInput(requestDto);
    }

    @Test
    public void validateInputEmptyFundName() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage(ErrorEnum.EMPTY_NAME.getValue());

        RequestDto requestDto = new RequestDto();
        requestDto.setInvestmentType(InvestmentTypeEnum.BEZPIECZNY);
        requestDto.setAmount(BigDecimal.valueOf(1001));
        List<FundDto> fundList = new ArrayList<>();
        fundList.add(new FundDto(1, null, FundKindEnum.POLSKIE));
        requestDto.setFunds(fundList);

        validationService.validateInput(requestDto);
    }

    @Test
    public void validateInputEmptyFundKind() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage(ErrorEnum.EMPTY_FUND_KIND.getValue());

        RequestDto requestDto = new RequestDto();
        requestDto.setInvestmentType(InvestmentTypeEnum.BEZPIECZNY);
        requestDto.setAmount(BigDecimal.valueOf(1001));
        List<FundDto> fundList = new ArrayList<>();
        fundList.add(new FundDto(1, "TEST", null));
        requestDto.setFunds(fundList);

        validationService.validateInput(requestDto);
    }

    @Test
    public void validateInputNoErrors() {

        RequestDto requestDto = new RequestDto();
        requestDto.setInvestmentType(InvestmentTypeEnum.BEZPIECZNY);
        requestDto.setAmount(BigDecimal.valueOf(1001));
        List<FundDto> fundList = new ArrayList<>();
        fundList.add(new FundDto(1, "TEST", FundKindEnum.POLSKIE));
        requestDto.setFunds(fundList);

        validationService.validateInput(requestDto);
    }


}