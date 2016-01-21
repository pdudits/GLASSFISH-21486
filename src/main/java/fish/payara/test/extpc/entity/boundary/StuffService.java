/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.test.extpc.entity.boundary;

import fish.payara.test.extpc.entity.Stuff;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author UI187816
 */
@Stateless
public class StuffService {
    @PersistenceContext
    EntityManager mgr;
    
    public Stuff create() {
        Stuff s = new Stuff();
        mgr.persist(s);
        return s;
    }
}
