package efub.session.blog.post.repository;
//테이블(Post)에 접근하는 매개체
import efub.session.blog.account.domain.Account;
import efub.session.blog.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    // pk의 타입이 Long

    Optional<Post> findByPostIdAndWriter_AccountId(Long postId, Long accountId);

    List<Post> findAllByWriter(Account writer);
}
