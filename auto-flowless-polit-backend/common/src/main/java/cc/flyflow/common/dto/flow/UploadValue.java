package cc.flyflow.common.dto.flow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 上传的文件对象
 * @author Huijun Zhao
 * @description
 * @date 2023-07-28 10:36
 */
@ApiModel(description = "上传的文件对象")
@Data
public class UploadValue {
    /**
     * 请求url
     */
    @ApiModelProperty(value = "请求url", position = 1)
    private String url;
    /**
     * 文件名字
     */
    @ApiModelProperty(value = "文件名字", position = 2)
    private String name;

}
