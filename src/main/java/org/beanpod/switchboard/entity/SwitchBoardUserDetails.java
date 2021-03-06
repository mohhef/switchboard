package org.beanpod.switchboard.entity;

import java.util.Collection;
import java.util.Collections;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SwitchBoardUserDetails implements UserDetails {

  @NotNull private String username;

  @NotNull private String password;

  @Builder.Default private UserRole userRole = UserRole.USER;

  public SwitchBoardUserDetails(UserEntity userEntity) {
    this.username = userEntity.getUsername();
    this.password = userEntity.getPassword();
    this.userRole = userEntity.getUserRole();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    final SimpleGrantedAuthority simpleGrantedAuthority =
        new SimpleGrantedAuthority(userRole.name());
    return Collections.singletonList(simpleGrantedAuthority);
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
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
}
