/*
 * This example was written by Bruno Lowagie
 * in the context of the book: iText 7 layout objects
 */
package com.itextpdf.highlevel.notused.appendix;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.border.DottedBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.hyphenation.HyphenationConfig;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author iText
 */
public class TableProperties {
    
    public static final String DEST = "results/appendix/table_properties.pdf";
    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new TableProperties().createPdf(DEST);
    }
    
    public void createPdf(String dest) throws IOException {
        // Initialize PDF writer and PDF document
        OutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdf = new PdfDocument(writer);
        
        // Initialize document
        Document document = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        Style style = new Style();
        style.setBackgroundColor(Color.YELLOW);
        document.add(createNewTable().addStyle(style).setDestination("Top").setWidth(300).setHorizontalAlignment(HorizontalAlignment.CENTER)).setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(createNewTable().setBorder(new DottedBorder(5)).setHyphenation(new HyphenationConfig("en", "uk", 3, 3)));
        document.add(createNewTable().setTextAlignment(TextAlignment.CENTER));
        document.add(ListSeparatorProperties.createNewSeparator().setMargin(10).setWidth(300).setKeepWithNext(true));
        document.add(createNewTable().setKeepTogether(true).setWidthPercent(90));
        document.add(createNewTable());
        document.add(createNewTable().setRelativePosition(10, 10, 50, 10));
        document.add(createNewTable());
        document.add(new AreaBreak());
        document.add(createNewTable().setFixedPosition(100, 400, 350).setAction(PdfAction.createGoTo("Top")));
        document.add(new AreaBreak());
        document.add(createNewTable().setBackgroundColor(Color.YELLOW).setMarginBottom(10));
        document.add(createNewTable().setBackgroundColor(Color.LIGHT_GRAY).setPaddingLeft(20).setPaddingRight(50));
        document.add(createNewTable().setBackgroundColor(Color.YELLOW));
        document.add(createNewTable().setBackgroundColor(Color.LIGHT_GRAY));
        document.add(createNewTable().setBackgroundColor(Color.YELLOW));
        document.add(createNewTable().setBackgroundColor(Color.LIGHT_GRAY).setMargin(50).setPadding(30));
        document.add(createNewTable().setBackgroundColor(Color.YELLOW));
        document.add(createNewTable().setBackgroundColor(Color.LIGHT_GRAY));
        document.close();
    }
    
    public Table createNewTable() {
        Table table = new Table(2);
        table.addCell("test1");
        table.addCell("test2");
        table.addCell("test3");
        table.addCell("test4");
        table.addCell("test5");
        table.addCell("test6");
        table.addCell("test7");
        table.addCell("This is a long text snippet that "
                + "will be used and reused to test paragraph "
                + "properties. This paragraph should take "
                + "more than one line. We'll change different "
                + "properties and then look at the effect "
                + "when we add the paragraph to the document.");
        return table;
    }
}