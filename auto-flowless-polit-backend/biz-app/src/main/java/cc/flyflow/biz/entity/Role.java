package cc.flyflow.biz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author Vincent
 * @since 2023-07-06
 */
@ApiModel(description = "<p> 角色 </p>")
@Getter
@Setter
@Accessors(chain = true)
public class Role  extends BaseEntity {


    /**
     * 角色名字
     */
    @ApiModelProperty("角色名字")
    @TableField("`name`")
    private String name;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    @TableField("`user_id`")
    private Long userId;
    /**
     * 角色唯一编码
     */
    @ApiModelProperty("角色唯一编码")
    @TableField("`key`")
    private String key;
    /**
     * 角色状态 1正常2禁用
     */
    @ApiModelProperty("角色状态 1正常2禁用")
    @TableField("`status`")
    private Integer status;


}
