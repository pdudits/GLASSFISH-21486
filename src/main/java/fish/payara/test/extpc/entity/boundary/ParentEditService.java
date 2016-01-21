/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.test.extpc.entity.boundary;

import fish.payara.test.extpc.entity.Child;
import fish.payara.test.extpc.entity.Parent;
import fish.payara.test.extpc.entity.Stuff;
import fish.payara.test.extpc.entity.StuffAttribute;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author UI187816
 */
@Stateful
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ParentEditService {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    EntityManager mgr;

    @EJB
    StuffService stuff;

    private Parent parent;

    private List<Child> newChildren;

    public void setParent(long parentId) {
        this.parent = mgr.find(Parent.class, parentId);
        newChildren = new ArrayList<>();
        assertManaged();
        findAttributes(parent.getStuff());
        assertManaged();
    }

    public Child addChild() {
        Child child = new Child(stuff.create(), this.parent);
        newChildren.add(child);
        parent.addChild(child);
        return child;
    }

    public int childrenCount() {
        return newChildren == null ? 0 : newChildren.size();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void save() {
        for (Child child : newChildren) {
            mgr.persist(child);
        }
    }

    /**
     * The find method (provided it is invoked without a lock or invoked with
     * LockModeType.NONE) and the getReference method are not required to be
     * invoked within a transaction. If an entity manager with
     * transaction-scoped persistence context is in use, the resulting entities
     * will be detached; if an entity manager with an extended persistence
     * context is used, they will be managed.
     * @see JPA 2.1 page 79
     */
    private void assertManaged() {
        if (!mgr.contains(parent)) {
            throw new IllegalStateException("Violation of JPA 2.1 §3.1.1. Entity in Extended PC obtained via find is not managed!");
        }
    }

    private void findAttributes(Stuff s) {
        mgr.createNamedQuery("StuffAttribute.byStuffId", StuffAttribute.class).setParameter("id", s.getId()).getResultList();
    }
}