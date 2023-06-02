package tko.refresh.repository.calendar;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import tko.refresh.dto.calendar.response.department.GetDepartmentNameResDto;
@Repository
public class DepartmentEmRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public List<GetDepartmentNameResDto> findAllDeptName() {
         return entityManager.createQuery("SELECT d.name FROM Department d",
                                                            String.class).getResultList().stream()
                             .map(e -> GetDepartmentNameResDto.builder().name(e).build()).collect(Collectors.toList());
    }
}
