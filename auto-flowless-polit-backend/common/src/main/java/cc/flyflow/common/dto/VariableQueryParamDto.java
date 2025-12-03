package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class VariableQueryParamDto {
    /**
     * 任务id
     */
    @ApiModelProperty(value = "任务id", position = 1)
    private String taskId;
    /**
     * 表单key
     */
    @ApiModelProperty(value = "表单key", position = 2)
    private List<String> keyList;
    /**
     * 执行id
     */
    @ApiModelProperty(value = "执行id", position = 3)
    private String executionId;

}
