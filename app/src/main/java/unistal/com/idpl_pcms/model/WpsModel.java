package unistal.com.idpl_pcms.model;

public class WpsModel {


    private String status;

    private String wpsID;

    private String wPSName;

    /*public WpsModel(String wpsID, String wPSName)
    {
        this.status= "success";
        this.wpsID = wpsID;
        this.wPSName = wPSName;
    }
*/


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWpsID() {
        return wpsID;
    }

    public void setWpsID(String wpsID) {
        this.wpsID = wpsID;
    }

    public String getWPSName() {
        return wPSName;
    }

    public void setWPSName(String wPSName) {
        this.wPSName = wPSName;
    }
}
