package API;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
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
   public void acreateEmployee(){
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
   @Test
   public void bgetCreatedEmployee(){
       RequestSpecification request=given().header("content-type","application/json").header("Authorization",token)
               .queryParam("employee_id",employee_id);
       Response response=request.when().get("/getOneEmployee.php");
       response.prettyPrint();
       response.then().assertThat().statusCode(200); //
       String tempId=response.jsonPath().getString("employee.employee_id");//get employee postman console body de küçü e oldugu için employee
       System.out.println(tempId);
       Assert.assertEquals(tempId,employee_id);


   }
   @Test
   public void cupdateEmployee(){
       RequestSpecification request=given().header("content-type","application/json").header("authorization",token)
               .body("{\n" +
                       "  \"employee_id\": \"34753A\",\n" +
                       "  \"emp_firstname\": \"edward\",\n" +
                       "  \"emp_lastname\": \"S\",\n" +
                       "  \"emp_middle_name\": \"ms\",\n" +
                       "  \"emp_gender\": \"M\",\n" +
                       "  \"emp_birthday\": \"2002-06-11\",\n" +
                       "  \"emp_status\": \"confirmed\",\n" +
                       "  \"emp_job_title\": \"Manager\"\n" +
                       "}");//"34753A" yerine, ""+employee_id+"\" yazabiliriz
       Response response=request.when().put("/updateEmployee.php");// enpointten response alıyoruz
       response.prettyPrint();
       response.then().assertThat().statusCode(200);

   }
   @Test
   public void dGetUpdatedEmployee(){

       RequestSpecification request=given().header("content-type","application/json").header("authorization",token)
               .queryParam("employee_id",employee_id);// while gettin changed parameter use .queryParam()
       Response response=request.when().get("/getOneEmployee.php");
       response.then().assertThat().statusCode(200);
       response.prettyPrint();
       //database den dogrulamak için aynı kodu 2 kez yazmış olduk


   }
   @Test
    public void eGetAllEmployee(){
       RequestSpecification request=given().header("content-type","application/json").header("authorization",token);
       Response response=request.when().get("/getAllEmployees.php");
       //response.then().assertThat().statusCode(200);

       //it return string response
      String allEmp= response.prettyPrint();


      //jsonPath is class
       //jsonPath() is a method belongs to jsonPath class

       //creating object of jsonPath class
       JsonPath jsonPath=new JsonPath(allEmp);

       //employee sayısını almak için
      int EmpCount= jsonPath.getInt("Employees.size()");
       System.out.println(EmpCount);

       //for print only id of all employees
       for (int i=0;i<EmpCount;i++){

         String empId=  jsonPath.getString("Employees["+ i +"].employee_id");
           System.out.println(empId);
       }


   }

}
