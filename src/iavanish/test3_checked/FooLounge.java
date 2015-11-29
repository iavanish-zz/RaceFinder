/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iavanish.test3_checked;
/**
 *
 * submitted by a student as an assignment in OOPD course and got full marks
 */
public class FooLounge {

    static Kitchen kitchen;
    static Cook[] cook=new Cook[4];
    
    private void initialize() throws InterruptedException {

        kitchen=new Kitchen();
        System.out.println("1. Kitchen stock at the beginning:\n\tQuantity of Ingredient 1:\t"+kitchen.store.amount_ingredient_1+"\n\tQuantity of Ingredient 2:\t"+kitchen.store.amount_ingredient_2);
        ServiceStaff staff1=new ServiceStaff(kitchen);
        ServiceStaff staff2=new ServiceStaff(kitchen);
        Thread[] t=new Thread[6];
        for(int i=0;i<4;i++)
        {
            cook[i]=new Cook(kitchen);
            t[i]=new Thread(cook[i]);
            t[i].start();
        }
        t[4]=new Thread(staff1);
        t[5]=new Thread(staff2);
        t[4].start();
        t[5].start();
        for(int i=0;i<4;i++)
            t[i].join();
        t[4].join();
        t[5].join();
        for(int i=0;i<4;i++)
        {
            System.out.println("2(i)Recipie details of cook "+(i+1));
            cook[i].printData();
        }
        staff1.printData();
        staff2.printData();
    }
    
    public static void main(String[] args) throws InterruptedException
    {
    	FooLounge obj = new FooLounge();
    	obj.initialize();
    }
}
