package com.sincetimes.statisticweb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


/**
 * @author MoonKuma
 * @since 2019/4/25
 *
 * Used for accepting standard query html request parameters and reconstruct into an object
 * It also returns a sql where clause and jdbc parameters
 *
 * Request:
 * {
 *     "startDate" : "2019-04-01",
 *     "endDate" : "2019-04-05",
 *     "checkDate" : "2019-04-03",
 *     "channel" : "1006",
 *     "channel" : "1007",
 *     "channel" : "1008",
 *     "zoneid" : "10001",
 *     "zoneid" : "10001",
 *     "zoneid" : "10001"
 * }
 *
 * Such object will initialized when construct
 */

@Data
public class StandardQueryRequest {

    private Date startDate = null;
    private Date endDate = null;
    private Date checkDate = null;
    private String[] channels = null;
    private String[] zoneids = null;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void setStartDate(String inputDate){
        Date datePre = null;
        if (inputDate != null && (!inputDate.trim().equalsIgnoreCase("null"))) {
            try {
                datePre = sdf.parse(inputDate.trim());
            } catch (Exception e) {
            }
        }
        if (datePre == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -3);
            datePre = calendar.getTime();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datePre);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        this.startDate = calendar.getTime();
    }

    public void setEndDate(String inputDate){
        Date datePre = null;
        if (inputDate != null && (!inputDate.trim().equalsIgnoreCase("null"))) {
            try {
                datePre = sdf.parse(inputDate.trim());
            } catch (Exception e) {
            }
        }
        if (datePre == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            datePre = calendar.getTime();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datePre);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        this.endDate = calendar.getTime();
    }

    public void setCheckDate(String inputDate)  {
        if (inputDate != null && (!inputDate.trim().equalsIgnoreCase("null"))) {
            try {
                Date datePre = sdf.parse(inputDate.trim());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(datePre);
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                this.checkDate = calendar.getTime();
            } catch (Exception e) {
            }
        }
    }

    public void setChannels(String[] inputChannels){
          this.channels = inputChannels==null?new String[]{"-1"}:inputChannels;
    }

    public void setZoneids(String[] inputZoneids){
        this.zoneids = inputZoneids==null?new String[]{"-1"}:inputZoneids;
    }


    // generate where clause for sql
    // adding parameter into parameters
    // eg:
    // where date between (:startDate) and (:startDate)
    public String getWhereClause(MapSqlParameterSource parameters){
        String whereClause = " where";
        if(checkDate!=null){
            whereClause = whereClause + " date=(:checkDate)";
            parameters.addValue("checkDate", new java.sql.Date(getCheckDate().getTime()));
        }else if (startDate!=null && endDate!=null){
            whereClause = whereClause + " date between (:startDate) and (:endDate)";
            parameters.addValue("startDate", new java.sql.Date(getStartDate().getTime()));
            parameters.addValue("endDate", new java.sql.Date(getEndDate().getTime()));
        }
        if (channels.length>0 && !"-1".equals(channels[0])) {
            whereClause = whereClause + " AND channel in (:channels) ";
            parameters.addValue("channels", Arrays.asList(channels));
        }
        if (zoneids.length>0 && !"-1".equals(zoneids[0])) {
            whereClause += " AND zoneid in (:zoneids) ";
            parameters.addValue("zoneids",Arrays.asList(zoneids));
        }
        if (" where".equals(whereClause)){
            return "";
        }
        else{
            return whereClause;
        }
    }


}
