package tko.refresh.repository.calendar;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.Holiday;
@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    @Query("SELECT h FROM Holiday h WHERE year(h.date) = ?1 AND month(h.date) = ?2")
    List<Holiday> findByYearMonth(int year, int month);

    int countByDateBetween(LocalDate startDate, LocalDate endDate);

}
