package tko.refresh.repository.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.AnnualCount;
@Repository
public interface AnnualCountRepository extends JpaRepository<AnnualCount, Long> {
}
