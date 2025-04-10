package payload;

public class dynamicTestData {
	public static String addbook(String isbn, String aisle) {

		return "{\r\n" + "\r\n" + "\"name\":\"Learn Selenide Automation with Java\",\r\n" + "\"isbn\":\""+isbn+"\",\r\n"
				+ "\"aisle\":\""+aisle+"\",\r\n" + "\"author\":\"John Fuck\"\r\n" + "}";
	}

	public static String deletebook(String id) {
		return "{\n" + "  \"ID\": \"" + id + "\"\n" + "}";
	}
}
