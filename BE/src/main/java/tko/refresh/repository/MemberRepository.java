package tko.refresh.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tko.refresh.domain.Member;
public interface MemberRepository extends JpaRepository<Member, UUID> {

}
