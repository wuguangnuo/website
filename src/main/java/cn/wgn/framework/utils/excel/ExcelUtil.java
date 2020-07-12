package cn.wgn.framework.utils.excel;


import cn.wgn.framework.utils.DateUtil;
import cn.wgn.framework.utils.excel.ExcelData;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;

/**
 * Excel 工具类
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/26 21:58
 */
@Component
public class ExcelUtil {
    /**
     * 导出表格
     *
     * @param response    http response
     * @param fileName    file name
     * @param excelName   excel name
     * @param excelTitles excel titles
     * @param data        excel data
     * @param <T>         class
     * @throws Exception
     */
    public static <T> void exportExcel(HttpServletResponse response, String fileName, String excelName, String excelTitles, List<T> data)
            throws IOException, IllegalAccessException {
        ExcelData excelData = new ExcelData();
        excelData.setName(excelName);

        List<String> titles = Arrays.asList(excelTitles.split(","));
        excelData.setTitles(titles);

        List<List<Object>> rows = new ArrayList<>();
        List<Object> row;
        if (data.size() != 0) {
            Field[] fields = data.get(0).getClass().getDeclaredFields();

            for (T datum : data) {
                row = new ArrayList<>();
                for (Field field : fields) {
                    field.setAccessible(true);

                    if (field.get(datum) == null) {
                        row.add("");
                    } else if ("java.time.LocalDateTime".equals(field.getType().getName())) {
                        row.add(field.get(datum).toString().replace('T', ' ').substring(0, 16));
                    } else if ("java.lang.Boolean".equals(field.getType().getName())) {
                        row.add((Boolean) field.get(datum) ? "是" : "否");
                    } else {
                        row.add(field.get(datum).toString());
                    }
                }
                rows.add(row);
            }
        }

        String[] rowArr = new String[excelData.getTitles().size()];
        rowArr[0] = "表格生成时间：" + cn.wgn.framework.utils.DateUtil.dateFormat(null, cn.wgn.framework.utils.DateUtil.DATE_TIME_PATTERN);
        rows.add(Arrays.asList(rowArr));
        excelData.setRows(rows);

        exportExcel(response, fileName, excelData);

    }

    /**
     * 导出表格
     *
     * @param response http response
     * @param fileName file name
     * @param data     excel data
     * @throws Exception
     */
    public static void exportExcel(HttpServletResponse response, String fileName, ExcelData data) throws IOException {
        fileName += DateUtil.dateFormat(null, "yyyyMMddHHmmss") + ".xlsx";
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        exportExcel(data, response.getOutputStream());
    }

    public static void exportExcel(ExcelData data, OutputStream out) throws IOException {

        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            String sheetName = data.getName();
            if (null == sheetName) {
                sheetName = "Sheet1";
            }
            XSSFSheet sheet = wb.createSheet(sheetName);
            writeExcel(wb, sheet, data);

            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 此处需要关闭 wb 变量
            out.close();
        }
    }

    private static void writeExcel(XSSFWorkbook wb, Sheet sheet, ExcelData data) {

        int rowIndex = 0;

        rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());
        writeRowsToExcel(wb, sheet, data.getRows(), rowIndex);
        writeTimestampToExcel(wb, sheet, data);
        autoSizeColumns(sheet, data.getTitles().size() + 1);

    }

    private static int writeTitlesToExcel(XSSFWorkbook wb, Sheet sheet, List<String> titles) {
        int rowIndex = 0;
        int colIndex = 0;

        Font titleFont = wb.createFont();
        titleFont.setFontName("simsun");
        // titleFont.setBoldweight(Short.MAX_VALUE);
        // titleFont.setFontHeightInPoints((short) 14);
        titleFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillForegroundColor(new XSSFColor(new Color(182, 184, 192), new DefaultIndexedColorMap()));
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleStyle.setFont(titleFont);
        setBorder(titleStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0), new DefaultIndexedColorMap()));

        Row titleRow = sheet.createRow(rowIndex);
        // titleRow.setHeightInPoints(25);
        colIndex = 0;

        for (String field : titles) {
            Cell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            cell.setCellStyle(titleStyle);
            colIndex++;
        }

        rowIndex++;
        return rowIndex;
    }

    private static int writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<List<Object>> rows, int rowIndex) {
        int colIndex = 0;

        Font dataFont = wb.createFont();
        dataFont.setFontName("simsun");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);

        XSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setFont(dataFont);
        setBorder(dataStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0), new DefaultIndexedColorMap()));

        for (List<Object> rowData : rows) {
            Row dataRow = sheet.createRow(rowIndex);
            // dataRow.setHeightInPoints(25);
            colIndex = 0;

            for (Object cellData : rowData) {
                Cell cell = dataRow.createCell(colIndex);
                if (cellData != null) {
                    cell.setCellValue(cellData.toString());
                } else {
                    cell.setCellValue("");
                }

                cell.setCellStyle(dataStyle);
                colIndex++;
            }
            rowIndex++;
        }
        return rowIndex;
    }

    private static void writeTimestampToExcel(XSSFWorkbook wb, Sheet sheet, ExcelData data) {
//        XSSFCellStyle cellStyle = wb.createCellStyle();
//        cellStyle.setAlignment(HorizontalAlignment.RIGHT);
//        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        setBorder(cellStyle, BorderStyle.THIN, new XSSFColor(new Color(0, 0, 0), new DefaultIndexedColorMap()));
//
//        sheet.createRow(data.getRows().size()).createCell(0).setCellStyle(cellStyle);

        CellRangeAddress region = new CellRangeAddress(data.getRows().size(), data.getRows().size(), 0, data.getTitles().size() - 1);
        sheet.addMergedRegion(region);
    }

    private static void autoSizeColumns(Sheet sheet, int columnNumber) {

        for (int i = 0; i < columnNumber; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = (int) (sheet.getColumnWidth(i) + 100);
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }

    private static void setBorder(XSSFCellStyle style, BorderStyle border, XSSFColor color) {
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
        style.setBorderBottom(border);
        style.setBorderColor(BorderSide.TOP, color);
        style.setBorderColor(BorderSide.LEFT, color);
        style.setBorderColor(BorderSide.RIGHT, color);
        style.setBorderColor(BorderSide.BOTTOM, color);
    }
}