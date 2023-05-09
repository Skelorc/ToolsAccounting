package wns.utils.excel;

/*
 *@author Skelorc
 */

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
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
public class ExcelEstimate extends ExcelUtil{

    public void createEstimate(Estimate estimate) {
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
        int num_cell = 4;

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

        addHeaders(headerStyle);
        addData(estimate);
        addImageAndMergedColsForHeaders();
        setBordersToMergedCells(sheet);
    }

    protected void addData(Estimate estimate) {
        int count_cell = 0;
        int num_row = 18;

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
                sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 6));

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

        num_row = createToolsResultRows(num_row,sheet,headerStyle,estimate);

        sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 6));
        Row row = sheet.createRow(num_row);
        addCellToRow(row,0,"",null);

        num_row++;
        XSSFCellStyle styleForSection = createStyleForSection();
        List<ToolsEstimate> toolsEstimates = estimate.getToolsEstimates();
        row = sheet.createRow(num_row);

        sheet.addMergedRegion(new CellRangeAddress(num_row, num_row, 0, 6));
        Cell cell = row.createCell(count_cell);
        cell.setCellValue(EstimateSection.SERVICE.getValue());
        cell.setCellStyle(headerStyle);
        num_row++;
        for (ToolsEstimate toolsEstimate : toolsEstimates) {
            if(toolsEstimate.getSection().equals(EstimateSection.SERVICE))
            {
                row = sheet.createRow(num_row);
                createCellsWithData(styleForSection, row, count_cell, toolsEstimate);
                num_row++;
            }
        }
        createEstimateResultRows(num_row,sheet,headerStyle,estimate);
    }



    protected void createCellsWithData(XSSFCellStyle section_style, Row row, int count_cell, ToolsEstimate toolsEstimate) {
        Cell cell = row.createCell(count_cell);
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
        cell.setCellValue(toolsEstimate.getTotalByDay());
        cell.setCellStyle(section_style);

        cell = row.createCell(count_cell + 6);
        cell.setCellValue(toolsEstimate.getTotalByDayWithDiscount());
        cell.setCellStyle(section_style);
    }

    protected void addHeaders(CellStyle headerStyle) {
        XSSFCellStyle section_style = createStyleForSection();

        sheet.addMergedRegion(new CellRangeAddress(16, 16, 0, 6));
        Row tools = sheet.createRow(16);
        Cell cell_tools = tools.createCell(0);
        cell_tools.setCellStyle(headerStyle);
        cell_tools.setCellValue("Оборудование");
        for (int i = 1; i < 6; i++) {
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
        cell_sum.setCellValue("Итого за смену");
        cell_sum.setCellStyle(section_style);
        sheet.autoSizeColumn(cell_sum.getColumnIndex());

        Cell cell_result = headers.createCell(6);
        cell_result.setCellValue("Итого за смену с учетом скидки");
        cell_result.setCellStyle(section_style);
        sheet.autoSizeColumn(cell_result.getColumnIndex());
    }

    private int createToolsResultRows(int num_row, Sheet sheet, XSSFCellStyle headerStyle, Estimate estimate) {
        int lastCol = 4;
        sheet.addMergedRegion(new CellRangeAddress(num_row, num_row+3, 0, lastCol));
        Row row = sheet.createRow(num_row);
        addCellToRow(row,0,"Итоговая стоимость оборудования",headerStyle);
        for (int i = num_row; i <= num_row+3; i++) {
            sheet.addMergedRegion(new CellRangeAddress(i, i, 5, 6));
        }
        addCellToRow(row,5,"В смену : " + estimate.getResultByToolsInShift(),headerStyle);
        addCellToRow(sheet.createRow(num_row+1),5,"Скидка (%) : " + estimate.getDiscountByTools(),headerStyle);
        addCellToRow(sheet.createRow(num_row+2),5,"С учетом скидки : " + estimate.getResultByToolsWithDiscount(),headerStyle);
        addCellToRow(sheet.createRow(num_row+3),5,"За проект : " + estimate.getTotalByTools(),headerStyle);
        return num_row + 4;
    }

    private void createEstimateResultRows(int num_row, Sheet sheet, XSSFCellStyle section_style, Estimate estimate)
    {
        int lastCol = 4;
        int rowResultService = num_row + 1;
        sheet.addMergedRegion(new CellRangeAddress(num_row, rowResultService, 0, lastCol));
        Row row = sheet.createRow(num_row);
        addCellToRow(row,0,"Итоговая стоимость обслуживания",section_style);
        for (int i = num_row; i <= rowResultService; i++) {
            sheet.addMergedRegion(new CellRangeAddress(i, i, 5, 6));
        }
        addCellToRow(row,5,"В смену : " + estimate.getResultByServiceInShift(),section_style);
        addCellToRow(sheet.createRow(num_row+1),5,"За проект :" + estimate.getTotalByService(),section_style);

        int rowTotalResult = rowResultService + 3;
        sheet.addMergedRegion(new CellRangeAddress(rowResultService+1, rowTotalResult, 0, lastCol));
        row = sheet.createRow(rowResultService+1);
        addCellToRow(row,0,"Общая стоимость",section_style);
        for (int i = rowResultService+1; i <= rowTotalResult; i++) {
            sheet.addMergedRegion(new CellRangeAddress(i, i, 5, 6));
        }
        addCellToRow(row,5,"За проект : "+estimate.getTotalByProject(),section_style);
        addCellToRow(sheet.createRow(rowResultService+2),5,"Процент УСН : "+estimate.getProcentUsn(),section_style);
        addCellToRow(sheet.createRow(rowResultService+3),5,"При оплате по УСН : "+estimate.getFinalTotalWithUsn(),section_style);
    }
}
