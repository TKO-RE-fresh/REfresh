package tko.refresh.repository.calendar;

import org.springframework.data.jpa.repository.JpaRepository;

import tko.refresh.domain.entity.Annual;

public interface AnnualRepository extends JpaRepository<Annual, Long> {
}
