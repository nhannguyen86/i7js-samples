package com.itextpdf.samples.book.part1.chapter01;

import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.samples.GenericTest;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_01_09_HelloWorldMemory extends GenericTest {
    static public final String DEST = "./target/test/resources/book/part1/chapter01/Listing_01_09_HelloWorldMemory.pdf";

    public static void main(String args[]) throws IOException {
        new Listing_01_09_HelloWorldMemory().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        // We'll create the file in memory
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        //Add paragraph to the document
        doc.add(new Paragraph("Hello World!"));

        //Close document
        doc.close();

        // Let's write the file in memory to a file anyway
        FileOutputStream fos = new FileOutputStream(DEST);
        fos.write(baos.toByteArray());
        fos.close();
    }
}
