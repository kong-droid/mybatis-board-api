package shop.zeedeco.entity;

import lombok.Data;

@Data
public class MemberRole {
	private int roleSeq;
	private String name;
	private String remark;
	private String createdDt;
}
