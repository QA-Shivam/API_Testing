import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import groovy.util.logging.Log;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payload.dynamicTestData;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestLibraryAPI extends dynamicTestData {
	@Test(enabled = false)
	public void addBookstatic() throws IOException {
		String jsonData = new String(Files.readAllBytes(Paths.get("src/payload/testData.json")));
		JSONObject jsonObject = new JSONObject(jsonData);
		// For Add Books (POST Request)
		JSONObject AddBooks = jsonObject.getJSONObject("add_book");

		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json").body(AddBooks.toString())
				.when().post("Library/Addbook.php").then().log().all().assertThat().statusCode(200)
				.body("Msg", equalTo("successfully added")).extract().asString();

	}

	@Test(dataProvider ="addbooks")
	public void addBookDynamic(String isbn, String aisle) throws IOException {
		// For Add Books (POST Request)
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json").body(addbook(isbn,aisle))
				.when().post("Library/Addbook.php").then().log().all().assertThat().statusCode(200)
				.body("Msg", equalTo("successfully added")).extract().asString();
		JsonPath js= new JsonPath(response);
		String bookid= js.get("ID");
		System.out.println(bookid);
		given().log().all().header("Content-Type","application/json").body(deletebook(bookid)).when().post("Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200).body("msg",equalTo("book is successfully deleted"));
	}
	
	@DataProvider(name="addbooks")
	public Object[][] getdata() {
		return new Object[][] {{"abxss","2001"},{"ppand","6382"},{"cbaps","7482"}};
	}
	
}
