package cc.flyflow.biz.entity;

import cc.flyflow.biz.constants.ValidGroup;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author Vincent
 * @since 2023-07-06
 */
@ApiModel(description = "<p> </p>")
@Getter
@Setter
@Accessors(chain = true)
public class Process  extends BaseEntity {



    /**
     * 表单ID
     */
    @ApiModelProperty("表单ID")
    @TableField("`flow_id`")
    private String flowId;

    /**
     * 表单名称
     */
    @ApiModelProperty(value = "表单名称", required = true)
    @NotBlank(groups = {ValidGroup.Crud.Create.class},message = "表单名称不能为空")
    @TableField("`name`")
    private String name;

    /**
     * 图标配置
     */
    @ApiModelProperty(value = "图标配置", required = true)
    @NotBlank(groups = {ValidGroup.Crud.Create.class},message = "表单头像不能为空")
    @TableField("`logo`")
    private String logo;


    /**
     * 分组ID
     */
    @ApiModelProperty(value = "分组ID", required = true)
    @NotNull(groups = {ValidGroup.Crud.Create.class},message = "请设置分组")
    @TableField("`group_id`")
    private Long groupId;

    /**
     * 表单设置内容
     */
    @ApiModelProperty(value = "表单设置内容", required = true)
    @NotBlank(groups = {ValidGroup.Crud.Create.class},message = "请设置表单")
    @TableField("`form_items`")
    private String formItems;
    /**
     * pc表单内容
     */
    @TableField("`form_items_pc`")
    private String formItemsPc;

    /**
     * 流程设置内容
     */
    @ApiModelProperty(value = "流程设置内容", required = true)
    @NotBlank(groups = {ValidGroup.Crud.Create.class},message = "请设置流程")
    @TableField("`process`")
    private String process;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @TableField("`remark`")
    private String remark;

    @ApiModelProperty(hidden = true)
    @TableField("`sort`")
    private Integer sort;

    /**
     * 0 正常 1=隐藏
     */
    @ApiModelProperty("0 正常 1=隐藏")
    @TableField("`is_hidden`")
    private Boolean hidden;



    /**
     * 0 正常 1=停用
     */
    @ApiModelProperty("0 正常 1=停用")
    @TableField("`is_stop`")
    private Boolean stop;

    /**
     * 流程管理员
     */
    @ApiModelProperty("流程管理员")
    @TableField("`admin_id`")
    private String adminId;


    /**
     * 管理员
     */
    @ApiModelProperty(value = "管理员", required = true)
    @NotBlank(groups = {ValidGroup.Crud.Create.class},message = "管理员不能为空")
    @TableField("`admin`")
    private String admin;

    /**
     * 范围描述显示
     */
    @ApiModelProperty("范围描述显示")
    @TableField("`range_show`")
    private String rangeShow;

    /**
     * 唯一性id
     */
    @ApiModelProperty("唯一性id")
    @TableField("`unique_id`")
    private String uniqueId;


    /**
     * 版本
     */
    @ApiModelProperty("版本")
    @TableField("`version`")
    private Integer version;



}
