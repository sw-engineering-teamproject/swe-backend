package jdbc.issue;

import jdbc.issue.comment.dto.CommentList;
import jdbc.issue.comment.service.CommentService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {
    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //comment 등록 api
    public boolean enrollComment(int issueKey, int userKey, String comment) {
        return commentService.enrollComment(issueKey, userKey, comment);
    }

    //이전 comment들 확인 api
    public List<CommentList> getComments(int issueKey) {
        return commentService.getComments(issueKey);
    }
}
