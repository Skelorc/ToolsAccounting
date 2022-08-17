package wns.constants;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
   ADMIN,
   MANAGER,
   WORKER;

   @Override
   public String getAuthority() {
      return this.name();
   }
}
