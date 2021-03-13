package com.eletronicodigitalmobile.security;

import java.util.Collection;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.eletronicodigitalmobile.domain.enums.Perfil;

@CrossOrigin("${origem-permitida}")
public class UserSS implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String email;
	private String senha;
	private Collection<? extends GrantedAuthority> authority;
	
	public UserSS() {
		
	}

	public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authority = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
				return authority;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	public Integer getId() {
		return id;
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
	
	public boolean hasRole(Perfil perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
		
	}

}
