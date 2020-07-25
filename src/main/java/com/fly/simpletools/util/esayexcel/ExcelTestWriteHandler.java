package com.fly.simpletools.util.esayexcel;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;

import java.util.List;

/**
 * @author Mr_Fei
 * @description 测试拦截器
 * @date 2020-06-24 10:39
 */
public class ExcelTestWriteHandler implements CellWriteHandler {

    /**
     * 图片文件
     */
    private byte[] fileBytes;

    public ExcelTestWriteHandler() {
    }

    public ExcelTestWriteHandler(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    //创建单元格之前
    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head,
                                 Integer integer, Integer integer1, Boolean aBoolean) {

    }

    //创建单元格之后
    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head,
                                Integer integer, Boolean aBoolean) {

    }

    //处理单元格之后
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> list,
                                 Cell cell, Head head, Integer integer, Boolean aBoolean) {
        Sheet sheet = writeSheetHolder.getSheet();
        //设置单元格样式
        this.setCellStyle(cell, sheet);
        //设置字体
        this.setFont(cell, sheet);
        //设置列宽 7000
        this.setColumnWidthBySheet(cell, sheet);
        //1、设置页眉页脚
        this.setSheetHeaderAndFooter(cell, sheet);
        //2、设置打印区域
        this.setPrintAreaBySheet(cell, sheet);
        //3、设置单元格公式
        this.setCellFormulaBySheet(cell);
        //4、合并单元格
        this.mergeCell(cell, sheet);
        //5、冻结窗格/单元格
        this.setFreezePaneBySheet(cell, sheet);
        //6、设置固定表头行/列
        this.setRepeatingColumnsBySheet(cell, sheet);
        //7、设置图片
        this.setPictureBySheet(cell, sheet);
        //8、设置柱状图
        this.addBarGraph(cell, sheet);

    }

    /**
     * @param sheet 工作簿对象
     * @author Mr_Fei
     * @date 2020/3/12 12:20
     * @description 生成柱状图
     */
    private void addBarGraph(Cell cell, Sheet sheet) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        if (columnIndex == 1 && rowIndex == 2) {
            //获取ctChart
            CTChart ctChart = this.getCTChart(sheet, 0, 8, 16, 25);
            CTPlotArea ctPlotArea = ctChart.getPlotArea();
            // 创建柱状图模型
            CTBarChart ctBarChart = ctPlotArea.addNewBarChart();
            this.setCTBarChart(ctBarChart, ctChart);
            // 创建序列,并且设置选中区域
            for (int i = 0; i < 3; i++) {
                CTBarSer ctBarSer = ctBarChart.addNewSer();
                //添加一个空的id元素，并设置id。
                ctBarSer.addNewIdx().setVal(i);
                // 图例区
                this.setLegendDataCtStrRef(sheet, ctBarSer, 0, 0, i + 1, i + 1);
                // 横坐标区
                this.setAxisDataCtStrRef(sheet, ctBarSer, 4, 10, 0, 0);
                // 数据区域
                this.setNumDataCtStrRef(sheet, ctBarSer, 4, 10, 0, 0);
                // 设置标签格式
                this.setCTDLbls(ctBarChart, ctBarSer);
            }
            //设置横纵坐标
            this.setXYCoordinate(ctBarChart, ctPlotArea);
            //设置图注
            this.setLegendByCTChart(ctChart);
        }
    }

    // legend图注
    private void setLegendByCTChart(CTChart ctChart) {
        // legend图注
        // if(true){
        CTLegend ctLegend = ctChart.addNewLegend();
        ctLegend.addNewLegendPos().setVal(STLegendPos.B);
        ctLegend.addNewOverlay().setVal(false);
    }

    //设置横纵坐标
    private void setXYCoordinate(CTBarChart ctBarChart, CTPlotArea ctPlotArea) {
        // 横坐标 告诉BarChart它有坐标轴，并给它们id
        ctBarChart.addNewAxId().setVal(111);
        CTCatAx ctCatAx = ctPlotArea.addNewCatAx();
        ctCatAx.addNewAxId().setVal(111);
        CTScaling ctScaling = ctCatAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctCatAx.addNewAxPos().setVal(STAxPos.B);
        ctCatAx.addNewCrossAx().setVal(111);
        ctCatAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        // 纵坐标
        ctBarChart.addNewAxId().setVal(222);
        CTValAx ctValAx = ctPlotArea.addNewValAx();
        ctValAx.addNewAxId().setVal(222);
        ctScaling = ctValAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctValAx.addNewAxPos().setVal(STAxPos.L);
        ctValAx.addNewCrossAx().setVal(222);
        ctValAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
        // 是否删除横坐标
        ctCatAx.addNewDelete().setVal(false);
        // 是否删除主左边轴
        ctValAx.addNewDelete().setVal(false);
    }

    // 数据区域
    private void setNumDataCtStrRef(Sheet sheet, CTBarSer ctBarSer, int firstRow, int lastRow, int firstCol, int lastCol) {
        CTNumRef ctNumRef = ctBarSer.addNewVal().addNewNumRef();
        // 选第1-6行,第1-3列作为数据区域 //1 2 3
        String numDataRange = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol)
                .formatAsString(sheet.getSheetName(), true);
        ctNumRef.setF(numDataRange);
    }

    // 横坐标区
    private void setAxisDataCtStrRef(Sheet sheet, CTBarSer ctBarSer, int firstRow, int lastRow, int firstCol, int lastCol) {
        CTStrRef axisDataCtStrRef = ctBarSer.addNewCat().addNewStrRef();
        String axisDataRange = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol)
                .formatAsString(sheet.getSheetName(), true);
        axisDataCtStrRef.setF(axisDataRange);
    }

    //设置图例区域
    private void setLegendDataCtStrRef(Sheet sheet, CTBarSer ctBarSer, int firstRow, int lastRow, int firstCol, int lastCol) {
        CTStrRef legendDataCtStrRef = ctBarSer.addNewTx().addNewStrRef();
        // 选定区域第0行,第1,2,3列标题作为图例 //1 2 3
        String legendDataRange = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol)
                .formatAsString(sheet.getSheetName(), true);
        legendDataCtStrRef.setF(legendDataRange);
    }

    //设置边框
    private void setCTDLbls(CTBarChart ctBarChart, CTBarSer ctBarSer) {
        // 添加柱状边框线
        ctBarSer.addNewSpPr().addNewLn().addNewSolidFill().addNewSrgbClr().setVal(new byte[]{0, 0, 0});
        // 设置负轴颜色不是白色
        ctBarSer.addNewInvertIfNegative().setVal(false);
        // 设置标签格式
        CTBoolean ctBoolean = ctBarChart.addNewVaryColors();
        ctBoolean.setVal(false);
        CTDLbls newDLbls = ctBarSer.addNewDLbls();
        newDLbls.setShowLegendKey(ctBoolean);
        ctBoolean.setVal(true);
        newDLbls.setShowVal(ctBoolean);
        ctBoolean.setVal(false);
        newDLbls.setShowCatName(ctBoolean);
        newDLbls.setShowSerName(ctBoolean);
        newDLbls.setShowPercent(ctBoolean);
        newDLbls.setShowBubbleSize(ctBoolean);
        newDLbls.setShowLeaderLines(ctBoolean);
    }

    //设置图类型
    private void setCTBarChart(CTBarChart ctBarChart, CTChart ctChart) {
        ctBarChart.getVaryColors().setVal(true);
        // 设置图类型
        ctBarChart.addNewGrouping().setVal(STBarGrouping.CLUSTERED);
        ctBarChart.addNewBarDir().setVal(STBarDir.COL);
        // 是否添加左侧坐标轴
        ctChart.addNewDispBlanksAs().setVal(STDispBlanksAs.ZERO);
        ctChart.addNewShowDLblsOverMax().setVal(true);
    }

    private CTChart getCTChart(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        // 创建一个画布
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        // 画一个图区域 前四个默认0，从第8行到第25行,从第0列到第6列的区域
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, firstRow, lastRow, firstCol, lastCol);
        // 创建一个chart对象
        Chart chart = drawing.createChart(anchor);
        return ((XSSFChart) chart).getCTChart();
    }

    /**
     * @param cell  单元格对象
     * @param sheet sheet
     * @author Mr_Fei
     * @date 2020/6/26 21:15
     * @description 设置页眉页脚
     */
    private void setSheetHeaderAndFooter(Cell cell, Sheet sheet) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        if (columnIndex == 1 && rowIndex == 1) {
            //页脚
            Footer footer = sheet.getFooter();
            footer.setCenter("Center Footer");
            footer.setLeft("Left Footer");
            footer.setRight("Right Footer");
            //页眉
            Header header = sheet.getHeader();
            header.setCenter("Center Header");
            header.setLeft("Left Header");
            header.setRight("Right Header");
        }
    }

    /**
     * @param cell  单元格对象
     * @param sheet sheet
     * @author Mr_Fei
     * @date 2020/6/26 21:15
     * @description 设置打印区域
     */
    private void setPrintAreaBySheet(Cell cell, Sheet sheet) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        Workbook workbook = sheet.getWorkbook();
        if (columnIndex == 1 && rowIndex == 1) {
            //参数1：工作表索引，默认第一个sheet
            //参数2：开始列坐标
            //参数3：结束列坐标
            //参数4：开始行坐标
            //参数5：结束行坐标
            workbook.setPrintArea(0, 0, 10, 0, 35);
        }
    }

    /**
     * @param cell 单元格对象
     * @author Mr_Fei
     * @date 2020/6/26 21:15
     * @description 设置单元格公式
     */
    private void setCellFormulaBySheet(Cell cell) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        //第一列，第十行
        if (columnIndex == 1 && rowIndex == 10) {
            cell.setCellFormula("=SUM(B2:B10)");
        }
        if (columnIndex == 2 && rowIndex == 10) {
            cell.setCellFormula("SUM(C2:C10)");
        }
        if (columnIndex == 3 && rowIndex == 10) {
            cell.setCellFormula("SUM(D2:D10)");
        }
    }

    /**
     * @param cell  单元格对象
     * @param sheet sheet
     * @author Mr_Fei
     * @date 2020/6/26 21:15
     * @description 合并单元格
     */
    private void mergeCell(Cell cell, Sheet sheet) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        //合并0-2行，0-0列
        if (rowIndex == 2 && columnIndex == 0) {
            CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 2, 0, 0);
            sheet.addMergedRegion(cellRangeAddress);
        }
    }

    /**
     * @param cell  单元格对象
     * @param sheet sheet
     * @author Mr_Fei
     * @date 2020/6/26 21:47
     * @description 设置冻结窗格/单元格
     */
    private void setFreezePaneBySheet(Cell cell, Sheet sheet) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        //第一列，第十行
        if (columnIndex == 1 && rowIndex == 1) {
            //只冻结一行
            //参数1：冻结列，
            //参数2：冻结行，
            //参数3：冻结后最左侧展示的列，隐藏的列数，冻结前的列不能隐藏
            //参数4：冻结后隐藏的行数，包括冻结的行数，行同理。
            sheet.createFreezePane(0, 1, 0, 1);
            //只冻结一列
            sheet.createFreezePane(1, 0, 1, 0);
            //冻结列和行（第一个参数是列，第二个参数是行）。
            //注意：坐标是从1开始，而不是0开始。
//            sheet.createFreezePane(2, 2);
        }
    }

    /**
     * @param cell  单元格对象
     * @param sheet sheet
     * @author Mr_Fei
     * @date 2020/6/26 21:47
     * @description 设置固定表头行/列
     */
    private void setRepeatingColumnsBySheet(Cell cell, Sheet sheet) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        //第一列，第十行
        if (columnIndex == 1 && rowIndex == 10) {
            //固定列
            sheet.setRepeatingColumns(CellRangeAddress.valueOf("A:A"));
            //固定行
            sheet.setRepeatingRows(CellRangeAddress.valueOf("1:1"));
        }
    }

    /**
     * @param cell  单元格对象
     * @param sheet sheet
     * @author Mr_Fei
     * @date 2020/6/26 21:47
     * @description 设置图片
     */
    private void setPictureBySheet(Cell cell, Sheet sheet) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        if (columnIndex == 1 && rowIndex == 2) {
            int pictureIdx = sheet.getWorkbook().addPicture(this.fileBytes, Workbook.PICTURE_TYPE_PNG);
            CreationHelper helper = sheet.getWorkbook().getCreationHelper();
            ClientAnchor anchor = helper.createClientAnchor();
            // 图片插入坐标
            anchor.setCol1(0);//开始列
            anchor.setCol2(1);//结束列
            anchor.setRow1(0);//开始行
            anchor.setRow2(3);//结束行
            anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
            // 插入图片
            Drawing drawing = sheet.createDrawingPatriarch();
            drawing.createPicture(anchor, pictureIdx);
        }
    }

    /**
     * @param cell  单元格对象
     * @param sheet sheet
     * @author Mr_Fei
     * @date 2020/6/26 21:15
     * @description 设置单元格样式
     */
    private void setCellStyle(Cell cell, Sheet sheet) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        Workbook workbook = sheet.getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        if (columnIndex > 0 && rowIndex > 4) {
            DataFormat dataFormat = workbook.createDataFormat();
            cellStyle.setDataFormat(dataFormat.getFormat("#,##0"));
        }
        //设置边框样式
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        //设置 垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        if (columnIndex >= 1 && rowIndex == 2) {
            //设置 水平居右
            cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        } else {
            //设置 水平居中
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        }
        //在单元格中应用样式
        cell.setCellStyle(cellStyle);
    }

    /**
     * @param cell  单元格对象
     * @param sheet sheet
     * @author Mr_Fei
     * @date 2020/6/26 21:15
     * @description 设置字体，需要先设置单元格样式，后设置字体
     */
    private void setFont(Cell cell, Sheet sheet) {
        int columnIndex = cell.getColumnIndex();
        Workbook workbook = sheet.getWorkbook();
        //设置字体
        Font font = workbook.createFont();
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        //设置字体名字
        font.setFontName("宋体");
        //设置第一列加粗
        if (columnIndex == 0) {
            //字体加粗
            font.setBold(true);
        }
        CellStyle cellStyle = cell.getCellStyle();
        //在样式中应用设置的字体;
        cellStyle.setFont(font);
    }

    /**
     * @param cell  单元格对象
     * @param sheet sheet
     * @author Mr_Fei
     * @date 2020/6/26 21:15
     * @description 设置列宽
     */
    private void setColumnWidthBySheet(Cell cell, Sheet sheet) {
        int columnIndex = cell.getColumnIndex();
        int rowIndex = cell.getRowIndex();
        if (rowIndex == 1) {
            sheet.setColumnWidth(columnIndex, 6000);
        }
    }
}
