package tko.refresh.repository.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.AnnualCount;
import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface AnnualCountRepository extends JpaRepository<AnnualCount, Long> {

    @Modifying
    @Query("UPDATE AnnualCount ac set ac.sumCount = ac.sumCount + 1 where FUNCTION('DATE', ac.annualDate) >= FUNCTION('DATE', :startDate) and FUNCTION('DATE', ac.annualDate) <= FUNCTION('DATE', :endDate) and ac.department.uid = :uid")
    int setAnnualSumCount(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("uid") UUID uid);

    @Modifying
    @Query("UPDATE AnnualCount ac set ac.sumCount = ac.sumCount - 1 where ac.annualDate between FUNCTION('DATE', :startDate) and FUNCTION('DATE', :endDate) and ac.department.uid = :uid")
    int setAnnualSubCount(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("uid") UUID uid);
=======
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.AnnualCount;
@Repository
public interface AnnualCountRepository extends JpaRepository<AnnualCount, Long> {
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
}
