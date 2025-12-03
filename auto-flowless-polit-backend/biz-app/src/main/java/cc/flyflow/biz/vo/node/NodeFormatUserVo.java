package cc.flyflow.biz.vo.node;

import cc.flyflow.biz.constants.NodeFormatUserVoStatusEnum;
import cc.flyflow.common.constants.TaskTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 节点用户
 */
@ApiModel(description = "节点用户")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeFormatUserVo {

    /**
     * 用户od
     */
    @ApiModelProperty(value = "用户od", position = 1)
    private String id;
    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称", position = 2)
    private String name;

    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间", position = 3)
    private Date showTime;
    /**
     * 操作时间格式化字符串显示
     */
    @ApiModelProperty(value = "操作时间格式化字符串显示", position = 4)
    private String showTimeStr;
    /**
     * 签署合同的url
     */
    @ApiModelProperty(value = "签署合同的url", position = 5)
    private String signUrl;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", position = 6)
    private String avatar;
    /**
     * 签署合同结果
     */
    @ApiModelProperty(value = "签署合同结果", position = 7)
    private Boolean signContractResult;

    /**
     * 操作类型 {@link TaskTypeEnum}
     */
    @ApiModelProperty(value = "操作类型 {@link constants.cc.flyflow.common.TaskTypeEnum}", position = 8)
    private String operType;
    /**
     * 状态 1进行中2已完成 {@link NodeFormatUserVoStatusEnum}
     */
    @ApiModelProperty(value = "状态 1进行中2已完成 {@link constants.cc.flyflow.biz.NodeFormatUserVoStatusEnum}", position = 9)
    private Integer status = 0;

}
