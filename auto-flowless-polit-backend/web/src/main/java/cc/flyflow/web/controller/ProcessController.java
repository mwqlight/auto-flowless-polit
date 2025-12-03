package cc.flyflow.web.controller;

import cc.flyflow.biz.constants.ValidGroup;
import cc.flyflow.biz.service.IProcessService;
import cc.flyflow.biz.vo.ProcessVO;
import cc.flyflow.common.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 流程接口
 */
@Api(value = "process", tags = {"流程接口"})
@Validated
@RestController
@RequestMapping(value = "process")
public class ProcessController {

    @Resource
    private IProcessService processService;
    /**
     * 根据流程唯一标识查询流程列表
     * @param uniqueId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "uniqueId", value = "", required = true)
    })
    @ApiOperation(value = "根据流程唯一标识查询流程列表", notes = "根据流程唯一标识查询流程列表", httpMethod = "GET")
    @GetMapping("/getListByUniqueId/{uniqueId}")
    public R<ProcessVO> getListByUniqueId(@PathVariable String uniqueId){
        return processService.getListByUniqueId(uniqueId);
    }

    /**
     * 设置主流程
     * @param flowId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "flowId", value = "", required = true)
    })
    @ApiOperation(value = "设置主流程", notes = "设置主流程", httpMethod = "POST")
    @PostMapping("setMainProcess/{flowId}")
    public R setMainProcess(@PathVariable String flowId){
        return processService.setMainProcess(flowId);
    }
    /**
     * 获取详细数据
     *
     * @param flowId 流程id
     * @return
     */
    @ApiOperation(value = "获取详细数据", notes = "获取详细数据", httpMethod = "GET")
    @GetMapping("getDetail")
    public R<ProcessVO> getDetail(String flowId) {
        return processService.getDetail(flowId);
    }

    /**
     * 创建流程
     *
     * @param processVO
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessVO", name = "processVO", value = "", required = true)
    })
    @ApiOperation(value = "创建流程", notes = "创建流程", httpMethod = "POST")
    @PostMapping("create")
    public R create(@Validated(value= ValidGroup.Crud.Create.class) @RequestBody ProcessVO processVO) {
        return processService.create(processVO);
    }



    /**
     * 编辑表单
     *
     * @param flowId 摸板ID
     * @param type       类型 stop using delete
     * @param groupId 流程组id
     * @return 操作结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "flowId", value = "摸板ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "type", value = "类型 stop using delete", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "long", name = "groupId", value = "流程组id")
    })
    @ApiOperation(value = "编辑表单", notes = "编辑表单", httpMethod = "PUT")
    @PutMapping("update/{flowId}")
   public R update(@PathVariable String flowId,
                 @RequestParam String type,
                 @RequestParam(required = false) Long groupId){
        return processService.update(flowId, type, groupId);
    }



}
