package tko.refresh.repository.myPage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tko.refresh.domain.entity.Annual;
import tko.refresh.domain.enu.AnnualStatus;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface AnnualHistoryRepository extends JpaRepository<Annual, UUID>, AnnualHistoryRepositoryCustom {

    @Modifying
    @Query("update Annual ann set ann.annualStatus = :status , ann.modifiedDate = now() , ann.modifiedBy = :modifiedBy where ann.uid = :uid")
    int cancelAnnualStatus(@Param("uid") UUID uid, @Param("status") AnnualStatus status,@Param("modifiedBy") String modifiedBy);
}
