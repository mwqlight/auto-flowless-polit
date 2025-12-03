package cc.flyflow.common.dto.flow.node;

import cc.flyflow.common.dto.flow.node.parent.SuperUserRootNode;
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
@JsonTypeName("0")
@Data
public class RootNode extends SuperUserRootNode {



    /**
     * 操作列表
     */
    @ApiModelProperty("操作列表")
    private List operList;


}
