package tko.refresh.repository.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.Annual;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface AnnualManageRepository extends JpaRepository<Annual, UUID>, AnnualManageRepositoryCustom{
    @Query("SELECT a FROM Annual a LEFT JOIN FETCH a.member m LEFT JOIN FETCH m.department")
    List<Annual> findAllWithMember(Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Annual ann set ann.annualStatus = :status, ann.acceptor= :acceptor , ann.modifiedDate = now() where ann.uid = :uid")
    int acceptAnnualStatus(UUID uid,String status,String acceptor);

    @Modifying
    @Transactional
    @Query("update Annual ann set ann.annualStatus = :status, ann.acceptor= :acceptor, ann.rejectReason = :msg , ann.modifiedDate=now() where ann.uid = :uid")
    int rejectAnnualStatus(UUID uid,String status,String acceptor ,String msg);
}
