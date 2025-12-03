package cc.flyflow.web.controller;

import cc.flyflow.biz.service.IFormService;
import cc.flyflow.biz.vo.FormRemoteSelectOptionParamVo;
import cc.flyflow.biz.vo.QueryFormListParamVo;
import cc.flyflow.common.dto.R;
import cc.flyflow.common.dto.flow.FormItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 表单相关控制器
 */
@Api(value = "form", tags = {"表单相关控制器"})
@RestController
@RequestMapping(value = "form")
public class FormController {
    @Resource
    private IFormService formService;

    /**
     * 获取下拉选项
     *
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "FormRemoteSelectOptionParamVo", name = "formRemoteSelectOptionParamVo", value = "", required = true)
    })
    @ApiOperation(value = "获取下拉选项", notes = "获取下拉选项", httpMethod = "POST")
    @PostMapping("selectOptions")
    public R selectOptions(@RequestBody FormRemoteSelectOptionParamVo formRemoteSelectOptionParamVo) {

        return formService.selectOptions(formRemoteSelectOptionParamVo);
    }

    /**
     * 获取表单数据
     *
     * @param taskDto 参数
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "QueryFormListParamVo", name = "taskDto", value = "参数", required = true)
    })
    @ApiOperation(value = "获取表单数据", notes = "获取表单数据", httpMethod = "POST")
    @PostMapping("getFormList")
    public R<List<FormItemVO>> getFormList(@RequestBody QueryFormListParamVo taskDto) {
        return formService.getFormList(taskDto);
    }

    /**
     * 获取表单详细数据
     *
     * @param taskDto 参数
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "QueryFormListParamVo", name = "taskDto", value = "参数", required = true)
    })
    @ApiOperation(value = "获取表单详细数据", notes = "获取表单详细数据", httpMethod = "POST")
    @PostMapping("getFormDetail")
    public R getFormDetail(@RequestBody QueryFormListParamVo taskDto) {
        return formService.getFormDetail(taskDto);
    }
    /**
     * 动态表单
     *
     * @param taskDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "QueryFormListParamVo", name = "taskDto", value = "", required = true)
    })
    @ApiOperation(value = "动态表单", notes = "动态表单", httpMethod = "POST")
    @PostMapping("dynamicFormList")
    public R<List<FormItemVO>> dynamicFormList(@RequestBody QueryFormListParamVo taskDto) {
        return formService.dynamicFormList(taskDto);
    }
}
