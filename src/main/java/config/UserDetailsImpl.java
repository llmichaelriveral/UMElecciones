package config;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import domain.user.User;

public class UserDetailsImpl extends User implements UserDetails { 

	private static final long serialVersionUID = 1L;
	private String userRoles;


	public UserDetailsImpl(User user, String userRoles){
	super(user);
		this.userRoles = userRoles;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String roles = userRoles; 
		return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public String getUsername() {
		return "user";
	}

	@Override
	public String getPassword() {
		return "password";
	}
}