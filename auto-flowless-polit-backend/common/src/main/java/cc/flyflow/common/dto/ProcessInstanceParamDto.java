package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程实例参数对象
 */
@ApiModel(description = "流程实例参数对象")
@Data
public class ProcessInstanceParamDto {
    /**
     * 流程id
     */
    @ApiModelProperty("流程id")
    private String flowId;
    /**
     * 流程主id
     */
    @ApiModelProperty("流程主id")
    private String uniqueId;
    /**
     * 业务key
     */
    @ApiModelProperty("业务key")
    private String bizKey;
    /**
     * 参数集合
     */
    @ApiModelProperty("参数集合")
    private Map<String,Object> paramMap=new HashMap<>();
    /**
     * 发起人
     */
    @ApiModelProperty("发起人")
    private String startUserId;
    /**
     * 实例id
     */
    @ApiModelProperty("实例id")
    private String processInstanceId;
    /**
     * 是否撤销
     */
    @ApiModelProperty("是否撤销")
    private Boolean cancel;
    /**
     * 结果
     */
    @ApiModelProperty("结果")
    private Integer result;
    /**
     * 原因
     */
    @ApiModelProperty("原因")
    private String reason;
    /**
     * 流程实例id集合
     */
    @ApiModelProperty("流程实例id集合")
    private List<String> processInstanceIdList;
    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private String tenantId;
}
