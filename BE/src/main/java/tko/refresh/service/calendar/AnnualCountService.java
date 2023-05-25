package tko.refresh.service.calendar;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tko.refresh.repository.calendar.AnnualCountRepository;
import tko.refresh.repository.calendar.AnnualCountRepositoryImpl;

@Service
@RequiredArgsConstructor
public class AnnualCountService {

    private final AnnualCountRepositoryImpl annualCountRepository;

    /**
     * Daniel Kim
     *
     * @param year
     * @param month
     * @return
     * 연, 월과 부서 이름을 통해 연차 집계 데이터를 가져옴
     *
     * 2023-04-27
     */
}
