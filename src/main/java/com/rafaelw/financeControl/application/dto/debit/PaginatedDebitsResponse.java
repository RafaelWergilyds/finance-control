package com.rafaelw.financeControl.application.dto.debit;

import java.util.List;

public record PaginatedDebitsResponse(
    List<DebitResponseDTO> data,
    Long startCursor,
    Long endCursor,
    Long nextPage,
    Long previousPage
) {

}
