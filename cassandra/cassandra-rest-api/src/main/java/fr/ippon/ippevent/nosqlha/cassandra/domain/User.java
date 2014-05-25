package fr.ippon.ippevent.nosqlha.cassandra.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@EqualsAndHashCode(of = {"username"})
@XmlRootElement
public class User {

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
