package cc.flyflow.web.controller;

import cc.flyflow.biz.service.IBaseService;
import cc.flyflow.biz.vo.*;
import cc.flyflow.common.dto.IndexPageStatistics;
import cc.flyflow.common.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 基础的控制器接口
 */
@Api(value = "base", tags = {"基础的控制器接口"})
@RestController
@RequestMapping(value = "base")
public class BaseController {

    @Resource
    private IBaseService baseService;

    /**
     * 修改前端版本号
     *
     * @param webVersionVO
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "WebVersionVO", name = "webVersionVO", value = "", required = true)
    })
    @ApiOperation(value = "修改前端版本号", notes = "修改前端版本号", httpMethod = "POST")
    @PostMapping("setWebVersion")
    public R setWebVersion(@RequestBody WebVersionVO webVersionVO) {
        return baseService.setWebVersion(webVersionVO);
    }

    /**
     * 获取当前系统版本号
     *
     * @return
     */
    @ApiOperation(value = "获取当前系统版本号", notes = "获取当前系统版本号", httpMethod = "GET")
    @GetMapping("getWebVersion")
    public R getWebVersion() {
        return baseService.getWebVersion();
    }


    /**
     * 首页数据
     *
     * @return
     */
    @ApiOperation(value = "首页数据", notes = "首页数据", httpMethod = "GET")
    @GetMapping("index")
    public R<IndexPageStatistics> index() {
        return baseService.index();
    }


    /**
     * 获取所有地区数据
     *
     * @return
     */
    @ApiOperation(value = "获取所有地区数据", notes = "获取所有地区数据", httpMethod = "GET")
    @GetMapping("areaList")
    public R areaList() {
        return baseService.areaList();
    }

    /**
     * 同步数据--主要是真的第三方接口，比如钉钉
     *
     * @return
     */
    @ApiOperation(value = "同步数据--主要是真的第三方接口，比如钉钉", notes = "同步数据--主要是真的第三方接口，比如钉钉", httpMethod = "POST")
    @PostMapping("loadRemoteData")
    public R loadRemoteData() {
        return baseService.loadRemoteData();
    }

    /**
     * 格式化流程显示
     *
     * @param nodeFormatParamVo
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "NodeFormatParamVo", name = "nodeFormatParamVo", value = "", required = true)
    })
    @ApiOperation(value = "格式化流程显示", notes = "格式化流程显示", httpMethod = "POST")
    @PostMapping("formatStartNodeShow")
    public R<NodeFormatResultVo> formatStartNodeShow(@RequestBody NodeFormatParamVo nodeFormatParamVo) {
        return baseService.formatStartNodeShow(nodeFormatParamVo);
    }

    /**
     * 查询头部显示数据
     *
     * @param nodeFormatParamVo
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "QueryFormListParamVo", name = "nodeFormatParamVo", value = "", required = true)
    })
    @ApiOperation(value = "查询头部显示数据", notes = "查询头部显示数据", httpMethod = "POST")
    @PostMapping("queryHeaderShow")
    public R<TaskHeaderShowResultVO> queryHeaderShow(@RequestBody QueryFormListParamVo nodeFormatParamVo) {
        return baseService.queryHeaderShow(nodeFormatParamVo);
    }

    /**
     * 获取任务操作列表
     *
     * @param taskId 任务id
     * @return
     */
    @ApiOperation(value = "获取任务操作列表", notes = "获取任务操作列表", httpMethod = "GET")
    @GetMapping("queryTaskOperData")
    public R<TaskOperDataResultVO> queryTaskOperData(String taskId) {
        return baseService.queryTaskOperData(taskId);
    }


}
