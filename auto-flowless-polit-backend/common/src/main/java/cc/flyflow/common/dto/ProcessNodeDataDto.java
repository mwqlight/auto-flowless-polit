package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class ProcessNodeDataDto {
    /**
     * 流程id
     */
    @ApiModelProperty("流程id")
    private String flowId;
    /**
     * 节点id
     */
    @ApiModelProperty("节点id")
    private String nodeId;
    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private String data;
    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private String tenantId;

}
