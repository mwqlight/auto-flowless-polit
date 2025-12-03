package cc.flyflow.web.controller;

import cc.flyflow.biz.service.IOrgService;
import cc.flyflow.biz.vo.OrgDataVo;
import cc.flyflow.biz.vo.OrgSelectShowVo;
import cc.flyflow.common.dto.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 组织架构接口
 */

@Api(value = "org", tags = {"组织架构接口"})
@RestController
@RequestMapping(value = {"org"})
public class OrgController {

    @Autowired
    private IOrgService orgService;









    /**
     * 按层级查询组织架构树
     * @param deptId 部门id
     * @param showLeave 是否显示离职员工
     * @return 组织架构树数据
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "deptId", value = "部门id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "boolean", name = "showLeave", value = "是否显示离职员工", required = true)
    })
    @ApiOperation(value = "按层级查询组织架构树", notes = "按层级查询组织架构树", httpMethod = "GET")
    @GetMapping("tree")
    public R<OrgSelectShowVo> getOrgTreeData(@RequestParam(defaultValue ="0") String deptId,
                                                                    String type,
                                                                    @RequestParam(defaultValue = "false") Boolean showLeave){
        return orgService.getOrgTreeData(deptId, type, showLeave);
    }


    /**
     * 查询所有的组织架构树，不分层
     * @return 组织架构树数据
     */
    @ApiOperation(value = "查询所有的组织架构树，不分层", notes = "查询所有的组织架构树，不分层", httpMethod = "GET")
    @GetMapping("treeAll")
    public R getOrgTreeDataAll(String keywords,Integer status){
        return orgService.getOrgTreeDataAll(keywords, status);
    }

    /**
     * 模糊搜索用户
     * @param userName 用户名/拼音/首字母
     * @return 匹配到的用户
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "userName", value = "用户名/拼音/首字母", required = true)
    })
    @ApiOperation(value = "模糊搜索用户", notes = "模糊搜索用户", httpMethod = "GET")
    @GetMapping("tree/user/search")
    public R<List<OrgDataVo>> getOrgTreeUser(@RequestParam String userName){
        return orgService.getOrgTreeUser(userName.trim());
    }


}
