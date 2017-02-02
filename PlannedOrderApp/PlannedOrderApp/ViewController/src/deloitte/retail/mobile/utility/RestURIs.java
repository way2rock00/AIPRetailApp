package deloitte.retail.mobile.utility;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Date;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;

public class RestURIs {
    public RestURIs() {
        super();
    }

    private static String PO_SUMMARY = "/XxRpmGetPoSummary/GetPoSummary_RS";//http://app101111.glbsnet.com/XxRpmGetPoSummary/GetPoSummary_RS/-999/305
    private static String LoginURL = "/XxRpmValidateUser/ValidateUser_RS";
    private static String PlannedSummaryURL = "/XxRpmGetPlannedOrdersSummary/GetPlannedOrdersRS";
    private static String OrderHeaderDetailsURL = "/XxRpmGetPlannedOrderDetail/GetOrderDetailRS";
    private static String OrderDetailsURL = "/XxRpmGetPlanOrderDetails/GetPlanOrderDetailsRS";
    private static String OrderSearchURL ="/XxRpmGetAIPOrderHdr/GetAIPOrderHdrRS";
    
    
    public static String getLoginURL(String userName,String password,String deviceId,
                                     String loginType,String appId){
        return LoginURL+"/"+userName+"/"+password+"/"+deviceId+"/"+loginType+"/"+appId;
    }
    
    public static String getPlannedSummaryURL(String strPlanner){
        return PlannedSummaryURL+"/"+strPlanner;
    }
    
    public static String getOrderHeaderDetailsURL(String strSelectedStatus,String strPlanner){
        return OrderHeaderDetailsURL+"/"+convertString2URLFormat(strSelectedStatus)+"/"+strPlanner;
    }
    
    public static String getOrderDetailsURL(String strSelectedOrderNumber){
        return OrderDetailsURL+"/"+strSelectedOrderNumber;
    }
    
    public static String getOrderSearchURL(String strOrderType,String strStatus,//tempString strSource,
                                           String strDestination,String strDelivDateFrom,String strDelivDateTo,
                                           String strOrderFrom,String strOrderTo,String strCaseUPCFrom,String strCaseUPCTo,
                                           String strTruckNum,String strFetchSize,String strPlanner){
    return OrderSearchURL+"/"+convertString2URLFormat(strOrderType)+"/"+strStatus+//temp"/"+strSource+
        "/"+strDestination+"/"+convertStringToDateFormat(strDelivDateFrom)+"/"+convertStringToDateFormat(strDelivDateTo)+
           "/"+strOrderFrom+"/"+strOrderTo+"/"+strCaseUPCFrom+"/"+
        strCaseUPCTo+"/"+strTruckNum+"/"+strFetchSize+"/"+strPlanner;
    }
    

    public static String convertString2URLFormat(String strInput) {
        String strOutput = "";
        for (int i = 0; i < strInput.length(); i++) {
            char c = strInput.charAt(i);
            if (32 == (int) c) {
                strOutput = strOutput + "%20";
            } else {
                strOutput = strOutput + c;
            }
        }
        return strOutput;
    }
    
    public static String convertStringToDateFormat(String strInput){
        String strRet = "-999";
        strInput = strInput.substring(0, strInput.indexOf("T"));
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        try{
            Date d1 = df.parse(strInput);
            df = new SimpleDateFormat("dd-MMM-YYYY");
            strRet = df.format(d1);
            
        }catch(Exception e){
            System.out.println(""+e.getMessage());
        }
        return strRet;
    }
    
//amx-panelPage-header-overflowIcon
}
