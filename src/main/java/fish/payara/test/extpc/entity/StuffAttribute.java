/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.test.extpc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 *
 * @author UI187816
 */
@NamedQuery(name = "StuffAttribute.byStuffId", query = "select sa from StuffAttribute sa where sa.stuff.id = :id")
@Entity
public class StuffAttribute {
    @GeneratedValue
    @Id
    private long id;
    
    @ManyToOne
    Stuff stuff;
}