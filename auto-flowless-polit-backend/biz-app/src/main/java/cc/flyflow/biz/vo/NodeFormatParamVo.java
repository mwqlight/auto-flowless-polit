package cc.flyflow.biz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * 格式化对象参数
 */
@ApiModel(description = "格式化对象参数")
@Data
public class NodeFormatParamVo {
    /**
     * 流程id
     */
    @ApiModelProperty(value = "流程id", position = 1)
    private String flowId;
    /**
     * 流程实例id
     */
    @ApiModelProperty(value = "流程实例id", position = 2)
    private String processInstanceId;
    /**
     * 任务id
     */
    @ApiModelProperty(value = "任务id", position = 3)
    private String taskId;
    /**
     * 抄送id
     */
    @ApiModelProperty(value = "抄送id", position = 4)
    private String ccId;
    /**
     * 来源哪里 比如 有流程实例id 可能来自发起（start） 因为有复制发起的功能
     */
    @ApiModelProperty(value = "来源哪里 比如 有流程实例id 可能来自发起（start） 因为有复制发起的功能", position = 5)
    private String from;
    /**
     * 参数集合
     */
    @ApiModelProperty(value = "参数集合", position = 6)
    private Map<String,Object> paramMap;

}
