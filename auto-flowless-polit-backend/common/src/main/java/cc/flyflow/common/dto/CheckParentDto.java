package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 检查是否是给定的父级
 *
 */
@ApiModel(description = "检查是否是给定的父级")
@Data
public class CheckParentDto {
    /**
     * 上级id
     */
    @ApiModelProperty(value = "上级id", position = 1)
    private String parentId;
    /**
     * 部门id集合
     */
    @ApiModelProperty(value = "部门id集合", position = 2)
    private List<String> deptIdList;

}
