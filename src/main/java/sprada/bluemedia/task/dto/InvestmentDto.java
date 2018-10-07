package sprada.bluemedia.task.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class InvestmentDto {
    private BigDecimal polskie;
    private BigDecimal zagraniczny;
    private BigDecimal pieniezny;

    public InvestmentDto(Double polskie, Double zagraniczny, Double pieniezny) {
        this.polskie = new BigDecimal(polskie).setScale(2, RoundingMode.HALF_UP);
        this.zagraniczny = new BigDecimal(zagraniczny).setScale(2, RoundingMode.HALF_UP);
        this.pieniezny = new BigDecimal(pieniezny).setScale(2, RoundingMode.HALF_UP);
    }

    public List<BigDecimal> getAllValues() {
        return new ArrayList<>(Arrays.asList(polskie, zagraniczny, pieniezny));
    }

}
