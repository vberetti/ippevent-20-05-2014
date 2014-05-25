package fr.ippon.ippevent.nosqlha.mongo.controller;

import fr.ippon.ippevent.nosqlha.mongo.configuration.ReadConfiguration;
import fr.ippon.ippevent.nosqlha.mongo.configuration.WriteConfiguration;
import fr.ippon.ippevent.nosqlha.mongo.domain.User;
import fr.ippon.ippevent.nosqlha.mongo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping(value = "/users",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Inject
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}")
    @ResponseBody
    public ResponseEntity<User> loadUser(@PathVariable String userId, @RequestParam(required = false, defaultValue = "PRIMARY") ReadConfiguration readConf) {
        User user =  userRepository.getByUsername(userId, readConf);
        return (user != null) ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> loadUsers(@RequestParam(required = false, defaultValue = "PRIMARY") ReadConfiguration readConf) {
        return userRepository.findUsers(readConf);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestParam String username, @RequestParam Integer age, @RequestParam(required=false, defaultValue="ACKNOWLEDGED") WriteConfiguration writeConf) {
        userRepository.createUser(username, age, writeConf);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{userId}/age")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserAge(
            @PathVariable String userId, @RequestParam int age, @RequestParam(required=false, defaultValue="ACKNOWLEDGED") WriteConfiguration writeConf) {
        userRepository.updateAge(userId, age, writeConf);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId, @RequestParam(required=false, defaultValue="ACKNOWLEDGED") WriteConfiguration writeConf) {
        userRepository.delete(userId, writeConf);
    }

}
