package swe.dto.issue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import swe.issue.domain.IssuePriority;
import swe.issue.domain.IssueStatus;

public record IssueReportedResponse(
    String column,
    Long count
) {

  public static List<IssueReportedResponse> createListByLocalDate(
      final Map<LocalDate, Long> statistics) {
    return statistics.entrySet().stream()
        .sorted(Entry.comparingByKey())
        .map(entry -> new IssueReportedResponse(entry.getKey().toString(), entry.getValue()))
        .toList();
  }

  public static List<IssueReportedResponse> createListByStatus(
      final Map<IssueStatus, Long> statistics
  ) {
    return statistics.entrySet().stream()
        .map(entry -> new IssueReportedResponse(entry.getKey().getName(), entry.getValue()))
        .toList();
  }

  public static List<IssueReportedResponse> createListByPriority(
      final Map<IssuePriority, Long> statistics
  ) {
    return statistics.entrySet().stream()
        .map(entry -> new IssueReportedResponse(entry.getKey().getName(), entry.getValue()))
        .toList();
  }
}
