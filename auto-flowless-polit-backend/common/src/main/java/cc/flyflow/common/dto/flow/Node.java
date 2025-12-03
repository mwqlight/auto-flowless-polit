package cc.flyflow.common.dto.flow;

import cc.flyflow.common.constants.NodeTypeEnum;
import cc.flyflow.common.config.NodeJacksonTypeIdResolver;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 节点对象
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM,property = "type",defaultImpl = Node.class
        ,visible = true)
@JsonTypeIdResolver(NodeJacksonTypeIdResolver.class)
@ApiModel(description = "节点对象")
@Data
public class Node implements Cloneable, Serializable {
    /**
     * 节点唯一id
     */
    @ApiModelProperty("节点唯一id")
    private String id;


    /**
     * 执行id
     */
    @ApiModelProperty("执行id")
    private String executionId;

    /**
     * 临时id
     */
    @ApiModelProperty("临时id")
    private String tempId;
    /**
     * 上级id
     */
    @ApiModelProperty("上级id")
    private String parentId;
    /**
     * 头部id 用户处理创建流程时用
     */
    @ApiModelProperty("头部id 用户处理创建流程时用")
    private String headId;
    /**
     * 尾部id 用户处理创建流程时用
     */
    @ApiModelProperty("尾部id 用户处理创建流程时用")
    private String tailId;
    /**
     * 提示内容
     */
    @ApiModelProperty("提示内容")
    private String placeHolder;

    /**
     * 节点类型 {@link NodeTypeEnum}
     */
    @ApiModelProperty("节点类型 {@link NodeTypeEnum}")
    private Integer type;

    /**
     * 节点名称
     */
    @ApiModelProperty("节点名称")
    private String nodeName;
    /**
     * 是否错误
     */
    @ApiModelProperty("是否错误")
    private Boolean error;

    /**
     * 子节点
     */
    @ApiModelProperty("子节点")
    private Node childNode;












    /**
     * 在渲染流程节点显示是，如果是true表示被标记了 不能再次被标记，即使没有执行id
     */
    private boolean remarkedAtNodeShow = false;

}
