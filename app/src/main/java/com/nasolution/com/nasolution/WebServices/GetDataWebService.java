package com.nasolution.com.nasolution.WebServices;

import com.nasolution.com.nasolution.Singleton.URLInstance;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class GetDataWebService {

    private static String NAMESPACE = URLInstance.getNameSpace();
    private static String URL = URLInstance.getUrl();
    private static String SOAP_ACTION = URLInstance.getSOAP_ACTION();


    public static String GetUserRole(String userRoleMethod) {

        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, userRoleMethod);// Create request
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);// Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + userRoleMethod, envelope);// Invoke web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();// Get the response
            resTxt = response.toString();// Assign it to fahren static variable
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }

    public static String GetEmpCode(String webMethName) {

        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, webMethName);// Create request
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);// Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + webMethName, envelope);// Invoke web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse(); // Get the response
            resTxt = response.toString();  // Assign it to fahren static variable
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }

    public static String GetAllState(String getStateMethodName) {

        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, getStateMethodName);// Create request
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);// Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + getStateMethodName, envelope);// Invoke web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();// Get the response
            resTxt = response.toString();// Assign it to fahren static variable
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }

    public static String GetAllDistrict(String districtMethodName, int stateID) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, districtMethodName);// Create request
        celsiusPI = new PropertyInfo();
        celsiusPI.setName("stateid");
        celsiusPI.setValue(stateID);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + districtMethodName, envelope);// Invoke web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();// Get the response
            resTxt = response.toString();// Assign it to fahren static variable
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }

    public static String GetStateForDistrict(String districtStateMethodName) {

        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, districtStateMethodName);// Create request
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + districtStateMethodName, envelope);// Invoke web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();// Get the response
            resTxt = response.toString();// Assign it to fahren static variable
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }

    public static String GetDistrictForTaluka(String methodName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, methodName);// Create request
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + methodName, envelope);// Invoke web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();// Get the response
            resTxt = response.toString();// Assign it to fahren static variable
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }

    public static String GetAllTaluka(String talukaMethodName, int districtId) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, talukaMethodName);// Create request
        celsiusPI = new PropertyInfo();
        celsiusPI.setName("districtid");
        celsiusPI.setValue(districtId);
        celsiusPI.setType(String.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + talukaMethodName, envelope);// Invoke web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();// Get the response
            resTxt = response.toString();// Assign it to fahren static variable
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }

    public static String GetClients(String clientMethodName, int tempCompanyId) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, clientMethodName);// Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("clientId");
        celsiusPI.setValue(tempCompanyId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + clientMethodName, envelope);// Invoke web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();// Get the response
            resTxt = response.toString();// Assign it to fahren static variable
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }
    public static String GetAllClients(String allClientMethodName) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, allClientMethodName);// Create request

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + allClientMethodName, envelope);// Invoke web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();// Get the response
            resTxt = response.toString();// Assign it to fahren static variable
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }

    public static String GetClientContact(String contactWebMethod, int saveClientID) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, contactWebMethod);// Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("clientId");
        celsiusPI.setValue(saveClientID);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + contactWebMethod, envelope);// Invoke web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();// Get the response
            resTxt = response.toString();// Assign it to fahren static variable
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String GetProfessionType(String professionWebMethod) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, professionWebMethod);// Create request

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + professionWebMethod, envelope);// Invoke web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();// Get the response
            resTxt = response.toString();// Assign it to fahren static variable
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String GetArchitectureList(String getArchitecWebMethod) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, getArchitecWebMethod);// Create request

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + getArchitecWebMethod, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String GetArchitectureDetails(String architecDetailWebMethod, int saveArchitechId) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, architecDetailWebMethod);// Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("architectureMasterId");
        celsiusPI.setValue(saveArchitechId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + architecDetailWebMethod, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String GetAllProjectList(String projectWebMethod) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, projectWebMethod);// Create request

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + projectWebMethod, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String GetAllServiceType(String serviceWebMethod) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, serviceWebMethod);// Create request

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + serviceWebMethod, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String GetProjectById(String projectWebMethod, int updateProjectId) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, projectWebMethod);// Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("projectMasterId");
        celsiusPI.setValue(updateProjectId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + projectWebMethod, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String GetStageById(String stageWebMethod, int projectId) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, stageWebMethod);// Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("projectMasterId");
        celsiusPI.setValue(projectId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + stageWebMethod, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String GetSubStageById(String subStageWebMethod, int saveStageId) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, subStageWebMethod);// Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("stageMasterId");
        celsiusPI.setValue(saveStageId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + subStageWebMethod, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String GetAllTask(String taskDetailMethodName, int saveProjectId) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, taskDetailMethodName);// Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProjectId");
        celsiusPI.setValue(saveProjectId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + taskDetailMethodName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String GetAllUser(String getUserWebMethod) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, getUserWebMethod);// Create request

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + getUserWebMethod, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String GetTaskDetail(String taskDetailMethodName, int saveProjectId) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, taskDetailMethodName);// Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProjectId");
        celsiusPI.setValue(saveProjectId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + taskDetailMethodName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String FetchRoles(String userName, String userPassword, String userRoleWebMethod) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, userRoleWebMethod);// Create request
        // Property which holds input parameters
        PropertyInfo celsiusPI = new PropertyInfo();        // Set Name

        celsiusPI.setName("email");      // Set Value
        celsiusPI.setValue(userName);       // Set dataType
        celsiusPI.setType(String.class);     // Add the property to request object
        request.addProperty(celsiusPI);

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("pasword");
        celsiusPI.setValue(userPassword);
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
            androidHttpTransport.call(SOAP_ACTION + userRoleWebMethod, envelope);
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

    public static String GetAllArchitecture(String architectureMethodName) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, architectureMethodName);// Create request

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + architectureMethodName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }


    public static String GetAllDistrictForUpdate(String districtMethodName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, districtMethodName);// Create request
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);// Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + districtMethodName, envelope);// Invoke web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();// Get the response
            resTxt = response.toString();// Assign it to fahren static variable
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }


    public static String GetAllTalukaForUpdate(String talukaMethodName) {
        String resTxt = null;

        SoapObject request = new SoapObject(NAMESPACE, talukaMethodName);// Create request
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);// Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            androidHttpTransport.call(SOAP_ACTION + talukaMethodName, envelope);// Invoke web service
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();// Get the response
            resTxt = response.toString();// Assign it to fahren static variable
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Network Found";
        }
        return resTxt;
    }

    public static String GetStageForUpdate(String StageWebMethod, int projectId) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, StageWebMethod);// Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProjectId");
        celsiusPI.setValue(projectId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + StageWebMethod, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String EditArchitectureForProject(String prevArchitectureWebMethod, int updateProjectId) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, prevArchitectureWebMethod);// Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("ProjectId");
        celsiusPI.setValue(updateProjectId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + prevArchitectureWebMethod, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String GetProjectDetails(String projectWebMethod, int companyId) {
        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, projectWebMethod);// Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("clientId");
        celsiusPI.setValue(companyId);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + projectWebMethod, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "No Record Found";
        }
        return resTxt;
    }

    public static String MyTaskDetails(String webMethodName, int userID) {

        String resTxt = null;
        PropertyInfo celsiusPI;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, webMethodName);// Create request

        celsiusPI = new PropertyInfo();
        celsiusPI.setName("UserId");
        celsiusPI.setValue(userID);
        celsiusPI.setType(Integer.class);
        request.addProperty(celsiusPI);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // Create envelope
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request); // Set output SOAP object

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);// Create HTTP call object
        try {
            // Invoke web service
            androidHttpTransport.call(SOAP_ACTION + webMethodName, envelope);
            // Get the response
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to fahren static variable
            resTxt = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            resTxt = "Task Details Not Found";
        }
        return resTxt;

    }
}
