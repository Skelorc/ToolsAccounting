package wns.utils.excel;

import lombok.NoArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wns.constants.EstimateSection;
import wns.entity.Estimate;
import wns.entity.ToolsEstimate;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@NoArgsConstructor
public abstract class ExcelUtil {
    @Value("${filepath}")
    private String path;
    @Value("${static-files}")
    private String filePath;

    protected Workbook workbook;
    protected Sheet sheet;
    protected Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public File createFileFromWorkBook(String name) {
        if (name.contains("\"")) {
            name = name.replaceAll("\"", "");
        }
        File file_to_write = new File(path + name + ".xlsx");
        try {
            FileOutputStream stream = new FileOutputStream(file_to_write);
            workbook.write(stream);
            workbook.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_to_write;
    }

    protected void addImageAndMergedColsForHeaders() {
        File file = new File(filePath+"static/img/logo/mad-dog-logo.png");
        try (InputStream inputStream = new FileInputStream(file)) {
                byte[] inputImageBytes1 = IOUtils.toByteArray(inputStream);
                int inputImagePictureID1 = workbook.addPicture(inputImageBytes1, Workbook.PICTURE_TYPE_JPEG);
                XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
                XSSFClientAnchor logo = new XSSFClientAnchor();
                logo.setCol1(0);
                logo.setCol2(4);
                logo.setRow1(0);
                logo.setRow2(16);
                sheet.addMergedRegion(new CellRangeAddress(0, 15, 0, 3));
                for (int i = 0; i < 16; i = i + 2) {
                    sheet.addMergedRegion(new CellRangeAddress(i, i + 1, 4, 6));
                }
                drawing.createPicture(logo, inputImagePictureID1);
        } catch (Exception e) {
            System.out.println("Ошибка записи в файл!");
            e.printStackTrace();
        }
    }



    protected XSSFCellStyle createStyleForSection() {
        XSSFCellStyle section_style = (XSSFCellStyle) workbook.createCellStyle();
        section_style.setVerticalAlignment(VerticalAlignment.CENTER);
        section_style.setAlignment(HorizontalAlignment.CENTER);
        section_style.setBorderBottom(BorderStyle.MEDIUM);
        section_style.setBorderRight(BorderStyle.MEDIUM);
        section_style.setBorderLeft(BorderStyle.MEDIUM);
        section_style.setBorderTop(BorderStyle.MEDIUM);
        section_style.setWrapText(true);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Ubuntu");
        font.setFontHeightInPoints((short) 12);
        section_style.setFont(font);
        return section_style;
    }

    protected XSSFCellStyle createHeaderStyle() {
        XSSFCellStyle headerStyle =  (XSSFCellStyle) workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setWrapText(true);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Ubuntu");
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setBorderTop(BorderStyle.MEDIUM);
        return headerStyle;
    }

    protected void addCellToRow(Row row, int numCol, String data, XSSFCellStyle section_style)
    {
        Cell cell = row.createCell(numCol);
        cell.setCellValue(data);
        cell.setCellStyle(section_style);
    }

    protected void setBordersToMergedCells(Sheet sheet) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (CellRangeAddress rangeAddress : mergedRegions) {
            RegionUtil.setBorderTop(BorderStyle.MEDIUM, rangeAddress, sheet);
            RegionUtil.setBorderLeft(BorderStyle.MEDIUM, rangeAddress, sheet);
            RegionUtil.setBorderRight(BorderStyle.MEDIUM, rangeAddress, sheet);
            RegionUtil.setBorderBottom(BorderStyle.MEDIUM, rangeAddress, sheet);
        }
    }

    abstract void addHeaders(CellStyle style);
    abstract void addData(Estimate estimate);
    abstract void createCellsWithData(XSSFCellStyle section_style, Row row, int count_cell, ToolsEstimate toolsEstimate);

}
