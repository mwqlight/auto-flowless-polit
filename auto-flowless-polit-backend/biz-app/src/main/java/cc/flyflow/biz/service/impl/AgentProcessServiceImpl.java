package cc.flyflow.biz.service.impl;

import cc.flyflow.biz.entity.AgentProcess;
import cc.flyflow.biz.entity.AgentProcessMain;
import cc.flyflow.biz.entity.AgentProcessStarter;
import cc.flyflow.biz.mapper.AgentProcessMapper;
import cc.flyflow.biz.service.IAgentProcessMainService;
import cc.flyflow.biz.service.IAgentProcessService;
import cc.flyflow.biz.service.IAgentProcessStarterService;
import cc.flyflow.biz.service.IAgentProcessFormService;
import cc.flyflow.biz.service.IClearService;
import cc.flyflow.biz.utils.CoreHttpUtil;
import cc.flyflow.biz.vo.AgentProcessVO;
import cc.flyflow.common.dto.R;
import cc.flyflow.common.dto.flow.FormItemVO;
import cc.flyflow.common.dto.flow.NodeUser;
import cc.flyflow.common.utils.JsonUtil;
import cc.flyflow.common.utils.NodeUtil;
import cc.flyflow.common.utils.TenantUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 智能体流程表 服务实现类
 * </p>
 *
 * @author xiaoge
 * @since 2023-05-25
 */
@Slf4j
@Service
public class AgentProcessServiceImpl extends ServiceImpl<AgentProcessMapper, AgentProcess> implements IAgentProcessService, IClearService {

    @Resource
    private IAgentProcessStarterService agentProcessStarterService;

    @Resource
    @Lazy
    private IAgentProcessFormService agentProcessFormService;

    @Resource
    public IAgentProcessMainService agentProcessMainService;

    /**
     * 清理流程数据
     *
     * @param uniqueId      流程唯一id
     * @param flowIdList    process表 流程id集合
     * @param processIdList process表的注解id集合
     * @param tenantId 租户id
     */
    @Override
    public void clearProcess(String uniqueId, List<String> flowIdList, List<Long> processIdList, String tenantId) {
        // 实现清理逻辑
    }

    @PostConstruct
    public void init() {

    }

    /**
     * 根据智能体流程唯一标识查询流程列表
     *
     * @param uniqueId 智能体流程唯一标识
     * @return
     */
    @Override
    public R<AgentProcessVO> getListByUniqueId(String uniqueId) {
        List<AgentProcess> agentProcessList = 
                this.lambdaQuery()
                        .eq(AgentProcess::getUniqueId, uniqueId)
                        .eq(AgentProcess::getTenantId, TenantUtil.get())
                        .eq(AgentProcess::getHidden, false)
                        .orderByDesc(AgentProcess::getCreateTime).list();

        List<AgentProcessVO> agentProcessVOS = BeanUtil.copyToList(agentProcessList, AgentProcessVO.class);

        return R.success(agentProcessVOS);
    }

    /**
     * 设置主智能体流程
     *
     * @param flowId 智能体流程ID
     * @return
     */
    @Override
    public R setMainProcess(String flowId) {
        AgentProcess byFlowId = this.getByFlowId(flowId);
        //先停用了
        this.lambdaUpdate()
                .set(AgentProcess::getStop, true)
                .eq(AgentProcess::getUniqueId, byFlowId.getUniqueId())
                .eq(AgentProcess::getTenantId, TenantUtil.get())
                .update(new AgentProcess());
        //设置为主智能体流程
        byFlowId.setStop(false);
        this.updateById(byFlowId);

        //修改主智能体流程
        AgentProcessMain agentProcessMain = BeanUtil.copyProperties(byFlowId, AgentProcessMain.class, "id");
        agentProcessMainService.lambdaUpdate()
                .eq(AgentProcessMain::getUniqueId, byFlowId.getUniqueId())
                .eq(AgentProcessMain::getTenantId, TenantUtil.get())
                .update(agentProcessMain);

        return R.success();
    }

    /**
     * 获取详细数据
     *
     * @param flowId 智能体流程ID
     * @return
     */
    @Override
    public R<AgentProcessVO> getDetail(String flowId) {
        AgentProcessVO agentProcessVO = this.getAgentProcessVO(flowId);
        if (agentProcessVO == null) {
            return R.fail("智能体流程不存在");
        }
        return R.success(agentProcessVO);
    }

    private AgentProcessVO getAgentProcessVO(String flowId) {
        AgentProcess agentProcess = getByFlowId(flowId);
        if (agentProcess == null) {
            return null;
        }
        String formItems = agentProcess.getFormItems();

        List<FormItemVO> formItemVOList = JsonUtil.parseArray(formItems, FormItemVO.class);

        agentProcess.setFormItems(JsonUtil.toJSONString(formItemVOList));

        AgentProcessVO agentProcessVO = BeanUtil.copyProperties(agentProcess, AgentProcessVO.class);

        //发起人范围
        List<AgentProcessStarter> agentProcessStarterList = agentProcessStarterService
                .lambdaQuery()
                .eq(AgentProcessStarter::getProcessId, agentProcess.getId())
                .eq(AgentProcessStarter::getTenantId, TenantUtil.get())
                .list();
        List<NodeUser> rangeList = new ArrayList<>();
        for (AgentProcessStarter agentProcessStarter : agentProcessStarterList) {
            NodeUser nodeUser = JsonUtil.parseObject(agentProcessStarter.getData(), NodeUser.class);
            rangeList.add(nodeUser);
        }
        agentProcessVO.setRangeList(rangeList);

        return agentProcessVO;
    }

    @Override
    public AgentProcess getByFlowId(String flowId) {
        return this.lambdaQuery()
                .eq(AgentProcess::getFlowId, flowId)
                .one();
    }

    /**
     * 查询智能体流程数据 包括已经被删除的
     *
     * @param flowId 智能体流程ID
     * @return
     */
    @Override
    public AgentProcess getByFlowIdContainDeleted(String flowId) {
        return this.baseMapper.selectByFlowId(flowId);
    }

    @Override
    public AgentProcess getByUniqueId(String uniqueId) {
        return this.lambdaQuery()
                .eq(AgentProcess::getUniqueId, uniqueId)
                .eq(AgentProcess::getHidden, false)
                .eq(AgentProcess::getStop, false)
                .one();
    }

    @Override
    public void updateByFlowId(AgentProcess agentProcess) {
        this.lambdaUpdate()
                .eq(AgentProcess::getFlowId, agentProcess.getFlowId())
                .update(agentProcess);
    }

    @Override
    public void stop(String flowId) {
        this.lambdaUpdate().set(AgentProcess::getStop, true)
                .eq(AgentProcess::getFlowId, flowId)
                .update(new AgentProcess());
    }

    /**
     * 创建智能体流程
     *
     * @param agentProcessVO 智能体流程数据
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R create(AgentProcessVO agentProcessVO) {
        // 验证智能体流程数据
        if (agentProcessVO == null || StrUtil.isBlank(agentProcessVO.getName()) || StrUtil.isBlank(agentProcessVO.getUniqueId())) {
            return R.fail("智能体流程数据不完整");
        }

        // 检查智能体流程名称是否已存在
        boolean nameExists = this.lambdaQuery()
                .eq(AgentProcess::getName, agentProcessVO.getName())
                .eq(AgentProcess::getTenantId, TenantUtil.get())
                .eq(AgentProcess::getHidden, false)
                .exists();
        if (nameExists) {
            return R.fail("智能体流程名称已存在");
        }

        // 检查智能体流程唯一标识是否已存在
        boolean uniqueIdExists = this.lambdaQuery()
                .eq(AgentProcess::getUniqueId, agentProcessVO.getUniqueId())
                .eq(AgentProcess::getTenantId, TenantUtil.get())
                .eq(AgentProcess::getHidden, false)
                .exists();
        if (uniqueIdExists) {
            return R.fail("智能体流程唯一标识已存在");
        }

        // 创建智能体流程实体
        AgentProcess agentProcess = BeanUtil.copyProperties(agentProcessVO, AgentProcess.class);
        agentProcess.setTenantId(TenantUtil.get());
        agentProcess.setCreateBy((Long) StpUtil.getLoginId());
        agentProcess.setHidden(false);
        agentProcess.setStop(false);

        // 保存智能体流程
        boolean saveResult = this.save(agentProcess);
        if (!saveResult) {
            return R.fail("创建智能体流程失败");
        }

        // 保存智能体流程发起人范围
        if (CollUtil.isNotEmpty(agentProcessVO.getRangeList())) {
            List<AgentProcessStarter> agentProcessStarterList = new ArrayList<>();
            for (NodeUser nodeUser : agentProcessVO.getRangeList()) {
                AgentProcessStarter agentProcessStarter = new AgentProcessStarter();
                agentProcessStarter.setProcessId(agentProcess.getId());
                agentProcessStarter.setData(JsonUtil.toJSONString(nodeUser));
                agentProcessStarter.setTenantId(TenantUtil.get());
                agentProcessStarter.setCreateBy((Long) StpUtil.getLoginId());
                agentProcessStarterList.add(agentProcessStarter);
            }
            agentProcessStarterService.saveBatch(agentProcessStarterList);
        }

        // 保存智能体流程主流程
        AgentProcessMain agentProcessMain = BeanUtil.copyProperties(agentProcess, AgentProcessMain.class, "id");
        agentProcessMainService.save(agentProcessMain);

        return R.success();
    }

    /**
     * 编辑智能体流程
     *
     * @param flowId 智能体流程ID
     * @param type       类型 stop using delete
     * @param groupId 智能体流程组id
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R update(String flowId, String type, Long groupId) {
        // 验证参数
        if (StrUtil.isBlank(flowId) || StrUtil.isBlank(type)) {
            return R.fail("参数不完整");
        }

        // 获取智能体流程
        AgentProcess agentProcess = this.getByFlowId(flowId);
        if (agentProcess == null) {
            return R.fail("智能体流程不存在");
        }

        // 根据类型执行不同的操作
        switch (type) {
            case "stop":
                // 停用智能体流程
                agentProcess.setStop(true);
                this.updateById(agentProcess);
                break;
            case "using":
                // 启用智能体流程
                agentProcess.setStop(false);
                this.updateById(agentProcess);

                // 更新主智能体流程
                AgentProcessMain agentProcessMain = BeanUtil.copyProperties(agentProcess, AgentProcessMain.class, "id");
                agentProcessMainService.lambdaUpdate()
                        .eq(AgentProcessMain::getUniqueId, agentProcess.getUniqueId())
                        .eq(AgentProcessMain::getTenantId, TenantUtil.get())
                        .update(agentProcessMain);
                break;
            case "delete":
                // 删除智能体流程
                agentProcess.setHidden(true);
                this.updateById(agentProcess);
                break;
            case "group":
                // 移动智能体流程到指定组
                agentProcess.setGroupId(groupId);
                this.updateById(agentProcess);
                break;
            default:
                return R.fail("操作类型不支持");
        }

        return R.success();
    }

    /**
     * 查询所有关联的智能体流程id
     * @param flowIdList 智能体流程ID列表
     * @return
     */
    @Override
    public R<List<String>> getAllRelatedFlowId(List<String> flowIdList) {
        // 验证参数
        if (CollUtil.isEmpty(flowIdList)) {
            return R.success(new ArrayList<>());
        }

        // 查询所有关联的智能体流程id
        List<String> relatedFlowIdList = new ArrayList<>();

        // 遍历每个智能体流程ID
        for (String flowId : flowIdList) {
            // 获取智能体流程
            AgentProcess agentProcess = this.getByFlowId(flowId);
            if (agentProcess == null) {
                continue;
            }

            // 查询与该智能体流程相关的所有智能体流程ID
            List<AgentProcess> relatedAgentProcessList = this.lambdaQuery()
                    .eq(AgentProcess::getUniqueId, agentProcess.getUniqueId())
                    .eq(AgentProcess::getTenantId, TenantUtil.get())
                    .eq(AgentProcess::getHidden, false)
                    .list();

            // 将相关的智能体流程ID添加到结果列表中
            for (AgentProcess relatedAgentProcess : relatedAgentProcessList) {
                relatedFlowIdList.add(relatedAgentProcess.getFlowId());
            }
        }

        // 去重并返回结果
        Set<String> uniqueRelatedFlowIdSet = relatedFlowIdList.stream().collect(Collectors.toSet());
        List<String> uniqueRelatedFlowIdList = new ArrayList<>(uniqueRelatedFlowIdSet);

        return R.success(uniqueRelatedFlowIdList);
    }
}
