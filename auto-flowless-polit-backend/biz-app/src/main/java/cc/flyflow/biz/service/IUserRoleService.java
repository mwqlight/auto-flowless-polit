package cc.flyflow.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cc.flyflow.biz.entity.UserRole;
import cc.flyflow.common.dto.R;

import java.util.List;

/**
 * <p>
 * 用户-角色 服务类
 * </p>
 *
 * @author Vincent
 * @since 2023-06-08
 */
public interface IUserRoleService extends IService<UserRole> {
    /**
     * 根据用户id获取角色key
     *
     * @param userId
     * @return
     */
    R<List<UserRole>> queryListByUserId(String userId);

}
