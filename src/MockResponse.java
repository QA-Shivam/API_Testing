
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;
import org.testng.Assert;

import netscape.javascript.JSObject;

public class MockResponse {
	public static void main(String[] args) throws IOException {
		String jsonData = new String(Files.readAllBytes(Paths.get("src/payload/testData.json")));
		JSONObject jsonObject = new JSONObject(jsonData);
		System.out.println("No Of Cources>> " + jsonObject.getJSONArray("courses").length());
		System.out.println("Purchase Amount>> " + jsonObject.getJSONObject("dashboard").getInt("purchaseAmount"));
		System.out.println(
				"Title Of 1st Course>> " + jsonObject.getJSONArray("courses").getJSONObject(0).getString("title"));
		jsonObject.getJSONArray("courses")
				.forEach(course -> System.out.println("Title: " + ((JSONObject) course).get("title")));
		jsonObject.getJSONArray("courses").forEach(course -> System.out.println("Title>> "
				+ ((JSONObject) course).get("title") + "Course Price>> " + ((JSONObject) course).getInt("price")));
		jsonObject.getJSONArray("courses").forEach(course -> {
			if (((JSONObject) course).get("title").equals("RPA")) {
				System.out.println("No Of Copies Sold By RPA Course>> " + ((JSONObject) course).getInt("copies"));
			}
		});
		
		// Use AtomicInteger to hold the purchaseAmount (mutable)
        AtomicInteger purchaseAmount = new AtomicInteger(0);  // Initialized with 0
		jsonObject.getJSONArray("courses").forEach(course -> {
			String title=((JSONObject) course).get("title").toString();
			int price =((JSONObject)course).getInt("price");
			int nofocopies=((JSONObject) course).getInt("copies");
			int soldprice= price*nofocopies;
			purchaseAmount.getAndAdd(soldprice);
			System.out.println(title +"Course Sold Price>> "+ soldprice );
		});
		System.out.println("Calculated Purchase Amount>> "+ purchaseAmount);
		int actualpurchaseAmount= jsonObject.getJSONObject("dashboard").getInt("purchaseAmount");
		Assert.assertEquals(actualpurchaseAmount, purchaseAmount.get());
	}
}
