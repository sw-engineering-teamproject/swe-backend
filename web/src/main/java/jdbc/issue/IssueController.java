package jdbc.issue;

import jdbc.issue.dto.AssigneeInfo;
import jdbc.issue.dto.Issue;
import jdbc.issue.dto.IssueEnroll;
import jdbc.issue.service.IssueService;
import jdbc.issue.dto.IssueList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IssueController {
    IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    //이슈 목록 조회 api
    @GetMapping("/issue/list")
    public List<IssueList> getIssueList() {
        return issueService.getIssueList();
    }

    //criterion : 기준
    //이슈 검색 api
    @GetMapping("/issue/search")
    public List<IssueList> getIssueBySearch(@RequestParam String criterion, @RequestParam String value) {
        return issueService.getIssueBySearch(criterion, value);
    }

    //이슈 등록 api
    @PostMapping("/issue/enroll")
    public boolean issueEnroll(@RequestBody IssueEnroll issue) {
        return issueService.issueEnroll(issue);
    }

    //이슈 정보조회 api
    @GetMapping("/issue")
    public Issue getIssueInfo(int key) {
        return issueService.getIssueInfo(key);
    }

    //assignee 추천 api
    public AssigneeInfo recommendAssignee(int issueKey) {
        return issueService.recommendAssignee(issueKey);
    }

    //assignee 지정 api
    public boolean setAssignee(int issueKey, int userKey) {
        return issueService.setAssignee(issueKey, userKey);
    }

    //이슈 우선순위 변경 api
    public boolean setPriority(int issueKey, String priority) {
        return issueService.setPriority(issueKey, priority);
    }

    //이슈 상태변경 api
    public boolean setStatus(int issueKey, int userKey, String status) {
        return issueService.setStatus(issueKey, userKey, status);
    }
}
