package tko.refresh.repository.leaveRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tko.refresh.domain.entity.Annual;

import java.util.UUID;

@Repository
public interface LeaveRequestRepository extends JpaRepository<Annual, UUID> {

}