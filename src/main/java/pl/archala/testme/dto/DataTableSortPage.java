package pl.archala.testme.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DataTableSortPage {

    private int page;
    private int length;

    @NotEmpty
    private String column;

    @NotEmpty
    private String direction;

}
