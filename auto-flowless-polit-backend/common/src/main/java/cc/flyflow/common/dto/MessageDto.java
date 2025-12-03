package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Huijun Zhao
 * @description
 * @date 2023-07-25 17:20
 */
@ApiModel(description = "")
@Data
public class MessageDto extends PageDto{
    /**
     * 是否已读
     */
    @ApiModelProperty(value = "是否已读", position = 1)
    private Boolean readed;
    /**
     *  id
     */
    @ApiModelProperty(value = "id", position = 2)
    private Long id;

}
