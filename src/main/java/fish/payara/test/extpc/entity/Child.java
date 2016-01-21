/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.test.extpc.entity;

import java.util.concurrent.atomic.AtomicInteger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author UI187816
 */
@Entity
public class Child {
    
    @Id
    private long id;
    
    @OneToOne
    @PrimaryKeyJoinColumn(name = "stuff_id", referencedColumnName = "ID")
    private Stuff stuff;        
    
    @ManyToOne()
    @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID", nullable = false)
    private Parent parent;
    
    protected Child() {
        
    }
    
    public Child(Stuff s, Parent p) {
        this.stuff = s;
        this.parent = p;
    }
    
    @PrePersist
    void generateId() {
        if (id == 0) {
            id = stuff.getId();
        }
    }
}
