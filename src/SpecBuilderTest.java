import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.location;

public class SpecBuilderTest {
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
		RequestSpecification req= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		ResponseSpecification res= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification addplace=given().spec(req).body(ap).log().all();
		Response addplaceresponse=addplace.when().post("/maps/api/place/add/json").then().log().all().spec(res).extract().response();
		String resp= addplaceresponse.asString();
		System.out.println("Response "+resp);
	}

}
