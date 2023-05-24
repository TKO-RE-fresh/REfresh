package tko.refresh.repository.calendar;

import org.springframework.data.jpa.repository.JpaRepository;

import tko.refresh.domain.entity.AnnualCount;

public interface AnnualCountRepository extends JpaRepository<AnnualCount, Long> {
}
