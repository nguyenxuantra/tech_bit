package com.tech_bit.tech_bit.common.filterDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDto {
    private String search;
    private Long fromDate;
    private Long toDate;
    private String sortBy;
    private String sortDir = "desc";
    private int pageNo = 1;
    private int pageSize = 5;
}
