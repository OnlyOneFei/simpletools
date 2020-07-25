package com.fly.simpletools.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Mr_Fei
 * @description EasyExcel业务处理
 * @date 2020-06-24 10:41
 */
@Service
@Slf4j
public class EasyExcelService {

    /**
     * @return java.util.List
     * @author Mr_Fei
     * @date 2020/6/24 10:47
     * @description 获取数据列
     */
    public List<List<Object>> getExcelDataList() {
        Random random = new Random();
        List<List<Object>> dataList = new ArrayList<>();
        int rowLength = 100;
        for (int i = 0; i < rowLength; i++) {
            List<Object> rowData = new ArrayList<>();
            //第一列
            rowData.add("第" + i + "行");
            int colLength = 10;
            for (int j = 0; j < colLength; j++) {
                //第二列至colLength列
                rowData.add(random.nextInt(5));
            }
            dataList.add(rowData);
        }
        return dataList;
    }

    /**
     * @return java.util.List
     * @author Mr_Fei
     * @date 2020/6/24 10:50
     * @description 获取标题集合
     */
    public List<List<Object>> getExcelTitleList() {
        List<List<Object>> dataList = new ArrayList<>();
        List<Object> rowData = new ArrayList<>();
        int colLength = 11;
        for (int j = 1; j <= colLength; j++) {
            //第二列至colLength列
            rowData.add("第" + j + "列标题");
        }
        dataList.add(rowData);
        return dataList;
    }

}
