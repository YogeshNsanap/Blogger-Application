package com.brainworks.rest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Catogory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;
    @Column(length = 100,nullable = false)
    private String catogoryTitle;
    private String catogoryDiscription;

    @OneToMany(mappedBy ="catogory",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Post> post=new ArrayList<> ();
}
