package com.soen390.erp.accounting.controller;


import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.soen390.erp.accounting.exceptions.LedgerNotFoundException;
import com.soen390.erp.accounting.model.Ledger;
import com.soen390.erp.accounting.report.GeneratePDFReport;
import com.soen390.erp.accounting.report.WriteCSVToResponse;
import com.soen390.erp.accounting.model.Account;
import com.soen390.erp.accounting.model.PurchaseOrder;
import com.soen390.erp.accounting.model.SaleOrder;
import com.soen390.erp.accounting.repository.LedgerRepository;
import com.soen390.erp.accounting.service.LedgerModelAssembler;
import com.soen390.erp.accounting.service.LedgerService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class LedgerController {

    private final LedgerRepository ledgerRepository;
    private final LedgerModelAssembler assembler;
    private final LedgerService ledgerService;

    public LedgerController(LedgerRepository ledgerRepository,
                            LedgerModelAssembler assembler,
                            LedgerService ledgerService)
    {

        this.ledgerRepository=ledgerRepository;
        this.assembler=assembler;
        this.ledgerService = ledgerService;
    }

    @GetMapping("/ledger")
    public ResponseEntity<?> all() {

        List<EntityModel<Ledger>> ledger = ledgerService.assembleToModel();

        return ResponseEntity.ok().body(
                CollectionModel.of(ledger, linkTo(methodOn(LedgerController.class).all()).withSelfRel()));
    }

    @GetMapping(path = "/ledger/{id}")
    public ResponseEntity<?> one(@PathVariable int id) {

        Ledger ledger = ledgerRepository.findById(id)
                .orElseThrow(() -> new LedgerNotFoundException(id));

        return ResponseEntity.ok().body(assembler.toModel(ledger));
    }

    @GetMapping(value = "/ledger/report/pdf")
    public ResponseEntity<InputStreamResource> exportPdf() {

        List<Ledger> ledgers = ledgerService.findAllLedgers();

        ByteArrayInputStream bis = GeneratePDFReport.ledgerReport(ledgers);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "inline; filename=ledgersReport" +
                ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/ledger/report/csv")
    public void exportCSV(HttpServletResponse response) throws Exception {

        //set file name and content type
        String filename = "ledgers.csv";

        List<Ledger> ledgers = ledgerService.findAllLedgers();

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");


        WriteCSVToResponse.writeLedgers(response.getWriter(), ledgers);



////        create a csv writer
//        StatefulBeanToCsv<Ledger> writer = new StatefulBeanToCsvBuilder<Ledger>(response.getWriter())
//                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
//                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
//                .withOrderedResults(false)
//                .build();
//
////        write all users to csv file
//        writer.write(ledgers);

    }

    @PostMapping("/ledger")
    public ResponseEntity<?> newTransaction(@RequestBody Ledger ledger){

        // Todo add account transaction / add inventory updates
        EntityModel<Ledger> entityModel = assembler.toModel(ledgerRepository.save(ledger));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @ResponseBody
    @ExceptionHandler(LedgerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String partNotFoundException(LedgerNotFoundException ex){
        return ex.getMessage();
    }

    @GetMapping("/ledger/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Ledger> ledgers = ledgerRepository.findAll();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Ledger ID", "Amount", "date", "debit_amount_id",
                "credit_account_id", "Purchase_oderer_id", "sale_order_id"};
        String[] nameMapping = {"id", "amount", "date", "debitAccount",
                "creditAccount", "purchaseOrder","saleOrder" };

        csvWriter.writeHeader(csvHeader);
        Account debita, credita;
        PurchaseOrder purchaseO;
        SaleOrder saleO;

        for (Ledger ledger : ledgers) {
            csvWriter.write(ledger, nameMapping);
//            debita = ledger.getDebitAccount();
//            credita = ledger.getCreditAccount();
//            purchaseO = ledger.getPurchaseOrder();
//            saleO = ledger.getSaleOrder();
//            csvWriter.write(debita.getId(), "debit_amount_id");
//            csvWriter.write(credita.getId(), "credit_amount_id");
//            csvWriter.write(purchaseO.getId(), "Purchase_oderer_id");
//            csvWriter.write(saleO.getId(), "sale_order_id");
        }

        csvWriter.close();

    }
}
