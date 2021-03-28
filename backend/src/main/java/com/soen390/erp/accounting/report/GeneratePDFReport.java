package com.soen390.erp.accounting.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.model.SaleOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

public class GeneratePDFReport {

    private static Font header1Font = new Font(Font.FontFamily.UNDEFINED, 18,
            Font.BOLD);


    private static final Logger logger =
            LoggerFactory.getLogger(GeneratePDFReport.class);

    public static ByteArrayInputStream ledgerReport(List<Ledger> ledgers)
    {
        Document document = new Document();
        Paragraph preface = new Paragraph();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            preface.add(new Paragraph("Ledger Report", header1Font));

            addEmptyLine(preface, 1);

            preface.add(new Paragraph(
                    "Report generated on : " + new Date() ));

            addEmptyLine(preface, 2);

            PdfPTable table = new PdfPTable(7);

            table.setWidthPercentage(90);
            table.setWidths(new int[]{1, 1, 2, 1, 1, 1, 1});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Amount", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Date", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Credit Account", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Debit Account", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Purchase Order", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Sale Order", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            for (Ledger ledger : ledgers) {

                PdfPCell cell;

                cell =
                        new PdfPCell(new Phrase(Integer.toString(ledger.getId())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(Double.toString(ledger.getAmount())));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(ledger.getDate())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(Integer.toString(ledger.getCreditAccount().getId())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(Integer.toString(ledger.getDebitAccount().getId())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                String purchaseOrderId = "--";
                if (ledger.getPurchaseOrder() != null)
                {
                    purchaseOrderId =
                            Integer.toString(ledger.getPurchaseOrder().getId());
                }
                cell =
                        new PdfPCell(new Phrase(purchaseOrderId));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                String saleOrderId = "--";
                if (ledger.getSaleOrder() != null)
                {
                    saleOrderId =
                            Integer.toString(ledger.getSaleOrder().getId());
                }

                cell =
                        new PdfPCell(new Phrase(saleOrderId));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);
            }

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(preface);
            document.add(table);

            document.close();

        } catch (DocumentException ex) {

            logger.error("Error occurred Generating the Ledger PDF", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public static ByteArrayInputStream saleOrderReport(List<SaleOrder> saleOrders)
    {
        Document document = new Document();
        Paragraph preface = new Paragraph();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            preface.add(new Paragraph("Sale Orders Report", header1Font));

            addEmptyLine(preface, 1);

            preface.add(new Paragraph(
                    "Report generated on : " + new Date() ));

            addEmptyLine(preface, 2);

            PdfPTable table = new PdfPTable(12);

            table.setWidthPercentage(99);
            table.setWidths(new int[]{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Date", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Discount", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Discount \nAmount", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Grand \nTotal", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Paid", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Shipped", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Tax", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Tax Amount", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Total Amount", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Client", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Plant", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            for (SaleOrder saleOrder : saleOrders) {

                PdfPCell cell;

                cell =
                        new PdfPCell(new Phrase(Integer.toString(saleOrder.getId())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(saleOrder.getDate())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(Double.toString(saleOrder.getDiscount())));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(saleOrder.getDiscountAmount())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(saleOrder.getGrandTotal())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(saleOrder.isPaid())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(saleOrder.isShipped())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(saleOrder.getTax())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(saleOrder.getTaxAmount())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(saleOrder.getTotalAmount())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(
                                Integer.toString(saleOrder.getClient().getId())
                                        + ", " + saleOrder.getClient().getName()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(
                                Integer.toString(saleOrder.getPlant().getId())
                                        + ", " + saleOrder.getPlant().getName()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);
            }

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(preface);
            document.add(table);

            document.close();

        } catch (DocumentException ex) {

            logger.error("Error occurred Generating the Sale Order PDF", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public static ByteArrayInputStream purchaseOrderReport( List<PurchaseOrder> purchaseOrders)
    {
        Document document = new Document();
        Paragraph preface = new Paragraph();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            preface.add(new Paragraph("Purchase Orders Report", header1Font));

            addEmptyLine(preface, 1);

            preface.add(new Paragraph(
                    "Report generated on : " + new Date() ));

            addEmptyLine(preface, 2);

            PdfPTable table = new PdfPTable(12);

            table.setWidthPercentage(98);
            table.setWidths(new int[]{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Date", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Discount", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Discount Amount", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Grand Total", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Tax", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Tax Amount", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Total Amount", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Plant", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Supplier", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Paid", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Received", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);


            for (PurchaseOrder purchaseOrder : purchaseOrders) {

                PdfPCell cell;

                cell =
                        new PdfPCell(new Phrase(Integer.toString(purchaseOrder.getId())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(purchaseOrder.getDate())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(Double.toString(purchaseOrder.getDiscount())));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(purchaseOrder.getDiscountAmount())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(purchaseOrder.getGrandTotal())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(purchaseOrder.getTax())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(purchaseOrder.getTaxAmount())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(purchaseOrder.getTotalAmount())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(
                                Integer.toString(purchaseOrder.getPlant().getId())
                                        + ", " + purchaseOrder.getPlant().getName()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(
                                Integer.toString(purchaseOrder.getSupplier().getId())
                                        + ", " + purchaseOrder.getSupplier().getName()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);


                cell =
                        new PdfPCell(new Phrase(String.valueOf(purchaseOrder.isPaid())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

                cell =
                        new PdfPCell(new Phrase(String.valueOf(purchaseOrder.isReceived())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                table.addCell(cell);

            }

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(preface);
            document.add(table);

            document.close();

        } catch (DocumentException ex) {

            logger.error("Error occurred Generating the Purchase Order PDF",
                    ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }


    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

}
