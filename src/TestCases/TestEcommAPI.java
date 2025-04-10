package TestCases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.AddProductResponse;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetails;
import pojo.PlaceOrderRequest;

public class TestEcommAPI {
	public static void main(String[] args) {

		// Login Request
		RequestSpecification loginrequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		LoginRequest l = new LoginRequest();
		l.setUserEmail("shivamkumar16399@gmail.com");
		l.setUserPassword("Coder@99");
		LoginResponse loginresponse = given().spec(loginrequest).body(l).log().all().when().post("/api/ecom/auth/login")
				.then().log().all().extract().response().as(LoginResponse.class);

		System.out.println(loginresponse.getUserId());
		System.out.println(loginresponse.getToken());
		String token = loginresponse.getToken();
		String userid = loginresponse.getUserId();

		// Add Product

		RequestSpecification addproductreqspec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		AddProductResponse addProductResponse = given().log().all().spec(addproductreqspec)
				.param("productName", "BATMAN").param("productAddedBy", userid).param("productCategory", "Marvels")
				.param("productSubCategory", "DC").param("productPrice", 2000)
				.param("productDescription", "Team Avangers").param("productFor", "Everyone")
				.multiPart("productImage", new File("C:\\Users\\satya\\OneDrive\\Pictures\\20240522_062757.jpg")).when()
				.post("/api/ecom/product/add-product").then().log().all().assertThat().statusCode(201)
				.body("message", equalTo("Product Added Successfully")).extract().response()
				.as(AddProductResponse.class);
		String prodcutid = addProductResponse.getProductId();

		// Place Order
		RequestSpecification placeorderreqspec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();

		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCountry("india");
		orderDetails.setProductOrderedId(prodcutid);
		List<OrderDetails> orderlist = new ArrayList<OrderDetails>();
		orderlist.add(orderDetails);
		PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
		placeOrderRequest.setOrders(orderlist);

		String placeOrderResponse = given().log().all().spec(placeorderreqspec).body(placeOrderRequest).when()
				.post("/api/ecom/order/create-order").then().log().all().assertThat().statusCode(201)
				.body("message", equalTo("Order Placed Successfully")).extract().response().asString();
		System.out.println(placeOrderResponse);
		JsonPath js = new JsonPath(placeOrderResponse);
		String orders = js.getString("orders[0]");
		System.out.println(orders);

		// Delete Order
		RequestSpecification deleteorderreqspec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();

		String deleteOrderResponse = given().log().all().spec(deleteorderreqspec).pathParam("orders", orders).when()
				.delete("/api/ecom/order/delete-order/{orders}").then().log().all().assertThat().statusCode(200)
				.body("message", equalTo("Orders Deleted Successfully")).extract().response().asString();

		// Delete Product

		RequestSpecification deleteproductreqspec = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
				.setContentType(ContentType.JSON).build();

		String deleteProductResponse = given().log().all().spec(deleteproductreqspec).pathParam("productId", prodcutid)
				.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().assertThat()
				.statusCode(200).body("message", equalTo("Product Deleted Successfully")).extract().response()
				.asString();
	}

}
