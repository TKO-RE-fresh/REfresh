package tko.refresh.repository.myPage;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.dto.myPage.AnnualHistoryDto;
import tko.refresh.dto.myPage.HistorySearchDto;
import tko.refresh.util.jwt.JwtAuthMember;

import java.util.List;

import static tko.refresh.domain.entity.QAnnual.annual;
import static tko.refresh.domain.entity.QMember.member;

@RequiredArgsConstructor
public class AnnualHistoryRepositoryImpl implements AnnualHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final JwtAuthMember jwtAuthMember;

    @Override
    public Page<AnnualHistoryDto> getAnnualHistoryPage(HistorySearchDto searchDto, Pageable pageable) {
        List<AnnualHistoryDto> content = getAnnualHistoryList(searchDto,pageable);
        Long count = getCount(searchDto);
        return new PageImpl<>(content,pageable,count);
    }

    public List<AnnualHistoryDto> getAnnualHistoryList(HistorySearchDto searchDto, Pageable pageable){
        return queryFactory
                .select(Projections.constructor(AnnualHistoryDto.class,
                        annual.uid,
                        annual.annualType,
                        annual.annualStatus,
                        JPAExpressions
                                .select(member.memberInfo.name)
                                .from(member)
                                .where(member.memberId.eq(annual.acceptor)),
                        JPAExpressions
                                .select(member.memberInfo.email)
                                .from(member)
                                .where(member.memberId.eq(annual.acceptor)),
                        annual.period,
                        annual.createdDate,
                        annual.modifiedDate,
                        annual.rejectReason))
                .from(annual)
                .where(annual.member.memberId.eq(jwtAuthMember.getJwtAuthMember().getMemberId()),
                        yearEq(searchDto.getYear()),
                        annualTypeEq(searchDto.getType()),
                        annualStatusEq(searchDto.getStatus()))
                .orderBy(annual.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private Long getCount(HistorySearchDto searchDto){
        return queryFactory
                .select(annual.count())
                .from(annual)
                .where(annual.member.memberId.eq(jwtAuthMember.getJwtAuthMember().getMemberId()),
                        yearEq(searchDto.getYear()),
                        annualTypeEq(searchDto.getType()),
                        annualStatusEq(searchDto.getStatus()))
                .fetchOne();
    }

    private BooleanExpression yearEq(int year){
        return year == 0 ?  null : annual.period.startDate.year().eq(year);
    }

   private BooleanExpression annualTypeEq(AnnualType type){
        return type == null ? null : annual.annualType.eq(type);
   }

    private BooleanExpression annualStatusEq(AnnualStatus status){
        return status == null ? null : annual.annualStatus.eq(status);
    }
}
