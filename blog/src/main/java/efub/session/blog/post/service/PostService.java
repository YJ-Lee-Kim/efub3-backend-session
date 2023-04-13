package efub.session.blog.post.service;

import efub.session.blog.account.domain.Account;
import efub.session.blog.account.repository.AccountRepository;
import efub.session.blog.post.domain.Post;
import efub.session.blog.post.dto.PostModifyRequestDto;
import efub.session.blog.post.dto.PostRequestDto;
import efub.session.blog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


// repository만 사용

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AccountRepository accountRepository;  //writer를 Account에서 가져오기 위해

    @Transactional  //삭제
    public Post addPost(PostRequestDto requestDto) {
        Account writer = accountRepository.findById(requestDto.getAccountId())
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 계정입니다."));
        // wrapper 클래스인 Account를 확인해줘야 함 -> orElseThrow로 예외 처리

        // db에 저장된 post를 반환
        return postRepository.save(
                // builder는 아무것도 넣어주지 않아도 null값으로 들어감
                Post.builder()
                        .title(requestDto.getTitle())
                        .content(requestDto.getContent())
                        .writer(writer)
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public List<Post> findPostList() {
        return postRepository.findAll();  //List<Post>를 반환
    }

    @Transactional(readOnly = true)
    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    public void removePost(Long postId, Long accountId) {
        /*
        Post post = postRepository.findByPostIdAndWriter_AccountId(postId, accountId)
                .orElseThrow(()->new IllegalArgumentException("잘못된 접근입니다."));
        postRepository.delete(post);
        */

        // findByPostIdAndWriter_AccountId -> postId, accountId 중 어떤 것이 잘못된 것인지 구체화해서 메소드 새로 작성해보기
        // 메소드를 쪼개면 메세지도 구체적으로 적을 수 있다.
        Post post1 = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));    // 먼저 postId로 게시글을 찾는다.

        if(post1.getWriter().getAccountId().equals(accountId))
            postRepository.delete(post1);    // 게시글이 존재하고 작성자가 일치하면, 글을 삭제한다.
        else
            throw new IllegalArgumentException("해당 글의 작성자가 아닙니다.");    // 게시글은 존재하나 작성자가 일치하지 않으면, 예외를 발생시킨다.


    }


    public Post modifyPost(Long postId, PostModifyRequestDto requestDto) {
        Post post = postRepository.findByPostIdAndWriter_AccountId(postId, requestDto.getAccountId())
                .orElseThrow(()->new IllegalArgumentException("잘못된 접근입니다."));
        post.updatePost(requestDto);
        return post;
    }
}
