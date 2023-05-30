package tko.refresh.repository.admin.annualManage;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.Annual;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnnualManageRepository extends JpaRepository<Annual, UUID>, AnnualManageRepositoryCustom{

    @Query("SELECT a FROM Annual a LEFT JOIN FETCH a.member m LEFT JOIN FETCH m.department")
    public List<Annual> findAllWithMember(Pageable pageable);


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Annual ann set ann.annualStatus = :status, ann.acceptor= :acceptor , ann.modifiedDate = now() where ann.uid = :uid")
    public int updateAnnualStatus(UUID uid,String status,String acceptor);

}
