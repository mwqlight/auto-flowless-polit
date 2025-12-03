package cc.flyflow.web.controller;

import cc.flyflow.biz.service.IFileService;
import cc.flyflow.biz.vo.FileVO;
import cc.flyflow.common.config.NotWriteLogAnno;
import cc.flyflow.common.dto.R;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 文件控制器
 */
@RestController
@Api(tags = "文件控制器",value = "file")
@RequestMapping(value = {"file"})
public class FileController {


    @Resource
    private IFileService fileService;
    @Value("${file.dir}")
    private String fileDir;


    /**
     * 上传文件
     * @param file
     * @return
     */
    @SneakyThrows
    @ApiOperation(value = "上传文件", notes = "返回类型为String")
    @PostMapping("upload")
    @NotWriteLogAnno(exclude = false,all = true)
    public R<String> upload(MultipartFile file){


        String originalFilename = file.getOriginalFilename();

        return fileService.save(file.getBytes(),originalFilename);

    }



    /**
     * 上传文件
     * @param file
     * @return
     */
    @SneakyThrows
    @ApiOperation(value = "上传base64文件", notes = "返回类型为String")
    @PostMapping("uploadBase64")
    @NotWriteLogAnno(exclude = false,all = true)
    public R<String> uploadBase64(@RequestBody FileVO file){

        File f=new File(StrUtil.format("/tmp/{}_{}", IdUtil.fastSimpleUUID(),file.getFileName()));

        try {
            Base64.decodeToFile(file.getBase64(),f);

            return fileService.save(FileUtil.readBytes(f),file.getFileName());
        } finally {
            FileUtil.del(f);
        }

    }

    /**
     * 显示文件
     * @param key
     */
    @ApiOperation(value = "显示文件", notes = "无返回值，渲染字节流")
    @SneakyThrows
    @GetMapping("/show/{key}")
    @NotWriteLogAnno(exclude = false,all = false,printResultLog = false,paramsExclude = "response")
    public void show(@PathVariable String key, HttpServletResponse response){
        String format = StrUtil.format("{}/{}", fileDir, key);
        IoUtil.write(response.getOutputStream(),true,FileUtil.readBytes(format));
    }

    /**
     * 显示文件
     * @param key
     */
    @ApiOperation(value = "显示文件", notes = "无返回值，渲染字节流")
    @SneakyThrows
    @GetMapping("/show/{s}/{key}")
    @NotWriteLogAnno(exclude = false,all = false,printResultLog = false,paramsExclude = "response")
    public void show(@PathVariable String key,@PathVariable String s, HttpServletResponse response){
        String format = StrUtil.format("{}/{}/{}", fileDir,s, key);
        IoUtil.write(response.getOutputStream(),true,FileUtil.readBytes(format));
    }
}
