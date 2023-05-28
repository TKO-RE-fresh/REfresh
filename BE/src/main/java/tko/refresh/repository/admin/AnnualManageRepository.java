package tko.refresh.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tko.refresh.domain.entity.Annual;

import java.util.List;

public interface AnnualManageRepository extends JpaRepository<Annual,Long> {

    @Query("SELECT a FROM Annual a LEFT JOIN FETCH a.member m LEFT JOIN FETCH m.department")
    public List<Annual> findAllWithMember();
}
