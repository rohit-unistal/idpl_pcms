package unistal.com.idpl_pcms.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DataBaseName="idpl_pcms";
    private static final int DB_VERSION = 11;
    private static String TABLE_NAME="defaul_setting";
    private static final String COLUMN_ID = "id";
    private static String Loop_Runing_Status="status";

    private static String ROU_TABLE="rou_table";
    private static String USER_ID="user_id";
    private static String SECTION_ID="sectionID";
    private static String TYPE="type";
    private static String DATE="date";
    private static String REPORT_NO="reportno";
    private static String CHAINAGE_FROM="chainage_from";
    private static String CHAINAGE_TO="chainage_to";
    private static String IP_FROM="IPFrom";
    private static String IP_TO="IPTo";
    private static String TP_FROM="TPFrom";
    private static String TP_TO="TPTo";

    private static String VILLAGE="village";
    private static String TEHSIL="tehsil";
    private static String DISTRICT="district";
    private static String Skipping_detail="Skipping_detail";
    private static String RPanchnama="RPanchnama";
    private static String Obstacle="Obstacle";
    private static String REMARK="remark";
    private static String PHOTO="Photo";


    private static String ROW_Table="ROW_Table";
    private static String NEW_ROW_Table="NEW_ROW_Table";
    private static String GROUND_TYPE="ground_type";
    private static String TYPE_ANODE="Type_Anode";
    private static String Alignment_Sheet="Alignment_Sheet";
    private static String namestructre="namestructre";
    private static String tpremark="tpremark";
    private static String bearingangle="bearingangle";
    private static String Ipno="Ipno";
    private static String IpChainage="IpChainage";
    private static String terrian="terrian";


    private static String STRUCTURE_DETAILS="structure_details";
    private static String DetailsStructure="DetailsStructure";
    private static String CNG_Table="CNG_Table";
    private static String work_interrupt="work_interruption";
    private static String Length="Length";
    private static String Description="Description";
    private static String IP_No="IP_No";
    private static String Rows="Rows";
    private static String TN_RowStatus="TN_RowStatus";

    private static String STRINGING_Table="STRINGING_Table";
    private static String TN_Damage="TN_Damage";
    private static String CorrosionType="CorrosionType";
    private static String PIPE_ID="PIPE_ID";
    private static String LATITUDE="LATITUDE";
    private static String LONGITUDE="LONGITUDE";
    private static String CONCRETE_COTING="CONCRETE_COTING";

    private static String BENDING_TABLE="bending_table";
    private static String CHAINAGE="chainage";
    private static String Bend_Number="Bend_Number";
    private static String PIPE_NO="PipeNo";
    private static String TYPE_OF_BEND="TypeOfBend";
    private static String BEND_DEGREE="Bend_Degree";
    private static String BEND_MIN="Bend_Min";
    private static String BEND_SEC="Bend_sec";
    private static String TP_NO="TPno";
    private static String VISUAL_CHECK="Visuals";
    private static String pre_heating="pre_heating";
    private static String DISBONDING_CHECK="Disbonding";
    private static String GUAGING_CHECK="Guaging";
    private static String HOLIDAY_CHECK="Holiday";
    private static String OVALITY_CHECK="Ovality";
    private static String TN_Manpower="TN_Manpower";
    private static String TN_Machineries="TN_Machineries";
    private static String latitude="latitude";
    private static String longitude="longitude";
    private static String HDPE_TABLE="HDPE_TABLE";
    private static String JOINT_FROM="joint_from";
    private static String JOINT_TO="joint_to";
    private static String PADDING_CHECK="padding_check";
    private static String WARNING_MAT="warning_mat";
    private static String CoupleNo="CoupleNo";
    private static String duct_length="duct_length";
    private static String duct_length_to="duct_length_to";


    private static String OFCBLOWING_TABLE="ofcblowing_Table";
    private static String DRUM_NO="Drumno";
    private static String DRUM_LENGTH="Drum_lenth";
    private static String LOOP_AT_PIT="loopatpit";
    private static String PIT_CABLE_LENGTH="Pitcabel_lenth";
    private static String blowingpit="blowingpit";
    private static String sequencemeter="sequencemeter";





    private static String TRENCHING_TABLE="trenching_table";
    private static String FROM_KM="from_km";
    private static String FROM_JOINT_TYPE="from_joint_type";
    private static String FROM_JOINT_NUBER="from_joint_no";
    private static String FROM_SUFFIX="from_suffix";
    private static String TO_KM="to_km";
    private static String TO_JOINT_TYPE="to_joint_type";
    private static String TO_JOINT_NUBER="to_joint_no";
    private static String TO_SUFFIX="to_suffix";
    private static String SECTION_LENGTH="section_length";
    private static String DEPTH="depth";
    private static String WIDTH="width";
    private static String WIDTH_AT_BOTTOM="width_at_bottom";
    private static String TypeofTerrain="TypeofTerrain";

    private static String seperation_soil="seperation_soil";
    private static String provision_sign="provision_sign";
    private static String verification_area="verification_area";
    private static String avability_cover="avability_cover";
    private static String trench_profile="trench_profile";


    private static String WELDING_Table="WELDING_Table";
    private static String LEFT_PIPE_ID="left_pipe_id";
    private static String RIGHT_PIPE_ID="right_pipe_id";
    private static String JOINT_ID="joint_id";
    private static String WPS_NO="WPSNo";

    private static String RWELDER1="root_welder_1";
    private static String RWELDER2="root_welder_2";
    private static String HWELDER1="hot_welder_1";
    private static String HWELDER2="hot_welder_2";
    private static String F1WELDER1="filler1_welder_1";
    private static String F1WELDER2="filler1_welder_2";
    private static String F2WELDER1="filler2_welder_1";
    private static String F2WELDER2="filler2_welder_2";

    private static String CWELDER1="cap_welder_1";
    private static String CWELDER2="cap_welder_2";


    private static String CWELDER3="cap_welder_3";
    private static String CWELDER4="cap_welder_4";



    private static String F3WelderNumber1="F3WelderNumber1";
    private static String F3WelderNumber2="F3WelderNumber2";

    private static String electrode_e6010_dia="electrode_e6010_dia";
    private static String electrode_e6010_batch="electrode_e6010_batch";
    private static String electrode_e8010_dia="electrode_e8010_dia";
    private static String electrode_e8010_batch="electrode_e8010_batch";
    private static String electrode_e9045_dia="electrode_e9045_dia";
    private static String electrode_e9045_batch="electrode_e9045_batch";
    private static String electrode_B221868_dia="electrode_B221868_dia";
    private static String electrode_B221868_batch="electrode_B221868_batch";
    private static String electrode_806012_dia="electrode_806012_dia";
    private static String electrode_806012_batch="electrode_806012_batch";
    private static String Pipe_dia="Pipe_dia";
    private static String Pipe_thick="Pipe_thick";

    private static String ChainageFrom="ChainageFrom";
    private static String ChainageTo="ChainageTo";
    private static String Process="Process";
    private static String Material="Material";
    private static String TR_alignment="TR_alignment";

    private static String Fitup="Fitup";
    private static String Weld_Visual="Weld_Visual";


    private static String Details="Details";
    private static String across_row="across_row";











    private static String LOWERING_TABLE="Lowering_Table";
    private static String WallThikness="WallThikness";
    private static String location="location";
    private static String holiday_detector="holiday_detector";
    private static String model="model";
    private static String voltage="voltage";
    private static String calibration="calibration";
    private static String repair_damages="repair_damages";

    private static String PRE_PADDING_CHECK="pre_padding_check";
    private static String POST_PADDING_CHECK="post_padding_check";

    private static String PipeMaterial="PipeMaterial";
    private static String PipeDia="PipeDia";
    private static String Dewatering="Dewatering";
    private static String PaddingMaterial ="PaddingMaterial";
    private static String Weather="Weather";
    private static String Padding="Padding";



    private static String BACKFILLING_TABLE="backfilling_Table";
    private static String PostPadding="PostPadding";
    private static String Slopebreaker="Slopebreaker";
    private static String Plasticgrating="Plasticgrating";
    private static String antibuyo="antibuyo";
    private static String cover="cover";
    private static String warning="warning";
    private static String center_level="center_level";
    private static String Manpower="Manpower";
    private static String Machineries="Machineries";
    private static String TN_HolidayCheck="TN_HolidayCheck";
    private static String TN_SoilRplacment="TN_SoilRplacment";
    private static String TN_SaddleWeight="TN_SaddleWeight";
    private static String TN_RockBackfill="TN_RockBackfill";
    private static String TN_CenterlineCheck="TN_CenterlineCheck";

    private static String TN_coating="TN_coating";
    private static String chfrom="chfrom";
    private static String chto="chto";

    private static String DRYING_TABLE="drying_Table";
    private static String START_DATE="start_date";
    private static String END_DATE="end_date";


    private static String RESTORATION_TABLE="restoration_Table";
    private static String LAND_TYPE="land_type";
    private static String SURPLUS_MATERIAL="susplus_material";
    private static String TOP_SOIL="Replacement_topSoil";
    private static String REINSTALLATION_BOUNDRY="ReinstallationBoundry";



    private static String RADIO_GRAPHY_TABLE="radio_graphy_table";
    private static String RESULT="results";
    private static String SEGMENT1="segment1";
    private static String SEGMENT2="segment2";
    private static String SEGMENT3="segment3";
    private static String SEGMENT4="segment4";
    private static String observation="observation";
    private static String observation2="observation2";
    private static String observation3="observation3";
    private static String observation4="observation4";

    private static String RootRepair="RootRepair";
    private static String RWelder="RWelder";
    private static String HotPass="HotPass";
    private static String HWelder="HWelder";
    private static String Filler="Filler";
    private static String FWelder="FWelder";
    private static String Capping="Capping";
    private static String CWelder="CWelder";

    private static String RootRepair2="RootRepair2";
    private static String RWelder2="RWelder2";
    private static String HotPass2="HotPass2";
    private static String HWelder2="HWelder2";
    private static String Filler2="Filler2";
    private static String FWelder2="FWelder2";
    private static String Capping2="Capping2";
    private static String CWelder2="CWelder2";

    private static String RootRepair3="RootRepair3";
    private static String RWelder3="RWelder3";
    private static String HotPass3="HotPass3";
    private static String HWelder3="HWelder3";
    private static String Filler3="Filler3";
    private static String FWelder3="FWelder3";
    private static String Capping3="Capping3";
    private static String CWelder3="CWelder3";
    private static String RootRepair4="RootRepair4";
    private static String RWelder4="RWelder4";
    private static String HotPass4="HotPass4";
    private static String HWelder4="HWelder4";
    private static String Filler4="Filler4";
    private static String FWelder4="FWelder4";
    private static String Capping4="Capping4";
    private static String CWelder4="CWelder4";
    private static String penetrameter="penetrameter";
    private static String film_type="film_type";
    private static String sensitivity="sensitivity";
    private static String gray_value="gray_value";
    private static String thickness="thickness";

    private static String ChaninageTo="ChaninageTo";
    private static String source="source";

    private static String UT_TABLE="ut_table";

    private static String JOINT_COATING_TABLE="joint_coating";

    private static String body_3="body_3";
    private static String body_6="body_6";
    private static String body_9="body_9";
    private static String body_12="body_12";
    private static String body_avg="body_avg";
    private static String bodyu_3="bodyu_3";
    private static String bodyu_6="bodyu_6";
   private static String bodyu_9="bodyu_9";
    private static String bodyu_12="bodyu_12";
    private static String bodyu_avg="bodyu_avg";
    private static String joint_3="joint_3";
    private static String joint_6="joint_6";
    private static String joint_9="joint_9";
    private static String joint_12="joint_12";
    private static String joint_avg="joint_avg";

    private static String Coating_Thick="Coating_Thick";

    private static String Thickness="Thickness";

    private static String Primer_A_Batch="Primer_A_Batch";
    private static String Primer_B_Batch="Primer_B_Batch";
    private static String Holiday_Tester="Holiday_Tester";
    private static String Type_Coating="Type_Coating";
    private static String Sleave="Sleave";
    private static String Callibration="Callibration";
    private static String BatchNo="BatchNo";
    private static String TN_RH = "TN_RH";
    private static String TN_Elcometer = "TN_Elcometer";
    private static String TN_PeelTest = "TN_PeelTest";
    private static String Roughness = "Roughnesss";
    private static String Dust = "Dust";
    private static String Profile = "Profile";
    private static String WFT = "WFT";


    private static String BODY_TEMP="body_temp";
    private static String WELD_TEMP="weld_temp";


    private static String pipe_dia="pipe_dia";
    private static String pipe_thickness="pipe_thickness";
    private static String batch_no="batch_no";
    private static String roughness="roughness";
    private static String dust_containment="dust_containment";
    private static String primer="primer";
    private static String primer_b="primer_b";
    private static String onbody="onbody";
    private static String onseam="onseam";
    private static String sleeve_option="sleeve_option";
    private static String coating_type="coating_type";
    private static String relative_humidity="relative_humidity";








    private static String Levelling_TABLE="levelling";
    private static String Cover="Cover";
    private static String XCord="XCord";
    private static String YCord="YCord";
    private static String ZCord="ZCord";
    private static String NGL="NGL";

    private static String ChFrom="ChFrom";
    private static String ChTo="ChTo";
    private static String Elevation="Elevation";
    private static String ImageData="ImageData";
    private static String JointFrom="JointFrom";
    private static String JointTo="JointTo";
    private static String PipeThik="PipeThik";
    private static String TpNo="TpNo";
    private static String SectionLength="SectionLength";

    private static String HYDRO_TESTING="hydro_testing_table";
    private static String HYDRO_PLAN_DATE="plan_date";
    private static String HYDRO_PLAN_REPORT_NO="plan_report_no";


    public DataBaseHelper(Context context) {
        super(context, DataBaseName, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String q="CREATE TABLE IF NOT EXISTS "+TABLE_NAME
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Loop_Runing_Status+" VARCHAR(255)"
                +")";

        String rou_query="CREATE TABLE IF NOT EXISTS "+ROU_TABLE
                +" ( "
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255), "
                +SECTION_ID+" VARCHAR(255), "
                +TYPE+" VARCHAR(255), "
                +Weather+" VARCHAR(255),"
                +DATE+" VARCHAR(255), "
                +REPORT_NO+" VARCHAR(255), "
                +CHAINAGE_FROM+" VARCHAR(255), "
                +CHAINAGE_TO+" VARCHAR(255), "
                +terrian+" VARCHAR(255), "
                +VILLAGE+" VARCHAR(255), "
                +TEHSIL+" VARCHAR(255), "
                +DISTRICT+" VARCHAR(255), "
                +DetailsStructure+" VARCHAR(255), "
                +across_row+" VARCHAR(255), "
                +Details+" VARCHAR(255), "
                +CHAINAGE+" VARCHAR(255), "
                +namestructre+" VARCHAR(255), "
                +Ipno+" VARCHAR(255), "
                +Alignment_Sheet+" VARCHAR(255), "
                +Skipping_detail+" VARCHAR(255), "
                +Obstacle+" VARCHAR(255), "
                +RPanchnama+" VARCHAR(255), "
                +PHOTO+" VARCHAR(255), "
                +ImageData+" VARCHAR, "
                +REMARK+" VARCHAR"
                +" ) ";

        String raw="CREATE TABLE IF NOT EXISTS "+ROW_Table
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255), "
                +SECTION_ID+" VARCHAR(255), "
                +TYPE+" VARCHAR(255), "
                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +CHAINAGE_FROM+" VARCHAR(255),"
                +CHAINAGE_TO+" VARCHAR(255),"
                +TYPE_ANODE+" VARCHAR(255),"
                +GROUND_TYPE+" VARCHAR(255),"
                +STRUCTURE_DETAILS+" VARCHAR(255),"
                +REMARK+" VARCHAR(255),"
                +ImageData+" VARCHAR,"
                +PHOTO+" VARCHAR"
                +")";
        String newRaw="CREATE TABLE IF NOT EXISTS "+NEW_ROW_Table
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255), "
                +SECTION_ID+" VARCHAR(255), "
                +TYPE+" VARCHAR(255), "
                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +CHAINAGE_FROM+" VARCHAR(255),"
                +CHAINAGE_TO+" VARCHAR(255),"
                +Alignment_Sheet+" VARCHAR(255),"
                +namestructre+" VARCHAR(255),"
                +Weather+" VARCHAR(255),"
                +tpremark+" VARCHAR(255),"
                +bearingangle+" VARCHAR(255),"
                +Ipno+" VARCHAR(255),"
                +IpChainage+" VARCHAR(255),"
                +terrian+" VARCHAR(255),"
                +CHAINAGE+" VARCHAR(255),"
                +STRUCTURE_DETAILS+" VARCHAR(255),"
                +REMARK+" VARCHAR(255),"
                +DetailsStructure+" VARCHAR(255),"
                +Details+" VARCHAR(255),"
                +across_row+" VARCHAR(255),"
                +ImageData+" VARCHAR,"


                +PHOTO+" VARCHAR"
                +")";

        String cNg ="CREATE TABLE IF NOT EXISTS "+CNG_Table
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255), "
                +SECTION_ID+" VARCHAR(255), "
                +TYPE+" VARCHAR(255), "
                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +CHAINAGE_FROM+" VARCHAR(255),"
                +CHAINAGE_TO+" VARCHAR(255),"
                +Alignment_Sheet+" VARCHAR(255),"
                +namestructre+" VARCHAR(255),"
                +CHAINAGE+" VARCHAR(255),"
                +Weather+" VARCHAR(255),"
                +IP_FROM+" VARCHAR(255),"
                +IP_TO+" VARCHAR(255),"
                +TP_FROM+" VARCHAR(255),"
                +TP_TO+" VARCHAR(255),"
                +GROUND_TYPE+" VARCHAR(255),"
                +DetailsStructure+" VARCHAR(255),"
                +REMARK+" VARCHAR(255),"
                +work_interrupt+" VARCHAR(255),"
                +Length+" VARCHAR(255),"
                +Description+" VARCHAR(255),"
                +IP_No+" VARCHAR(255),"
                +Rows+ " VARCHAR(255),"
                +TN_RowStatus+" VARCHAR(255),"
                +TN_Manpower+" VARCHAR(255),"
                +TN_Machineries+" VARCHAR(255),"
                +ImageData+" VARCHAR,"
                +PHOTO+" VARCHAR"
                +")";



        String qStringing ="CREATE TABLE IF NOT EXISTS "+STRINGING_Table
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255), "
                +SECTION_ID+" VARCHAR(255), "
                +TYPE+" VARCHAR(255), "
                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +Alignment_Sheet+" VARCHAR(255),"
                +Weather+" VARCHAR(255),"
                +PipeDia+" VARCHAR(255),"
                +PipeMaterial+" VARCHAR(255),"
                +CHAINAGE_FROM+" VARCHAR(255),"
                +PIPE_ID+" VARCHAR(255),"
                +LATITUDE+" VARCHAR(255),"
                +LONGITUDE+" VARCHAR(255),"
                +CONCRETE_COTING+" VARCHAR(255),"
                +REMARK+" VARCHAR(255),"
                +TN_Damage+" VARCHAR(255),"
                +CorrosionType+" VARCHAR(255),"
                +ImageData+" VARCHAR,"
                +PHOTO+" VARCHAR"
                +")";



        String qBending="CREATE TABLE IF NOT EXISTS "+BENDING_TABLE
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"
                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +Alignment_Sheet+" VARCHAR(255),"
                +CHAINAGE+" VARCHAR(255),"
                +Bend_Number+" VARCHAR(255),"
                +PIPE_NO+" VARCHAR(255),"
                +TYPE_OF_BEND+" VARCHAR(255),"
                +BEND_DEGREE+" VARCHAR(255),"
                +BEND_MIN+" VARCHAR(255),"
                +BEND_SEC+" VARCHAR(255),"
                +TP_NO+" VARCHAR(255),"
                +VISUAL_CHECK+" VARCHAR(255),"
                +DISBONDING_CHECK+" VARCHAR(255),"
                +GUAGING_CHECK+" VARCHAR(255),"
                +HOLIDAY_CHECK+" VARCHAR(255),"
                +OVALITY_CHECK+" VARCHAR(255),"
                +REMARK+" VARCHAR(255),"
                +Weather+" VARCHAR(255),"
                +TN_Manpower+" VARCHAR(255),"
                +TN_Machineries+" VARCHAR(255),"
                +latitude+" VARCHAR(255),"
                +longitude+" VARCHAR(255),"
                +ImageData+" VARCHAR,"
                +PHOTO+" VARCHAR"
                +")";


        String qHDPE="CREATE TABLE IF NOT EXISTS "+HDPE_TABLE
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"
                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +SECTION_LENGTH+" VARCHAR(255),"
                +CoupleNo+" VARCHAR(255),"
                +JOINT_FROM+" VARCHAR(255),"
                +JOINT_TO+" VARCHAR(255),"
                +Weather+" VARCHAR(255),"
                +CHAINAGE_FROM+" VARCHAR(255),"
                +CHAINAGE_TO+" VARCHAR(255),"
                +duct_length+" VARCHAR(255),"
                +duct_length_to+" VARCHAR(255),"
                +location+" VARCHAR(255),"
                +PADDING_CHECK+" VARCHAR(255),"
                +WARNING_MAT+" VARCHAR(255),"
                +REMARK+" VARCHAR(255),"
                +PHOTO+" VARCHAR"
                +")";

        String queryTrenching="CREATE TABLE IF NOT EXISTS "+TRENCHING_TABLE
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"

                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +Alignment_Sheet+" VARCHAR(255),"
                +CHAINAGE_FROM+" VARCHAR(255),"
                +CHAINAGE_TO+" VARCHAR(255),"
                +WIDTH_AT_BOTTOM+" VARCHAR(255),"
                +TypeofTerrain +" VARCHAR(255),"
                +Weather +" VARCHAR(255),"
                +JOINT_FROM+" VARCHAR(255),"
                +JOINT_TO+" VARCHAR(255),"
                +SECTION_LENGTH+" VARCHAR(255),"
                +seperation_soil+" VARCHAR(255),"
                +provision_sign+" VARCHAR(255),"
                +verification_area+" VARCHAR(255),"
                +avability_cover+" VARCHAR(255),"
                +trench_profile+" VARCHAR(255),"
                +DEPTH+" VARCHAR(255),"
                +WIDTH+" VARCHAR(255),"

                +REMARK+" VARCHAR(255),"
                +ImageData+" VARCHAR,"
                +PHOTO+" VARCHAR"
                +")";



        String qOFCBlowing="CREATE TABLE IF NOT EXISTS "+OFCBLOWING_TABLE
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"

                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +Weather+" VARCHAR(255),"
                +DRUM_NO+" VARCHAR(255),"
                +DRUM_LENGTH+" VARCHAR(255),"


                +sequencemeter+" VARCHAR(255),"
                +blowingpit+" VARCHAR(255),"
                +SECTION_LENGTH+" VARCHAR(255),"

                +JOINT_FROM+" VARCHAR(255),"
                +JOINT_TO+" VARCHAR(255),"

                +LOOP_AT_PIT+" VARCHAR(255),"
                +PIT_CABLE_LENGTH+" VARCHAR(255),"

                +REMARK+" VARCHAR(255),"
                +PHOTO+" VARCHAR"
                +")";
        String qWelding="CREATE TABLE IF NOT EXISTS "+WELDING_Table
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"
                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +LEFT_PIPE_ID+" VARCHAR(255),"
                +RIGHT_PIPE_ID+" VARCHAR(255),"
                +JOINT_ID+" VARCHAR(255),"
                +WPS_NO+" VARCHAR(255),"
                +RWELDER1+" VARCHAR(255),"
                +RWELDER2+" VARCHAR(255),"
                +F1WELDER1+" VARCHAR(255),"
                +F1WELDER2+" VARCHAR(255),"
                +F2WELDER1+" VARCHAR(255),"
                +F2WELDER2+" VARCHAR(255),"


                +HWELDER1+" VARCHAR(255),"
                +HWELDER2+" VARCHAR(255),"
                +CWELDER1+" VARCHAR(255),"
                +CWELDER2+" VARCHAR(255),"
                +CWELDER3+" VARCHAR(255),"
                +CWELDER4+" VARCHAR(255),"
                +F3WelderNumber1+" VARCHAR(255),"
                +F3WelderNumber2+" VARCHAR(255),"


                +electrode_e6010_dia+" VARCHAR(255),"
                +electrode_e6010_batch+" VARCHAR(255),"
                +electrode_e8010_dia+" VARCHAR(255),"
                +electrode_e8010_batch+" VARCHAR(255),"
                +electrode_e9045_dia+" VARCHAR(255),"
                +electrode_e9045_batch+" VARCHAR(255),"
                +electrode_806012_dia+" VARCHAR(255),"
                +electrode_806012_batch+" VARCHAR(255),"
                +electrode_B221868_dia+" VARCHAR(255),"
                +electrode_B221868_batch+" VARCHAR(255),"
                +Pipe_dia+" VARCHAR(255),"
                +Pipe_thick+" VARCHAR(255),"
                +Weather+" VARCHAR(255),"
                +ChainageFrom+" VARCHAR(255),"
                +ChainageTo+" VARCHAR(255),"
                +Process+" VARCHAR(255),"
                +Material+" VARCHAR(255),"
                +TR_alignment+" VARCHAR(255),"
                +Fitup+" VARCHAR(255),"
                +Weld_Visual+" VARCHAR(255),"






                +REMARK+" VARCHAR(255),"
                +ImageData+" VARCHAR,"
                +PHOTO+" VARCHAR"
                +")";
        String qLowering="CREATE TABLE IF NOT EXISTS "+LOWERING_TABLE
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"

                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +Alignment_Sheet+" VARCHAR(255),"
                +CHAINAGE_FROM+" VARCHAR(255),"
                +CHAINAGE_TO+" VARCHAR(255),"
                +SECTION_LENGTH+" VARCHAR(255),"

                +JOINT_FROM+" VARCHAR(255),"
                +JOINT_TO+" VARCHAR(255),"

                +PRE_PADDING_CHECK+" VARCHAR(255),"
                +HOLIDAY_CHECK+" VARCHAR(255),"
                +POST_PADDING_CHECK+" VARCHAR(255),"

                +PipeMaterial+" VARCHAR(255),"
                +PipeDia+" VARCHAR(255),"
                +location+" VARCHAR(255),"
                + WallThikness +" VARCHAR(255),"
                +holiday_detector+" VARCHAR(255),"
                +model+" VARCHAR(255),"
                +voltage+" VARCHAR(255),"
                +calibration+" VARCHAR(255),"
                +repair_damages+" VARCHAR(255),"

                +Weather+" VARCHAR(255),"
                +Dewatering+" VARCHAR(255),"
                +PaddingMaterial+" VARCHAR(255),"
                +Padding+" VARCHAR(255),"

                +REMARK+" VARCHAR(255),"
                +ImageData+" VARCHAR,"
                +PHOTO+" VARCHAR"
                +")";

        String qBackfilling="CREATE TABLE IF NOT EXISTS "+BACKFILLING_TABLE
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"
                +Weather+" VARCHAR(255),"
                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"

                +SECTION_LENGTH+" VARCHAR(255),"
                +PostPadding+" VARCHAR(255),"
                +Slopebreaker+" VARCHAR(255),"
                +antibuyo+" VARCHAR(255),"
                +cover+" VARCHAR(255),"
                +warning+" VARCHAR(255),"
                +center_level+" VARCHAR(255),"
                +Plasticgrating+" VARCHAR(255),"
                +Alignment_Sheet+" VARCHAR(255),"
                +Pipe_dia+" VARCHAR(255),"
                +Pipe_thick+" VARCHAR(255),"
                +JOINT_FROM+" VARCHAR(255),"
                +JOINT_TO+" VARCHAR(255),"
                +chfrom+" VARCHAR(255),"
                +chto+" VARCHAR(255),"
         //       +PADDING_CHECK+" VARCHAR(255),"
         //       +WARNING_MAT+" VARCHAR(255),"
                +Manpower+" VARCHAR(255),"
                +Machineries+" VARCHAR(255),"
                +TN_HolidayCheck+" VARCHAR(255),"
                +TN_SoilRplacment+" VARCHAR(255),"
                +TN_SaddleWeight+" VARCHAR(255),"
                +TN_RockBackfill+" VARCHAR(255),"

                +TN_CenterlineCheck+" VARCHAR(255),"
                +TN_coating+" VARCHAR(255),"
                +REMARK+" VARCHAR(255),"
                +ImageData+" VARCHAR,"
                +PHOTO+" VARCHAR"
                +")";
        String qDrying="CREATE TABLE IF NOT EXISTS "+DRYING_TABLE
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"

                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"

                +JOINT_FROM+" VARCHAR(255),"
                +JOINT_TO+" VARCHAR(255),"

                +START_DATE+" VARCHAR(255),"
                +END_DATE+" VARCHAR(255),"

                +REMARK+" VARCHAR(255),"
                +PHOTO+" VARCHAR"
                +")";

        String qRestoration="CREATE TABLE IF NOT EXISTS "+RESTORATION_TABLE
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"

                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"

                +CHAINAGE_FROM+" VARCHAR(255),"
                +CHAINAGE_TO+" VARCHAR(255),"

                +LAND_TYPE+" VARCHAR(255),"

                +SURPLUS_MATERIAL+" VARCHAR(255),"
                +TOP_SOIL+" VARCHAR(255),"
                +REINSTALLATION_BOUNDRY+" VARCHAR(255),"

                +REMARK+" VARCHAR(255),"
                +PHOTO+" VARCHAR"
                +")";


        String qRadioGraphy="CREATE TABLE IF NOT EXISTS "+RADIO_GRAPHY_TABLE
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"

                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +observation+" VARCHAR(255),"
                +observation2+" VARCHAR(255),"
                +observation3+" VARCHAR(255),"
                +observation4+" VARCHAR(255),"
                +JOINT_FROM+" VARCHAR(255),"
                +Weather+" VARCHAR(255),"
                +SEGMENT1+" VARCHAR(255),"
                +SEGMENT2+" VARCHAR(255),"
                +SEGMENT3+" VARCHAR(255),"
                +SEGMENT4+" VARCHAR(255),"

                +RESULT+" VARCHAR(255),"
                +RootRepair+" VARCHAR(255),"
                +RWelder+" VARCHAR(255),"
                +HotPass+" VARCHAR(255),"
                +HWelder+" VARCHAR(255),"
                +Filler+" VARCHAR(255),"
                +FWelder+" VARCHAR(255),"
                +Capping+" VARCHAR(255),"
                +CWelder+" VARCHAR(255),"

                +RootRepair2+" VARCHAR(255),"
                +RWelder2+" VARCHAR(255),"
                +HotPass2+" VARCHAR(255),"
                +HWelder2+" VARCHAR(255),"
                +Filler2+" VARCHAR(255),"
                +FWelder2+" VARCHAR(255),"
                +Capping2+" VARCHAR(255),"
                +CWelder2+" VARCHAR(255),"

                +RootRepair3+" VARCHAR(255),"
                +RWelder3+" VARCHAR(255),"
                +HotPass3+" VARCHAR(255),"
                +HWelder3+" VARCHAR(255),"
                +Filler3+" VARCHAR(255),"
                +FWelder3+" VARCHAR(255),"
                +Capping3+" VARCHAR(255),"
                +CWelder3+" VARCHAR(255),"

                +RootRepair4+" VARCHAR(255),"
                +RWelder4+" VARCHAR(255),"
                +HotPass4+" VARCHAR(255),"
                +HWelder4+" VARCHAR(255),"
                +Filler4+" VARCHAR(255),"
                +FWelder4+" VARCHAR(255),"
                +Capping4+" VARCHAR(255),"
                +CWelder4+" VARCHAR(255),"


                +penetrameter+" VARCHAR(255),"
                +film_type+" VARCHAR(255),"
                +sensitivity+" VARCHAR(255),"
                +gray_value+" VARCHAR(255),"
                +thickness+" VARCHAR(255),"
                +Alignment_Sheet+" VARCHAR(255),"
                +ChainageFrom+" VARCHAR(255),"
                +ChaninageTo+" VARCHAR(255),"
                +source+" VARCHAR(255),"


                +REMARK+" VARCHAR(255),"
                +ImageData+" VARCHAR,"
                +PHOTO+" VARCHAR"
                +")";


        String qUT="CREATE TABLE IF NOT EXISTS "+UT_TABLE
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"

                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +observation+" VARCHAR(255),"
                +observation2+" VARCHAR(255),"
                +observation3+" VARCHAR(255),"
                +observation4+" VARCHAR(255),"
                +JOINT_FROM+" VARCHAR(255),"
                +Weather+" VARCHAR(255),"
                +SEGMENT1+" VARCHAR(255),"
                +SEGMENT2+" VARCHAR(255),"
                +SEGMENT3+" VARCHAR(255),"
                +SEGMENT4+" VARCHAR(255),"

                +RESULT+" VARCHAR(255),"

                +REMARK+" VARCHAR(255),"
                +PHOTO+" VARCHAR"
                +")";
        String qJointCoating="CREATE TABLE IF NOT EXISTS "+JOINT_COATING_TABLE
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"

                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +Weather+" VARCHAR(255),"
                +pipe_dia+" VARCHAR(255),"
                +pipe_thickness+" VARCHAR(255),"
           //     +batch_no+" VARCHAR(255),"
                +roughness+" VARCHAR(255),"
                +dust_containment+" VARCHAR(255),"
                +primer+" VARCHAR(255),"
                +primer_b+" VARCHAR(255),"
                +onbody+" VARCHAR(255),"
                +onseam+" VARCHAR(255),"
                +relative_humidity+" VARCHAR(255),"
          //      +coating_type+" VARCHAR(255),"
          //      +sleeve_option+" VARCHAR(255),"
                +JOINT_FROM+" VARCHAR(255),"

                +VISUAL_CHECK+" VARCHAR(255),"
                +HOLIDAY_CHECK+" VARCHAR(255),"

                +BODY_TEMP+" VARCHAR(255),"
                +WELD_TEMP+" VARCHAR(255),"

                +Primer_A_Batch+" VARCHAR(255),"
                +Primer_B_Batch+" VARCHAR(255),"
                + Holiday_Tester+" VARCHAR(255),"
                + Type_Coating+" VARCHAR(255),"
                +Sleave+" VARCHAR(255),"
                +Callibration+" VARCHAR(255),"
                +BatchNo+" VARCHAR(255),"
                +TN_RH+" VARCHAR(255),"
                + TN_Elcometer+" VARCHAR(255),"
                + TN_PeelTest+" VARCHAR(255),"
               +Roughness+" VARCHAR(255),"
                +Dust+" VARCHAR(255),"
                +Profile+" VARCHAR(255),"
                +WFT+" VARCHAR(255),"


                +Material+" VARCHAR(255),"
                +ChainageFrom+" VARCHAR(255),"
                +ChainageTo+" VARCHAR(255),"
                +Thickness+" VARCHAR(255),"
                +Coating_Thick+" VARCHAR(255),"
                +pre_heating+" VARCHAR(255),"
                +Alignment_Sheet+" VARCHAR(255),"
                + TN_Manpower +" VARCHAR(255),"
                + TN_Machineries +" VARCHAR(255),"
                + body_3+" VARCHAR(255),"
                + body_6+" VARCHAR(255),"
                + body_9+" VARCHAR(255),"
                + body_12+" VARCHAR(255),"
                + body_avg+" VARCHAR(255),"
                + bodyu_3+" VARCHAR(255),"
                + bodyu_6+" VARCHAR(255),"
                + bodyu_9+" VARCHAR(255),"
                + bodyu_12+" VARCHAR(255),"
                + bodyu_avg+" VARCHAR(255),"
                + joint_3+" VARCHAR(255),"
                + joint_6+" VARCHAR(255),"
                + joint_9+" VARCHAR(255),"
                + joint_12+" VARCHAR(255),"
                + joint_avg+" VARCHAR(255),"
                +REMARK+" VARCHAR(255),"
                +PHOTO+" VARCHAR"
                +")";
        String qLevelling="CREATE TABLE IF NOT EXISTS "+Levelling_TABLE
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"

                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +Weather+" VARCHAR(255),"
                +NGL+" VARCHAR(255),"
                +JOINT_FROM+" VARCHAR(255),"
                +Alignment_Sheet+" VARCHAR(255),"
                +Cover+" VARCHAR(255),"
                +XCord+" VARCHAR(255),"
                +YCord+" VARCHAR(255),"
                +ZCord+" VARCHAR(255),"
                +ChFrom+" VARCHAR(255),"
                +ChTo+" VARCHAR(255),"
                +Elevation+" VARCHAR(255),"
                +JointFrom+" VARCHAR(255),"
                +JointTo+" VARCHAR(255),"
                +PipeThik+" VARCHAR(255),"
                +TpNo+" VARCHAR(255),"
                +SectionLength+" VARCHAR(255),"
                +PipeDia+" VARCHAR(255),"
                +REMARK+" VARCHAR(255),"
                +ImageData+" VARCHAR(255),"
                +PHOTO+" VARCHAR"
                +")";
        String qHydroTest="CREATE TABLE IF NOT EXISTS "+HYDRO_TESTING
                +"("
                +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +USER_ID+" VARCHAR(255),"
                +SECTION_ID+" VARCHAR(255),"
                +TYPE+" VARCHAR(255),"
                +DATE+" VARCHAR(255),"
                +REPORT_NO+" VARCHAR(255),"
                +Weather+" VARCHAR(255),"
                +JOINT_FROM+" VARCHAR(255),"
                +JOINT_TO+" VARCHAR(255),"
                +SECTION_LENGTH+" VARCHAR(255),"
                +HYDRO_PLAN_DATE+" VARCHAR(255),"
                +HYDRO_PLAN_REPORT_NO+" VARCHAR(255),"
                +REMARK+" VARCHAR(255),"
                +PHOTO+" VARCHAR"
                +")";


        String CREATE_DATABASE_WELDING_WPS = "create table tbl_welding_wps_num("
                + "_id integer  primary key autoincrement , "
                + "WpsID,"
                + "WPSName);";
        String CREATE_DATABASE_WELDING_WELDER = "create table tbl_welding_welder_num("
                + "_id integer  primary key autoincrement , "
                + "WelderID,"
                + "WelderName);";

        String CREATE_DATABASE_ALIGNMENT_SHEET = "create table tbl_alignment_sheet("
                + "_id integer  primary key autoincrement , "
                + "AlignmentID,"
                + "AlignmentName);";

        db.execSQL(q);
        db.execSQL(raw);
        db.execSQL(newRaw);
        db.execSQL(rou_query);
        db.execSQL(cNg);
        db.execSQL(qStringing);
        db.execSQL(qBending);
        db.execSQL(qHDPE);
        db.execSQL(CREATE_DATABASE_WELDING_WPS);
        db.execSQL(CREATE_DATABASE_WELDING_WELDER);
        db.execSQL(CREATE_DATABASE_ALIGNMENT_SHEET);
        db.execSQL(qWelding);
        db.execSQL(queryTrenching);
        db.execSQL(qLowering);
        db.execSQL(qBackfilling);
        db.execSQL(qOFCBlowing);
        db.execSQL(qDrying);
        db.execSQL(qRestoration);
        db.execSQL(qRadioGraphy);
        db.execSQL(qUT);
        db.execSQL(qJointCoating);
        db.execSQL(qHydroTest);
        db.execSQL(qLevelling);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +ROW_Table);
        db.execSQL("DROP TABLE IF EXISTS " +ROU_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +NEW_ROW_Table);
        db.execSQL("DROP TABLE IF EXISTS " +CNG_Table);
        db.execSQL("DROP TABLE IF EXISTS " +STRINGING_Table);
        db.execSQL("DROP TABLE IF EXISTS " +BENDING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +HDPE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +WELDING_Table);
        db.execSQL("DROP TABLE IF EXISTS " +TRENCHING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +LOWERING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +BACKFILLING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +BENDING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +OFCBLOWING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +DRYING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +RESTORATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +RADIO_GRAPHY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +UT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +JOINT_COATING_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +HYDRO_TESTING);
        db.execSQL("DROP TABLE IF EXISTS " +Levelling_TABLE);
        db.execSQL("DROP TABLE IF EXISTS tbl_welding_wps_num");
        db.execSQL("DROP TABLE IF EXISTS tbl_welding_welder_num");
        db.execSQL("DROP TABLE IF EXISTS tbl_alignment_sheet");
        onCreate(db);
    }

    public boolean insert(String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Loop_Runing_Status, status);
            db.insert(TABLE_NAME, null, contentValues);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    // ---insert a title into the database---
    public void insert(String DATABASE_TABLE, ContentValues initialValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean RouInsert(HashMap<String,String> hashMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));
        contentValues.put(DATE, hashMap.get("TR_Rouhandover_Date"));
        contentValues.put(REPORT_NO, hashMap.get("TR_Report_Number"));
        contentValues.put(CHAINAGE_FROM, hashMap.get("MR_Chainage_From"));
        contentValues.put(CHAINAGE_TO, hashMap.get("MR_Chainage_To"));
        contentValues.put(terrian, hashMap.get("TypeTerrain"));
        contentValues.put(VILLAGE, hashMap.get("Village"));
        contentValues.put(TEHSIL, hashMap.get("Tehsil"));
        contentValues.put(DISTRICT, hashMap.get("District"));
        contentValues.put(DetailsStructure, hashMap.get("DetailsStructure"));
        contentValues.put(across_row, hashMap.get("across_row"));
        contentValues.put(Details, hashMap.get("Details"));
        contentValues.put(CHAINAGE, hashMap.get("chainage"));
        contentValues.put(namestructre, hashMap.get("namestructre"));
        contentValues.put(Ipno, hashMap.get("Ipno"));
        contentValues.put(Alignment_Sheet, hashMap.get("Alignment_Sheet"));
        contentValues.put(Skipping_detail, hashMap.get("Skipping_detail"));
        contentValues.put(Obstacle, hashMap.get("Obstacle"));
        contentValues.put(RPanchnama, hashMap.get("RPanchnama"));
        contentValues.put(PHOTO, hashMap.get("Photo"));
        contentValues.put(ImageData, hashMap.get("ImageData"));
        contentValues.put(REMARK, hashMap.get("TN_Remarks"));
        contentValues.put(Weather, hashMap.get("Weather"));


        long i = db.insert(ROU_TABLE, null, contentValues);

        Log.e("Database",String.valueOf(i));
        if (i==-1){
            return false;
        }else
            return true;
    }
    public void update (String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery( "select * from " +TABLE_NAME, null );
        res.moveToFirst();

        String Id=res.getString(0);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Loop_Runing_Status, status);

        db.update(TABLE_NAME, contentValues, COLUMN_ID+" = ? ", new String[] { Id } );
        res.close();
    }
    public boolean getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " +TABLE_NAME, null );
        res.moveToFirst();
        String s=res.getString(1);
        res.close();
        return Boolean.parseBoolean(s);
    }

    public ArrayList<HashMap<String, String>> getRouData() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();
        Cursor cursor =  db.rawQuery( "select * from " +ROU_TABLE, null );
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();
                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("TR_Rouhandover_Date",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("TR_Report_Number",cursor.getString(cursor.getColumnIndex(REPORT_NO)));
                h.put("MR_Chainage_From",cursor.getString(cursor.getColumnIndex(CHAINAGE_FROM)));
                h.put("MR_Chainage_To",cursor.getString(cursor.getColumnIndex(CHAINAGE_TO)));
                h.put("Village",cursor.getString(cursor.getColumnIndex(VILLAGE)));
                h.put("Tehsil",cursor.getString(cursor.getColumnIndex(TEHSIL)));
                h.put("District",cursor.getString(cursor.getColumnIndex(DISTRICT)));
                h.put("TN_Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));
                h.put("TypeTerrain",cursor.getString(cursor.getColumnIndex(terrian)));

                h.put("DetailsStructure",cursor.getString(cursor.getColumnIndex(DetailsStructure)));
                h.put("across_row",cursor.getString(cursor.getColumnIndex(across_row)));
                h.put("Details",cursor.getString(cursor.getColumnIndex(Details)));
                h.put("chainage",cursor.getString(cursor.getColumnIndex(CHAINAGE)));
                h.put("namestructre",cursor.getString(cursor.getColumnIndex(namestructre)));

                h.put("Ipno", cursor.getString(cursor.getColumnIndex(Ipno)));

                h.put("Alignment_Sheet", cursor.getString(cursor.getColumnIndex(Alignment_Sheet)));



                h.put("Skipping_detail",cursor.getString(cursor.getColumnIndex(Skipping_detail)));
                h.put("Obstacle",cursor.getString(cursor.getColumnIndex(Obstacle)));
                h.put("RPanchnama",cursor.getString(cursor.getColumnIndex(RPanchnama)));
                h.put("Photo",cursor.getString(cursor.getColumnIndex(PHOTO)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(ImageData)));
                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();


        return arrayList;
    }
    public Cursor getAll(String select) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(select, null);
    }

    public boolean DeleteRow(String Id,String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery( "select * from " +tableName, null );
        res.moveToFirst();
        int i=db.delete(tableName,  COLUMN_ID+" = ? ", new String[] { Id } );
        res.close();
        if (i>0){
            return true;
        }else
            return false;

    }
    public boolean deleteAll(String DATABASE_TABLE) {
        SQLiteDatabase db =this.getWritableDatabase();
        return db.delete(DATABASE_TABLE, null, null) > 0;
//        db.execSQL("delete from "+ DATABASE_TABLE);
    }
    public boolean RowInsert(HashMap<String,String> hashMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(TYPE, hashMap.get("type"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(DATE, hashMap.get("TR_Rou_Date"));
        contentValues.put(REPORT_NO, hashMap.get("TR_Report_Number"));
        contentValues.put(CHAINAGE_FROM, hashMap.get("MR_Chainage_From"));
        contentValues.put(CHAINAGE_TO, hashMap.get("MR_Chainage_To"));
        contentValues.put(TYPE_ANODE, hashMap.get("Type_Anode"));
        contentValues.put(GROUND_TYPE, hashMap.get("spinGround"));
        contentValues.put(STRUCTURE_DETAILS, hashMap.get("Others"));
        contentValues.put(REMARK, hashMap.get("TN_Remarks"));
        contentValues.put(ImageData, hashMap.get("ImageData"));
        contentValues.put(PHOTO, hashMap.get("Photo"));
        int i = (int) db.insert(ROW_Table, null, contentValues);
        if (i==-1){
            return false;
        }else
            return true;
    }

    public ArrayList<HashMap<String, String>> getRowData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +ROW_Table, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();
                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("TR_Rou_Date",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("TR_Report_Number",cursor.getString(cursor.getColumnIndex(REPORT_NO)));

                h.put("MR_Chainage_From",cursor.getString(cursor.getColumnIndex(CHAINAGE_FROM)));
                h.put("MR_Chainage_To",cursor.getString(cursor.getColumnIndex(CHAINAGE_TO)));
                h.put("Type_Anode",cursor.getString(cursor.getColumnIndex(TYPE_ANODE)));
                h.put("spinGround",cursor.getString(cursor.getColumnIndex(GROUND_TYPE)));
                h.put("Others",cursor.getString(cursor.getColumnIndex(STRUCTURE_DETAILS)));

                h.put("TN_Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("Photo",cursor.getString(cursor.getColumnIndex(PHOTO)));


                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }
    public boolean newRowInsert(HashMap<String,String> hashMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(TYPE, hashMap.get("type"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(DATE, hashMap.get("TR_Rou_Date"));
        contentValues.put(REPORT_NO, hashMap.get("TR_Report_Number"));
        contentValues.put(CHAINAGE_FROM, hashMap.get("MR_Chainage_From"));
        contentValues.put(CHAINAGE_TO, hashMap.get("MR_Chainage_To"));
        contentValues.put(Alignment_Sheet, hashMap.get("Alignment_Sheet"));

        contentValues.put(Weather, hashMap.get("Weather"));

        contentValues.put(tpremark, hashMap.get("tpremark"));
        contentValues.put(bearingangle, hashMap.get("bearingangle"));
        contentValues.put(Ipno, hashMap.get("Ipno"));
        contentValues.put(IpChainage, hashMap.get("IpChainage"));
        contentValues.put(terrian, hashMap.get("terrian"));
        contentValues.put(namestructre, hashMap.get("namestructre"));
       contentValues.put(CHAINAGE, hashMap.get("chainage"));
        contentValues.put(STRUCTURE_DETAILS, hashMap.get("Others"));
        contentValues.put(REMARK, hashMap.get("TN_Remarks"));
        contentValues.put(DetailsStructure, hashMap.get("DetailsStructure"));
        contentValues.put(Details, hashMap.get("Details"));
        contentValues.put(across_row, hashMap.get("across_row"));
        contentValues.put(PHOTO, hashMap.get("Photo"));
        contentValues.put(ImageData, hashMap.get("ImageData"));
        int i = (int) db.insert(NEW_ROW_Table, null, contentValues);
        if (i==-1){
            return false;
        }else
            return true;
    }

    public ArrayList<HashMap<String, String>> getnewRowData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +NEW_ROW_Table, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();
                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("TR_Rou_Date",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("TR_Report_Number",cursor.getString(cursor.getColumnIndex(REPORT_NO)));

                h.put("MR_Chainage_From",cursor.getString(cursor.getColumnIndex(CHAINAGE_FROM)));
                h.put("MR_Chainage_To",cursor.getString(cursor.getColumnIndex(CHAINAGE_TO)));
                h.put("Alignment_Sheet",cursor.getString(cursor.getColumnIndex(Alignment_Sheet)));

                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));

                h.put("tpremark",cursor.getString(cursor.getColumnIndex(tpremark)));
                h.put("bearingangle",cursor.getString(cursor.getColumnIndex(bearingangle)));
                h.put("Ipno",cursor.getString(cursor.getColumnIndex(Ipno)));
                h.put("IpChainage",cursor.getString(cursor.getColumnIndex(IpChainage)));
                h.put("terrian",cursor.getString(cursor.getColumnIndex(terrian)));
                h.put("namestructre",cursor.getString(cursor.getColumnIndex(namestructre)));
                h.put("chainage",cursor.getString(cursor.getColumnIndex(CHAINAGE)));
                h.put("Others",cursor.getString(cursor.getColumnIndex(STRUCTURE_DETAILS)));

                h.put("TN_Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("DetailsStructure",cursor.getString(cursor.getColumnIndex(DetailsStructure)));
                h.put("Details",cursor.getString(cursor.getColumnIndex(Details)));
                h.put("across_row",cursor.getString(cursor.getColumnIndex(across_row)));
                h.put("Photo",cursor.getString(cursor.getColumnIndex(PHOTO)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(ImageData)));


                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }
    public boolean ClearingGradingInsert(Map<String, String> hashMap) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(TYPE, hashMap.get("type"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(DATE, hashMap.get("TR_Clearing_Date"));
        contentValues.put(REPORT_NO, hashMap.get("TR_Report_Number"));
        contentValues.put(Alignment_Sheet, hashMap.get("Alignment_Sheet"));
        contentValues.put(IP_FROM, hashMap.get("IPFrom"));
        contentValues.put(IP_TO, hashMap.get("IPTo"));
        contentValues.put(TP_FROM, hashMap.get("TPFrom"));
        contentValues.put(TP_TO, hashMap.get("TPTo"));
        contentValues.put(Weather, hashMap.get("Weather"));
        contentValues.put(namestructre, hashMap.get("namestructure"));
        contentValues.put(CHAINAGE, hashMap.get("chainage"));
        contentValues.put(CHAINAGE_FROM, hashMap.get("MR_Chainage_From"));
        contentValues.put(CHAINAGE_TO, hashMap.get("MR_Chainage_To"));
        contentValues.put(GROUND_TYPE, hashMap.get("spinGround"));
        contentValues.put(DetailsStructure, hashMap.get("DetailsStructure"));
        contentValues.put(REMARK, hashMap.get("TN_Remarks"));
        contentValues.put(work_interrupt, hashMap.get("work_interruption"));
        contentValues.put(Length, hashMap.get("Length"));
        contentValues.put(Description, hashMap.get("Description"));
        contentValues.put(IP_No, hashMap.get("work_interruption"));
        contentValues.put(Rows, hashMap.get("Row"));
        contentValues.put(TN_RowStatus, hashMap.get("TN_RowStatus"));
        contentValues.put(TN_Manpower, hashMap.get("TN_Manpower"));
        contentValues.put(TN_Machineries, hashMap.get("TN_Machineries"));
        contentValues.put(ImageData, hashMap.get("ImageData"));
        contentValues.put(PHOTO, hashMap.get("Photo"));

        int i = (int) db.insert(CNG_Table, null, contentValues);
        if (i==-1){
            return false;
        }else
            return true;

    }
    public ArrayList<HashMap<String, String>> getCNGData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +CNG_Table, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();
                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("TR_Clearing_Date",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("TR_Report_Number",cursor.getString(cursor.getColumnIndex(REPORT_NO)));
                h.put("Alignment_Sheet",cursor.getString(cursor.getColumnIndex(Alignment_Sheet)));

                h.put("MR_Chainage_From",cursor.getString(cursor.getColumnIndex(CHAINAGE_FROM)));
                h.put("MR_Chainage_To",cursor.getString(cursor.getColumnIndex(CHAINAGE_TO)));

                h.put("IPFrom",cursor.getString(cursor.getColumnIndex(IP_FROM)));
                h.put("IPTo",cursor.getString(cursor.getColumnIndex(IP_TO)));

                h.put("TPFrom",cursor.getString(cursor.getColumnIndex(TP_FROM)));
                h.put("TPTo",cursor.getString(cursor.getColumnIndex(TP_TO)));
                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));
                h.put("namestructure",cursor.getString(cursor.getColumnIndex(namestructre)));
                h.put("chainage",cursor.getString(cursor.getColumnIndex(CHAINAGE)));

                h.put("spinGround",cursor.getString(cursor.getColumnIndex(GROUND_TYPE)));
                h.put("DetailsStructure",cursor.getString(cursor.getColumnIndex(DetailsStructure)));

                h.put("TN_Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("work_interruption",cursor.getString(cursor.getColumnIndex(work_interrupt)));
                h.put("Length", cursor.getString(cursor.getColumnIndex(Length)));
                h.put("Description", cursor.getString(cursor.getColumnIndex(Description)));
                h.put("IP_No", cursor.getString(cursor.getColumnIndex(IP_No)));
                h.put("Row", cursor.getString(cursor.getColumnIndex(Rows)));

                h.put("TN_RowStatus", cursor.getString(cursor.getColumnIndex(TN_RowStatus)));
                h.put("TN_Manpower", cursor.getString(cursor.getColumnIndex(TN_Manpower)));
                h.put("TN_Machineries", cursor.getString(cursor.getColumnIndex(TN_Machineries)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(ImageData)));
                h.put("Photo",cursor.getString(cursor.getColumnIndex(PHOTO)));


                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }


    public boolean StringingInsert(HashMap<String, String> hashMap) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(TYPE, hashMap.get("type"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(DATE, hashMap.get("TR_Stringing_Date"));
        contentValues.put(REPORT_NO, hashMap.get("TR_Report_Number"));
        contentValues.put(Alignment_Sheet, hashMap.get("Alignment_Sheet"));
        contentValues.put(CHAINAGE_FROM, hashMap.get("MR_Chainage_From"));
        contentValues.put(Weather, hashMap.get("Weather"));
        contentValues.put(PipeDia, hashMap.get("PipeDia"));
        contentValues.put(PipeMaterial, hashMap.get("PipeMaterial"));
        contentValues.put(PIPE_ID, hashMap.get("PipeID"));
        contentValues.put(LATITUDE, hashMap.get("latitude"));
        contentValues.put(LONGITUDE, hashMap.get("longitude"));
        contentValues.put(CONCRETE_COTING, hashMap.get("ConcreteCoating"));
        contentValues.put(REMARK, hashMap.get("TN_Remarks"));
        contentValues.put(TN_Damage, hashMap.get("TN_Damage"));
        contentValues.put(CorrosionType, hashMap.get("CorrosionType"));
        contentValues.put(PHOTO, hashMap.get("Photo"));
        contentValues.put(ImageData, hashMap.get("ImageData"));

        int i = (int) db.insert(STRINGING_Table, null, contentValues);
        if (i==-1){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getStringingData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +STRINGING_Table, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();

                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("TR_Stringing_Date",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("TR_Report_Number",cursor.getString(cursor.getColumnIndex(REPORT_NO)));
                h.put("Alignment_Sheet",cursor.getString(cursor.getColumnIndex(Alignment_Sheet)));
                h.put("PipeDia",cursor.getString(cursor.getColumnIndex(PipeDia)));
                h.put("PipeMaterial",cursor.getString(cursor.getColumnIndex(PipeMaterial)));
                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));
                h.put("MR_Chainage_From",cursor.getString(cursor.getColumnIndex(CHAINAGE_FROM)));
                h.put("PipeID",cursor.getString(cursor.getColumnIndex(PIPE_ID)));
                h.put("latitude",cursor.getString(cursor.getColumnIndex(LATITUDE)));
                h.put("longitude",cursor.getString(cursor.getColumnIndex(LONGITUDE)));
                h.put("ConcreteCoating",cursor.getString(cursor.getColumnIndex(CONCRETE_COTING)));
                h.put("TN_Damage",cursor.getString(cursor.getColumnIndex(TN_Damage)));
                h.put("CorrosionType",cursor.getString(cursor.getColumnIndex(CorrosionType)));
                h.put("TN_Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("Photo",cursor.getString(cursor.getColumnIndex(PHOTO)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(ImageData)));

                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }


    public boolean BendingInsert(HashMap<String,String> hashMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));
        contentValues.put(DATE, hashMap.get("TR_Date"));
        contentValues.put(REPORT_NO, hashMap.get("TR_Report_Number"));
        contentValues.put(Alignment_Sheet, hashMap.get("Alignment_Sheet"));
        contentValues.put(CHAINAGE, hashMap.get("TN_Chainage"));
        contentValues.put(Bend_Number, hashMap.get("Bend_Number"));
    contentValues.put(PIPE_NO, hashMap.get("Pipe_Number"));
        contentValues.put(TYPE_OF_BEND, hashMap.get("TR_BEND"));
        contentValues.put(BEND_DEGREE, hashMap.get("TN_Degree"));
        contentValues.put(BEND_MIN, hashMap.get("TN_Min"));
        contentValues.put(BEND_SEC, hashMap.get("TN_Sec"));
        contentValues.put(TP_NO, hashMap.get("TN_TP"));
        contentValues.put(VISUAL_CHECK, hashMap.get("TN_Visual"));
        contentValues.put(DISBONDING_CHECK, hashMap.get("TN_Disbonding"));
        contentValues.put(GUAGING_CHECK, hashMap.get("TN_Gauging"));
        contentValues.put(HOLIDAY_CHECK, hashMap.get("TN_Holiday"));
        contentValues.put(OVALITY_CHECK, hashMap.get("TN_Ovality"));
        contentValues.put(Weather, hashMap.get("Weather"));

        contentValues.put(TN_Manpower, hashMap.get("TN_Manpower"));
        contentValues.put(TN_Machineries, hashMap.get("TN_Machineries"));
        contentValues.put(latitude, hashMap.get("latitude"));
        contentValues.put(longitude, hashMap.get("longitude"));

        contentValues.put(REMARK, hashMap.get("TN_Remarks"));
        contentValues.put(PHOTO, hashMap.get("Photo"));
        contentValues.put(ImageData, hashMap.get("ImageData"));
        int i = (int) db.insert(BENDING_TABLE, null, contentValues);
        if (i==0){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getBendingData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +BENDING_TABLE, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();

                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("TR_Date",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("TR_Report_Number",cursor.getString(cursor.getColumnIndex(REPORT_NO)));
                h.put("Alignment_Sheet",cursor.getString(cursor.getColumnIndex(Alignment_Sheet)));
                h.put("TN_Chainage",cursor.getString(cursor.getColumnIndex(CHAINAGE)));
                h.put("Bend_Number",cursor.getString(cursor.getColumnIndex(Bend_Number)));
                h.put("Pipe_Number",cursor.getString(cursor.getColumnIndex(PIPE_NO)));
                h.put("TN_Degree",cursor.getString(cursor.getColumnIndex(BEND_DEGREE)));
                h.put("TR_BEND",cursor.getString(cursor.getColumnIndex(TYPE_OF_BEND)));
                h.put("TN_Min",cursor.getString(cursor.getColumnIndex(BEND_MIN)));
                h.put("TN_Sec",cursor.getString(cursor.getColumnIndex(BEND_SEC)));
                h.put("TN_TP",cursor.getString(cursor.getColumnIndex(TP_NO)));
                h.put("TN_Visual",cursor.getString(cursor.getColumnIndex(VISUAL_CHECK)));
                h.put("TN_Disbonding",cursor.getString(cursor.getColumnIndex(DISBONDING_CHECK)));
                h.put("TN_Gauging",cursor.getString(cursor.getColumnIndex(GUAGING_CHECK)));
                h.put("TN_Holiday",cursor.getString(cursor.getColumnIndex(HOLIDAY_CHECK)));
                h.put("TN_Ovality",cursor.getString(cursor.getColumnIndex(OVALITY_CHECK)));
                h.put("TN_Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(ImageData)));
                h.put("Photo",cursor.getString(cursor.getColumnIndex(PHOTO)));
                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));
                h.put("TN_Manpower",cursor.getString(cursor.getColumnIndex(TN_Manpower)));
                h.put("TN_Machineries",cursor.getString(cursor.getColumnIndex(TN_Machineries)));
                h.put("latitude",cursor.getString(cursor.getColumnIndex(latitude)));
                h.put("longitude",cursor.getString(cursor.getColumnIndex(longitude)));
                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }



    public boolean HDPEInsert(HashMap<String,String> hashMap) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));
        contentValues.put(DATE, hashMap.get("HDEDDate"));
        contentValues.put(REPORT_NO, hashMap.get("HDEDReptNo"));
        contentValues.put(SECTION_LENGTH, hashMap.get("SectionLength"));
        contentValues.put(CoupleNo, hashMap.get("CoupleNo"));
        contentValues.put(JOINT_FROM, hashMap.get("JointFrom"));
        contentValues.put(JOINT_TO, hashMap.get("JointTo"));
        contentValues.put(Weather, hashMap.get("Weather"));
        contentValues.put(CHAINAGE_FROM, hashMap.get("chainage_from"));
        contentValues.put(CHAINAGE_TO, hashMap.get("chainage_to"));
        contentValues.put(duct_length, hashMap.get("duct_length"));
        contentValues.put(duct_length_to, hashMap.get("duct_length_to"));
        contentValues.put(location, hashMap.get("location"));
        contentValues.put(PADDING_CHECK, hashMap.get("Padding"));
        contentValues.put(WARNING_MAT, hashMap.get("Warnning_mat"));

        contentValues.put(REMARK, hashMap.get("Remarks"));
        contentValues.put(PHOTO, hashMap.get("ImageData"));

        int i = (int) db.insert(HDPE_TABLE, null, contentValues);
        if (i==0){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getHDPEData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +HDPE_TABLE, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();
                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("HDEDDate",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("HDEDReptNo",cursor.getString(cursor.getColumnIndex(REPORT_NO)));
                h.put("SectionLength",cursor.getString(cursor.getColumnIndex(SECTION_LENGTH)));
                h.put("CoupleNo",cursor.getString(cursor.getColumnIndex(CoupleNo)));
                h.put("JointFrom",cursor.getString(cursor.getColumnIndex(JOINT_FROM)));
                h.put("JointTo",cursor.getString(cursor.getColumnIndex(JOINT_TO)));
                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));
                h.put("chainage_from",cursor.getString(cursor.getColumnIndex(CHAINAGE_FROM)));
                h.put("chainage_to",cursor.getString(cursor.getColumnIndex(CHAINAGE_TO)));
                h.put("duct_length",cursor.getString(cursor.getColumnIndex(duct_length)));
                h.put("duct_length_to",cursor.getString(cursor.getColumnIndex(duct_length_to)));
                h.put("location",cursor.getString(cursor.getColumnIndex(location)));
                h.put("Padding",cursor.getString(cursor.getColumnIndex(PADDING_CHECK)));
                h.put("Warnning_mat",cursor.getString(cursor.getColumnIndex(WARNING_MAT)));
                h.put("Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(PHOTO)));

                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public boolean WeldingInsert(Map<String, String> hashMap) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(TYPE, hashMap.get("type"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(DATE, hashMap.get("TR_Welding_Date"));
        contentValues.put(REPORT_NO, hashMap.get("TR_Report_Number"));
        contentValues.put(LEFT_PIPE_ID, hashMap.get("LeftPipeNumber"));
        contentValues.put(RIGHT_PIPE_ID, hashMap.get("RightPipeNumber"));
        contentValues.put(WPS_NO, hashMap.get("WPSNo"));
        contentValues.put(JOINT_ID, hashMap.get("JointID"));

        contentValues.put(RWELDER1, hashMap.get("RWelderNumber1"));
        contentValues.put(RWELDER2, hashMap.get("RWelderNumber2"));
        contentValues.put(HWELDER1, hashMap.get("HWelderNumber1"));
        contentValues.put(HWELDER2, hashMap.get("HWelderNumber2"));
        contentValues.put(F1WELDER1, hashMap.get("FWelderNumber1"));
        contentValues.put(F1WELDER2, hashMap.get("FWelderNumber2"));
        contentValues.put(F2WELDER1, hashMap.get("F2WelderNumber1"));
        contentValues.put(F2WELDER2, hashMap.get("F2WelderNumber2"));
        contentValues.put(CWELDER1, hashMap.get("CWelderNumber1"));
        contentValues.put(CWELDER2, hashMap.get("CWelderNumber2"));
        contentValues.put(CWELDER3, hashMap.get("CWelderNumber3"));
        contentValues.put(CWELDER4, hashMap.get("CWelderNumber4"));
        contentValues.put(F3WelderNumber1, hashMap.get("F3WelderNumber1"));
        contentValues.put(F3WelderNumber2, hashMap.get("F3WelderNumber2"));
        contentValues.put(electrode_e6010_dia, hashMap.get("electrode_e6010_dia"));
        contentValues.put(electrode_e6010_batch, hashMap.get("electrode_e6010_batch"));
        contentValues.put(electrode_e8010_dia, hashMap.get("electrode_e8010_dia"));
        contentValues.put(electrode_e8010_batch, hashMap.get("electrode_e8010_batch"));
        contentValues.put(electrode_e9045_dia, hashMap.get("electrode_e9045_dia"));
        contentValues.put(electrode_e9045_batch, hashMap.get("electrode_e9045_batch"));
        contentValues.put(electrode_806012_dia, hashMap.get("electrode_806012_dia"));
        contentValues.put(electrode_806012_batch, hashMap.get("electrode_806012_batch"));
        contentValues.put(electrode_B221868_dia, hashMap.get("electrode_B221868_dia"));
        contentValues.put(electrode_B221868_batch, hashMap.get("electrode_B221868_batch"));
        contentValues.put(Pipe_dia, hashMap.get("Pipe_dia"));
        contentValues.put(Pipe_thick, hashMap.get("Pipe_thick"));
        contentValues.put(Weather, hashMap.get("Weather"));
        contentValues.put(ChainageFrom, hashMap.get("ChainageFrom"));
        contentValues.put(ChainageTo, hashMap.get("ChainageTo"));
        contentValues.put(Process, hashMap.get("Process"));
        contentValues.put(Material, hashMap.get("Material"));
        contentValues.put(TR_alignment, hashMap.get("TR_alignment"));
        contentValues.put(Fitup, hashMap.get("Fitup"));
        contentValues.put(Weld_Visual, hashMap.get("Weld_Visual"));




        contentValues.put(REMARK, hashMap.get("TN_Remarks"));
        contentValues.put(PHOTO, hashMap.get("Photo"));
        contentValues.put(ImageData, hashMap.get("ImageData"));

        int i = (int) db.insert(WELDING_Table, null, contentValues);
        if (i==-1){
            return false;
        }else
            return true;

    }
    public ArrayList<HashMap<String, String>> getWeldingData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +WELDING_Table, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();
                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("TR_Welding_Date",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("TR_Report_Number",cursor.getString(cursor.getColumnIndex(REPORT_NO)));


                h.put("LeftPipeNumber",cursor.getString(cursor.getColumnIndex(LEFT_PIPE_ID)));
                h.put("RightPipeNumber",cursor.getString(cursor.getColumnIndex(RIGHT_PIPE_ID)));
                h.put("JointID",cursor.getString(cursor.getColumnIndex(JOINT_ID)));
                h.put("WPSNo",cursor.getString(cursor.getColumnIndex(WPS_NO)));

                h.put("TN_Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("Photo",cursor.getString(cursor.getColumnIndex(PHOTO)));
                h.put("RWelderNumber1",cursor.getString(cursor.getColumnIndex(RWELDER1)));
                h.put("RWelderNumber2",cursor.getString(cursor.getColumnIndex(RWELDER2)));
                h.put("HWelderNumber1",cursor.getString(cursor.getColumnIndex(HWELDER1)));
                h.put("HWelderNumber2",cursor.getString(cursor.getColumnIndex(HWELDER2)));
                h.put("FWelderNumber1",cursor.getString(cursor.getColumnIndex(F1WELDER1)));
                h.put("FWelderNumber2",cursor.getString(cursor.getColumnIndex(F1WELDER2)));
                h.put("F2WelderNumber1",cursor.getString(cursor.getColumnIndex(F2WELDER1)));
                h.put("F2WelderNumber2",cursor.getString(cursor.getColumnIndex(F2WELDER2)));
                h.put("CWelderNumber1",cursor.getString(cursor.getColumnIndex(CWELDER1)));
                h.put("CWelderNumber2",cursor.getString(cursor.getColumnIndex(CWELDER2)));
                h.put("CWelderNumber3",cursor.getString(cursor.getColumnIndex(CWELDER3)));
                h.put("CWelderNumber4",cursor.getString(cursor.getColumnIndex(CWELDER4)));

                h.put("F3WelderNumber1",cursor.getString(cursor.getColumnIndex(F3WelderNumber1)));
                h.put("F3WelderNumber2",cursor.getString(cursor.getColumnIndex(F3WelderNumber2)));
                h.put("electrode_e6010_dia",cursor.getString(cursor.getColumnIndex(electrode_e6010_dia)));
                h.put("electrode_e6010_batch",cursor.getString(cursor.getColumnIndex(electrode_e6010_batch)));
                h.put("electrode_e8010_dia",cursor.getString(cursor.getColumnIndex(electrode_e8010_dia)));
                h.put("electrode_e8010_batch",cursor.getString(cursor.getColumnIndex(electrode_e8010_batch)));
                h.put("electrode_e9045_dia",cursor.getString(cursor.getColumnIndex(electrode_e9045_dia)));
                h.put("electrode_e9045_batch",cursor.getString(cursor.getColumnIndex(electrode_e9045_batch)));
                h.put("electrode_806012_dia",cursor.getString(cursor.getColumnIndex(electrode_806012_dia)));
                h.put("electrode_806012_batch",cursor.getString(cursor.getColumnIndex(electrode_806012_batch)));
                h.put("electrode_B221868_dia",cursor.getString(cursor.getColumnIndex(electrode_B221868_dia)));
                h.put("electrode_B221868_batch",cursor.getString(cursor.getColumnIndex(electrode_B221868_batch)));
                h.put("Pipe_dia",cursor.getString(cursor.getColumnIndex(Pipe_dia)));
                h.put("Pipe_thick",cursor.getString(cursor.getColumnIndex(Pipe_thick)));
                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));
                h.put("ChainageFrom",cursor.getString(cursor.getColumnIndex(ChainageFrom)));
                h.put("ChainageTo",cursor.getString(cursor.getColumnIndex(ChainageTo)));
                h.put("Process",cursor.getString(cursor.getColumnIndex(Process)));
                h.put("Material",cursor.getString(cursor.getColumnIndex(Material)));
                h.put("TR_alignment",cursor.getString(cursor.getColumnIndex(TR_alignment)));

                h.put("Fitup",cursor.getString(cursor.getColumnIndex(Fitup)));
                h.put("Weld_Visual",cursor.getString(cursor.getColumnIndex(Weld_Visual)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(ImageData)));





                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }
    public boolean TrenchinInsert(HashMap<String,String> hashMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));
        contentValues.put(DATE, hashMap.get("TR_Trenching_Date"));
        contentValues.put(REPORT_NO, hashMap.get("TR_Report_Number"));
        contentValues.put(Alignment_Sheet, hashMap.get("Alignment_Sheet"));

        contentValues.put(CHAINAGE_FROM, hashMap.get("ChainageFrom"));
        contentValues.put(CHAINAGE_TO, hashMap.get("ChainageTo"));
        contentValues.put(WIDTH_AT_BOTTOM, hashMap.get("TN_Trenching_Lower_Width"));

        contentValues.put(JOINT_FROM, hashMap.get("TN_JointNumber_From"));
        contentValues.put(JOINT_TO, hashMap.get("TN_JointNumber_To"));

        contentValues.put(SECTION_LENGTH, hashMap.get("MR_Distance_Cleared"));

        contentValues.put(seperation_soil, hashMap.get("seperation_soil"));
        contentValues.put(provision_sign, hashMap.get("provision_sign"));
        contentValues.put(verification_area, hashMap.get("verification_area"));
        contentValues.put(avability_cover, hashMap.get("avability_cover"));
        contentValues.put(trench_profile, hashMap.get("trench_profile"));




        contentValues.put(DEPTH, hashMap.get("MN_Trenching_Depth"));
        contentValues.put(WIDTH, hashMap.get("MN_Trenching_UpperWidth"));
        contentValues.put(TypeofTerrain, hashMap.get("TypeofTerrain"));
        contentValues.put(Weather, hashMap.get("Weather"));
        contentValues.put(REMARK, hashMap.get("TN_Remarks"));
        contentValues.put(PHOTO, hashMap.get("Photo"));
        contentValues.put(ImageData, hashMap.get("ImageData"));
        int i = (int) db.insert(TRENCHING_TABLE, null, contentValues);
        if (i==0){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getTrenchingData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +TRENCHING_TABLE, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();
        /*cursor.moveToFirst();
        String s=cursor.getString(1);*/

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();
                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("TR_Trenching_Date",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("TR_Report_Number",cursor.getString(cursor.getColumnIndex(REPORT_NO)));
                h.put("Alignment_Sheet",cursor.getString(cursor.getColumnIndex(Alignment_Sheet)));

                h.put("ChainageFrom",cursor.getString(cursor.getColumnIndex(CHAINAGE_FROM)));
                h.put("ChainageTo",cursor.getString(cursor.getColumnIndex(CHAINAGE_TO)));
                h.put("TN_Trenching_Lower_Width",cursor.getString(cursor.getColumnIndex(WIDTH_AT_BOTTOM)));


                h.put("TN_JointNumber_From",cursor.getString(cursor.getColumnIndex(JOINT_FROM)));
                h.put("TN_JointNumber_To",cursor.getString(cursor.getColumnIndex(JOINT_TO)));


                h.put("MR_Distance_Cleared",cursor.getString(cursor.getColumnIndex(SECTION_LENGTH)));
                h.put("seperation_soil",cursor.getString(cursor.getColumnIndex(seperation_soil)));
                h.put("provision_sign",cursor.getString(cursor.getColumnIndex(provision_sign)));
                h.put("verification_area",cursor.getString(cursor.getColumnIndex(verification_area)));
                h.put("avability_cover",cursor.getString(cursor.getColumnIndex(avability_cover)));
                h.put("trench_profile",cursor.getString(cursor.getColumnIndex(trench_profile)));



                h.put("MN_Trenching_Depth",cursor.getString(cursor.getColumnIndex(DEPTH)));
                h.put("MN_Trenching_UpperWidth",cursor.getString(cursor.getColumnIndex(WIDTH)));
                h.put("TypeofTerrain",cursor.getString(cursor.getColumnIndex(TypeofTerrain)));
                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));

                h.put("TN_Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("Photo",cursor.getString(cursor.getColumnIndex(PHOTO)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(ImageData)));

                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public boolean LoweringInsert(HashMap<String,String> hashMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));

        contentValues.put(CHAINAGE_FROM, hashMap.get("ChainageFrom"));
        contentValues.put(CHAINAGE_TO, hashMap.get("ChainageTo"));
        contentValues.put(SECTION_LENGTH, hashMap.get("SectionLength"));

        contentValues.put(DATE, hashMap.get("LoweringDate"));
        contentValues.put(REPORT_NO, hashMap.get("LoweringRepNo"));
        contentValues.put(Alignment_Sheet, hashMap.get("Alignment_Sheet"));

        contentValues.put(JOINT_FROM, hashMap.get("JointFrom"));
        contentValues.put(JOINT_TO, hashMap.get("JointTo"));

        contentValues.put(PRE_PADDING_CHECK, hashMap.get("PrePadding"));
        contentValues.put(HOLIDAY_CHECK, hashMap.get("HolidayChecking"));
        contentValues.put(POST_PADDING_CHECK, hashMap.get("PostPadding"));

        contentValues.put(PipeMaterial, hashMap.get("PipeMaterial"));
        contentValues.put(PipeDia, hashMap.get("PipeDia"));
        contentValues.put(location, hashMap.get("location"));
        contentValues.put(WallThikness, hashMap.get("WallThikness"));
        contentValues.put(holiday_detector, hashMap.get("holiday_detector"));
        contentValues.put(model, hashMap.get("model"));
        contentValues.put(voltage, hashMap.get("voltage"));
        contentValues.put(calibration, hashMap.get("calibration"));
        contentValues.put(repair_damages, hashMap.get("repair_damages"));


        contentValues.put(Weather, hashMap.get("Weather"));
        contentValues.put(Dewatering, hashMap.get("Dewatering"));
        contentValues.put(PaddingMaterial, hashMap.get("PaddingMaterial"));
        contentValues.put(Padding, hashMap.get("Padding"));


        contentValues.put(REMARK, hashMap.get("Remarks"));
        contentValues.put(PHOTO, hashMap.get("Photo"));
        contentValues.put(ImageData, hashMap.get("ImageData"));
        int i = (int) db.insert(LOWERING_TABLE, null, contentValues);
        if (i==0){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getLoweringData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +LOWERING_TABLE, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();

                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("LoweringDate",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("LoweringRepNo",cursor.getString(cursor.getColumnIndex(REPORT_NO)));
                h.put("Alignment_Sheet",cursor.getString(cursor.getColumnIndex(Alignment_Sheet)));

                h.put("ChainageFrom",cursor.getString(cursor.getColumnIndex(CHAINAGE_FROM)));
                h.put("ChainageTo",cursor.getString(cursor.getColumnIndex(CHAINAGE_TO)));
                h.put("SectionLength",cursor.getString(cursor.getColumnIndex(SECTION_LENGTH)));

                h.put("JointFrom",cursor.getString(cursor.getColumnIndex(JOINT_FROM)));
                h.put("JointTo",cursor.getString(cursor.getColumnIndex(JOINT_TO)));

                h.put("PrePadding",cursor.getString(cursor.getColumnIndex(PRE_PADDING_CHECK)));
                h.put("HolidayChecking",cursor.getString(cursor.getColumnIndex(HOLIDAY_CHECK)));
                h.put("PostPadding",cursor.getString(cursor.getColumnIndex(POST_PADDING_CHECK)));

                h.put("PipeMaterial",cursor.getString(cursor.getColumnIndex(PipeMaterial)));
                h.put("PipeDia",cursor.getString(cursor.getColumnIndex(PipeDia)));
                h.put("WallThikness",cursor.getString(cursor.getColumnIndex(WallThikness)));
                h.put("location",cursor.getString(cursor.getColumnIndex(location)));
                h.put("holiday_detector",cursor.getString(cursor.getColumnIndex(holiday_detector)));
                h.put("model",cursor.getString(cursor.getColumnIndex(model)));
                h.put("voltage",cursor.getString(cursor.getColumnIndex(voltage)));
                h.put("calibration",cursor.getString(cursor.getColumnIndex(calibration)));
                h.put("repair_damages",cursor.getString(cursor.getColumnIndex(repair_damages)));




                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));
                h.put("Dewatering",cursor.getString(cursor.getColumnIndex(Dewatering)));
                h.put("PaddingMaterial",cursor.getString(cursor.getColumnIndex(PaddingMaterial)));
                h.put("Padding",cursor.getString(cursor.getColumnIndex(Padding)));

                h.put("Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(ImageData)));
                h.put("Photo",cursor.getString(cursor.getColumnIndex(PHOTO)));

                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }
    public boolean BackfillingInsert(HashMap<String,String> hashMap) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));
        contentValues.put(DATE, hashMap.get("BackfillingDate"));
        contentValues.put(REPORT_NO, hashMap.get("ReportNo"));
        contentValues.put(Weather, hashMap.get("Weather"));

        contentValues.put(SECTION_LENGTH, hashMap.get("SectionLength"));
        contentValues.put(Alignment_Sheet, hashMap.get("Alignment_Sheet"));
        contentValues.put(PostPadding, hashMap.get("PostPadding"));
        contentValues.put(Slopebreaker, hashMap.get("Slopebreaker"));
        contentValues.put(antibuyo, hashMap.get("antibuyo"));
        contentValues.put(cover, hashMap.get("cover"));
        contentValues.put(warning, hashMap.get("warning"));
        contentValues.put(center_level, hashMap.get("center_level"));
        contentValues.put(Plasticgrating, hashMap.get("Plasticgrating"));
        contentValues.put(Pipe_dia, hashMap.get("Pipe_dia"));
        contentValues.put(Pipe_thick, hashMap.get("Pipe_thick"));
        contentValues.put(JOINT_FROM, hashMap.get("JointFrom"));
        contentValues.put(JOINT_TO, hashMap.get("JointTo"));
        contentValues.put(REMARK, hashMap.get("Remarks"));

        contentValues.put(chfrom, hashMap.get("ChainageFrom"));
        contentValues.put(chto, hashMap.get("ChainageTo"));
        contentValues.put(Manpower, hashMap.get("Manpower"));
        contentValues.put(Machineries, hashMap.get("Machineries"));
        contentValues.put(TN_HolidayCheck, hashMap.get("TN_HolidayCheck"));
        contentValues.put(TN_SoilRplacment, hashMap.get("TN_SoilRplacment"));
        contentValues.put(TN_SaddleWeight, hashMap.get("TN_SaddleWeight"));
        contentValues.put(TN_RockBackfill, hashMap.get("TN_RockBackfill"));
        contentValues.put(TN_CenterlineCheck, hashMap.get("TN_CenterlineCheck"));
        contentValues.put(TN_coating, hashMap.get("TN_coating"));


        contentValues.put(PHOTO, hashMap.get("Photo"));
        contentValues.put(ImageData, hashMap.get("ImageData"));
        int i = (int) db.insert(BACKFILLING_TABLE, null, contentValues);
        if (i==0){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getBackfillingData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +BACKFILLING_TABLE, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();
                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("BackfillingDate",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("ReportNo",cursor.getString(cursor.getColumnIndex(REPORT_NO)));
                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));

                h.put("SectionLength",cursor.getString(cursor.getColumnIndex(SECTION_LENGTH)));
                h.put("PostPadding",cursor.getString(cursor.getColumnIndex(PostPadding)));
                h.put("Slopebreaker",cursor.getString(cursor.getColumnIndex(Slopebreaker)));
                h.put("antibuyo",cursor.getString(cursor.getColumnIndex(antibuyo)));
                h.put("cover",cursor.getString(cursor.getColumnIndex(cover)));
                h.put("warning",cursor.getString(cursor.getColumnIndex(warning)));
                h.put("center_level",cursor.getString(cursor.getColumnIndex(center_level)));
                h.put("Plasticgrating",cursor.getString(cursor.getColumnIndex(Plasticgrating)));
                h.put("Alignment_Sheet",cursor.getString(cursor.getColumnIndex(Alignment_Sheet)));
                h.put("Pipe_dia",cursor.getString(cursor.getColumnIndex(Pipe_dia)));
                h.put("Pipe_thick",cursor.getString(cursor.getColumnIndex(Pipe_thick)));
                h.put("JointFrom",cursor.getString(cursor.getColumnIndex(JOINT_FROM)));
                h.put("JointTo",cursor.getString(cursor.getColumnIndex(JOINT_TO)));
                h.put("ChainageFrom",cursor.getString(cursor.getColumnIndex(chfrom)));
                h.put("ChainageTo",cursor.getString(cursor.getColumnIndex(chto)));

                h.put("Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("Manpower",cursor.getString(cursor.getColumnIndex(Manpower)));
                h.put("Machineries",cursor.getString(cursor.getColumnIndex(Machineries)));
                h.put("TN_HolidayCheck",cursor.getString(cursor.getColumnIndex(TN_HolidayCheck)));
                h.put("TN_SoilRplacment",cursor.getString(cursor.getColumnIndex(TN_SoilRplacment)));
                h.put("TN_SaddleWeight",cursor.getString(cursor.getColumnIndex(TN_SaddleWeight)));
                h.put("TN_RockBackfill",cursor.getString(cursor.getColumnIndex(TN_RockBackfill)));
                h.put("TN_CenterlineCheck",cursor.getString(cursor.getColumnIndex(TN_CenterlineCheck)));
                h.put("TN_coating",cursor.getString(cursor.getColumnIndex(TN_coating)));
                h.put("Photo",cursor.getString(cursor.getColumnIndex(PHOTO)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(ImageData)));
                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public boolean OFCBlowingInsert(HashMap<String,String> hashMap) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));
        contentValues.put(DATE, hashMap.get("OFCBlowingDate"));
        contentValues.put(REPORT_NO, hashMap.get("ReportNo"));

        contentValues.put(DRUM_NO, hashMap.get("Drumno"));
        contentValues.put(DRUM_LENGTH, hashMap.get("Drum_lenth"));

        contentValues.put(sequencemeter, hashMap.get("sequencemeter"));
        contentValues.put(blowingpit, hashMap.get("blowingpit"));
        contentValues.put(SECTION_LENGTH, hashMap.get("SectionLength"));

        contentValues.put(JOINT_FROM, hashMap.get("JointFrom"));
        contentValues.put(JOINT_TO, hashMap.get("JointTo"));

        contentValues.put(LOOP_AT_PIT, hashMap.get("loopatpit"));
        contentValues.put(PIT_CABLE_LENGTH, hashMap.get("Pitcabel_lenth"));

        contentValues.put(REMARK, hashMap.get("Remarks"));
        contentValues.put(PHOTO, hashMap.get("ImageData"));
        contentValues.put(Weather, hashMap.get("Weather"));

        int i = (int) db.insert(OFCBLOWING_TABLE, null, contentValues);
        if (i==0){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getOFCData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +OFCBLOWING_TABLE, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();
                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("OFCBlowingDate",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("ReportNo",cursor.getString(cursor.getColumnIndex(REPORT_NO)));

                h.put("Drumno",cursor.getString(cursor.getColumnIndex(DRUM_NO)));
                h.put("Drum_lenth",cursor.getString(cursor.getColumnIndex(DRUM_LENGTH)));

                h.put("SectionLength",cursor.getString(cursor.getColumnIndex(SECTION_LENGTH)));
                h.put("sequencemeter",cursor.getString(cursor.getColumnIndex(sequencemeter)));
                h.put("blowingpit",cursor.getString(cursor.getColumnIndex(blowingpit)));


                h.put("JointFrom",cursor.getString(cursor.getColumnIndex(JOINT_FROM)));
                h.put("JointTo",cursor.getString(cursor.getColumnIndex(JOINT_TO)));

                h.put("loopatpit",cursor.getString(cursor.getColumnIndex(LOOP_AT_PIT)));
                h.put("Pitcabel_lenth",cursor.getString(cursor.getColumnIndex(PIT_CABLE_LENGTH)));

                h.put("Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(PHOTO)));
                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));
                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }
    public boolean DryingInsert(HashMap<String,String> hashMap) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));
        contentValues.put(DATE, hashMap.get("DryingDate"));
        contentValues.put(REPORT_NO, hashMap.get("ReportNo"));

        contentValues.put(START_DATE, hashMap.get("Satart_date"));
        contentValues.put(END_DATE, hashMap.get("End_date"));

        contentValues.put(JOINT_FROM, hashMap.get("JointFrom"));
        contentValues.put(JOINT_TO, hashMap.get("JointTo"));

        contentValues.put(REMARK, hashMap.get("Remarks"));
        contentValues.put(PHOTO, hashMap.get("ImageData"));

        int i = (int) db.insert(DRYING_TABLE, null, contentValues);
        if (i==0){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getDryingData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +DRYING_TABLE, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();

                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("DryingDate",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("ReportNo",cursor.getString(cursor.getColumnIndex(REPORT_NO)));

                h.put("Satart_date",cursor.getString(cursor.getColumnIndex(START_DATE)));
                h.put("End_date",cursor.getString(cursor.getColumnIndex(END_DATE)));

                h.put("JointFrom",cursor.getString(cursor.getColumnIndex(JOINT_FROM)));
                h.put("JointTo",cursor.getString(cursor.getColumnIndex(JOINT_TO)));

                h.put("Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(PHOTO)));

                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public boolean RestorationInsert(HashMap<String, String> hashMap) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));

        contentValues.put(DATE, hashMap.get("RestorationBlowingDate"));
        contentValues.put(REPORT_NO, hashMap.get("ReportNo"));

        contentValues.put(CHAINAGE_FROM, hashMap.get("chainge_from"));
        contentValues.put(CHAINAGE_TO, hashMap.get("chainge_to"));

        contentValues.put(LAND_TYPE, hashMap.get("Type_land"));

        contentValues.put(SURPLUS_MATERIAL, hashMap.get("Surplus_Material"));
        contentValues.put(TOP_SOIL, hashMap.get("Replacement_topSoil"));
        contentValues.put(REINSTALLATION_BOUNDRY, hashMap.get("ReinstallationBoundry"));

        contentValues.put(REMARK, hashMap.get("Remarks"));
        contentValues.put(PHOTO, hashMap.get("ImageData"));

        int i = (int) db.insert(RESTORATION_TABLE, null, contentValues);
        if (i==0){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getRestorationData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +RESTORATION_TABLE, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();

                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("RestorationBlowingDate",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("ReportNo",cursor.getString(cursor.getColumnIndex(REPORT_NO)));

                h.put("chainge_from",cursor.getString(cursor.getColumnIndex(CHAINAGE_FROM)));
                h.put("chainge_to",cursor.getString(cursor.getColumnIndex(CHAINAGE_TO)));
                h.put("Type_land",cursor.getString(cursor.getColumnIndex(LAND_TYPE)));

                h.put("Surplus_Material",cursor.getString(cursor.getColumnIndex(SURPLUS_MATERIAL)));
                h.put("Replacement_topSoil",cursor.getString(cursor.getColumnIndex(TOP_SOIL)));
                h.put("ReinstallationBoundry",cursor.getString(cursor.getColumnIndex(REINSTALLATION_BOUNDRY)));

                h.put("Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(PHOTO)));

                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public boolean RadioGraphyInsert(HashMap<String, String> hashMap) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));

        contentValues.put(DATE, hashMap.get("WeldTestDate"));
        contentValues.put(REPORT_NO, hashMap.get("ReportNo"));

        contentValues.put(JOINT_FROM, hashMap.get("JointId"));
        contentValues.put(RESULT, hashMap.get("Result"));

        contentValues.put(REMARK, hashMap.get("Remarks"));


        contentValues.put(Weather, hashMap.get("Weather"));
        contentValues.put(SEGMENT1, hashMap.get("segment1"));
        contentValues.put(SEGMENT2, hashMap.get("segment2"));
        contentValues.put(SEGMENT3, hashMap.get("segment3"));
        contentValues.put(SEGMENT4, hashMap.get("segment4"));
        contentValues.put(observation, hashMap.get("observation"));
        contentValues.put(observation2, hashMap.get("observation2"));
        contentValues.put(observation3, hashMap.get("observation3"));
        contentValues.put(observation4, hashMap.get("observation4"));


        contentValues.put(RootRepair, hashMap.get("RootRepair"));
        contentValues.put(RWelder, hashMap.get("RWelder"));
        contentValues.put(HotPass, hashMap.get("HotPass"));
        contentValues.put(HWelder, hashMap.get("HWelder"));
        contentValues.put(Filler, hashMap.get("Filler"));
        contentValues.put(FWelder, hashMap.get("FWelder"));
        contentValues.put(Capping, hashMap.get("Capping"));
        contentValues.put(CWelder, hashMap.get("CWelder"));


        contentValues.put(RootRepair2, hashMap.get("RootRepair2"));
        contentValues.put(RWelder2, hashMap.get("RWelder2"));
        contentValues.put(HotPass2, hashMap.get("HotPass2"));
        contentValues.put(HWelder2, hashMap.get("HWelder2"));
        contentValues.put(Filler2, hashMap.get("Filler2"));
        contentValues.put(FWelder2, hashMap.get("FWelder2"));
        contentValues.put(Capping2, hashMap.get("Capping2"));
        contentValues.put(CWelder2, hashMap.get("CWelder2"));

        contentValues.put(RootRepair3, hashMap.get("RootRepair3"));
        contentValues.put(RWelder3, hashMap.get("RWelder3"));
        contentValues.put(HotPass3, hashMap.get("HotPass3"));
        contentValues.put(HWelder3, hashMap.get("HWelder3"));
        contentValues.put(Filler3, hashMap.get("Filler3"));
        contentValues.put(FWelder3, hashMap.get("FWelder3"));
        contentValues.put(Capping3, hashMap.get("Capping3"));
        contentValues.put(CWelder3, hashMap.get("CWelder3"));

        contentValues.put(RootRepair4, hashMap.get("RootRepair4"));
        contentValues.put(RWelder4, hashMap.get("RWelder4"));
        contentValues.put(HotPass4, hashMap.get("HotPass4"));
        contentValues.put(HWelder4, hashMap.get("HWelder4"));
        contentValues.put(Filler4, hashMap.get("Filler4"));
        contentValues.put(FWelder4, hashMap.get("FWelder4"));
        contentValues.put(Capping4, hashMap.get("Capping4"));
        contentValues.put(CWelder4, hashMap.get("CWelder4"));

        contentValues.put(penetrameter, hashMap.get("penetrameter"));
        contentValues.put(film_type, hashMap.get("film_type"));
        contentValues.put(sensitivity, hashMap.get("sensitivity"));
        contentValues.put(gray_value, hashMap.get("gray_value"));
        contentValues.put(thickness, hashMap.get("thickness"));
        contentValues.put(Alignment_Sheet, hashMap.get("Alignment_Sheet"));
        contentValues.put(ChainageFrom, hashMap.get("ChainageFrom"));
        contentValues.put(ChaninageTo, hashMap.get("ChaninageTo"));
        contentValues.put(source, hashMap.get("source"));






        contentValues.put(PHOTO, hashMap.get("Photo"));
        contentValues.put(ImageData, hashMap.get("ImageData"));

        int i = (int) db.insert(RADIO_GRAPHY_TABLE, null, contentValues);
        if (i==0){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getRadiographyData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +RADIO_GRAPHY_TABLE, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();

                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));

                h.put("WeldTestDate",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("ReportNo",cursor.getString(cursor.getColumnIndex(REPORT_NO)));

                h.put("JointId",cursor.getString(cursor.getColumnIndex(JOINT_FROM)));
                h.put("Result",cursor.getString(cursor.getColumnIndex(RESULT)));
                h.put("observation",cursor.getString(cursor.getColumnIndex(observation)));
                h.put("observation2",cursor.getString(cursor.getColumnIndex(observation2)));
                h.put("observation3",cursor.getString(cursor.getColumnIndex(observation3)));
                h.put("observation4",cursor.getString(cursor.getColumnIndex(observation4)));
                h.put("Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));

                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));
                h.put("segment1",cursor.getString(cursor.getColumnIndex(SEGMENT1)));
                h.put("segment2",cursor.getString(cursor.getColumnIndex(SEGMENT2)));
                h.put("segment3",cursor.getString(cursor.getColumnIndex(SEGMENT3)));
                h.put("segment4",cursor.getString(cursor.getColumnIndex(SEGMENT4)));

                h.put("RootRepair",cursor.getString(cursor.getColumnIndex(RootRepair)));
                h.put("RWelder",cursor.getString(cursor.getColumnIndex(RWelder)));
                h.put("HotPass",cursor.getString(cursor.getColumnIndex(HotPass)));
                h.put("HWelder",cursor.getString(cursor.getColumnIndex(HWelder)));
                h.put("Filler",cursor.getString(cursor.getColumnIndex(Filler)));
                h.put("FWelder",cursor.getString(cursor.getColumnIndex(FWelder)));
                h.put("Capping",cursor.getString(cursor.getColumnIndex(Capping)));
                h.put("CWelder",cursor.getString(cursor.getColumnIndex(CWelder)));

                h.put("RootRepair2",cursor.getString(cursor.getColumnIndex(RootRepair2)));
                h.put("RWelder2",cursor.getString(cursor.getColumnIndex(RWelder2)));
                h.put("HotPass2",cursor.getString(cursor.getColumnIndex(HotPass2)));
                h.put("HWelder2",cursor.getString(cursor.getColumnIndex(HWelder2)));
                h.put("Filler2",cursor.getString(cursor.getColumnIndex(Filler2)));
                h.put("FWelder2",cursor.getString(cursor.getColumnIndex(FWelder2)));
                h.put("Capping2",cursor.getString(cursor.getColumnIndex(Capping2)));
                h.put("CWelder2",cursor.getString(cursor.getColumnIndex(CWelder2)));

                h.put("RootRepair3",cursor.getString(cursor.getColumnIndex(RootRepair3)));
                h.put("RWelder3",cursor.getString(cursor.getColumnIndex(RWelder3)));
                h.put("HotPass3",cursor.getString(cursor.getColumnIndex(HotPass3)));
                h.put("HWelder3",cursor.getString(cursor.getColumnIndex(HWelder3)));
                h.put("Filler3",cursor.getString(cursor.getColumnIndex(Filler3)));
                h.put("FWelder3",cursor.getString(cursor.getColumnIndex(FWelder3)));
                h.put("Capping3",cursor.getString(cursor.getColumnIndex(Capping3)));
                h.put("CWelder3",cursor.getString(cursor.getColumnIndex(CWelder3)));

                h.put("RootRepair4",cursor.getString(cursor.getColumnIndex(RootRepair4)));
                h.put("RWelder4",cursor.getString(cursor.getColumnIndex(RWelder4)));
                h.put("HotPass4",cursor.getString(cursor.getColumnIndex(HotPass4)));
                h.put("HWelder4",cursor.getString(cursor.getColumnIndex(HWelder4)));
                h.put("Filler4",cursor.getString(cursor.getColumnIndex(Filler4)));
                h.put("FWelder4",cursor.getString(cursor.getColumnIndex(FWelder4)));
                h.put("Capping4",cursor.getString(cursor.getColumnIndex(Capping4)));
                h.put("CWelder4",cursor.getString(cursor.getColumnIndex(CWelder4)));


                h.put("penetrameter",cursor.getString(cursor.getColumnIndex(penetrameter)));
                h.put("film_type",cursor.getString(cursor.getColumnIndex(film_type)));
                h.put("sensitivity",cursor.getString(cursor.getColumnIndex(sensitivity)));
                h.put("gray_value",cursor.getString(cursor.getColumnIndex(gray_value)));
                h.put("thickness",cursor.getString(cursor.getColumnIndex(thickness)));
                h.put("Alignment_Sheet",cursor.getString(cursor.getColumnIndex(Alignment_Sheet)));
                h.put("ChainageFrom",cursor.getString(cursor.getColumnIndex(ChainageFrom)));
                h.put("ChaninageTo",cursor.getString(cursor.getColumnIndex(ChaninageTo)));
                h.put("source",cursor.getString(cursor.getColumnIndex(source)));




                h.put("Photo",cursor.getString(cursor.getColumnIndex(PHOTO)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(ImageData)));
                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public boolean UTInsert(HashMap<String, String> hashMap) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));

        contentValues.put(DATE, hashMap.get("WeldTestDate"));
        contentValues.put(REPORT_NO, hashMap.get("ReportNo"));

        contentValues.put(JOINT_FROM, hashMap.get("JointId"));
        contentValues.put(RESULT, hashMap.get("Result"));

        contentValues.put(REMARK, hashMap.get("Remarks"));
        contentValues.put(PHOTO, hashMap.get("ImageData"));
        contentValues.put(Weather, hashMap.get("Weather"));
        contentValues.put(SEGMENT1, hashMap.get("segment1"));
        contentValues.put(SEGMENT2, hashMap.get("segment2"));
        contentValues.put(SEGMENT3, hashMap.get("segment3"));
        contentValues.put(SEGMENT4, hashMap.get("segment4"));
        contentValues.put(observation, hashMap.get("observation"));
        contentValues.put(observation2, hashMap.get("observation2"));
        contentValues.put(observation3, hashMap.get("observation3"));
        contentValues.put(observation4, hashMap.get("observation4"));
        int i = (int) db.insert(UT_TABLE, null, contentValues);
        if (i==0){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getUTData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + UT_TABLE, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();

                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));

                h.put("WeldTestDate",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("ReportNo",cursor.getString(cursor.getColumnIndex(REPORT_NO)));

                h.put("JointId",cursor.getString(cursor.getColumnIndex(JOINT_FROM)));
                h.put("Result",cursor.getString(cursor.getColumnIndex(RESULT)));
                h.put("observation",cursor.getString(cursor.getColumnIndex(observation)));
                h.put("observation2",cursor.getString(cursor.getColumnIndex(observation2)));
                h.put("observation3",cursor.getString(cursor.getColumnIndex(observation3)));
                h.put("observation4",cursor.getString(cursor.getColumnIndex(observation4)));

                h.put("Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(PHOTO)));
                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));
                h.put("segment1",cursor.getString(cursor.getColumnIndex(SEGMENT1)));
                h.put("segment2",cursor.getString(cursor.getColumnIndex(SEGMENT2)));
                h.put("segment3",cursor.getString(cursor.getColumnIndex(SEGMENT3)));
                h.put("segment4",cursor.getString(cursor.getColumnIndex(SEGMENT4)));

                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public boolean InsertJointCoating(HashMap<String, String> hashMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));

        contentValues.put(DATE, hashMap.get("JointDate"));
        contentValues.put(REPORT_NO, hashMap.get("JointCoatingRepNo"));

        contentValues.put(JOINT_FROM, hashMap.get("JointID"));

        contentValues.put(VISUAL_CHECK, hashMap.get("Visuals"));
        contentValues.put(HOLIDAY_CHECK, hashMap.get("Holiday"));

        contentValues.put(BODY_TEMP, hashMap.get("PipeCoatingThickness"));
        contentValues.put(WELD_TEMP, hashMap.get("JointCoatingThickness"));

        contentValues.put(REMARK, hashMap.get("Remarks"));

        contentValues.put(Weather, hashMap.get("Weather"));
        contentValues.put(pipe_dia, hashMap.get("pipe_dia"));
        contentValues.put(pipe_thickness, hashMap.get("pipe_thickness"));
    //    contentValues.put(batch_no, hashMap.get("batch_no"));
        contentValues.put(roughness, hashMap.get("roughness"));
        contentValues.put(dust_containment, hashMap.get("dust_containment"));
        contentValues.put(primer, hashMap.get("primer"));
        contentValues.put(primer_b, hashMap.get("primer_b"));
        contentValues.put(onbody, hashMap.get("onbody"));
        contentValues.put(onseam, hashMap.get("onseam"));
        contentValues.put(relative_humidity, hashMap.get("relative_humidity"));
    //    contentValues.put(coating_type, hashMap.get("coating_type"));
    //    contentValues.put(sleeve_option, hashMap.get("sleeve_option"));


        contentValues.put(Primer_A_Batch, hashMap.get("Primer_A_Batch"));
        contentValues.put(Primer_B_Batch, hashMap.get("Primer_B_Batch"));
        contentValues.put(Holiday_Tester, hashMap.get("Holiday_Tester"));
        contentValues.put(Type_Coating, hashMap.get("Type_Coating"));
        contentValues.put(Sleave, hashMap.get("Sleave"));
        contentValues.put(Callibration, hashMap.get("Callibration"));
        contentValues.put(BatchNo, hashMap.get("BatchNo"));
        contentValues.put(TN_RH, hashMap.get("TN_RH"));
        contentValues.put(TN_Elcometer, hashMap.get("TN_Elcometer"));
        contentValues.put(TN_PeelTest, hashMap.get("TN_PeelTest"));
        contentValues.put(Roughness, hashMap.get("Roughness"));
        contentValues.put(Dust, hashMap.get("Dust"));
        contentValues.put(Profile, hashMap.get("Profile"));
        contentValues.put(WFT, hashMap.get("WFT"));


        contentValues.put(Material, hashMap.get("Material"));
        contentValues.put(ChainageFrom, hashMap.get("ChainageFrom"));
        contentValues.put(ChainageTo, hashMap.get("ChainageTo"));
        contentValues.put(Thickness, hashMap.get("Thickness"));
        contentValues.put(Coating_Thick, hashMap.get("Coating_Thick"));
        contentValues.put(pre_heating, hashMap.get("pre_heating"));
        contentValues.put(Alignment_Sheet, hashMap.get("Alignment_Sheet"));
        contentValues.put(TN_Manpower , hashMap.get("TN_Manpower"));
        contentValues.put(TN_Machineries , hashMap.get("TN_Machineries"));



        contentValues.put(body_3, hashMap.get("body_3"));
        contentValues.put(body_6, hashMap.get("body_6"));
        contentValues.put(body_9, hashMap.get("body_9"));
        contentValues.put(body_12, hashMap.get("body_12"));
        contentValues.put(body_avg, hashMap.get("body_avg"));
        contentValues.put(bodyu_3, hashMap.get("bodyu_3"));
        contentValues.put(bodyu_6, hashMap.get("bodyu_6"));
        contentValues.put(bodyu_9, hashMap.get("bodyu_9"));
        contentValues.put(bodyu_12, hashMap.get("bodyu_12"));
        contentValues.put(bodyu_avg, hashMap.get("bodyu_avg"));
        contentValues.put(joint_3, hashMap.get("joint_3"));
        contentValues.put(joint_6, hashMap.get("joint_6"));
        contentValues.put(joint_9, hashMap.get("joint_9"));
        contentValues.put(joint_12, hashMap.get("joint_12"));
        contentValues.put(joint_avg, hashMap.get("joint_avg"));

        contentValues.put(PHOTO, hashMap.get("Photo"));
        contentValues.put(ImageData, hashMap.get("ImageData"));

        int i = (int) db.insert(JOINT_COATING_TABLE, null, contentValues);
        if (i==0){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getJointCoatingData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +JOINT_COATING_TABLE, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();

                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));

                h.put("JointDate",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("JointCoatingRepNo",cursor.getString(cursor.getColumnIndex(REPORT_NO)));

                h.put("JointID",cursor.getString(cursor.getColumnIndex(JOINT_FROM)));

                h.put("Visuals",cursor.getString(cursor.getColumnIndex(VISUAL_CHECK)));
                h.put("Holiday",cursor.getString(cursor.getColumnIndex(HOLIDAY_CHECK)));
                h.put("PipeCoatingThickness",cursor.getString(cursor.getColumnIndex(BODY_TEMP)));
                h.put("JointCoatingThickness",cursor.getString(cursor.getColumnIndex(WELD_TEMP)));

                h.put("Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));

                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));
                h.put("pipe_dia",cursor.getString(cursor.getColumnIndex(pipe_dia)));
                h.put("pipe_thickness",cursor.getString(cursor.getColumnIndex(pipe_thickness)));
      //          h.put("batch_no",cursor.getString(cursor.getColumnIndex(batch_no)));
                h.put("roughness",cursor.getString(cursor.getColumnIndex(roughness)));
                h.put("dust_containment",cursor.getString(cursor.getColumnIndex(dust_containment)));
                h.put("primer",cursor.getString(cursor.getColumnIndex(primer)));
                h.put("primer_b",cursor.getString(cursor.getColumnIndex(primer_b)));
                h.put("onbody",cursor.getString(cursor.getColumnIndex(onbody)));
                h.put("onseam",cursor.getString(cursor.getColumnIndex(onseam)));
                h.put("relative_humidity",cursor.getString(cursor.getColumnIndex(relative_humidity)));
      //          h.put("coating_type",cursor.getString(cursor.getColumnIndex(coating_type)));
      //          h.put("sleeve_option",cursor.getString(cursor.getColumnIndex(sleeve_option)));


                h.put("Primer_A_Batch",cursor.getString(cursor.getColumnIndex(Primer_A_Batch)));
                h.put("Primer_B_Batch",cursor.getString(cursor.getColumnIndex(Primer_B_Batch)));
                h.put("Holiday_Tester",cursor.getString(cursor.getColumnIndex(Holiday_Tester)));
                h.put("Type_Coating",cursor.getString(cursor.getColumnIndex(Type_Coating)));
                h.put("Sleave",cursor.getString(cursor.getColumnIndex(Sleave)));
                h.put("Callibration",cursor.getString(cursor.getColumnIndex(Callibration)));
                h.put("BatchNo",cursor.getString(cursor.getColumnIndex(BatchNo)));
                h.put("TN_RH",cursor.getString(cursor.getColumnIndex(TN_RH)));
                h.put("TN_Elcometer",cursor.getString(cursor.getColumnIndex(TN_Elcometer)));
                h.put("TN_PeelTest",cursor.getString(cursor.getColumnIndex(TN_PeelTest)));
                h.put("Roughness",cursor.getString(cursor.getColumnIndex(Roughness)));
                h.put("Dust",cursor.getString(cursor.getColumnIndex(Dust)));
                h.put("Profile",cursor.getString(cursor.getColumnIndex(Profile)));
                h.put("WFT",cursor.getString(cursor.getColumnIndex(WFT)));


                h.put("Material",cursor.getString(cursor.getColumnIndex(Material)));
                h.put("ChainageFrom",cursor.getString(cursor.getColumnIndex(ChainageFrom)));
                h.put("ChainageTo",cursor.getString(cursor.getColumnIndex(ChainageTo)));
                h.put("Thickness",cursor.getString(cursor.getColumnIndex(Thickness)));
                h.put("Coating_Thick",cursor.getString(cursor.getColumnIndex(Coating_Thick)));
                h.put("pre_heating",cursor.getString(cursor.getColumnIndex(pre_heating)));
                h.put("Alignment_Sheet",cursor.getString(cursor.getColumnIndex(Alignment_Sheet)));
                h.put("TN_Manpower",cursor.getString(cursor.getColumnIndex(TN_Manpower)));
                h.put("TN_Machineries",cursor.getString(cursor.getColumnIndex("TN_Machineries")));




                h.put("body_3",cursor.getString(cursor.getColumnIndex(body_3)));
                h.put("body_6",cursor.getString(cursor.getColumnIndex(body_6)));
                h.put("body_9",cursor.getString(cursor.getColumnIndex(body_9)));
                h.put("body_12",cursor.getString(cursor.getColumnIndex(body_12)));
                h.put("body_avg",cursor.getString(cursor.getColumnIndex(body_avg)));

               h.put("bodyu_3",cursor.getString(cursor.getColumnIndex(bodyu_3)));
                h.put("bodyu_6",cursor.getString(cursor.getColumnIndex(bodyu_6)));
                h.put("bodyu_9",cursor.getString(cursor.getColumnIndex(bodyu_9)));
                h.put("bodyu_12",cursor.getString(cursor.getColumnIndex(bodyu_12)));
                h.put("bodyu_avg",cursor.getString(cursor.getColumnIndex(bodyu_avg)));

                h.put("joint_3",cursor.getString(cursor.getColumnIndex(joint_3)));
                h.put("joint_6",cursor.getString(cursor.getColumnIndex(joint_6)));
                h.put("joint_9",cursor.getString(cursor.getColumnIndex(joint_9)));
                h.put("joint_12",cursor.getString(cursor.getColumnIndex(joint_12)));
                h.put("joint_avg",cursor.getString(cursor.getColumnIndex(joint_avg)));
                h.put("Photo",cursor.getString(cursor.getColumnIndex(PHOTO)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(ImageData)));
                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public boolean HydroTestingInsert(HashMap<String,String> hashMap) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));
        contentValues.put(DATE, hashMap.get("ReportDate"));
        contentValues.put(REPORT_NO, hashMap.get("ReportNo"));
        contentValues.put(Weather, hashMap.get("Weather"));

        contentValues.put(JOINT_FROM, hashMap.get("JointFrom"));
        contentValues.put(JOINT_TO, hashMap.get("JointTo"));
        contentValues.put(SECTION_LENGTH, hashMap.get("HydrotestingLength"));

        /*contentValues.put(HYDRO_PLAN_DATE, hashMap.get("Padding"));
        contentValues.put(HYDRO_PLAN_REPORT_NO, hashMap.get("Warnning_mat"));*/

        contentValues.put(REMARK, hashMap.get("Remarks"));
        contentValues.put(PHOTO, hashMap.get("ImageData"));

        int i = (int) db.insert(HYDRO_TESTING, null, contentValues);
        if (i==0){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getHydroTestingData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +HYDRO_TESTING, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();
                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));
                h.put("ReportDate",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("ReportNo",cursor.getString(cursor.getColumnIndex(REPORT_NO)));
                h.put("JointFrom",cursor.getString(cursor.getColumnIndex(JOINT_FROM)));
                h.put("JointTo",cursor.getString(cursor.getColumnIndex(JOINT_TO)));
                h.put("HydrotestingLength",cursor.getString(cursor.getColumnIndex(SECTION_LENGTH)));
                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));

                h.put("Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(PHOTO)));
                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }
    public boolean LevellingInsert(HashMap<String, String> hashMap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(USER_ID, hashMap.get("UserID"));
        contentValues.put(SECTION_ID, hashMap.get("SectionID"));
        contentValues.put(TYPE, hashMap.get("type"));

        contentValues.put(DATE, hashMap.get("LevellingDate"));
        contentValues.put(REPORT_NO, hashMap.get("LevellingRepNo"));

        contentValues.put(JOINT_FROM, hashMap.get("JointID"));

        contentValues.put(Cover, hashMap.get("Cover"));
        contentValues.put(XCord, hashMap.get("XCord"));
        contentValues.put(YCord, hashMap.get("YCord"));
        contentValues.put(ZCord, hashMap.get("ZCord"));
        contentValues.put(Weather, hashMap.get("Weather"));
        contentValues.put(NGL, hashMap.get("NGL"));
        contentValues.put(ChFrom, hashMap.get("ChFrom"));
        contentValues.put(ChTo, hashMap.get("ChTo"));
        contentValues.put(Elevation, hashMap.get("Elevation"));
        contentValues.put(PipeDia, hashMap.get("PipeDia"));
        contentValues.put(REMARK, hashMap.get("Remarks"));
        contentValues.put(Alignment_Sheet, hashMap.get("Alignment_Sheet"));
        contentValues.put(ImageData, hashMap.get("ImageData"));
        contentValues.put(JointFrom, hashMap.get("JointFrom"));
        contentValues.put(JointTo, hashMap.get("JointTo"));
        contentValues.put(PipeThik, hashMap.get("PipeThik"));
        contentValues.put(TpNo, hashMap.get("TpNo"));
        contentValues.put(SectionLength, hashMap.get("SectionLength"));
        contentValues.put(PHOTO, hashMap.get("Photo"));
        contentValues.put(ImageData, hashMap.get("ImageData"));
        int i = (int) db.insert(Levelling_TABLE, null, contentValues);
        if (i==0){
            return false;
        }else
            return true;
    }
    public ArrayList<HashMap<String, String>> getLevellingData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " +Levelling_TABLE, null );
        ArrayList<HashMap<String ,String >> arrayList=new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HashMap<String,String> h=new HashMap<>();

                h.put("COLUMN_ID",cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                h.put("UserID",cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.put("SectionID",cursor.getString(cursor.getColumnIndex(SECTION_ID)));
                h.put("type",cursor.getString(cursor.getColumnIndex(TYPE)));

                h.put("LevellingDate",cursor.getString(cursor.getColumnIndex(DATE)));
                h.put("LevellingRepNo",cursor.getString(cursor.getColumnIndex(REPORT_NO)));

                h.put("JointID",cursor.getString(cursor.getColumnIndex(JOINT_FROM)));

                h.put("Cover",cursor.getString(cursor.getColumnIndex(Cover)));

                h.put("XCord",cursor.getString(cursor.getColumnIndex(XCord)));
                h.put("YCord",cursor.getString(cursor.getColumnIndex(YCord)));
                h.put("ZCord",cursor.getString(cursor.getColumnIndex(ZCord)));
                h.put("ChFrom",cursor.getString(cursor.getColumnIndex(ChFrom)));

                h.put("ChTo",cursor.getString(cursor.getColumnIndex(ChTo)));
                h.put("Elevation",cursor.getString(cursor.getColumnIndex(Elevation)));
                h.put("PipeDia",cursor.getString(cursor.getColumnIndex(PipeDia)));


                h.put("Weather",cursor.getString(cursor.getColumnIndex(Weather)));
                h.put("NGL",cursor.getString(cursor.getColumnIndex(NGL)));


                h.put("Remarks",cursor.getString(cursor.getColumnIndex(REMARK)));
                h.put("Photo",cursor.getString(cursor.getColumnIndex(PHOTO)));
                h.put("ImageData",cursor.getString(cursor.getColumnIndex(ImageData)));
                h.put("JointFrom",cursor.getString(cursor.getColumnIndex(JointFrom)));
                h.put("JointTo",cursor.getString(cursor.getColumnIndex(JointTo)));
                h.put("PipeThik",cursor.getString(cursor.getColumnIndex(PipeThik)));
                h.put("TpNo",cursor.getString(cursor.getColumnIndex(TpNo)));
                h.put("SectionLength",cursor.getString(cursor.getColumnIndex(SectionLength)));
                h.put("Alignment_Sheet",cursor.getString(cursor.getColumnIndex(Alignment_Sheet)));
                arrayList.add(h);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }





}
