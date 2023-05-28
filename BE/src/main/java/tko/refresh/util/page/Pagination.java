package tko.refresh.util.page;

import org.springframework.data.domain.Pageable;

public class Pagination {

    public static Pageable setPageable(int page, int size){
        return Pageable.ofSize(size).withPage(page - 1);
    };
}
