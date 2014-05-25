package fr.ippon.ippevent.nosqlha.cassandra.controller;

import fr.ippon.ippevent.nosqlha.cassandra.configuration.ReadConfiguration;
import fr.ippon.ippevent.nosqlha.cassandra.configuration.WriteConfiguration;
import fr.ippon.ippevent.nosqlha.cassandra.domain.User;
import fr.ippon.ippevent.nosqlha.cassandra.repository.UserRepository;
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
    public ResponseEntity<User> loadUser(@PathVariable String userId, @RequestParam(required = false, defaultValue = "ONE") ReadConfiguration readConf) {
        User user =  userRepository.getByUsername(userId, readConf);
        return (user != null) ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> loadUsers(@RequestParam(required = false, defaultValue = "ONE") ReadConfiguration readConf) {
        return userRepository.findUsers(readConf);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestParam String username, @RequestParam Integer age, @RequestParam(required=false, defaultValue="ONE") WriteConfiguration writeConf) {
        userRepository.createUser(username, age, writeConf);
    }

}
