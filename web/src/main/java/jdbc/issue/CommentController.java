package jdbc.issue;

import jdbc.issue.comment.dto.CommentList;
import jdbc.issue.comment.service.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {
    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //comment 등록 api
    @GetMapping("/comment/enroll")
    public boolean enrollComment(@RequestParam int issueKey, @RequestParam int userKey, @RequestParam String comment) {
        return commentService.enrollComment(issueKey, userKey, comment);
    }

    //이전 comment들 확인 api
    @GetMapping("/comment/list")
    public List<CommentList> getComments(@RequestParam int issueKey) {
        return commentService.getComments(issueKey);
    }
}
