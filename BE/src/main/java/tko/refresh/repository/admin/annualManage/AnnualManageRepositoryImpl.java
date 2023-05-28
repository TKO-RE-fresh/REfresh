package tko.refresh.repository.admin.annualManage;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import tko.refresh.domain.entity.Annual;
import tko.refresh.domain.entity.QAnnual;
import tko.refresh.domain.entity.QDepartment;
import tko.refresh.domain.entity.QMember;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.dto.admin.AnnualSearchDto;


import java.util.List;

@RequiredArgsConstructor
public class AnnualManageRepositoryImpl implements AnnualManageRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QAnnual annual = QAnnual.annual;
    QMember member = QMember.member;
    QDepartment department = QDepartment.department;


    @Override
    public List<Annual> searchAnnual(AnnualSearchDto searchDto, Pageable pageable) {

        return queryFactory
                .selectFrom(annual)
                .leftJoin(annual.member, member)
                .fetchJoin()
                .leftJoin(member.department,department)
                .fetchJoin()
                .where(
                        memberNameEq(searchDto.getMemberName()),
                        departmentNameEq(searchDto.getDepartmentName()),
                        annualStatusEq(searchDto.getStatus())
                )
                .orderBy(annual.createdDate.desc())
                .fetch();
    }

    private BooleanExpression memberNameEq(String memberName){
        return memberName == null ? null :member.memberInfo.name.contains(memberName);
    }

    private BooleanExpression departmentNameEq(String departmentName){
        return departmentName == null? null : department.name.eq(departmentName);
    }

    private BooleanExpression annualStatusEq(AnnualStatus status){
        return status == null? null : annual.annualStatus.eq(status);
    }
}
