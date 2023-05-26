package tko.refresh.dto.calendar.request.annual;


import org.springframework.data.domain.Pageable;

public interface GlobalPage {
    default Pageable setPageable(int page, int size) {
        return Pageable.ofSize(size).withPage(page - 1);
    }
}
