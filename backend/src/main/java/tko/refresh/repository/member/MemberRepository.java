package tko.refresh.repository.member;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tko.refresh.domain.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID>, MemberRepositoryCustom {

    @Query("SELECT m from Member m join fetch Department d on m.department.uid = d.uid where m.memberId = :id")
    Optional<Member> findMemberDepartment(@Param("id") String id);

    @Query("SELECT m FROM Member m WHERE m.memberInfo.email = :email")
    Optional<Member> findByLoginEmail(@Param("email") String email);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Member m set m.annualCount = m.annualCount-:count where m.memberId = :memberId")
    int setSubAnnualCount(@Param("memberId") String memberId, @Param("count")double count);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Member m set m.annualCount = m.annualCount+:count where m.memberId = :memberId")
    int setAnnualCount(@Param("memberId") String memberId, @Param("count")double count);

    @Query("select YEAR(m.createdDate) from Member m where m.memberId = :memberId")
    int getMemberCreationYear(@Param("memberId") String memberId);
    @Query("SELECT m FROM Member m WHERE m.memberId = :memberId")
    Optional<Member> findLoginMemberId(@Param("memberId") String memberId);
}
