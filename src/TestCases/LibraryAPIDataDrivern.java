package TestCases;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
public class LibraryAPIDataDrivern {
@Test
public void addBook() throws IOException {
	RestAssured.baseURI="http://216.10.245.166";
	DDTTest data= new DDTTest();
	ArrayList<Object> datalist= data.getData("AddBook");
	HashMap< String, Object> map = new HashMap<>();
	map.put("name", datalist.get(1));
	map.put("isbn", datalist.get(2));
	map.put("aisle", datalist.get(3));
	map.put("author", datalist.get(4));

	
	String AddBookResponse= given().header("Content-Type","application/json")
			.body(map)
			.log().all().when().post("/Library/Addbook.php").then()
			.assertThat().statusCode(200)
			.log().all().extract().response().asString();
	JsonPath js = new JsonPath(AddBookResponse);
	String bookid= js.get("ID");
	System.out.println(bookid);
	
	// Delete Book
	HashMap< String, Object> map1 = new HashMap<>();
	map1.put("ID", bookid);
	String DeleteBookResponse= given().header("Content-Type","application/json")
			.body(map1)
			.log().all().when().delete("/Library/DeleteBook.php").then()
			.assertThat().statusCode(200).body("msg",equalTo("book is successfully deleted"))
			.log().all().extract().response().asString();

}

}
