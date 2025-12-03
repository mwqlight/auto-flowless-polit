package cc.flyflow.biz.service;

import cc.flyflow.biz.entity.AgentProcessInstance;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 智能体流程实例Service接口
 *
 * @author
 * @since 2023-10-01
 */
public interface AgentProcessInstanceService extends IService<AgentProcessInstance> {

    /**
     * 执行智能体流程
     *
     * @param flowId 流程ID
     * @param formData 表单数据
     * @return 流程实例
     */
    AgentProcessInstance executeAgentProcess(String flowId, Map<String, Object> formData);

    /**
     * 终止智能体流程实例
     *
     * @param processInstanceId 流程实例ID
     * @return 是否成功
     */
    boolean terminateAgentProcessInstance(String processInstanceId);

    /**
     * 查询智能体流程实例详情
     *
     * @param processInstanceId 流程实例ID
     * @return 流程实例
     */
    AgentProcessInstance getAgentProcessInstanceDetail(String processInstanceId);

    /**
     * 查询智能体流程实例列表
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param flowName 流程名称
     * @param status 状态
     * @param executionTimeStart 执行时间开始
     * @param executionTimeEnd 执行时间结束
     * @return 分页结果
     */
    IPage<AgentProcessInstance> queryAgentProcessInstanceList(int pageNum, int pageSize, String flowName, String status, String executionTimeStart, String executionTimeEnd);
}