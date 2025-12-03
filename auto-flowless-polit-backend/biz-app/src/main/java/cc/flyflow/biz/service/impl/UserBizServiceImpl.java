package cc.flyflow.biz.service.impl;

import cc.flyflow.biz.api.ApiStrategyFactory;
import cc.flyflow.biz.service.*;
import cc.flyflow.biz.vo.UserListQueryVO;
import cc.flyflow.biz.vo.third.UserDtoExtension;
import cc.flyflow.common.dto.R;
import cc.flyflow.common.dto.third.PageResultDto;
import cc.flyflow.common.dto.third.UserDto;
import cc.flyflow.common.dto.third.UserQueryDto;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xiaoge
 * @since 2023-05-05
 */
@Service
@Slf4j
public class UserBizServiceImpl  implements IUserBizService {

    @Lazy
    @Resource
    private IRoleService roleService;

    @Resource
    private IMenuService menuService;


    @Value("${role.allPermission}")
    private Boolean allPermission;







    /**
     * 获取当前用户详细信息
     *
     * @return
     */
    @Override
    public R getCurrentUserDetail() {
        try {
            String userId = StpUtil.getLoginIdAsString();

            ApiStrategy strategy = ApiStrategyFactory.getStrategy();
            if (strategy == null) {
                log.error("获取当前用户详细信息失败：ApiStrategy 为 null");
                return R.fail("获取当前用户详细信息失败");
            }

            UserDto user = strategy.getUser(userId);
            if (user == null) {
                log.error("获取当前用户详细信息失败：用户 {} 不存在", userId);
                return R.fail("获取当前用户详细信息失败");
            }

            UserDtoExtension userDtoExtension = BeanUtil.copyProperties(user, UserDtoExtension.class);
            if (userDtoExtension == null) {
                log.error("获取当前用户详细信息失败：用户 {} 转换为 UserDtoExtension 失败", userId);
                return R.fail("获取当前用户详细信息失败");
            }

            Set<String> roleKeySet = null;
            if (allPermission) {
                roleKeySet = roleService.list().stream().map(w -> w.getKey()).collect(Collectors.toSet());
            } else {
                R<Set<String>> roleKeySetR = roleService.queryRoleKeyByUserId(userId);
                if (roleKeySetR != null) {
                    roleKeySet = roleKeySetR.getData();
                }
            }

            userDtoExtension.setRoles(roleKeySet);
            if (CollUtil.isNotEmpty(roleKeySet)) {
                R<Set<String>> permsR = menuService.listRolePerms(roleKeySet);
                if (permsR != null) {
                    userDtoExtension.setPerms(permsR.getData());
                } else {
                    userDtoExtension.setPerms(new HashSet<>());
                }
            } else {
                userDtoExtension.setPerms(new HashSet<>());
            }

            return R.success(userDtoExtension);
        } catch (Exception e) {
            log.error("获取当前用户详细信息失败：", e);
            return R.fail("获取当前用户详细信息失败");
        }
    }


    /**
     * 用户管理 查询用户列表
     *
     * @param userListQueryVO
     * @return
     */
    @Override
    public R queryList(UserListQueryVO userListQueryVO) {

        UserQueryDto userQueryDto = new UserQueryDto();
        userQueryDto.setDeptId(userListQueryVO.getDeptId());
        userQueryDto.setKeywords(userListQueryVO.getKeywords());
        userQueryDto.setStatus(userListQueryVO.getStatus());
        userQueryDto.setName(userListQueryVO.getName());
        userQueryDto.setDeptIdList(userListQueryVO.getDeptIdList());
        userQueryDto.setPageNum(userListQueryVO.getPageNum());
        userQueryDto.setPageSize(userListQueryVO.getPageSize());

        PageResultDto<UserDto> resultDto = ApiStrategyFactory.getStrategy().queryUserList(userQueryDto);
        return R.success(resultDto);

    }

}
