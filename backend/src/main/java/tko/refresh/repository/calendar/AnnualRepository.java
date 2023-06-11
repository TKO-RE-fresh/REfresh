<<<<<<< HEAD
package tko.refresh.repository.calendar;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.Annual;
@Repository
public interface AnnualRepository extends JpaRepository<Annual, Long> {
    @Query("SELECT count(a) from Annual a where a.member.memberId = :memberId and (a.annualStatus = '대기' or a.annualStatus='승인')"
           + "and ((a.period.startDate between FUNCTION('DATE', :startDate) and FUNCTION('DATE', :endDate))"
           + "or (a.period.endDate between FUNCTION('DATE', :startDate) and FUNCTION('DATE', :endDate))"
           + "or (FUNCTION('DATE', :startDate) <= a.period.startDate and FUNCTION('DATE', :endDate) >= a.period.endDate))")
    int countAnnualByMemberUidAndPeriod(@Param("memberId") String memberId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
=======
package tko.refresh.repository.leaveRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tko.refresh.domain.entity.Annual;

import java.util.UUID;

@Repository
public interface LeaveRequestRepository extends JpaRepository<Annual, UUID> {

}
>>>>>>> 8e1b2a184f8df8342d618b4c44ca9a6c9d9789c3
