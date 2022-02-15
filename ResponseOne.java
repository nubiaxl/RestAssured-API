package doJob;



import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


import java.io.IOException;


import files.resources;
import files.payLoad;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class ResponseOne
{
	@Test
  public void f() throws NoClassDefFoundError, IOException {
//	public static void main(String[] args ) throws IOException {
	  
			
			
	        payLoad pl = new payLoad();
	        resources rsr = new resources();
	        pl.setSendBody();
			RestAssured.baseURI= rsr.getData().getProperty("HOST");
			

			Response res = given().

			queryParam("key",rsr.getData().getProperty("KEY")).
	
			body(pl.getSendBody(rsr.getData().getProperty("XMLPATH"))).

			when().

			post(rsr.targetAddData()).

			then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().

			body("status",equalTo("OK")).

			

			extract().response();
			String responseString=res.asString();
			System.out.println(responseString);
			JsonPath js = new JsonPath(responseString);
			String result = js.get(rsr.getData().getProperty("RETRIEVE"));
			System.out.println("result=" + result);
			
			// send place to delete request
			RestAssured.baseURI= rsr.getData().getProperty("HOST");
			pl.setResultBody(result);
			Response res2 = given().queryParam("key", rsr.getData().getProperty("KEY")).
					
					body(pl.getResultBody()).

					when().

					post(rsr.resultDeleteData()).
					then().assertThat().statusCode(200).
					extract().response();
					System.out.println("gothere:  "+ res2);
					String responseReply = res2.asString();
					JsonPath sj = new JsonPath(responseReply);
					result = sj.get(rsr.getData().getProperty("STATUS"));
					
					
					System.out.println(result);
			}
			
	  
	  
	  
	  
	  
  }
