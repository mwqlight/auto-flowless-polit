package cc.flyflow.biz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 流程记录
 * </p>
 *
 * @author Vincent
 * @since 2023-07-06
 */
@ApiModel(description = "<p> 流程记录 </p>")
@Getter
@Setter
@Accessors(chain = true)
public class ProcessInstanceRecord  extends BaseEntity {



    /**
     * 流程名字
     */
    @ApiModelProperty("流程名字")
    @TableField("`name`")
    private String name;

    /**
     * 发起人的主部门id
     */
    @ApiModelProperty("发起人的主部门id")
    @TableField("`main_dept_id`")
    private String mainDeptId;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    @TableField("`logo`")
    private String logo;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @TableField("`user_id`")
    private String userId;


    /**
     * 流程id
     */
    @ApiModelProperty("流程id")
    @TableField("`flow_id`")
    private String flowId;

    /**
     * 流程实例id
     */
    @ApiModelProperty("流程实例id")
    @TableField("`process_instance_id`")
    private String processInstanceId;

    /**
     * 流程实例业务key
     */
    @ApiModelProperty("流程实例业务key")
    @TableField("`process_instance_biz_key`")
    private String processInstanceBizKey;

    /**
     * 流程实例业务编码
     */
    @ApiModelProperty("流程实例业务编码")
    @TableField("`process_instance_biz_code`")
    private String processInstanceBizCode;

    /**
     * 表单数据
     */
    @ApiModelProperty("表单数据")
    @TableField("`form_data`")
    private String formData;

    /**
     * 组id
     */
    @ApiModelProperty("组id")
    @TableField("`group_id`")
    private Long groupId;

    /**
     * 组名称
     */
    @ApiModelProperty("组名称")
    @TableField("`group_name`")
    private String groupName;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    @TableField("`status`")
    private Integer status;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    @TableField("`end_time`")
    private Date endTime;

    /**
     * 上级流程实例id
     */
    @ApiModelProperty("上级流程实例id")
    @TableField("`parent_process_instance_id`")
    private String parentProcessInstanceId;
    /**
     * 流程节点内容
     */
    @ApiModelProperty("流程节点内容")
    @TableField("`process`")
    private String process;
    /**
     * 结果
     */
    @ApiModelProperty("结果")
    @TableField("`result`")
    private Integer result;
    /**
     * 主流程节点执行id
     */
    @TableField("`parent_process_node_execution_id`")
    private String parentProcessNodeExecutionId;



}
