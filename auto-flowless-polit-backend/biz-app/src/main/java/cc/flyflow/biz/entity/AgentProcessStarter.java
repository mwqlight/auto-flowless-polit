package cc.flyflow.biz.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 智能体流程发起人范围表
 * </p>
 *
 * @author xiaoge
 * @since 2023-05-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("agent_process_starter")
public class AgentProcessStarter extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 智能体流程ID
     */
    @TableField("process_id")
    private Long processId;

    /**
     * 发起人范围数据
     */
    @TableField("data")
    private String data;

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
