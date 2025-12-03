package cc.flyflow.biz.service;

import cc.flyflow.biz.entity.AgentProcess;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 智能体流程Service接口
 *
 * @author
 * @since 2023-10-01
 */
public interface AgentProcessService extends IService<AgentProcess> {

    /**
     * 创建智能体流程
     *
     * @param agentProcess 智能体流程信息
     * @return 智能体流程
     */
    AgentProcess createAgentProcess(AgentProcess agentProcess);

    /**
     * 更新智能体流程
     *
     * @param agentProcess 智能体流程信息
     * @return 智能体流程
     */
    AgentProcess updateAgentProcess(AgentProcess agentProcess);

    /**
     * 删除智能体流程
     *
     * @param id 流程ID
     * @return 是否成功
     */
    boolean deleteAgentProcess(String id);

    /**
     * 启用智能体流程
     *
     * @param id 流程ID
     * @return 是否成功
     */
    boolean enableAgentProcess(String id);

    /**
     * 停用智能体流程
     *
     * @param id 流程ID
     * @return 是否成功
     */
    boolean disableAgentProcess(String id);

    /**
     * 设置主流程
     *
     * @param id 流程ID
     * @return 是否成功
     */
    boolean setMainProcess(String id);

    /**
     * 根据唯一标识查询智能体流程列表
     *
     * @param uniqueId 唯一标识
     * @return 智能体流程列表
     */
    List<AgentProcess> getAgentProcessListByUniqueId(String uniqueId);

    /**
     * 查询智能体流程详情
     *
     * @param id 流程ID
     * @return 智能体流程
     */
    AgentProcess getAgentProcessDetail(String id);

    /**
     * 查询智能体流程列表
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param flowName 流程名称
     * @param status 状态
     * @return 分页结果
     */
    IPage<AgentProcess> queryAgentProcessList(int pageNum, int pageSize, String flowName, Integer status);
}