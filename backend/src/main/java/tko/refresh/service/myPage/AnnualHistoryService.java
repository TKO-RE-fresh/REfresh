package tko.refresh.service.myPage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tko.refresh.domain.emb.Period;
import tko.refresh.domain.entity.Annual;
import tko.refresh.domain.entity.Member;
import tko.refresh.domain.enu.AnnualStatus;
import tko.refresh.domain.enu.AnnualType;
import tko.refresh.dto.myPage.AnnualHistoryDto;
import tko.refresh.dto.myPage.HistorySearchDto;
import tko.refresh.repository.admin.AnnualManageRepository;
import tko.refresh.repository.calendar.AnnualCountRepository;
import tko.refresh.repository.member.MemberRepository;
import tko.refresh.repository.myPage.AnnualHistoryRepository;
import tko.refresh.service.admin.AnnualManageService;
import tko.refresh.util.jwt.JwtAuthMember;
import tko.refresh.util.page.Pagination;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnnualHistoryService {

    private final int PAGE_SIZE = 5;

    private final AnnualHistoryRepository annualHistoryRepository;
    private final AnnualManageRepository annualManageRepo;
    private final AnnualManageService annualManageService;
    private final MemberRepository memberRepository;
    private final AnnualCountRepository annualCountRepository;

    public Page<AnnualHistoryDto> getAnnualHistoryPage(HistorySearchDto searchDto, int page) {
        Pageable pageable = Pagination.setPageable(page, PAGE_SIZE);
        Page<AnnualHistoryDto> list = annualHistoryRepository.getAnnualHistoryPage(searchDto, pageable);
        return list;
    }

    @Transactional
    public boolean CancelAnnualRequest(UUID uid) {
        Annual annual = annualManageRepo.findByOne(uid).orElse(null);
        if (annual == null) return false;

        Period period = annual.getPeriod();
        Member member = annual.getMember();
        int subResult = 0, annualCountResult = 0, statusResult = 0;

        double periodCount = annual.getAnnualType().equals(AnnualType.ANNUAL_LEAVE) ? annualManageService.WorkingDaysCounter(period) : 0.5;
        if(periodCount < 1) return false;

        statusResult = annualHistoryRepository.cancelAnnualStatus(uid, AnnualStatus.CANCEL, member.getMemberId());

        //연차 승인상태 일 때,
        if (annual.getAnnualStatus().equals(AnnualStatus.AGREE)) {
            annualCountResult = memberRepository.setAnnualCount(member.getMemberId(), periodCount);
            subResult = annualCountRepository.setAnnualSubCount(period.getStartDate(), period.getEndDate(), member.getDepartment().getUid());
            return annualManageService.successTransaction(annualCountResult, subResult, statusResult);
        }
        return periodCount > 0;
    }
}
