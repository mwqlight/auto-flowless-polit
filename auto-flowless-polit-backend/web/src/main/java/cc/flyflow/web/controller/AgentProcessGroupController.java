package cc.flyflow.web.controller;

import cc.flyflow.biz.entity.AgentProcessGroup;
import cc.flyflow.biz.service.IAgentProcessGroupService;
import cc.flyflow.common.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 智能体流程组接口
 */
@Api(value = "agentProcessGroup", tags = {"智能体流程组接口"})
@RestController
@RequestMapping(value = {"agentProcessGroup"})
public class AgentProcessGroupController {

    @Resource
    private IAgentProcessGroupService agentProcessGroupService;

    /**
     * 智能体流程组列表
     *
     * @return
     */
    @ApiOperation(value = "智能体流程组列表", notes = "智能体流程组列表", httpMethod = "GET")
    @GetMapping("list")
    public R<List<AgentProcessGroup>> queryList() {
        return agentProcessGroupService.queryList();
    }

    /**
     * 新增智能体流程分组
     *
     * @param agentProcessGroup 智能体流程分组数据
     * @return 添加结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "AgentProcessGroup", name = "agentProcessGroup", value = "智能体流程分组数据", required = true)
    })
    @ApiOperation(value = "新增智能体流程分组", notes = "新增智能体流程分组", httpMethod = "POST")
    @PostMapping("create")
    public R create(@RequestBody AgentProcessGroup agentProcessGroup) {
        return agentProcessGroupService.create(agentProcessGroup);
    }

    /**
     * 上移排序
     *
     * @param agentProcessGroup 智能体流程分组数据
     * @return 添加结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "AgentProcessGroup", name = "agentProcessGroup", value = "智能体流程分组数据", required = true)
    })
    @ApiOperation(value = "上移排序", notes = "上移排序", httpMethod = "POST")
    @PostMapping("topSort")
    public R topSort(@RequestBody AgentProcessGroup agentProcessGroup) {
        return agentProcessGroupService.topSort(agentProcessGroup);
    }

    /**
     * 下移排序
     *
     * @param agentProcessGroup 智能体流程分组数据
     * @return 添加结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "AgentProcessGroup", name = "agentProcessGroup", value = "智能体流程分组数据", required = true)
    })
    @ApiOperation(value = "下移排序", notes = "下移排序", httpMethod = "POST")
    @PostMapping("bottomSort")
    public R bottomSort(@RequestBody AgentProcessGroup agentProcessGroup) {
        return agentProcessGroupService.bottomSort(agentProcessGroup);
    }

    /**
     * 修改智能体流程分组
     *
     * @param agentProcessGroup 智能体流程分组数据
     * @return 添加结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "AgentProcessGroup", name = "agentProcessGroup", value = "智能体流程分组数据", required = true)
    })
    @ApiOperation(value = "修改智能体流程分组", notes = "修改智能体流程分组", httpMethod = "POST")
    @PostMapping("edit")
    public R edit(@RequestBody AgentProcessGroup agentProcessGroup) {
        return agentProcessGroupService.edit(agentProcessGroup);
    }

    /**
     * 删除智能体流程分组
     * @param id 智能体流程分组ID
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "智能体流程分组ID", required = true)
    })
    @ApiOperation(value = "删除智能体流程分组", notes = "删除智能体流程分组", httpMethod = "DELETE")
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable long id){
        return agentProcessGroupService.delete(id);
    }
}
