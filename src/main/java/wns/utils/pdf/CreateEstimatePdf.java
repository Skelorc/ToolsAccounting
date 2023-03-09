package wns.utils.pdf;

/*
 *@author Skelorc
 */

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import wns.aspects.LoggingAspect;
import wns.constants.EstimateSection;
import wns.entity.Estimate;
import wns.entity.ToolsEstimate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CreateEstimatePdf extends CreateBasePdf{
    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Override
    protected void createDocument(Estimate estimate) {
        try
        {
            PdfWriter.getInstance(document, new FileOutputStream("Smeta - "+estimate.getProject().getId()+"-"+estimate.getId()+ ", data -" + LocalDate.now().toString()+".pdf"));
            document.open();
            Image image = addImage();
            document.add(image);

            BaseFont baseFont = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 10, Font.NORMAL);
            Font fontHeader = new Font(baseFont, 12, Font.NORMAL);
            Font fontBold = new Font(baseFont, 10, Font.BOLD);

            PdfPTable table = createTable(50, 2);
            table.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(new Phrase(""));
            table.addCell(new Phrase(""));

            createCellForMainTables(table, font, "Проект:",  Element.ALIGN_LEFT);
            createCellForMainTables(table, font, estimate.getProject().getName(), Element.ALIGN_CENTER);
            createCellForMainTables(table, font, "Количество смен:",  Element.ALIGN_LEFT);
            createCellForMainTables(table, font, String.valueOf(estimate.getCount_shifts()), Element.ALIGN_CENTER);
            createCellForMainTables(table, font, "Съёмочный период:",  Element.ALIGN_LEFT);
            createCellForMainTables(table, font, estimate.getStart().toString()+"\n"+estimate.getEnd().toString(), Element.ALIGN_CENTER);
            createCellForMainTables(table, font, "Оператор:",  Element.ALIGN_LEFT);
            createCellForMainTables(table, font, estimate.getOperator(), Element.ALIGN_CENTER);
            createCellForMainTables(table, font, "Заказчик:",  Element.ALIGN_LEFT);
            createCellForMainTables(table, font, estimate.getProject().getClient().getFullName(), Element.ALIGN_CENTER);
            createCellForMainTables(table, font, "Менеджер:",  Element.ALIGN_LEFT);
            createCellForMainTables(table, font, estimate.getProject().getEmployee(), Element.ALIGN_CENTER);
            createCellForMainTables(table, font, "Телефон:",  Element.ALIGN_LEFT);
            createCellForMainTables(table, font, estimate.getProject().getPhoneNumber(), Element.ALIGN_CENTER);
            createCellForMainTables(table, font, "Сайт:",  Element.ALIGN_LEFT);
            createCellForMainTables(table, font, "maddogrental.pro", Element.ALIGN_CENTER);
            document.add(table);

            int[] colWidths = new int[]{2, 1, 1, 1};
            table = new PdfPTable(4);
            table.setWidths(colWidths);
            table.setSplitRows(true);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setColspan(1);

            BaseColor color = new BaseColor(240, 240, 240);


            addCenterHeader("Оборудование", fontHeader, color, table);

            addHeaderCells("Наименование", font, color, table, Element.ALIGN_LEFT);
            addHeaderCells("Стоимость (руб)", font, color, table, Element.ALIGN_CENTER);
            addHeaderCells("Количество", font, color, table, Element.ALIGN_CENTER);
            addHeaderCells("Сумма", font, color, table, Element.ALIGN_CENTER);
            Map<EstimateSection, ArrayList<ToolsEstimate>> data = new LinkedHashMap<>();
            for (int i = 0; i < estimate.getToolsEstimates().size(); i++) {
                ToolsEstimate toolsEstimate = estimate.getToolsEstimates().get(i);
                EstimateSection section = toolsEstimate.getSection();
                if(!data.containsKey(section))
                {
                    ArrayList<ToolsEstimate> list = new ArrayList<>();
                    list.add(toolsEstimate);
                    data.put(section,list);
                }
                else
                {
                    ArrayList<ToolsEstimate> toolsEstimates = data.get(estimate);
                    toolsEstimates.add(toolsEstimate);
                }
            }

            for (EstimateSection estimateSection : data.keySet()) {
                if(estimateSection!=EstimateSection.SERVICE)
                {
                    addCenterHeader(estimateSection.getValue(), fontHeader, color, table);
                    ArrayList<ToolsEstimate> toolsEstimates = data.get(estimateSection);
                    for (ToolsEstimate toolsEstimate : toolsEstimates) {
                        addSimpleCells(toolsEstimate.getName(), font, table, Element.ALIGN_LEFT);
                        addSimpleCells(String.valueOf(toolsEstimate.getPriceByDay()), font, table, Element.ALIGN_CENTER);
                        addSimpleCells(String.valueOf(toolsEstimate.getAmount()), font, table, Element.ALIGN_CENTER);
                        int sum = toolsEstimate.getAmount() * toolsEstimate.getPriceByDay() * toolsEstimate.getCountShifts();
                        addSimpleCells(String.valueOf(sum), font, table, Element.ALIGN_CENTER);
                    }
                }
            }

            addResultToolsRows("Итоговая стоимость оборудования", font, color, table);
            addResultCells("в смену:", font, color, table, Element.ALIGN_LEFT);
            addResultCells("111", font, color, table, Element.ALIGN_RIGHT);
            addResultCells("скидка (%)", font, color, table, Element.ALIGN_LEFT);
            addResultCells("222", font, color, table, Element.ALIGN_RIGHT);
            addResultCells("с учетом скидки", font, color, table, Element.ALIGN_LEFT);
            addResultCells("333", font, color, table, Element.ALIGN_RIGHT);
            addResultCells("за проект", font, color, table, Element.ALIGN_LEFT);
            addResultCells("444", fontBold, color, table, Element.ALIGN_RIGHT);



        }catch (IOException | DocumentException e) {
            logger.error("Can't find file for create");
            e.printStackTrace();
        }
    }
}
