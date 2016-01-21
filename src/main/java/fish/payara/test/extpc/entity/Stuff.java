/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.test.extpc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author UI187816
 */
@Entity
public class Stuff {
   @Id
   @GeneratedValue
   private long id; 

    public long getId() {
        return id;
    }
   
   
}
