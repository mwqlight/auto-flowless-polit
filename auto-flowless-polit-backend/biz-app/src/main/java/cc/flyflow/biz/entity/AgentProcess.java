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
 * 智能体流程实体类
 *
 * @author
 * @since 2023-10-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("agent_process")
public class AgentProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("id")
    private String id;

    /**
     * 流程名称
     */
    @TableField("flow_name")
    private String flowName;

    /**
     * 唯一标识
     */
    @TableField("unique_id")
    private String uniqueId;

    /**
     * 流程描述
     */
    @TableField("description")
    private String description;

    /**
     * 版本号
     */
    @TableField("version")
    private String version;

    /**
     * 状态：0-停用，1-启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否主流程：0-否，1-是
     */
    @TableField("is_main")
    private Integer isMain;

    /**
     * 表单名称
     */
    @TableField("form_name")
    private String formName;

    /**
     * 表单JSON配置
     */
    @TableField("form_json")
    private String formJson;

    /**
     * 表单描述
     */
    @TableField("form_description")
    private String formDescription;

    /**
     * 流程JSON配置
     */
    @TableField("flow_json")
    private String flowJson;

    /**
     * 流程描述
     */
    @TableField("flow_description")
    private String flowDescription;

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