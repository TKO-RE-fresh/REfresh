package tko.refresh.repository.admin;

<<<<<<< HEAD
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
=======
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
import org.springframework.data.domain.Pageable;
import tko.refresh.domain.entity.Annual;
import tko.refresh.domain.entity.QAnnual;
import tko.refresh.domain.entity.QDepartment;
import tko.refresh.domain.entity.QMember;
import tko.refresh.domain.enu.AnnualStatus;
<<<<<<< HEAD
import tko.refresh.dto.admin.AnnualManageDto;
import tko.refresh.dto.admin.AnnualSearchDto;


import java.sql.SQLOutput;
=======
import tko.refresh.dto.admin.AnnualSearchDto;


>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
import java.util.List;

@RequiredArgsConstructor
public class AnnualManageRepositoryImpl implements AnnualManageRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QAnnual annual = QAnnual.annual;
    QMember member = QMember.member;
    QDepartment department = QDepartment.department;


<<<<<<< HEAD


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
                .leftJoin(annual.member, member)
                .leftJoin(member.department,department)
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
                .leftJoin(annual.member.department, department)
=======
    @Override
    public List<Annual> searchAnnual(AnnualSearchDto searchDto, Pageable pageable) {

        return queryFactory
                .selectFrom(annual)
                .leftJoin(annual.member, member)
                .fetchJoin()
                .leftJoin(member.department,department)
                .fetchJoin()
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
                .where(
                        memberNameEq(searchDto.getMemberName()),
                        departmentNameEq(searchDto.getDepartmentName()),
                        annualStatusEq(searchDto.getStatus())
                )
                .orderBy(annual.createdDate.desc())
<<<<<<< HEAD
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }


    private BooleanExpression memberNameEq(String memberName){
        return memberName.isEmpty() ? null :member.memberInfo.name.contains(memberName);
    }

    private BooleanExpression departmentNameEq(String departmentName){
        return departmentName.isEmpty() ? null : department.name.eq(departmentName);
    }

    private BooleanExpression annualStatusEq(AnnualStatus status){
        return status == null ? null : annual.annualStatus.eq(status);
    }


=======
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
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
}
