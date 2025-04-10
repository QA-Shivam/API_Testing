import static io.restassured.RestAssured.*;

import java.awt.RenderingHints.Key;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;
public class OAuth2Test {
public static void main(String[] args) throws InterruptedException {
	// get code

	WebDriver driver = new ChromeDriver();
	driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyrsa");
	String code= driver.getCurrentUrl();
	driver.findElement(By.xpath("//input[@aria-label='Email or phone']")).sendKeys("shivamkumar16399@gmail.com");
	driver.findElement(By.xpath("//input[@aria-label='Email or phone']")).sendKeys(Keys.ENTER);
	driver.findElement(By.xpath("//input[@aria-label='Enter your password']")).sendKeys("Coder@99");
	driver.findElement(By.xpath("//input[@aria-label='Enter your password']")).sendKeys(Keys.ENTER);
	Thread.sleep(2000);
	String url= driver.getCurrentUrl();
	System.out.println(url);
//	
	
	//get access token
//	String response=given().queryParam("code", "")
//			.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
//			.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
//			.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
//			.queryParam("grant_type", "authorization_code")
//			.when().log().all().post("https://www.googleapis.com/oauth2/v4").then().log().all().extract().response().asString();
//	JsonPath js= new JsonPath(response);
//	String accessToken=js.getString("access_token");
//	
//	String getcourcedetails=given().queryParam("access_token","ya29.a0AZYkNZgGvH7TSyTgw3qkeyyii0KhFSnqUKHKemgaWMeXDH6Rp9DXZ1GwMrtqfnfafUWZY5BQbE4Ss5Or2XFE0-KHOE57EcadNQxDHIGfkZGJjVoMFz_hbFv8GCizTwylVogFuqQPKJnnYGC1XkFG2Sl905Q2rTftxKVy4XvLWAaCgYKAVwSARASFQHGX2Mi0y_jdWwQ4Tp-634zcOPQLQ0177").when().log().all().get("https://rahulshettyacademy.com/getCourse.php").then().log().all().extract().response().asString();
//	System.out.println(getcourcedetails);
}
}
