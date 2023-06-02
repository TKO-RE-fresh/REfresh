package tko.refresh.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tko.refresh.domain.entity.Member;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.dto.admin.MemberSearchDto;
import tko.refresh.dto.admin.MemberUpdateDto;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.util.page.Pagination;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final int PAGE_SIZE = 1;

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

    public MemberDto getMemberDetail(String memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);

        MemberDto memberDto = MemberDto.builder()
                .memberId(member.get().getMemberId())
                .memberName(member.get().getMemberInfo().getName())
                .departmentName(member.get().getDepartment().getName())
                .memberCellphone(member.get().getMemberInfo().getCellphone())
                .memberEmail(member.get().getMemberInfo().getEmail())
                .createdDate(member.get().getCreatedDate())
                .retireDate(member.get().getRetireDate())
                .memberStatus(member.get().getMemberStatus())
                .build();

        return memberDto;
    }

    @Transactional
    public void modifyMember(String memberId, MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow();
        member.updateMember(memberUpdateDto);
    }
}

