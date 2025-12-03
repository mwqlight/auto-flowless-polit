package cc.flyflow.common.dto.flow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class HttpSetting {

    @ApiModelProperty(hidden = true)
    private Boolean enable;
    @ApiModelProperty(hidden = true)
    private String url;
    @ApiModelProperty(hidden = true)
    private List<HttpSettingData> header;
    @ApiModelProperty(hidden = true)
    private List<HttpSettingData> body;
    @ApiModelProperty(hidden = true)
    private List<HttpSettingData> result;
}
