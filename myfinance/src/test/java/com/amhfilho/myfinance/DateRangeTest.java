package com.amhfilho.myfinance;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateRangeTest {
    @Test
    public void shouldCreateDateRangeWithEndAndStart(){
        final LocalDate date1 = LocalDate.of(2018, Month.OCTOBER, 23);
        final LocalDate date2 = LocalDate.of(2018, Month.OCTOBER, 22);
        DateRange dateRange = new DateRange(date1, date2);
        assertTrue(date2.isEqual(dateRange.getStart()));
        assertTrue(date1.isEqual(dateRange.getEnd()));
    }

    @Test
    public void shouldCreateDateRangeWithStartAndEnd(){
        final LocalDate date2 = LocalDate.of(2018, Month.OCTOBER, 23);
        final LocalDate date1 = LocalDate.of(2018, Month.OCTOBER, 22);
        DateRange dateRange = new DateRange(date1, date2);
        assertTrue(date1.isEqual(dateRange.getStart()));
        assertTrue(date2.isEqual(dateRange.getEnd()));
    }

    @Test
    public void shouldCreateDateRangeWithEqualDates(){
        final LocalDate date2 = LocalDate.of(2018, Month.OCTOBER, 23);
        final LocalDate date1 = LocalDate.of(2018, Month.OCTOBER, 23);
        DateRange dateRange = new DateRange(date1, date2);
        assertTrue(date1.isEqual(dateRange.getStart()));
        assertTrue(date2.isEqual(dateRange.getEnd()));
    }

    @Test
    public void shouldReturnTrueForContainingDateInRange(){
        final LocalDate date1 = LocalDate.of(2018, Month.OCTOBER, 23);
        final LocalDate date2 = LocalDate.of(2018, Month.SEPTEMBER, 29);
        DateRange dateRange = new DateRange(date1,date2);
        assertTrue(dateRange.contains(LocalDate.of(2018, Month.SEPTEMBER, 30)));
        assertTrue(dateRange.contains(LocalDate.of(2018, Month.SEPTEMBER, 29)));
        assertTrue(dateRange.contains(LocalDate.of(2018, Month.OCTOBER, 23)));
        assertFalse(dateRange.contains(LocalDate.of(2018, Month.OCTOBER, 24)));

    }
}
