package ejercicioItems;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;

public class CRUDItem {

    @Test
    public void crudItem(){
        //CREATE ITEM
        JSONObject body = new JSONObject();
        body.put("Content","Primer item - Manuel V");
        body.put("ProjectId",3981743);

        Response response=given()
                .auth()
                .preemptive()
                .basic("upb_api_prueba@upb.com","upb123")
                .body(body.toString())
                .log().all()
                .when()
                .post("https://todo.ly/api/items.json");
        response.then()
                .statusCode(200)
                .body("Content",equalTo("Primer item - Manuel V"))
                .body("ProjectId",equalTo(3981743))
                .log().all();

        int idItem=response.then().extract().path("Id");

        //READ ITEM
        response=given()
                .auth()
                .preemptive()
                .basic("upb_api_prueba@upb.com","upb123")
                .log().all()
                .when()
                .get("https://todo.ly/api/items/"+idItem+".json");
        response.then()
                .statusCode(200)
                .body("Content",equalTo("Primer item - Manuel V"))
                .body("ProjectId",equalTo(3981743))
                .log().all();

        //UPDATE ITEM
        body.put("Content","Primer item update");
        body.put("Checked",true);
        response=given()
                .auth()
                .preemptive()
                .basic("upb_api_prueba@upb.com","upb123")
                .body(body.toString())
                .log().all()
                .when()
                .put("https://todo.ly/api/items/"+idItem+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo("Primer item update"))
                .body("Checked",equalTo(true))
                .log().all();

        //DELETE ITEM
        response=given()
                .auth()
                .preemptive()
                .basic("upb_api_prueba@upb.com","upb123")
                .log().all()
                .when()
                .delete("https://todo.ly/api/items/"+idItem+".json");

        response.then()
                .statusCode(200)
                .body("Content",equalTo("Primer item update"))
                .body("ProjectId",equalTo(3981743))
                .body("Deleted",equalTo(true))
                .log().all();

    }
}
