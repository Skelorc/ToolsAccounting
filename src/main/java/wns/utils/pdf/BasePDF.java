package wns.utils.pdf;

/*
 *@author Skelorc
 */

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wns.aspects.LoggingAspect;
import wns.aspects.ToLog;
import wns.entity.Estimate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public abstract class BasePDF {
    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    protected Document document;
    @ToLog
    protected Image addImage(String pathToFile) {
        try {
            Image img = Image.getInstance(pathToFile + "static/img/logo/mad-dog-logo.png");
            img.scaleToFit(240, 200);
            img.setAbsolutePosition(40, 612);
            return img;
        }catch (IOException | BadElementException e) {
            logger.error("Can't find image in folder!");
            e.printStackTrace();
            throw new NullPointerException();
        }
    }


    protected void createCellForMainTables(PdfPTable table, Font font, String text, int pos) {
        Phrase p = new Phrase(text, font);
        PdfPCell cell = new PdfPCell(p);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        if (pos == 2)
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setMinimumHeight(24f);
        table.addCell(cell);
    }

    protected PdfPTable createTable(int percentage, int numCols)
    {
        PdfPTable table = new PdfPTable(numCols);
        table.setWidthPercentage(percentage);
        return table;
    }

    protected void addCenterHeader(String name, Font font, BaseColor color, PdfPTable table) {
        Phrase phrase = new Phrase(name,font);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBackgroundColor(color);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(4);
        if(name.equals("Оборудование")) {
            cell.setMinimumHeight(24f);
        }
        phrase.setFont(font);
        table.addCell(cell);
    }


    protected void addHeaderCells(String name, Font font, BaseColor color, PdfPTable table, int pos) {
        Phrase phrase = new Phrase(name, font);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBackgroundColor(color);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(pos);
        table.addCell(cell);
    }

    protected void addSimpleCells(String name, Font font, PdfPTable table, int pos) {
        Phrase phrase = new Phrase(name, font);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(pos);
        table.addCell(cell);
    }

    protected void addResultCells(String data, Font font, BaseColor color, PdfPTable table, int pos)
    {
        Phrase phrase = new Phrase(data, font);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBackgroundColor(color);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(pos);
        cell.setBackgroundColor(color);
        table.addCell(cell);
    }

    protected void addResultToolsRows(String data, Font font, BaseColor color, PdfPTable table, int sizeList) {
        Phrase phrase = new Phrase(data, font);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setRowspan(table.getRows().size()-sizeList-1);
        cell.setColspan(2);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBackgroundColor(color);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(color);
        table.addCell(cell);
    }

    public abstract File createDocument(Estimate estimate, String fileName) throws IOException, DocumentException;
}
