package com.brainworks.rest.restControllers;

import com.brainworks.rest.payloads.response.ApiResponseStatus;
import com.brainworks.rest.payloads.response.UserDto;
import com.brainworks.rest.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    @PreAuthorize ("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.ok(this.userService.getAlluser());
    }

    @GetMapping("/{userId}")
    @PreAuthorize ("hasAuthority('USER')")
    public ResponseEntity<UserDto> getUserById(@PathVariable ("userId") Integer uId) {
        UserDto UserDto = this.userService.getUserById(uId);
        return ResponseEntity.ok(UserDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable ("userId") Integer uId) {
        UserDto updatedUserDto = this.userService.updateUser(userDto,uId);
        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseStatus> deleteUser(@PathVariable("userId") Integer uId){
        this.userService.deleteUser(uId);
        return new ResponseEntity<>(new ApiResponseStatus("Account deleted Successfully",true),HttpStatus.OK);
    }

    @PatchMapping(value = "/{userId}",
            consumes = {"multipart/form-data","application/json","application/xml"})
    public ResponseEntity<UserDto> updateSpecificField(@PathVariable ("userId") Integer uId,@RequestBody Map<String,Object> objectMap){
        System.out.println (objectMap.isEmpty ());
        UserDto updatedUserDto = this.userService.updateSpecificField(uId,objectMap );
        return ResponseEntity.ok(updatedUserDto);
    }
}
