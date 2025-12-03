package cc.flyflow.common.dto;

import cc.flyflow.common.dto.flow.UploadValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 任务完成参数对象
 *
 */
@ApiModel(description = "任务完成参数对象")
@Data
public class TaskParamDto {

    /**
     * 实例id
     */
    @ApiModelProperty("实例id")
    private String processInstanceId;
    /**
     * 审批结果 同意还是拒绝
     */
    @ApiModelProperty("审批结果 同意还是拒绝")
    private Boolean approveResult;
    /**
     * 审批意见
     */
    @ApiModelProperty("审批意见")
    private String approveDesc;
    /**
     * 多实例id集合
     */
    @ApiModelProperty("多实例id集合")
    private List<String> processInstanceIdList;
    /**
     * 签署顺序
     */
    @ApiModelProperty("签署顺序")
    private Integer signOrder;
    /**
     * 审批上传的文件
     */
    @ApiModelProperty("审批上传的文件")
    private List<UploadValue> approveFileList;
    /**
     * 审批上传的图片
     */
    @ApiModelProperty("审批上传的图片")
    private List<UploadValue> approveImageList;

    /**
     * 节点id
     */
    @ApiModelProperty("节点id")
    private String nodeId;
    /**
     * 添加子流程发起人
     */
    @ApiModelProperty("添加子流程发起人")
    private Boolean appendChildProcessRootId;

    /**
     * 任务id
     */
    @ApiModelProperty("任务id")
    private String taskId;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String userId;
    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String userName;
    /**
     * 模板用户id
     */
    @ApiModelProperty("模板用户id")
    private String targetUserId;
    /**
     * 模板用户id集合
     */
    @ApiModelProperty("模板用户id集合")
    private List<String> targetUserIdList;
    /**
     * 目标执行id集合
     */
    @ApiModelProperty("目标执行id集合")
    private List<String> targetExecutionIdList;
    /**
     * 目标用户名字集合
     */
    @ApiModelProperty("目标用户名字集合")
    private List<String> targetUserNameList;
    /**
     * 目标用户名称
     */
    @ApiModelProperty("目标用户名称")
    private String targetUserName;
    /**
     * 参数
     */
    @ApiModelProperty("参数")
    private Map<String,Object> paramMap;



    /**
     * 目标节点
     */
    @ApiModelProperty("目标节点")
    private String targetNodeId;
    /**
     * 节点id集合
     */
    @ApiModelProperty("节点id集合")
    private List<String> nodeIdList;
    /**
     * 任务id集合
     */
    @ApiModelProperty("任务id集合")
    private List<String> taskIdList;
    /**
     * 执行id
     */
    @ApiModelProperty("执行id")
    private String executionId;
    /**
     * 签名url
     */
    @ApiModelProperty("签名url")
    private String signUrl;
    /**
     * 原始文件地址
     */
    @ApiModelProperty("原始文件地址")
    private String oriFileUrl;
    /**
     * 签章图片的集合
     */
    @ApiModelProperty("签章图片的集合")
    private List<SignContractImg> signContractImgList;


    /**
     * 签署合同签章图片信息
     */
    @ApiModel(description = "签署合同签章图片信息")
    @Data
    public static class SignContractImg{
        /**
         * 页面的宽度
         */
        @ApiModelProperty("页面的宽度")
        private Float pageWidth;
        /**
         * 页面高度
         */
        @ApiModelProperty("页面高度")
        private Float pageHeight;

        /**
         * 签署合同的url
         */
        @ApiModelProperty("签署合同的url")
        private String url;
        /**
         * 页面x坐标
         */
        @ApiModelProperty("页面x坐标")
        private Float fx;
        /**
         * 页面y坐标
         */
        @ApiModelProperty("页面y坐标")
        private Float fy;
        /**
         * 图片宽度
         */
        @ApiModelProperty("图片宽度")
        private Float width;
        /**
         * 图片高度
         */
        @ApiModelProperty("图片高度")
        private Float height;
        /**
         * 页码
         */
        @ApiModelProperty("页码")
        private Integer page;

    }

}
