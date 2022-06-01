package com.example.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationForm {
    private String username;
    private String displayName;
    private String gender;
    private String day, month, year;
    private String password;
    private String confirmPassword;

    public User toUser(PasswordEncoder passwordEncoder) throws ParseException {
        while (day.length() < 2) day = "0" + day;
        while (month.length() < 2) month = "0" + month;
        Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse( day + "/" + month + "/" + year);
        Date d2 = new Date(System.currentTimeMillis());
        User user = new User(username, displayName, gender, d1, passwordEncoder.encode(password));
        user.setCreatedAt(d2);
        return user;
    }
}
