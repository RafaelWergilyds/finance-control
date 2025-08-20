package com.rafaelw.financeControl.application.dto.debit;

public record PaginationDTO(Integer pageNumber, Integer pageSize) {

  public PaginationDTO {
    if (pageNumber == null) {
      pageNumber = 0;
    }
    if (pageSize == null) {
      pageNumber = 10;
    }
  }

}
