package cc.flyflow.common.dto.flow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 节点用户对象
 */
@ApiModel(description = "节点用户对象")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class NodeUser implements Serializable {

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String id;
    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String name;
    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private String type;
    /**
     * 选择
     */
    @ApiModelProperty("选择")
    private Boolean selected;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 是否包含下级部门
     */
    @ApiModelProperty("是否包含下级部门")
    private Boolean containChildrenDept = false;


}
