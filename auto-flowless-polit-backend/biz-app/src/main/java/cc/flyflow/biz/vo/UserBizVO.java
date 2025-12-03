package cc.flyflow.biz.vo;

import cc.flyflow.biz.entity.User;
import cc.flyflow.common.dto.flow.NodeUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户对象
 */
@ApiModel(description = "用户对象")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBizVO extends User {

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String deptName;
    /**
     * 直属主管对象集合
     */
    @ApiModelProperty("直属主管对象集合")
    private List<NodeUser> parentShow;
    /**
     * 部门id集合
     */
    @ApiModelProperty("部门id集合")
    private List<String> deptIdList;
    /**
     * 角色集合
     */
    @ApiModelProperty("角色集合")
    private Set<String> roles;
    /**
     * 权限集合
     */
    @ApiModelProperty("权限集合")
    private Set<String> perms;
    /**
     * 扩展字段
     */
    @ApiModelProperty("扩展字段")
    private List<UserFieldDataVo> userFieldDataList;
    /**
     * 参数数据
     */
    @ApiModelProperty("参数数据")
    private Map<String,Object> fieldData;
    /**
     * 验证码
     */
    @ApiModelProperty("验证码")
    private String verifyCode;
    /**
     * 验证码key
     */
    @ApiModelProperty("验证码key")
    private String verifyCodeKey;
    /**
     * 角色id集合
     */
    @ApiModelProperty("角色id集合")
    private List<String> roleIds;


}
