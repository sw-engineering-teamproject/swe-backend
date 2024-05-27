package swe.dto;

import static java.util.Comparator.comparing;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record IssueReportedDateResponse(LocalDate reportedDate, Long count) {

  public static List<IssueReportedDateResponse> createList(final Map<LocalDate, Long> statistics) {
    return statistics.entrySet().stream()
        .map(entry -> new IssueReportedDateResponse(entry.getKey(), entry.getValue()))
        .sorted(comparing(IssueReportedDateResponse::reportedDate))
        .toList();
  }
}
