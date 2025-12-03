package cc.flyflow.biz.mapper;

import cc.flyflow.biz.entity.AgentProcessInstance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 智能体流程实例Mapper接口
 *
 * @author
 * @since 2023-10-01
 */
@Mapper
public interface AgentProcessInstanceMapper extends BaseMapper<AgentProcessInstance> {

    /**
     * 根据流程ID查询实例列表
     *
     * @param flowId 流程ID
     * @return 实例列表
     */
    List<AgentProcessInstance> selectByFlowId(@Param("flowId") String flowId);

    /**
     * 根据流程实例ID查询实例
     *
     * @param processInstanceId 流程实例ID
     * @return 实例
     */
    AgentProcessInstance selectByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    /**
     * 查询运行中的实例
     *
     * @param flowId 流程ID
     * @return 实例列表
     */
    List<AgentProcessInstance> selectRunningInstances(@Param("flowId") String flowId);
}