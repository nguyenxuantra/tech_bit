package com.tech_bit.tech_bit.common.pageResponse;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PageResponse <T>{
    List<T> content;
    int pageNo;
    int pageSize;
    long totalElement;
    int totalPages;
    boolean last;
}
