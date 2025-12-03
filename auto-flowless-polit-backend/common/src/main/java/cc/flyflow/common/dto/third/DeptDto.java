package cc.flyflow.common.dto.third;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptDto {
    /**
     * 部门id 不能为空
     */
    @ApiModelProperty("部门id 不能为空")
    private String id;
    /**
     * 部门名字 不能为空
     */
    @ApiModelProperty("部门名字 不能为空")
    private String name;
    /**
     * 部门上级id 不能为空 若为顶级 则是0
     */
    @ApiModelProperty("部门上级id 不能为空 若为顶级 则是0")
    private String parentId;
    /**
     * 部门主管的userId 可以为空
     */
    @ApiModelProperty("部门主管的userId 可以为空")
    private List<String> leaderUserIdList;
    /**
     * 部门状态 0 禁用 1启用
     */
    @ApiModelProperty("部门状态 0 禁用 1启用")
    private Integer status;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

}
