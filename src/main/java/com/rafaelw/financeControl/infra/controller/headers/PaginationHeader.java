package com.rafaelw.financeControl.infra.controller.headers;

import com.rafaelw.financeControl.application.utils.PaginatedResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class PaginationHeader {

  public <T> HttpHeaders execute(PaginatedResponse<T> data) {
    HttpHeaders header = new HttpHeaders();
    header.add("X-Start-Cursor", String.valueOf(data.startCursor()));
    header.add("X-End-Cursor", String.valueOf(data.endCursor()));
    if (data.nextPage() != null) {
      header.add("X-Next-Page", String.valueOf(data.nextPage()));
    }
    if (data.previousPage() != null) {
      header.add("X-Previous-Page", String.valueOf(data.previousPage()));
    }

    return header;
  }

}
