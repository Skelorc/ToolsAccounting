package wns.utils.excel;

/*
 *@author Skelorc
 */

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import wns.constants.EstimateSection;
import wns.entity.Estimate;
import wns.entity.ToolsEstimate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelShipment extends ExcelUtil{

    public void createShipment(Estimate estimate)
    {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Отгрузка");
        for (int i = 0; i < 8; i++) {
            sheet.addMergedRegion(new CellRangeAddress(i, i, 1, 3));
        }
        Row name_project = sheet.createRow(0);
        Row count_shifts = sheet.createRow(1);
        Row period = sheet.createRow(2);
        Row operator = sheet.createRow(3);
        Row client = sheet.createRow(4);
        Row employee = sheet.createRow(5);
        Row phone_number = sheet.createRow(6);
        Row site = sheet.createRow(7);
        int num_cell_key = 0;
        int num_cell_value = 1;

        XSSFCellStyle headerStyle =  (XSSFCellStyle) workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setWrapText(true);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Segoe UI");
        font.setFontHeightInPoints((short) 14);
        headerStyle.setFont(font);

        Cell cell_name_project = name_project.createCell(num_cell_key);
        cell_name_project.setCellValue("Проект: ");
        cell_name_project.setCellStyle(headerStyle);
        cell_name_project = name_project.createCell(num_cell_value);
        cell_name_project.setCellValue(estimate.getProject().getName());
        cell_name_project.setCellStyle(headerStyle);

        Cell cell_count_shifts = count_shifts.createCell(num_cell_key);
        cell_count_shifts.setCellValue("Количество смен: ");
        cell_count_shifts.setCellStyle(headerStyle);
        cell_count_shifts = count_shifts.createCell(num_cell_value);
        cell_count_shifts.setCellValue(estimate.getCount_shifts());
        cell_count_shifts.setCellStyle(headerStyle);

        Cell cell_period = period.createCell(num_cell_key);
        cell_period.getRow().setHeightInPoints(cell_period.getSheet().getDefaultRowHeightInPoints() * 2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        cell_period.setCellValue("Съёмочный период: ");
        cell_period.setCellStyle(headerStyle);
        cell_period = period.createCell(num_cell_value);
        cell_period.getRow().setHeightInPoints(cell_period.getSheet().getDefaultRowHeightInPoints() * 2);
        cell_period.setCellValue("начало - " + estimate.getStart().format(formatter) + ",\nокончание - " + estimate.getEnd().format(formatter));
        cell_period.setCellStyle(headerStyle);
        sheet.autoSizeColumn(num_cell_key, true);

        Cell cell_operator = operator.createCell(num_cell_key);
        cell_operator.setCellValue("Оператор: ");
        cell_operator.setCellStyle(headerStyle);
        cell_operator = operator.createCell(num_cell_value);
        cell_operator.setCellValue(estimate.getOperator());
        cell_operator.setCellStyle(headerStyle);

        Cell cell_client = client.createCell(num_cell_key);
        cell_client.setCellValue("Клиент: ");
        cell_client.setCellStyle(headerStyle);
        cell_client = client.createCell(num_cell_value);
        cell_client.setCellValue(estimate.getProject().getClient().getFullName());
        cell_client.setCellStyle(headerStyle);

        Cell cell_employee = employee.createCell(num_cell_key);
        cell_employee.setCellValue("Менеджер: ");
        cell_employee.setCellStyle(headerStyle);
        cell_employee = employee.createCell(num_cell_value);
        cell_employee.setCellValue( estimate.getProject().getEmployee());
        cell_employee.setCellStyle(headerStyle);

        Cell cell_phone_number = phone_number.createCell(num_cell_key);
        cell_phone_number.setCellValue("Тел.: ");
        cell_phone_number.setCellStyle(headerStyle);
        cell_phone_number = phone_number.createCell(num_cell_value);
        cell_phone_number.setCellValue(estimate.getProject().getPhoneNumber());
        cell_phone_number.setCellStyle(headerStyle);

        Cell cell_site = site.createCell(num_cell_key);
        cell_site.setCellValue("Сайт:");
        cell_site.setCellStyle(headerStyle);
        cell_site = site.createCell(num_cell_value);
        cell_site.setCellValue("https://maddogrental.pro/");
        cell_site.setCellStyle(headerStyle);
        sheet.autoSizeColumn(num_cell_key);

        addHeaders(createHeaderStyle());
        addData(estimate);
        setBordersToMergedCells(sheet);
    }


    protected void addData(Estimate estimate) {
        int count_cell = 0;
        int num_row = 9;
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
                Row row = sheet.createRow(num_row);
                sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 3));

                Cell cell = row.createCell(count_cell);
                cell.setCellValue(estimateSection.getValue());
                cell.setCellStyle(headerStyle);
                List<ToolsEstimate> list_tools = sectionListMap.get(estimateSection);
                for (int i = 0; i < list_tools.size(); i++, num_row++) {
                    ToolsEstimate toolsEstimate = list_tools.get(i);

                    row = sheet.createRow(num_row + 1);

                    createCellsWithData(section_style, row, count_cell, toolsEstimate);
                }
                num_row++;
            }
        }
    }

    protected void createCellsWithData(XSSFCellStyle section_style, Row row, int count_cell, ToolsEstimate toolsEstimate)
    {
        Cell cell = row.createCell(count_cell);
        cell.setCellValue(toolsEstimate.getName());
        cell.setCellStyle(section_style);

        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 1, 2));
        cell = row.createCell(count_cell + 1);
        cell.setCellValue(toolsEstimate.getAmount());
        cell.setCellStyle(section_style);

        cell = row.createCell(count_cell + 3);
        cell.setCellStyle(section_style);
    }

    protected void addHeaders(CellStyle style) {
        Row headers = sheet.createRow(8);

        Cell cellName = headers.createCell(0);
        cellName.setCellValue("Наименование");
        cellName.setCellStyle(style);

        sheet.addMergedRegion(new CellRangeAddress(8, 8, 1, 2));
        Cell cellQuantity = headers.createCell(1);
        cellQuantity.setCellValue("Количество");
        cellQuantity.setCellStyle(style);

        Cell cellNote = headers.createCell(3);
        cellNote.setCellValue("Примечание");
        cellNote.setCellStyle(style);
    }
}
