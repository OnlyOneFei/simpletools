package com.fly.simpletools.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author Mr_Fei
 * @description 文件读写工具类
 * @date 2020-08-26 13:58
 */
@Slf4j
public class FileReadWriteUtil {

    private FileReadWriteUtil() {
        throw new IllegalStateException("Utility class");
    }

    /* public static void main(String[] args) {
        int index = 0;
        int replaceNum = 0;
        String content = readFileContent("F:\\var\\country_2-size2M.json");
        Pattern regex = Pattern.compile("\\[{1}[1-9]{1}[0-9]{1,2}\\.[0-9]{6,}\\,[1-9]{1}[0-9]*\\.[0-9]{6,}\\]\\,");
        //正则匹配满足条件的告警类型
        Matcher matcher = regex.matcher(content);
        while (matcher.find()) {
            if (index % 2 == 0) {
                String group = matcher.group();
                content = content.replace(group, "");
                replaceNum++;
            }
            index++;
            if (index % 100 == 0) {
                System.out.println("已找到" + index + "个经纬度");
                System.out.println("已替换" + replaceNum + "个经纬度");

            }
        }
        System.out.println("=========>>>>查询完成，执行存储");
        writeLocalStrOne(content, "F:\\var\\country_2.json");
    }*/

    /**
     * 写入文件
     *
     * @param str  要写入的文件内容
     * @param path 文件具体路径
     * @author Mr_Fei
     * @date 2020/8/20 12:20
     * @description writeLocalStrOne
     */
    public static void writeLocalStrOne(String str, String path) {
        try {
            File file = new File(path);
            File parentFile = file.getParentFile();
            if (!parentFile.exists() && parentFile.mkdirs()) {
                log.error("创建文件目录失败！目录地址{}", parentFile.toPath());
            }
            if (file.createNewFile()) {
                log.error("创建文件失败！文件路径：{}", file.toPath());
            }
            if (str != null && !"".equals(str)) {
                FileWriter fw = new FileWriter(file, true);
                fw.write(str);//写入本地文件中
                fw.flush();
                fw.close();
                log.info("执行完毕!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取文件
     *
     * @param fileName 文件名称
     * @return java.lang.String
     * @author Mr_Fei
     * @date 2020/8/20 12:17
     * @description readFileContent
     */
    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuilder sbf = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }
}
