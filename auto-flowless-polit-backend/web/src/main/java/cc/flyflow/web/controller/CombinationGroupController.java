package cc.flyflow.web.controller;

import cc.flyflow.biz.service.ICombinationGroupService;
import cc.flyflow.biz.vo.FormGroupVo;
import cc.flyflow.biz.vo.ProcessDataQueryVO;
import cc.flyflow.common.dto.PageDto;
import cc.flyflow.common.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 流程组聚合接口控制器
 */
@Api(value = "combination/group", tags = {"流程组聚合接口控制器"})
@RestController
@RequestMapping(value = "combination/group")
public class CombinationGroupController {

    @Resource
    private ICombinationGroupService combinationGroupService;


    /**
     * 查询表单组包含流程
     *
     * @param hidden 是否查询隐藏的
     * @return 表单组数据
     */
    @ApiOperation(value = "查询表单组包含流程", notes = "查询表单组包含流程", httpMethod = "GET")
    @GetMapping("listGroupWithProcess")
    public R<List<FormGroupVo>> listGroupWithProcess(Boolean hidden) {
        return combinationGroupService.listGroupWithProcess(hidden);
    }

    /**
     * 查询流程分组和主流程数据
     *
     * @return
     */
    @ApiOperation(value = "查询流程分组和主流程数据", notes = "查询流程分组和主流程数据", httpMethod = "GET")
    @GetMapping("listGroupWithProcessMain")
    public R<List<FormGroupVo>> listGroupWithProcessMain() {
        return combinationGroupService.listGroupWithProcessMain();
    }

    /**
     * 搜索流程
     *
     * @param word 关键词
     * @return
     */
    @GetMapping("searchFlowList")
    public R<List<FormGroupVo.FlowVo>> searchFlowList(String word) {
        return combinationGroupService.searchFlowList(word);
    }

    /**
     * 查询所有我可以发起的表单组
     *
     * @return
     */
    @ApiOperation(value = "查询所有我可以发起的表单组", notes = "查询所有我可以发起的表单组", httpMethod = "GET")
    @GetMapping("listCurrentUserStartGroup")
    public R<List<FormGroupVo>> listCurrentUserStartGroup() {
        return combinationGroupService.listCurrentUserStartGroup();
    }

    /**
     * 删除主流程
     *
     * @param uniqueId
     * @return
     */

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "uniqueId", value = "", required = true)
    })
    @ApiOperation(value = "删除主流程", notes = "删除主流程", httpMethod = "DELETE")
    @DeleteMapping("deleteProcessMain/{uniqueId}")
    public R deleteProcessMain(@PathVariable String uniqueId) {
        return combinationGroupService.deleteProcessMain(uniqueId);
    }

    /**
     * 清理流程
     *
     * @param uniqueId 流程唯一id
     * @return 成功失败
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "uniqueId", value = "流程唯一id", required = true)
    })
    @ApiOperation(value = "清理流程", notes = "清理流程", httpMethod = "DELETE")
    @DeleteMapping("clear/{uniqueId}")
    public R clear(@PathVariable String uniqueId) {
        return combinationGroupService.clear(uniqueId);
    }

    /**
     * 查询当前登录用户的待办任务
     *
     * @param pageVO
     * @return
     */
    @PostMapping("queryTodoTaskList")
    public R queryTodoTaskList(@RequestBody PageDto pageVO) {
        return combinationGroupService.queryTodoTaskList(pageVO);
    }

    /**
     * 查询已办任务
     *
     * @param pageVO
     * @return
     */
    @PostMapping("queryFinishedTaskList")
    public R queryFinishedTaskList(@RequestBody ProcessDataQueryVO pageVO) {
        return combinationGroupService.queryFinishedTaskList(pageVO);
    }

    /**
     * 查询已发起的任务
     *
     * @param pageVO
     * @return
     */
    @PostMapping("queryInitiatedTaskList")
    public R queryInitiatedTaskList(@RequestBody ProcessDataQueryVO pageVO) {
        return combinationGroupService.queryInitiatedTaskList(pageVO);
    }

    /**
     * 查询抄送任务
     *
     * @param pageVO
     * @return
     */
    @PostMapping("queryCopiedTaskList")
    public R queryCopiedTaskList(@RequestBody ProcessDataQueryVO pageVO) {
        return combinationGroupService.queryCopiedTaskList(pageVO);
    }
}
