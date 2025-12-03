package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 消息通知对象
 * @author Huijun Zhao
 * @description
 * @date 2023-09-14 17:01
 */
@ApiModel(description = "消息通知对象")
@Data
public class NotifyMessageDto {
    /**
     * 流程id
     */
    @ApiModelProperty(value = "流程id", position = 1)
    private String flowId;
    /**
     * 流程实例id
     */
    @ApiModelProperty(value = "流程实例id", position = 2)
    private String processInstanceId;
    /**
     * 消息通知id
     */
    @ApiModelProperty(value = "消息通知id", position = 3)
    private String messageNotifyId;

}
