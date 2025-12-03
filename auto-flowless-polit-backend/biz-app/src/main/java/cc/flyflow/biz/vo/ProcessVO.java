package cc.flyflow.biz.vo;

import cc.flyflow.biz.entity.Process;
import cc.flyflow.common.dto.flow.NodeUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 流程对象
 */
@ApiModel(description = "流程对象")
@Data
public class ProcessVO extends Process {
    /**
     * 需要发起人选择的节点id
     */
    @ApiModelProperty("需要发起人选择的节点id")
    private List<String> selectUserNodeId;
    /**
     * 发起人范围
     */
    @ApiModelProperty("发起人范围")
    private List<NodeUser> rangeList;
    /**
     * 变量参数集合
     */
    @ApiModelProperty("变量参数集合")
    private Map<String, Object> variableMap;
    /**
     * 是否直接发布
     */
    @ApiModelProperty("是否直接发布")
    private Boolean publish=true;
}
