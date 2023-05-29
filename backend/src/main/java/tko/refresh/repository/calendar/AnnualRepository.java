package tko.refresh.repository.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.Annual;
@Repository
public interface AnnualRepository extends JpaRepository<Annual, Long> {
}
