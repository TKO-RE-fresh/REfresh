package tko.refresh.repository.admin;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import tko.refresh.domain.entity.Annual;
import tko.refresh.domain.entity.QAnnual;
import tko.refresh.domain.entity.QDepartment;
import tko.refresh.domain.entity.QMember;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.dto.admin.AnnualManageDto;
import tko.refresh.dto.admin.AnnualSearchDto;


import java.sql.SQLOutput;
import java.util.List;

@RequiredArgsConstructor
public class AnnualManageRepositoryImpl implements AnnualManageRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QAnnual annual = QAnnual.annual;
    QMember member = QMember.member;
    QDepartment department = QDepartment.department;




    @Override
    public Page<AnnualManageDto> searchAnnual(AnnualSearchDto searchDto, Pageable pageable) {
        List<AnnualManageDto> content = searchAnnualList(searchDto,pageable);
        Long count = getCount(searchDto);
        return new PageImpl<>(content,pageable,count);
    }

    private Long getCount(AnnualSearchDto searchDto){
        return queryFactory
                .select(annual.count())
                .from(annual)
                .join(annual.member, member)
                .join(member.department,department)
                .where(
                        memberNameEq(searchDto.getMemberName()),
                        departmentNameEq(searchDto.getDepartmentName()),
                        annualStatusEq(searchDto.getStatus())
                )
                .fetchOne();
    }

    public List<AnnualManageDto> searchAnnualList(AnnualSearchDto searchDto, Pageable pageable) {

         return queryFactory
                .select(Projections.constructor(AnnualManageDto.class,
                        annual.uid,
                        annual.member.memberInfo.name,
                        annual.member.memberInfo.email,
                        annual.member.department.name,
                        annual.annualType,
                        annual.annualStatus,
                        annual.period,
                        annual.createdDate,
                        annual.rejectReason
                ))
                .from(annual)
                .leftJoin(annual.member, member)
                .leftJoin(member.department, department)
                .where(
                        memberNameEq(searchDto.getMemberName()),
                        departmentNameEq(searchDto.getDepartmentName()),
                        annualStatusEq(searchDto.getStatus())
                )
                .orderBy(annual.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


    private BooleanExpression memberNameEq(String memberName){
        return memberName == null ? null :member.memberInfo.name.contains(memberName);
    }

    private BooleanExpression departmentNameEq(String departmentName){
        return departmentName == null ? null : department.name.eq(departmentName);
    }

    private BooleanExpression annualStatusEq(AnnualStatus status){
        return status == null ? null : annual.annualStatus.eq(status);
    }


}
