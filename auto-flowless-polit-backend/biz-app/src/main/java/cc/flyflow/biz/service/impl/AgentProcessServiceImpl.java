package cc.flyflow.biz.service.impl;

import cc.flyflow.biz.entity.AgentProcess;
import cc.flyflow.biz.mapper.AgentProcessMapper;
import cc.flyflow.biz.service.IAgentProcessService;
import cc.flyflow.biz.vo.AgentProcessVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 智能体流程Service实现类
 */
@Service
public class AgentProcessServiceImpl extends ServiceImpl<AgentProcessMapper, AgentProcess> implements IAgentProcessService {

    @Override
    public AgentProcessVO create(AgentProcessVO agentProcessVO) {
        AgentProcess agentProcess = new AgentProcess();
        BeanUtils.copyProperties(agentProcessVO, agentProcess);
        save(agentProcess);
        agentProcessVO.setId(agentProcess.getId());
        return agentProcessVO;
    }

    @Override
    public AgentProcessVO update(AgentProcessVO agentProcessVO) {
        AgentProcess agentProcess = new AgentProcess();
        BeanUtils.copyProperties(agentProcessVO, agentProcess);
        updateById(agentProcess);
        return agentProcessVO;
    }

    @Override
    public AgentProcessVO getById(Long id) {
        AgentProcess agentProcess = baseMapper.selectById(id);
        AgentProcessVO agentProcessVO = new AgentProcessVO();
        BeanUtils.copyProperties(agentProcess, agentProcessVO);
        return agentProcessVO;
    }

    @Override
    public AgentProcessVO getByUniqueId(String uniqueId) {
        QueryWrapper<AgentProcess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("unique_id", uniqueId);
        AgentProcess agentProcess = baseMapper.selectOne(queryWrapper);
        AgentProcessVO agentProcessVO = new AgentProcessVO();
        BeanUtils.copyProperties(agentProcess, agentProcessVO);
        return agentProcessVO;
    }

    @Override
    public List<AgentProcessVO> list(AgentProcess agentProcess) {
        QueryWrapper<AgentProcess> queryWrapper = new QueryWrapper<>(agentProcess);
        List<AgentProcess> agentProcessList = baseMapper.selectList(queryWrapper);
        return agentProcessList.stream()
                .map(process -> {
                    AgentProcessVO agentProcessVO = new AgentProcessVO();
                    BeanUtils.copyProperties(process, agentProcessVO);
                    return agentProcessVO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public AgentProcessVO publish(Long id) {
        AgentProcess agentProcess = baseMapper.selectById(id);
        agentProcess.setStop(false);
        updateById(agentProcess);
        AgentProcessVO agentProcessVO = new AgentProcessVO();
        BeanUtils.copyProperties(agentProcess, agentProcessVO);
        return agentProcessVO;
    }

    @Override
    public AgentProcessVO stop(Long id) {
        AgentProcess agentProcess = baseMapper.selectById(id);
        agentProcess.setStop(true);
        updateById(agentProcess);
        AgentProcessVO agentProcessVO = new AgentProcessVO();
        BeanUtils.copyProperties(agentProcess, agentProcessVO);
        return agentProcessVO;
    }

    @Override
    public boolean delete(Long id) {
        return removeById(id);
    }
}