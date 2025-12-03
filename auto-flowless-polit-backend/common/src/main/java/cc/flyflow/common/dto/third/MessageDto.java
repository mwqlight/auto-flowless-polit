package cc.flyflow.common.dto.third;

import cc.flyflow.common.constants.MessageTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 通知消息
 * </p>
 *
 * @author xiaoge
 * @since 2023-07-25
 */
@ApiModel(description = "<p> 通知消息 </p>")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto   implements Serializable {


    /**
     * 类型
     * {@link MessageTypeEnum}
     */
    @ApiModelProperty("类型 {@link MessageTypeEnum}")
    private String type;

    /**
     * 是否已读
     */
    @ApiModelProperty("是否已读")
    private Boolean readed;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String userId;

    /**
     * 唯一id
     */
    @ApiModelProperty("唯一id")
    private String bizUniqueId;

    /**
     * 消息参数
     */
    @ApiModelProperty("消息参数")
    private String param;

    /**
     * 消息内容
     */
    @ApiModelProperty("消息内容")
    private String content;

    /**
     * 消息头
     */
    @ApiModelProperty("消息头")
    private String title;
    /**
     * 流程id
     */
    @ApiModelProperty("流程id")
    private String flowId;
    /**
     *  流程实例id
     */
    @ApiModelProperty("流程实例id")
    private String processInstanceId;
    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private String tenantId;
    /**
     * 发起人id
     */
    private String startUserId;
    /**
     * 发起人姓名
     */
    private String startUserName;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 流程名称
     */
    private String flowName;

}
