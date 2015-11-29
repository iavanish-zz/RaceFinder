/*
 * submitted by a student as an assignment in OOPD course and got full marks
 * 
 */
package iavanish.test3_checked;
public class Cook implements Runnable{
    Recipie r;
    Kitchen k;
    boolean finished;
    float wait_store;
    float wait_counter;
    Cook(Kitchen ki)
    {
        r=new Recipie();
        k=new Kitchen();
        k=ki;
        k.store.amount_ingredient_1+=2;
        k.store.amount_ingredient_2+=2;
        finished=false;
        wait_counter=0;
        wait_store=(float)Math.random();
    }
    public synchronized void preparingDish()
    {
        try
        {
            long waitStart = System.currentTimeMillis();
            if(k.cooking.dirty==false)
                k.cooking.dirty=true;
            finished=true;
            wait_counter+=(System.currentTimeMillis()- waitStart)/1000;
            wait_counter+=Math.random();
        }catch(Exception e)
        {
            System.err.println(e);
        }
    }
    public synchronized void goStore()
    {
        try{
        long waitStart = System.currentTimeMillis();
        if(k.store.occupied==true)
            wait();
        wait_store+=(System.currentTimeMillis()- waitStart)/1000;
        waitStart = System.currentTimeMillis();
        if(k.store.amount_ingredient_1<r.ingredient_amount_1||k.store.amount_ingredient_2<r.ingredient_amount_2)
            wait();
        wait_store+=(System.currentTimeMillis()- waitStart)/1000;
        k.store.amount_ingredient_1-=r.ingredient_amount_1;
        k.store.amount_ingredient_2-=r.ingredient_amount_2;
        System.out.println("Amount of ingredient 1 left after consumption:\t"+k.store.amount_ingredient_1);
        System.out.println("Amount of ingredient 2 left after consumption:\t"+k.store.amount_ingredient_2);
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }
    @Override
    public void run()
    {
        goStore();
        preparingDish();
        r.time=wait_counter+wait_store+(float)Math.random();
    }
    public void printData()
    {
        System.out.println("\t(a) Quantity of ingredient 1 required:\t"+r.ingredient_amount_1);
        System.out.println("\t(b) Quantity of ingredient 2 required:\t"+r.ingredient_amount_2);
        System.out.println("2(ii)Preparation time required:\t"+r.time);
        System.out.println("2(iii)Waiting time at storage:\t"+wait_store);
        System.out.println("2(iv)Waiting time at cooking counter:\t"+wait_counter);
    }
}