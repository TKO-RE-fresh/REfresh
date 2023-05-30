package tko.refresh.repository.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.AnnualCount;

import java.time.LocalDate;

@Repository
public interface AnnualCountRepository extends JpaRepository<AnnualCount, Long> {

    @Modifying
    @Query("UPDATE AnnualCount ac set ac.sumCount = ac.sumCount +1 where ac.annualDate BETWEEN :startDate and :endDate and ac.department in (select d from Department d where d.name = :name)")
    int setAnnualSumCount(LocalDate startDate, LocalDate endDate, String name);
}
