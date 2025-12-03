package cc.flyflow.biz.vo;

import cc.flyflow.biz.vo.node.NodeShowVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NodeFormatResultVo {
    /**
     * 节点集合
     */
    @ApiModelProperty(value = "节点集合", position = 1)
    private List<NodeShowVo> processNodeShowDtoList;
    /**
     * 是否禁止选择用户
     */
    @ApiModelProperty(value = "是否禁止选择用户", position = 2)
    private Boolean disableSelectUser;
    /**
     * 要选择用户的节点集合
     */
    @ApiModelProperty(value = "要选择用户的节点集合", position = 3)
    private List<String> selectUserNodeIdList;

}
