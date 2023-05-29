package tko.refresh.repository.calendar;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.Annual;
import tko.refresh.dto.calendar.request.annual.GetAnnualByDateAndDeptReqDto;
import tko.refresh.dto.calendar.request.annual.GetAnnualByDeptNameReqDto;
import tko.refresh.dto.calendar.request.annual.GetAnnualByMemberNameReqDto;
import tko.refresh.dto.calendar.response.annual.AnnualResponse;
import tko.refresh.dto.calendar.response.annual.GetAnnualByDateAndDeptResDto;
import tko.refresh.dto.calendar.response.annual.GetAnnualByNameResDto;
import tko.refresh.dto.calendar.response.annual.AnnualResponseDto;

@Repository
public class AnnualEmRepository {

    @PersistenceContext
    private EntityManager entityManager;



    public AnnualResponseDto<AnnualResponse> findAnnualByDept(GetAnnualByDeptNameReqDto dto) {
        String countQuery = "SELECT COUNT(a) FROM Annual a JOIN a.member m JOIN m.department d "
                            + "WHERE d.name = :department and a.annualStatus = '승인'";
        String jpql = "SELECT a FROM Annual a JOIN FETCH a.member m JOIN FETCH m.department d "
                      + "WHERE d.name = :department and a.annualStatus = '승인'";
        Pageable pageable = dto.getPageable();
        TypedQuery<Long> query = entityManager.createQuery(countQuery, Long.class);
        query.setParameter("department", dto.getName());
        long totalElements = query.getSingleResult();
        int pageNumber = pageable.getPageNumber();

        TypedQuery<Annual> q = entityManager.createQuery(jpql, Annual.class);
        q.setParameter("department", dto.getName());
        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());

        List<AnnualResponse> collect = q.getResultList().stream().map(annual -> GetAnnualByDateAndDeptResDto.builder()
                .period(annual.getPeriod()).annualType(annual.getAnnualType()).deptName(dto.getName()).build()).collect(
                Collectors.toList());

        return AnnualResponseDto.builder().totalPage(totalElements).curPage(pageNumber).content(collect).build();

    }
    public AnnualResponseDto<AnnualResponse> findAnnualByMember(GetAnnualByMemberNameReqDto dto) {
        String countQuery = "SELECT COUNT(a) FROM Annual a JOIN a.member m WHERE m.memberInfo.name LIKE CONCAT('%', :name, '%') and a.annualStatus = '승인'";
        String jpql = "SELECT a FROM Annual a JOIN FETCH a.member m WHERE m.memberInfo.name LIKE CONCAT('%', :name, '%') and a.annualStatus = '승인'";
        Pageable pageable = dto.getPageable();
        TypedQuery<Long> query = entityManager.createQuery(countQuery, Long.class);
        query.setParameter("name", dto.getName());
        long totalElements = query.getSingleResult();
        int pageNumber = pageable.getPageNumber();
        TypedQuery<Annual> q = entityManager.createQuery(jpql, Annual.class);
        q.setParameter("name", dto.getName());
        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());
        List<AnnualResponse> collect = q.getResultList().stream().map(annual -> GetAnnualByNameResDto.builder()
                .memberInfo(annual.getMember().getMemberInfo()).period(annual.getPeriod()).annualType(annual.getAnnualType()).build())
                                        .collect(Collectors.toList());


        return AnnualResponseDto.builder().content(collect).totalPage(totalElements).curPage(pageNumber).build();

    }

    public AnnualResponseDto<AnnualResponse> findAnnualByDateAndDept(GetAnnualByDateAndDeptReqDto dto) {
        String countQuery = "SELECT count(a) FROM Annual a JOIN a.member m JOIN "
                            + "m.department d WHERE d.name = :department and a.annualStatus = '승인' "
                            + "and :today BETWEEN a.period.startDate and a.period.endDate";

        String jpql = "SELECT a FROM Annual a JOIN FETCH a.member m JOIN FETCH "
                      + "m.department d WHERE d.name = :department and a.annualStatus = '승인' "
                      + "and :today BETWEEN a.period.startDate and a.period.endDate";
        Pageable pageable = dto.getPageable();
        TypedQuery<Long> query = entityManager.createQuery(countQuery, Long.class);
        query.setParameter("department", dto.getDeptName());
        query.setParameter("today", dto.getToday().toLocalDateTime());
        long totalElements = query.getSingleResult();
        int pageNumber = pageable.getPageNumber();


        TypedQuery<Annual> q = entityManager.createQuery(jpql, Annual.class);
        q.setParameter("department", dto.getDeptName());
        q.setParameter("today", dto.getToday().toLocalDateTime());


        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());


        List<AnnualResponse> collect = q.getResultList().stream().map(annual -> GetAnnualByDateAndDeptResDto.
                builder().deptName(dto.getDeptName()).memberName(annual.getMember().getMemberInfo().getName())
                .period(annual.getPeriod()).build()).collect(Collectors.toList());

        return AnnualResponseDto.builder().content(collect).totalPage(totalElements).curPage(pageNumber).build();
    }
}