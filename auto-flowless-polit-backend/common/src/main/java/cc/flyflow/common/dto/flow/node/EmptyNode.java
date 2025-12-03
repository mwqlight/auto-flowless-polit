package cc.flyflow.common.dto.flow.node;

import cc.flyflow.common.dto.flow.GroupCondition;
import cc.flyflow.common.dto.flow.Node;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhj
 * @version 1.0
 * @description: TODO
 * @date 2024/4/3 16:56
 */
@JsonTypeName("3")
@Data
public class EmptyNode extends Node {


    /**
     * 路由节点中用到了 指跳转到的节点id
     */
    @ApiModelProperty("路由节点中用到了 指跳转到的节点id")
    private String nodeId;
    /**
     * false:或|| true： 且&&
     */
    @ApiModelProperty("false:或|| true： 且&&")
    private Boolean mode;
    /**
     * 条件组之间的关系
     */
    @ApiModelProperty("条件组之间的关系")
    private Boolean groupRelationMode;
    /**
     * 优先级
     */
    @ApiModelProperty("优先级")
    private Integer priorityLevel;




    @ApiModelProperty(hidden = true)
    private Object groupRelation;

    /**
     * 条件列表
     */
    @ApiModelProperty("条件列表")
    private List<GroupCondition> conditionList;

}
