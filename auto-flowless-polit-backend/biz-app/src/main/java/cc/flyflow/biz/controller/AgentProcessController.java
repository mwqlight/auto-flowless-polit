package cc.flyflow.biz.controller;

import cc.flyflow.biz.entity.AgentProcess;
import cc.flyflow.biz.service.AgentProcessService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 智能体流程Controller
 *
 * @author
 * @since 2023-10-01
 */
@RestController
@RequestMapping("/agent-process")
@Tag(name = "智能体流程管理", description = "智能体流程相关接口")
public class AgentProcessController {

    @Resource
    private AgentProcessService agentProcessService;

    /**
     * 创建智能体流程
     *
     * @param agentProcess 智能体流程信息
     * @return 智能体流程信息
     */
    @PostMapping
    @Operation(summary = "创建智能体流程", description = "创建智能体流程")
    public AgentProcess createAgentProcess(@RequestBody AgentProcess agentProcess) {
        return agentProcessService.createAgentProcess(agentProcess);
    }

    /**
     * 更新智能体流程
     *
     * @param agentProcess 智能体流程信息
     * @return 智能体流程信息
     */
    @PutMapping
    @Operation(summary = "更新智能体流程", description = "更新智能体流程")
    public AgentProcess updateAgentProcess(@RequestBody AgentProcess agentProcess) {
        return agentProcessService.updateAgentProcess(agentProcess);
    }

    /**
     * 删除智能体流程
     *
     * @param id 智能体流程ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除智能体流程", description = "删除智能体流程")
    public boolean deleteAgentProcess(@PathVariable String id) {
        return agentProcessService.deleteAgentProcess(id);
    }

    /**
     * 启用智能体流程
     *
     * @param id 智能体流程ID
     * @return 启用结果
     */
    @PostMapping("/enable/{id}")
    @Operation(summary = "启用智能体流程", description = "启用智能体流程")
    public boolean enableAgentProcess(@PathVariable String id) {
        return agentProcessService.enableAgentProcess(id);
    }

    /**
     * 停用智能体流程
     *
     * @param id 智能体流程ID
     * @return 停用结果
     */
    @PostMapping("/disable/{id}")
    @Operation(summary = "停用智能体流程", description = "停用智能体流程")
    public boolean disableAgentProcess(@PathVariable String id) {
        return agentProcessService.disableAgentProcess(id);
    }

    /**
     * 设置主流程
     *
     * @param id 智能体流程ID
     * @return 设置结果
     */
    @PostMapping("/set-main/{id}")
    @Operation(summary = "设置主流程", description = "设置主流程")
    public boolean setMainProcess(@PathVariable String id) {
        return agentProcessService.setMainProcess(id);
    }

    /**
     * 根据唯一标识获取智能体流程列表
     *
     * @param uniqueId 唯一标识
     * @return 智能体流程列表
     */
    @GetMapping("/unique/{uniqueId}")
    @Operation(summary = "根据唯一标识获取智能体流程列表", description = "根据唯一标识获取智能体流程列表")
    public List<AgentProcess> getAgentProcessListByUniqueId(@PathVariable String uniqueId) {
        return agentProcessService.getAgentProcessListByUniqueId(uniqueId);
    }

    /**
     * 获取智能体流程详情
     *
     * @param id 智能体流程ID
     * @return 智能体流程详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取智能体流程详情", description = "获取智能体流程详情")
    public AgentProcess getAgentProcessDetail(@PathVariable String id) {
        return agentProcessService.getAgentProcessDetail(id);
    }

    /**
     * 分页查询智能体流程列表
     *
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @param flowName 流程名称
     * @param status   状态
     * @return 智能体流程列表
     */
    @GetMapping("/list")
    @Operation(summary = "分页查询智能体流程列表", description = "分页查询智能体流程列表")
    public IPage<AgentProcess> queryAgentProcessList(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int pageNum,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") int pageSize,
            @Parameter(description = "流程名称") @RequestParam(required = false) String flowName,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status) {
        return agentProcessService.queryAgentProcessList(pageNum, pageSize, flowName, status);
    }
}