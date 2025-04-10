import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class TestJiraAPI {
	@Test
	public void createIssue() {
		RestAssured.baseURI = "https://shivamkumar16399.atlassian.net";
		String response = given().header("Content-Type", "application/json").header("Authorization",
				"Basic c2hpdmFta3VtYXIxNjM5OUBnbWFpbC5jb206QVRBVFQzeEZmR0YwY1BlWGNIdTdaS2hUM0VBOUNYdTViRFdnZWM1MGFXM1NQbGZvdHl0VENuY2t5UXU5Q2dNMmVpQjlDSDRPSk9WcVk3eHp6NHluNF9Ha3hLS0V0UERMR0w0R0M3VG9UUUF0MjNTWjFEbDV6OWxWUHdZWmt2Q0dDcGc1aktHTkE4V0VIUGQ3NkszbGJZemtZblhrcWxmT3pVc2thMGc4aWNFUVVfVmh4RUYtRUdVPTM0QkI1MzA2")
				.body("{\r\n" + "    \"fields\": {\r\n" + "       \"project\":\r\n" + "       {\r\n"
						+ "          \"key\": \"SCRUM\"\r\n" + "       },\r\n" + "       \"summary\": \"Radio buttons are not working \",\r\n"
						+ "       \"issuetype\": {\r\n" + "          \"name\": \"Bug\"\r\n" + "       }\r\n"
						+ "   }\r\n" + "}")
				.log().all().post("rest/api/3/issue").then().log().all().assertThat().statusCode(201).extract()
				.asString();

		JsonPath js = new JsonPath(response);
		int jiraid = js.getInt("id");
		
		//Add Attachment
		
		given().log().all().pathParam("key", jiraid)
		.header("X-Atlassian-Token","no-check").header("Authorization","Basic c2hpdmFta3VtYXIxNjM5OUBnbWFpbC5jb206QVRBVFQzeEZmR0YwY1BlWGNIdTdaS2hUM0VBOUNYdTViRFdnZWM1MGFXM1NQbGZvdHl0VENuY2t5UXU5Q2dNMmVpQjlDSDRPSk9WcVk3eHp6NHluNF9Ha3hLS0V0UERMR0w0R0M3VG9UUUF0MjNTWjFEbDV6OWxWUHdZWmt2Q0dDcGc1aktHTkE4V0VIUGQ3NkszbGJZemtZblhrcWxmT3pVc2thMGc4aWNFUVVfVmh4RUYtRUdVPTM0QkI1MzA2")
		.multiPart("file",new File("C:\\Users\\satya\\OneDrive\\Pictures\\Screenshots\\Screenshot 2024-03-08 073927.png")).post("rest/api/3/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
	}
}
