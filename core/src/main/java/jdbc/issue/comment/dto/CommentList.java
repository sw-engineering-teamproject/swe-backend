package jdbc.issue.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CommentList {
    String nickname;
    String content;
    String time;
}
