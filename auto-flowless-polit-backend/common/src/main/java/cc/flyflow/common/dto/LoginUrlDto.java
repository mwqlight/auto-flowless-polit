package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUrlDto {
    /**
     * 是否是内部地址  如果是内部地址，如下url类似于 /page/a 否则就是完整的http请求
     */
    @ApiModelProperty("是否是内部地址  如果是内部地址，如下url类似于 /page/a 否则就是完整的http请求")
    private Boolean innerUrl;
    /**
     * 登录地址
     */
    @ApiModelProperty("登录地址")
    private String url;
    /**
     * 是否开启验证码
     * 默认开启的
     */
    @ApiModelProperty("是否开启验证码 默认开启的")
    private Boolean captcha = true;

}
