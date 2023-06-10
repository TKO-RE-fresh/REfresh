package tko.refresh.dto.member;

import lombok.*;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.util.converter.MemberStatusConverter;
import tko.refresh.util.converter.RoleTypeConverter;

import javax.persistence.Convert;
import javax.validation.constraints.*;

@Getter
@ToString
public class MemberJoinDto {

    @NotBlank(message = "아이디를 입력하세요.")
    @Size(min = 3, max = 10, message = "아이디를 3자 이상 10자 이하로 입력하세요")
    private String memberId;


    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 4, max = 20, message = "비밀번호를 4자 이상 20자 이하로 입력하세요")
    private String password;

    @NotNull
    @Convert(converter = RoleTypeConverter.class)
    private RoleType memberAuth;

    @NotBlank(message = "이름을 입력하세요")
    private String memberName;

    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "전화번호 형식을 확인하세요 : XXX-XXXX-XXXX")
    @NotBlank(message = "전화번호를 입력하세요")
    private String memberCellphone;

    @Email(message = "이메일 형식을 확인하세요.")
    @NotBlank(message = "이메일을 입력하세요")
    private String memberEmail;

    @NotNull
    @Convert(converter = MemberStatusConverter.class)
    private MemberStatus memberStatus;

    private double annualCount;

    @NotNull(message = "부서를 선택하세요")
    private String departmentName;

    public MemberJoinDto(){

    }
    
    @Builder
    public MemberJoinDto(String memberId, String password, RoleType memberAuth, String memberName, String memberCellphone, String memberEmail, MemberStatus memberStatus, double annualCount, String departmentName, String createdDate) {
        this.memberId = memberId;
        this.password = password;
        this.memberAuth = memberAuth;
        this.memberName = memberName;
        this.memberCellphone = memberCellphone;
        this.memberEmail = memberEmail;
        this.memberStatus = memberStatus;
        this.annualCount = annualCount;
        this.departmentName = departmentName;
        this.createdDate = createdDate;
    }

    private String createdDate;

    public void setEncodePwd(String encode) {
        this.password = encode;
    }
}
