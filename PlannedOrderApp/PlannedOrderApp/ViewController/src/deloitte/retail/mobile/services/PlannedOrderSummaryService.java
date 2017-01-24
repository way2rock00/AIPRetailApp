package deloitte.retail.mobile.services;

import deloitte.retail.mobile.pojo.PlannedOrderSummaryInfo;

import deloitte.retail.mobile.utility.RestURIs;
import deloitte.retail.mobile.utility.ServiceManager;

import java.util.ArrayList;
import java.util.List;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.json.JSONArray;
import oracle.adfmf.json.JSONObject;

public class PlannedOrderSummaryService {
    public PlannedOrderSummaryService() {
        super();
    }
    
    private List<PlannedOrderSummaryInfo> plannedOrderSummaryList  = new ArrayList<PlannedOrderSummaryInfo>();


    public PlannedOrderSummaryInfo[] getPlannedOrderSummary() {
        PlannedOrderSummaryInfo[] plannedOrderArray = null;
        plannedOrderSummaryList = new ArrayList<PlannedOrderSummaryInfo>();
        ServiceManager serviceManager = new ServiceManager();
        String buyer = (String)AdfmfJavaUtilities.getELValue("#{applicationScope.loggedInBuyerNumber}");
        String strDebug = "plannedOrder:"+buyer;
        System.out.println("buyer");
        String url = RestURIs.getPlannedSummaryURL(buyer); 
        strDebug = strDebug +"::"+url;
        String jsonArrayAsString = serviceManager.invokeREAD(url);
        strDebug = strDebug + ":"+jsonArrayAsString;
        try {
            JSONObject jsonObject = new JSONObject(jsonArrayAsString);
            JSONObject parent = jsonObject.getJSONObject("P_PLANNED_ORD_SUMMARY_TAB");
            JSONArray nodeArray = parent.getJSONArray("P_PLANNED_ORD_SUMMARY_TAB_ITEM");

            int size = nodeArray.length();
            for (int i = 0; i < size; i++) {
                JSONObject temp = nodeArray.getJSONObject(i);

                String recordId = null;
                if (temp.getString("RECORD_ID") != null)
                    recordId = temp.getString("RECORD_ID");

                String status = null;
                if (temp.getString("STATUS") != null)
                    status = temp.getString("STATUS");

                String status_count = null;
                if (temp.getString("STATUS_COUNT") != null)
                    status_count = temp.getString("STATUS_COUNT");

                PlannedOrderSummaryInfo plannedOrderSummaryRec = new PlannedOrderSummaryInfo(recordId, status, status_count);

                plannedOrderSummaryList.add(plannedOrderSummaryRec);

                System.out.println("summary servie:" + status);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            strDebug = strDebug + ":Error:"+e.getMessage();
        }     
        plannedOrderArray = plannedOrderSummaryList.toArray(new PlannedOrderSummaryInfo[plannedOrderSummaryList.size()]);
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.strDebug}", strDebug);
        return plannedOrderArray;
    }
}
