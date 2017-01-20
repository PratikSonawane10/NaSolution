package com.nasolution.com.nasolution.WebServices;

import android.widget.EditText;

import com.nasolution.com.nasolution.Singleton.URLInstance;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class UpdateDataWebservice {

    private static String NAMESPACE = URLInstance.getNameSpace();
    private static String URL = URLInstance.getUrl();
    private static String SOAP_ACTION = URLInstance.getSOAP_ACTION();

    public static String UpdateState(String updateStateMethod, String stateName, int stateMasterId) {

        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, updateStateMethod);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("stateName");
        celsiusPI.setValue(stateName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("state_MasterId");
        celsiusPI.setValue(stateMasterId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);   // Set output SOAP object

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + updateStateMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String UpdateDistrict(String updateDistrictMethodName, int stateId, int districtId, String updateDistrictName) {

        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, updateDistrictMethodName);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("stateId");
        celsiusPI.setValue(stateId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("districtName");
        celsiusPI.setValue(updateDistrictName);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("district_MasterId");
        celsiusPI.setValue(districtId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);   // Set output SOAP object

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + updateDistrictMethodName, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String UpdateTaluka(String updateTalukaMethodName, String updateTalukaName, int talukaId, int districtId) {
        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, updateTalukaMethodName);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("talukaid");
        celsiusPI.setValue(talukaId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("talukaname");
        celsiusPI.setValue(updateTalukaName);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("districtid");
        celsiusPI.setValue(districtId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);   // Set output SOAP object

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + updateTalukaMethodName, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String UpdateCompanytDetail(String updateCompanyWebMethod, int updateCompanyId, String updateCompanyName, String updateCompanyAddres,
                                              String updateEmail, String updateCompanyLandmark, String updateCompanyCity,
                                              int saveUpdateCompanyDistrictId, String saveUpdateCompanyStateName, String updateCompanyPincode) {
        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, updateCompanyWebMethod);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("clientId");
        celsiusPI.setValue(updateCompanyId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("title");
        celsiusPI.setValue(updateCompanyName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("addres");
        celsiusPI.setValue(updateCompanyAddres);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("email");
        celsiusPI.setValue(updateEmail);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("landmark");
        celsiusPI.setValue(updateCompanyLandmark);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("city");
        celsiusPI.setValue(updateCompanyCity);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("district");
        celsiusPI.setValue(saveUpdateCompanyDistrictId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("state");
        celsiusPI.setValue(saveUpdateCompanyStateName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("pincode");
        celsiusPI.setValue(updateCompanyPincode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);   // Set output SOAP object

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + updateCompanyWebMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String UpdateClientContact(String updateContactMethod, int clientId, String updateName, String updateMobile) {

        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, updateContactMethod);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("clientId");
        celsiusPI.setValue(clientId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("name");
        celsiusPI.setValue(updateName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("mobile");
        celsiusPI.setValue(updateMobile);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);   // Set output SOAP object

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + updateContactMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String UpdateArchitecture(String updateArchitecWebMethod, int architecId, String architecName, String architecEmail, String architecMobile, String architecProfessionName) {
        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, updateArchitecWebMethod);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("architectureMasterId");
        celsiusPI.setValue(architecId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("name");
        celsiusPI.setValue(architecName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("email");
        celsiusPI.setValue(architecEmail);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("mobile");
        celsiusPI.setValue(architecMobile);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI=new PropertyInfo();
        celsiusPI.setName("professiontype");
        celsiusPI.setValue(architecProfessionName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);   // Set output SOAP object

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + updateArchitecWebMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String UpdateSubStage(String updateSubStageMethod, int projectId, int stageId, String allSubStageName, String allSubStagePercent, int userId) {
        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, updateSubStageMethod);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("projectId");
        celsiusPI.setValue(projectId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("stageMasterId");
        celsiusPI.setValue(stageId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("title");
        celsiusPI.setValue(allSubStageName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("subSatgepercent");
        celsiusPI.setValue(allSubStagePercent);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);


        celsiusPI=new PropertyInfo();
        celsiusPI.setName("addedby");
        celsiusPI.setValue(userId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);   // Set output SOAP object

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + updateSubStageMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;

    }

    public static String UpdateTaskDetail(String taskWebMethod, int projectId, String userId, String stageId, int addedBy, String date, String count) {
        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, taskWebMethod);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProjectId");
        celsiusPI.setValue(projectId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("Username");
        celsiusPI.setValue(userId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("Stagename");
        celsiusPI.setValue(stageId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("addedby");
        celsiusPI.setValue(addedBy);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("submitonDate");
        celsiusPI.setValue(date);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("countName");
        celsiusPI.setValue(count);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);   // Set output SOAP object

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
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

    public static String UpdateStage(String addStageMethod, int projectId, String allStageName, String allStagePercent, int clientID) {
        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, addStageMethod);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("projectMasterId");
        celsiusPI.setValue(projectId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("title");
        celsiusPI.setValue(allStageName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("stagepercent");
        celsiusPI.setValue(allStagePercent);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("addedby");
        celsiusPI.setValue(clientID);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);   // Set output SOAP object

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + addStageMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String UpdateProjectForStage(String addStageMethod, int projectId,int userId, int projectIdForStage) {
        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, addStageMethod);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProjectMasterId");
        celsiusPI.setValue(projectId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProjectId");
        celsiusPI.setValue(projectIdForStage);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("addedby");
        celsiusPI.setValue(userId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);   // Set output SOAP object

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + addStageMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String updateProject(String updateProjectWebMethod, int projectId, int companyId, String projectName, String projectDescription, int serviceTypeId, String oldSurvey, String newSurvey, int talukaId, int districtId, String fileNumber, String villageName, String projectArea, int userId, String multipleSelectedValue) {
        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, updateProjectWebMethod);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("projectMasterId");
        celsiusPI.setValue(projectId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("clientId");
        celsiusPI.setValue(companyId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("title");
        celsiusPI.setValue(projectName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("description");
        celsiusPI.setValue(projectDescription);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("servicetype");
        celsiusPI.setValue(serviceTypeId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("oldSurveyNo");
        celsiusPI.setValue(oldSurvey);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("NewSurveyNo");
        celsiusPI.setValue(newSurvey);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("taluka");
        celsiusPI.setValue(talukaId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("district");
        celsiusPI.setValue(districtId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("fileNumber");
        celsiusPI.setValue(fileNumber);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("VillageName");
        celsiusPI.setValue(villageName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("ProjectArea");
        celsiusPI.setValue(projectArea);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("addedby");
        celsiusPI.setValue(userId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("architecture");
        celsiusPI.setValue(multipleSelectedValue);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);   // Set output SOAP object

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + updateProjectWebMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

}
