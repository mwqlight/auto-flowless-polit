package cc.flyflow.common.dto.flow.node;

import cc.flyflow.common.dto.flow.Node;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhj
 * @version 1.0
 * @description: TODO
 * @date 2024/4/3 16:56
 */
@JsonTypeName("7")
@ApiModel(description = ": TODO")
@Data
public class DelayNode extends Node {



    @ApiModelProperty(hidden = true)
    private Object value;
    /**
     * true:延迟时间  false：表示具体时间
     */
    @ApiModelProperty("true:延迟时间  false：表示具体时间")
    private Boolean mode;
    /**
     * 延迟单位
     */
    @ApiModelProperty("延迟单位")
    private String delayUnit;
}
