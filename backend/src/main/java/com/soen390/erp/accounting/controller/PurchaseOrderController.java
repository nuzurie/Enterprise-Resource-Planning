package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.report.CsvReportGenerator;
import com.soen390.erp.accounting.report.IReportGenerator;
import com.soen390.erp.accounting.report.PdfReportGenerator;
import com.soen390.erp.accounting.repository.PurchaseOrderRepository;
import com.soen390.erp.accounting.service.AccountService;
import com.soen390.erp.accounting.service.LedgerService;
import com.soen390.erp.accounting.service.PurchaseOrderService;
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
public class PurchaseOrderController {
    private final PurchaseOrderService purchaseOrderService;
    private final AccountService accountService;
    private final LedgerService ledgerService;
    private final PurchaseOrderRepository purchaseOrderRepository;

    @GetMapping(path = "/PurchaseOrders")
    public ResponseEntity<?> all(){
        //TODO: use stream and return a mapped collection or use assembler
        return ResponseEntity.ok().body(purchaseOrderService.getAllPurchaseOrders());
    }

    @GetMapping(path = "/PurchaseOrders/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        Optional<PurchaseOrder> purchaseOrder = purchaseOrderService.getPurchaseOrder(id);

        if (purchaseOrder.isPresent()){
            return ResponseEntity.ok().body(purchaseOrder.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/PurchaseOrders")
    public ResponseEntity<?> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder){
        //TODO: validate input

        purchaseOrder.setPaid(false);
        purchaseOrder.setReceived(false);

        boolean isSuccessful = purchaseOrderService.addPurchaseOrder(purchaseOrder);
        if (isSuccessful == true){
            //TODO: debug if id has value
            return ResponseEntity.ok().body(purchaseOrder.getId());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        }
    }

    @PutMapping(path = "/PurchaseOrders/{id}/MakePayment")
    public ResponseEntity<?> makePayment(@PathVariable int id){

        //region validation
        //check if purchase order exists
        Optional<PurchaseOrder> purchaseOrderOptional = purchaseOrderService.getPurchaseOrder(id);
        if (purchaseOrderOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        PurchaseOrder purchaseOrder = purchaseOrderOptional.get();
        //check if transaction valid
        if(purchaseOrder.isPaid()){
            return ResponseEntity.badRequest().build();
        }
        //TODO check if bank balance is more than grand total
        //endregion

        purchaseOrderService.makePaymentTransactions(purchaseOrder);

        //region return
        return ResponseEntity.ok().build();
        //endregion
    }
    @PutMapping(path = "/PurchaseOrders/{id}/ReceiveMaterial")
    public ResponseEntity<?> receiveMaterial(@PathVariable int id){
        //region validation
        //check if purchase order exists
        Optional<PurchaseOrder> purchaseOrderOptional = purchaseOrderService.getPurchaseOrder(id);
        if (purchaseOrderOptional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        PurchaseOrder purchaseOrder = purchaseOrderOptional.get();
        //check if transaction valid
        if(purchaseOrder.isReceived()){
            return ResponseEntity.badRequest().build();
        }
        //TODO check if inventory balance is more than grand total
        //endregion

        purchaseOrderService.receiveMaterialTransactions(purchaseOrder);

        //region return
        return ResponseEntity.status(HttpStatus.CREATED).build();
        //endregion
    }

    @GetMapping(value = "/PurchaseOrders/report/pdf")
    public ResponseEntity<InputStreamResource> exportToPdf() throws IOException
    {
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; " +
                "filename=purchaseOrderReport" +
                ".pdf");

        IReportGenerator pdfReportGenerator = new PdfReportGenerator();
        purchaseOrderService.accept(pdfReportGenerator);
        ByteArrayInputStream inputStream = pdfReportGenerator.getInputStream();

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(inputStream));
    }

    @GetMapping("/PurchaseOrders/report/csv")
    public void exportToCSV(HttpServletResponse response)
            throws IOException
    {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue =
                "attachment; filename=purchaseOrdersReport_" + currentDateTime +
                        ".csv";
        response.setHeader(headerKey, headerValue);

        IReportGenerator csvReportGenerator = new CsvReportGenerator();
        csvReportGenerator.setResponse(response);
        purchaseOrderService.accept(csvReportGenerator);
    }
}