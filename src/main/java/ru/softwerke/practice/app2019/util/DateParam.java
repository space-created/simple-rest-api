package ru.softwerke.practice.app2019.util;

import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateParam {
    private static final String PATTERN = "dd.MM.yyyy";
    private static final String FORMAT = "the date format: " + PATTERN;
    private final LocalDate date;
    private final String typeQuery;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN).withLocale(Locale.getDefault());
    
    public DateParam(String dateStr, String typeField, String typeQuery) throws WebApplicationException {
        QueryUtils.checkLengthOfHeaderFields(dateStr, typeField, typeQuery);
        if (StringUtils.isBlank(dateStr)) {
            Response response = QueryUtils.
                    getResponseWithMessage(Response.Status.BAD_REQUEST,
                            QueryUtils.EMPTY_VALUE_TYPE_ERROR,
                            QueryUtils.getEmptyOrNullParamsMessage(typeField, typeQuery));
            throw new WebApplicationException(response);
        }
        
        try {
            this.date = LocalDate.parse(dateStr, formatter);
            this.typeQuery = typeQuery;
        } catch (DateTimeParseException e) {
            Response response = QueryUtils.
                    getResponseWithMessage(Response.Status.BAD_REQUEST,
                            QueryUtils.INVALID_FORMAT_TYPE_ERROR,
                            QueryUtils.getInvalidFormatMessage(dateStr, typeField, typeQuery, FORMAT));
            throw new WebApplicationException(response);
        }
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public String getTypeQuery() {
        return typeQuery;
    }
}