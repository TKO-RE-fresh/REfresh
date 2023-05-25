package tko.refresh.repository.calendar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tko.refresh.domain.entity.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    List<Holiday> findHolidayByLocdateContaining(String locdate);
}
