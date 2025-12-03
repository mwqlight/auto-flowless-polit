package cc.flyflow.biz.vo;

import cc.flyflow.common.constants.StatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 组织对象
 */
@ApiModel(description = "组织对象")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgDataVo {



    /**
     * 用户od
     */
    @ApiModelProperty(value = "用户od", position = 1)
    private String id;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称", position = 2)
    private String name;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", position = 3)
    private String type;
    /**
     * 选择
     */
    @ApiModelProperty(value = "选择", position = 4)
    private Boolean selected;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", position = 5)
    private String avatar;
    /**
     * 用户状态 {@link StatusEnum}
     */
    @ApiModelProperty(value = "用户状态 {@link constants.cc.flyflow.common.StatusEnum}", position = 6)
    private Integer status;

}
