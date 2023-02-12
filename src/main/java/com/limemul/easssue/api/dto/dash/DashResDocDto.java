package com.limemul.easssue.api.dto.dash;

import com.limemul.easssue.entity.ArticleLog;
import com.limemul.easssue.entity.ArticleLogDoc;
import com.limemul.easssue.entity.Category;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DashResDocDto {

    private GraphDocDto graph;
    private String cloud;
    private GrassDocDto grass;
    private List<ArticleDto> newsList;


    public DashResDocDto(List<Category> categories, List<GraphValueDocDto> radialGraphInfo, String cloud,
                         List<GrassValueDocDto> calendarHeatMapInfo, List<ArticleLogDoc> articleLogList) {
        this.graph=new GraphDocDto(categories,radialGraphInfo);
        this.cloud=cloud;
        this.grass=new GrassDocDto(calendarHeatMapInfo);
        this.newsList=articleLogList.stream().map(ArticleDto::new).toList();
    }
}
