package cc.flyflow.biz.service.impl;

import cc.flyflow.biz.entity.AgentProcess;
import cc.flyflow.biz.mapper.AgentProcessMapper;
import cc.flyflow.biz.service.AgentProcessService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 智能体流程Service实现类
 *
 * @author
 * @since 2023-10-01
 */
@Service
public class AgentProcessServiceImpl extends ServiceImpl<AgentProcessMapper, AgentProcess> implements AgentProcessService {

    @Resource
    private AgentProcessMapper agentProcessMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AgentProcess createAgentProcess(AgentProcess agentProcess) {
        // 生成ID
        agentProcess.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
        // 设置默认值
        agentProcess.setStatus(agentProcess.getStatus() == null ? 1 : agentProcess.getStatus());
        agentProcess.setIsMain(agentProcess.getIsMain() == null ? 0 : agentProcess.getIsMain());
        agentProcess.setDelFlag(0);
        agentProcess.setCreateTime(LocalDateTime.now());
        agentProcess.setUpdateTime(LocalDateTime.now());

        // 插入数据
        agentProcessMapper.insert(agentProcess);

        // 如果设置为主流程，停用同唯一标识的其他流程
        if (agentProcess.getIsMain() == 1) {
            setMainProcess(agentProcess.getId());
        }

        return agentProcess;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AgentProcess updateAgentProcess(AgentProcess agentProcess) {
        // 设置更新时间
        agentProcess.setUpdateTime(LocalDateTime.now());

        // 更新数据
        agentProcessMapper.updateById(agentProcess);

        // 如果设置为主流程，停用同唯一标识的其他流程
        if (agentProcess.getIsMain() == 1) {
            setMainProcess(agentProcess.getId());
        }

        return agentProcess;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAgentProcess(String id) {
        AgentProcess agentProcess = new AgentProcess();
        agentProcess.setId(id);
        agentProcess.setDelFlag(1);
        agentProcess.setUpdateTime(LocalDateTime.now());

        return agentProcessMapper.updateById(agentProcess) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enableAgentProcess(String id) {
        AgentProcess agentProcess = new AgentProcess();
        agentProcess.setId(id);
        agentProcess.setStatus(1);
        agentProcess.setUpdateTime(LocalDateTime.now());

        return agentProcessMapper.updateById(agentProcess) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disableAgentProcess(String id) {
        AgentProcess agentProcess = new AgentProcess();
        agentProcess.setId(id);
        agentProcess.setStatus(0);
        agentProcess.setUpdateTime(LocalDateTime.now());

        return agentProcessMapper.updateById(agentProcess) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setMainProcess(String id) {
        AgentProcess agentProcess = agentProcessMapper.selectById(id);
        if (agentProcess == null) {
            return false;
        }

        // 停用同唯一标识的其他流程
        agentProcessMapper.disableOtherProcesses(agentProcess.getUniqueId(), id);

        // 设置当前流程为主流程并启用
        AgentProcess updateProcess = new AgentProcess();
        updateProcess.setId(id);
        updateProcess.setIsMain(1);
        updateProcess.setStatus(1);
        updateProcess.setUpdateTime(LocalDateTime.now());

        return agentProcessMapper.updateById(updateProcess) > 0;
    }

    @Override
    public List<AgentProcess> getAgentProcessListByUniqueId(String uniqueId) {
        return agentProcessMapper.selectByUniqueId(uniqueId);
    }

    @Override
    public AgentProcess getAgentProcessDetail(String id) {
        return agentProcessMapper.selectById(id);
    }

    @Override
    public IPage<AgentProcess> queryAgentProcessList(int pageNum, int pageSize, String flowName, Integer status) {
        Page<AgentProcess> page = new Page<>(pageNum, pageSize);
        QueryWrapper<AgentProcess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", 0);

        if (flowName != null && !flowName.isEmpty()) {
            queryWrapper.like("flow_name", flowName);
        }

        if (status != null) {
            queryWrapper.eq("status", status);
        }

        queryWrapper.orderByDesc("create_time");

        return agentProcessMapper.selectPage(page, queryWrapper);
    }
}