package com.soen390.erp.quality.service;

import java.util.List;

public interface Handler {
    void setNext(Handler handler);
    void handle(List<String> currentMonthRecord);
}
