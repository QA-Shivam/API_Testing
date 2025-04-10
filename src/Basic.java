import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.testng.Assert;

public class Basic {
	public static void main(String[] args) throws IOException {
		String jsonData = new String(Files.readAllBytes(Paths.get("src/payload/testData.json")));
		JSONObject jsonObject= new JSONObject(jsonData);
		// For Add Place (POST Request)
		JSONObject AddPlace= jsonObject.getJSONObject("add_place");
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body(AddPlace.toString()).
		when().post("maps/api/place/add/json").
		then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
		.extract().response().asString();
		System.out.println("Response>> "+ response);
		JsonPath jspath= new JsonPath(response);
		String place_id= jspath.getString("place_id");
		String ref=jspath.getString("reference");
		System.out.println("place_id>> "+ place_id);
		System.out.println("reference>> "+ ref);
		
		String updateAddress= jsonObject.getString("updateAddress");
		// For Update Place (PUT Request)
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+place_id+"\",\r\n"
				+ "\"address\":\""+updateAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		String getresponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id",place_id).when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		JsonPath js= new JsonPath(getresponse);
		String actualaddress= js.getString("address");
		System.out.println("New Addres>>" + actualaddress);
		Assert.assertEquals(updateAddress, actualaddress);
	}
}
