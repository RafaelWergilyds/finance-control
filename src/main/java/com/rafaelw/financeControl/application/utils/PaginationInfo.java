package com.rafaelw.financeControl.application.utils;

public record PaginationInfo(Long startCursor, Long endCursor, Long nextPage, Long previousPage) {

}
