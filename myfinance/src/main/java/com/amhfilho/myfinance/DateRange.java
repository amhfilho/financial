package com.amhfilho.myfinance;

import java.time.LocalDate;

/**
 * Represents an interval between 2 dates. Provides specific methods that the java DateTime api does not
 */
public class DateRange {
    private LocalDate start;
    private LocalDate end;

    public DateRange(LocalDate date1, LocalDate date2){
        if(date1.isBefore(date2) || date1.isEqual(date2)) {
            start = date1;
            end = date2;
        }
        else {
            start = date2;
            end = date1;
        }
    }

    public boolean contains(LocalDate date){
        return ((date.isEqual(start) || date.isAfter(start)) &&  (date.isBefore(end) || date.isEqual(end)));
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}
