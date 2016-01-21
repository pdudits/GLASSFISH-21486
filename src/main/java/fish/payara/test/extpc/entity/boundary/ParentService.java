package fish.payara.test.extpc.entity.boundary;

import fish.payara.test.extpc.entity.Parent;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Patrik Dudits
 */
@Stateless
public class ParentService {
    @PersistenceContext
    EntityManager mgr;
    
    public Parent create() {
        Parent p = new Parent();
        mgr.persist(p);
        return p;
    }
    
}
