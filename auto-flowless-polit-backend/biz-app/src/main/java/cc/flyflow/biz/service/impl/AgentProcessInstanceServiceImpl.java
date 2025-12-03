package cc.flyflow.biz.service.impl;

import cc.flyflow.biz.entity.AgentProcess;
import cc.flyflow.biz.entity.AgentProcessInstance;
import cc.flyflow.biz.mapper.AgentProcessInstanceMapper;
import cc.flyflow.biz.service.AgentProcessInstanceService;
import cc.flyflow.biz.service.AgentProcessService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 智能体流程实例Service实现类
 *
 * @author
 * @since 2023-10-01
 */
@Service
public class AgentProcessInstanceServiceImpl extends ServiceImpl<AgentProcessInstanceMapper, AgentProcessInstance> implements AgentProcessInstanceService {

    @Resource
    private AgentProcessInstanceMapper agentProcessInstanceMapper;

    @Resource
    private AgentProcessService agentProcessService;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AgentProcessInstance executeAgentProcess(String flowId, Map<String, Object> formData) {
        // 获取流程信息
        AgentProcess agentProcess = agentProcessService.getAgentProcessDetail(flowId);
        if (agentProcess == null) {
            throw new RuntimeException("流程不存在");
        }

        if (agentProcess.getStatus() != 1) {
            throw new RuntimeException("流程未启用");
        }

        // 创建流程实例
        AgentProcessInstance instance = new AgentProcessInstance();
        instance.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        instance.setProcessInstanceId(java.util.UUID.randomUUID().toString().replace("-", ""));
        instance.setFlowId(flowId);
        instance.setFlowName(agentProcess.getFlowName());
        instance.setStatus("running");
        instance.setInitiator("system"); // TODO: 从登录信息获取发起人
        try {
            instance.setFormData(objectMapper.writeValueAsString(formData));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("表单数据转换失败", e);
        }
        instance.setFormJson(agentProcess.getFormJson());
        instance.setFlowJson(agentProcess.getFlowJson());
        instance.setResult("success");
        instance.setExecutionTime(LocalDateTime.now());
        instance.setDelFlag(0);
        instance.setCreateTime(LocalDateTime.now());
        instance.setUpdateTime(LocalDateTime.now());

        // 插入实例数据
        agentProcessInstanceMapper.insert(instance);

        try {
            // TODO: 执行智能体流程逻辑
            // 这里可以调用工作流引擎或者自定义的流程执行逻辑
            // 模拟流程执行
            Thread.sleep(2000);

            // 更新实例状态为已完成
            instance.setStatus("completed");
            instance.setCompletionTime(LocalDateTime.now());
            instance.setDuration(Duration.between(instance.getExecutionTime(), instance.getCompletionTime()).getSeconds());
            instance.setUpdateTime(LocalDateTime.now());
            agentProcessInstanceMapper.updateById(instance);
        } catch (Exception e) {
            // 更新实例状态为失败
            instance.setStatus("failed");
            instance.setResult("failed");
            instance.setErrorMessage(e.getMessage());
            instance.setCompletionTime(LocalDateTime.now());
            instance.setDuration(Duration.between(instance.getExecutionTime(), instance.getCompletionTime()).getSeconds());
            instance.setUpdateTime(LocalDateTime.now());
            agentProcessInstanceMapper.updateById(instance);
            throw new RuntimeException("流程执行失败", e);
        }

        return instance;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean terminateAgentProcessInstance(String processInstanceId) {
        AgentProcessInstance instance = agentProcessInstanceMapper.selectByProcessInstanceId(processInstanceId);
        if (instance == null) {
            return false;
        }

        if (!"running".equals(instance.getStatus())) {
            throw new RuntimeException("流程实例不是运行状态");
        }

        // TODO: 终止流程执行逻辑

        // 更新实例状态为已终止
        instance.setStatus("terminated");
        instance.setCompletionTime(LocalDateTime.now());
        instance.setDuration(Duration.between(instance.getExecutionTime(), instance.getCompletionTime()).getSeconds());
        instance.setUpdateTime(LocalDateTime.now());

        return agentProcessInstanceMapper.updateById(instance) > 0;
    }

    @Override
    public AgentProcessInstance getAgentProcessInstanceDetail(String processInstanceId) {
        return agentProcessInstanceMapper.selectByProcessInstanceId(processInstanceId);
    }

    @Override
    public IPage<AgentProcessInstance> queryAgentProcessInstanceList(int pageNum, int pageSize, String flowName, String status, String executionTimeStart, String executionTimeEnd) {
        Page<AgentProcessInstance> page = new Page<>(pageNum, pageSize);
        QueryWrapper<AgentProcessInstance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);

        if (flowName != null && !flowName.isEmpty()) {
            queryWrapper.like("flow_name", flowName);
        }

        if (status != null && !status.isEmpty()) {
            queryWrapper.eq("status", status);
        }

        if (executionTimeStart != null && !executionTimeStart.isEmpty()) {
            queryWrapper.ge("execution_time", executionTimeStart);
        }

        if (executionTimeEnd != null && !executionTimeEnd.isEmpty()) {
            queryWrapper.le("execution_time", executionTimeEnd);
        }

        queryWrapper.orderByDesc("create_time");

        return agentProcessInstanceMapper.selectPage(page, queryWrapper);
    }
}