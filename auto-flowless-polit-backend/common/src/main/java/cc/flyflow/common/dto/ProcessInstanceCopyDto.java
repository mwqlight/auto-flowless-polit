package cc.flyflow.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel
@Data
public class ProcessInstanceCopyDto {



    /**
     * 当前节点时间
     */
    @ApiModelProperty("当前节点时间")
    @JsonFormat( timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date nodeTime;

    /**
     * 发起人
     */
    @ApiModelProperty("发起人")
    private String startUserId;

    /**
     * 流程id
     */
    @ApiModelProperty("流程id")
    private String flowId;

    /**
     * 实例id
     */
    @ApiModelProperty("实例id")
    private String processInstanceId;

    /**
     * 节点id
     */
    @ApiModelProperty("节点id")
    private String nodeId;


    /**
     * 节点 名称
     */
    @ApiModelProperty("节点 名称")
    private String nodeName;

    /**
     * 表单数据
     */
    @ApiModelProperty("表单数据")
    private String formData;


    /**
     *  抄送人
     */
    private List<String> userIdList;

    /**
     * 租户id
     */
    @ApiModelProperty("租户id")
    private String tenantId;
}
