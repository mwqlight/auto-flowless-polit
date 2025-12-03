package cc.flyflow.biz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@ApiModel
@Getter
@Setter
@Accessors(chain = true)
public class ProcessForm extends BaseEntity {


    /**
     * 流程唯一id
     */
    @ApiModelProperty("流程唯一id")
    @TableField("`unique_id`")
    private String uniqueId;
    /**
     * 流程id
     */
    @ApiModelProperty("流程id")
    @TableField("`flow_id`")
    private String flowId;

    /**
     * 表单名称
     */
    @ApiModelProperty("表单名称")
    @TableField("`form_name`")
    private String formName;


    /**
     * 表单id
     */
    @ApiModelProperty("表单id")
    @TableField("`form_id`")
    private String formId;


    /**
     * 表单类型
     */
    @ApiModelProperty("表单类型")
    @TableField("`form_type`")
    private String formType;

    /**
     * 表单属性
     */
    @ApiModelProperty("表单属性")
    @TableField("`props`")
    private String props;



}
