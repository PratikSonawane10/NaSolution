package com.nasolution.com.nasolution.WebServices;

import com.google.common.base.Strings;
import com.nasolution.com.nasolution.Singleton.URLInstance;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class DeleteDataWebService {

    private static String NAMESPACE = URLInstance.getNameSpace();
    private static String URL = URLInstance.getUrl();
    private static String SOAP_ACTION = URLInstance.getSOAP_ACTION();

    public static String DeleteState(String deleteStateMethodName, int stateId) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, deleteStateMethodName);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("state_MasterId");
        celsiusPI.setValue(stateId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);    // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);  // Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + deleteStateMethodName, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String DeleteDistrict(String deleteDistrictMethodName, int districtId) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, deleteDistrictMethodName);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("district_MasterId");
        celsiusPI.setValue(districtId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);    // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);  // Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + deleteDistrictMethodName, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String DeleteTaluka(String deleteTalukaMethodName, int talukaId) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, deleteTalukaMethodName);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("talukaid");
        celsiusPI.setValue(talukaId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);    // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);  // Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + deleteTalukaMethodName, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String DeleteCompany(String deleteCompanyWebMethod, int saveDeleteCompanyId) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, deleteCompanyWebMethod);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("clientId");
        celsiusPI.setValue(saveDeleteCompanyId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);    // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);  // Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + deleteCompanyWebMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String DeleteAchitecture(String deleteArchitectureMethod, int architecId) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, deleteArchitectureMethod);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("architectureMasterId");
        celsiusPI.setValue(architecId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);    // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);  // Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + deleteArchitectureMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String DeleteProject(String delteProjectWebMethod, int saveDeleteProjectId) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, delteProjectWebMethod);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("projectMasterId");
        celsiusPI.setValue(saveDeleteProjectId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);    // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);  // Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + delteProjectWebMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String DeleteSubStage(String deleteSubStageMethodName, int subStageId) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, deleteSubStageMethodName);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("subStageId");
        celsiusPI.setValue(subStageId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);    // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);  // Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + deleteSubStageMethodName, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String DeleteTask(String taskWebMethod, String taskId, String countName) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, taskWebMethod);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("TaskAssignMasterId");
        celsiusPI.setValue(taskId);
        celsiusPI.setType(Strings.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("count");
        celsiusPI.setValue(countName);
        celsiusPI.setType(Strings.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);    // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);  // Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + taskWebMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }
}
