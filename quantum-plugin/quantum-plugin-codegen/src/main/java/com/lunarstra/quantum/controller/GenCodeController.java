package com.lunarstra.quantum.controller;

import com.lunarstra.quantum.CodegenLauncher;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/10/18 20:44
 */
@RestController
@RequestMapping("/inner/codegen")
public class GenCodeController {
    @Resource
    private DataSource dataSource;

    @PostMapping
    public String GenCode(@RequestBody List<String> tableList) {
        CodegenLauncher.startGenCode(dataSource, tableList);
        return "生成成功：" + tableList.toString();
    }
}
