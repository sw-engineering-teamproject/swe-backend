package swe.dto.issue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public record IssueReportedResponse(
    String column,
    Long count
) {

  public static List<IssueReportedResponse> createList(final Map<LocalDate, Long> statistics) {
    return statistics.entrySet().stream()
        .sorted(Entry.comparingByKey())
        .map(entry -> new IssueReportedResponse(entry.getKey().toString(), entry.getValue()))
        .toList();
  }
}
