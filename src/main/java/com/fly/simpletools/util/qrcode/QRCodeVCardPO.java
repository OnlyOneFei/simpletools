package com.fly.simpletools.util.qrcode;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author Mr_Fei
 * @description 联系人对象
 * @date 2020-04-19 12:32
 */
@Data
public class QRCodeVCardPO {

    /**
     * 行号
     */
    @ExcelProperty(index = 0)
    private String id;

    /**
     * 姓名
     */
    @ExcelProperty(index = 1)
    private String userName;
    /**
     * 部门
     */
    @ExcelProperty(index = 2)
    private String department;
    /**
     * 职务
     */
    @ExcelProperty(index = 3)
    private String position;
    /**
     * 邮箱
     */
    @ExcelProperty(index = 4)
    private String email;
    /**
     * 联系方式
     */
    @ExcelProperty(index = 5)
    private String telPhone;

    /**
     * 公司地址
     */
    private String workAddress = "成都市高新区天华一路99号天府软件园B区8栋4层";
    /**
     * 公司地址英文
     */
    private String workAddressEN = "4F, B8, Tianfu Software Park, No. 99, Tianhua 1st Road, Chengdu";

    public String getVCardContent() {
        return "BEGIN:VCARD\n" +
                "VERSION:3.0\n" +
                //姓名
                "FN:" + this.userName + "\n" +
                //部门
                "ROLE:" + this.department + "\n" +
                //职位
                "TITLE:" + this.position + "\n" +
                //邮箱
                "EMAIL;PREF;INTERNET:" + this.email + "\n" +
                //联系方式
                "TEL;CELL;VOICE:" + this.telPhone + "\n" +
                //工作地址
                "ADR;WORK;POSTAL:" + this.workAddress + "\n" +
//                "ADR;WORK;POSTAL:4F, B8, Tianfu Software Park, No. 99, Tianhua 1st Road, Chengdu\n" +
                "END:VCARD";
    }

    public String getContent() {
        return "姓名:" + this.userName + "\n" +
                "部门:" + this.department + "\n" +
                "职务:" + this.position + "\n" +
                "邮箱:" + this.email + "\n" +
                "联系方式:" + this.telPhone + "\n" +
                "公司地址:" + this.workAddress + "\n" +
                this.workAddressEN + "\n";
    }
}
