package swe.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class IssueReportedDateResponseTest {

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
    final List<IssueReportedDateResponse> actual = IssueReportedDateResponse.createList(statistics);

    //then
    final List<IssueReportedDateResponse> expected = List.of(
        new IssueReportedDateResponse(LocalDate.of(2024, 3, 11), 20L),
        new IssueReportedDateResponse(LocalDate.of(2023, 3, 11), 20L),
        new IssueReportedDateResponse(LocalDate.of(2022, 4, 12), 20L),
        new IssueReportedDateResponse(LocalDate.of(2021, 3, 11), 20L)
    );
    assertThat(actual)
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(expected);
  }
}