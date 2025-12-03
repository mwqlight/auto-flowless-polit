package cc.flyflow.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel
@Data
public class SignContractInfoDto {
    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String url;
    /**
     * pdf文件 转成图片的url集合
     */
    @ApiModelProperty("pdf文件 转成图片的url集合")
    private List<String> imgUrlList;
    /**
     * 结果
     */
    @ApiModelProperty("结果")
    private Boolean result;
    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Date finishTime;
    /**
     * 顺序
     */
    @ApiModelProperty("顺序")
    private Integer index;
    /**
     * 是否可以拒绝
     */
    @ApiModelProperty("是否可以拒绝")
    private Boolean refuseAble;
}
