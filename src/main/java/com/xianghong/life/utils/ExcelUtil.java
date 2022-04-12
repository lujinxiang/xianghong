package com.xianghong.life.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Quartet;
import org.springframework.boot.system.ApplicationHome;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Excel导出工具类
 */
@Slf4j
public class ExcelUtil {
    /**
     * 构造Excel并导出
     *
     * @param fileName
     * @param sheetName
     * @param titleList
     * @param dataList
     * @param response
     */
    public static void exportExcel(String fileName, String sheetName, List<ExcelTitle> titleList,
                                   List<List<String>> dataList, HttpServletResponse response) throws IOException {
        exportExcel(fileName, sheetName, titleList, dataList, null, response);
    }

    /**
     * 构造Excel并导出，支持合并单元格
     */
    public static void exportExcel(String fileName, String sheetName, List<ExcelTitle> titleList, List<List<String>> dataList,
                                   List<Quartet<Integer, Integer, Integer, Integer>> mergedRegionList, HttpServletResponse response) throws IOException {
        XSSFWorkbook wb = null;
        OutputStream output = null;
        try {
            // workbook
            wb = ExcelUtil.exportExcel(sheetName, titleList, dataList, mergedRegionList);
            // response
            response.setHeader("Content-Disposition", String.format("attachment;filename=%s", fileName));
            response.setContentType("application/vnd.ms-excel");
            // write output
            output = response.getOutputStream();
            wb.write(output);
            output.flush();
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
            } catch (IOException e) {
                log.error("[exportExcel] XSSFWorkbook close error", e);
            }
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                log.error("[exportExcel] OutputStream close error", e);
            }
        }

    }

    /**
     * 构造Excel
     *
     * @param sheetName
     * @param titleList
     * @param dataList
     * @return
     */
    private static XSSFWorkbook exportExcel(String sheetName, List<ExcelTitle> titleList, List<List<String>> dataList, List<Quartet<Integer, Integer, Integer, Integer>> mergedRegionList) {
        // excel
        XSSFWorkbook wb = new XSSFWorkbook();
        // sheet
        XSSFSheet sheet = wb.createSheet(sheetName);
        // mergedRegions
        addMergedRegions(mergedRegionList, sheet);
        // row
        int firstRow = 0;
        // row:title
        fillTitle(wb, sheet, firstRow, titleList, true);
        // row:data
        fillData(sheet, firstRow + 1, dataList);
        return wb;
    }

    private static void addMergedRegions(List<Quartet<Integer, Integer, Integer, Integer>> mergedRegionList, XSSFSheet sheet) {
        if (CollectionUtils.isEmpty(mergedRegionList)) {
            return;
        }
        for (Quartet<Integer, Integer, Integer, Integer> quartet : mergedRegionList) {
            sheet.addMergedRegion(new CellRangeAddress(quartet.getValue0(), quartet.getValue1(), quartet.getValue2(), quartet.getValue3()));
        }
    }

    /**
     * 表头
     *
     * @param wb
     * @param sheet
     * @param titleRowNum
     * @param titleList
     * @param freezePane
     */
    private static void fillTitle(XSSFWorkbook wb, XSSFSheet sheet, Integer titleRowNum,
                                  List<ExcelTitle> titleList, boolean freezePane) {
        if (CollectionUtils.isEmpty(titleList)) {
            return;
        }
        XSSFRow row = sheet.createRow(titleRowNum);
        int columnSize = titleList.size();
        for (int i = 0; i < columnSize; i++) {
            XSSFCell cell = row.createCell(i);
            ExcelTitle excelTitle = titleList.get(i);
            cell.setCellValue(excelTitle.getColumnName());
            // Aqua background
            CellStyle style = wb.createCellStyle();
            style.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
            style.setFillPattern(FillPatternType.LEAST_DOTS);
            cell.setCellStyle(style);
            // columnWidth
            Integer columnWidth = excelTitle.getColumnWidth();
            if (Objects.nonNull(columnWidth)) {
                sheet.setColumnWidth(i, columnWidth);
            }
        }
        // 冻结标题行列
        if (freezePane) {
            sheet.createFreezePane(1, titleRowNum + 1);
        }
    }

    /**
     * 数据
     *
     * @param sheet
     * @param firstDataRowNum
     * @param dataList
     */
    private static void fillData(XSSFSheet sheet, int firstDataRowNum, List<List<String>> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        for (int i = 0; i < dataList.size(); i++) {
            XSSFRow row = sheet.createRow(firstDataRowNum + i);
            List<String> datas = dataList.get(i);
            if (CollectionUtils.isEmpty(datas)) {
                continue;
            }
            for (int j = 0; j < datas.size(); j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(datas.get(j));
            }
        }
    }

    /**
     * 构造文件名
     *
     * @param fileNamePrefix
     * @return
     */
    public static String buildExportFileName(String fileNamePrefix) {
        return new StringBuilder(fileNamePrefix).append("_")
                .append(".xlsx").toString();
    }

    /**
     * 获取用户路径
     *
     * @return
     */
    public static String getUserRootPath() {
        ApplicationHome home = new ApplicationHome(ExcelUtil.class.getClass());
        return home.getDir().getAbsolutePath();
    }

    /**
     * 构造用户空间下文件
     *
     * @param userName
     * @param fileNamePrefix
     * @return
     * @throws Exception
     */
    public static String buildUserExportFileName(String userName, String fileNamePrefix) throws Exception {
        String userRootExportPath = getUserRootPath() + File.separator + userName + File.separator + "export";
        String fileName = buildExportFileName(fileNamePrefix);
        String absoluteFilePath = userRootExportPath + File.separator + fileName;
        if (log.isInfoEnabled()) {
            log.info("buildUserExportFileName {} absoluteFilePath {}", fileNamePrefix, absoluteFilePath);
        }
        return absoluteFilePath;
    }
}
