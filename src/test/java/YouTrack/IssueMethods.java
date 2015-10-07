package YouTrack;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Cookies;
import com.jayway.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
/**
 * Created by MariaShmelyova on 30.09.2015.
 */
public class IssueMethods {
    Cookies cookies;
    private String createIssue() throws Exception{
        Response response =
                given().
                        cookies(cookies).
                        param("project", "GOITQA").
                        param("summary", "MariaSummary").
                        param("description", "sh.m").
                        when().
                        put("/issue");
        String location = response.getHeader("Location");
        String issueId = location.substring(location.lastIndexOf('/')+ 1);
        return issueId;
    }

    @Before
    public void setUp() throws Exception {
        RestAssured.baseURI = "https://goit2.myjetbrains.com/youtrack/rest/";

        Response response=
        given().
                param("login", "m.shmelyova@gmail.com").
                param("password", "qwerty1234").
                when().
                        post("user/login");

        cookies = response.getDetailedCookies();
//        RestAssured.baseURI = "https://gorest.myjetbrains.com/youtrack/rest/";
//
//        Response response =
//                given().
//                        param("login", "m.shmelyova@gmail.com").
//                        param("password", "qwerty123").
//                        when().
//                        post("/user/login");
//
//        cookies = response.getDetailedCookies();

    }
    @Test
    public void CreateIssue() throws Exception {
        given().
                cookies(cookies).
                param("project", "GOITQA").
                param("summary", "MariaSummary").
                param("description", "sh.m").
        when().
                put("/issue").
                then().statusCode(201);

    }

    @Test
    public void testDeleteIssue() throws Exception {
        String issue = createIssue();

        given().
                cookies(cookies).
        when().
                delete("/issue/" + issue).
        then().
                statusCode(200);

    }

    @Test
    public void testGetIssue() throws Exception {
        String issue = createIssue();
        given().
                cookies(cookies).
        when().
                get("/issue/" + issue).
        then().
                statusCode(200).
                body("issue.@id", equalTo(issue))
        ;
//        Response r =
//                given().
//                        cookies(cookies).
//                        when().
//                        get("/issue/" + issue).
//                        then().
//                        statusCode(200).extract().response();
//
//        System.out.print(r.asString());

    }

    @Test
    public void testIssueExists() throws Exception {
        String issueID = createIssue();

        given().
                cookies(cookies).
        when().
                get("/issue/" + issueID + "/exists").
        then().
                statusCode(200);

    }
    @Test
    public void testIssueNotExists() throws Exception {


        given().
                cookies(cookies).
                when().
                get("/issue/" + 123 + "/exists").
                then().
                statusCode(404);
    }

    @Test
    public void testName() throws Exception {

    }
}


