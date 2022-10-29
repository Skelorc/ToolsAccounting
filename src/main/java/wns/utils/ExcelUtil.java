package wns.utils;

import lombok.NoArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import wns.constants.EstimateSection;
import wns.entity.Estimate;
import wns.entity.ToolsEstimate;

import java.io.*;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@NoArgsConstructor
@Component
public class ExcelUtil {
    @Autowired
    private ResourceLoader resourceLoader;
    private Workbook workbook;
    private Sheet sheet;

    public String createDocument(Estimate estimate) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(estimate.getProject().getName());
        Row name_project = sheet.createRow(0);
        Row count_shifts = sheet.createRow(2);
        Row period = sheet.createRow(4);
        Row operator = sheet.createRow(6);
        Row client = sheet.createRow(8);
        Row employee = sheet.createRow(10);
        Row phone_number = sheet.createRow(12);
        Row site = sheet.createRow(14);
        int num_cell = 5;
        addImage();

        XSSFCellStyle headerStyle = createHeaderStyle();

        Cell cell_name_project = name_project.createCell(num_cell);
        cell_name_project.setCellValue("Проект: " + estimate.getProject().getName());
        cell_name_project.setCellStyle(headerStyle);

        Cell cell_count_shifts = count_shifts.createCell(num_cell);
        cell_count_shifts.setCellValue("Количество смен: " + estimate.getCount_shifts());
        cell_count_shifts.setCellStyle(headerStyle);

        Cell cell_period = period.createCell(num_cell);
        cell_period.getRow().setHeightInPoints(cell_period.getSheet().getDefaultRowHeightInPoints() * 2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        cell_period.setCellValue("Съёмочный период: начало - " + estimate.getStart().format(formatter) + ",\nокончание - " + estimate.getEnd().format(formatter));
        cell_period.setCellStyle(headerStyle);
        sheet.autoSizeColumn(num_cell, true);

        Cell cell_operator = operator.createCell(num_cell);
        cell_operator.setCellValue("Оператор: " + estimate.getProject().getClient().getDirectorOfPhotography());
        cell_operator.setCellStyle(headerStyle);

        Cell cell_client = client.createCell(num_cell);
        cell_client.setCellValue("Клиент: " + estimate.getProject().getClient().getFullName());
        cell_client.setCellStyle(headerStyle);

        Cell cell_employee = employee.createCell(num_cell);
        cell_employee.setCellValue("Менеджер: " + estimate.getProject().getEmployee());
        cell_employee.setCellStyle(headerStyle);

        Cell cell_phone_number = phone_number.createCell(num_cell);
        cell_phone_number.setCellValue("Тел.: " + estimate.getProject().getPhoneNumber());
        cell_phone_number.setCellStyle(headerStyle);

        Cell cell_site = site.createCell(num_cell);
        cell_site.setCellValue("Сайт: maddog-rental.com");
        cell_site.setCellStyle(headerStyle);
        sheet.autoSizeColumn(num_cell, true);
        addHeaders(headerStyle);
        addData(estimate);
        setBordersToMergedCells(sheet);

        return createFile(estimate.getProject().getId() + "-" + estimate.getId());
    }



    private void setBordersToMergedCells(Sheet sheet) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (CellRangeAddress rangeAddress : mergedRegions) {
            RegionUtil.setBorderTop(BorderStyle.MEDIUM, rangeAddress, sheet);
            RegionUtil.setBorderLeft(BorderStyle.MEDIUM, rangeAddress, sheet);
            RegionUtil.setBorderRight(BorderStyle.MEDIUM, rangeAddress, sheet);
            RegionUtil.setBorderBottom(BorderStyle.MEDIUM, rangeAddress, sheet);
        }
    }
    private void addImage() {
        try (InputStream inputStream = ExcelUtil.class.getClassLoader()
                .getResourceAsStream("static/img/logo/mad-dog-logo.png")) {
            try {
                byte[] inputImageBytes1 = IOUtils.toByteArray(inputStream);
                int inputImagePictureID1 = workbook.addPicture(inputImageBytes1, Workbook.PICTURE_TYPE_PNG);
                XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
                XSSFClientAnchor logo = new XSSFClientAnchor();
                logo.setCol1(0);
                logo.setCol2(5);
                logo.setRow1(0);
                logo.setRow2(16);
                sheet.addMergedRegion(new CellRangeAddress(0, 15, 0, 4));
                for (int i = 0; i < 16; i = i + 2) {
                    sheet.addMergedRegion(new CellRangeAddress(i, i + 1, 5, 5));
                }
                drawing.createPicture(logo, inputImagePictureID1);
                inputStream.close();
            } catch (IOException e) {
                System.out.println("ошибка записи в файл");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("ошибка закрытия потока получения файла!");
            e.printStackTrace();
        }
    }

    private void addHeaders(CellStyle headerStyle) {
        XSSFCellStyle section_style = createStyleForSection();

        sheet.addMergedRegion(new CellRangeAddress(16, 16, 0, 5));
        Row tools = sheet.createRow(16);
        Cell cell_tools = tools.createCell(0);
        cell_tools.setCellStyle(headerStyle);
        cell_tools.setCellValue("Оборудование");
        for (int i = 1; i < 5; i++) {
            cell_tools = tools.createCell(i);
            cell_tools.setCellStyle(section_style);
        }

        sheet.autoSizeColumn(cell_tools.getColumnIndex());
        Row headers = sheet.createRow(17);

        Cell cell_name_tool = headers.createCell(0);
        cell_name_tool.setCellValue("Наименование");
        cell_name_tool.setCellStyle(section_style);
        sheet.autoSizeColumn(cell_name_tool.getColumnIndex());

        Cell cell_price = headers.createCell(1);
        cell_price.setCellValue("Стоимость (руб.)");
        cell_price.setCellStyle(section_style);
        sheet.autoSizeColumn(cell_price.getColumnIndex());

        Cell cell_amount = headers.createCell(2);
        cell_amount.setCellValue("Ед. Техники");
        cell_amount.setCellStyle(section_style);
        sheet.autoSizeColumn(cell_amount.getColumnIndex());

        Cell cell_days = headers.createCell(3);
        cell_days.setCellValue("Дней");
        cell_days.setCellStyle(section_style);
        sheet.autoSizeColumn(cell_days.getColumnIndex());

        Cell cell_discount = headers.createCell(4);
        cell_discount.setCellValue("Скидка (%)");
        cell_discount.setCellStyle(section_style);
        sheet.autoSizeColumn(cell_discount.getColumnIndex());

        Cell cell_sum = headers.createCell(5);
        cell_sum.setCellValue("Сумма");
        cell_sum.setCellStyle(section_style);
        sheet.autoSizeColumn(cell_sum.getColumnIndex());
    }


    private void addData(Estimate estimate) {
        int count_cell = 0;
        int num_row = 18;
        Row row = null;
        Cell cell;
        XSSFCellStyle section_style = createStyleForSection();
        XSSFCellStyle headerStyle = createHeaderStyle();
        List<ToolsEstimate> list_result = estimate.getToolsEstimates();
        Map<EstimateSection, List<ToolsEstimate>> sectionListMap = new HashMap<>();
        for (ToolsEstimate toolsEstimate : list_result) {
            EstimateSection section = toolsEstimate.getSection();
            if (!sectionListMap.containsKey(section)) {
                List<ToolsEstimate> list = new ArrayList<>();
                list.add(toolsEstimate);
                sectionListMap.put(section, list);
            } else {
                sectionListMap.get(section).add(toolsEstimate);
            }
        }
        for (EstimateSection estimateSection : sectionListMap.keySet()) {
            if (estimateSection != EstimateSection.SERVICE) {
                row = sheet.createRow(num_row);
                sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 5));

                cell = row.createCell(count_cell);
                cell.setCellValue(estimateSection.getValue());
                cell.setCellStyle(headerStyle);
                List<ToolsEstimate> list_tools = sectionListMap.get(estimateSection);
                for (int i = 0; i < list_tools.size(); i++, num_row++) {
                    ToolsEstimate toolsEstimate = list_tools.get(i);

                    row = sheet.createRow(num_row + 1);

                    createCellsWithData(section_style, row, count_cell, toolsEstimate);

                    cell = row.createCell(count_cell + 5);
                    long sum_without_procent = ((long) toolsEstimate.getAmount() * toolsEstimate.getPriceByDay() * toolsEstimate.getCountShifts());
                    long sum = Math.round(sum_without_procent - (((sum_without_procent) / 100.0) * toolsEstimate.getDiscount()));
                    cell.setCellValue(sum);
                    cell.setCellStyle(section_style);
                }
                num_row++;
            }
        }
        createResultRows(num_row,sheet,headerStyle,estimate);
    }



    private String createFile(String name) {

        if (name.contains("\"")) {
            name = name.replaceAll("\"", "");
        }
        File file_to_write = new File("estimates" + FileSystems.getDefault().getSeparator() + name + ".xlsx");
        try {
            FileOutputStream stream = new FileOutputStream(file_to_write);
            workbook.write(stream);
            workbook.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file_to_write.getName();
    }



    private void createResultRows(int num_row, Sheet sheet, XSSFCellStyle section_style,Estimate estimate)
    {
        sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 4));
        Row row = sheet.createRow(num_row);
        Cell cell = row.createCell(0);
        cell.setCellValue("Всего по проекту:");
        cell.setCellStyle(section_style);
        cell = row.createCell(5);
        cell.setCellValue(estimate.getAllByProject());
        cell.setCellStyle(section_style);
        num_row++;

        sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 4));
        row = sheet.createRow(num_row);
        cell = row.createCell(0);
        cell.setCellValue("Скидка на оборудование(%):");
        cell.setCellStyle(section_style);
        cell = row.createCell(5);
        cell.setCellValue(estimate.getDiscountByTools());
        cell.setCellStyle(section_style);
        num_row++;

        sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 4));
        row = sheet.createRow(num_row);
        cell = row.createCell(0);
        cell.setCellValue("Всего по проекту с учетом скидки:");
        cell.setCellStyle(section_style);
        cell = row.createCell(5);
        cell.setCellValue(estimate.getAllByProjectWithDiscount());
        cell.setCellStyle(section_style);
        num_row++;

        sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 5));
        row = sheet.createRow(num_row);
        cell = row.createCell(0);
        cell.setCellValue("Оборудование и транспорт");
        cell.setCellStyle(section_style);
        num_row++;


        XSSFCellStyle styleForSection = createStyleForSection();
        List<ToolsEstimate> toolsEstimates = estimate.getToolsEstimates();
        int count_cell = 0;
        for (ToolsEstimate toolsEstimate : toolsEstimates) {
            if(toolsEstimate.getSection().equals(EstimateSection.SERVICE))
            {
                row = sheet.createRow(num_row);
                createCellsWithData(styleForSection, row, count_cell, toolsEstimate);
                num_row++;
            }
        }

        sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 4));
        row = sheet.createRow(num_row);
        cell = row.createCell(0);
        cell.setCellValue("Всего по обслуживанию и транспорту:");
        cell.setCellStyle(section_style);
        cell = row.createCell(5);
        cell.setCellValue(estimate.getAllByService());
        cell.setCellStyle(section_style);
        num_row++;

        sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 4));
        row = sheet.createRow(num_row);
        cell = row.createCell(0);
        cell.setCellValue("Итоговая сумма по проекту:");
        cell.setCellStyle(section_style);
        cell = row.createCell(5);
        cell.setCellValue(estimate.getFinalSumByProject());
        cell.setCellStyle(section_style);
        num_row++;

        sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 4));
        row = sheet.createRow(num_row);
        cell = row.createCell(0);
        cell.setCellValue("Процент УСН (%):");
        cell.setCellStyle(section_style);
        cell = row.createCell(5);
        cell.setCellValue(estimate.getProcentUsn());
        cell.setCellStyle(section_style);
        num_row++;

        sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 4));
        row = sheet.createRow(num_row);
        cell = row.createCell(0);
        cell.setCellValue("Итоговая сумма УСН:");
        cell.setCellStyle(section_style);
        cell = row.createCell(5);
        cell.setCellValue(estimate.getFinalSumWithUsn());
        cell.setCellStyle(section_style);
    }

    private void createCellsWithData(XSSFCellStyle section_style, Row row, int count_cell, ToolsEstimate toolsEstimate) {
        Cell cell;
        cell = row.createCell(count_cell);
        cell.setCellValue(toolsEstimate.getName());
        cell.setCellStyle(section_style);

        cell = row.createCell(count_cell + 1);
        cell.setCellValue(toolsEstimate.getPriceByDay());
        cell.setCellStyle(section_style);

        cell = row.createCell(count_cell + 2);
        cell.setCellValue(toolsEstimate.getAmount());
        cell.setCellStyle(section_style);

        cell = row.createCell(count_cell + 3);
        cell.setCellValue(toolsEstimate.getCountShifts());
        cell.setCellStyle(section_style);

        cell = row.createCell(count_cell + 4);
        cell.setCellValue(toolsEstimate.getDiscount());
        cell.setCellStyle(section_style);

        cell = row.createCell(count_cell + 5);
        long sum_without_procent = ((long) toolsEstimate.getAmount() * toolsEstimate.getPriceByDay() * toolsEstimate.getCountShifts());
        long sum = Math.round(sum_without_procent - (((sum_without_procent) / 100.0) * toolsEstimate.getDiscount()));
        cell.setCellValue(sum);
        cell.setCellStyle(section_style);
    }

    private XSSFCellStyle createStyleForSection() {
        XSSFCellStyle section_style = (XSSFCellStyle) workbook.createCellStyle();
        section_style.setVerticalAlignment(VerticalAlignment.CENTER);
        section_style.setAlignment(HorizontalAlignment.CENTER);
        section_style.setBorderBottom(BorderStyle.MEDIUM);
        section_style.setBorderRight(BorderStyle.MEDIUM);
        section_style.setBorderLeft(BorderStyle.MEDIUM);
        section_style.setBorderTop(BorderStyle.MEDIUM);
        section_style.setWrapText(true);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Segoe UI");
        font.setFontHeightInPoints((short) 11);
        section_style.setFont(font);
        return section_style;
    }

    private XSSFCellStyle createHeaderStyle() {
        XSSFCellStyle headerStyle =  (XSSFCellStyle) workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setWrapText(true);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Segoe UI");
        font.setFontHeightInPoints((short) 12);
        headerStyle.setFont(font);
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setBorderTop(BorderStyle.MEDIUM);
        return headerStyle;
    }


}
