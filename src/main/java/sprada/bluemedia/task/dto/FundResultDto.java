package sprada.bluemedia.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundResultDto extends FundDto {
    private String amount;
    private String percent;


    public FundResultDto(FundDto fund, String amount, String percent) {
        this.name = fund.getName();
        this.id = fund.getId();
        this.fundKind = fund.getFundKind();
        this.amount = amount;
        this.percent = percent;
    }
}
