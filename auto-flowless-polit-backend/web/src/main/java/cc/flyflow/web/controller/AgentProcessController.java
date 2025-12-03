package cc.flyflow.web.controller;

import cc.flyflow.biz.entity.AgentProcess;
import cc.flyflow.biz.service.IAgentProcessService;
import cc.flyflow.biz.vo.AgentProcessVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 智能体流程Controller
 */
@Api(tags = "智能体流程管理")
@RestController
@RequestMapping("/api/agent-process")
public class AgentProcessController extends BaseController {

    @Autowired
    private IAgentProcessService agentProcessService;

    @ApiOperation("创建智能体流程")
    @PostMapping("/create")
    public AgentProcessVO create(@RequestBody AgentProcessVO agentProcessVO) {
        return agentProcessService.create(agentProcessVO);
    }

    @ApiOperation("更新智能体流程")
    @PostMapping("/update")
    public AgentProcessVO update(@RequestBody AgentProcessVO agentProcessVO) {
        return agentProcessService.update(agentProcessVO);
    }

    @ApiOperation("根据ID查询智能体流程")
    @GetMapping("/getById/{id}")
    public AgentProcessVO getById(@PathVariable Long id) {
        return agentProcessService.getById(id);
    }

    @ApiOperation("根据唯一标识查询智能体流程")
    @GetMapping("/getByUniqueId/{uniqueId}")
    public AgentProcessVO getByUniqueId(@PathVariable String uniqueId) {
        return agentProcessService.getByUniqueId(uniqueId);
    }

    @ApiOperation("查询智能体流程列表")
    @PostMapping("/list")
    public List<AgentProcessVO> list(@RequestBody AgentProcess agentProcess) {
        return agentProcessService.list(agentProcess);
    }

    @ApiOperation("发布智能体流程")
    @PostMapping("/publish/{id}")
    public AgentProcessVO publish(@PathVariable Long id) {
        return agentProcessService.publish(id);
    }

    @ApiOperation("停用智能体流程")
    @PostMapping("/stop/{id}")
    public AgentProcessVO stop(@PathVariable Long id) {
        return agentProcessService.stop(id);
    }

    @ApiOperation("删除智能体流程")
    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        return agentProcessService.delete(id);
    }
}