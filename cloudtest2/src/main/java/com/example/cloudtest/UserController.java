package com.example.cloudtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        logger.info("UserController initialized");
    }

    @GetMapping("/")
    public String home() {
        logger.info("Entering method: home()");
        logger.info("Exiting method: home()");
        return "index";
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        logger.info("Entering method: viewUsers()");
        try {
            model.addAttribute("users", userRepository.findAll());
            logger.info("Loaded users successfully in viewUsers()");
            logger.info("Exiting method: viewUsers()");
            return "users";
        } catch (Exception e) {
            logger.error("Exception in viewUsers(): unable to load users", e);
            throw e;
        }
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        logger.info("Entering method: showAddForm()");
        try {
            model.addAttribute("user", new User());
            logger.info("Prepared empty user object in showAddForm()");
            logger.info("Exiting method: showAddForm()");
            return "add-user";
        } catch (Exception e) {
            logger.error("Exception in showAddForm(): unable to prepare add form", e);
            throw e;
        }
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        logger.info("Entering method: saveUser()");
        try {
            logger.info("Saving user with email: {}", user.getEmail());
            userRepository.save(user);
            logger.info("User saved successfully in saveUser()");
            logger.info("Exiting method: saveUser()");
            return "redirect:/users";
        } catch (Exception e) {
            logger.error("Exception in saveUser(): unable to save user", e);
            throw e;
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        logger.info("Entering method: showEditForm() for id={}", id);
        try {
            User user = userRepository.findById(id).orElseThrow();
            model.addAttribute("user", user);
            logger.info("Loaded user successfully in showEditForm() for id={}", id);
            logger.info("Exiting method: showEditForm()");
            return "edit-user";
        } catch (Exception e) {
            logger.error("Exception in showEditForm(): unable to load user for id={}", id, e);
            throw e;
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        logger.info("Entering method: deleteUser() for id={}", id);
        try {
            userRepository.deleteById(id);
            logger.info("Deleted user successfully in deleteUser() for id={}", id);
            logger.info("Exiting method: deleteUser()");
            return "redirect:/users";
        } catch (Exception e) {
            logger.error("Exception in deleteUser(): unable to delete user for id={}", id, e);
            throw e;
        }
    }
}