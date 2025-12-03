package cc.flyflow.common.dto;

import java.lang.String;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流程节点记录-执行人
 */
@ApiModel(description = "流程节点记录-执行人")
@Data
public class ProcessInstanceAssignUserRecordParamDto {


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
     * 表单数据
     */
    @ApiModelProperty("表单数据")
    private String data;
    /**
     * 流程本地数据
     */
    @ApiModelProperty("流程本地数据")
    private String localData;
    /**
     * 节点id
     */
    @ApiModelProperty("节点id")
    private String nodeId;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String userId;
    /**
     * 执行id
     */
    @ApiModelProperty("执行id")
    private String executionId;
    /**
     * 节点执行id
     */
    @ApiModelProperty("节点执行id")
    private String parentExecutionId;

    /**
     * 任务id
     */
    @ApiModelProperty("任务id")
    private String taskId;
    /**
     * 节点名称
     */
    @ApiModelProperty("节点名称")
    private String nodeName;
    /**
     * 流程唯一id
     */
    @ApiModelProperty("流程唯一id")
    private String flowUniqueId;
    /**
     * 任务类型
     */
    @ApiModelProperty("任务类型")
    private String taskType;

    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private String tenantId;

    /**
     * 是否是自动完成
     */
    @ApiModelProperty("是否是自动完成")
    private Boolean auto;
}
