package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class ProcessDto {

    /**
     * 唯一性id
     */
    @ApiModelProperty(value = "唯一性id", position = 1)
    private String uniqueId;
    /**
     * 设置项
     */
    @ApiModelProperty(value = "设置项", position = 2)
    private String settings;
}
