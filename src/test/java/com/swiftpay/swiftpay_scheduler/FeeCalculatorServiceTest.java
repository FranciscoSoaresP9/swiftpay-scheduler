package com.swiftpay.swiftpay_scheduler;

import com.swiftpay.swiftpay_scheduler.entity.fee.FeeRange;
import com.swiftpay.swiftpay_scheduler.service.fee.FeeCalculatorService;
import com.swiftpay.swiftpay_scheduler.service.fee.FeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class FeeCalculatorServiceTest {

    @Mock
    private FeeService feeService;

    @InjectMocks
    private FeeCalculatorService feeCalculatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldApplyFixedAndPercentageFee_WhenAmountIs1000_AndDateIsToday() {
        var amount = new BigDecimal("1000");
        var date = LocalDate.now();
        var mockFeeRange = new FeeRange(0, 0, new BigDecimal("3"), new BigDecimal("3"));

        when(feeService.getFeeRange(amount, date)).thenReturn(Optional.of(mockFeeRange));

        var result = feeCalculatorService.calculateTotalAmountWithFee(amount, date);

        var expected = new BigDecimal("1033.00");
        assertEquals(expected, result, "The calculated total amount should be 1033.00");
    }

    @Test
    void shouldApplyOnlyPercentageFee_WhenAmountIs1350_AndDateIsInFiveDays() {
        var amount = new BigDecimal("1350");
        var date = LocalDate.now().plusDays(5);
        var mockFeeRange = new FeeRange(1, 10, new BigDecimal("0"), new BigDecimal("9"));

        when(feeService.getFeeRange(amount, date)).thenReturn(Optional.of(mockFeeRange));

        var result = feeCalculatorService.calculateTotalAmountWithFee(amount, date);

        var expected = new BigDecimal("1471.50");
        assertEquals(expected, result, "The calculated total amount should be 1471.50");
    }

    @Test
    void shouldApplyOnlyPercentageFee_WhenAmountIs2010_AndDateIsInTwelveDays() {
        var amount = new BigDecimal("2010");
        var date = LocalDate.now().plusDays(12);
        var mockFeeRange = new FeeRange(11, 20, new BigDecimal("0"), new BigDecimal("8.2"));

        when(feeService.getFeeRange(amount, date)).thenReturn(Optional.of(mockFeeRange));

        var result = feeCalculatorService.calculateTotalAmountWithFee(amount, date);

        var expected = new BigDecimal("2174.82");
        assertEquals(expected, result, "The calculated total amount should be 2174.82");
    }

    @Test
    void shouldApplyOnlyPercentageFee_WhenAmountIs2800_AndDateIsInTwentyNineDays() {
        var amount = new BigDecimal("2800");
        var date = LocalDate.now().plusDays(29);
        var mockFeeRange = new FeeRange(21, 30, new BigDecimal("0"), new BigDecimal("6.9"));

        when(feeService.getFeeRange(amount, date)).thenReturn(Optional.of(mockFeeRange));

        var result = feeCalculatorService.calculateTotalAmountWithFee(amount, date);

        var expected = new BigDecimal("2993.20");
        assertEquals(expected, result, "The calculated total amount should be 2993.2");
    }

    @Test
    void shouldApplyOnlyPercentageFee_WhenAmountIs9980_AndDateIsInFortyDays() {
        var amount = new BigDecimal("9980");
        var date = LocalDate.now().plusDays(40);
        var mockFeeRange = new FeeRange(31, 40, new BigDecimal("0"), new BigDecimal("4.7"));

        when(feeService.getFeeRange(amount, date)).thenReturn(Optional.of(mockFeeRange));

        var result = feeCalculatorService.calculateTotalAmountWithFee(amount, date);

        var expected = new BigDecimal("10449.06");
        assertEquals(expected, result, "The calculated total amount should be 10449,06");
    }

    @Test
    void shouldApplyOnlyPercentageFee_WhenAmountIs6050_AndDateIsMoreThanFortyDays() {
        var amount = new BigDecimal(6050);
        var date = LocalDate.now().plusDays(41);
        var mockFeeRange = new FeeRange(41, null, new BigDecimal("0"), new BigDecimal("1.70"));

        when(feeService.getFeeRange(amount, date)).thenReturn(Optional.of(mockFeeRange));

        var result = feeCalculatorService.calculateTotalAmountWithFee(amount, date);

        var expected = new BigDecimal("6152.85");
        assertEquals(expected, result, "The calculated total amount should be 6152,85");
    }

    @Test
    void shouldReturnSameAmount_WhenNoFeeIsApplied() {
        var amount = new BigDecimal("500.00");
        LocalDate date = LocalDate.now();

        when(feeService.getFeeRange(amount, date)).thenReturn(Optional.empty());

        var result = feeCalculatorService.calculateTotalAmountWithFee(amount, date);

        assertEquals(amount, result, "If there is no fee, the final amount should be the same as the original");
    }
}
