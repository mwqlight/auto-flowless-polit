package cc.flyflow.web.controller;

import cc.flyflow.common.service.biz.IRemoteService;
import cc.flyflow.common.dto.*;
import cc.flyflow.common.dto.third.DeptDto;
import cc.flyflow.common.dto.third.MessageDto;
import cc.flyflow.common.dto.third.UserQueryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 远程请求控制器
 */
@Api(value = "remote", tags = {"远程请求控制器"})
@RestController
@RequestMapping("remote")
public class RemoteController {
    @Resource
    private IRemoteService remoteService;


    /**
     * 根据用户id查询角色id集合
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户id查询角色id集合", notes = "根据用户id查询角色id集合", httpMethod = "GET")
    @GetMapping("loadRoleIdListByUserId")
    public R<List<String>> loadRoleIdListByUserId(String userId) {
        return remoteService.loadRoleIdListByUserId(userId);
    }

    /**
     * 根据部门id获取部门列表
     *
     * @param deptIdList
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<String>", name = "deptIdList", value = "", required = true)
    })
    @ApiOperation(value = "根据部门id获取部门列表", notes = "根据部门id获取部门列表", httpMethod = "POST")
    @PostMapping("queryDeptList")
    public R<List<DeptDto>> queryDeptList(@RequestBody List<String> deptIdList) {
        return remoteService.queryDeptList(deptIdList);
    }

    /**
     * 保存待办任务
     *
     * @param messageDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "MessageDto", name = "messageDto", value = "", required = true)
    })
    @ApiOperation(value = "保存待办任务", notes = "保存待办任务", httpMethod = "POST")
    @PostMapping("saveMessage")
    public R saveMessage(@RequestBody MessageDto messageDto) {
        return remoteService.saveMessage(messageDto);
    }

    /**
     * 根据角色id集合查询用户id集合
     *
     * @param roleIdList
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<String>", name = "roleIdList", value = "", required = true)
    })
    @ApiOperation(value = "根据角色id集合查询用户id集合", notes = "根据角色id集合查询用户id集合", httpMethod = "POST")
    @PostMapping("queryUserIdListByRoleIdList")
    public R<List<String>> queryUserIdListByRoleIdList(@RequestBody List<String> roleIdList) {
        return remoteService.queryUserIdListByRoleIdList(roleIdList);
    }

    /**
     * 根据角色id集合和部门id集合查询人员id集合
     *
     * @param userQueryDto 查询对象  只关注roleIdList和deptIdList即可
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserQueryDto", name = "userQueryDto", value = "查询对象 只关注roleIdList和deptIdList即可", required = true)
    })
    @ApiOperation(value = "根据角色id集合和部门id集合查询人员id集合", notes = "根据角色id集合和部门id集合查询人员id集合", httpMethod = "POST")
    @PostMapping("queryUserIdListByRoleIdListAndDeptIdList")
    public R<List<String>> queryUserIdListByRoleIdListAndDeptIdList(@RequestBody UserQueryDto userQueryDto) {
        return remoteService.queryUserIdListByRoleIdListAndDeptIdList(userQueryDto);
    }

    /**
     * 保存抄送
     *
     * @param copyDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessInstanceCopyDto", name = "copyDto", value = "", required = true)
    })
    @ApiOperation(value = "保存抄送", notes = "保存抄送", httpMethod = "POST")
    @PostMapping("saveCC")
    public R saveCC(@RequestBody ProcessInstanceCopyDto copyDto) {
        return remoteService.saveCC(copyDto);
    }

    /**
     * 检查是否是所有的父级
     *
     * @param checkParentDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "CheckParentDto", name = "checkParentDto", value = "", required = true)
    })
    @ApiOperation(value = "检查是否是所有的父级", notes = "检查是否是所有的父级", httpMethod = "POST")
    @PostMapping("checkIsAllParent")
    public R<Boolean> checkIsAllParent(@RequestBody CheckParentDto checkParentDto) {
        return remoteService.checkIsAllParent(checkParentDto);
    }


    /**
     * 根据部门id集合查询用户id集合
     *
     * @param deptIdList
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<String>", name = "deptIdList", value = "", required = true)
    })
    @ApiOperation(value = "根据部门id集合查询用户id集合", notes = "根据部门id集合查询用户id集合", httpMethod = "POST")
    @PostMapping("queryUserIdListByDepIdList")
    public R<List<String>> queryUserIdListByDepIdList(@RequestBody List<String> deptIdList) {
        return remoteService.queryUserIdListByDepIdList(deptIdList);
    }

    /**
     * 检查是否是所有的子级
     *
     * @param checkChildDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "CheckChildDto", name = "checkChildDto", value = "", required = true)
    })
    @ApiOperation(value = "检查是否是所有的子级", notes = "检查是否是所有的子级", httpMethod = "POST")
    @PostMapping("checkIsAllChild")
    public R<Boolean> checkIsAllChild(@RequestBody CheckChildDto checkChildDto) {
        return remoteService.checkIsAllChild(checkChildDto);
    }

    /**
     * 获取用户的信息-包括扩展字段
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取用户的信息-包括扩展字段", notes = "获取用户的信息-包括扩展字段", httpMethod = "GET")
    @GetMapping("queryUserAllInfo")
    public R<Map<String, Object>> queryUserAllInfo(String userId) {
        return remoteService.queryUserAllInfo(userId);
    }


    /**
     * 查询上级部门
     *
     * @param deptId
     * @return
     */
    @ApiOperation(value = "查询上级部门", notes = "查询上级部门", httpMethod = "GET")
    @SneakyThrows
    @GetMapping("queryParentDeptList")
    public R<List<DeptDto>> queryParentDeptList(String deptId) {

        return remoteService.queryParentDeptList(deptId);
    }


    /**
     * 批量获取部门的下级部门
     *
     * @param deptIdList 部门id集合
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "List<String>", name = "deptIdList", value = "部门id集合", required = true)
    })
    @ApiOperation(value = "批量获取部门的下级部门", notes = "批量获取部门的下级部门", httpMethod = "POST")
    @PostMapping("batchQueryChildDeptList")
    public R<Map<String, List<DeptDto>>> batchQueryChildDeptList(@RequestBody List<String> deptIdList) {
        return remoteService.batchQueryChildDeptList(deptIdList);
    }

    /**
     * 开始节点事件
     *
     * @param recordParamDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessInstanceNodeRecordParamDto", name = "recordParamDto", value = "", required = true)
    })
    @ApiOperation(value = "开始节点事件", notes = "开始节点事件", httpMethod = "POST")
    @PostMapping("startNodeEvent")
    public R startNodeEvent(@RequestBody ProcessInstanceNodeRecordParamDto recordParamDto) {
        return remoteService.startNodeEvent(recordParamDto);
    }


    /**
     * 流程创建了
     *
     * @param processInstanceRecordParamDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessInstanceRecordParamDto", name = "processInstanceRecordParamDto", value = "", required = true)
    })
    @ApiOperation(value = "流程创建了", notes = "流程创建了", httpMethod = "POST")
    @PostMapping("processStartEvent")
    public R processStartEvent(@RequestBody ProcessInstanceRecordParamDto processInstanceRecordParamDto) {
        return remoteService.processStartEvent(processInstanceRecordParamDto);
    }

    /**
     * 结束节点事件
     *
     * @param recordParamDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessInstanceNodeRecordParamDto", name = "recordParamDto", value = "", required = true)
    })
    @ApiOperation(value = "结束节点事件", notes = "结束节点事件", httpMethod = "POST")
    @PostMapping("endNodeEvent")
    public R endNodeEvent(@RequestBody ProcessInstanceNodeRecordParamDto recordParamDto) {
        return remoteService.endNodeEvent(recordParamDto);
    }

    /**
     * 取消节点事件
     *
     * @param recordParamDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessInstanceNodeRecordParamDto", name = "recordParamDto", value = "", required = true)
    })
    @ApiOperation(value = "取消节点事件", notes = "取消节点事件", httpMethod = "POST")
    @PostMapping("cancelNodeEvent")
    public R cancelNodeEvent(@RequestBody ProcessInstanceNodeRecordParamDto recordParamDto) {
        return remoteService.cancelNodeEvent(recordParamDto);
    }

    /**
     * 开始设置执行人
     *
     * @param processInstanceAssignUserRecordParamDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessInstanceAssignUserRecordParamDto", name = "processInstanceAssignUserRecordParamDto", value = "", required = true)
    })
    @ApiOperation(value = "开始设置执行人", notes = "开始设置执行人", httpMethod = "POST")
    @PostMapping("createTaskEvent")
    public R createTaskEvent(@RequestBody ProcessInstanceAssignUserRecordParamDto processInstanceAssignUserRecordParamDto) {
        return remoteService.createTaskEvent(processInstanceAssignUserRecordParamDto);
    }

    /**
     * 任务结束事件
     *
     * @param processInstanceAssignUserRecordParamDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessInstanceAssignUserRecordParamDto", name = "processInstanceAssignUserRecordParamDto", value = "", required = true)
    })
    @ApiOperation(value = "任务结束事件", notes = "任务结束事件", httpMethod = "POST")
    @PostMapping("taskCompletedEvent")
    public R taskCompletedEvent(@RequestBody ProcessInstanceAssignUserRecordParamDto processInstanceAssignUserRecordParamDto) {
        return remoteService.taskCompletedEvent(processInstanceAssignUserRecordParamDto);
    }

    /**
     * 任务结束
     *
     * @param processInstanceAssignUserRecordParamDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessInstanceAssignUserRecordParamDto", name = "processInstanceAssignUserRecordParamDto", value = "", required = true)
    })
    @ApiOperation(value = "任务结束", notes = "任务结束", httpMethod = "POST")
    @PostMapping("taskEndEvent")
    public R taskEndEvent(@RequestBody ProcessInstanceAssignUserRecordParamDto processInstanceAssignUserRecordParamDto) {
        return remoteService.taskEndEvent(processInstanceAssignUserRecordParamDto);
    }


    /**
     * 实例结束
     *
     * @param processInstanceParamDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessInstanceParamDto", name = "processInstanceParamDto", value = "", required = true)
    })
    @ApiOperation(value = "实例结束", notes = "实例结束", httpMethod = "POST")
    @PostMapping("processEndEvent")
    public R processEndEvent(@RequestBody ProcessInstanceParamDto processInstanceParamDto) {
        return remoteService.processEndEvent(processInstanceParamDto);
    }



    /**
     * 查询流程管理员
     *
     * @param flowId
     * @return
     */
    @ApiOperation(value = "查询流程管理员", notes = "查询流程管理员", httpMethod = "GET")
    @GetMapping("queryProcessAdmin")
    public R<String> queryProcessAdmin(String flowId) {
        return remoteService.queryProcessAdmin(flowId);
    }



    /**
     * 查询流程数据
     *
     * @param flowId
     * @return
     */
    @ApiOperation(value = "查询流程数据", notes = "查询流程数据", httpMethod = "GET")
    @GetMapping("queryProcess")
    public R<ProcessDto> queryProcess(String flowId) {
        return remoteService.queryProcess(flowId);
    }

    /**
     * 保存节点数据
     *
     * @param processNodeDataDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessNodeDataDto", name = "processNodeDataDto", value = "", required = true)
    })
    @ApiOperation(value = "保存节点数据", notes = "保存节点数据", httpMethod = "POST")
    @PostMapping("saveNodeData")
    public R saveNodeData(@RequestBody ProcessNodeDataDto processNodeDataDto) {
        return remoteService.saveNodeData(processNodeDataDto);
    }

    /**
     * 保存执行数据
     * @param processInstanceExecutionDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessInstanceExecutionDto", name = "processInstanceExecutionDto", value = "", required = true)
    })
    @ApiOperation(value = "保存执行数据", notes = "保存执行数据", httpMethod = "POST")
    @PostMapping("saveExecution")
    public R saveExecution(@RequestBody ProcessInstanceExecutionDto processInstanceExecutionDto) {
        return remoteService.saveExecution(processInstanceExecutionDto);
    }

    /**
     * 获取节点数据
     *
     * @param flowId 流程id
     * @param nodeId 节点id
     * @return
     */
    @ApiOperation(value = "获取节点数据", notes = "获取节点数据", httpMethod = "GET")
    @GetMapping("getNodeData")
    public R<String> getNodeData(String flowId, String nodeId) {
        return remoteService.getNodeData(flowId, nodeId);
    }
}
