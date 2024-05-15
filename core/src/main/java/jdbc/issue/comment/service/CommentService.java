package jdbc.issue.comment.service;

import jdbc.issue.comment.dto.CommentList;
import jdbc.issue.comment.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public boolean enrollComment(int issueKey, int userKey, String comment) {
        return commentRepository.enrollComment(issueKey, userKey, comment);
    }

    public List<CommentList> getComments(int issueKey) {
        return commentRepository.getComments(issueKey);
    }
}
