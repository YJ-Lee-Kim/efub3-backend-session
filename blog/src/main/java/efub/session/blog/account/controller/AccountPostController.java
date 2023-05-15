package efub.session.blog.account.controller;

import efub.session.blog.account.service.AccountService;
import efub.session.blog.post.domain.Post;
import efub.session.blog.post.dto.response.PostListResponseDto;
import efub.session.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/posts")
@RequiredArgsConstructor
public class AccountPostController {

    // 의존관계 : AccountCommentController -> PostService
    private final PostService postService;
    // 의존관계 : AccountCommentController -> AccountService
    private final AccountService accountService;

    // 특정 유저의 게시글 목록 조회
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PostListResponseDto readAccountPosts(@PathVariable Long accountId) {
        List<Post> postList = postService.findPostListByWriter(accountId);
        return PostListResponseDto.of(postList);
    }
}