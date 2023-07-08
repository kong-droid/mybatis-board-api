package site.kongdroid.api.config.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import site.kongdroid.api.constants.UserRole;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ExtendedUserDetails extends User{
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Integer memberSeq;

    public ExtendedUserDetails(Integer memberSeq, String email, UserRole role){
        super(email, "", AuthorityUtils.createAuthorityList("ROLE_" + role.name()));
        this.memberSeq = memberSeq;
    }

    public ExtendedUserDetails(Integer memberSeq, String email, UserRole role, boolean enabled){
        super(email, "", enabled, true, true,
                true, AuthorityUtils.createAuthorityList("ROLE_" + role.name()));
        this.memberSeq = memberSeq;
    }
}
