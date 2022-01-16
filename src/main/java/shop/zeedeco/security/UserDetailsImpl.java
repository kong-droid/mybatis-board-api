package shop.zeedeco.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import shop.zeedeco.entity.Member;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@SuppressWarnings("serial")
public class UserDetailsImpl implements UserDetails {

	private Collection<? extends GrantedAuthority> authorities;
	private int memberSeq;
	private String id;
	@JsonIgnore //	@JsonIgnore : 선언된 필드의 경우 client에게 전달되는 response 데이터에서 제외
	private String password;
	private String email;
	private String name;
	
	private Map<String, Object> attributes;
	private Map<String, Object> loginMap;


	public UserDetailsImpl(int memberSeq, String id, String password, String email, String name, Collection<? extends GrantedAuthority> authorities) {
		this.memberSeq = memberSeq;
		this.id = id;
		this.password = password;
		this.email = email;
		this.name = name;
		this.authorities = authorities;
	}

	public static UserDetailsImpl loadUser(Member member) {
		
		List<GrantedAuthority> authorities = member.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		
		return new UserDetailsImpl(
			  member.getMemberSeq()
			, member.getId()
			, member.getPassword()
			, member.getEmail()
			, member.getName()
			, authorities
		);
	}
	
    public static UserDetailsImpl loadUser(Member member, Map<String, Object> attributes) {
    	UserDetailsImpl userDetailsImpl = UserDetailsImpl.loadUser(member);
    	userDetailsImpl.setAttributes(attributes);
        return userDetailsImpl;
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
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(memberSeq, user.memberSeq);
	}
	
	public Map<String, Object> getAttributes() {
        return attributes;
    }

	@Override
	public String getUsername() {
		return this.name;
	}
}
