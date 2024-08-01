package com.example.demo;

import com.example.demo.domain.user.User;
import lombok.Getter;

import java.util.Map;

@Getter
public class UserAdapter extends CustomUserDetails {
    private User user;
    private Map<String, Object> attributes;

    public UserAdapter(User user) {
        super(user, null);
        this.user = user;
    }

    public UserAdapter(User user, Map<String, Object> attributes) {
        super(user, attributes);
        this.user = user;
        this.attributes = attributes;
    }
}
