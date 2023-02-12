package com.limemul.easssue.api.dto.dash;

import com.limemul.easssue.entity.Category;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GraphDocDto {

    /** 카테고리 이름 */
    private List<String> labels;
    /** 카테고리 별 최근 일주일 동안 읽은 기사 수 */
    private int[] data;

    public GraphDocDto(List<Category> categories, List<GraphValueDocDto> graphInfo) {
        this.labels=categories.stream().map(Category::getName).toList();
        //todo 좀더 효율적인 방법 고민해보기
        this.data=new int[categories.size()];
        for (Category category : categories) {
            for (GraphValueDocDto dto : graphInfo) {
                if(category.getName().equals(dto.getCategory())){
                    data[Math.toIntExact(category.getId()-1)]=dto.getCount();
                }
            }
        }
    }
}
