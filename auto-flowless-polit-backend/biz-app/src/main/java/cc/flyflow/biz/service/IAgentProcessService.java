package cc.flyflow.biz.service;

import cc.flyflow.biz.entity.AgentProcess;
import cc.flyflow.biz.vo.AgentProcessVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 智能体流程Service接口
 */
public interface IAgentProcessService extends IService<AgentProcess> {

    /**
     * 创建智能体流程
     * @param agentProcessVO 智能体流程VO
     * @return 智能体流程VO
     */
    AgentProcessVO create(AgentProcessVO agentProcessVO);

    /**
     * 更新智能体流程
     * @param agentProcessVO 智能体流程VO
     * @return 智能体流程VO
     */
    AgentProcessVO update(AgentProcessVO agentProcessVO);

    /**
     * 根据ID查询智能体流程
     * @param id 流程ID
     * @return 智能体流程VO
     */
    AgentProcessVO getById(Long id);

    /**
     * 根据唯一标识查询智能体流程
     * @param uniqueId 唯一标识
     * @return 智能体流程VO
     */
    AgentProcessVO getByUniqueId(String uniqueId);

    /**
     * 查询智能体流程列表
     * @param agentProcess 智能体流程实体
     * @return 智能体流程列表
     */
    List<AgentProcessVO> list(AgentProcess agentProcess);

    /**
     * 发布智能体流程
     * @param id 流程ID
     * @return 智能体流程VO
     */
    AgentProcessVO publish(Long id);

    /**
     * 停用智能体流程
     * @param id 流程ID
     * @return 智能体流程VO
     */
    AgentProcessVO stop(Long id);

    /**
     * 删除智能体流程
     * @param id 流程ID
     * @return 是否删除成功
     */
    boolean delete(Long id);
}