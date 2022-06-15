package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.given;


public class HardCodedExamples {
   String baseURI=RestAssured.baseURI="http://hrm.syntaxtechs.net/syntaxapi/api";
   String token="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NTUzMjcxODcsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY1NTM3MDM4NywidXNlcklkIjoiMzc5NCJ9.HGunfoxahGC4sGuFnFIWsMnSDU1HgwR1v36vKcwOXGA";

   @Test
   public void createEmployee(){
      RequestSpecification request= given().header("Content-Type","application/json").
              header("Authorization",token).body("{\n" +
                      "  \"emp_firstname\": \"Hilmi\",\n" +
                      "  \"emp_lastname\": \"zc\",\n" +
                      "  \"emp_middle_name\": \"mr\",\n" +
                      "  \"emp_gender\": \"M\",\n" +
                      "  \"emp_birthday\": \"1990-06-11\",\n" +
                      "  \"emp_status\": \"Probation\",\n" +
                      "  \"emp_job_title\": \"QA\"\n" +    //given baslangıç gerekleri
                      "}");
     Response response= request.when().post("/createEmployee.php"); //postmandan gelen en point swaggerde post oldugu için
      response.prettyPrint();
   }
}
