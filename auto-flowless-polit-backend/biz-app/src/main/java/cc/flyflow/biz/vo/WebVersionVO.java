package cc.flyflow.biz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端版本对象
 * @author Huijun Zhao
 * @description
 * @date 2023-10-20 16:39
 */
@ApiModel(description = "前端版本对象")
@Data
public class WebVersionVO {

    @ApiModelProperty( value = "版本号",required = true,example = "V2.0.1")
    private String versionNo;

}
