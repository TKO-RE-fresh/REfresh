package tko.refresh.repository.admin;

import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.Annual;
import tko.refresh.domain.enu.AnnualStatus;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnnualManageRepository extends JpaRepository<Annual, UUID>, AnnualManageRepositoryCustom{
    @Query("SELECT a FROM Annual a LEFT JOIN FETCH a.member m LEFT JOIN FETCH m.department")
    @BatchSize(size = 1)
    List<Annual> findAllWithMember(Pageable pageable);

    @Query("SELECT a FROM Annual a LEFT JOIN FETCH a.member m LEFT JOIN FETCH m.department where a.uid = :uid")
    Optional<Annual> findByOne(@Param("uid") UUID uid);

    @Modifying
    @Query("update Annual ann set ann.annualStatus = :status, ann.acceptor= :acceptor , ann.modifiedDate = now() where ann.uid = :uid")
    int acceptAnnualStatus(@Param("uid") UUID uid, @Param("status") AnnualStatus status, @Param("acceptor")String acceptor);

    @Modifying
    @Query("update Annual ann set ann.annualStatus = :status, ann.acceptor= :acceptor, ann.rejectReason = :msg , ann.modifiedDate=now() where ann.uid = :uid")
    int rejectAnnualStatus(@Param("uid") UUID uid, @Param("status") AnnualStatus status, @Param("acceptor")String acceptor , @Param("msg") String msg);

}
