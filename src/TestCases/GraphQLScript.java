package TestCases;

import static io.restassured.RestAssured.*;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
public class GraphQLScript {
public static void main(String[] args) {
	//Query
	int characterId=13851;
	int locationId=19994;
	int episodeId=13979;
	
	String response=given().log().all().header("Content-Type","application/json").body("{\"query\":\"query ($characterId: Int!, $locationId: Int!, $episodeId: Int!) {\\n  character(characterId: $characterId) {\\n    name\\n    gender\\n    type\\n    status\\n    species\\n    id\\n  }\\n  location(locationId: $locationId) {\\n    name\\n    id\\n    dimension\\n  }\\n  episode(episodeId: $episodeId) {\\n    id\\n    episode\\n    name\\n    air_date\\n    characters {\\n      id\\n    }\\n  }\\n}\\n\",\"variables\":{\"characterId\":"+characterId+",\"locationId\":"+locationId+",\"episodeId\":"+episodeId+"}}").when().post("https://rahulshettyacademy.com/gq/graphql").then().log().all().extract().response().asString();
	
	System.out.println(response);
	JsonPath jsonPath=new JsonPath(response);
	String Name= jsonPath.getString("data.character.name");
	Assert.assertEquals(Name, "Shivam");
	
	//Mutation
	String newCharacterName="Ramya";
	String mutationresponse=given().log().all().header("Content-Type","application/json").body("{\"query\":\"mutation ($locationName: String!, $characterName: String!, $episodeName: String!) {\\n  createLocation(location: {name: $locationName, type: \\\"ABCD\\\", dimension: \\\"2345\\\"}) {\\n    id\\n  }\\n  createCharacter(character: {name: $characterName, type: \\\"Men\\\", status: \\\"dead\\\", species: \\\"human\\\", gender: \\\"male\\\", image: \\\"jpg\\\", locationId: 19994, originId: 19994}) {\\n    id\\n  }\\n  createEpisode(episode: {name: $episodeName, air_date: \\\"4/6/2025\\\", episode: \\\"Mira season1\\\"}) {\\n    id\\n  }\\n  deleteLocations(locationIds: [19995]) {\\n    locationsDeleted\\n  }\\n}\\n\",\"variables\":{\"locationName\":\"Alsalvador\",\"characterName\":\""+newCharacterName+"\",\"episodeName\":\"Mira Episode Seasion 1\"}}\r\n"
			+ "").when().post("https://rahulshettyacademy.com/gq/graphql").then().log().all().extract().response().asString();
	
	System.out.println(mutationresponse);
	
}
}
