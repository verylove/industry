package com.fable.industry.api.export;

import java.util.ArrayList;
import java.util.List;

import com.fable.industry.api.export.ExcelLog;
import org.apache.commons.lang3.StringUtils;

/**
 * The <code>ExcelLogs</code>
 * 
 * @author sargeras.wang
 * @version 1.0, Created at 2013年9月17日
 */
public class ExcelLogs {
    private Boolean hasError;
    private List<com.fable.industry.api.export.ExcelLog> logList;

    /**
     * 
     */
    public ExcelLogs() {
        super();
        hasError = false;
    }

    /**
     * @return the hasError
     */
    public Boolean getHasError() {
        return hasError;
    }

    /**
     * @param hasError
     *            the hasError to set
     */
    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }

    /**
     * @return the logList
     */
    public List<com.fable.industry.api.export.ExcelLog> getLogList() {
        return logList;
    }

    public List<com.fable.industry.api.export.ExcelLog> getErrorLogList() {
        List<com.fable.industry.api.export.ExcelLog> errList = new ArrayList<>();
        for (com.fable.industry.api.export.ExcelLog log : this.logList) {
            if (log != null && StringUtils.isNotBlank(log.getLog())) {
                errList.add(log);
            }
        }
        return errList;
    }

    /**
     * @param logList
     *            the logList to set
     */
    public void setLogList(List<ExcelLog> logList) {
        this.logList = logList;
    }

}
