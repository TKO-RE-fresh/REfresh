package tko.refresh.repository.member;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import tko.refresh.domain.enu.MemberStatus;
import tko.refresh.dto.admin.MemberDto;
import tko.refresh.dto.admin.MemberSearchDto;

import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;
import static tko.refresh.domain.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MemberDto> allMemberPage(Pageable pageable) {
        List<MemberDto> content = queryFactory
                .select(Projections.constructor(MemberDto.class,
                        member.memberId,
                        member.memberInfo.name.as("memberName"),
                        member.department.name.as("departmentName"),
                        member.memberInfo.cellphone.as("memberCellphone"),
                        member.memberInfo.email.as("memberEmail"),
                        member.createdDate,
                        member.retireDate,
                        member.memberStatus
                ))
                .from(member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = getCount();

        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public Page<MemberDto> searchMemberPage(MemberSearchDto searchDto, Pageable pageable) {
        List<MemberDto> content = queryFactory
                .select(Projections.constructor(MemberDto.class,
                        member.memberId,
                        member.memberInfo.name.as("memberName"),
                        member.department.name.as("departmentName"),
                        member.memberInfo.cellphone.as("memberCellphone"),
                        member.memberInfo.email.as("memberEmail"),
                        member.createdDate,
                        member.retireDate,
                        member.memberStatus
                        ))
                .from(member)
                .where(memberNameEq(searchDto.getMemberName()),
                        departmentNameEq(searchDto.getDepartmentName()),
                        memberStatusEq(searchDto.getStatus()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = getCount(searchDto);

        return new PageImpl<>(content, pageable, count);
    }

    private Long getCount(MemberSearchDto...searchDto) {
        Long count;
        if(searchDto.length == 0) { // 사원 전체 조회
            count = queryFactory
                    .select(member.count())
                    .from(member)
                    .where()
                    .fetchOne();
        } else { // 사원 검색
            count = queryFactory
                    .select(member.count())
                    .from(member)
                    .where(memberNameEq(searchDto[0].getMemberName()),
                            departmentNameEq(searchDto[0].getDepartmentName()),
                            memberStatusEq(searchDto[0].getStatus()))
                    .fetchOne();
        }
        return count;
    }

    private BooleanExpression memberNameEq(String memberName) {
        return isEmpty(memberName) ? null : member.memberInfo.name.contains(memberName);
    }

    private BooleanExpression departmentNameEq(String departmentName) {
        return isEmpty(departmentName) ? null : member.department.name.eq(departmentName);
    }

    private BooleanExpression memberStatusEq(MemberStatus status) {
        return status == null ? null : member.memberStatus.eq(status);
    }

}
