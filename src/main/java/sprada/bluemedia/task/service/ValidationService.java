package sprada.bluemedia.task.service;

import org.springframework.stereotype.Service;
import sprada.bluemedia.task.dto.FundDto;
import sprada.bluemedia.task.dto.RequestDto;
import sprada.bluemedia.task.enums.ErrorEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@Service
public class ValidationService {
    public void validateInput(RequestDto requestDto) {
        List<ErrorEnum> errorEnums = new ArrayList<>();
        if (requestDto == null) {
            errorEnums.add(ErrorEnum.EMPTY_REQUEST);
            throw new IllegalArgumentException(getErrorsValues(errorEnums));
        } else {
            if (requestDto.getInvestmentType() == null) {
                errorEnums.add(ErrorEnum.EMPTY_INVESTMENT_TYPE);
            }
            if (requestDto.getAmount() == null) {
                errorEnums.add(ErrorEnum.EMPTY_AMOUNT);

            } else if (!(requestDto.getAmount().signum() == 1)) {
                errorEnums.add(ErrorEnum.NEGATIVE_AMOUNT);
            }
            if (requestDto.getFunds() == null) {
                errorEnums.add(ErrorEnum.EMPTY_FUNDS);
            } else {
                getErrorsForFunds(requestDto.getFunds(), FundDto::getId).ifPresent(i -> errorEnums.add(ErrorEnum.EMPTY_ID));
                getErrorsForFunds(requestDto.getFunds(), FundDto::getName).ifPresent(i -> errorEnums.add(ErrorEnum.EMPTY_NAME));
                getErrorsForFunds(requestDto.getFunds(), FundDto::getFundKind).ifPresent(i -> errorEnums.add(ErrorEnum.EMPTY_FUND_KIND));
            }
        }
        if (!errorEnums.isEmpty())
            throw new IllegalArgumentException(getErrorsValues(errorEnums));
    }

    private Optional<FundDto> getErrorsForFunds(List<FundDto> funds, Function<FundDto, Object> f) {
        return funds.stream().filter(i -> f.apply(i) == null).findAny();
    }

    private String getErrorsValues(List<ErrorEnum> errorEnumList) {
        return String.join(", ", errorEnumList.stream().map(ErrorEnum::getValue).collect(toList()));
    }
}
