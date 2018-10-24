package com.talabaty.swever.admin.Mabi3at;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

import static com.itextpdf.text.html.HtmlTags.FONT;

public class TestPdf {
    public static final String ARABIC = "الالالال";

    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Font f = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Phrase p = new Phrase("This is incorrect: ");
        p.add(new Chunk(ARABIC, f));
        p.add(new Chunk(": 50.00 USD"));
        document.add(p);

        p = new Phrase("This is correct: ");
        p.add(new Chunk(ARABIC, f));
        p.add(new Phrase(": 50.00"));

        ColumnText canvas = new ColumnText(writer.getDirectContent());
        canvas.setSimpleColumn(36, 750, 559, 780);
        canvas.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
        canvas.addElement(p);
        canvas.go();

        document.close();
    }
}
