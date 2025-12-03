package cc.flyflow.biz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 部门-用户表
 *
 * @author Vincent
 * @since 2023-12-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("flyflow_dept_user")
public class DeptUser {


    /**
     * 部门id
     */
    @TableField("`dept_id`")
    private String  deptId;


    /**
     * 主管user_id
     */
    @TableField("`user_id`")
    private String userId;

    /**
     * 租户id
     */
    @TableField("`tenant_id`")
    private String tenantId;

    /**
     * 用户id
     */
    @TableField("id")
    private Long id;

    /**
     * 逻辑删除字段
     */
    @TableField("del_flag")
    private Boolean delFlag;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

}
