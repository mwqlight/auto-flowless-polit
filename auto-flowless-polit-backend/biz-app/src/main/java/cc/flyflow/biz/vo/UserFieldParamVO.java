package cc.flyflow.biz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 对象
 */
@ApiModel(description = "对象")
@Data
public class UserFieldParamVO {

    /**
     * 字段名
     */
    @ApiModelProperty(value = "字段名", position = 1)
    private String name;


    /**
     * 字段类型
     */
    @ApiModelProperty(value = "字段类型", position = 2)
    private String type;

    /**
     * 是否必填
     */
    @ApiModelProperty(value = "是否必填", position = 3)
    private Boolean required;

    /**
     * 配置json字符串
     */
    @ApiModelProperty(value = "配置json字符串", position = 4)
    private String props;
    /**
     * 字段唯一key
     */
    @ApiModelProperty(value = "字段唯一key", position = 5)
    private String key;
}
