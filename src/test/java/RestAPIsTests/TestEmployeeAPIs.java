package RestAPIsTests;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.testng.Assert.assertEquals;
//import java.util.Map;

/*This class is to test POST,GET and DELETE operations of Employee APIs
 * Base URL: http://dummy.restapiexample.com
 * POST URL: http://dummy.restapiexample.com/api/v1/create
 * GET URL: http://dummy.restapiexample.com/api/v1/employee/"+employeeID
 * DELETE URL: http://dummy.restapiexample.com/api/v1/delete/"+employeeID
 */
public class TestEmployeeAPIs {	
	
	public static void main(String[] args) throws Exception
	{
		//Declaring variables
		RequestSpecification employeeRequest= RestAssured.given();
		Response getResPonse;
		Response postReponse;
		Response delResPonse;
		String employeeID="";		
		//Provide URLs to POST, GET and DELETE operations
		String createEmployeeURL ="http://dummy.restapiexample.com/api/v1/create";
		//Generating sample body content
		String requestBody = "{\"employee_name\": \"Anugam\",\"employee_salary\": 85600,\"employee_age\": 48,\"profile_image\": \"\"}";	
		employeeRequest.header("Content-Type","application/json");
		employeeRequest.body(requestBody);
		//Posting the request
		try
		{
			postReponse= employeeRequest.post(createEmployeeURL);
			//Verifying the response status code.It should be 200 for successful post
			int createResponseCode = postReponse.getStatusCode();
			assertEquals(createResponseCode, 200);		
			JsonPath jp1 = new JsonPath(postReponse.asString());		
			
			//Logic to get Employee ID which is created above
			/*
			Map employeeData = ((Map)jp1.get("data"));
	        // iterating employeeData Map 
	        Iterator<Map.Entry> itr1 = employeeData.entrySet().iterator(); 
	        while (itr1.hasNext()) { 
	            Map.Entry pair = itr1.next(); 
	            if(pair.getKey().toString().equals("id"))
	            {
	            	employeeID = pair.getValue().toString();
	            	break;
	            }
	        } 
	        */
			
			//Logic: To get the Employee ID which is created above with POST method using string methods		
			int idIndex = jp1.get("data").toString().indexOf("id");
			int strlen = jp1.get("data").toString().length();
			employeeID = jp1.get("data").toString().substring(idIndex+3,strlen-1);
			
			//Testing the GET method with given employeeID(Which is created in above post method)
			String getSpecificEmployeesURL ="http://dummy.restapiexample.com/api/v1/employee/"+employeeID;		
			getResPonse= employeeRequest.get(getSpecificEmployeesURL);	
			//Verifying the response status code.It should be 200 for successful for get operation
			assertEquals(getResPonse.getStatusCode(),200);
			
			//Testing the DELETE method with given employeeID(Which is created in above post method)
			String deleteSpecificEmployeesURL ="http://dummy.restapiexample.com/api/v1/delete/"+employeeID;		
			delResPonse= employeeRequest.delete(deleteSpecificEmployeesURL);	
			//Verifying the response status code.It should be 200 for successful for delete operation
			assertEquals(delResPonse.getStatusCode(),200);
			//Verifying the response message for DELETE operation
			assertEquals(delResPonse.jsonPath().get("message"),"Successfully! Record has been deleted");	
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception: "+ e.getMessage());
		}			
    }
}
