package fr.ippon.ippevent.nosqlha.mongo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "users")
@Data
@EqualsAndHashCode(of = {"username"})
public class User {

    @Id
    private String username;

    public User username(String username){
        setUsername(username);
        return this;
    }

    private Integer age;

    public User age(Integer age){
        setAge(age);
        return this;
    }
}
