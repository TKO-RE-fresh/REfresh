package tko.refresh.repository.calendar;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.AnnualCount;
import tko.refresh.dto.calendar.GetAnnualCountDto;

@Repository
public class AnnualCountRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Daniel Kim
     *
     * @param year
     * @param month
     * @return
     * 연, 월과 부서 이름을 통해 연차 집계 데이터를 가져옴
     *
     * 2023-04-27
     */
    public List<AnnualCount> getAnnualCountByDept(String year, String month, String name) {
        return entityManager.createQuery(
                                    "SELECT a FROM AnnualCount a WHERE a.department.name IN (SELECT d.name FROM Department d WHERE d.name = :departmentName) AND year(a.annualDate) = :year AND month(a.annualDate) = :month",
                                    AnnualCount.class)
                            .setParameter("year", Integer.parseInt(year))
                            .setParameter("month", Integer.parseInt(month))
                            .setParameter("departmentName", name)
                            .getResultList();
    }

}
