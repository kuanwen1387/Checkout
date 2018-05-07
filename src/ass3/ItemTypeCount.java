/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ass3;

/**
 *
 * @author kwn961
 */
public class ItemTypeCount extends ItemType
{
    protected int count;
    
    public ItemTypeCount(String newId, String newName, double newPrice, double newPopular, double newPopularMember, int newCount)
    {
        super(newId, newName, newPrice, newPopular, newPopularMember);
        
        count = newCount;
    }
    
    public ItemTypeCount(ItemTypeCount assign)
    {
        super(assign.id, assign.name, assign.price, assign.popular, assign.popularMember);
        
        count = assign.count;
    }
    
    @Override
    public String print()
    {
        return String.format("%-15s%-15.2f%-15d%-15.2f\n", name, price, count, (price * count));
    }
    
    @Override
    public String save()
    {
        return (id + ":" + name + ":" + price + ":" + popular);
    }
    
    @Override
    public double total()
    {
        return price * count;
    }
    
    @Override
    public double getAmount()
    {
        return count;
    }
    
    @Override
    public void setAmount(double newAmount)
    {
        count = (int) newAmount;
    }
    
    @Override
    public double getValue()
    {
        return count * price;
    }
}
