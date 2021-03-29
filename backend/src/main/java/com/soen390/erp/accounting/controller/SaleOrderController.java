package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.model.SaleOrder;
import com.soen390.erp.accounting.report.CsvReportGenerator;
import com.soen390.erp.accounting.report.IReportGenerator;
import com.soen390.erp.accounting.report.PdfReportGenerator;
import com.soen390.erp.accounting.repository.SaleOrderRepository;
import com.soen390.erp.accounting.service.AccountService;
import com.soen390.erp.accounting.service.LedgerService;
import com.soen390.erp.accounting.service.SaleOrderService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class SaleOrderController {
    private final SaleOrderService saleOrderService;
    private final AccountService accountService;
    private final LedgerService ledgerService;
    private final SaleOrderRepository saleOrderRepository;


    @GetMapping(path = "/SaleOrders")
    public ResponseEntity<?> all(){
        //TODO: use stream and return a mapped collection or use assembler
        return ResponseEntity.ok().body(saleOrderService.getAllSaleOrders());
    }

    @GetMapping(path = "/SaleOrders/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        Optional<SaleOrder> saleOrder = saleOrderService.getSaleOrder(id);

        if (saleOrder.isPresent()){
            return ResponseEntity.ok().body(saleOrder.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

<<<<<<< HEAD
    @GetMapping(value = "/SaleOrders/report/pdf")
    public ResponseEntity<InputStreamResource> exportPdf() {

        List<SaleOrder> saleOrders = saleOrderService.getAllSaleOrders();

        ByteArrayInputStream bis =
                GeneratePDFReport.saleOrderReport(saleOrders);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "inline; filename=saleOrderReport" +
                ".pdf");
=======
>>>>>>> 8b0d6e6 (merge with Adam's csv report and implemented visitor pattern for report generation)


    @PostMapping(path = "/SaleOrders")
    public ResponseEntity<?> createSaleOrder(@RequestBody SaleOrder saleOrder){
        //TODO: validate input

        saleOrder.setPaid(false);
        saleOrder.setShipped(false);

        boolean isSuccessful = saleOrderService.addSaleOrder(saleOrder);
        if (isSuccessful == true){
            //TODO: debug if id has value
            return ResponseEntity.ok().body(saleOrder.getId());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        }
    }

    @PutMapping(path = "/SaleOrders/{id}/ReceivePayment")
    public ResponseEntity<?> receivePayment(@PathVariable int id){

        //region validation
        //check if sale order exists
        Optional<SaleOrder> saleOrderOptional = saleOrderService.getSaleOrder(id);
        if (saleOrderOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        SaleOrder saleOrder = saleOrderOptional.get();
        //check if transaction valid
        if(saleOrder.isPaid()){
            return ResponseEntity.badRequest().build();
        }
        //TODO check if bank balance is more than grand total
        //TODO check if new status is valid
        //endregion


        saleOrderService.receivePaymentTransactions(saleOrder);

        //region return
        return ResponseEntity.status(HttpStatus.CREATED).build();
        //endregion
    }

    @PutMapping(path = "/SaleOrders/{id}/ShipBike")
    public ResponseEntity<?> shipBike(@PathVariable int id){

        //region validation
        //check if sale order exists
        Optional<SaleOrder> saleOrderOptional = saleOrderService.getSaleOrder(id);
        if (saleOrderOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        SaleOrder saleOrder = saleOrderOptional.get();
        //check if transaction valid
        if(saleOrder.isShipped()){
            return ResponseEntity.badRequest().build();
        }
        //TODO check if bank balance is more than grand total
        //TODO check if new status is valid
        //endregion

        saleOrderService.shipBikeTransactions(saleOrder);

        //region return
        return ResponseEntity.ok().build();
        //endregion
    }

    @GetMapping("/SaleOrders/report/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException
    {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        IReportGenerator csvReportGenerator = new CsvReportGenerator();

//        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
//                CsvPreference.STANDARD_PREFERENCE);

        csvReportGenerator.setResponse(response);

        saleOrderService.accept(csvReportGenerator);

//        return ResponseEntity.ok().body("Sale Orders CSV Report Generated.");

//        List<SaleOrder> listSales = saleOrderService.getAllSaleOrders();
//
//        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
//        String[] csvHeader = {"Sale Oder ID",  "Plant", "date", "Client",
//                "Total Amount", "Discount", "Discount Amount", "Tax",
//                "Tax Amount", "Total", "Paid", "Shipped" , "Sale Items"};
//        String[] nameMapping = {"id", "plant", "date", "client",  "totalAmount",
//                "discount","discountAmount", "tax", "taxAmount", "grandTotal",
//                "paid", "shipped", "saleOrderItems" };
//
//        csvWriter.writeHeader(csvHeader);
//
//        for (SaleOrder sale : listSales) {
//            csvWriter.write(sale, nameMapping);
//        }
//
//        csvWriter.close();
    }

    @GetMapping(value = "/SaleOrders/report/pdf")
    public ResponseEntity<InputStreamResource> exportToPdf() throws IOException
    {
        var headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "inline; filename=saleOrderReport" +
                        ".pdf");

        IReportGenerator pdfReportGenerator = new PdfReportGenerator();
        saleOrderService.accept(pdfReportGenerator);
        ByteArrayInputStream inputStream = pdfReportGenerator.getInputStream();

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(inputStream));
    }
}