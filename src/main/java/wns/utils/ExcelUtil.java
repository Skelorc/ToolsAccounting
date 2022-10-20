package wns.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;
import wns.constants.EstimateSection;
import wns.entity.Estimate;
import wns.entity.ToolsEstimate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Component
public class ExcelUtil {
    private Workbook workbook;
    private Sheet sheet;

    public void createDocument(Estimate estimate, String name_file) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(name_file);
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

        XSSFCellStyle headerStyle = (XSSFCellStyle) workbook.createCellStyle();
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

        Cell cell_name_project = name_project.createCell(num_cell);
        cell_name_project.setCellValue("Проект: " + estimate.getProject().getName());
        cell_name_project.setCellStyle(headerStyle);

        Cell cell_count_shifts = count_shifts.createCell(num_cell);
        cell_count_shifts.setCellValue("Количество смен: " + estimate.getCount_shifts());
        cell_count_shifts.setCellStyle(headerStyle);

        Cell cell_period = period.createCell(num_cell);
        cell_period.getRow().setHeightInPoints(cell_period.getSheet().getDefaultRowHeightInPoints() * 2);
        cell_period.setCellValue("Съёмочный период: начало - " + estimate.getStart() + ",\nокончание - " + estimate.getEnd());
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

        font.setBold(true);
        Cell cell_site = site.createCell(num_cell);
        cell_site.setCellValue("Сайт: maddog-rental.com");
        cell_site.setCellStyle(headerStyle);
        sheet.autoSizeColumn(num_cell, true);
        addHeaders(headerStyle);
        addData(estimate.getToolsEstimates(), estimate.getProject().getName());
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
        cell_amount.setCellValue("Количество");
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


    private void addData(List<ToolsEstimate> list_result, String name) {
        int count_cell = 0;
        int num_row = 18;
        XSSFCellStyle section_style = createStyleForSection();
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
                Row row = sheet.createRow(num_row);
                sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 5));

                Cell cell = row.createCell(count_cell);
                cell.setCellValue(estimateSection.getValue());
                cell.setCellStyle(section_style);
                List<ToolsEstimate> list_tools = sectionListMap.get(estimateSection);
                for (int i = 0; i < list_tools.size(); i++, num_row++) {
                    ToolsEstimate toolsEstimate = list_tools.get(i);

                    row = sheet.createRow(num_row + 1);

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
                num_row++;
            }
            else {

            }
        }

        createFile(name);
    }

    private void createFile(String name) {
        File file_dir = new File("estimates");
        if (!file_dir.exists()) {
            file_dir.mkdir();
        }
        if (name.contains("\"")) {
            name = name.replaceAll("\"", "");
        }
        File file_to_write = new File("estimates/" + name + ".xlsx");
        try {
            FileOutputStream stream = new FileOutputStream(file_to_write);
            workbook.write(stream);
            workbook.close();
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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


}
