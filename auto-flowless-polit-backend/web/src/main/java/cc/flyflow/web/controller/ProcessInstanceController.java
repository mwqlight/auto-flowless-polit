package cc.flyflow.web.controller;

import cc.flyflow.biz.service.IProcessInstanceService;
import cc.flyflow.biz.service.IProcessInstanceUserCopyService;
import cc.flyflow.biz.vo.ProcessDataQueryVO;
import cc.flyflow.biz.vo.ProcessInstanceCopyVo;
import cc.flyflow.biz.vo.ProcessInstanceRecordVO;
import cc.flyflow.common.dto.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 流程实例接口
 */
@Api(value = "process-instance", tags = {"流程实例接口"})
@RestController
@RequestMapping( "process-instance")
public class ProcessInstanceController {

    @Resource
    private IProcessInstanceService processInstanceService;

    @Resource
    private IProcessInstanceUserCopyService processInstanceUserCopyService;



    /**
     * 启动流程
     *
     * @param processInstanceParamDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessInstanceParamDto", name = "processInstanceParamDto", value = "", required = true)
    })
    @ApiOperation(value = "启动流程", notes = "启动流程", httpMethod = "POST")
    @SneakyThrows
    @PostMapping("startProcessInstance")
    public R startProcessInstance(@RequestBody ProcessInstanceParamDto processInstanceParamDto) {

        return processInstanceService.startProcessInstance(processInstanceParamDto);

    }



    /**
     * 查询当前登录用户已办任务的流程实例
     *
     * @param pageDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessDataQueryVO", name = "pageDto", value = "", required = true)
    })
    @ApiOperation(value = "查询当前登录用户已办任务的流程实例", notes = "查询当前登录用户已办任务的流程实例", httpMethod = "POST")
    @SneakyThrows
    @PostMapping("queryMineDoneProcessInstance")
    public R<PageResultDto<ProcessInstanceDto>> queryMineDoneProcessInstance(@RequestBody ProcessDataQueryVO pageDto) {

        return processInstanceService.queryMineDoneProcessInstance(pageDto);

    }


    /**
     * 发起的流程列表
     *
     * @param pageDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessDataQueryVO", name = "pageDto", value = "", required = true)
    })
    @ApiOperation(value = "发起的流程列表", notes = "发起的流程列表", httpMethod = "POST")
    @PostMapping("queryList")
    public R<Page<ProcessInstanceRecordVO>> queryList(@RequestBody ProcessDataQueryVO pageDto) {
        return processInstanceService.queryList(pageDto);
    }

    /**
     * 查询流程实例详情
     *
     * @param processInstanceId
     * @return
     */
    @ApiOperation(value = "查询流程实例详情", notes = "查询流程实例详情", httpMethod = "GET")
    @GetMapping("queryDetailByProcessInstanceId")
    public R<ProcessInstanceRecordVO> queryDetailByProcessInstanceId(String processInstanceId) {
        return processInstanceService.queryDetailByProcessInstanceId(processInstanceId);
    }



    /**
     * 查询抄送我的流程实例
     *
     * @param pageDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "ProcessDataQueryVO", name = "pageDto", value = "", required = true)
    })
    @ApiOperation(value = "查询抄送我的流程实例", notes = "查询抄送我的流程实例", httpMethod = "POST")
    @SneakyThrows
    @PostMapping("queryMineCCProcessInstance")
    public R<Page<ProcessInstanceCopyVo>> queryMineCCProcessInstance(@RequestBody ProcessDataQueryVO pageDto) {
        return processInstanceUserCopyService.queryMineCCProcessInstance(pageDto);
    }





    /**
     * 查询进行中的任务
     *
     * @param processInstanceId
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "processInstanceId", value = "", required = true)
    })
    @ApiOperation(value = "查询进行中的任务", notes = "查询进行中的任务", httpMethod = "POST")
    @SneakyThrows
    @PostMapping("queryTaskListInProgress/{processInstanceId}")
    public R<List<TaskDto>> queryTaskListInProgress(@PathVariable String processInstanceId) {

        return processInstanceService.queryTaskListInProgress(processInstanceId);

    }



}
