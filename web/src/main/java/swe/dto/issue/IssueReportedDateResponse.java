package swe.dto.issue;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static java.util.Comparator.comparing;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record IssueReportedDateResponse(
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate reportedDate,
    Long count
) {

  public static List<IssueReportedDateResponse> createList(final Map<LocalDate, Long> statistics) {
    return statistics.entrySet().stream()
        .map(entry -> new IssueReportedDateResponse(entry.getKey(), entry.getValue()))
        .sorted(comparing(IssueReportedDateResponse::reportedDate))
        .toList();
  }
}
