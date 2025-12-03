package cc.flyflow.biz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 智能体流程实例实体类
 *
 * @author
 * @since 2023-10-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("agent_process_instance")
public class AgentProcessInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("id")
    private String id;

    /**
     * 流程实例ID
     */
    @TableField("process_instance_id")
    private String processInstanceId;

    /**
     * 流程ID
     */
    @TableField("flow_id")
    private String flowId;

    /**
     * 流程名称
     */
    @TableField("flow_name")
    private String flowName;

    /**
     * 状态：running-运行中，completed-已完成，terminated-已终止，failed-失败
     */
    @TableField("status")
    private String status;

    /**
     * 发起人
     */
    @TableField("initiator")
    private String initiator;

    /**
     * 表单数据
     */
    @TableField("form_data")
    private String formData;

    /**
     * 表单JSON配置
     */
    @TableField("form_json")
    private String formJson;

    /**
     * 流程JSON配置
     */
    @TableField("flow_json")
    private String flowJson;

    /**
     * 执行结果：success-成功，failed-失败
     */
    @TableField("result")
    private String result;

    /**
     * 错误信息
     */
    @TableField("error_message")
    private String errorMessage;

    /**
     * 执行时间
     */
    @TableField("execution_time")
    private LocalDateTime executionTime;

    /**
     * 完成时间
     */
    @TableField("completion_time")
    private LocalDateTime completionTime;

    /**
     * 执行时长（秒）
     */
    @TableField("duration")
    private Long duration;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 删除标记：0-正常，1-删除
     */
    @TableField("del_flag")
    private Integer delFlag;
}