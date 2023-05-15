package efub.session.blog.post.dto.response;

import efub.session.blog.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*
{
    "postId": 4,
    "writerName": "퍼비",
    "title": "나는 퍼비",
    "content": "오늘 이펍 세션이 있는 날이다. ",
    "createdDate": "2023-02-21T12:50:12.666667",
    "modifiedDate": "2023-02-21T12:50:12.666667"
}
 */

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String writerName;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PostResponseDto (Post post) {
        this.postId = post.getPostId();
        this.writerName = post.getWriter().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
    }

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(post);
    }
}
