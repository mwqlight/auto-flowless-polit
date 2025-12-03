package cc.flyflow.web.controller;

import cc.flyflow.biz.service.ITaskService;
import cc.flyflow.common.dto.R;
import cc.flyflow.common.dto.TaskParamDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 任务实例接口
 */
@Api(value = "task", tags = {"任务实例接口"})
@RestController
@RequestMapping(value = "task")
public class TaskController {

    @Resource
    private ITaskService taskService;


    /**
     * 完成任务
     *
     * @param completeParamDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "TaskParamDto", name = "completeParamDto", value = "", required = true)
    })
    @ApiOperation(value = "完成任务", notes = "完成任务", httpMethod = "POST")
    @SneakyThrows
    @PostMapping("completeTask")
    public R completeTask(@RequestBody TaskParamDto completeParamDto) {

        return taskService.completeTask(completeParamDto);

    }


    /**
     * 获取任务信息
     *
     * @param taskId 任务id
     * @return
     */
    @GetMapping("getTask")
    public R getTask(String taskId) {
        return taskService.getTask(taskId);
    }

}
