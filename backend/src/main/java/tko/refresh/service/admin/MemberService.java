package tko.refresh.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tko.refresh.domain.entity.Department;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.domain.enu.RoleType;
import tko.refresh.dto.admin.MemberDetailDto;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.dto.admin.MemberSearchDto;
import tko.refresh.dto.admin.MemberUpdateDto;
import tko.refresh.repository.department.MemberDepartmentRepository;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.util.page.Pagination;

import javax.transaction.Transactional;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final int PAGE_SIZE = 1;

    private final MemberRepository memberRepository;

    private final MemberDepartmentRepository memberDepartmentRepository;

    public Page<MemberDto> getAllMemberList(int page) {
        Pageable pageable = Pagination.setPageable(page,PAGE_SIZE);
        Page<MemberDto> list = memberRepository.allMemberPage(pageable);
        System.out.println("조회 결과 : " + list);
        System.out.println(list.getContent());
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
        System.out.println("memberUpdatedto.getdepartmentName : " + memberUpdateDto.getDepartmentName());
        System.out.println("부서 정보 : " + department);
//        UUID uuid = UUID.fromString(String.valueOf(department.getUid()));
//        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
//        bb.putLong(uuid.getMostSignificantBits());
//        bb.putLong(uuid.getLeastSignificantBits());
//        byte[] uuidBytes = bb.array();

//        String hexUUID = String.valueOf(department.getUid());
//        UUID uuid = UUID.fromString(hexUUID);
//        System.out.println(hexUUID);
//        System.out.println("인사팀 uuid : " + uuid);

        member.updateMember(memberUpdateDto, department);
    }
}

