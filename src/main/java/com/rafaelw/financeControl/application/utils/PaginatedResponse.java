package com.rafaelw.financeControl.application.utils;

import java.util.List;

public record PaginatedResponse<T>(
    List<T> data, Long startCursor,
    Long endCursor,
    Long nextPage,
    Long previousPage
) {

}
