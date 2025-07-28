package com.dhopecode.shoppingCart.data;

import com.dhopecode.shoppingCart.model.User;
import com.dhopecode.shoppingCart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;

    public void onApplicationEvent(ApplicationReadyEvent event){
        createDefaultUserIfNotExist();
    }

    private void createDefaultUserIfNotExist(){
        for (int i=1; i<=5; i++){
            String defaultEmail = "user"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("the user");
            user.setLastName("User"+i);
            user.setEmail(defaultEmail);
            user.setPassword("123456");
            userRepository.save(user);
            System.out.println("default user" + i + "created successfully");
        }
    }
}
