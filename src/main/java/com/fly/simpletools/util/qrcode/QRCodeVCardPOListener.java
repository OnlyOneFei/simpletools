package com.fly.simpletools.util.qrcode;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr_Fei
 * @description
 * @date 2020-04-19 12:56
 * 有个很重要的点 QRCodeVCardPOListener 不能被spring管理，
 * 要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 */
@Slf4j
public class QRCodeVCardPOListener extends AnalysisEventListener<QRCodeVCardPO> {

    /**
     * 存储结构，数据行数过多，考虑分段存储，避免OOM
     */
    private List<QRCodeVCardPO> qrCodeVCardPOS = new ArrayList<>();

    /**
     * @param data    单行数据
     * @param context 解析上下文
     * @author Mr_Fei
     * @date 2020/4/19 12:59
     * @description 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(QRCodeVCardPO data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        if (StringUtils.isEmpty(data.getUserName()) ||
                StringUtils.isEmpty(data.getEmail()) ||
                StringUtils.isEmpty(data.getTelPhone())) {
            log.error("用户名、邮箱或联系方式信息不完整");
            return;
        }
        qrCodeVCardPOS.add(data);
    }

    /**
     * @param context 解析上下文
     * @author Mr_Fei
     * @date 2020/4/19 12:58
     * @description 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        String fileDirVCard = "F:/QRCode/VCard";
        String fileDirText = "F:/QRCode/text";
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        if (!qrCodeVCardPOS.isEmpty()) {
            qrCodeVCardPOS.forEach(qrCodeVCardPO -> {
                QRCodeUtil.createQRCode(qrCodeVCardPO.getContent(), fileDirText, qrCodeVCardPO.getUserName());
                QRCodeUtil.createQRCode(qrCodeVCardPO.getVCardContent(), fileDirVCard, qrCodeVCardPO.getUserName());
                log.info("生成二维码，用户名：{}", qrCodeVCardPO.getUserName());
            });
        }
        log.info("所有数据解析完成！");
    }

    public static void main(String[] args) {
        // 写法1：
        String fileName = "F:/QRCode/员工信息_二维码.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, QRCodeVCardPO.class, new QRCodeVCardPOListener()).sheet().doRead();
    }
}
