package com.talabaty.swever.admin.Mabi3at;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.talabaty.swever.admin.AgentReports.Agent;
import com.talabaty.swever.admin.Mabi3at.DoneTalabat.Talabat;
import com.talabaty.swever.admin.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Methods {

//    ResultDatabase resultDatabase;
//    Cursor cursor1, cursor2;
    Context context;
    //Todo: Change Logo in Pdf
    public Methods(Context context )  {
        this.context = context;
//        try {
//            new TestPdf().createPdf("لتابلتابتا");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
    }

    public boolean writeAgent(String fname, java.util.List<Agent> talabatList){
        String time = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Calendar.getInstance().getTime());
        String fpath = "/sdcard/" + "Talabaty "+time + ".pdf";
        File file = new File(fpath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img = null;
        byte[] byteArray = stream.toByteArray();
        try {
            img = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        Document document = new Document();
        PdfPTable header = new PdfPTable(new float[] { 2, 2, 2 });
        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(timeStamp);
        header.addCell(img);
        header.addCell("طلباتى");

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(" م ");//إجمالى الطلبات	| اسم العميل	| م
        table.addCell(" أسم العميل ");
        table.addCell(" إجمالى الطلبات ");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.BLUE);
        }
        for (int i=1;i<talabatList.size();i++){
            table.addCell(talabatList.get(i).getNum()+"");
            table.addCell(talabatList.get(i).getName());
            table.addCell(talabatList.get(i).getNum()+"");
        }
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();
        System.out.println("Done");
        return true;
    }

    public boolean writeDoneTalabt(String fname, java.util.List<Talabat> talabatList){

        String time = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Calendar.getInstance().getTime());
        String fpath = "/sdcard/" + "Talabaty "+time + ".pdf";
        File file = new File(fpath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img = null;
        byte[] byteArray = stream.toByteArray();
        try {
            img = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document document = new Document();
        PdfPTable header = new PdfPTable(new float[] { 2, 2, 2 });
        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(timeStamp);
        header.addCell(img);
        header.addCell("طلباتى");

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(" م ");//"| تاريخ التسليم\t| وقت التسليم\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م
        table.addCell(" تاريخ ");//"| تاريخ التسليم\t| وقت التسليم\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م
        table.addCell(" وقت ");//"| تاريخ التسليم\t| وقت التسليم\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م
        table.addCell(" التليفون ");//"| تاريخ التسليم\t| وقت التسليم\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م
        table.addCell(" أسم العميل ");
        table.addCell(" وقت التسليم ");
        table.addCell(" تاريخ التسليم ");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.BLUE);
        }
        for (int i=1;i<talabatList.size();i++){
            table.addCell(talabatList.get(i).getNum()+"");
            table.addCell(talabatList.get(i).getEstlam_date());
            table.addCell(talabatList.get(i).getEstlam_time()+"");
            table.addCell(talabatList.get(i).getPhone()+"");
            table.addCell(talabatList.get(i).getName()+"");
            table.addCell(talabatList.get(i).getTasleem_time()+"");
            table.addCell(talabatList.get(i).getTasleem_date()+"");
        }
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        System.out.println("Done");
        return true;
    }

    public boolean writeSailedTalabt(String fname, java.util.List<Talabat> talabatList){

        String time = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Calendar.getInstance().getTime());
        String fpath = "/sdcard/" + "Talabaty "+time + ".pdf";
        File file = new File(fpath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img = null;
        byte[] byteArray = stream.toByteArray();
        try {
            img = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document document = new Document();
        PdfPTable header = new PdfPTable(new float[] { 2, 2, 2 });
        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(timeStamp);
        header.addCell(img);
        header.addCell("طلباتى");

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(" م ");//"| تاريخ التسليم\t| وقت التسليم\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م
        table.addCell(" تاريخ ");//"| تاريخ التسليم\t| وقت التسليم\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م
        table.addCell(" وقت ");//"| تاريخ التسليم\t| وقت التسليم\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م
        table.addCell(" التليفون ");//"| تاريخ التسليم\t| وقت التسليم\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م
        table.addCell(" أسم العميل ");
        table.addCell(" وقت التسليم ");
        table.addCell(" تاريخ التسليم ");
        table.addCell(" الإجمالى ");
        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.BLUE);
        }
        for (int i=1;i<talabatList.size();i++){
            table.addCell(talabatList.get(i).getNum()+"");
            table.addCell(talabatList.get(i).getEstlam_date());
            table.addCell(talabatList.get(i).getEstlam_time()+"");
            table.addCell(talabatList.get(i).getPhone()+"");
            table.addCell(talabatList.get(i).getName()+"");
            table.addCell(talabatList.get(i).getTasleem_time()+"");
            table.addCell(talabatList.get(i).getTasleem_date()+"");
            table.addCell(talabatList.get(i).getTotal()+"");
        }
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        System.out.println("Done");
        return true;
    }

    public boolean writeNewTalabt(String fname, java.util.List<Talabat> talabatList){

        String time = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Calendar.getInstance().getTime());
        String fpath = "/sdcard/" + "Talabaty "+time + ".pdf";
        File file = new File(fpath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img = null;
        byte[] byteArray = stream.toByteArray();
        try {
            img = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document document = new Document();
        PdfPTable header = new PdfPTable(new float[] { 2, 2, 2 });
        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(timeStamp);
        header.addCell(img);
        header.addCell("طلباتى");

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(" م ");//"
        table.addCell(" تاريخ ");//
        table.addCell(" وقت ");//"
        table.addCell(" التليفون ");//| العنوان	| أسم العميل	| التليفون	| وقت	| تاريخ	| م  |
        table.addCell(" أسم العميل ");
        table.addCell(" العنوان ");

        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.BLUE);
        }
        for (int i=1;i<talabatList.size();i++){
            table.addCell(talabatList.get(i).getNum()+"");
            table.addCell(talabatList.get(i).getEstlam_date());
            table.addCell(talabatList.get(i).getEstlam_time()+"");
            table.addCell(talabatList.get(i).getPhone()+"");
            table.addCell(talabatList.get(i).getName()+"");
            table.addCell(talabatList.get(i).getAddress()+"");
        }
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        System.out.println("Done");
        return true;
    }

    public boolean writeNotiTalabt(String fname, java.util.List<Talabat> talabatList){

        String time = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Calendar.getInstance().getTime());
        String fpath = "/sdcard/" + "Talabaty "+time + ".pdf";
        File file = new File(fpath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img = null;
        byte[] byteArray = stream.toByteArray();
        try {
            img = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document document = new Document();
        PdfPTable header = new PdfPTable(new float[] { 2, 2, 2 });
        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(timeStamp);
        header.addCell(img);
        header.addCell("طلباتى");

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(" م ");//"
        table.addCell(" تاريخ ");//
        table.addCell(" وقت ");//"
        table.addCell(" التليفون ");//| العنوان	| أسم العميل	| التليفون	| وقت	| تاريخ	| م  |
        table.addCell(" أسم العميل ");
        table.addCell(" العنوان ");

        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.BLUE);
        }
        for (int i=1;i<talabatList.size();i++){
            table.addCell(talabatList.get(i).getNum()+"");
            table.addCell(talabatList.get(i).getEstlam_date());
            table.addCell(talabatList.get(i).getEstlam_time()+"");
            table.addCell(talabatList.get(i).getPhone()+"");
            table.addCell(talabatList.get(i).getName()+"");
            table.addCell(talabatList.get(i).getAddress()+"");
        }
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        System.out.println("Done");
        return true;
    }

    public boolean writeMyTasks(String fname, java.util.List<Talabat> talabatList){

        String time = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Calendar.getInstance().getTime());
        String fpath = "/sdcard/" + "Talabaty "+time + ".pdf";
        File file = new File(fpath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img = null;
        byte[] byteArray = stream.toByteArray();
        try {
            img = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document document = new Document();
        PdfPTable header = new PdfPTable(new float[] { 2, 2, 2 });
        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(timeStamp);
        header.addCell(img);
        header.addCell("طلباتى");

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(" م ");//"
        table.addCell(" تاريخ ");//
        table.addCell(" وقت ");//"
        table.addCell(" التليفون ");//| العنوان	| أسم العميل	| التليفون	| وقت	| تاريخ	| م  |
        table.addCell(" أسم العميل ");
        table.addCell(" العنوان ");

        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.BLUE);
        }
        for (int i=1;i<talabatList.size();i++){
            table.addCell(talabatList.get(i).getNum()+"");
            table.addCell(talabatList.get(i).getEstlam_date());
            table.addCell(talabatList.get(i).getEstlam_time()+"");
            table.addCell(talabatList.get(i).getPhone()+"");
            table.addCell(talabatList.get(i).getName()+"");
            table.addCell(talabatList.get(i).getAddress()+"");
        }
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        System.out.println("Done");
        return true;
    }

    public boolean writeRetuTalabt(String fname, java.util.List<Talabat> talabatList){

        String time = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Calendar.getInstance().getTime());
        String fpath = "/sdcard/" + "Talabaty "+time + ".pdf";
        File file = new File(fpath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img = null;
        byte[] byteArray = stream.toByteArray();
        try {
            img = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document document = new Document();
        PdfPTable header = new PdfPTable(new float[] { 2, 2, 2 });
        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(timeStamp);
        header.addCell(img);
        header.addCell("طلباتى");

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(" م ");//"
        table.addCell(" تاريخ ");//
        table.addCell(" وقت ");//"
        table.addCell(" التليفون ");//| العنوان	| أسم العميل	| التليفون	| وقت	| تاريخ	| م  |
        table.addCell(" أسم العميل ");
        table.addCell(" العنوان ");

        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.BLUE);
        }
        for (int i=1;i<talabatList.size();i++){
            table.addCell(talabatList.get(i).getNum()+"");
            table.addCell(talabatList.get(i).getEstlam_date());
            table.addCell(talabatList.get(i).getEstlam_time()+"");
            table.addCell(talabatList.get(i).getPhone()+"");
            table.addCell(talabatList.get(i).getName()+"");
            table.addCell(talabatList.get(i).getAddress()+"");
        }
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        System.out.println("Done");
        return true;
    }

    public boolean writePendTalabt(String fname, java.util.List<Talabat> talabatList){

        String time = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Calendar.getInstance().getTime());
        String fpath = "/sdcard/" + "Talabaty "+time + ".pdf";
        File file = new File(fpath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img = null;
        byte[] byteArray = stream.toByteArray();
        try {
            img = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document document = new Document();
        PdfPTable header = new PdfPTable(new float[] { 2, 2, 2 });
        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(timeStamp);
        header.addCell(img);
        header.addCell("طلباتى");

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(" م ");//"
        table.addCell(" تاريخ ");//
        table.addCell(" وقت ");//"
        table.addCell(" التليفون ");//| العنوان	| أسم العميل	| التليفون	| وقت	| تاريخ	| م  |
        table.addCell(" أسم العميل ");
        table.addCell(" العنوان ");

        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.BLUE);
        }
        for (int i=1;i<talabatList.size();i++){
            table.addCell(talabatList.get(i).getNum()+"");
            table.addCell(talabatList.get(i).getEstlam_date());
            table.addCell(talabatList.get(i).getEstlam_time()+"");
            table.addCell(talabatList.get(i).getPhone()+"");
            table.addCell(talabatList.get(i).getName()+"");
            table.addCell(talabatList.get(i).getAddress()+"");
        }
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        System.out.println("Done");
        return true;
    }

    public boolean writeRaedyTalabt(String fname, java.util.List<Talabat> talabatList){

        String time = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Calendar.getInstance().getTime());
        String fpath = "/sdcard/" + "Talabaty "+time + ".pdf";
        File file = new File(fpath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img = null;
        byte[] byteArray = stream.toByteArray();
        try {
            img = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document document = new Document();
        PdfPTable header = new PdfPTable(new float[] { 2, 2, 2 });
        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(timeStamp);
        header.addCell(img);
        header.addCell("طلباتى");

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(" م ");//"
        table.addCell(" تاريخ ");//
        table.addCell(" وقت ");//"
        table.addCell(" التليفون ");//| العنوان	| أسم العميل	| التليفون	| وقت	| تاريخ	| م  |
        table.addCell(" أسم العميل ");
        table.addCell(" العنوان ");

        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.BLUE);
        }
        for (int i=1;i<talabatList.size();i++){
            table.addCell(talabatList.get(i).getNum()+"");
            table.addCell(talabatList.get(i).getEstlam_date());
            table.addCell(talabatList.get(i).getEstlam_time()+"");
            table.addCell(talabatList.get(i).getPhone()+"");
            table.addCell(talabatList.get(i).getName()+"");
            table.addCell(talabatList.get(i).getAddress()+"");
        }
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        System.out.println("Done");
        return true;
    }

    public boolean writeRejectedReport(String fname, java.util.List<Talabat> talabatList){

        String time = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Calendar.getInstance().getTime());
        String fpath = "/sdcard/" + "Talabaty "+time + ".pdf";
        File file = new File(fpath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image img = null;
        byte[] byteArray = stream.toByteArray();
        try {
            img = Image.getInstance(byteArray);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document document = new Document();
        PdfPTable header = new PdfPTable(new float[] { 2, 2, 2 });
        header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(timeStamp);
        header.addCell(img);
        header.addCell("طلباتى");

        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(" م ");//"
        table.addCell(" تاريخ ");//
        table.addCell(" وقت ");//"
        table.addCell(" أسم العميل ");
        table.addCell(" سبب الإلغاء ");
        table.addCell(" الإجمالى ");

        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.BLUE);
        }
        for (int i=1;i<talabatList.size();i++){
            table.addCell(talabatList.get(i).getNum()+"");
            table.addCell(talabatList.get(i).getEstlam_date());
            table.addCell(talabatList.get(i).getEstlam_time()+"");
            table.addCell(talabatList.get(i).getName()+"");
            table.addCell(talabatList.get(i).getReason()+"");
            table.addCell(talabatList.get(i).getTotal()+"");
        }
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
        System.out.println("Done");
        return true;
    }


//    public Boolean writeAgent(String fname, java.util.List<Offer> talabatList) {
////        resultDatabase = new ResultDatabase( context );
////        cursor1 = resultDatabase.ShowQuestionANDAnswer();
////        cursor2 = resultDatabase.ShowData();
//
//        try {
//            String fpath = "/sdcard/" + fname + ".pdf";
//            File file = new File(fpath);
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
////            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
////            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//
//
//            Document document = new Document();
//
//            PdfWriter.getInstance(document,
//                    new FileOutputStream(file.getAbsoluteFile()));
//            document.open();
////            Chunk glue = new Chunk(new VerticalPositionMark());
////            Paragraph p = new Paragraph("Text to the left");/// Time
////            p.add(new Chunk(glue));
////            document.add(p);
//
//            PdfPTable table = new PdfPTable(3);
//            table.setWidthPercentage(100);
//            String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
//
//            table.addCell(getCell(timeStamp, PdfPCell.ALIGN_LEFT));
////            table.addCell(getCell("Logo", PdfPCell.ALIGN_CENTER));
//            table.addCell(getCell("طلباتى", PdfPCell.ALIGN_RIGHT));
//            document.add(table);
//
//
//            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Image img = null;
//            byte[] byteArray = stream.toByteArray();
//            try {
//                img = Image.getInstance(byteArray);
//            } catch (BadElementException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
//            float s = (PageSize.A4.getWidth() - img.getScaledWidth()) / 2;
//            float y = (PageSize.A4.getHeight() - img.getScaledHeight()) / 2;
//            img.setAbsolutePosition(s, y);
//            document.add(img);
//
////            document.add(img);
////            while (cursor2.moveToNext()) {
////                document.add( new Paragraph("Student Num: "+ cursor2.getString( 1 )+"\n" ) );
////            }
//            document.addCreationDate();
//            document.setMargins( 10,10,10,10 );
//            //document.add(new Paragraph("Powered by @FCI-Learn" ));
//            String p = "";
//            for (int x=0; x<PageSize.A4.getWidth(); x++){
//                p+="_";
//            }
//            document.add( new Paragraph( p ) );
//            document.add( new Paragraph( "| إجمالى الطلبات\t| اسم العميل\t| م  |" ) );
//            for (int x=0; x<talabatList.size(); x++){
//                document.add( new Paragraph( "  "+talabatList.get(x).getNum()+"\t| "+talabatList.get(x).getName()+"\t| "+talabatList.get(x).getId()+"\t| " ) );
//
//            }
//
//            document.add( new Paragraph( p ) );
////            document.add( new Paragraph( "     Result =   "+counter+"  " ) );
//            document.close();
//
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//    }

//    public Boolean writeDoneTalabt(String fname, java.util.List<Talabat> talabatList) {
////        resultDatabase = new ResultDatabase( context );
////        cursor1 = resultDatabase.ShowQuestionANDAnswer();
////        cursor2 = resultDatabase.ShowData();
//
//        try {
//            String fpath = "/sdcard/" + fname + ".pdf";
//            File file = new File(fpath);
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
////            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
////            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//
//
//            Document document = new Document();
//
//            PdfWriter.getInstance(document,
//                    new FileOutputStream(file.getAbsoluteFile()));
//            document.open();
////            Chunk glue = new Chunk(new VerticalPositionMark());
////            Paragraph p = new Paragraph("Text to the left");/// Time
////            p.add(new Chunk(glue));
////            document.add(p);
//
//            PdfPTable table = new PdfPTable(3);
//            table.setWidthPercentage(100);
//            String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
//
//            table.addCell(getCell(timeStamp, PdfPCell.ALIGN_LEFT));
////            table.addCell(getCell("Logo", PdfPCell.ALIGN_CENTER));
//            table.addCell(getCell("طلباتى", PdfPCell.ALIGN_RIGHT));
//            document.add(table);
//
//
//            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Image img = null;
//            byte[] byteArray = stream.toByteArray();
//            try {
//                img = Image.getInstance(byteArray);
//            } catch (BadElementException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
//            float s = (PageSize.A4.getWidth() - img.getScaledWidth()) / 2;
//            float y = (PageSize.A4.getHeight() - img.getScaledHeight()) / 2;
//            img.setAbsolutePosition(s, y);
//            document.add(img);
//
////            document.add(img);
////            while (cursor2.moveToNext()) {
////                document.add( new Paragraph("Student Num: "+ cursor2.getString( 1 )+"\n" ) );
////            }
//            document.addCreationDate();
//            document.setMargins( 10,10,10,10 );
//            //document.add(new Paragraph("Powered by @FCI-Learn" ));
//            String p = "";
//            for (int x=0; x<PageSize.A4.getWidth(); x++){
//                p+="_";
//            }
//            document.add( new Paragraph( p ) );
//            document.add( new Paragraph( "| تاريخ التسليم\t| وقت التسليم\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م  |" ) );
//            for (int x=0; x<talabatList.size(); x++){
//                document.add( new Paragraph( "  "+talabatList.get(x).getNum()+"\t| "+talabatList.get(x).getEstlam_date()+"\t| "+talabatList.get(x).getEstlam_time()+"\t| "+talabatList.get(x).getPhone()+"\t| "+talabatList.get(x).getName()+"\t| "+talabatList.get(x).getTasleem_time()+"\t| "+talabatList.get(x).getTasleem_date()+"\t| " ) );
//
//            }
//
//            document.add( new Paragraph( p ) );
////            document.add( new Paragraph( "     Result =   "+counter+"  " ) );
//            document.close();
//
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//    }

//    public Boolean writeNewTalabt(String fname, java.util.List<Talabat> talabatList) {
////        resultDatabase = new ResultDatabase( context );
////        cursor1 = resultDatabase.ShowQuestionANDAnswer();
////        cursor2 = resultDatabase.ShowData();
//
//        try {
//            ActivityCompat.requestPermissions((Activity) context,
//                    new String[] {
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    },
//                    100);
//            String fpath = "/sdcard/" + fname + ".pdf";
//            File file = new File(fpath);
//
////            if(!file.mkdirs()) {
////                Log.i("Test", "This path is already exist: " + file.getAbsolutePath());
////            }
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
//
//
////            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
////            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//
//
//            Document document = new Document();
//
//            PdfWriter.getInstance(document,
//                    new FileOutputStream(file.getAbsoluteFile()));
//            document.open();
////            Chunk glue = new Chunk(new VerticalPositionMark());
////            Paragraph p = new Paragraph("Text to the left");/// Time
////            p.add(new Chunk(glue));
////            document.add(p);
//
//            Rectangle rect= new Rectangle(36,108);
//            rect.enableBorderSide(1);
//            rect.enableBorderSide(2);
//            rect.enableBorderSide(4);
//            rect.enableBorderSide(8);
//            rect.setBorder(2);
//            rect.setBorderColor(BaseColor.BLACK);
//            document.add(rect);
//
//
//            PdfPTable table = new PdfPTable(3);
//            table.setWidthPercentage(100);
//            String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
//
//            table.addCell(getCell(timeStamp, PdfPCell.ALIGN_LEFT));
////            table.addCell(getCell("Logo", PdfPCell.ALIGN_CENTER));
//            table.addCell(getCell("طلباتى", PdfPCell.ALIGN_RIGHT));
//            document.add(table);
//
//
//            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.notification);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Image img = null;
//            byte[] byteArray = stream.toByteArray();
//            try {
//                img = Image.getInstance(byteArray);
//            } catch (BadElementException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
//            float s = (PageSize.A4.getWidth() - img.getScaledWidth()) / 2;
//            float y = (PageSize.A4.getHeight() - img.getScaledHeight()) / 2;
//            img.setAbsolutePosition(s, y);
//            document.add(img);
//
////            document.add(img);
////            while (cursor2.moveToNext()) {
////                document.add( new Paragraph("Student Num: "+ cursor2.getString( 1 )+"\n" ) );
////            }
//            document.addCreationDate();
//            document.setMargins( 10,10,10,10 );
//            //document.add(new Paragraph("Powered by @FCI-Learn" ));
//
//;
//            document.add( new Paragraph( "| العنوان\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م  |" ) );
//
//            for (int x=0; x<talabatList.size(); x++){
//                document.add( new Paragraph( "  "+talabatList.get(x).getNum()+"\t| "+talabatList.get(x).getEstlam_date()+"\t| "+talabatList.get(x).getEstlam_time()+"\t| "+talabatList.get(x).getPhone()+"\t| "+talabatList.get(x).getName()+"\t| "+talabatList.get(x).getAddress()+"\t| " ) );
//
//            }
//
////            document.add( new Paragraph( "     Result =   "+counter+"  " ) );
//            document.close();
//
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//    }

//    public Boolean writeNotiTalabt(String fname, java.util.List<Talabat> talabatList) {
////        resultDatabase = new ResultDatabase( context );
////        cursor1 = resultDatabase.ShowQuestionANDAnswer();
////        cursor2 = resultDatabase.ShowData();
//
//        try {
//            String fpath = "/sdcard/" + fname + ".pdf";
//            File file = new File(fpath);
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
////            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
////            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//
//
//            Document document = new Document();
//
//            PdfWriter.getInstance(document,
//                    new FileOutputStream(file.getAbsoluteFile()));
//            document.open();
////            Chunk glue = new Chunk(new VerticalPositionMark());
////            Paragraph p = new Paragraph("Text to the left");/// Time
////            p.add(new Chunk(glue));
////            document.add(p);
//
//            PdfPTable table = new PdfPTable(3);
//            table.setWidthPercentage(100);
//            String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
//
//            table.addCell(getCell(timeStamp, PdfPCell.ALIGN_LEFT));
////            table.addCell(getCell("Logo", PdfPCell.ALIGN_CENTER));
//            table.addCell(getCell("طلباتى", PdfPCell.ALIGN_RIGHT));
//            document.add(table);
//
//
//            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Image img = null;
//            byte[] byteArray = stream.toByteArray();
//            try {
//                img = Image.getInstance(byteArray);
//            } catch (BadElementException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
//            float s = (PageSize.A4.getWidth() - img.getScaledWidth()) / 2;
//            float y = (PageSize.A4.getHeight() - img.getScaledHeight()) / 2;
//            img.setAbsolutePosition(s, y);
//            document.add(img);
//
////            document.add(img);
////            while (cursor2.moveToNext()) {
////                document.add( new Paragraph("Student Num: "+ cursor2.getString( 1 )+"\n" ) );
////            }
//            document.addCreationDate();
//            document.setMargins( 10,10,10,10 );
//            //document.add(new Paragraph("Powered by @FCI-Learn" ));
//            String p = "";
//            for (int x=0; x<PageSize.A4.getWidth(); x++){
//                p+="_";
//            }
//            document.add( new Paragraph( p ) );
//            document.add( new Paragraph( "| العنوان\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م  |" ) );
//            for (int x=0; x<talabatList.size(); x++){
//                document.add( new Paragraph( "  "+talabatList.get(x).getNum()+"\t| "+talabatList.get(x).getEstlam_date()+"\t| "+talabatList.get(x).getEstlam_time()+"\t| "+talabatList.get(x).getPhone()+"\t| "+talabatList.get(x).getName()+"\t| "+talabatList.get(x).getAddress()+"\t| " ) );
//
//            }
//
//            document.add( new Paragraph( p ) );
////            document.add( new Paragraph( "     Result =   "+counter+"  " ) );
//            document.close();
//
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//    }

//    public Boolean writeMyTasks(String fname, java.util.List<Talabat> talabatList) {
////        resultDatabase = new ResultDatabase( context );
////        cursor1 = resultDatabase.ShowQuestionANDAnswer();
////        cursor2 = resultDatabase.ShowData();
//
//        try {
//            String fpath = "/sdcard/" + fname + ".pdf";
//            File file = new File(fpath);
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
////            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
////            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//
//
//            Document document = new Document();
//
//            PdfWriter.getInstance(document,
//                    new FileOutputStream(file.getAbsoluteFile()));
//            document.open();
////            Chunk glue = new Chunk(new VerticalPositionMark());
////            Paragraph p = new Paragraph("Text to the left");/// Time
////            p.add(new Chunk(glue));
////            document.add(p);
//
//            PdfPTable table = new PdfPTable(3);
//            table.setWidthPercentage(100);
//            String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
//
//            table.addCell(getCell(timeStamp, PdfPCell.ALIGN_LEFT));
////            table.addCell(getCell("Logo", PdfPCell.ALIGN_CENTER));
//            table.addCell(getCell("طلباتى", PdfPCell.ALIGN_RIGHT));
//            document.add(table);
//
//
//            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Image img = null;
//            byte[] byteArray = stream.toByteArray();
//            try {
//                img = Image.getInstance(byteArray);
//            } catch (BadElementException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
//            float s = (PageSize.A4.getWidth() - img.getScaledWidth()) / 2;
//            float y = (PageSize.A4.getHeight() - img.getScaledHeight()) / 2;
//            img.setAbsolutePosition(s, y);
//            document.add(img);
//
////            document.add(img);
////            while (cursor2.moveToNext()) {
////                document.add( new Paragraph("Student Num: "+ cursor2.getString( 1 )+"\n" ) );
////            }
//            document.addCreationDate();
//            document.setMargins( 10,10,10,10 );
//            //document.add(new Paragraph("Powered by @FCI-Learn" ));
//            String p = "";
//            for (int x=0; x<PageSize.A4.getWidth(); x++){
//                p+="_";
//            }
//            document.add( new Paragraph( p ) );
//            document.add( new Paragraph( "| العنوان\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م  |" ) );
//            for (int x=0; x<talabatList.size(); x++){
//                document.add( new Paragraph( "  "+talabatList.get(x).getNum()+"\t| "+talabatList.get(x).getEstlam_date()+"\t| "+talabatList.get(x).getEstlam_time()+"\t| "+talabatList.get(x).getPhone()+"\t| "+talabatList.get(x).getName()+"\t| "+talabatList.get(x).getAddress()+"\t| " ) );
//
//            }
//
//            document.add( new Paragraph( p ) );
////            document.add( new Paragraph( "     Result =   "+counter+"  " ) );
//            document.close();
//
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//    }

//    public Boolean writeSailedTalabt(String fname, java.util.List<Talabat> talabatList) {
////        resultDatabase = new ResultDatabase( context );
////        cursor1 = resultDatabase.ShowQuestionANDAnswer();
////        cursor2 = resultDatabase.ShowData();
//
//        try {
//            String fpath = "/sdcard/" + fname + ".pdf";
//            File file = new File(fpath);
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
////            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
////            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//
//
//            Document document = new Document();
//
//            PdfWriter.getInstance(document,
//                    new FileOutputStream(file.getAbsoluteFile()));
//            document.open();
////            Chunk glue = new Chunk(new VerticalPositionMark());
////            Paragraph p = new Paragraph("Text to the left");/// Time
////            p.add(new Chunk(glue));
////            document.add(p);
//
//            PdfPTable table = new PdfPTable(3);
//            table.setWidthPercentage(100);
//            String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
//
//            table.addCell(getCell(timeStamp, PdfPCell.ALIGN_LEFT));
////            table.addCell(getCell("Logo", PdfPCell.ALIGN_CENTER));
//            table.addCell(getCell("طلباتى", PdfPCell.ALIGN_RIGHT));
//            document.add(table);
//
//
//            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Image img = null;
//            byte[] byteArray = stream.toByteArray();
//            try {
//                img = Image.getInstance(byteArray);
//            } catch (BadElementException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
//            float s = (PageSize.A4.getWidth() - img.getScaledWidth()) / 2;
//            float y = (PageSize.A4.getHeight() - img.getScaledHeight()) / 2;
//            img.setAbsolutePosition(s, y);
//            document.add(img);
//
////            document.add(img);
////            while (cursor2.moveToNext()) {
////                document.add( new Paragraph("Student Num: "+ cursor2.getString( 1 )+"\n" ) );
////            }
//            document.addCreationDate();
//            document.setMargins( 10,10,10,10 );
//            //document.add(new Paragraph("Powered by @FCI-Learn" ));
//            String p = "";
//            for (int x=0; x<PageSize.A4.getWidth(); x++){
//                p+="_";
//            }
//            document.add( new Paragraph( p ) );
//            document.add( new Paragraph( "| الإجمالى\t|تاريخ التسليم\t|وقت التسليم\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م  |" ) );
//            for (int x=0; x<talabatList.size(); x++){
//                document.add( new Paragraph( "  "+talabatList.get(x).getNum()+"\t| "+talabatList.get(x).getEstlam_date()+"\t| "+talabatList.get(x).getEstlam_time()+"\t| "+talabatList.get(x).getName()+"\t| "+talabatList.get(x).getTasleem_time()+"\t| "+talabatList.get(x).getTasleem_date()+"\t|"+talabatList.get(x).getTotal()+"\t| " ) );
//
//            }
//
//            document.add( new Paragraph( p ) );
////            document.add( new Paragraph( "     Result =   "+counter+"  " ) );
//            document.close();
//
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public Boolean writeRetuTalabt(String fname, java.util.List<Talabat> talabatList) {
////        resultDatabase = new ResultDatabase( context );
////        cursor1 = resultDatabase.ShowQuestionANDAnswer();
////        cursor2 = resultDatabase.ShowData();
//
//        try {
//            String fpath = "/sdcard/" + fname + ".pdf";
//            File file = new File(fpath);
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
////            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
////            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//
//
//            Document document = new Document();
//
//            PdfWriter.getInstance(document,
//                    new FileOutputStream(file.getAbsoluteFile()));
//            document.open();
////            Chunk glue = new Chunk(new VerticalPositionMark());
////            Paragraph p = new Paragraph("Text to the left");/// Time
////            p.add(new Chunk(glue));
////            document.add(p);
//
//            PdfPTable table = new PdfPTable(3);
//            table.setWidthPercentage(100);
//            String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
//
//            table.addCell(getCell(timeStamp, PdfPCell.ALIGN_LEFT));
////            table.addCell(getCell("Logo", PdfPCell.ALIGN_CENTER));
//            table.addCell(getCell("طلباتى", PdfPCell.ALIGN_RIGHT));
//            document.add(table);
//
//
//            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Image img = null;
//            byte[] byteArray = stream.toByteArray();
//            try {
//                img = Image.getInstance(byteArray);
//            } catch (BadElementException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
//            float s = (PageSize.A4.getWidth() - img.getScaledWidth()) / 2;
//            float y = (PageSize.A4.getHeight() - img.getScaledHeight()) / 2;
//            img.setAbsolutePosition(s, y);
//            document.add(img);
//
////            document.add(img);
////            while (cursor2.moveToNext()) {
////                document.add( new Paragraph("Student Num: "+ cursor2.getString( 1 )+"\n" ) );
////            }
//            document.addCreationDate();
//            document.setMargins( 10,10,10,10 );
//            //document.add(new Paragraph("Powered by @FCI-Learn" ));
//            String p = "";
//            for (int x=0; x<PageSize.A4.getWidth(); x++){
//                p+="_";
//            }
//            document.add( new Paragraph( p ) );
//            document.add( new Paragraph( "| العنوان\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م  |" ) );
//            for (int x=0; x<talabatList.size(); x++){
//                document.add( new Paragraph( "  "+talabatList.get(x).getNum()+"\t| "+talabatList.get(x).getEstlam_date()+"\t| "+talabatList.get(x).getEstlam_time()+"\t| "+talabatList.get(x).getPhone()+"\t| "+talabatList.get(x).getName()+"\t| "+talabatList.get(x).getAddress()+"\t| " ) );
//
//            }
//
//            document.add( new Paragraph( p ) );
////            document.add( new Paragraph( "     Result =   "+counter+"  " ) );
//            document.close();
//
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public Boolean writePendTalabt(String fname, java.util.List<Talabat> talabatList) {
////        resultDatabase = new ResultDatabase( context );
////        cursor1 = resultDatabase.ShowQuestionANDAnswer();
////        cursor2 = resultDatabase.ShowData();
//
//        try {
//            String fpath = "/sdcard/" + fname + ".pdf";
//            File file = new File(fpath);
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
////            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
////            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//
//
//            Document document = new Document();
//
//            PdfWriter.getInstance(document,
//                    new FileOutputStream(file.getAbsoluteFile()));
//            document.open();
////            Chunk glue = new Chunk(new VerticalPositionMark());
////            Paragraph p = new Paragraph("Text to the left");/// Time
////            p.add(new Chunk(glue));
////            document.add(p);
//
//            PdfPTable table = new PdfPTable(3);
//            table.setWidthPercentage(100);
//            String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
//
//            table.addCell(getCell(timeStamp, PdfPCell.ALIGN_LEFT));
////            table.addCell(getCell("Logo", PdfPCell.ALIGN_CENTER));
//            table.addCell(getCell("طلباتى", PdfPCell.ALIGN_RIGHT));
//            document.add(table);
//
//
//            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Image img = null;
//            byte[] byteArray = stream.toByteArray();
//            try {
//                img = Image.getInstance(byteArray);
//            } catch (BadElementException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
//            float s = (PageSize.A4.getWidth() - img.getScaledWidth()) / 2;
//            float y = (PageSize.A4.getHeight() - img.getScaledHeight()) / 2;
//            img.setAbsolutePosition(s, y);
//            document.add(img);
//
////            document.add(img);
////            while (cursor2.moveToNext()) {
////                document.add( new Paragraph("Student Num: "+ cursor2.getString( 1 )+"\n" ) );
////            }
//            document.addCreationDate();
//            document.setMargins( 10,10,10,10 );
//            //document.add(new Paragraph("Powered by @FCI-Learn" ));
//            String p = "";
//            for (int x=0; x<PageSize.A4.getWidth(); x++){
//                p+="_";
//            }
//            document.add( new Paragraph( p ) );
//            document.add( new Paragraph( "| العنوان\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م  |" ) );
//            for (int x=0; x<talabatList.size(); x++){
//                document.add( new Paragraph( "  "+talabatList.get(x).getNum()+"\t| "+talabatList.get(x).getEstlam_date()+"\t| "+talabatList.get(x).getEstlam_time()+"\t| "+talabatList.get(x).getPhone()+"\t| "+talabatList.get(x).getName()+"\t| "+talabatList.get(x).getAddress()+"\t| " ) );
//
//            }
//
//            document.add( new Paragraph( p ) );
////            document.add( new Paragraph( "     Result =   "+counter+"  " ) );
//            document.close();
//
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public Boolean writeRaedyTalabt(String fname, java.util.List<Talabat> talabatList) {
////        resultDatabase = new ResultDatabase( context );
////        cursor1 = resultDatabase.ShowQuestionANDAnswer();
////        cursor2 = resultDatabase.ShowData();
//
//        try {
//            String fpath = "/sdcard/" + fname + ".pdf";
//            File file = new File(fpath);
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
////            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
////            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//
//
//            Document document = new Document();
//
//            PdfWriter.getInstance(document,
//                    new FileOutputStream(file.getAbsoluteFile()));
//            document.open();
////            Chunk glue = new Chunk(new VerticalPositionMark());
////            Paragraph p = new Paragraph("Text to the left");/// Time
////            p.add(new Chunk(glue));
////            document.add(p);
//
//            PdfPTable table = new PdfPTable(3);
//            table.setWidthPercentage(100);
//            String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
//
//            table.addCell(getCell(timeStamp, PdfPCell.ALIGN_LEFT));
////            table.addCell(getCell("Logo", PdfPCell.ALIGN_CENTER));
//            table.addCell(getCell("طلباتى", PdfPCell.ALIGN_RIGHT));
//            document.add(table);
//
//
//            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Image img = null;
//            byte[] byteArray = stream.toByteArray();
//            try {
//                img = Image.getInstance(byteArray);
//            } catch (BadElementException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
//            float s = (PageSize.A4.getWidth() - img.getScaledWidth()) / 2;
//            float y = (PageSize.A4.getHeight() - img.getScaledHeight()) / 2;
//            img.setAbsolutePosition(s, y);
//            document.add(img);
//
////            document.add(img);
////            while (cursor2.moveToNext()) {
////                document.add( new Paragraph("Student Num: "+ cursor2.getString( 1 )+"\n" ) );
////            }
//            document.addCreationDate();
//            document.setMargins( 10,10,10,10 );
//            //document.add(new Paragraph("Powered by @FCI-Learn" ));
//            String p = "";
//            for (int x=0; x<PageSize.A4.getWidth(); x++){
//                p+="_";
//            }
//            document.add( new Paragraph( p ) );
//            document.add( new Paragraph( "| العنوان\t| أسم العميل\t| التليفون\t| وقت\t| تاريخ\t| م  |" ) );
//            for (int x=0; x<talabatList.size(); x++){
//                document.add( new Paragraph( "  "+talabatList.get(x).getNum()+"\t| "+talabatList.get(x).getEstlam_date()+"\t| "+talabatList.get(x).getEstlam_time()+"\t| "+talabatList.get(x).getPhone()+"\t| "+talabatList.get(x).getName()+"\t| "+talabatList.get(x).getAddress()+"\t| " ) );
//
//            }
//
//            document.add( new Paragraph( p ) );
////            document.add( new Paragraph( "     Result =   "+counter+"  " ) );
//            document.close();
//
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//    }

//    public Boolean writeRejectedReport(String fname, java.util.List<Talabat> talabatList) {
////        resultDatabase = new ResultDatabase( context );
////        cursor1 = resultDatabase.ShowQuestionANDAnswer();
////        cursor2 = resultDatabase.ShowData();
//
//        try {
//            String fpath = "/sdcard/" + fname + ".pdf";
//            File file = new File(fpath);
//
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
////            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
////            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
//
//
//            Document document = new Document();
//
//            PdfWriter.getInstance(document,
//                    new FileOutputStream(file.getAbsoluteFile()));
//            document.open();
////            Chunk glue = new Chunk(new VerticalPositionMark());
////            Paragraph p = new Paragraph("Text to the left");/// Time
////            p.add(new Chunk(glue));
////            document.add(p);
//
//            PdfPTable table = new PdfPTable(3);
//            table.setWidthPercentage(100);
//            String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
//
//            table.addCell(getCell(timeStamp, PdfPCell.ALIGN_LEFT));
////            table.addCell(getCell("Logo", PdfPCell.ALIGN_CENTER));
//            table.addCell(getCell("طلباتى", PdfPCell.ALIGN_RIGHT));
//            document.add(table);
//
//
//            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            Image img = null;
//            byte[] byteArray = stream.toByteArray();
//            try {
//                img = Image.getInstance(byteArray);
//            } catch (BadElementException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
//            float s = (PageSize.A4.getWidth() - img.getScaledWidth()) / 2;
//            float y = (PageSize.A4.getHeight() - img.getScaledHeight()) / 2;
//            img.setAbsolutePosition(s, y);
//            document.add(img);
//
////            document.add(img);
////            while (cursor2.moveToNext()) {
////                document.add( new Paragraph("Student Num: "+ cursor2.getString( 1 )+"\n" ) );
////            }
//            document.addCreationDate();
//            document.setMargins( 10,10,10,10 );
//            //document.add(new Paragraph("Powered by @FCI-Learn" ));
//            String p = "";
//            for (int x=0; x<PageSize.A4.getWidth(); x++){
//                p+="_";
//            }
//            document.add( new Paragraph( p ) );
//            document.add( new Paragraph( "| الإجمالى \t| سبب الإلغاء \t| أسم العميل\t| وقت\t| تاريخ\t| م  |" ) );
//            for (int x=0; x<talabatList.size(); x++){
//                document.add( new Paragraph( "  "+talabatList.get(x).getNum()+"\t| "+talabatList.get(x).getEstlam_date()+"\t| "+talabatList.get(x).getEstlam_time()+"\t| "+talabatList.get(x).getName()+"\t| "+talabatList.get(x).getReason()+"\t| "+talabatList.get(x).getTotal()+"\t| " ) );
//
//            }
//
//            document.add( new Paragraph( p ) );
////            document.add( new Paragraph( "     Result =   "+counter+"  " ) );
//            document.close();
//
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//    }



//    public PdfPCell getCell(String text, int alignment) {
//        PdfPCell cell = new PdfPCell(new Phrase(text));
//        cell.setPadding(0);
//        cell.setHorizontalAlignment(alignment);
//        cell.setBorder(PdfPCell.NO_BORDER);
//        return cell;
//    }

}