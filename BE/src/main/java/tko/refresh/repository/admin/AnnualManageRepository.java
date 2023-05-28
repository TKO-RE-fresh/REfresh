package tko.refresh.repository.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.Annual;

import java.util.List;
@Repository
public interface AnnualManageRepository extends JpaRepository<Annual,Long>, AnnualManageRepositoryCustom{

    @Query("SELECT a FROM Annual a LEFT JOIN FETCH a.member m LEFT JOIN FETCH m.department")
    public List<Annual> findAllWithMember(Pageable pageable);

}
