package cc.flyflow.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cc.flyflow.biz.entity.AgentProcess;

/**
 * <p>
 * 智能体流程表 Mapper 接口
 * </p>
 *
 * @author xiaoge
 * @since 2023-05-25
 */
public interface AgentProcessMapper extends BaseMapper<AgentProcess> {

    /**
     * 根据流程ID查询智能体流程数据 包括已经被删除的
     * @param flowId 智能体流程ID
     * @return
     */
    AgentProcess selectByFlowId(String flowId);
}
