package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 检查是否是给定的子级
 *
 */
@ApiModel(description = "检查是否是给定的子级")
@Data
public class CheckChildDto {
    /**
     * 子级id
     */
    @ApiModelProperty(value = "子级id", position = 1)
    private String childId;
    /**
     * 部门id集合
     */
    @ApiModelProperty(value = "部门id集合", position = 2)
    private List<String> deptIdList;

}
