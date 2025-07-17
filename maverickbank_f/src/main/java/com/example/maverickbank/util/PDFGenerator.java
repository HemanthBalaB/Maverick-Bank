package com.example.maverickbank.util;

import com.example.maverickbank.model.AccountHolder;
import com.example.maverickbank.model.TransactionHistory;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
public class PDFGenerator {

    public ByteArrayInputStream generatePdf(AccountHolder account, List<TransactionHistory> transactions) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Account Statement", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Account No: " + account.getAccountNo()));
            document.add(new Paragraph("Account Type: " + account.getAccountType()));
            document.add(new Paragraph("Balance: ₹" + account.getBalance()));
            document.add(new Paragraph("Branch: " + (account.getBranch() != null ? account.getBranch().getBranchName() : "N/A")));
            document.add(new Paragraph("IFSC: " + account.getIfscCode()));
            document.add(new Paragraph(" "));

            // Table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 2, 2, 5});

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            table.addCell(new PdfPCell(new Phrase("Date", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Type", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Amount", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Description", headerFont)));

            for (TransactionHistory txn : transactions) {
                table.addCell(txn.getTimestamp() != null ? txn.getTimestamp().toLocalDate().toString() : "N/A");
                table.addCell(txn.getType());
                table.addCell("₹" + txn.getAmount());
                table.addCell(txn.getDescription());
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF: " + e.getMessage());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
