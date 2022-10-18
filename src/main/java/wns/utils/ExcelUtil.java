package wns.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import wns.entity.Estimate;
import wns.entity.ToolsEstimate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ExcelUtil {
    private Workbook workbook;
    private Sheet sheet;
    private CellStyle style;

    public void createDocumentAndAddHeaders(Estimate estimate, String name_file) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(name_file);

        Row name_project = sheet.createRow(0);
        Row count_shifts = sheet.createRow(1);
        Row period = sheet.createRow(2);
        Row operator = sheet.createRow(3);
        Row client = sheet.createRow(4);
        Row employee = sheet.createRow(5);
        Row phone_number = sheet.createRow(6);
        Row site = sheet.createRow(7);
        int num_cell = 9;
        addImage();
        createFile(name_file);

        Cell cell_name_project = name_project.createCell(num_cell);
        cell_name_project.setCellValue("Проект - " + estimate.getProject().getName());
        sheet.autoSizeColumn(cell_name_project.getColumnIndex());

        Cell cell_count_shifts = count_shifts.createCell(num_cell);
        cell_count_shifts.setCellValue("Количество смен - " + estimate.getCount_shifts());
        sheet.autoSizeColumn(cell_count_shifts.getColumnIndex());

        Cell cell_period = period.createCell(num_cell);
        cell_period.setCellValue("Съёмочный период: начало - " + estimate.getStart() + ", окончание - " + estimate.getEnd());
        sheet.autoSizeColumn(cell_period.getColumnIndex());

        Cell cell_operator = operator.createCell(num_cell);
        cell_operator.setCellValue("Оператор - " + estimate.getProject().getClient().getDirectorOfPhotography());
        sheet.autoSizeColumn(cell_operator.getColumnIndex());

        Cell cell_client = client.createCell(num_cell);
        cell_client.setCellValue("Клиент - " + estimate.getProject().getClient().getFullName());
        sheet.autoSizeColumn(cell_client.getColumnIndex());

        Cell cell_employee = employee.createCell(num_cell);
        cell_employee.setCellValue("Менеджер - " + estimate.getProject().getEmployee());
        sheet.autoSizeColumn(cell_employee.getColumnIndex());

        Cell cell_phone_number = phone_number.createCell(num_cell);
        cell_phone_number.setCellValue("Тел. - " + estimate.getProject().getPhoneNumber());
        sheet.autoSizeColumn(cell_phone_number.getColumnIndex());

        CellStyle style_for_site = workbook.createCellStyle();
        style_for_site.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Segoe UI");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        style_for_site.setFont(font);
        Cell cell_site = site.createCell(num_cell);
        cell_site.setCellValue("Сайт - maddog-rental.com");
        cell_site.setCellStyle(style_for_site);
        sheet.autoSizeColumn(cell_site.getColumnIndex());


    }

    public void addData(List<ToolsEstimate> list_result, String name) {
        int count_cell = 0;
        int num_row = 8;
        List<ToolsEstimate> sorted_list = list_result.stream().sorted(Comparator.comparing(o -> o.getSection().getNumber()))
                .collect(Collectors.toList());
        {
            Cell name_cell = null, date_cell = null, time_cell = null, result_cell = null;
            for (int i = 0; i < sorted_list.size(); i++,num_row++) {
                Row row = sheet.createRow(num_row);
                ToolsEstimate toolsEstimate = list_result.get(i);

               /* name_cell = row.createCell(count_cell);
                name_cell.setCellValue(result.getName_student());
                name_cell.setCellStyle(style);

                date_cell = row.createCell(count_cell + 1);
                date_cell.setCellValue(result.getDate());
                date_cell.setCellStyle(style);

                time_cell = row.createCell(count_cell + 2);
                time_cell.setCellValue(result.getTime());
                time_cell.setCellStyle(style);

                result_cell = row.createCell(count_cell + 3);
                result_cell.setCellValue(result.getValue());
                result_cell.setCellStyle(style);*/

                sheet.autoSizeColumn(name_cell.getColumnIndex());
                sheet.autoSizeColumn(date_cell.getColumnIndex());
                sheet.autoSizeColumn(time_cell.getColumnIndex());
                sheet.autoSizeColumn(result_cell.getColumnIndex());
            }
            createFile(name);
        }
    }

    private void createFile(String name) {
        File file_dir = new File("estimates");
        if (!file_dir.exists()) {
            file_dir.mkdir();
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

    private void addImage() {
        try (InputStream inputStream = ExcelUtil.class.getClassLoader()
                .getResourceAsStream("static/img/logo/mad-dog-logo.png")) {
            try {
                byte[] inputImageBytes1 = IOUtils.toByteArray(inputStream);
                int inputImagePictureID1 = workbook.addPicture(inputImageBytes1, Workbook.PICTURE_TYPE_PNG);
                XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
                XSSFClientAnchor logo = new XSSFClientAnchor();
                logo.setCol1(0);
                logo.setCol2(8);
                logo.setRow1(0);
                logo.setRow2(22);
                sheet.addMergedRegion(new CellRangeAddress(0, 23, 0, 7));
                for (int i = 0; i < 24; i=i+3) {
                    sheet.addMergedRegion(new CellRangeAddress(i, i+2, 8, 9));
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
}
