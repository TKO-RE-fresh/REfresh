package tko.refresh.repository.leaveRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tko.refresh.domain.entity.Member;

import java.util.UUID;

public interface LeaveRequesterInfoRepository extends JpaRepository<Member, UUID> {

    @Query("SELECT m.annualCount FROM Member m WHERE m.memberId = :memberId")
    Double findByAnnualCount(@Param("memberId") String memberId);
}
