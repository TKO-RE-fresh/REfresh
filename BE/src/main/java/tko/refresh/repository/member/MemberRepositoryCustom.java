package tko.refresh.repository.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.dto.admin.MemberSearchDto;

public interface MemberRepositoryCustom {

    Page<MemberDto> searchMemberPage(MemberSearchDto searchDto, Pageable pageable);

    Page<MemberDto> allMemberPage(Pageable pageable);
}
