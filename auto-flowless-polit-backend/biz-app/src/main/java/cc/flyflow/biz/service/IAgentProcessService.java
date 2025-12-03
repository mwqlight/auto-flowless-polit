package cc.flyflow.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cc.flyflow.biz.entity.AgentProcess;
import cc.flyflow.biz.vo.AgentProcessVO;
import cc.flyflow.common.dto.R;

import java.util.List;

/**
 * <p>
 * 智能体流程服务类
 * </p>
 *
 * @author xiaoge
 * @since 2023-05-25
 */
public interface IAgentProcessService extends IService<AgentProcess> {

    /**
     * 获取详细数据
     *
     * @param flowId 智能体流程ID
     * @return
     */
    R<AgentProcessVO> getDetail(String flowId);

    /**
     * 根据智能体流程唯一标识查询流程列表
     * @param uniqueId 智能体流程唯一标识
     * @return
     */
    R<AgentProcessVO> getListByUniqueId(String uniqueId);

    /**
     * 设置主智能体流程
     * @param flowId 智能体流程ID
     * @return
     */
    R setMainProcess(String flowId);

    AgentProcess getByFlowId(String flowId);

    /**
     * 查询智能体流程数据 包括已经被删除的
     * 慎用
     * @param flowId 智能体流程ID
     * @return
     */
    AgentProcess getByFlowIdContainDeleted(String flowId);

    AgentProcess getByUniqueId(String uniqueId);

    void updateByFlowId(AgentProcess agentProcess);

    void stop(String flowId);

    /**
     * 创建智能体流程
     *
     * @param agentProcessVO 智能体流程数据
     * @return
     */
    R create(AgentProcessVO agentProcessVO);

    /**
     * 编辑智能体流程
     *
     * @param flowId 智能体流程ID
     * @param type       类型 stop using delete
     * @return 操作结果
     */
    R update(String flowId, String type, Long groupId);

    /**
     * 查询所有关联的智能体流程id
     * @param flowIdList 智能体流程ID列表
     * @return
     */
    R<List<String>> getAllRelatedFlowId(List<String> flowIdList);
}
