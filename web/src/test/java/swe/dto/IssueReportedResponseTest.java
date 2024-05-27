package swe.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import swe.dto.issue.IssueReportedResponse;

class IssueReportedResponseTest {

  @Test
  void 일자별로_정렬해서_반환한다() {
    //given
    final Map<LocalDate, Long> statistics = Map.of(
        LocalDate.of(2021, 3, 11), 20L,
        LocalDate.of(2022, 4, 12), 20L,
        LocalDate.of(2024, 3, 11), 20L,
        LocalDate.of(2023, 3, 11), 20L
    );

    //when
    final List<IssueReportedResponse> actual = IssueReportedResponse.createListByLocalDate(statistics);

    //then
    final List<IssueReportedResponse> expected = List.of(
        new IssueReportedResponse("2024-03-11", 20L),
        new IssueReportedResponse("2023-03-11", 20L),
        new IssueReportedResponse("2022-04-12", 20L),
        new IssueReportedResponse("2021-03-11", 20L)
    );
    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(expected);
  }
}