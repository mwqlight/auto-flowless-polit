package cc.flyflow.biz.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 智能体流程表
 * </p>
 *
 * @author xiaoge
 * @since 2023-05-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("agent_process")
public class AgentProcess extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 流程ID
     */
    @TableField("flow_id")
    private String flowId;

    /**
     * 流程唯一标识
     */
    @TableField("unique_id")
    private String uniqueId;

    /**
     * 流程名称
     */
    @TableField("name")
    private String name;

    /**
     * 流程描述
     */
    @TableField("description")
    private String description;

    /**
     * 流程数据
     */
    @TableField("data")
    private String data;

    /**
     * 表单数据
     */
    @TableField("form_items")
    private String formItems;

    /**
     * 流程组ID
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 是否停用
     */
    @TableField("stop")
    private Boolean stop;

    /**
     * 是否隐藏
     */
    @TableField("hidden")
    private Boolean hidden;

    /**
     * 删除状态
     */
    @TableField("deleted")
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
