package com.shklyar.demo.entities;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

   @ManyToMany(cascade = CascadeType.ALL)

   @JoinTable(name = "roles",
           joinColumns = @JoinColumn(name = "userId"),
           inverseJoinColumns = @JoinColumn(name = "roleId")
   )
   private List<Role> roles = new ArrayList<>();



   @OneToOne(cascade = CascadeType.REMOVE)
   private Backet backet;


   public User(Long userId, String username, String firstName,String lastName, String email, String password, List<Role> roles)
   {
      this.userId = userId;
      this.username = username;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.roles = roles;
      this.backet = null;
   }


   @Override
   public Collection<? extends GrantedAuthority> getAuthorities()
   {

      return roles;
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
