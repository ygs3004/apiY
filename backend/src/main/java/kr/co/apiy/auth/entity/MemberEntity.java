package kr.co.apiy.auth.entity;

import jakarta.persistence.*;
import kr.co.apiy.auth.dto.MemberRole;
import kr.co.apiy.global.entity.BaseEntity;
import kr.co.apiy.global.converters.Converters;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MEMBER")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    @Builder.Default
    @Convert(converter = Converters.ForMemberRole.class)
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole){
        roleSet.add(memberRole);
    }

}
