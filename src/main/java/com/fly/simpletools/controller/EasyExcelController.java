package com.fly.simpletools.controller;

import com.alibaba.excel.EasyExcel;
import com.fly.simpletools.service.EasyExcelService;
import com.fly.simpletools.service.observer.subject.ConcreteSubject;
import com.fly.simpletools.util.ResponseData;
import com.fly.simpletools.util.esayexcel.ExcelTestWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author Mr_Fei
 * @description
 * @date 2020-06-24 10:27
 */
@RestController
@Slf4j
@RequestMapping(value = "esayexcel")
public class EasyExcelController {

    @Resource
    private ConcreteSubject concreteSubject;

    @Resource
    private EasyExcelService easyExcelService;

    /**
     * @param response 响应消息
     * @author Mr_Fei
     * @date 2020/6/24 10:33
     * @description 获取Excel信息
     */
    @GetMapping(value = "getExcelInfo")
    public void getExcelInfo(HttpServletResponse response) throws IOException {
        String fileNameInfo = "测试文件";
        String sheetName = "文件数量";
        List<List<Object>> dataList = easyExcelService.getExcelDataList();
        List<List<Object>> titleList = easyExcelService.getExcelTitleList();
        titleList.addAll(dataList);
        //=============================================================
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(fileNameInfo, "UTF-8") + ".xlsx";
        //响应设置
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        //导出操作
        EasyExcel.write(response.getOutputStream())
                .registerWriteHandler(new ExcelTestWriteHandler())//自定义监听器
                .sheet(sheetName).doWrite(titleList);
    }

    @GetMapping(value = "setSubjectContent")
    public ResponseData setSubjectContent(String content) {
        concreteSubject.setContent(content);
        return ResponseData.success();
    }

    /**
     * 读取图片，并生成Excel文档信息
     */
  /*  public static void main(String[] args) throws IOException {
        String filePath = "F:\\var\\测试文件.xlsx";
        String picturePath="F:\\文档信息\\Logo图片文件\\变压器图片.png";
        String sheetName = "文件数量";
        byte[] fileBytes = FileUtils.readFileToByteArray(new File(picturePath));

        EasyExcelService easyExcelService = new EasyExcelService();
        List<List<Object>> dataList = easyExcelService.getExcelDataList();
        List<List<Object>> titleList = easyExcelService.getExcelTitleList();
        titleList.addAll(dataList);
        EasyExcel.write(filePath).registerWriteHandler(new ExcelTestWriteHandler(fileBytes))
                .sheet(sheetName).doWrite(titleList);
    }*/

}
