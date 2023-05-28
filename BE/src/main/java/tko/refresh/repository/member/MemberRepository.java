package tko.refresh.repository.member;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.Member;
import tko.refresh.dto.member.MemberLoginDto;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    Optional<Member> findByMemberId(String memberId);
    @Query("SELECT m FROM Member m WHERE m.memberInfo.email = :email")
    Optional<Member> findByLoginEmail(@Param("email") String email);

    @Query("SELECT m FROM Member m WHERE m.memberId = :memberId")
    Optional<Member> findLoginMemberId(@Param("memberId") String memberId);
}
