package com.shklyar.demo.entities;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long userId;

   @NonNull
   @Size(max = 20)
   private String username;

   @NonNull
   @Size(max = 20)
   private String password;

   @Enumerated(EnumType.STRING)
   private Role role;

   @OneToOne(cascade = CascadeType.REMOVE)
   private Backet backet;


   public User(String username, String password)
   {
      this.username = username;
      this.password = password;
      this.role = Role.CLIENT;
      this.backet = null;
   }


   @Override
   public Collection<? extends GrantedAuthority> getAuthorities()
   {
      return null;
   }

   @Override
   public boolean isAccountNonExpired()
   {
      return true;
   }

   @Override
   public boolean isAccountNonLocked()
   {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired()
   {
      return true;
   }

   @Override
   public boolean isEnabled()
   {
      return true;
   }
}
