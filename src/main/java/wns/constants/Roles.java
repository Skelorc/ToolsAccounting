package wns.constants;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
   ADMIN("ADMIN"),
   MANAGER("MANAGER"),
   WORKER("WORKER");

   private final String value;

   Roles(String value) {
      this.value = value;
   }

   public String getValue()
   {
      return this.value;
   }

   @Override
   public String getAuthority() {
      return this.name();
   }

   @Override
   public String toString() {
      return this.name();
   }

   public String getRoles()
   {
      return "ROLE_" + this.name();
   }
}
