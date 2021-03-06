package deloitte.retail.mobile.beans;

import deloitte.retail.mobile.utility.RestURIs;

import deloitte.retail.mobile.utility.ServiceManager;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.json.JSONObject;

public class BarcodeBean {
    private String barcodeError = "";
    private String barcodeResult = "";
    private String barcodeFormat = "";
    private String barcodeCancelled = "";

    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport (this);

    public BarcodeBean () {
        super ();
    }

    public void scanBarcode (ActionEvent event) {
        // Our AMX page includes a small JavaScript function which wraps the Cordova
        // barcode scanning function in a manner that makes it more suitable for invocation
        // from Java bean code. This is the function we are invoking below:
        System.out.println("Inside barcode bean");
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureId(),
                                                                  "scanBarcodeFromJavaBean",
                                                                  new Object[] { });
 
        
    }

    public void setBarcodeFormat(String barcodeFormat) {
        String oldBarcodeFormat = this.barcodeFormat;
        this.barcodeFormat = barcodeFormat;
        propertyChangeSupport.firePropertyChange("barcodeFormat", oldBarcodeFormat, barcodeFormat);
    }

    public String getBarcodeFormat() {
        return barcodeFormat;
    }

    public void setBarcodeCancelled(String barcodeCancelled) {
        String oldBarcodeCancelled = this.barcodeCancelled;
        this.barcodeCancelled = barcodeCancelled;
        propertyChangeSupport.firePropertyChange("barcodeCancelled", oldBarcodeCancelled, barcodeCancelled);
    }

    public String getBarcodeCancelled() {
        return barcodeCancelled;
    }

    public void setBarcodeResult (String barcodeResult) {
        String oldBarcodeResult = this.barcodeResult;
        this.barcodeResult = barcodeResult;
        propertyChangeSupport.firePropertyChange ("barcodeResult", oldBarcodeResult, barcodeResult);
        findDescription();
    }

    public String getBarcodeResult () {
        return barcodeResult;
    }

    public void setBarcodeError (String barcodeError) {
        String oldBarcodeError = this.barcodeError;
        this.barcodeError = barcodeError;
        propertyChangeSupport.firePropertyChange ("barcodeError", oldBarcodeError, barcodeError);
    }

    public String getBarcodeError () {
        return barcodeError;
    }

    public void addPropertyChangeListener (PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener (l);
    }

    public void removePropertyChangeListener (PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener (l);
    }

    public void findDescription() {
        String strDebug = "S:";
        String barcodeSuccess = (String)AdfmfJavaUtilities.getELValue("#{pageFlowScope.BarcodeBean.barcodeError}");
        String strItemNumber = (String)AdfmfJavaUtilities.getELValue("#{pageFlowScope.BarcodeBean.barcodeResult}");
        strDebug=strDebug+"~barcodeSuccess:"+barcodeSuccess+"~+strItemNumber:"+strItemNumber;
        if("".equals(barcodeSuccess)&&!"".equals(strItemNumber)){
            String url = RestURIs.getScanItemDetailsURL(strItemNumber);
            strDebug = strDebug+"~"+url;
            ServiceManager serviceManager = new ServiceManager();
            String jsonArrayAsString = serviceManager.invokeREAD(url); 
            strDebug = strDebug+"~"+jsonArrayAsString;
            try{
                JSONObject jsonObject = new JSONObject(jsonArrayAsString);
                JSONObject parent = jsonObject.getJSONObject("OutputParameters");
                strDebug = strDebug+"~"+"1";
                String returnStatus = null;
                if (parent.getString("X_RETURN_STATUS") != null)
                    returnStatus = parent.getString("X_RETURN_STATUS");
                strDebug = strDebug+"~"+"2:"+returnStatus;
                
                String returnMsg = null;
                if (parent.getString("X_RETURN_MSG") != null)
                    returnMsg = parent.getString("X_RETURN_MSG");
                strDebug = strDebug+"~"+"3:"+returnMsg;
                
                String desc = null;
                if (parent.getString("P_DESC") != null)
                    desc = parent.getString("P_DESC");

                strDebug = strDebug+"~"+"4:"+desc;
                
                
                String subClass = null;
                if (parent.getString("P_SUBCLASS") != null)
                    subClass = parent.getString("P_SUBCLASS");
                strDebug = strDebug+"~"+"5:"+subClass;
                
                
                AdfmfJavaUtilities.setELValue("#{pageFlowScope.itemNumber}",strItemNumber);
                AdfmfJavaUtilities.setELValue("#{pageFlowScope.itemDesc}",desc );
                AdfmfJavaUtilities.setELValue("#{pageFlowScope.itemSubClass}",subClass);

               
            }
            catch(Exception e){
                strDebug = strDebug +":Error:"+e.getMessage();
            }
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.strDebug}",strDebug);
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.BarcodeBean.barcodeError}",strDebug);
        }
    }
}
