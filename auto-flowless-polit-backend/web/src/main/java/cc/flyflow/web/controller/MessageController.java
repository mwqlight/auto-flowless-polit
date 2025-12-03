package cc.flyflow.web.controller;

import cc.flyflow.biz.entity.Message;
import cc.flyflow.biz.service.IMessageService;
import cc.flyflow.common.config.NotWriteLogAnno;
import cc.flyflow.common.dto.MessageDto;
import cc.flyflow.common.dto.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 消息控制器
 */
@Api(value = "message", tags = {"消息控制器"})
@RestController
@RequestMapping(value = {"message"})
public class MessageController {

    @Resource
    private IMessageService messageService;


    /**
     * 获取未读数量
     */
    @ApiOperation(value = "获取未读数量", notes = "获取未读数量", httpMethod = "GET")
    @NotWriteLogAnno(printResultLog = false)
    @SneakyThrows
    @GetMapping("/unreadNum")
    public R queryUnreadNum(Long lastId) {
        return messageService.queryUnreadNum(lastId);
    }

    /**
     * 查询列表
     *
     * @param pageDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "MessageDto", name = "pageDto", value = "", required = true)
    })
    @ApiOperation(value = "查询列表", notes = "查询列表", httpMethod = "POST")
    @PostMapping("queryList")
    public R<Page<Message>> queryList(@RequestBody MessageDto pageDto) {
        return messageService.queryList(pageDto);
    }

    /**
     * 删除消息
     *
     * @param messageDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "MessageDto", name = "messageDto", value = "", required = true)
    })
    @ApiOperation(value = "删除消息", notes = "删除消息", httpMethod = "DELETE")
    @DeleteMapping("delete")
    public R delete(@RequestBody MessageDto messageDto) {
        return messageService.delete(messageDto);
    }

    /**
     * 置为已读
     *
     * @param messageDto
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "MessageDto", name = "messageDto", value = "", required = true)
    })
    @ApiOperation(value = "置为已读", notes = "置为已读", httpMethod = "POST")
    @PostMapping("read")
    public R read(@RequestBody MessageDto messageDto) {
        return messageService.read(messageDto);
    }

    /**
     * 全部已读
     * @return
     */
    @PostMapping("readAll")
    public R readAll() {
        return messageService.readAll();
    }


}
