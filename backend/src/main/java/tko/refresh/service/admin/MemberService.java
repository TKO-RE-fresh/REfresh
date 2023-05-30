package tko.refresh.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.dto.admin.MemberSearchDto;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.util.page.Pagination;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final int PAGE_SIZE = 10;

    private final MemberRepository memberRepository;

    public Page<MemberDto> getAllMemberList(int page) {
        Pageable pageable = Pagination.setPageable(page,PAGE_SIZE);
        Page<MemberDto> list = memberRepository.allMemberPage(pageable);

        return list;
    }

    public Page<MemberDto> getSearchMemberList(MemberSearchDto searchDto, int page) {
        Pageable pageable = Pagination.setPageable(page,PAGE_SIZE);
        Page<MemberDto> memberSearchList = memberRepository.searchMemberPage(searchDto, pageable);

        return memberSearchList;
    }

}

