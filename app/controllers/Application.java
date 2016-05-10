package controllers;

import models.Person;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public class Application extends Controller {

    public Result index() {
        try {
            List<Person> list = Person.getPersons();
            return ok(Json.toJson(list));
        } catch (SQLException e) {
            return internalServerError(e.getMessage());
        } catch (ClassNotFoundException e) {
            return internalServerError(e.getMessage());
        } catch (FileNotFoundException e) {
            return internalServerError(e.getMessage());
        }

    }

    @BodyParser.Of(BodyParser.FormUrlEncoded.class)
    public Result addPerson() {
        try {
            Person person = Person.getPersonObject(request().body().asFormUrlEncoded());
            person.save();
            List<Person> list =person.getPersons();
            return ok(Json.toJson(list));
        } catch (IllegalArgumentException ex) {
            return badRequest(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            return internalServerError("ClassNotFound" + ex.getMessage());
        } catch (SQLException e) {
            return internalServerError("Sql Exception" + e.getMessage());
        } catch (FileNotFoundException e) {
            return internalServerError("FileNotFound" + e.getMessage());
        }
    }

    public Result removePerson(String id) {
        try {
            if (Person.remove(Integer.parseInt(id))) {
                return ok("Person not exist");
            } else {
                return ok("removed!");
            }
        } catch (SQLException e) {
            return internalServerError("Cant Remove" + e.getMessage());
        } catch (ClassNotFoundException e) {
            return internalServerError("Class not found" + e.getMessage());
        }
    }

    public Result updatePerson(String id, String name) {
        try {
            Person.update(Integer.parseInt(id), name);
            return ok();
        } catch (FileNotFoundException e) {
            return internalServerError(e.getMessage());
        } catch (ClassNotFoundException e) {
            return internalServerError(e.getMessage());
        } catch (SQLException e) {
            return internalServerError(e.getMessage());
        }

    }
}