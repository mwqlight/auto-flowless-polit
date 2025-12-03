package cc.flyflow.biz.service.impl;

import cc.flyflow.biz.entity.AgentProcessGroup;
import cc.flyflow.biz.mapper.AgentProcessGroupMapper;
import cc.flyflow.biz.service.IAgentProcessGroupService;
import cc.flyflow.common.dto.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 智能体流程组表 服务实现类
 * </p>
 *
 * @author xiaoge
 * @since 2023-05-25
 */
@Slf4j
@Service
public class AgentProcessGroupServiceImpl extends ServiceImpl<AgentProcessGroupMapper, AgentProcessGroup> implements IAgentProcessGroupService {

    /**
     * 智能体流程组列表
     *
     * @return
     */
    @Override
    public R<List<AgentProcessGroup>> queryList() {
        List<AgentProcessGroup> agentProcessGroupList = this.lambdaQuery()
                .orderByAsc(AgentProcessGroup::getSort)
                .list();
        return R.success(agentProcessGroupList);
    }

    /**
     * 新增智能体流程分组
     *
     * @param agentProcessGroup 智能体流程分组数据
     * @return 添加结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R create(AgentProcessGroup agentProcessGroup) {
        // 设置排序值为当前最大排序值+1
        Integer maxSort = this.lambdaQuery()
                .select(AgentProcessGroup::getSort)
                .orderByDesc(AgentProcessGroup::getSort)
                .last("LIMIT 1")
                .oneOpt()
                .map(AgentProcessGroup::getSort)
                .orElse(0);
        agentProcessGroup.setSort(maxSort + 1);

        boolean saveResult = this.save(agentProcessGroup);
        if (saveResult) {
            return R.success();
        } else {
            return R.fail("新增智能体流程分组失败");
        }
    }

    /**
     * 上移排序
     *
     * @param agentProcessGroup 智能体流程分组数据
     * @return 添加结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R topSort(AgentProcessGroup agentProcessGroup) {
        // 获取当前智能体流程分组的排序值
        Integer currentSort = agentProcessGroup.getSort();

        // 获取排序值小于当前排序值的最大排序值的智能体流程分组
        AgentProcessGroup prevAgentProcessGroup = this.lambdaQuery()
                .select(AgentProcessGroup::getId, AgentProcessGroup::getSort)
                .lt(AgentProcessGroup::getSort, currentSort)
                .orderByDesc(AgentProcessGroup::getSort)
                .last("LIMIT 1")
                .one();

        if (prevAgentProcessGroup == null) {
            // 已经是第一个，不需要移动
            return R.success();
        }

        // 交换当前智能体流程分组和前一个智能体流程分组的排序值
        Integer prevSort = prevAgentProcessGroup.getSort();

        this.lambdaUpdate()
                .set(AgentProcessGroup::getSort, prevSort)
                .eq(AgentProcessGroup::getId, agentProcessGroup.getId())
                .update();

        this.lambdaUpdate()
                .set(AgentProcessGroup::getSort, currentSort)
                .eq(AgentProcessGroup::getId, prevAgentProcessGroup.getId())
                .update();

        return R.success();
    }

    /**
     * 下移排序
     *
     * @param agentProcessGroup 智能体流程分组数据
     * @return 添加结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R bottomSort(AgentProcessGroup agentProcessGroup) {
        // 获取当前智能体流程分组的排序值
        Integer currentSort = agentProcessGroup.getSort();

        // 获取排序值大于当前排序值的最小排序值的智能体流程分组
        AgentProcessGroup nextAgentProcessGroup = this.lambdaQuery()
                .select(AgentProcessGroup::getId, AgentProcessGroup::getSort)
                .gt(AgentProcessGroup::getSort, currentSort)
                .orderByAsc(AgentProcessGroup::getSort)
                .last("LIMIT 1")
                .one();

        if (nextAgentProcessGroup == null) {
            // 已经是最后一个，不需要移动
            return R.success();
        }

        // 交换当前智能体流程分组和后一个智能体流程分组的排序值
        Integer nextSort = nextAgentProcessGroup.getSort();

        this.lambdaUpdate()
                .set(AgentProcessGroup::getSort, nextSort)
                .eq(AgentProcessGroup::getId, agentProcessGroup.getId())
                .update();

        this.lambdaUpdate()
                .set(AgentProcessGroup::getSort, currentSort)
                .eq(AgentProcessGroup::getId, nextAgentProcessGroup.getId())
                .update();

        return R.success();
    }

    /**
     * 修改智能体流程分组
     *
     * @param agentProcessGroup 智能体流程分组数据
     * @return 添加结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R edit(AgentProcessGroup agentProcessGroup) {
        boolean updateResult = this.updateById(agentProcessGroup);
        if (updateResult) {
            return R.success();
        } else {
            return R.fail("修改智能体流程分组失败");
        }
    }

    /**
     * 删除智能体流程分组
     * @param id 智能体流程分组ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R delete(long id) {
        boolean removeResult = this.removeById(id);
        if (removeResult) {
            // 重新排序剩余的智能体流程分组
            List<AgentProcessGroup> agentProcessGroupList = this.lambdaQuery()
                    .orderByAsc(AgentProcessGroup::getSort)
                    .list();

            for (int i = 0; i < agentProcessGroupList.size(); i++) {
                AgentProcessGroup agentProcessGroup = agentProcessGroupList.get(i);
                if (agentProcessGroup.getSort() != i + 1) {
                    this.lambdaUpdate()
                            .set(AgentProcessGroup::getSort, i + 1)
                            .eq(AgentProcessGroup::getId, agentProcessGroup.getId())
                            .update();
                }
            }

            return R.success();
        } else {
            return R.fail("删除智能体流程分组失败");
        }
    }
}
