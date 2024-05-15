package jdbc.issue.service;

import jdbc.issue.dto.AssigneeInfo;
import jdbc.issue.dto.Issue;
import jdbc.issue.dto.IssueEnroll;
import jdbc.issue.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import jdbc.issue.dto.IssueList;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class IssueService {
    IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public List<IssueList> getIssueList() {
        return issueRepository.getIssueList();
    }

    public List<IssueList> getIssueBySearch(String criterion, String value) {
        return issueRepository.getIssueBySearch(criterion, value);
    }

    public boolean issueEnroll(IssueEnroll issue) {
        return issueRepository.issueEnroll(issue);
    }

    public Issue getIssueInfo(int key) {
        return issueRepository.getIssueInfo(key);
    }

    public boolean setPriority(int issueKey, String priority) {
        return issueRepository.setPriority(issueKey, priority);
    }

    public boolean setStatus(int issueKey, int userKey, String status) {
        return issueRepository.setStatus(issueKey, userKey, status);
    }
}
