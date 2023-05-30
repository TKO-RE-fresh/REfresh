package tko.refresh.domain.entity;

import static lombok.AccessLevel.*;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Holiday {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "holiday_name")
    private String name;

    @Transient
    private String locdate;

    @Column(name = "holiday_date")
    private LocalDate date;



    // 20230305
    @Builder
    public Holiday(String name, String locdate) {
        this.name = name;
        this.date = LocalDate.of(getYear(locdate), getMonth(locdate), getDay(locdate));
    }

    public int getYear(String s) {
        return Integer.parseInt(s.substring(0, 4));
    }

    public int getMonth(String s) {
        return Integer.parseInt(s.substring(4, 6));
    }

    public int getDay(String s) {
        return Integer.parseInt(s.substring(6, 8));
    }

}
