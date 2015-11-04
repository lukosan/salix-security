package org.lukosan.salix.security;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecurityController {

	@Autowired
	private SalixSecurityService salixSecurityService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value="/salix/authentication", method=RequestMethod.GET)
	public String login(Principal principal, Model model) {
		model.addAttribute("user", UserDetailsAdapter.userFromPrincipal(principal));
		return "salix/security/authentication";
	}
	
	@RequestMapping(value="/salix/login", method=RequestMethod.GET)
	public String login() {
		return "salix/security/login";
	}
	
	@RequestMapping(value="/salix/register", method=RequestMethod.GET)
	public String register() {
		return "salix/security/register";
	}

	@PreAuthorize("hasPermission(#scope, 'SALIX_ADMIN')")
	@RequestMapping(value="/salix/register", method=RequestMethod.POST)
	public String register(String username, String password, String scope, String role) {
		SalixUser user = salixSecurityService.save(username, passwordEncoder.encode(password));
		salixSecurityService.allow(user.getUsername(), scope, role);
		return "redirect:/salix/login";
	}
}