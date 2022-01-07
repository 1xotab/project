package com.foxminded.division.formatter;

import com.foxminded.division.model.DivisionResult;
import com.foxminded.division.model.DivisionStep;

public class IntegerDivisionFormatter {
    private static final String SPACE = " ";
    private static final String MINUS = "_";
    private static final String NEWLINE = "\n";
    private static final String DASH = "-";
    private static final String VERTICALLINE = "|";

    public String formatDivisionResult(DivisionResult divisionResult) {
        return createDivisionHeader(divisionResult) + createDivisionBody(divisionResult);
    }

    private String createDivisionHeader(DivisionResult divisionResult) {

        int dividend = divisionResult.getDividend();
        int divisor = divisionResult.getDivisor();
        int quotient = divisionResult.getQuotient();
        int firstStepDivisorMultiple = divisionResult.getSteps().get(0).getDivisorMultiple();
        int spaceAmountToEndLine = calculateNumberLength(divisionResult.getDividend()) - calculateNumberLength(firstStepDivisorMultiple);
        int dashAmountBelowDivisorMultiple = calculateNumberLength(firstStepDivisorMultiple);
        int dashAmountInAnswer = calculateDashAmount(quotient, divisor);
        int indentAmountBeforeZero = calculateNumberLength(dividend);

        if (dividend < divisor) {
            spaceAmountToEndLine = 0;
        } else {
            indentAmountBeforeZero = 1;
        }
        if (divisor < 0 || quotient < 0) {
            dashAmountInAnswer++;
        }

        return MINUS + dividend + VERTICALLINE + divisor
            + NEWLINE
            + SPACE.repeat(indentAmountBeforeZero) + firstStepDivisorMultiple + SPACE.repeat(spaceAmountToEndLine) + VERTICALLINE + DASH.repeat(dashAmountInAnswer)
            + NEWLINE
            + SPACE.repeat(indentAmountBeforeZero) + DASH.repeat(dashAmountBelowDivisorMultiple) + SPACE.repeat(spaceAmountToEndLine) + VERTICALLINE + quotient;
    }
    private String createDivisionBody(DivisionResult divisionResult) {
        StringBuilder result = new StringBuilder();
        int indent = 2;
        int lastStepIndex = divisionResult.getSteps().size() - 1;

        for (int i = 1; i < divisionResult.getSteps().size()-1; i++, indent++) {
            result.append(formatStep(divisionResult.getSteps().get(i), indent));
        }
        result.append(NEWLINE);
        result.append(SPACE.repeat(indent - 1));
        result.append(divisionResult.getSteps().get(lastStepIndex).getRemainder());

        return result.toString();
    }

    private String formatStep(DivisionStep step, int indent) {
        int dividendLength = calculateNumberLength(step.getElementaryDividend());

        return NEWLINE
            + SPACE.repeat(indent - 1)
            + MINUS + step.getElementaryDividend()
            + NEWLINE
            + SPACE.repeat(indent)
            + step.getDivisorMultiple()
            + NEWLINE
            + SPACE.repeat(indent)
            + DASH.repeat(dividendLength);
    }

    private int calculateNumberLength(int number) {
        return String.valueOf(Math.abs(number)).length();
    }

    private int calculateDashAmount(int quotient, int divisor) {
        return Math.max(calculateNumberLength(quotient),
            calculateNumberLength(divisor));

    }
    public static int additionalSpacesCalculator(int[] dividendDigits,int start){
        int i = start;
        int counter=2;
        while (dividendDigits[i]==0){
            counter++;
            i++;
        }
        return counter;
    }
}

