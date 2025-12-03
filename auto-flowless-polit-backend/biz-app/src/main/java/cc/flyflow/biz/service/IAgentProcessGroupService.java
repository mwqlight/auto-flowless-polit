package cc.flyflow.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cc.flyflow.biz.entity.AgentProcessGroup;
import cc.flyflow.common.dto.R;

import java.util.List;

/**
 * <p>
 * 智能体流程组服务类
 * </p>
 *
 * @author xiaoge
 * @since 2023-05-25
 */
public interface IAgentProcessGroupService extends IService<AgentProcessGroup> {

    /**
     * 智能体流程组列表
     *
     * @return
     */
    R<List<AgentProcessGroup>> queryList();

    /**
     * 新增智能体流程分组
     *
     * @param agentProcessGroup 智能体流程分组数据
     * @return 添加结果
     */
    R create(AgentProcessGroup agentProcessGroup);

    /**
     * 上移排序
     *
     * @param agentProcessGroup 智能体流程分组数据
     * @return 添加结果
     */
    R topSort(AgentProcessGroup agentProcessGroup);

    /**
     * 下移排序
     *
     * @param agentProcessGroup 智能体流程分组数据
     * @return 添加结果
     */
    R bottomSort(AgentProcessGroup agentProcessGroup);

    /**
     * 修改智能体流程分组
     *
     * @param agentProcessGroup 智能体流程分组数据
     * @return 添加结果
     */
    R edit(AgentProcessGroup agentProcessGroup);

    /**
     * 删除智能体流程分组
     * @param id 智能体流程分组ID
     * @return
     */
    R delete(long id);
}
