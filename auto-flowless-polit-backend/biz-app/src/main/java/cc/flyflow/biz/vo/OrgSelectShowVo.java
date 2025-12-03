package cc.flyflow.biz.vo;

import cc.flyflow.common.dto.third.DeptDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 选择组织对象数据
 * @author Huijun Zhao
 * @description
 * @date 2023-12-06 10:20
 */
@ApiModel(description = "选择组织对象数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgSelectShowVo {
    /**
     * 头部标题显示
     */
    @ApiModelProperty(value = "头部标题显示", position = 1)
    private List<DeptDto> titleDepartments;
    /**
     * 角色集合
     */
    @ApiModelProperty(value = "角色集合", position = 2)
    private List<OrgDataVo> roleList;
    /**
     * 子级部门
     */
    @ApiModelProperty(value = "子级部门", position = 3)
    private List<OrgDataVo> childDepartments;
    /**
     * 人员集合
     */
    @ApiModelProperty(value = "人员集合", position = 4)
    private List<OrgDataVo> employees;
}
