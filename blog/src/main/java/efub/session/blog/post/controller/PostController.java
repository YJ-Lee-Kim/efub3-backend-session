package efub.session.blog.post.controller;

import efub.session.blog.post.domain.Post;
import efub.session.blog.post.dto.PostModifyRequestDto;
import efub.session.blog.post.dto.PostRequestDto;
import efub.session.blog.post.dto.PostResponseDto;
import efub.session.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


// presentation layer에 해당
// repository를 만들면 안 됨
// service만 사용

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostResponseDto postAdd(@RequestBody PostRequestDto requestDto){
        Post post = postService.addPost(requestDto);  //요청을 받아서 post를 생성하고 그 post를 반환

        return new PostResponseDto(post);
    }

    // 전체 조회
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<PostResponseDto> postListFind(){
        //request body 없음

        /*
        List<Post> postList = postService.findPostList();
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        for(Post post : postList){
            responseDtoList.add(new PostResponseDto(post));

        return responseDtoList;
        }*/

        // for문을 stream으로 바꿔보기
        List<Post> postList = postService.findPostList();
        return postList.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    // 개별 조회
    @GetMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto postFind(@PathVariable Long postId){
        Post post = postService.findPost(postId);
        return new PostResponseDto(post);
    }

    // 삭제
    @DeleteMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String postRemove(@PathVariable Long postId, @RequestParam Long accountId){
        postService.removePost(postId, accountId);
        return "성공적으로 삭제되었습니다.";
    }

    // 수정
    @PutMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK)
    public PostResponseDto postModify(@PathVariable Long postId, @RequestBody PostModifyRequestDto requestDto){
        Post post = postService.modifyPost(postId, requestDto);
        return new PostResponseDto(post);
    }
}
