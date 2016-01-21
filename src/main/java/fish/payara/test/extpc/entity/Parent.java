package fish.payara.test.extpc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

/**
 *
 * @author Patrik Dudits
 */
@Entity
@NamedQuery(name = "Parent.otherIds", query = "select p from Parent p where p.id <> :id")
public class Parent {
    @Id
    @GeneratedValue
    private long id;
    
    public long getId() {
        return id;
    }

}
