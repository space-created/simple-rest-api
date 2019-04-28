package ru.softwerke.practice.app2019.util;

import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class LongParam {
    private static final String FORMAT = "the long number format";
    private final long longValue;
    private final String typeField;
    private final String typeQuery;
    
    public LongParam(String longValueStr, String typeField, String typeQuery) throws WebApplicationException {
        QueryUtils.checkLengthOfHeaderFields(longValueStr, typeField, typeQuery);
        if (StringUtils.isBlank(longValueStr)) {
            Response response = QueryUtils.
                    getResponseWithMessage(Response.Status.BAD_REQUEST,
                            QueryUtils.EMPTY_VALUE_TYPE_ERROR,
                            QueryUtils.getEmptyOrNullParamsMessage(typeField, typeQuery));
            throw new WebApplicationException(response);
        }
        try {
            long temp = Long.valueOf(longValueStr);
            if (temp >= 0) {
                this.longValue = temp;
                this.typeField = typeField;
                this.typeQuery = typeQuery;
            } else {
                Response response = QueryUtils.
                        getResponseWithMessage(Response.Status.BAD_REQUEST,
                                QueryUtils.INVALID_FORMAT_TYPE_ERROR,
                                QueryUtils.getNegativeNumberErrorMessage(longValueStr, typeField, typeQuery));
                throw new WebApplicationException(response);
            }
        } catch (NumberFormatException e) {
            Response response = QueryUtils.
                    getResponseWithMessage(Response.Status.BAD_REQUEST,
                            QueryUtils.INVALID_FORMAT_TYPE_ERROR,
                            QueryUtils.getInvalidFormatMessage(longValueStr, typeField, typeQuery, FORMAT));
            throw new WebApplicationException(response);
        }
        
    }
    
    public long getLongValue() {
        return longValue;
    }
    
    public String getTypeField() {
        return typeField;
    }
    
    public String getTypeQuery() {
        return typeQuery;
    }
}
