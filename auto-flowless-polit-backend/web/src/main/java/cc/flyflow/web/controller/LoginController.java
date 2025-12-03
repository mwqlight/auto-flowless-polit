package cc.flyflow.web.controller;

import cc.flyflow.biz.security.captcha.EasyCaptchaService;
import cc.flyflow.biz.service.ILoginService;
import cc.flyflow.biz.vo.CaptchaResult;
import cc.flyflow.biz.vo.UserBizVO;
import cc.flyflow.common.config.NotWriteLogAnno;
import cc.flyflow.common.dto.LoginUrlDto;
import cc.flyflow.common.dto.R;
import cn.dev33.satoken.stp.SaTokenInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户接口
 */
@Api(value = "login", tags = {"用户接口"})
@RestController
@RequestMapping(value = {"login"})
public class LoginController {

    @Resource
    private ILoginService loginService;

    @Resource
    private EasyCaptchaService captchaService;

    /**
     * 获取验证码
     *
     * @return
     */
    @ApiOperation(value = "获取验证码", notes = "获取验证码", httpMethod = "GET")
    @GetMapping("/captcha")
    @NotWriteLogAnno(printResultLog = false)
    public R<CaptchaResult> getCaptcha() {
        return captchaService.getCaptcha();
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserBizVO", name = "user", value = "", required = true)
    })
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @SneakyThrows
    @PostMapping("login")
    public R login(@RequestBody UserBizVO user) {

        return loginService.login(user);

    }

    /**
     * h5用户登录
     *
     * @param user
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "UserBizVO", name = "user", value = "", required = true)
    })
    @ApiOperation(value = "h5用户登录", notes = "h5用户登录", httpMethod = "POST")
    @SneakyThrows
    @PostMapping("loginAtH5")
    public R<SaTokenInfo> loginAtH5(@RequestBody UserBizVO user) {

        return loginService.loginAtH5(user);

    }

    /**
     * 用户token自动登录
     *
     * @param token
     * @return
     */
    @ApiOperation(value = "用户token自动登录", notes = "用户token自动登录", httpMethod = "GET")
    @SneakyThrows
    @GetMapping("loginByToken")
    public R<SaTokenInfo> loginByToken(String token) {

        return loginService.loginByToken(token);

    }

    /**
     * 用户退出登录
     *
     * @return
     */
    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @SneakyThrows
    @PostMapping("logout")
    public R logout() {
        return loginService.logout();
    }

    /**
     * 钉钉登录
     *
     * @param authCode 授权码
     * @return
     */
    @ApiOperation(value = "钉钉登录", notes = "钉钉登录", httpMethod = "GET")
    @GetMapping("/loginAtDingTalk")
    public R<SaTokenInfo> loginAtDingTalk(String authCode) {
        return loginService.loginAtDingTalk(authCode);
    }

    /**
     * 根据不同平台获取登录地址，设计网页登录、钉钉登录
     *
     * @return
     */
    @ApiOperation(value = "根据不同平台获取登录地址，设计网页登录、钉钉登录", notes = "根据不同平台获取登录地址，设计网页登录、钉钉登录", httpMethod = "GET")
    @GetMapping("/getLoginUrl")
    public R<LoginUrlDto> getLoginUrl() {
        return loginService.getLoginUrl();
    }

    /**
     * 获取登录参数
     *
     * @return
     */
    @ApiOperation(value = "获取登录参数", notes = "获取登录参数", httpMethod = "POST")
    @PostMapping("/getLoginParam")
    public R getLoginParam(@RequestBody Map<String, Object> paramMap) {
        return loginService.getLoginParam(paramMap);
    }





}
