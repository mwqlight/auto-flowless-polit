package cc.flyflow.common.dto.flow.node.parent;

import cc.flyflow.common.dto.flow.Node;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author zhj
 * @version 1.0
 * @description: TODO
 * @date 2024/4/4 21:24
 */
@Data
public class SuperUserRootNode extends Node {

    /**
     * 表单权限
     */
    @ApiModelProperty("表单权限")
    private Map<String, String> formPerms;

    /**
     * 多人审批方式
     */
    @ApiModelProperty("多人审批方式")
    private Integer multipleMode;


}
