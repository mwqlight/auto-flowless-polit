package cc.flyflow.web.controller;

import cc.flyflow.biz.entity.User;
import cc.flyflow.biz.service.IOrgService;
import cc.flyflow.biz.service.IUserBizService;
import cc.flyflow.biz.service.IUserService;
import cc.flyflow.biz.vo.UserBizVO;
import cc.flyflow.biz.vo.UserListQueryVO;
import cc.flyflow.common.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户接口
 */
@Api(value = "user", tags = {"用户接口"})
@RestController
@RequestMapping(value = {"user"})
public class UserController {

    @Resource
    private IUserService userService;
    @Resource
    private IUserBizService userBizService;
    @Resource
    private IOrgService orgService;


    /**
     * 创建用户
     * @param user
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserBizVO", name = "user", value = "", required = true)
    })
    @ApiOperation(value = "创建用户", notes = "创建用户", httpMethod = "POST")
    @PostMapping("create")
    public R create(@RequestBody UserBizVO user){
        return userService.createUser(user);
    }
    /**
     * 修改用户
     * @param user
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserBizVO", name = "user", value = "", required = true)
    })
    @ApiOperation(value = "修改用户", notes = "修改用户", httpMethod = "PUT")
    @PutMapping("edit")
    public R editUser(@RequestBody UserBizVO user){
        return userService.editUser(user);
    }



    /**
     * 修改密码
     * @param user
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "User", name = "user", value = "", required = true)
    })
    @ApiOperation(value = "修改密码", notes = "修改密码", httpMethod = "POST")
    @PostMapping("password")
    public R password(@RequestBody User user){
        return userService.password(user);
    }
    /**
     * 修改状态
     * @param user
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "User", name = "user", value = "", required = true)
    })
    @ApiOperation(value = "修改状态", notes = "修改状态", httpMethod = "POST")
    @PostMapping("status")
    public R status(@RequestBody User user){
        return userService.status(user);
    }

    /**
     * 获取当前用户详细信息
     *

     * @return
     */
    @ApiOperation(value = "获取当前用户详细信息", notes = "获取当前用户详细信息", httpMethod = "GET")
    @SneakyThrows
    @GetMapping("getCurrentUserDetail")
    public R getCurrentUserDetail() {

        return userBizService.getCurrentUserDetail();

    }

    /**
     * 获取用户详细信息
     * @param user
     * @return
     */
    @ApiOperation(value = "获取用户详细信息", notes = "获取用户详细信息", httpMethod = "GET")
    @GetMapping("getUserDetail")
    public R getUserDetail(String userId){
        return orgService.getUserDetail(userId);
    }


    /**
     * 查询用户列表
     * @param userListQueryVO
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserListQueryVO", name = "userListQueryVO", value = "", required = true)
    })
    @ApiOperation(value = "查询用户列表", notes = "查询用户列表", httpMethod = "POST")
    @PostMapping("queryList")
    public R queryList(@RequestBody UserListQueryVO userListQueryVO){
        return userBizService.queryList(userListQueryVO);
    }
    /**
     * 删除用户
     * @param userListQueryVO
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "User", name = "userListQueryVO", value = "", required = true)
    })
    @ApiOperation(value = "删除用户", notes = "删除用户", httpMethod = "DELETE")
    @DeleteMapping("delete")
    public R delete(@RequestBody User userListQueryVO){
        return orgService.delete(userListQueryVO);
    }
}
