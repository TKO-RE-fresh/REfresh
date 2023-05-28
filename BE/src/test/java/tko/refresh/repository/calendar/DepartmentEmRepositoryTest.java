package tko.refresh.repository.calendar;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import tko.refresh.domain.entity.Department;

@SpringBootTest
@TestPropertySource("classpath:application-TEST.properties")
@Transactional
class DepartmentEmRepositoryTest {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentEmRepository repo;
    @BeforeEach
    void setUp() {
        Department department1 = new Department("개발팀", "code", "intro", "image", LocalDateTime.now(),
                                               LocalDateTime.now());

        Department department2 = new Department("인사팀", "code", "intro", "image", LocalDateTime.now(),
                                               LocalDateTime.now());

        Department department3 = new Department("영업팀", "code", "intro", "image", LocalDateTime.now(),
                                                LocalDateTime.now());

        Department department4 = new Department("디자인팀", "code", "intro", "image", LocalDateTime.now(),
                                                LocalDateTime.now());
        departmentRepository.save(department1);
        departmentRepository.save(department2);
        departmentRepository.save(department3);
        departmentRepository.save(department4);
    }

    @Test
    void 모든_부서_찾기() {
        assertEquals(4, repo.findAllDeptName().size());
    }


}