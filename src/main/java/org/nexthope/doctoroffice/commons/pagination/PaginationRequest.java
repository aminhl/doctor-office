package org.nexthope.doctoroffice.commons.pagination;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PaginationRequest {

    @Builder.Default
    private Integer page = 0;

    @Builder.Default
    private Integer size = 10;

    @Builder.Default
    private Sort.Direction sortDirection = Sort.Direction.DESC;

    @Builder.Default
    private String sortField = "id";

    public static PaginationRequest of(Integer page, Integer size, Sort.Direction sortDirection, String sortField) {
        return PaginationRequest.builder()
                .page(page)
                .size(size)
                .sortDirection(sortDirection)
                .sortField(sortField)
                .build();
    }

}
