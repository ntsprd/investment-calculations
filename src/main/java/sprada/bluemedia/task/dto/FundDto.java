package sprada.bluemedia.task.dto;

import lombok.*;
import sprada.bluemedia.task.enums.FundKindEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundDto {

    protected Integer id;

    protected String name;

    protected FundKindEnum fundKind;

}
