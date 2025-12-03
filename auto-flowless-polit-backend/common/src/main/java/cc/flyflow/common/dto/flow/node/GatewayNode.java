package cc.flyflow.common.dto.flow.node;

import cc.flyflow.common.dto.flow.Node;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhj
 * @version 1.0
 * @description: TODO
 * @date 2024/4/3 22:48
 */
@Data
public class GatewayNode extends Node {

    /**
     * 条件
     */
    @ApiModelProperty("条件")
    private List<Node> conditionNodes;



}
