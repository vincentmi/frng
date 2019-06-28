package in.philo.fr;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import java.util.HashMap;
import java.util.Map;

public class MainTest {

    @BeforeClass
    public void setUp() {
        Reporter.log("read simple case");
    }

    @Test(groups = { "base" })
    public void baseRequest() {


        HashMap<String,String> params = new HashMap<>();

        params.put("id","1009");
        params.put("name","miwenshu");

        //GET 请求
        given().cookie("italent","1").
        when()
                .get("http://screening-svc.philowork.com/?name={name}&school={school}", new HashMap<String,String>() {{
           put("name","vincent");
           put("school","lfr");
        }})

                .then()
                .cookie("italent","xxxx")
                .statusCode(200)
                .body("data.svc",equalTo("office-screening-svc"));


        //PUT请求 RAWJSON
        Response resp = given()
                .request()
                .contentType("application/json")
                .body(new HashMap<String,String>(){{put("mac","TEST-1");}} )
                .when().put("http://screening-svc.philowork.com/api/v1/screen");
        //resp.prettyPrint();
        resp.then()
                .statusCode(200)
                .body("code",equalTo(600604))
                ;
    }


}
