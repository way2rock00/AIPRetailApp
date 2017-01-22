package deloitte.retail.mobile.utility;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;

public class RestURIs {
    public RestURIs() {
        super();
    }

    private static String PO_SUMMARY = "/XxRpmGetPoSummary/GetPoSummary_RS";//http://app101111.glbsnet.com/XxRpmGetPoSummary/GetPoSummary_RS/-999/305
    private static String LoginURL = "/XxRpmValidateUser/ValidateUser_RS";
    
    
    public static String getLoginURL(String userName,String password,String deviceId,
                                     String loginType,String appId){
        return LoginURL+"/"+userName+"/"+password+"/"+deviceId+"/"+loginType+"/"+appId;
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
    

}
