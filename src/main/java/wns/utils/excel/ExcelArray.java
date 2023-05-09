package wns.utils.excel;

/*
 *@author Skelorc
 */

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import wns.constants.EstimateSection;
import wns.entity.Estimate;
import wns.entity.ToolsEstimate;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class ExcelArray extends ExcelUtil{

    public void createArray(Estimate estimate) {
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


        XSSFCellStyle headerStyle = createHeaderStyle();

        Cell cell_name_project = name_project.createCell(num_cell);
        cell_name_project.setCellValue("Проект: " + estimate.getProject().getName());
        cell_name_project.setCellStyle(headerStyle);

        Cell cell_count_shifts = count_shifts.createCell(num_cell);
        cell_count_shifts.setCellValue("Количество смен: " + estimate.getCount_shifts());
        cell_count_shifts.setCellStyle(headerStyle);

        Cell cell_period = period.createCell(num_cell);
        cell_period.getRow().setHeightInPoints(cell_period.getSheet().getDefaultRowHeightInPoints() * 2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        cell_period.setCellValue("Съёмочный период: начало - " + estimate.getStart().format(formatter) + ",\nокончание - " + estimate.getEnd().format(formatter));
        cell_period.setCellStyle(headerStyle);
        sheet.autoSizeColumn(num_cell, true);

        Cell cell_operator = operator.createCell(num_cell);
        cell_operator.setCellValue("Оператор: " + estimate.getOperator());
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
        cell_site.setCellValue("Сайт: https://maddogrental.pro/");
        cell_site.setCellStyle(headerStyle);
        sheet.autoSizeColumn(num_cell);

        addHeaders(createHeaderStyle());
        addData(estimate);
        setBordersToMergedCells(sheet);

        addImageAndMergedColsForHeaders();
    }

    @Override
    void addHeaders(CellStyle style) {
        Row header = sheet.createRow(16);
        sheet.addMergedRegion(new CellRangeAddress(16, 16, 0, 5));
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Оборудование");
        headerCell.setCellStyle(style);

        sheet.addMergedRegion(new CellRangeAddress(17, 17, 0, 4));
        Row headers = sheet.createRow(17);
        Cell cellName = headers.createCell(0);
        cellName.setCellValue("Наименование");
        cellName.setCellStyle(style);

        Cell cellQuantity = headers.createCell(5);
        cellQuantity.setCellValue("Количество");
        cellQuantity.setCellStyle(style);
    }

    @Override
    void addData(Estimate estimate) {
        int count_cell = 0;
        int num_row = 18;
        Row row;
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
        Set<EstimateSection> support = new LinkedHashSet<>();
        for (EstimateSection estimateSection : sectionListMap.keySet()) {
            if (estimateSection != EstimateSection.SERVICE) {
                num_row = addRowHeaderWithData(count_cell, num_row, section_style, headerStyle, sectionListMap, estimateSection);
            }
            else
                support.add(estimateSection);
        }

        for (EstimateSection estimateSection : support) {
            num_row = addRowHeaderWithData(count_cell, num_row, section_style, headerStyle, sectionListMap, estimateSection);
        }
    }

    private int addRowHeaderWithData(int count_cell, int num_row, XSSFCellStyle section_style, XSSFCellStyle headerStyle, Map<EstimateSection, List<ToolsEstimate>> sectionListMap, EstimateSection estimateSection) {
        Row row = sheet.createRow(num_row);
        sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 5));

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
        return num_row;
    }

    @Override
    void createCellsWithData(XSSFCellStyle section_style, Row row, int count_cell, ToolsEstimate toolsEstimate) {
        Cell cell;
        sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 4));
        cell = row.createCell(count_cell);
        cell.setCellValue(toolsEstimate.getName());
        cell.setCellStyle(section_style);

        cell = row.createCell(count_cell+5);
        cell.setCellValue(toolsEstimate.getAmount());
        cell.setCellStyle(section_style);
    }
}
