package efub.session.blog.post.domain;
// data access layer에 포함

import efub.session.blog.account.domain.Account;
import efub.session.blog.global.entity.BaseTimeEntity;
import efub.session.blog.post.dto.PostModifyRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Post extends BaseTimeEntity {
    // BaseTimeEntity를 상속하여 생성시각과 수정시각 저장

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long postId;

    @Column
    private String title;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "account_id")  //account_id가 foreign key임을 명시
    private Account writer;  //post에서 쓰이는 account는 작성자이므로 writer로 명명

    @Builder
    public Post(Long postId, String title, String content, Account writer) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void updatePost(PostModifyRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
