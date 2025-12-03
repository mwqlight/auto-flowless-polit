package cc.flyflow.biz.vo;

import cc.flyflow.biz.entity.Dept;
import cc.flyflow.common.dto.flow.NodeUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 部门vo
 */
@ApiModel(description = "部门vo")
@Data
public class DeptVO extends Dept {
    /**
     * 部门主管
     */
    @ApiModelProperty("部门主管")
    private List<NodeUser> leaderUser;

}
