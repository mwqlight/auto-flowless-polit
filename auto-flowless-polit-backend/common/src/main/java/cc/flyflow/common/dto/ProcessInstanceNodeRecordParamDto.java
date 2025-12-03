package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 流程节点记录
 */
@ApiModel(description = "流程节点记录")
@Data
public class ProcessInstanceNodeRecordParamDto {


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
     * 跳转标识
     */
    @ApiModelProperty("跳转标识")
    private String flowUniqueId;
    /**
     * 节点id
     */
    @ApiModelProperty("节点id")
    private String nodeId;

    /**
     * 上级节点id
     */
    @ApiModelProperty("上级节点id")
    private String parentNodeId;


    /**
     * 节点类型
     */
    @ApiModelProperty("节点类型")
    private Integer nodeType;

    /**
     * 节点名字
     */
    @ApiModelProperty("节点名字")
    private String nodeName;
    /**
     * 执行id
     */
    @ApiModelProperty("执行id")
    private String executionId;
    /**
     * 子级执行id
     */
    @ApiModelProperty("子级执行id")
    private List<String> childExecutionId;



    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private String tenantId;

}
