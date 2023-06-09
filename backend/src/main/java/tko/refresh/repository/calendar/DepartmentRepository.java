package tko.refresh.repository.calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.Department;
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findByName(String name);

}
