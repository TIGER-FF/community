package com.tigerff.community.controller;

import com.tigerff.community.dto.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/3/8 1:16
 */
@Slf4j
@Controller
public class FileController {
    @ResponseBody
    @PostMapping("/upload")
    public FileDto upload()
    {
        FileDto fileDto=new FileDto();
        log.info("要进行上传图片了---");
        fileDto.setSuccess(1);
        fileDto.setUrl("/images/logos/editormd-logo-32x32.png");
        return fileDto;
    }
}
