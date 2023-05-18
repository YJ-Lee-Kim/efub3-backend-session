package efub.session.blog.comment.service;

import efub.session.blog.account.domain.Account;
import efub.session.blog.account.service.AccountService;
import efub.session.blog.comment.domain.Comment;
import efub.session.blog.comment.dto.CommentRequestDto;
import efub.session.blog.comment.repository.CommentRepository;
import efub.session.blog.post.domain.Post;
import efub.session.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    // 의존관계 : CommentService -> CommentRepository
    private final CommentRepository commentRepository;

    // 의존관계 : CommentService -> PostService
    private final PostService postService;
    // 의존관계 : CommentService -> AccountService
    private final AccountService accountService;

    // 댓글 작성
    public Long createComment(Long postId, CommentRequestDto requestDto){
        Post post = postService.findPost(postId);
        Account writer = accountService.findAccountById(requestDto.getAccountId());
        return commentRepository.save(requestDto.toEntity(post, writer)).getCommentId();
    }

    @Transactional(readOnly = true)
    // 댓글 조회 - ID별
    public Comment findCommentById(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 댓글입니다. id="+commentId));
    }

    @Transactional(readOnly = true)
    // 댓글 목록 조회 - 작성자별
    public List<Comment> findCommentListByWriter(Account writer){
        return commentRepository.findAllByWriter(writer);
    }

    // 댓글 목록 조회 - 게시글별
    public List<Comment> findCommentListByPost(Long postId){
        Post post = postService.findPost(postId);
        return commentRepository.findAllByPost(post);
    }

    // 댓글 수정
    public void updateComment(CommentRequestDto requestDto, Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 댓글입니다."));
        comment.updateComment(requestDto.getContent());
    }

    // 댓글 삭제
    public void deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 댓글입니다."));
        commentRepository.delete(comment);
    }
}
