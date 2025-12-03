package cc.flyflow.common.dto.flow.node.parent;

import cc.flyflow.common.constants.ProcessInstanceConstant;
import cc.flyflow.common.dto.flow.NodeUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhj
 * @version 1.0
 * @description: TODO
 * @date 2024/4/3 16:56
 */
@Data
public class SuperUserNode extends SuperUserRootNode {



    /**
     * 部门主管级别
     */
    @ApiModelProperty("部门主管级别")
    private Integer deptLeaderLevel;

    /**
     * 表单id
     */
    @ApiModelProperty("表单id")
    private String formUserId;
    /**
     * 表单名称
     */
    @ApiModelProperty("表单名称")
    private String formUserName;
    /**
     * 节点用户列表
     */
    @ApiModelProperty("节点用户列表")
    private List<NodeUser> nodeUserList;

    /**
     * 角色列表
     */
    @ApiModelProperty("角色列表")
    private List<NodeUser> roleList;
    /**
     * 部门组件时  指定人员是主管还是部门下的员工
     * {@link ProcessInstanceConstant.AssignedTypeFormDeptUserTypeClass}
     */
    @ApiModelProperty("部门组件时  指定人员是主管还是部门下的员工 {@link ProcessInstanceConstant.AssignedTypeFormDeptUserTypeClass}")
    private String deptUserType;
    /**
     * 是否包含子级部门
     */
    @ApiModelProperty("是否包含子级部门")
    private Boolean containChildrenDept;


    /**
     * 是否是多选
     */
    @ApiModelProperty("是否是多选")
    private Boolean multiple;
    /**
     * 人员指定类型
     */
    @ApiModelProperty("人员指定类型")
    private Integer assignedType;
}
