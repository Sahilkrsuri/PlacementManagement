package com.tint.edu.PlacementManagemebt.entity;

import jakarta.persistence.*;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String name;
    @Column(length = 200)
    private String description;

    public Role() {
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public  static RoleBuilder builder(){
        return new RoleBuilder();
    }
    public static  class RoleBuilder{
        private String name;
        private  String description;

        public RoleBuilder() {
        }

        public RoleBuilder(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public RoleBuilder name(String name){
            this.name=name;
            return this;
        }
        public RoleBuilder description(String description){
            this.description=description;
            return this;
        }
        public Role build(){
            Role role=new Role();
            role.setName(name);
            role.setDescription(description);
            return  role;
        }


    }
}
