package tko.refresh.repository.calendar;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.Annual;
import tko.refresh.dto.calendar.request.GetAnnualByDateAndDeptReqDto;
import tko.refresh.dto.calendar.request.GetAnnualByNameReqDto;
import tko.refresh.dto.calendar.response.GetAnnualByDateAndDeptResDto;
import tko.refresh.dto.calendar.response.GetAnnualByNameResDto;


@Repository
public class AnnualEmRepository {

    @PersistenceContext
    private EntityManager entityManager;



    public Page<GetAnnualByNameResDto> findAnnualByDept(GetAnnualByNameReqDto dto) {
        String countQuery = "SELECT COUNT(a) FROM Annual a JOIN a.member m JOIN m.department d WHERE d.name = :department and a.annualStatus = '승인'";
        String jpql = "SELECT a FROM Annual a JOIN FETCH a.member m JOIN FETCH m.department d WHERE d.name = :department and a.annualStatus = '승인'";
        Pageable pageable = Pageable.ofSize(dto.getPageable().getPageSize()).withPage(dto.getPageable().getPageNumber());
        TypedQuery<Long> query = entityManager.createQuery(countQuery, Long.class);
        query.setParameter("department", dto.getName());
        long totalElements = query.getSingleResult();

        TypedQuery<Annual> q = entityManager.createQuery(jpql, Annual.class);
        q.setParameter("department", dto.getName());
        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());
        List<GetAnnualByNameResDto> content = q.getResultList().stream()
                                               .map(annual -> GetAnnualByNameResDto.builder().memberInfo(annual.getMember().getMemberInfo()).annualType(annual.getAnnualType()).period(annual.getPeriod()).build())
                                               .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, totalElements);
    }
    public Page<GetAnnualByNameResDto> findAnnualByMember(GetAnnualByNameReqDto dto) {
        String countQuery = "SELECT COUNT(a) FROM Annual a JOIN a.member m WHERE m.memberInfo.name LIKE CONCAT('%', :name, '%') and a.annualStatus = '승인'";
        String jpql = "SELECT a FROM Annual a JOIN FETCH a.member m WHERE m.memberInfo.name LIKE CONCAT('%', :name, '%') and a.annualStatus = '승인'";
        Pageable pageable = Pageable.ofSize(dto.getPageable().getPageSize()).withPage(dto.getPageable().getPageNumber());
        TypedQuery<Long> query = entityManager.createQuery(countQuery, Long.class);
        query.setParameter("name", dto.getName());
        long totalElements = query.getSingleResult();

        TypedQuery<Annual> q = entityManager.createQuery(jpql, Annual.class);
        q.setParameter("name", dto.getName());
        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());
        List<GetAnnualByNameResDto> content = q.getResultList().stream()
                                               .map(annual -> GetAnnualByNameResDto.builder().memberInfo(annual.getMember().getMemberInfo())
                                                                                   .annualType(annual.getAnnualType())
                                                                                   .period(annual.getPeriod()).build())
                                               .collect(Collectors.toList());

        return new PageImpl<>(content, pageable, totalElements);
    }

    public List<GetAnnualByDateAndDeptResDto> findAnnualByDateAndDept(GetAnnualByDateAndDeptReqDto dto) {
        String jpql = "SELECT a FROM Annual a JOIN FETCH a.member m JOIN FETCH "
                      + "m.department d WHERE d.name = :department and a.annualStatus = '승인' "
                      + "and :today BETWEEN a.period.startDate and a.period.endDate";
        TypedQuery<Annual> q = entityManager.createQuery(jpql, Annual.class);
        q.setParameter("department", dto.getDeptName());
        q.setParameter("today", dto.getToday().toLocalDateTime());


        return q.getResultList().stream()
                .map(annual -> GetAnnualByDateAndDeptResDto.builder()
                                                            .memberName(annual.getMember().getMemberInfo().getName())
                                                            .annualType(annual.getAnnualType().getCode())
                                                            .period(annual.getPeriod()).build())
                .collect(Collectors.toList());

    }
}