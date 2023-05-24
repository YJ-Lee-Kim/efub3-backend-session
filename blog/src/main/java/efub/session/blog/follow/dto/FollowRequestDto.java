package efub.session.blog.follow.dto;

import efub.session.blog.account.domain.Account;
import efub.session.blog.follow.domain.Follow;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 어노테이션 추가
@Getter
@NoArgsConstructor
public class FollowRequestDto {
    private Long followingId;

    public Follow toEntity(Account follower, Account following){
        return Follow.builder()
                .follower(follower)
                .following(following)
                .build();
    }
}
