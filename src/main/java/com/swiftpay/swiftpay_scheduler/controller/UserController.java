package com.swiftpay.swiftpay_scheduler.controller;

import com.swiftpay.swiftpay_scheduler.dto.user.UserDTO;
import com.swiftpay.swiftpay_scheduler.dto.user.WriteUserDTO;
import com.swiftpay.swiftpay_scheduler.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.swiftpay.swiftpay_scheduler.constants.ApiPaths.API_PATH;
import static com.swiftpay.swiftpay_scheduler.constants.ApiPaths.USER;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = API_PATH + USER)
public class UserController {

    private final UserService service;

    @GetMapping
    public UserDTO getCurrentUser() {
        return service.getCurrentDTO();
    }

    @PatchMapping()
    public void patchName(@RequestBody WriteUserDTO write) {
        service.patchName(write.name());
    }
}
