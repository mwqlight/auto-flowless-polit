package cc.flyflow.common.dto;

import cc.flyflow.common.dto.flow.node.EmptyNode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@ApiModel
@Data
public class CheckConditionMatchDto {
    /**
     * 节点条件
     */
    @ApiModelProperty(value = "节点条件", position = 1)
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,property = "type",defaultImpl = EmptyNode.class)
    private EmptyNode node;
    /**
     * 参数
     */
    @ApiModelProperty(value = "参数", position = 2)
    private Map<String,Object> paramMap;

}
