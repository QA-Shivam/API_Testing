package TestCases;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.location;

public class SerializationTest {
	
@Test
public void addPlace() {
	AddPlace ap= new AddPlace();
	location l= new location();
	l.setLat(-38.383494);
	l.setLng(33.427362);
	ap.setLocation(l);
	ap.setAccuracy(50);
	ap.setName("Frontline House");
	ap.setPhone_number("(+91) 983 893 3937");
	ap.setAddress("28, side layout, cohen 09");
	List<String>types= new ArrayList<>();
	types.add("shoe park");
	types.add("shop park");
	ap.setTypes(types);
	ap.setWebsite("http://google.com");
	ap.setLanguage("French-Germny");
	
	RestAssured.baseURI="https://rahulshettyacademy.com";
	Response addplace=given().queryParam("key", "qaclick123").body(ap).log().all().when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract().response();
	String resp= addplace.asString();
	System.out.println("Response "+resp);
}

}
