package tko.refresh.repository.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tko.refresh.domain.entity.Department;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemberDepartmentRepository extends JpaRepository<Department, UUID> {

    @Query("SELECT d.name FROM Department d")
    List<String> getDepartmentNameList();
}
