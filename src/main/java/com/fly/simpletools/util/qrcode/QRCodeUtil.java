package com.fly.simpletools.util.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.util.EnumMap;

/**
 * @author Mr_Fei
 * @description QRCode 二维码工具类
 * @date 2020-04-19 11:17
 * VCard：标准的通信薄基本格式 https://zh.wikipedia.org/wiki/VCard
 */
@Slf4j
public class QRCodeUtil {


    private QRCodeUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param content  内容，如果是连接，则需要加 http://
     * @param fileDir  文件目录
     * @param fileName 文件名字
     * @author Mr_Fei
     * @date 2020/4/19 12:05
     * @description 创建二维码
     */
    public static void createQRCode(String content, String fileDir, String fileName) {
        //宽度
        int width = 500;
        //高度
        int height = 500;
        //生成图片格式
        String format = "png";
        //参数对象
        EnumMap<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        //字符编码集
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //纠错等级 L(7%)<M(15%)<Q(25%)<H(30%) 二维码能被正常扫描时允许被遮挡的最大面积占总面积的比率。
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //边距，二维码距离边框的距离
        hints.put(EncodeHintType.MARGIN, 2);
        //格式对象
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            //创建文件
            File sourceFile = new File(fileDir + File.separator + fileName + "." + format);
            if (!sourceFile.exists() && !sourceFile.mkdirs()) {
                log.error("文件夹创建失败,文件对象：{}", sourceFile);
            }
            //获取文件路径
            Path filePath = sourceFile.toPath();
            //写出图片到文件路径
            MatrixToImageWriter.writeToPath(bitMatrix, format, filePath);
        } catch (Exception e) {
            log.error("生成二维码错误，QRCodeUtil/createQRCode，{}", e.getMessage());
        }
    }
}

