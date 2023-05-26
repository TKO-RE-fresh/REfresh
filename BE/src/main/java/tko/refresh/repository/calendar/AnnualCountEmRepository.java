package tko.refresh.repository.calendar;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import tko.refresh.domain.entity.AnnualCount;
import tko.refresh.dto.calendar.request.annualcount.AnnualCountReqDto;
import tko.refresh.dto.calendar.response.annualcount.AnnualCountResDto;

@Repository
public class AnnualCountEmRepository {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Daniel Kim
     *
     * @param dto
     * @return
     * 연, 월과 부서 이름을 통해 연차 집계 데이터를 가져옴
     *
     * 2023-05-25
     */
    public List<AnnualCountResDto> getAnnualCountByDept(AnnualCountReqDto dto) {
        return entityManager.createQuery("SELECT a FROM AnnualCount a WHERE a.department.name "
                                                            + "IN (SELECT d.name FROM Department d WHERE d.name = :departmentName) "
                                                            + "AND year(a.annualDate) = :year AND month(a.annualDate) = :month "
                                         + "ORDER BY a.annualDate ASC",
                                                            AnnualCount.class)
                                                    .setParameter("year", dto.getYearMonth().getYear())
                                                    .setParameter("month", dto.getYearMonth().getMonth())
                                                    .setParameter("departmentName", dto.getDeptName())
                                                    .getResultList().stream().map(ac -> AnnualCountResDto.builder()
                                                                                                    .annualDate(ac.getAnnualDate().toLocalDate())
                                                                                                    .sumCount(ac.getSumCount())
                                                                                                    .build()).collect(Collectors.toList());
    }

}
