package cc.flyflow.biz.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 智能体流程组表
 * </p>
 *
 * @author xiaoge
 * @since 2023-05-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("agent_process_group")
public class AgentProcessGroup extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分组名称
     */
    @TableField("name")
    private String name;

    /**
     * 分组描述
     */
    @TableField("description")
    private String description;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

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
