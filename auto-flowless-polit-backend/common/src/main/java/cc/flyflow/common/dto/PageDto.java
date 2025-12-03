package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页对象
 */
@ApiModel(description = "分页对象")
@Data
public class PageDto {

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码", position = 1)
    private Integer pageNum;
    /**
     * 每页的数量
     */
    @ApiModelProperty(value = "每页的数量", position = 2)
    private Integer pageSize;


}
