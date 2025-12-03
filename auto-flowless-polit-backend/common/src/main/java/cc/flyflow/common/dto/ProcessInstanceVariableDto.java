package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流程实例变量
 * @author zhj
 */
@ApiModel(description = "流程实例变量")
@Data
public class ProcessInstanceVariableDto {

    /**
     * 实例id
     */
    @ApiModelProperty("实例id")
    private String processInstanceId;
    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private String tenantId;
    /**
     * 变量名称
     */
    @ApiModelProperty("变量名称")
    private String variableName;
    /**
     * 变量值
     */
    @ApiModelProperty("变量值")
    private Object variableValue;
}
