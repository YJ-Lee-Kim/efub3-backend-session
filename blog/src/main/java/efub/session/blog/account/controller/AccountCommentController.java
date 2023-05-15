package efub.session.blog.account.controller;

import efub.session.blog.account.domain.Account;
import efub.session.blog.account.dto.resposne.AccountCommentsResponseDto;
import efub.session.blog.account.service.AccountService;
import efub.session.blog.comment.domain.Comment;
import efub.session.blog.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/{accountId}/comments")
@RequiredArgsConstructor
public class AccountCommentController {

    // 의존관계 : AccountCommentController -> CommentService
    private final CommentService commentService;
    // 의존관계 : AccountCommentController -> AccountService
    private final AccountService accountService;

    // 특정 유저의 댓글 목록 조회
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public AccountCommentsResponseDto readAccountComments(@PathVariable Long accountId){
        Account account = accountService.findAccountById(accountId);
        List<Comment> commentList = commentService.findCommentListByWriter(account);
        return AccountCommentsResponseDto.of(account.getNickname(), commentList);
    }

}
