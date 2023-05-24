package tko.refresh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tko.refresh.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
