/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iavanish.test3;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * submitted by a student as an assignment in OOPD course and got full marks
 */
public class ServiceStaff implements Runnable{
    Kitchen k;
    int temp;
    float wait_counter;
    float wait_storage;
    public ServiceStaff(Kitchen ki) 
    {
        k=new Kitchen();
        k=ki;
        temp=0;
        wait_counter=0;
        wait_storage=0;
    }
    public void run() 
    {
        System.out.println("Status of service staff "+((int)Thread.currentThread().getName().charAt(7)-51));
        while(FooLounge.cook[0].finished==false||FooLounge.cook[1].finished==false||FooLounge.cook[2].finished==false||FooLounge.cook[3].finished==false)
        {
            serviceKitchen();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
            Logger.getLogger(FooLounge.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public synchronized void serviceKitchen()
    {
        wait_counter+=5;
        wait_storage+=5;
        wait_counter+=Math.random();
        wait_storage+=Math.random();
        if(temp==0)
        {
            replenishStorage();
            temp=1;
        }
        else
        {
            cleanTable();
            temp=0;
        }
    }
    public synchronized void replenishStorage()
    {
        long waitStart = System.currentTimeMillis();
        if(k.store.occupied==false)
        {
            k.store.occupied=true;
            wait_storage+=5;
            if(k.store.amount_ingredient_1<2)
            {
                k.store.amount_ingredient_1+=5;
                
            }
            if(k.store.amount_ingredient_2<2)
            {
                k.store.amount_ingredient_2+=5;
            }
            k.store.occupied=false;
        }
        else
        wait_storage+=(System.currentTimeMillis()- waitStart)/1000;
        
       try
       {
            notifyAll();
       }catch(Exception e)
       {
           System.out.println(e);
       }
    }
    public synchronized void cleanTable()
    {
        long waitStart = System.currentTimeMillis();
        if(k.cooking.dirty==true)
        {
            k.cooking.dirty=false;
            notifyAll();
        } 
        else
        wait_counter+=(System.currentTimeMillis()- waitStart)/1000;
    }
    public void printData()
    {
        System.out.println("Total Wait time at storage:\t"+wait_storage);
        System.out.println("Total Wait time at counter:\t"+wait_counter);
    }
}
