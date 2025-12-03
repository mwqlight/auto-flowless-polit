package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 流程实例对象
 */
@ApiModel(description = "流程实例对象")
@Data
public class ProcessInstanceDto {
    /**
     * 实例id
     */
    @ApiModelProperty(value = "实例id", position = 1)
    private String processInstanceId;
    /**
     * 流程id
     */
    @ApiModelProperty(value = "流程id", position = 2)
    private String flowId;
    /**
     * 流程名称
     */
    @ApiModelProperty(value = "流程名称", position = 3)
    private String processName;
    /**
     * 发起人id
     */
    @ApiModelProperty(value = "发起人id", position = 4)
    private String startUserId;
    /**
     * 发起人名称
     */
    @ApiModelProperty(value = "发起人名称", position = 5)
    private String startUserName;
    /**
     * 群组名称
     */
    @ApiModelProperty(value = "群组名称", position = 6)
    private String groupName;
    /**
     * 发起时间
     */
    @ApiModelProperty(value = "发起时间", position = 7)
    private Date startTime;
    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间", position = 8)
    private Date endTime;
    /**
     * 流程结果
     */
    @ApiModelProperty(value = "流程结果", position = 9)
    private Integer processInstanceResult;
    /**
     * 流程状态
     */
    @ApiModelProperty(value = "流程状态", position = 10)
    private Integer processInstanceStatus;

}
