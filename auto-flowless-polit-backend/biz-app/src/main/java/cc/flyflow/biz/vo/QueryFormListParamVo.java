package cc.flyflow.biz.vo;

import cc.flyflow.common.dto.flow.FormItemVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 查询对象
 */
@ApiModel(description = "查询对象")
@Data
public class QueryFormListParamVo {
    /**
     * 流程id
     */
    @ApiModelProperty(value = "流程id", position = 1)
    private String flowId;
    /**
     * 参数集合
     */
    @ApiModelProperty(value = "参数集合", position = 2)
    private Map<String,Object> paramMap;


    /**
     * 实例id
     */
    @ApiModelProperty(value = "实例id", position = 3)
    private String processInstanceId;

    /**
     * 任务id
     */
    @ApiModelProperty(value = "任务id", position = 4)
    private String taskId;
    /**
     * 抄送id
     */
    @ApiModelProperty(value = "抄送id", position = 5)
    private Long ccId;
    /**
     * 表单唯一id
     */
    @ApiModelProperty(value = "表单唯一id", position = 6)
    private String formUniqueId;
    /**
     * 节点id
     */
    @ApiModelProperty(value = "节点id", position = 7)
    private String nodeId;
    /**
     * 表单列表
     */
    @ApiModelProperty(value = "表单列表", position = 8)
    private List<FormItemVO> formItemVOList;
    /**
     * 来源  现在有值就是start 表示来自发起
     */
    @ApiModelProperty(value = "来源  现在有值就是start 表示来自发起", position = 9)
    private String from;
}
