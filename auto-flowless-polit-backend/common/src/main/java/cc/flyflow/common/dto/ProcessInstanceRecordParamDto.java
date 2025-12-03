package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 流程记录
 * </p>
 *
 * @author Vincent
 * @since 2023-05-07
 */
@ApiModel(description = "<p> 流程记录 </p>")
@Getter
@Setter
public class ProcessInstanceRecordParamDto {
    /**
     * 发起人主部门id
     */
    @ApiModelProperty("发起人主部门id")
    private String mainDeptId;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String userId;


    /**
     * 流程id
     */
    @ApiModelProperty("流程id")
    private String flowId;

    /**
     * 流程实例id
     */
    @ApiModelProperty("流程实例id")
    private String processInstanceId;
    /**
     * 流程实例业务key
     */
    @ApiModelProperty("流程实例业务key")
    private String processInstanceBizKey;
    /**
     * 业务编码
     */
    @ApiModelProperty("业务编码")
    private String processInstanceBizCode;

    @ApiModelProperty(hidden = true)
    private String parentProcessInstanceId;
    /**
     * 主流程节点执行id
     */
    @ApiModelProperty("主流程节点执行id")
    private String parentProcessNodeExecutionId;

    /**
     * 表单数据
     */
    @ApiModelProperty("表单数据")
    private String formData;

    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private String tenantId;
}
