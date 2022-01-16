package shop.zeedeco.entity;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Member {
	private int memberSeq;
	private String id;
	private String password;
	private String name;
	private String role;
	private String email;
	
	private Set<MemberRole> roles = new HashSet<>(); 
}
