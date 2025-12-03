package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 首页统计数据
 */
@ApiModel(description = "首页统计数据")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexPageStatistics {
    /**
     * 待办数量
     */
    @ApiModelProperty(value = "待办数量", position = 1)
    private Long pendingNum;
    /**
     * 发起数量
     */
    @ApiModelProperty(value = "发起数量", position = 2)
    private Long startedNum;
    /**
     * 抄送任务
     */
    @ApiModelProperty(value = "抄送任务数量", position = 3)
    private Long copyNum;

    /**
     * 完成数量
     */
    @ApiModelProperty(value = "完成任务数量", position = 4)
    private Long completedNum;

}
