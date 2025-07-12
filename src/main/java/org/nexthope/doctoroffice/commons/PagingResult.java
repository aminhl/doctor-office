package org.nexthope.doctoroffice.commons;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PagingResult <T> {

    private Collection<T> content;
    private Integer totalPages;
    private long totalElements;
    private Integer size;
    private Integer page;
    private boolean isEmpty;

    public PagingResult(Collection<T> content, Integer totalPages, long totalElements, Integer size, Integer page, boolean isEmpty) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.size = size;
        this.page = page+1;
        this.isEmpty = isEmpty;
    }
}
