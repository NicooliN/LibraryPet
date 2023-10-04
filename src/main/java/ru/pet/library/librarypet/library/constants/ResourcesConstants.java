package ru.pet.library.librarypet.library.constants;

import java.util.List;

public interface ResourcesConstants {

   final List<String> RESOURCES_WHITE_LIST = List.of("/resources/**",
            "/js/**",
            "/css/**",
           "/webjars/bootstrap/5.0.2/**",
            "/");

    final List<String> BOOKS_WHITE_LIST = List.of("/books",
            "/books/search",
            "/books/{id}");

    final List<String> BOOKS_PERMISSION_LIST =List.of("/books/add",
            "/books/update",
            "/books/delete");

     final List<String> USERS_WHITE_LIST = List.of("/login",
             "/users/registration",
             "/users/remember-password",
             "/users/change-password");


}
