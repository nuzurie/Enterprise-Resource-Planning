package com.soen390.erp.accounting.report;

import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

import com.soen390.erp.accounting.model.Ledger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.util.List;

public class WriteCSVToResponse {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(WriteCSVToResponse.class);

    public static void writeLedgers(PrintWriter writer, List<Ledger> ledgers) {

        try {

            ColumnPositionMappingStrategy<Ledger> mapStrategy
                    = new ColumnPositionMappingStrategy<>();

            mapStrategy.setType(Ledger.class);

//            String[] columns = new String[]{ "id", "amount", "date",
//                    "credit_account_id", "debit_account_id",
//                    "purchase_order_id",
//                    "sale_order_id" };
            String[] columns = new String[]{ "id", "amount", "date"};
            mapStrategy.setColumnMapping(columns);

            StatefulBeanToCsv<Ledger> btcsv = new StatefulBeanToCsvBuilder<Ledger>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mapStrategy)
                    .withSeparator(',')
                    .build();

            btcsv.write(ledgers);

        } catch (CsvException ex) {

            LOGGER.error("Error mapping Bean to CSV", ex);
        }
    }


}
