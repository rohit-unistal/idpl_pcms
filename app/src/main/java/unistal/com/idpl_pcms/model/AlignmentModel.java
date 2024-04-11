package unistal.com.idpl_pcms.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlignmentModel {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("AlignmentID")
    @Expose
    private String alignmentID;
    @SerializedName("AlignmentName")
    @Expose
    private String alignmentName;

  /*  public AlignmentModel(String alignmentID, String alignmentName)
    {
        this.status= "success";
        this.alignmentID = alignmentID;
        this.alignmentName = alignmentName;
    }*/

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlignmentID() {
        return alignmentID;
    }

    public void setAlignmentID(String alignmentID) {
        this.alignmentID = alignmentID;
    }

    public String getAlignmentName() {
        return alignmentName;
    }

    public void setAlignmentName(String alignmentName) {
        this.alignmentName = alignmentName;
    }

}
