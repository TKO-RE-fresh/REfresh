package tko.refresh.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tko.refresh.domain.Annual;

public interface AnnualRepository extends JpaRepository<Annual, UUID> {


}
