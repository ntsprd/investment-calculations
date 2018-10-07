package sprada.bluemedia.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sprada.bluemedia.task.dto.InvestmentDto;
import sprada.bluemedia.task.enums.InvestmentTypeEnum;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class InvestmentConfiguration {
    @Bean
    Map<InvestmentTypeEnum, InvestmentDto> getInvestmentTypes() {

        InvestmentDto bezpieczny = new InvestmentDto(0.2, 0.75, 0.05);
        InvestmentDto zrownowazony = new InvestmentDto(0.3, 0.6, 0.1);
        InvestmentDto agresywny = new InvestmentDto(0.4, 0.2, 0.4);

        Map<InvestmentTypeEnum, InvestmentDto> investmentDtoMap = new HashMap<>();
        investmentDtoMap.put(InvestmentTypeEnum.BEZPIECZNY, bezpieczny);
        investmentDtoMap.put(InvestmentTypeEnum.ZROWNOWAZONY, zrownowazony);
        investmentDtoMap.put(InvestmentTypeEnum.AGRESYWNY, agresywny);


        return investmentDtoMap;


    }

}
