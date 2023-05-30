package tko.refresh.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tko.refresh.domain.entity.Annual;
import tko.refresh.domain.entity.Member;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.repository.member.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final int PAGE_SIZE = 10;

    private final MemberRepository memberRepository;

    public List<MemberDto> getAllMemberList(int page) {
        List<MemberDto> resultList = new ArrayList<>();
        Pageable pageable = Pageable.ofSize(PAGE_SIZE).withPage(page);
        List<Member> memberList = memberRepository.findAll();

        for (Member data : memberList) {
            resultList.add(MemberDto.builder()
                    .memberId(data.getMemberId())
                    .memberName(data.getMemberInfo().getName())
                    .departmentName(data.getDepartment().getName())
                    .memberCellphone(data.getMemberInfo().getCellphone())
                    .memberEmail(data.getMemberInfo().getEmail())
                    .createdDate(String.valueOf(data.getCreatedDate()))
                    .retireDate(String.valueOf(data.getRetireDate()))
                    .memberStatus(String.valueOf(data.getMemberStatus()))
                    .build()
            );
        }
        return resultList;
    }

}

