package cc.flyflow.web.controller;

import cc.flyflow.biz.constants.ValidGroup;
import cc.flyflow.biz.service.IAgentProcessService;
import cc.flyflow.biz.vo.AgentProcessVO;
import cc.flyflow.common.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 智能体流程接口
 */
@Api(value = "agentProcess", tags = {"智能体流程接口"})
@Validated
@RestController
@RequestMapping(value = "agentProcess")
public class AgentProcessController {

    @Resource
    private IAgentProcessService agentProcessService;

    /**
     * 根据智能体流程唯一标识查询流程列表
     * @param uniqueId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "uniqueId", value = "智能体流程唯一标识", required = true)
    })
    @ApiOperation(value = "根据智能体流程唯一标识查询流程列表", notes = "根据智能体流程唯一标识查询流程列表", httpMethod = "GET")
    @GetMapping("/getListByUniqueId/{uniqueId}")
    public R<AgentProcessVO> getListByUniqueId(@PathVariable String uniqueId){
        return agentProcessService.getListByUniqueId(uniqueId);
    }

    /**
     * 设置主智能体流程
     * @param flowId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "flowId", value = "智能体流程ID", required = true)
    })
    @ApiOperation(value = "设置主智能体流程", notes = "设置主智能体流程", httpMethod = "POST")
    @PostMapping("setMainProcess/{flowId}")
    public R setMainProcess(@PathVariable String flowId){
        return agentProcessService.setMainProcess(flowId);
    }

    /**
     * 获取详细数据
     *
     * @param flowId 智能体流程id
     * @return
     */
    @ApiOperation(value = "获取详细数据", notes = "获取详细数据", httpMethod = "GET")
    @GetMapping("getDetail")
    public R<AgentProcessVO> getDetail(String flowId) {
        return agentProcessService.getDetail(flowId);
    }

    /**
     * 创建智能体流程
     *
     * @param agentProcessVO
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "AgentProcessVO", name = "agentProcessVO", value = "智能体流程数据", required = true)
    })
    @ApiOperation(value = "创建智能体流程", notes = "创建智能体流程", httpMethod = "POST")
    @PostMapping("create")
    public R create(@Validated(value= ValidGroup.Crud.Create.class) @RequestBody AgentProcessVO agentProcessVO) {
        return agentProcessService.create(agentProcessVO);
    }

    /**
     * 编辑智能体流程
     *
     * @param flowId 智能体流程ID
     * @param type       类型 stop using delete
     * @param groupId 智能体流程组id
     * @return 操作结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "flowId", value = "智能体流程ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "type", value = "类型 stop using delete", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "long", name = "groupId", value = "智能体流程组id")
    })
    @ApiOperation(value = "编辑智能体流程", notes = "编辑智能体流程", httpMethod = "PUT")
    @PutMapping("update/{flowId}")
    public R update(@PathVariable String flowId,
                     @RequestParam String type,
                     @RequestParam(required = false) Long groupId){
        return agentProcessService.update(flowId, type, groupId);
    }
}
