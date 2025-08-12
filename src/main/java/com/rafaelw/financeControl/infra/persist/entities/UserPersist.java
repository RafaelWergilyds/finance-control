package com.rafaelw.financeControl.infra.persist.entities;

import com.rafaelw.financeControl.domain.entities.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@SQLDelete(sql = "UPDATE users SET active = false WHERE id = ?")
@SQLRestriction("active = true")
@Table(name = "users")
public class UserPersist {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;

  @Column(unique = true)
  private String email;
  private String password;

  @Column(columnDefinition = "boolean default true")
  private boolean active = true;

  @Enumerated(value = EnumType.STRING)
  @Column(columnDefinition = "varchar(20) default 'COMMON'")
  private Role role = Role.COMMON;

  @OneToMany(mappedBy = "user")
  private Set<CategoryPersist> categories = new HashSet<>();

  @OneToMany(mappedBy = "user")
  private List<DebitPersist> debits = new ArrayList<>();

}
