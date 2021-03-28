package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.report.GeneratePDFReport;
import com.soen390.erp.accounting.repository.LedgerRepository;
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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    @GetMapping(value = "/PurchaseOrders/report/pdf")
    public ResponseEntity<InputStreamResource> exportPdf() {

        List<PurchaseOrder> purchaseOrders =
                purchaseOrderService.getAllPurchaseOrders();

        ByteArrayInputStream bis =
                GeneratePDFReport.purchaseOrderReport(purchaseOrders);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; " +
                "filename=purchaseOrderReport" +
                ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
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

    @GetMapping("/PurchaseOrders/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<PurchaseOrder> listPurchases = purchaseOrderRepository.findAll();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Purchase Oder ID", "Plant", "date", "Supplier",
                "Total Amount", "discount", "Discount Amount", "Tax",
                "Tax Amount", "Paid", "Received" , "Purchased Items"};
        String[] nameMapping = {"id", "plant", "date", "supplier", "totalAmount",
                "discount","discountAmount", "tax", "taxAmount", "grandTotal",
                "paid", "received", "purchaseOrderItems" };

        csvWriter.writeHeader(csvHeader);

        for (PurchaseOrder purchase : listPurchases) {
            csvWriter.write(purchase, nameMapping);
        }

        csvWriter.close();

    }
}