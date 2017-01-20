package com.nasolution.com.nasolution.WebServices;

import com.nasolution.com.nasolution.Singleton.URLInstance;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class InsertDataWebservice {

    private static String NAMESPACE = URLInstance.getNameSpace();
    private static String URL = URLInstance.getUrl();
    private static String SOAP_ACTION = URLInstance.getSOAP_ACTION();

    public static String CreateLogin(String email, String pasword, String webMethName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);// Create request
        // Property which holds input parameters
        PropertyInfo celsiusPI = new PropertyInfo();        // Set Name

        celsiusPI.setName("email");      // Set Value
        celsiusPI.setValue(email);       // Set dataType
        celsiusPI.setType(String.class);     // Add the property to request object
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("pasword");
        celsiusPI.setValue(pasword);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }


    public static String CreateUser(String webMethName, String name, String address, String email, String worksfor, String mobile, String empcode, String username, String password, String profilephoto, String idproofphoto, String userrole, String dob) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethName);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("name");
        celsiusPI.setValue(name);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("address");
        celsiusPI.setValue(address);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("email");
        celsiusPI.setValue(email);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("workfor");
        celsiusPI.setValue(worksfor);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("mobile");
        celsiusPI.setValue(mobile);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("empcode");
        celsiusPI.setValue(empcode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("username");
        celsiusPI.setValue(username);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("password");
        celsiusPI.setValue(password);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("photo");
        celsiusPI.setValue(profilephoto);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("idproof");
        celsiusPI.setValue(idproofphoto);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("roleid");
        celsiusPI.setValue(userrole);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("dob");
        celsiusPI.setValue(dob);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String AddState(String addStateMethod, String saveNewStateName) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, addStateMethod);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("stateName");
        celsiusPI.setValue(saveNewStateName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        /*celsiusPI=new PropertyInfo();
        celsiusPI.setName("state_MasterId");
        celsiusPI.setValue(saveNewStateMasterid);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);*/

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + addStateMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }


    public static String AddDistrict(String addDistrictMethodName, String districtName, int stateId) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        SoapObject request = new SoapObject(NAMESPACE, addDistrictMethodName);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("districtName");
        celsiusPI.setValue(districtName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("stateId");
        celsiusPI.setValue(stateId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + addDistrictMethodName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }

    public static String AddTaluka(String addDistrictMethodName, String talukaName, int districtId) {

        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, addDistrictMethodName);// Create request
        // Property which holds input parameters
        PropertyInfo celsiusPI = new PropertyInfo();        // Set Name

        celsiusPI.setName("talukaname");      // Set Value
        celsiusPI.setValue(talukaName);       // Set dataType
        celsiusPI.setType(String.class);     // Add the property to request object
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("districtid");
        celsiusPI.setValue(districtId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + addDistrictMethodName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }

    public static String CreateCompany(String addCompanyMethodName, String companyTitle, String address, String emailId, String landmark, String city, int companyDistrictID,  String companyStateName, String pincode) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, addCompanyMethodName);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("fullname");
        celsiusPI.setValue(companyTitle);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        /*celsiusPI=new PropertyInfo();
        celsiusPI.setName("fullname");
        celsiusPI.setValue(fullName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);*/

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("addres");
        celsiusPI.setValue(address);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("email");
        celsiusPI.setValue(emailId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("landmark");
        celsiusPI.setValue(landmark);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("city");
        celsiusPI.setValue(city);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("district");
        celsiusPI.setValue(companyDistrictID);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("state");
        celsiusPI.setValue(companyStateName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("pincode");
        celsiusPI.setValue(pincode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + addCompanyMethodName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }

    public static String InsertClient(String clientContactMethod, int clientId, String clientName, String clientMobile) {

        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, clientContactMethod);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("clientId");
        celsiusPI.setValue(clientId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("name");
        celsiusPI.setValue(clientName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("mobile");
        celsiusPI.setValue(clientMobile);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);   // Set output SOAP object

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + clientContactMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String InsertArchitect(String addArchitecWebMethod, String name, String email, String mobile, String professionType) {
        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, addArchitecWebMethod);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("name");
        celsiusPI.setValue(name);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("email");
        celsiusPI.setValue(email);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("mobile");
        celsiusPI.setValue(mobile);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("professiontype");
        celsiusPI.setValue(professionType);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);   // Set output SOAP object

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + addArchitecWebMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String InsertProject(String addProjectWebMethod, int companyId, String projectName, String projectDescription,
                                       int serviceTypeId, String oldSurvey, String newSurvey, int talukaId, int districtId,
                                       String fileNumber, String villageName, String projectArea, int userId, String multipleSelectedValue) {
        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, addProjectWebMethod);  // Create request

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
            androidHttpTransport.call(SOAP_ACTION + addProjectWebMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String InsertSubStage(String addSubStageMethod, int projectId, int stageId, String allSubStageName, String allSubStagePercent, int userId) {
        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, addSubStageMethod);  // Create request

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
            androidHttpTransport.call(SOAP_ACTION + addSubStageMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String InsertStage(String addStageMethod, int projectId,String allStageName, String allStagePercent, int clientId) {
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
        celsiusPI.setValue(clientId);
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

    public static String InsertProjectForStage(String addStageMethod, int projectId,int userId, int projectIdForStage) {
        String resTxt = null;
        PropertyInfo celsiusPI;

        SoapObject request = new SoapObject(NAMESPACE, addStageMethod);  // Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("projectMasterId");
        celsiusPI.setValue(projectId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("projectIdForStage");
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

    public static String InsertTaskDetail(String taskWebMethod, int projectId, String userId, String stageId, int addedBy, String date, String count) {
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

    public static String AddUser(String createUsersMethod, String savename, String saveaddress, String saveemail, String saveworksfor, String savemobileno, String saveempcode, String saveusername, String savepassword, String saveprofileImageName, String saveidProofImageName, String saveuserrole, String savedob) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, createUsersMethod);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("name");
        celsiusPI.setValue(savename);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("address");
        celsiusPI.setValue(saveaddress);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("email");
        celsiusPI.setValue(saveemail);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("workfor");
        celsiusPI.setValue(saveworksfor);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("mobile");
        celsiusPI.setValue(savemobileno);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("empcode");
        celsiusPI.setValue(saveempcode);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("username");
        celsiusPI.setValue(saveusername);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("password");
        celsiusPI.setValue(savepassword);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("photo");
        celsiusPI.setValue(saveprofileImageName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("idproof");
        celsiusPI.setValue(saveidProofImageName);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("roleid");
        celsiusPI.setValue(saveuserrole);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("dob");
        celsiusPI.setValue(savedob);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);
        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + createUsersMethod, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occured";
        }
        return resTxt;
    }

    public static String AssignTaskDetails(String saveMyTaskRemarkMethodName, int mytaskAssignMasterId, String myTaskstatus, String myTaskcount, String myTaskremark) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, saveMyTaskRemarkMethodName);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("TaskAssignMasterId");
        celsiusPI.setValue(mytaskAssignMasterId);
        celsiusPI.setType(int.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("count");
        celsiusPI.setValue(myTaskcount);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("status");
        celsiusPI.setValue(myTaskstatus);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        celsiusPI=new PropertyInfo();
        celsiusPI.setName("Remark");
        celsiusPI.setValue(myTaskremark);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);

        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try {
            androidHttpTransport.call(SOAP_ACTION + saveMyTaskRemarkMethodName, envelope); // Invole web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Error occurred";
        }
        return resTxt;

    }
}
