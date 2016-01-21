/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.test.extpc.entity.boundary;

import fish.payara.test.extpc.entity.Parent;
import fish.payara.test.extpc.entity.Stuff;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author UI187816
 */
@Stateless
public class ParentService {
    @PersistenceContext
    EntityManager mgr;
    
    @EJB
    StuffService stuff;
    
    public Parent create() {
        Parent p = new Parent();
        mgr.persist(p);
        return p;
    }
    
    public void deleteParent(Parent p) {
        p = mgr.find(Parent.class, p.getId());
        mgr.remove(p);
    }
    
    public int childrenCount(Parent p) {
        p = mgr.find(Parent.class, p.getId());
        return p.childrenCount();
    }
    
}
