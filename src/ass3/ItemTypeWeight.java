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
public class ItemTypeWeight extends ItemType
{
    protected double weight;
    
    public ItemTypeWeight(String newId, String newName, double newPrice, double newPopular, double newPopularMember, double newWeight)
    {
        super(newId, newName, newPrice, newPopular, newPopularMember);
        
        weight = newWeight;
    }
    
    public ItemTypeWeight(ItemTypeWeight assign)
    {
        super(assign.id, assign.name, assign.price, assign.popular, assign.popularMember);
        
        weight = assign.weight;
    }
    
    @Override
    public String print()
    {
        return String.format("%-15s%-15.2f%-15.2f%-15.2f\n", name, price, weight, (price * weight));
    }
    
    @Override
    public String save()
    {
        return (id + ":" + name + ":" + price + ":" + popular);
    }
    
    @Override
    public double total()
    {
        return price * weight;
    }
    
    @Override
    public double getAmount()
    {
        return weight;
    }
    
    @Override
    public void setAmount(double newAmount)
    {
        weight = newAmount;
    }
    
    @Override
    public double getValue()
    {
        return weight * price;
    }
}
