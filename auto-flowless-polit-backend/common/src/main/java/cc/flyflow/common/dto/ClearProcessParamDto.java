package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 清理流程对象
 * @author zhj
 */
@ApiModel(description = "清理流程对象")
@Data
public class ClearProcessParamDto {
    /**
     * 流程id集合
     */
    @ApiModelProperty("流程id集合")
    private List<String> flowIdList;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String userId;
    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    private String userName;

}
