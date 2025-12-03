package cc.flyflow.common.dto;

import cc.flyflow.common.dto.flow.Node;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 创建流程对象
 * @author Huijun Zhao
 * @description
 * @date 2023-09-22 10:45
 */
@ApiModel(description = "创建流程对象")
@Data
public class CreateFlowDto {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", position = 1)
    private String userId;
    /**
     * 节点
     */
    @ApiModelProperty(value = "节点", position = 2)
    private Node node;
    /**
     * 流程名称
     */
    @ApiModelProperty(value = "流程名称", position = 3)
    private String processName;

}
