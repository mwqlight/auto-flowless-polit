package cc.flyflow.common.dto;

import cc.flyflow.common.constants.ApproveResultEnum;
import cn.hutool.core.lang.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 任务对象
 */
@ApiModel(description = "任务对象")
@Data
public class TaskDto {
    /**
     * 流程id
     */
    @ApiModelProperty("流程id")
    private String flowId;

    /**
     * 参数集合
     */
    @ApiModelProperty("参数集合")
    private Map<String,Object> paramMap;
    /**
     *  表单值显示
     */
    @ApiModelProperty("表单值显示")
    private List<Dict> formValueShowList;

    /**
     * 实例id
     */
    @ApiModelProperty("实例id")
    private String processInstanceId;
    /**
     * 执行id
     */
    @ApiModelProperty("执行id")
    private String executionId;
    /**
     * 耗时
     */
    @ApiModelProperty("耗时")
    private Long durationInMillis;
    /**
     * 任务id
     */
    @ApiModelProperty("任务id")
    private String taskId;
    /**
     * 执行人
     */
    @ApiModelProperty("执行人")
    private String assign;
    /**
     * 任务名称
     */
    @ApiModelProperty("任务名称")
    private String taskName;
    /**
     * 节点id
     */
    @ApiModelProperty("节点id")
    private String nodeId;
    /**
     * 任务创建时间
     */
    @ApiModelProperty("任务创建时间")
    private Date taskCreateTime;
    /**
     * 任务结束时间
     */
    @ApiModelProperty("任务结束时间")
    private Date taskEndTime;
    /**
     * 流程组名字
     */
    @ApiModelProperty("流程组名字")
    private String groupName;
    /**
     * 发起人id
     */
    @ApiModelProperty("发起人id")
    private String rootUserId;
    /**
     * 发起人名字
     */
    @ApiModelProperty("发起人名字")
    private String rootUserName;
    /**
     * 发起人头像
     */
    @ApiModelProperty("发起人头像")
    private String rootUserAvatarUrl;

    /**
     * 发起时间
     */
    @ApiModelProperty("发起时间")
    private Date startTime;
    /**
     * 流程实例业务编码
     */
    @ApiModelProperty("流程实例业务编码")
    private String processInstanceBizCode;
    /**
     * 流程名称
     */
    @ApiModelProperty("流程名称")
    private String processName;

    /**
     * 需要发起人选择的节点id
     */
    @ApiModelProperty("需要发起人选择的节点id")
    private List<String> selectUserNodeId;
    /**
     * 流程结果 {@link ApproveResultEnum}
     */
    @ApiModelProperty("流程结果 {@link constants.cc.flyflow.common.ApproveResultEnum}")
    private Integer processInstanceResult;
    /**
     * 用户名字
     */
    @ApiModelProperty("用户名字")
    private String userName;

    /**
     * 流程唯一id
     */
    @ApiModelProperty("流程唯一id")
    private String flowUniqueId;

    /**
     * 正在处理的人员显示
     */
    @ApiModelProperty("正在处理的人员显示")
    private String taskAssignShow;
    /**
     * 发起时间显示
     */
    @ApiModelProperty("发起时间显示")
    private String startTimeShow;

}
