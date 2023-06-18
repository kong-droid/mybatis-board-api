package site.kongdroid.api.config.security;

import lombok.val;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import site.kongdroid.api.constants.UserRole;
import site.kongdroid.api.service.MemberService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements ExtendedUserDetailsService {

    private final Supplier<MemberService> serviceSupplier;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        val requestMap = new HashMap<String, Object>();
        requestMap.put("email", email);
        return convert(serviceSupplier.get().getMembers(requestMap, false));
    }

    @Override
    public UserDetails loadUserByNo(Integer userNo) throws UsernameNotFoundException {
        val requestMap = new HashMap<String, Object>();
        requestMap.put("userNo", userNo);
        return convert(serviceSupplier.get().getMembers(requestMap, false));
    }

    private UserDetails convert(Map<String, Object> memberMap){
        Integer userNo = (Integer) memberMap.get("userNo");
        String email = (String) memberMap.get("email");
        UserRole role = (UserRole) memberMap.get("role");
        return new ExtendedUserDetails(userNo, email, role);
    }
}
