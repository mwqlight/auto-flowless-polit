package cc.flyflow.common.dto.flow;

import cc.flyflow.common.constants.FormTypeEnum;
import cc.flyflow.common.dto.flow.node.EmptyNode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 表单
 */
@ApiModel(description = "表单")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormItemVO {
    /**
     * 表单id
     */
    @ApiModelProperty("表单id")
    private String id;
    /**
     * 表单权限  R E H
     */
    @ApiModelProperty("表单权限  R E H")
    private String perm;
    /**
     *  标题图标
     */
    @ApiModelProperty("标题图标")
    private String icon;
    /**
     * 表单名称
     */
    @ApiModelProperty("表单名称")
    private String name;
    /**
     * 表单类型 {@link FormTypeEnum}
     */
    @ApiModelProperty("表单类型 {@link constants.cc.flyflow.common.FormTypeEnum}")
    private String type;

    /**
     * 是否必须
     */
    @ApiModelProperty("是否必须")
    private Boolean required;

    /**
     * 表单类型名称
     */
    @ApiModelProperty("表单类型名称")
    private String typeName;
    /**
     * 显示提示语
     */
    @ApiModelProperty("显示提示语")
    private String placeholder;
    /**
     * 表单属性
     */
    @ApiModelProperty("表单属性")
    private Props props;



    /**
     * 动态表单配置
     */
    @ApiModel(description = "动态表单配置")
    @Data
    public static class DynamicFormConfig{

        /**
         * 表单配置
         */
        @ApiModelProperty("表单配置")
        private List<FormConfig> list;
        /**
         * 节点条件
         */
        @ApiModelProperty("节点条件")
        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,property = "type",defaultImpl = EmptyNode.class)
        private EmptyNode condition;

        /**
         * 表单配置
         */
        @ApiModel(description = "表单配置")
        @Data
        public static class FormConfig {
            /**
             * 配置值
             */
            @ApiModelProperty("配置值")
            private Object value;

            /**
             * 配置条件
             */
            @ApiModelProperty("配置条件")
            private String contentConfig;
        }


    }

    /**
     * 表单属性
     */
    @ApiModel(description = "表单属性")
    @Data
    public static class Props{
        /**
         * 是否是私有变量值 如果true 则变量值存储在任务私有变量里
         */
        @ApiModelProperty("是否是私有变量值 如果true 则变量值存储在任务私有变量里")
        private Boolean privateVal;
        /**
         * 远程配置
         */
        @ApiModelProperty("远程配置")
        private HttpSetting remoteConfig;

        /**
         * 1手动 2远程
         */
        @ApiModelProperty("1手动 2远程")
        private Integer dataFrom;
        /**
         * 统计总数
         */
        @ApiModelProperty("统计总数")
        private Boolean sum;

        /**
         * 表单值
         */
        @ApiModelProperty("表单值")
        private Object value;
        /**
         * 数据是否是空的
         */
        @ApiModelProperty("数据是否是空的")
        private Boolean isBlank=false;
        /**
         * 表单选项
         */
        @ApiModelProperty("表单选项")
        private Object options;
        /**
         * 是否可以选择自己
         */
        @ApiModelProperty("是否可以选择自己")
        private Boolean self;
        /**
         * 多选单选
         */
        @ApiModelProperty("多选单选")
        private Boolean multi;
        /**
         * 是否默认值是发起人
         */
        @ApiModelProperty("是否默认值是发起人")
        private Boolean defaultRoot;
        /**
         * 旧的表单
         */
        @ApiModelProperty("旧的表单")
        private Object oriForm;
        /**
         * 最小长度
         */
        @ApiModelProperty("最小长度")
        private Integer minLength;
        /**
         * 最大长度
         */
        @ApiModelProperty("最大长度")
        private Integer maxLength;
        /**
         * 小数位数
         */
        @ApiModelProperty("小数位数")
        private Integer radixNum;
        /**
         * 最大尺寸/数量等
         */
        @ApiModelProperty("最大尺寸/数量等")
        private Integer maxSize;
        /**
         * 是否显示中文大写
         */
        @ApiModelProperty("是否显示中文大写")
        private Boolean showChinese;
        /**
         * 正则表达式
         */
        @ApiModelProperty("正则表达式")
        private String regex;
        /**
         * 正则表达式描述
         */
        @ApiModelProperty("正则表达式描述")
        private String regexDesc;
        /**
         * 单位
         */
        @ApiModelProperty("单位")
        private String unit;
        /**
         * 是否可以选半个  比如评分表单
         */
        @ApiModelProperty("是否可以选半个  比如评分表单")
        private Boolean halfSelect;
        /**
         * 表达式集合  公式表达中使用
         */
        @ApiModelProperty("表达式集合  公式表达中使用")
        private List expList;

        /**
         * 是否使用上次的内容
         * 签名表单
         */
        @ApiModelProperty("是否使用上次的内容 签名表单")
        private Boolean lastContent;
        /**
         * 是否只读
         */
        @ApiModelProperty("是否只读")
        private Boolean readonly;
        /**
         * 文件后缀集合
         */
        @ApiModelProperty("文件后缀集合")
        private Object suffixArray;
        /**
         * 最大值
         */
        @ApiModelProperty("最大值")
        private Object max;
        /**
         * 最小值
         */
        @ApiModelProperty("最小值")
        private Object min;
    }

}
