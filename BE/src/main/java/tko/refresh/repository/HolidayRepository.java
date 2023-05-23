package tko.refresh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tko.refresh.domain.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

}
