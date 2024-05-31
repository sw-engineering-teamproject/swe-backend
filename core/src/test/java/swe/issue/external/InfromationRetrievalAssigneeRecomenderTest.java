package swe.issue.external;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import swe.issue.domain.Issue;
import swe.issue.domain.IssueRepository;
import swe.support.ServiceTest;

class InfromationRetrievalAssigneeRecomenderTest extends ServiceTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private IssueRepository issueRepository;


  @Autowired
  private InformationRetrievalAssigneeRecommender informationRetrievalAssigneeRecommender;

  @Test
  void 크로마_디비에_새_이슈들을_추가한다() {
    createInitData();
    //given
    final long issueId = 1L;

    final Issue issue = issueRepository.findById(issueId).get();

    //when
    try {
      informationRetrievalAssigneeRecommender.addNewIssuesToVectorDB(
          List.of(issue));
    }
    //then
    catch (Exception e) {
      Assertions.assertThat(e).isNull();
    }
  }

  @Test
  void 담당자_세_명을_추천한다() {
    createInitData();
    //given
    final long issueId = 1L;

    //when
    final var recommendedUsers = informationRetrievalAssigneeRecommender.recommend(1L,
        issueRepository.findById(issueId).get());

    //then
    assertThat(recommendedUsers).hasSize(3);
  }

  public void createInitData() {
    String filePath = "initdatasql.txt";
    String insertDataSql = null;

    try {
      ClassLoader classLoader = getClass().getClassLoader();
      insertDataSql = new String(
          Files.readAllBytes(Paths.get(classLoader.getResource(filePath).toURI())));
    } catch (Exception e) {
      Assertions.assertThat(e).isNull();
    }

    Assertions.assertThat(insertDataSql).isNotNull();
    jdbcTemplate.update(insertDataSql);
  }
}
