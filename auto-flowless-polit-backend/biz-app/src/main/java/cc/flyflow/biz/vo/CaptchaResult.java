package cc.flyflow.biz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码响应对象
 *
 * @author haoxr
 * @since 2023/03/24
 */
@ApiModel(description = "验证码响应对象")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaResult {
    /**
     * 验证码关联key
     */
    @ApiModelProperty(value = "验证码关联key", position = 1)
    private String verifyCodeKey;
    /**
     * 图片base64
     */
    @ApiModelProperty(value = "图片base64", position = 2)
    private String verifyCodeBase64;

}
