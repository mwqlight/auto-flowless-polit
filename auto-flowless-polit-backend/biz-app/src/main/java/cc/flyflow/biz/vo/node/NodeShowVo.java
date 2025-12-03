package cc.flyflow.biz.vo.node;

import cc.flyflow.biz.constants.ProcessInstanceNodeRecordStatusEnum;
import cc.flyflow.biz.vo.ProcessFormatNodeApproveDescVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 流程节点显示对象
 */
@ApiModel(description = "流程节点显示对象")
@Data
public class NodeShowVo {
    /**
     * 节点id
     */
    @ApiModelProperty("节点id")
    private String id;

    /**
     * 用户列表
     */
    @ApiModelProperty("用户列表")
    private List<NodeFormatUserVo> userVoList;

    /**
     * 显示
     */
    @ApiModelProperty("显示")
    private String placeholder;
    /**
     * 状态 1进行中2已完成3已撤销0未开始 {@link ProcessInstanceNodeRecordStatusEnum}
     */
    @ApiModelProperty("状态 1进行中2已完成3已撤销0未开始 {@link ProcessInstanceNodeRecordStatusEnum}")
    private Integer status;


    /**
     * 节点名称
     */
    @ApiModelProperty("节点名称")
    private String name;
    /**
     * 节点类型
     */
    @ApiModelProperty("节点类型")
    private Integer type;
    /**
     * 发起人选择用户
     */
    @ApiModelProperty("发起人选择用户")
    private Boolean selectUser;


    /**
     * 是否多选
     */
    @ApiModelProperty("是否多选")
    private Boolean multiple;
    /**
     * 子级列表
     */
    @ApiModelProperty("子级列表")
    private List<NodeShowVo> children;
    /**
     * 分支列表
     */
    @ApiModelProperty("分支列表")
    private List<NodeShowVo> branch;
    /**
     * 显示时间
     */
    @ApiModelProperty("显示时间")
    private String showTimeStr;

    /**
     * 评论列表
     */
    @ApiModelProperty("评论列表")
    private List<ProcessFormatNodeApproveDescVo> approveDescList;
    /**
     * 子流程列表
     */
    @ApiModelProperty("子流程列表")
    private List<SubProcessShowVo> subProcessShowVoList;

    /**
     * 子流程显示对象
     */
    @ApiModel(description = "子流程显示对象")
    @Data
    public static class SubProcessShowVo {
        /**
         * 流程实例id
         */
        @ApiModelProperty("流程实例id")
        private String processInstanceId;
        /**
         * 发起人id
         */
        @ApiModelProperty("发起人id")
        private String startUserId;
        /**
         * 发起人姓名
         */
        @ApiModelProperty("发起人姓名")
        private String startUserName;
        /**
         * 流程名称
         */
        @ApiModelProperty("流程名称")
        private String processName;
        /**
         * 流程状态
         */
        @ApiModelProperty("流程状态")
        private Integer processStatus;
    }
}
