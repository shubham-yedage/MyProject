package models;


import javax.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(generator = "")
	public String id;
    @OrderBy
    public String name;
}
