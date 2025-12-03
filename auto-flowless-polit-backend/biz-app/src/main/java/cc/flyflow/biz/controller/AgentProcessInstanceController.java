package cc.flyflow.biz.controller;

import cc.flyflow.biz.entity.AgentProcessInstance;
import cc.flyflow.biz.service.AgentProcessInstanceService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 智能体流程实例Controller
 *
 * @author
 * @since 2023-10-01
 */
@RestController
@RequestMapping("/agent-process-instance")
@Tag(name = "智能体流程实例管理", description = "智能体流程实例相关接口")
public class AgentProcessInstanceController {

    @Resource
    private AgentProcessInstanceService agentProcessInstanceService;

    /**
     * 执行智能体流程
     *
     * @param flowId    流程ID
     * @param formData 表单数据
     * @return 流程实例信息
     */
    @PostMapping("/execute/{flowId}")
    @Operation(summary = "执行智能体流程", description = "执行智能体流程")
    public AgentProcessInstance executeAgentProcess(
            @Parameter(description = "流程ID") @PathVariable String flowId,
            @Parameter(description = "表单数据") @RequestBody Map<String, Object> formData) {
        return agentProcessInstanceService.executeAgentProcess(flowId, formData);
    }

    /**
     * 终止智能体流程实例
     *
     * @param processInstanceId 流程实例ID
     * @return 终止结果
     */
    @PostMapping("/terminate/{processInstanceId}")
    @Operation(summary = "终止智能体流程实例", description = "终止智能体流程实例")
    public boolean terminateAgentProcessInstance(@PathVariable String processInstanceId) {
        return agentProcessInstanceService.terminateAgentProcessInstance(processInstanceId);
    }

    /**
     * 获取智能体流程实例详情
     *
     * @param processInstanceId 流程实例ID
     * @return 流程实例详情
     */
    @GetMapping("/{processInstanceId}")
    @Operation(summary = "获取智能体流程实例详情", description = "获取智能体流程实例详情")
    public AgentProcessInstance getAgentProcessInstanceDetail(@PathVariable String processInstanceId) {
        return agentProcessInstanceService.getAgentProcessInstanceDetail(processInstanceId);
    }

    /**
     * 分页查询智能体流程实例列表
     *
     * @param pageNum             页码
     * @param pageSize            每页数量
     * @param flowName            流程名称
     * @param status              状态
     * @param executionTimeStart  执行时间开始
     * @param executionTimeEnd    执行时间结束
     * @return 智能体流程实例列表
     */
    @GetMapping("/list")
    @Operation(summary = "分页查询智能体流程实例列表", description = "分页查询智能体流程实例列表")
    public IPage<AgentProcessInstance> queryAgentProcessInstanceList(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int pageNum,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") int pageSize,
            @Parameter(description = "流程名称") @RequestParam(required = false) String flowName,
            @Parameter(description = "状态") @RequestParam(required = false) String status,
            @Parameter(description = "执行时间开始") @RequestParam(required = false) String executionTimeStart,
            @Parameter(description = "执行时间结束") @RequestParam(required = false) String executionTimeEnd) {
        return agentProcessInstanceService.queryAgentProcessInstanceList(pageNum, pageSize, flowName, status, executionTimeStart, executionTimeEnd);
    }
}