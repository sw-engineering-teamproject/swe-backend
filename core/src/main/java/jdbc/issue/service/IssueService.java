package jdbc.issue.service;

import jdbc.issue.dto.AssigneeInfo;
import jdbc.issue.dto.Issue;
import jdbc.issue.dto.IssueEnroll;
import org.springframework.stereotype.Service;

import java.util.List;
import jdbc.issue.dto.IssueList;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class IssueService {
    public List<IssueList> getIssueList() {

    }

    public List<IssueList> getIssueBySearch(String criterion, String value) {

    }

    public boolean issueEnroll(IssueEnroll issue) {

    }

    public Issue getIssueInfo(int key) {

    }

    public AssigneeInfo recommendAssignee(int issueKey) {

    }

    public boolean setAssignee(int issueKey, int userKey) {
        //user가 tester인지 확인필요.

        //state를 assigned로 변경필요.
    }

    public boolean setPriority(int issueKey, String priority) {

    }

    public boolean setStatus(int issueKey, int userKey, String status) {
        //fixed-이전 상태가 assigned인지, user가 dev인지 확인필요, user를 fixer에 등록해야함.
        //resolved-이전 상태가 fixed인지, user가 tester인지 확인필요.
        //closed-이전 상태가 resolved인지, user가 PL인지 확인필요.
    }
}
