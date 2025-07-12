package org.nexthope.doctoroffice.commons;

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
    private String sortField = "id";

    @Builder.Default
    private Sort.Direction sortDirection = Sort.Direction.DESC;

    public static PaginationRequest of(Integer page, Integer size, String sortField, Sort.Direction sortDirection) {
        return PaginationRequest.builder()
                .page(page != null ? page : 0)
                .size(size != null ? size : 1)
                .sortField(sortField != null ? sortField : "id")
                .sortDirection(sortDirection != null ? sortDirection : Sort.Direction.DESC)
                .build();
    }

}
