package com.quick.sms.controller;

import com.quick.sms.documents.Route;
import com.quick.sms.dto.ManageClient;
import com.quick.sms.dto.authentication.ChangePasswordByOtpDto;
import com.quick.sms.dto.authentication.ChangePasswordDto;
import com.quick.sms.dto.authentication.ForgotPasswordDto;
import com.quick.sms.dto.authentication.ResetPasswordDto;
import com.quick.sms.dto.response.RouteResponse;
import com.quick.sms.repository.ClientRepository;
import com.quick.sms.repository.RouteRepository;
import com.quick.sms.service.AuthService;
import com.quick.sms.service.ClientService;
import com.quick.sms.service.RouteService;
import com.quick.sms.vo.InputRequest;
import com.quick.sms.vo.LoginRequest;
import com.quick.sms.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins="*")
@Slf4j
public class AuthController {
	@Autowired
	AuthService authService;

	@PostMapping("/login")
	public Response login(@Valid @RequestBody LoginRequest loginRequest) throws Exception{
		return authService.loginClient(loginRequest);
	}

	@PostMapping("/password/forgot")
	public String forgotPassword(@Valid ForgotPasswordDto forgotPasswordDto) throws Exception{
		return authService.forgotPassword(forgotPasswordDto);
	}

	@PostMapping("/password/change")
	public String changePasswordAfterLogin(@Valid @RequestBody ChangePasswordDto changePasswordDto) throws Exception{
		return authService.changePasswordAfterLogin(changePasswordDto);
	}

	@PostMapping("/password/change/by_otp")
	public String changePasswordBeforeLogin(@Valid @RequestBody ChangePasswordByOtpDto dto) throws Exception{
		return authService.changePasswordBeforeLogin(dto);
	}

	@PostMapping("/password/reset")
	public String resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) throws Exception{
		return authService.resetPassword(resetPasswordDto);
	}


	@PostMapping("/blockUser")
	public ResponseEntity blockUser(@Valid @RequestBody ManageClient manageClient) throws Exception{		;
		return new ResponseEntity(authService.blockUser(manageClient), HttpStatus.OK);
	}

	@PostMapping("/deleteUser")
	public ResponseEntity deleteUser(@Valid @RequestBody ManageClient manageClient) throws Exception{
		return new ResponseEntity(authService.deleteUser(manageClient), HttpStatus.OK);
	}

	@PostMapping("/activateUser")
	public ResponseEntity activateUser(@Valid @RequestBody ManageClient manageClient) throws Exception{
		return new ResponseEntity(authService.activateUser(manageClient), HttpStatus.OK);
	}
}
