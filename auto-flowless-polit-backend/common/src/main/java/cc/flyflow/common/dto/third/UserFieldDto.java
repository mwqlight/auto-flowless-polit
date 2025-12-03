package cc.flyflow.common.dto.third;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户字段
 * </p>
 *
 * @author Vincent
 * @since 2023-07-06
 */
@ApiModel(description = "<p> 用户字段 </p>")
@Getter
@Setter
@Accessors(chain = true)
public class UserFieldDto  {


    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", position = 1)
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
     * 字段
     */
    @ApiModelProperty(value = "字段", position = 5)
    private String key;
}
