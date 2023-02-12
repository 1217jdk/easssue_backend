package com.limemul.easssue.api.dto.dash;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class GrassDocDto {

    /** 저번 달 마지막 날 */
    private String startDate;
    /** 이번 달 마지막 날 */
    private String endDate;
    /** 이번 한 달의 날짜별 읽은 기사 수 */
    private List<GrassValueDocDto> values;

    public GrassDocDto(List<GrassValueDocDto> heatMapInfo) {
        LocalDate now = LocalDate.now();

        this.startDate=now.minusMonths(1).with(lastDayOfMonth()).toString();
        this.endDate=now.with(lastDayOfMonth()).toString();
        log.info("GrassDto startDate: {}, endDate: {}",startDate,endDate);

        if(heatMapInfo.size()>0){
            this.values=heatMapInfo;
        }else{
            //이번 달에 읽은 기사 없을 시 빈 리스트 반환
            this.values=new ArrayList<>();
        }
    }
}
