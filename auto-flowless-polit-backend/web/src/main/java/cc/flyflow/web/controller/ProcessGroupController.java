package cc.flyflow.web.controller;

import cc.flyflow.biz.entity.ProcessGroup;
import cc.flyflow.biz.service.IProcessGroupService;
import cc.flyflow.common.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 流程组接口
 */
@Api(value = "processGroup", tags = {"流程组接口"})
@RestController
@RequestMapping(value = {"processGroup"})
public class ProcessGroupController {

    @Resource
    private IProcessGroupService processGroupService;

    /**
     * 组列表
     *
     * @return
     */
    @ApiOperation(value = "组列表", notes = "组列表", httpMethod = "GET")
    @GetMapping("list")
    public R<List<ProcessGroup>> queryList() {
        return processGroupService.queryList();
    }

    /**
     * 新增流程分组
     *
     * @param processGroup 分组名
     * @return 添加结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessGroup", name = "processGroup", value = "分组名", required = true)
    })
    @ApiOperation(value = "新增流程分组", notes = "新增流程分组", httpMethod = "POST")
    @PostMapping("create")
    public R create(@RequestBody ProcessGroup processGroup) {
        return processGroupService.create(processGroup);
    }
    /**
     * 上移排序
     *
     * @param processGroup 分组名
     * @return 添加结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessGroup", name = "processGroup", value = "分组名", required = true)
    })
    @ApiOperation(value = "上移排序", notes = "上移排序", httpMethod = "POST")
    @PostMapping("topSort")
    public R topSort(@RequestBody ProcessGroup processGroup) {
        return processGroupService.topSort(processGroup);
    }
    /**
     * 下移排序
     *
     * @param processGroup 分组名
     * @return 添加结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessGroup", name = "processGroup", value = "分组名", required = true)
    })
    @ApiOperation(value = "下移排序", notes = "下移排序", httpMethod = "POST")
    @PostMapping("bottomSort")
    public R bottomSort(@RequestBody ProcessGroup processGroup) {
        return processGroupService.bottomSort(processGroup);
    }

    /**
     * 修改流程分组
     *
     * @param processGroup 分组名
     * @return 添加结果
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessGroup", name = "processGroup", value = "分组名", required = true)
    })
    @ApiOperation(value = "修改流程分组", notes = "修改流程分组", httpMethod = "POST")
    @PostMapping("edit")
    public R edit(@RequestBody ProcessGroup processGroup) {
        return processGroupService.edit(processGroup);
    }


    /**
     *  删除分组
     * @param id
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "", required = true)
    })
    @ApiOperation(value = "删除分组", notes = "删除分组", httpMethod = "DELETE")
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable long id){
        return processGroupService.delete(id);
    }
}
