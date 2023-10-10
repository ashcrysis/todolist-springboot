package br.com.ashermartins.todolist.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping("/")
    public void create(@RequestBody UserModel userModel) {
        System.out.println("Usuário : " + userModel.username);
        System.out.println("Nome : " + userModel.name);
        System.out.println("Senha : " + userModel.password);
    }
}
