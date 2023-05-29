package tko.refresh.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tko.refresh.util.converter.DateConverter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@ToString
public class MemberDto {

    private final String FORMAT = "YYYY년 MM월 dd일";

    @NotEmpty
    private String memberId;
    @NotEmpty
    private String memberName;
    @NotEmpty
    private String departmentName;
    @NotEmpty
    private String memberCellphone;
    @NotEmpty
    private String memberEmail;
    @NotEmpty
    private String createdDate;
    private String retireDate;
    @NotEmpty
    private String memberStatus;

    @Builder
    public MemberDto(String memberId, String memberName, String departmentName, String memberCellphone, String memberEmail, String createdDate, String retireDate, String memberStatus) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.departmentName = departmentName;
        this.memberCellphone = memberCellphone;
        this.memberEmail = memberEmail;
        this.createdDate = DateConverter.format(LocalDateTime.parse(createdDate), FORMAT);
        this.retireDate = (retireDate.equals("null")) ? null : DateConverter.format(LocalDateTime.parse(retireDate), FORMAT);
        this.memberStatus = memberStatus;
    }
}
