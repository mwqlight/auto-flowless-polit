package cc.flyflow.biz.vo;

import cc.flyflow.biz.entity.ProcessInstanceRecord;
import cn.hutool.core.lang.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Huijun Zhao
 * @description
 * @date 2023-07-28 15:23
 */
@ApiModel(description = "")
@Data
public class ProcessInstanceRecordVO extends ProcessInstanceRecord {

    /**
     *  表单值显示
     */
    @ApiModelProperty("表单值显示")
    private List<Dict> formValueShowList;

    /**
     * 发起人名字
     */
    @ApiModelProperty("发起人名字")
    private String rootUserName;
    /**
     * 发起人头像
     */
    @ApiModelProperty("发起人头像")
    private String rootUserAvatarUrl;
    /**
     * 是否允许撤销
     */
    @ApiModelProperty("是否允许撤销")
    private Boolean cancelEnable;
    /**
     * 正在处理的人员显示
     */
    @ApiModelProperty("正在处理的人员显示")
    private String taskAssignShow;
    /**
     * 发起时间显示
     */
    @ApiModelProperty("发起时间显示")
    private String startTimeShow;
    /**
     * 流程唯一id
     */
    @ApiModelProperty("流程唯一id")
    private String flowUniqueId;
}
