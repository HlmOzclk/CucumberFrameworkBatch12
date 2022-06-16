package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //metodlar olması gerektiği sırada run olmuyorsa fix method order kullan
public class HardCodedExamples {
   String baseURI=RestAssured.baseURI="http://hrm.syntaxtechs.net/syntaxapi/api";
   String token="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NTUzNzc5MTEsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY1NTQyMTExMSwidXNlcklkIjoiMzc5NCJ9.aSZJXoZ_R1kg_w-Yzkm7RYdNo3icQj-PCqH9YHNOnRE";
   static String employee_id;
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
       response.then().assertThat().statusCode(201);
       //Hamcrest matchers=> to create matcher objects
       //prettyPrint => is used for printing response to console
       response.then().assertThat().body("Message",equalTo("Employee Created"));
    //bu şekilde null value verir//response.then().assertThat().body("emp_firstname",equalTo( "Hilmi"));
       response.then().assertThat().body("Employee.emp_firstname",equalTo( "Hilmi"));
       // ardına key e belirlemek için jsonPath metodu kullanılmalı
      employee_id= response.jsonPath().getString("Employee.employee_id");//create Employee postman console bodyde büyük E
       System.out.println(employee_id);

   }
   public void getCreatedEmployee(){
       RequestSpecification request=given().header("content-type","application/json").header("Authorization",token)
               .queryParam("employee_id",employee_id);
       Response response=request.when().get("/getOneEmployee.php");
       response.prettyPrint();
       response.then().assertThat().statusCode(200); //
       String tempId=response.jsonPath().getString("employee.employee_id");//get employee postman console body de küçü e oldugu için employee
       System.out.println(tempId);
       Assert.assertEquals(tempId,token);

   }

}
