package efub.session.blog.post.domain;
// data access layer에 포함

import efub.session.blog.account.domain.Account;
import efub.session.blog.comment.domain.Comment;
import efub.session.blog.global.entity.BaseTimeEntity;
import efub.session.blog.heart.domain.PostHeart;
import efub.session.blog.post.dto.request.PostModifyRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    // mappedBy : 연관 관계의 주인(Owner)
    // cascade : 엔티티 삭제 시 연관된 엔티티의 처리 방식
    // orphanRemoval : 고아 객체(연관된 부모 엔티티가 없는 자식 엔티티, foreign key가 null인 경우)의 처리 방식
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostHeart> postHeartList = new ArrayList<>();

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
