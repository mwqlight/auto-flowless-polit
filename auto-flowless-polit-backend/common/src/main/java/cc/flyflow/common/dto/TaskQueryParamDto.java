package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户任务查询参数
 */
@ApiModel(description = "用户任务查询参数")
@Data
public class TaskQueryParamDto extends PageDto{
    /**
     * 任务执行人
     */
    @ApiModelProperty(value = "任务执行人", position = 1)
    private String assign;

}
