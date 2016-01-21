package fish.payara.test.extpc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Patrik Dudits
 */
@Entity
public class Parent {
    @Id
    @GeneratedValue
    private long id;
    
    public long getId() {
        return id;
    }

}
