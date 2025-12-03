package cc.flyflow.biz.vo;

import cc.flyflow.common.dto.flow.FormItemVO;
import cc.flyflow.common.dto.flow.NodeUser;
import lombok.Data;

import java.util.List;

/**
 * 智能体流程VO
 *
 * @author xiaoge
 * @since 2023-05-25
 */
@Data
public class AgentProcessVO {

    /**
     * 流程ID
     */
    private String flowId;

    /**
     * 流程唯一标识
     */
    private String uniqueId;

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程描述
     */
    private String description;

    /**
     * 流程数据
     */
    private String data;

    /**
     * 表单数据
     */
    private List<FormItemVO> formItems;

    /**
     * 流程组ID
     */
    private Long groupId;

    /**
     * 是否停用
     */
    private Boolean stop;

    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 发起人范围
     */
    private List<NodeUser> rangeList;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;
}
