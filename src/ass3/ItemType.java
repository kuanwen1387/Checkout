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
public class ItemType implements Comparable<ItemType>
{
    protected String id;
    protected String name;
    protected double price;
    protected double popular;
    protected double popularMember;
    
    public ItemType(ItemType assign)
    {
        id = assign.id;
        name = assign.name;
        price = assign.price;
        popular = assign.popular;
        popularMember = assign.popularMember;
    }
    
    public ItemType(String newId, String newName, double newPrice, double newPopular, double newPopularMember)
    {
        id = newId;
        name = newName;
        price = newPrice;
        popular = newPopular;
        popularMember = newPopularMember;
    }
    
    public String print()
    {
        return (name + ":" + popularMember);
    }
    
    public String save()
    {
        return "";
    }
    
    public String getName()
    {
        return name;
    }
    
    public double getPopular()
    {
        return popular;
    }
    
    public double getPopularMember()
    {
        return popularMember;
    }
    
    public double total()
    {
        return 0;
    }
    
    public double getAmount()
    {
        return 0;
    }
    
    public void setAmount(double newAmount)
    {
        
    }
    
    public double getValue()
    {
        return 0;
    }
    
    @Override
    public int compareTo( final ItemType compare)
    {
        return name.compareTo(compare.name);
    }
}
