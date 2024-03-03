package com.inn.cafe.POJO;


import com.inn.cafe.POJO.auth.Role;
import com.inn.cafe.POJO.auth.Token;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;




@NamedQuery(name = "User.findByUsername",query = "select u from User u where  u.username=:username")


@NamedQuery(name = "User.getAllUser",query = "select new com.inn.cafe.wrapper.UserWrapper(u.id,u.name,u.contactNumber,u.username,u.status) from  User u where u.role='user' ")
@NamedQuery(name = "User.getAllAdmin",query = "select new com.inn.cafe.wrapper.UserWrapper(u.id,u.name,u.contactNumber,u.username,u.status) from  User u where u.role='admin'")

//@NamedQuery(name = "User.updateStatus",query ="update User u  set u.status=:status where u.id=:id")
@Entity
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "user")
public class User  implements  UserDetails{

//    private  static  final  long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Integer id;
    @Column(name = "name")
    private String name;


    @Column(name = "contactNumber")

    private String contactNumber;

    @Column(name = "username")
    private String username;


    @Column(name = "password")
    private  String password;
    @Column(name = "status")
    private String status;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
