package com.soen390.erp.accounting.controller;

import com.soen390.erp.accounting.model.SaleOrder;
import com.soen390.erp.accounting.report.GeneratePDFReport;
import com.soen390.erp.accounting.repository.SaleOrderRepository;
import com.soen390.erp.accounting.service.AccountService;
import com.soen390.erp.accounting.service.LedgerService;
import com.soen390.erp.accounting.service.SaleOrderService;
import com.soen390.erp.configuration.ResponseEntityWrapper;
import com.soen390.erp.configuration.service.LogService;
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
public class SaleOrderController {
    private final SaleOrderService saleOrderService;
    private final LogService logService;
    private static final String category = "accounting";
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
            logService.addLog("Retrieved sale order with id "+id+".", category);
            return ResponseEntity.ok().body(saleOrder.get());
        }else{
            logService.addLog("Failed to retrieved client with id "+id+". Doesn't exist.", category);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/SaleOrders/report/pdf")
    public ResponseEntity<InputStreamResource> exportPdf() {

        List<SaleOrder> saleOrders = saleOrderService.getAllSaleOrders();

        ByteArrayInputStream bis =
                GeneratePDFReport.saleOrderReport(saleOrders);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "inline; filename=saleOrderReport" +
                ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @PostMapping(path = "/SaleOrders")
    public ResponseEntityWrapper createSaleOrder(@RequestBody SaleOrder saleOrder){
        //TODO: validate input

        saleOrder.setPaid(false);
        saleOrder.setShipped(false);

        boolean isSuccessful = saleOrderService.addSaleOrder(saleOrder);
        if (isSuccessful == true){
            //TODO: debug if id has value
            String message = "Created Sale Order with id " + saleOrder.getId() + ".";
            logService.addLog(message, category);
            return new ResponseEntityWrapper(ResponseEntity.ok().body(saleOrder.getId()), message);
        }else{
            logService.addLog("Failed to create sale order with id "+saleOrder.getId()+".", category);
            return new ResponseEntityWrapper(ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null), "Unable to created new Sale Order.");
        }
    }

    @PutMapping(path = "/SaleOrders/{id}/ReceivePayment")
    public ResponseEntityWrapper receivePayment(@PathVariable int id){

        //region validation
        //check if sale order exists
        Optional<SaleOrder> saleOrderOptional = saleOrderService.getSaleOrder(id);
        if (saleOrderOptional.isEmpty()){
            logService.addLog("Failed to receive payment for sale order with id "+id+". No such sale order exists.", category);
            return new ResponseEntityWrapper(ResponseEntity.badRequest().build(), "Sale Order with id " + id + " could not be found.");
        }
        SaleOrder saleOrder = saleOrderOptional.get();
        //check if transaction valid
        if(saleOrder.isPaid()){
            logService.addLog("Failed to receive payment for sale order with id "+id+". Already paid for.", category);
            return new ResponseEntityWrapper(ResponseEntity.badRequest().build(), "Sale Order with id " + id + " has already been paid.");
        }
        //TODO check if bank balance is more than grand total
        //TODO check if new status is valid
        //endregion

        saleOrderService.receivePaymentTransactions(saleOrder);
        logService.addLog("Received payment for sale order with id "+id+".", category);

        //region return
        return new ResponseEntityWrapper(ResponseEntity.status(HttpStatus.CREATED).build(), "Sale Order with id " + id + " is now paid.");
        //endregion
    }

    @PutMapping(path = "/SaleOrders/{id}/ShipBike")
    public ResponseEntityWrapper shipBike(@PathVariable int id){

        //region validation
        //check if sale order exists
        Optional<SaleOrder> saleOrderOptional = saleOrderService.getSaleOrder(id);
        if (saleOrderOptional.isEmpty()){
            logService.addLog("Failed to ship bike for sale order with id "+id+". No such sale order exists.", category);
            return new ResponseEntityWrapper(ResponseEntity.badRequest().build(), "Unable to find Sale Order with id " + id + ".");
        }
        SaleOrder saleOrder = saleOrderOptional.get();
        //check if transaction valid
        if(saleOrder.isShipped()){
            logService.addLog("Failed to ship bike for sale order with id "+id+". Already shipped.", category);
            return new ResponseEntityWrapper(ResponseEntity.badRequest().build(), "Sale Order with id " + id + " has already been shipped.");
        }
        //TODO check if bank balance is more than grand total
        //TODO check if new status is valid
        //endregion

        saleOrderService.shipBikeTransactions(saleOrder);
        logService.addLog("Shipped bike for sale order with id "+id+".", category);
        //region return
        return new ResponseEntityWrapper(ResponseEntity.ok().build(), "Sale order with id " + id + " is now shipped.");
        //endregion
    }

    @GetMapping("/SaleOrders/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<SaleOrder> listSales = saleOrderRepository.findAll();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Sale Oder ID",  "Plant", "date", "Client",
                "Total Amount", "Discount", "Discount Amount", "Tax",
                "Tax Amount", "Total", "Paid", "Shipped" , "Sale Items"};
        String[] nameMapping = {"id", "plant", "date", "client",  "totalAmount",
                "discount","discountAmount", "tax", "taxAmount", "grandTotal",
                "paid", "shipped", "saleOrderItems" };

        csvWriter.writeHeader(csvHeader);

        for (SaleOrder sale : listSales) {
            csvWriter.write(sale, nameMapping);
        }

        csvWriter.close();

    }
}