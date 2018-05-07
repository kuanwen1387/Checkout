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
public class ItemTypeBarcode extends ItemType
{
    protected String barcode;
    protected int count;
    
    public ItemTypeBarcode(String newId, String newName, double newPrice, double newPopular, double newPopularMember, String newBarcode, int newCount)
    {
        super(newId, newName, newPrice, newPopular, newPopularMember);
        
        barcode = newBarcode;
        count = newCount;
    }
    
    public ItemTypeBarcode(ItemTypeBarcode assign)
    {
        super(assign.id, assign.name, assign.price, assign.popular, assign.popularMember);
        
        barcode = assign.barcode;
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
        return (id + ":" + name + ":" + price + ":" + popular + ":" + barcode);
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
