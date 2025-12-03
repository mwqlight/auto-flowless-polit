package cc.flyflow.biz.mapper;

import cc.flyflow.biz.entity.AgentProcess;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 智能体流程Mapper接口
 *
 * @author
 * @since 2023-10-01
 */
@Mapper
public interface AgentProcessMapper extends BaseMapper<AgentProcess> {

    /**
     * 根据唯一标识查询智能体流程列表
     *
     * @param uniqueId 唯一标识
     * @return 智能体流程列表
     */
    List<AgentProcess> selectByUniqueId(@Param("uniqueId") String uniqueId);

    /**
     * 查询启用的主流程
     *
     * @param uniqueId 唯一标识
     * @return 智能体流程
     */
    AgentProcess selectEnabledMainProcess(@Param("uniqueId") String uniqueId);

    /**
     * 停用同唯一标识的其他流程
     *
     * @param uniqueId 唯一标识
     * @param excludeId 排除的流程ID
     * @return 影响行数
     */
    int disableOtherProcesses(@Param("uniqueId") String uniqueId, @Param("excludeId") String excludeId);
}