/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.test.extpc.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author UI187816
 */
@Entity
public class Parent {
    @Id
    private long id;
    
    @OneToMany(mappedBy = "parent", cascade = { CascadeType.REFRESH, CascadeType.REMOVE })
    //@OrderBy("name")
    private List<Child> children;
    
    @OneToOne
    @PrimaryKeyJoinColumn(name = "stuff_id", referencedColumnName = "ID")
    private Stuff stuff;       
    
    protected Parent() {
        
    }
    
    public Parent(Stuff s) {
        this.stuff = s;
    }
    
    @PrePersist
    void updateId() {
        if (id == 0) {
            id = stuff.getId();
        }
    }
    
    public void addChild(Child c) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(c);
    }
    
    public void removeChild(Child c) {
        if (children != null) {
            children.remove(c);
        }
    }
    
    public int childrenCount() {
        return children.size();
    }
    
    public Child getChild(int index) {
        return children.get(index);
    }

    public long getId() {
        return id;
    }

    public Stuff getStuff() {
        return stuff;
    }
}
