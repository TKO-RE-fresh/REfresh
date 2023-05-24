package tko.refresh.repository.calendar;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import tko.refresh.dto.calendar.GetAnnualByNameFilterDto;
import tko.refresh.dto.calendar.GetAnnualSearchDto;

@Repository
public class AnnualRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;


    public Page<GetAnnualSearchDto> findAnnualByDept(GetAnnualByNameFilterDto dto) {
        String countQuery = "SELECT COUNT(a) FROM Annual a JOIN a.member m JOIN m.department d WHERE d.name = :department";
        String jpql = "SELECT a FROM Annual a JOIN FETCH a.member m JOIN FETCH m.department d WHERE d.name = :department and a.annual_status = '승인'";
        Pageable pageable = Pageable.ofSize(dto.getSize()).withPage(dto.getPage());
        TypedQuery<Long> query = entityManager.createQuery(countQuery, Long.class);
        query.setParameter("department", dto.getName());
        long totalElements = query.getSingleResult();

        TypedQuery<GetAnnualSearchDto> q = entityManager.createQuery(jpql, GetAnnualSearchDto.class);
        q.setParameter("department", dto.getName());
        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());
        List<GetAnnualSearchDto> content = q.getResultList();

        return new PageImpl<>(content, pageable, totalElements);
    }
    public Page<GetAnnualSearchDto> findAnnualByMember(GetAnnualByNameFilterDto dto) {
        String countQuery = "SELECT COUNT(a) FROM Annual a JOIN a.member m WHERE m.name = :name";
        String jpql = "SELECT a FROM Annual a JOIN FETCH a.member m WHERE m.name like %:name% and a.annual_status = '승인'";
        Pageable pageable = Pageable.ofSize(dto.getSize()).withPage(dto.getPage());
        TypedQuery<Long> query = entityManager.createQuery(countQuery, Long.class);
        query.setParameter("name", dto.getName());
        long totalElements = query.getSingleResult();

        TypedQuery<GetAnnualSearchDto> q = entityManager.createQuery(jpql, GetAnnualSearchDto.class);
        q.setParameter("name", dto.getName());
        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());
        List<GetAnnualSearchDto> content = q.getResultList();

        return new PageImpl<>(content, pageable, totalElements);
    }

}