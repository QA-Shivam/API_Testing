import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import pojo.getCourseDetails;

import static io.restassured.RestAssured.*;

public class OAuthTest {

	// note use extract or asString to get the response 
	@Test
	public void OAuthAPITest() {
		String response=given().formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust").when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		System.out.println(response);
		
		JsonPath jsonPath= new JsonPath(response);
		String access_token=jsonPath.getString("access_token");
		
		getCourseDetails response1=given().queryParam("access_token", access_token).log().all().when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.then().log().all().assertThat().statusCode(401).extract().as(getCourseDetails.class);
		System.out.println(response1.getUrl());
		System.out.println(response1.getServices());
		System.out.println(response1.getExpertise());
		response1.getCourses().getMobile().forEach(e-> {
			if(e.getCourseTitle().equalsIgnoreCase("Appium-Mobile Automation using Java")) {
				System.out.println(e.getCourseTitle()+" : "+e.getPrice());
			}
		});
		//Get all web automation course
		response1.getCourses().getWebAutomation().forEach(e-> System.out.println(e.getCourseTitle()+" : "+e.getPrice()));
	}
}
