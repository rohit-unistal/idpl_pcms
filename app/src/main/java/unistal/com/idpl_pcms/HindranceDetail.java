package unistal.com.idpl_pcms;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HindranceDetail implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;

        @SerializedName("activity_affected")
        @Expose
        private String activityAffected;
        @SerializedName("report_date")
        @Expose
        private String reportDate;
        @SerializedName("report_no")
        @Expose
        private String reportNo;
        @SerializedName("chainage_from")
        @Expose
        private String chainageFrom;
        @SerializedName("chainage_to")
        @Expose
        private String chainageTo;
        @SerializedName("area_hold")
        @Expose
        private String areaHold;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("date_from")
        @Expose
        private String dateFrom;
        @SerializedName("date_to")
        @Expose
        private String dateTo;
        @SerializedName("remarks")
        @Expose
        private String remarks;
       /* @SerializedName("SectionID")
        @Expose
        private String sectionID;
        @SerializedName("UserID")
        @Expose
        private String userID;*/
        @SerializedName("responsibility")
        @Expose
        private String responsibility;
        @SerializedName("duration")
        @Expose
        private String duration;
        @SerializedName("weather")
        @Expose
        private String weather;
        @SerializedName("status")
        @Expose
        private String status;
    @SerializedName("imagepath")
    @Expose
    private String imagepath;
    public HindranceDetail(String activityAffected, String id, String reportDate, String reportNo, String chainageFrom, String chainageTo, String areaHold, String description, String dateFrom , String dateTo, String remarks, String responsibility, String duration, String weather, String status, String imagepath)
    {
        this.activityAffected = activityAffected;
        this.id = id;
        this.reportDate = reportDate;
        this.reportNo = reportNo;
        this.chainageFrom = chainageFrom;
        this.chainageTo = chainageTo;
        this.areaHold = areaHold;
        this.description = description;
        this.dateFrom  = dateFrom ;
        this.dateTo = dateTo;
        this.remarks = remarks;
        this.responsibility = responsibility;
        this.duration = duration;
        this.weather = weather;
        this.status = status;
        this.imagepath = imagepath;
    }

        public String getActivityAffected() {
            return activityAffected;
        }

        public void setActivityAffected(String activityAffected) {
            this.activityAffected = activityAffected;
        }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

        public String getReportDate() {
            return reportDate;
        }

        public void setReportDate(String reportDate) {
            this.reportDate = reportDate;
        }

        public String getReportNo() {
            return reportNo;
        }

        public void setReportNo(String reportNo) {
            this.reportNo = reportNo;
        }

        public String getChainageFrom() {
            return chainageFrom;
        }

        public void setChainageFrom(String chainageFrom) {
            this.chainageFrom = chainageFrom;
        }

        public String getChainageTo() {
            return chainageTo;
        }

        public void setChainageTo(String chainageTo) {
            this.chainageTo = chainageTo;
        }

        public String getAreaHold() {
            return areaHold;
        }

        public void setAreaHold(String areaHold) {
            this.areaHold = areaHold;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDateFrom() {
            return dateFrom;
        }

        public void setDateFrom(String dateFrom) {
            this.dateFrom = dateFrom;
        }

        public String getDateTo() {
            return dateTo;
        }

        public void setDateTo(String dateTo) {
            this.dateTo = dateTo;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

      /*  public String getSectionID() {
            return sectionID;
        }

        public void setSectionID(String sectionID) {
            this.sectionID = sectionID;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }*/

        public String getResponsibility() {
            return responsibility;
        }

        public void setResponsibility(String responsibility) {
            this.responsibility = responsibility;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

}
