package cc.flyflow.biz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 打印数据
 * @author Huijun Zhao
 * @description
 * @date 2023-10-12 09:47
 */
@ApiModel(description = "打印数据")
@Data
public class PrintDataResultVO {
    /**
     * 自定义模板
     */
    @ApiModelProperty("自定义模板")
    private Boolean printTemplateEnable;
    /**
     * 自定义模板内容
     */
    @ApiModelProperty("自定义模板内容")
    private String printTemplateContent;

    /**
     * 流程结果
     */
    @ApiModelProperty("流程结果")
    private Integer processInstanceResult;
    /**
     * 流程状态
     */
    @ApiModelProperty("流程状态")
    private Integer processStatus;
    /**
     * 流程实例id
     */
    @ApiModelProperty("流程实例id")
    private String processInstanceId;
    /**
     * 流程业务key
     */
    @ApiModelProperty("流程业务key")
    private String processInstanceBizKey;
    /**
     * 流程业务编码
     */
    @ApiModelProperty("流程业务编码")
    private String processInstanceBizCode;
    /**
     * 流程状态显示
     */
    @ApiModelProperty("流程状态显示")
    private String processStatusShow;
    /**
     * 发起人姓名
     */
    @ApiModelProperty("发起人姓名")
    private String starterName;
    /**
     * 发起人部门名称
     */
    @ApiModelProperty("发起人部门名称")
    private String starterDeptName;
    /**
     * 流程名称
     */
    @ApiModelProperty("流程名称")
    private String processName;
    /**
     * 发起时间
     */
    @ApiModelProperty("发起时间")
    private String startTime;
    /**
     * 表单集合
     */
    @ApiModelProperty("表单集合")
    private List<Form> formList;
    /**
     * 审批集合
     */
    @ApiModelProperty("审批集合")
    private List<Approve> approveList;

    /**
     * 表单
     */
    @ApiModel(description = "表单")
    @Data
    public static class Form{
        /**
         * 表单名称
         */
        @ApiModelProperty("表单名称")
        private String formName;
        /**
         * 表单类型
         */
        @ApiModelProperty("表单类型")
        private String formType;
        /**
         * 表单值
         */
        @ApiModelProperty("表单值")
        private Object formValue;
        /**
         * 表单值显示
         */
        @ApiModelProperty("表单值显示")
        private String formValueShow;
    }

    /**
     * 审批对象
     */
    @ApiModel(description = "审批对象")
    @Data
    public static class Approve{
        /**
         * 节点名称
         */
        @ApiModelProperty("节点名称")
        private String nodeName;
        /**
         * 操作类型
         */
        @ApiModelProperty("操作类型")
        private String operType;
        /**
         * 评论
         */
        @ApiModelProperty("评论")
        private String desc;
    }

}
