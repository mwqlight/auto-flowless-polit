package cc.flyflow.biz.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 智能体流程主流程表
 * </p>
 *
 * @author xiaoge
 * @since 2023-05-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("agent_process_main")
public class AgentProcessMain extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 智能体流程唯一标识
     */
    @TableField("unique_id")
    private String uniqueId;

    /**
     * 智能体流程名称
     */
    @TableField("name")
    private String name;

    /**
     * 智能体流程描述
     */
    @TableField("description")
    private String description;

    /**
     * 智能体流程组ID
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 智能体流程配置
     */
    @TableField("config")
    private String config;

    /**
     * 智能体流程表单配置
     */
    @TableField("form_config")
    private String formConfig;

    /**
     * 智能体流程状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 智能体流程创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 智能体流程更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
