package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.model.SaleOrder;
import com.soen390.erp.accounting.report.GeneratePDFReport;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class SaleOrderController {
    private final SaleOrderService saleOrderService;
    private final AccountService accountService;
    private final LedgerService ledgerService;


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

    @GetMapping(value = "/SaleOrders/report/pdf")
    public ResponseEntity<InputStreamResource> exportPdf() {

        List<SaleOrder> saleOrders = saleOrderService.getAllSaleOrders();

        ByteArrayInputStream bis =
                GeneratePDFReport.saleOrderReport(saleOrders);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=saleOrderReport" +
                ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

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
}