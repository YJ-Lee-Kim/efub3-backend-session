package efub.session.blog.account.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountInfoRequestDto {
    @NotNull(message = "작성자는 필수로 입력되어야 합니다.")
    private Long accountId;

    public AccountInfoRequestDto(Long accountId){
        this.accountId = accountId;
    }
}
