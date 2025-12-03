package cc.flyflow.biz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@ApiModel(description = "流程组")
@Getter
@Setter
@Accessors(chain = true)
public class ProcessGroup  extends BaseEntity {


    /**
     * 分组名
     */
    @ApiModelProperty(value = "分组名", position = 1)
    @TableField("`group_name`")
    private String groupName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", position = 2)
    @TableField("`sort`")
    private Integer sort;


}
