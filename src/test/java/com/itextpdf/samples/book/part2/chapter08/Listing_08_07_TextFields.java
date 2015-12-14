package com.itextpdf.samples.book.part2.chapter08;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.color.Color;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.renderer.CellRenderer;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;
import java.util.Map;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore("Document fonts")
@Category(SampleTest.class)
public class Listing_08_07_TextFields extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part2/chapter08/Listing_08_07_TextFields.pdf";
    public static final String FILLED = "./target/test/resources/book/part2/chapter08/Listing_08_07_TextFields_filled.pdf";

    public static void main(String[] args) throws Exception {
        new Listing_08_07_TextFields().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        createPdf(DEST);
        fillPdf(DEST, FILLED);
    }

    public void createPdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);

        Cell cell;
        Table table = new Table(new float[]{ 1, 2 });
        table.setWidthPercent(80);

        table.addCell(new Cell().add(new Paragraph("Name:")));
        cell = new Cell();
        cell.setNextRenderer(new TextFilledCellRenderer(cell, 1));
        table.addCell(cell);

        table.addCell(new Cell().add(new Paragraph("Loginname:")));

        cell = new Cell();
        cell.setNextRenderer(new TextFilledCellRenderer(cell, 2));
        table.addCell(cell);

        table.addCell(new Cell().add(new Paragraph("Password:")));

        cell = new Cell();
        cell.setNextRenderer(new TextFilledCellRenderer(cell, 3));

        table.addCell(cell);

        table.addCell(new Cell().add(new Paragraph("Reason:")));

        cell = new Cell();
        cell.setNextRenderer(new TextFilledCellRenderer(cell, 4));

        cell.setHeight(60);
        table.addCell(cell);

        doc.add(table);

        doc.close();
    }

    public void fillPdf(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        Map<String, PdfFormField> fields = form.getFormFields();

        fields.get("text_1").setValue("Bruno Lowagie");

        fields.get("text_2").setFieldFlags(0);
        fields.get("text_2").setBorderColor(Color.RED);
        fields.get("text_2").setValue("bruno");

        fields.get("text_3").setFieldFlag(PdfFormField.FF_PASSWORD, false);
        // TODO MO FLAGS_PRINT constant
        // form.setFieldProperty("text_3", "setflags", PdfAnnotation.FLAGS_PRINT, null);
        form.getField("text_3").setValue("12345678", "********");
        ((PdfTextFormField) form.getField("text_4")).setMaxLen(12);
        form.getField("text_4").regenerateField();
        pdfDoc.close();
    }


    protected class TextFilledCellRenderer extends CellRenderer {
        /** The text field index of a TextField that needs to be added to a cell. */
        protected int tf = 0;

        public TextFilledCellRenderer(Cell modelElement, int tf) {
            super(modelElement);
            this.tf = tf;
        }

        @Override
        public void draw(PdfDocument document, PdfCanvas canvas) {
            super.draw(document, canvas);
            PdfTextFormField text = PdfFormField.createText(document, getOccupiedAreaBBox(),
                    "Enter your name here...", String.format("text_%s", tf));
            // TODO DEVSIX-233
            //text.setBackgroundColor(new DeviceGray(0.75f));
            PdfDictionary borderStyleDict = new PdfDictionary();
            switch(tf) {
                case 1:
                    borderStyleDict.put(PdfName.S, PdfName.B);
                    text.getWidgets().get(0).setBorderStyle(borderStyleDict);
                    // TODO DEVSIX-233
                    // text.setFontSize(10);
                    // text.setValue("Enter your name here...");
                    text.setRequired(true);
                    // TODO No setAlignment
                    // text.setAlignment(Element.ALIGN_CENTER);
                    break;
                case 2:
                    borderStyleDict.put(PdfName.S, PdfName.S);
                    text.getWidgets().get(0).setBorderStyle(borderStyleDict);
                    text.setMaxLen(8);
                    text.setComb(true);
                    text.setBorderWidth(2);
                    break;
                case 3:
                    borderStyleDict.put(PdfName.S, PdfName.I);
                    text.getWidgets().get(0).setBorderStyle(borderStyleDict);
                    text.setPassword(true);
                    // TODO DEVSIX-233
                    // text.setValue("Choose a password");
                    text.setVisibility(PdfFormField.VISIBLE_BUT_DOES_NOT_PRINT);
                    break;
                case 4:
                    borderStyleDict.put(PdfName.S, PdfName.D);
                    text.getWidgets().get(0).setBorderStyle(borderStyleDict);
                    // TODO DEVSIX-233
                    // text.setBorderColor(Color.RED);
                    // text.setFontSize(8);
                    //  text.setValue("Enter the reason why you want to win a free " +
                    //          "accreditation for the Foobar Film Festival");
                    text.setBorderWidth(2);
                    text.setMultiline(true);
                    text.setRequired(true);
                    break;
            }
            PdfAcroForm.getAcroForm(document, true).addField(text);
        }
    }
}