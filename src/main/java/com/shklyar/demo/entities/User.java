package com.shklyar.demo.entities;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;
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
   private Long userId;

   @NonNull
   @Size(max = 20)
   private String username;

   @NonNull
   @Size(max = 20)
   private String firstName;

   @NonNull
   @Size(max = 20)
   private String lastName;

   @NonNull
   @Size(max = 20)
   private String email;

   @NonNull
   @Size(max = 20)
   private String password;


   @ManyToOne
   @JoinColumn(name = "user_role")
   private Role role;

   @NonNull
   @Size(max = 20)
   private String postOffice;
   @NonNull
   @Size(max = 20)
   private String numberPhone;
   @NonNull
   @Size(max = 20)
   private String departmentPostOffice;


   @OneToOne(cascade = CascadeType.REMOVE)
   private Backet backet;


   public User(Long userId, String username, String firstName,String lastName, String email, String password, Role role)
   {
      this.userId = userId;
      this.username = username;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.role = role;
      this.backet = null;
   }


   @Override
   public Collection<? extends GrantedAuthority> getAuthorities()
   {
       ArrayList<Role> result = new ArrayList<>();

       result.add(role);

       return result;
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
