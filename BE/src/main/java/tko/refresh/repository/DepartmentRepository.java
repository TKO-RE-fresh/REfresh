package tko.refresh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tko.refresh.domain.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
