package tko.refresh.service.admin;

import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tko.refresh.domain.entity.Department;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.dto.admin.*;
import tko.refresh.dto.member.AuthMemberDto;
import tko.refresh.repository.department.MemberDepartmentRepository;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.util.jwt.JwtAuthMember;
import tko.refresh.util.page.Pagination;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
=======
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tko.refresh.domain.entity.Member;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.repository.member.MemberRepository;

import java.util.ArrayList;
import java.util.List;
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3

@Service
@RequiredArgsConstructor
public class MemberService {
<<<<<<< HEAD
    private final int PAGE_SIZE = 5;

    private final MemberRepository memberRepository;

    private final MemberDepartmentRepository memberDepartmentRepository;

    private final MemberDepartmentRepository departmentRepository;

    private final JwtAuthMember jwtAuthMember;

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

    public MemberDetailDto getMemberDetail(String memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);

        List<MemberStatus> memberStatuses = new ArrayList<>();
        for(MemberStatus status : MemberStatus.values()) {
            memberStatuses.add(status);
        }

        List<RoleType> roleTypes = new ArrayList<>();
        for(RoleType role : RoleType.values()) {
            roleTypes.add(role);
        }

        MemberDetailDto memberDetailDto = MemberDetailDto.builder()
                .memberId(member.get().getMemberId())
                .memberName(member.get().getMemberInfo().getName())
                .departmentName(member.get().getDepartment().getName())
                .memberCellphone(member.get().getMemberInfo().getCellphone())
                .memberEmail(member.get().getMemberInfo().getEmail())
                .annualCount(member.get().getAnnualCount())
                .createdBy(member.get().getCreatedBy())
                .modifiedBy(member.get().getModifiedBy())
                .createdDate(member.get().getCreatedDate())
                .modifiedDate(member.get().getModifiedDate())
                .retireDate(member.get().getRetireDate())
                .memberStatus(member.get().getMemberStatus())
                .memberAuth(member.get().getMemberAuth())
                .departmentNameList(memberDepartmentRepository.getDepartmentNameList())
                .memberStatusList(memberStatuses)
                .roleTypeList(roleTypes)
                .build();

        return memberDetailDto;
    }

    @Transactional
    public void editMember(String memberId, MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow();
        Department department = memberDepartmentRepository.getDepartmentByName(memberUpdateDto.getDepartmentName());
        AuthMemberDto authMemberDto = jwtAuthMember.getJwtAuthMember();

        member.updateMember(memberUpdateDto, department, authMemberDto.getMemberId());
    }

    public SearchFormDto getSearchForm() {

        List<String> departmentNameList = departmentRepository.getDepartmentNameList();

        List<MemberStatus> memberStatuses = new ArrayList<>();
        for(MemberStatus status : MemberStatus.values()) {
            memberStatuses.add(status);
        }

        List<RoleType> memberAuth = new ArrayList<>();
        for(RoleType role : RoleType.values()) {
            memberAuth.add(role);
        }

        SearchFormDto searchFormDto = SearchFormDto.builder()
                .departmentNameList(departmentNameList)
                .memberStatusList(memberStatuses)
                .memberAuthList(memberAuth)
                .build();

        return searchFormDto;
    }


=======
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
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
}

