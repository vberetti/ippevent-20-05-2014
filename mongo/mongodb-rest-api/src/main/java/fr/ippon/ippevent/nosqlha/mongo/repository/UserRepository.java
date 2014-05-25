package fr.ippon.ippevent.nosqlha.mongo.repository;

import fr.ippon.ippevent.nosqlha.mongo.configuration.ReadConfiguration;
import fr.ippon.ippevent.nosqlha.mongo.configuration.WriteConfiguration;
import fr.ippon.ippevent.nosqlha.mongo.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
@Getter
@Setter
public class UserRepository {

    @Resource
    private Map<ReadConfiguration, MongoTemplate> readTemplates;
    @Resource
    private Map<WriteConfiguration, MongoTemplate> writeTemplates;

    public User getByUsername(String username, ReadConfiguration readConf) {
        return readTemplates.get(readConf).findById(username, User.class);
    }

    public void delete(String username, WriteConfiguration writeConf) {
        writeTemplates.get(writeConf).remove(new Query(Criteria.where("username").is(username)), User.class);
    }

    public void updateAge(String username, int age, WriteConfiguration writeConf) {
       writeTemplates.get(writeConf).updateFirst(
                new Query(Criteria.where("username").is(username)),
                Update.update("age", age), User.class);
    }

    public void createUser(String username, Integer age, WriteConfiguration writeConf) {
        User user = new User().username(username).age(age);
        //writeTemplates.get(writeConf).insert(user);
        Query query = Query.query(Criteria.where("username").is(username));
        Update update = Update.update("age", age);
        writeTemplates.get(writeConf).upsert(query, update, User.class);
    }

    public List<User> findUsers(ReadConfiguration readConf) {
        return readTemplates.get(readConf).find(new Query().limit(20), User.class);
    }
}