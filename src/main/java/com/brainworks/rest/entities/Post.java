package com.brainworks.rest.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer postId;
    @Column(length = 100,nullable = false)
    private  String title;
    @Column(length =10000)
    private  String content;
    private String imageName;
    private Date addedDate;

    @ManyToOne
    @JoinColumn(name = "catogoryId")
    private Catogory catogory;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comment> comment=new HashSet<> ();

}
