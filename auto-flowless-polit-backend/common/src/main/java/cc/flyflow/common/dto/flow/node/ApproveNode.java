package cc.flyflow.common.dto.flow.node;

import cc.flyflow.common.dto.flow.*;
import cc.flyflow.common.dto.flow.node.parent.SuperUserNode;
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
@Data
@JsonTypeName("1")
public class ApproveNode extends SuperUserNode {
    /**
     * 没有人时处理方式
     */
    @ApiModelProperty("没有人时处理方式")
    private Nobody nobody;

    /**
     * 审批拒绝时
     */
    @ApiModelProperty("审批拒绝时")
    private Refuse refuse;

    /**
     * 操作列表
     */
    @ApiModelProperty("操作列表")
    private List operList;


}
