/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ass3;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author kwn961
 */
public class ItemTable
{
    //ArrayList for various ItemType
    protected ArrayList<ItemTypeBarcode> itemTableBarcode = new ArrayList<ItemTypeBarcode>();
    protected ArrayList<ItemTypeWeight> itemTableWeight = new ArrayList<ItemTypeWeight>();
    protected ArrayList<ItemTypeCount> itemTableCount = new ArrayList<ItemTypeCount>();
    protected ArrayList<ItemType> purchases = new ArrayList<ItemType>();
    protected ArrayList<ItemType> purchasesFinal = new ArrayList<ItemType>();
    protected ArrayList<Member> memberList = new ArrayList<Member>();
    protected int itemCount;
    protected double total;
    protected int transactionId;
    
    //ItemType objects for data input
    ItemTypeBarcode itemTypeBarcode = new ItemTypeBarcode(" ", " ", 0, 0, 0, " ", 0);
    ItemTypeWeight itemTypeWeight = new ItemTypeWeight(" ", " ", 0, 0, 0, 0);
    ItemTypeCount itemTypeCount = new ItemTypeCount(" ", " ", 0, 0, 0, 0);
    
    //Simulate member and credit card payment
    protected boolean member;
    protected String customerId;
    protected int customerIndex;
    protected int discount;
    protected int discountFactor;
    protected ArrayList<CreditCard> creditCard = new ArrayList<CreditCard>();
    
    //Admin password
    protected String password;
    
    //Initialize ItemTable
    public ItemTable()
    {
        itemCount = 0;
        total = 0;
        
        //Initialize password
        password = "csit121";
        
        //Add credit card details
        creditCard.add(new CreditCard("777", 10));
        creditCard.add(new CreditCard("888", 1000));
        
        //Member
        member = false;
        customerId = "";
        customerIndex = 0;
        discount = 0;
        discountFactor = 0;
        
        try
        {
            loadItems();
            loadMembers();
        }
        
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
    
    public void loadItems()
    {
        String tempLine = null;
        String data[] = null;
        int itemType = 0;
        
        try
        {
            BufferedReader read = new BufferedReader(new FileReader("/home/kwn961/NetBeansProjects/Ass3/src/ass3/data.txt"));
            
            //Get transaction number
            tempLine = read.readLine();
            transactionId = Integer.parseInt(tempLine);
            
            while ((tempLine = read.readLine()) != null)
            {
                //Switch to input ItemTypeBarcode
                if (tempLine.equals("Barcode"))
                {
                    itemType = 0;
                }

                //Switch to input ItemTypeWeight
                else if (tempLine.equals("Weight"))
                {
                    itemType = 1;
                }

                //Switch to input ItemTypeCount
                else if (tempLine.equals("Count"))
                {
                    itemType = 2;
                }

                //Input ItemType
                else
                {
                    //Input ItemTypeBarcode
                    if (itemType == 0)
                    {
                        data = new String(tempLine).split(":");

                        itemTableBarcode.add(new ItemTypeBarcode(data[0], data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]), 0, data[4], 0));
                    }

                    //Input ItemTypeWeight
                    else if (itemType == 1)
                    {
                        data = new String(tempLine).split(":");

                        itemTableWeight.add(new ItemTypeWeight(data[0], data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]), 0, 0));
                    }

                    //Input ItemTypeCount
                    else
                    {
                        data = new String(tempLine).split(":");

                        itemTableCount.add(new ItemTypeCount(data[0], data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]), 0, 0));
                    }
                }
            }
            
            read.close();
        }
        
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
    
    public void loadMembers()
    {
        String tempLine = null;
        String data[] = null;
        Member tempMember = new Member();
        
        try
        {
            BufferedReader read = new BufferedReader(new FileReader("/home/kwn961/NetBeansProjects/Ass3/src/ass3/member1.txt"));
            
            while ((tempLine = read.readLine()) != null)
            {
                if (tempLine.equals("customer"))
                {
                    for (ItemTypeWeight insert : itemTableWeight)
                    {
                        tempMember.memberItems.add(new ItemTypeWeight(insert));
                    }

                    for (ItemTypeCount insert : itemTableCount)
                    {
                        tempMember.memberItems.add(new ItemTypeCount(insert));
                    }
                    
                    tempLine = read.readLine();
                    data = new String(tempLine).split(":");
                    tempMember.lastName = data[0];
                    tempMember.firstName = data[1];
                    tempMember.customerId = data[2];
                    tempMember.points = Integer.parseInt(data[3]);
                }
                
                if (tempLine.equals("end"))
                {
                    Collections.sort(tempMember.memberItems, Comparator.comparing(ItemType::getPopularMember).reversed());
                    memberList.add(new Member(tempMember));
                    tempMember.memberItems.clear();
                }
                
                else
                {
                    data = new String(tempLine).split(":");
                    
                    for (ItemType find : tempMember.memberItems)
                    {
                        if (find.name.equals(data[0]))
                            find.popularMember = Double.parseDouble(data[1]);
                    }
                }
            }
            
            read.close();
        }
        
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
    
    //Validate barcode
    public boolean searchBarcode(String barcode)
    {
        boolean search = false;
        
        for (ItemTypeBarcode search1 : itemTableBarcode)
        {
            if (search1.barcode.equals(barcode))
                search = true;
        }
        
        return search;
    }
    
    //Add barcode item to bill
    public void addBarcode(String barcode, int newCount)
    {
        for (ItemTypeBarcode search : itemTableBarcode)
        {
            if (search.barcode.equals(barcode))
            {
                search.count += newCount;
                search.popular += newCount;
                itemCount += newCount;
                
                ItemTypeBarcode tempBarcode = new ItemTypeBarcode(search);
                tempBarcode.count = newCount;
                purchases.add(new ItemTypeBarcode(tempBarcode));
            }
        }
    }
    
    //Add weighted item to bill
    public void addWeight(String name, double newWeight)
    {
        for (ItemTypeWeight search : itemTableWeight)
        {
            if (search.name.equals(name))
            {
                if (search.weight == 0)
                {
                    itemCount++;
                }
                search.weight += newWeight;
                search.popular += newWeight;
                
                ItemTypeWeight tempWeight = new ItemTypeWeight(search);
                tempWeight.weight = newWeight;
                purchases.add(new ItemTypeWeight(tempWeight));
            }
        }
    }
    
    //Add counted item to bill
    public void addCount(String name, int newCount)
    {
        for (ItemTypeCount search : itemTableCount)
        {
            if (search.name.equals(name))
            {
                search.count += newCount;
                search.popular += newCount;
                itemCount += newCount;
                
                ItemTypeCount tempCount = new ItemTypeCount(search);
                tempCount.count = newCount;
                purchases.add(new ItemTypeCount(tempCount));
            }
        }
    }
    
    //Add item for fav
    public void addFav(String name, double newCount)
    {
        boolean search = false;
        
        //Search if item is counted
        for (int index = 0; index < itemTableCount.size() && !search; index++)
        {
            if (itemTableCount.get(index).name.equals(name))
            {
                addCount(name, (int) newCount);
                search = true;
            }
        }
        
        //Search if item is weighted
        for (int index = 0; index < itemTableWeight.size() && !search; index++)
        {
            if (itemTableWeight.get(index).name.equals(name))
            {
                addWeight(name, newCount);
                search = true;
            }
        }
        
        //Add to member fav
        for (int index = 0; index < memberList.get(customerIndex).memberItems.size(); index++)
        {
            if (memberList.get(customerIndex).memberItems.get(index).name.equals(name))
                memberList.get(customerIndex).memberItems.get(index).popularMember += newCount;
        }
    }
    
    //Reset count/weight for new bill
    public void reset()
    {
        purchases.clear();
    }
    
    //Print bill after purchase
    public String printBill()
    {
        total = 0;
        String bill = "";
        
        if (purchases.size() == 0)
            return bill;
        
        bill += "Bill for " + itemCount + " items.\n";
        bill += String.format("%-15s%-15s%-15s%-15s\n", "Item", "Price", "Weight/Count", "$");
        
        for (ItemType item : purchases)
        {
            bill += item.print();
            total += item.total();
        }
        
        //Member
        if (member)
        {
            if (memberList.get(customerIndex).points >= 1000)
            {
                discountFactor = (int) total / 10;
                        
                if (discountFactor >= memberList.get(customerIndex).points / 1000)
                {
                    discountFactor = memberList.get(customerIndex).points / 1000;
                    discount = discountFactor * 10;
                }

                else
                {
                    discount = discountFactor * 10;
                }

                bill += String.format("%-45s%-15d\n", "Member Rewards", -discount);
                total -= discount;
            }
        }
        
        bill += String.format("\n%45s", " ").replace(' ', '*');
        bill += String.format("%-15s\n", "Total");
        bill += String.format("%-45s", " ");
        bill += String.format("%-15.2f\n", total);
        
        return bill;
    }
    
    public String printBillFinal()
    {
        total = 0;
        String bill = "";
        purchasesFinal.clear();
        
        if (purchases.size() == 0)
            return bill;
        
        bill += "Bill for " + itemCount + " items.\n";
        bill += String.format("%-15s%-15s%-15s%-15s\n", "Item", "Price", "Weight/Count", "$");
        
        for (ItemTypeBarcode itemBarcode : itemTableBarcode)
        {
            if (itemBarcode.count > 0)
            {
                purchasesFinal.add(itemBarcode);
            }
        }
        
        for (ItemTypeWeight itemWeight : itemTableWeight)
        {
            if (itemWeight.weight > 0)
            {
                purchasesFinal.add(itemWeight);
            }
        }
        
        for (ItemTypeCount itemCount : itemTableCount)
        {
            if (itemCount.count > 0)
            {
                purchasesFinal.add(itemCount);
            }
        }
        
        for (ItemType item : purchasesFinal)
        {
            bill += item.print();
            total += item.total();
        }
        
        //Member
        if (member)
        {
            if (memberList.get(customerIndex).points >= 1000)
            {
                discountFactor = (int) total / 10;
                        
                if (discountFactor >= memberList.get(customerIndex).points / 1000)
                {
                    discountFactor = memberList.get(customerIndex).points / 1000;
                    discount = discountFactor * 10;
                }

                else
                {
                    discount = discountFactor * 10;
                }

                bill += String.format("%-45s%-15d\n", "Member Rewards", -discount);
                total -= discount;
            }
        }
        
        bill += String.format("\n%45s", " ").replace(' ', '*');
        bill += String.format("%-15s\n", "Total");
        bill += String.format("%-45s", " ");
        bill += String.format("%-15.2f\n", total);
        
        //bill += itemTableWeight.get(0).name + "\n";
        
        return bill;
    }
    
    //Save popularity after exiting CheckOut
    public void saveData()
    {
        try
        {
            BufferedWriter write = new BufferedWriter(new FileWriter("/home/kwn961/NetBeansProjects/Ass3/src/ass3/data1.txt"));
            
            write.write(Integer.toString(transactionId));
            write.newLine();
            
            write.write("Barcode");
            write.newLine();
            for (ItemTypeBarcode saveBarcode : itemTableBarcode)
            {
                write.write(saveBarcode.save());
                write.newLine();
            }
                
            write.write("Weight");
            write.newLine();
            for (ItemTypeWeight saveWeight : itemTableWeight)
            {
                write.write(saveWeight.save());
                write.newLine();
            }

            write.write("Count");
            write.newLine();
            for (ItemTypeCount saveCount : itemTableCount)
            {
                write.write(saveCount.save());
                write.newLine();
            }
            
            write.close();
        }
        
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
    
    public void saveMembers()
    {
        try
        {
            BufferedWriter write = new BufferedWriter(new FileWriter("/home/kwn961/NetBeansProjects/Ass3/src/ass3/member1.txt"));
            
            for (Member saveMember : memberList)
            {
                write.write("customer");
                write.newLine();
                write.write(saveMember.print());
                write.newLine();
                
                Collections.sort(saveMember.memberItems, Comparator.comparing(ItemType::getPopularMember).reversed());
                
                for (int index = 0; index < 12; index++)
                {
                    write.write(saveMember.memberItems.get(index).print());
                    write.newLine();
                }
                
                write.write("end");
                write.newLine();
            }
            
            write.close();
        }
        
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
    
    public void saveLog()
    {
        try
        {
            BufferedWriter write = new BufferedWriter(new FileWriter("/home/kwn961/NetBeansProjects/Ass3/src/ass3/log.txt", true));
        
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        
            for (ItemType item : purchasesFinal)
            {
                write.write(Integer.toString(transactionId) + " " + date + " " + customerId + " " + item.name + " " + item.price + " " + item.getAmount());
                write.newLine();
            }
            
            write.close();
        }
        
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
    
    public String readLog()
    {
        try
        {
            BufferedReader read = new BufferedReader(new FileReader("/home/kwn961/NetBeansProjects/Ass3/src/ass3/log.txt"));
            
            String tempLine = null, log = String.format("%-15s%-15s%-15s%-15s%-15s%-15s\n", "TransactionID", "Date", "CustomerID", "Name", "Price", "Count/Weight"), data[];
            
            while ((tempLine = read.readLine()) != null)
            {
                data = tempLine.split(" ");
                log += String.format("%-15s%-15s%-15s%-15s%-15s%-15s\n", data[0], data[1], data[2], data[3], data[4], data[5]);
            }
            
            read.close();
            return log;
        }
        
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
        return "";
    }
    
    public String readLog(String itemName)
    {
        try
        {
            BufferedReader read = new BufferedReader(new FileReader("/home/kwn961/NetBeansProjects/Ass3/src/ass3/log.txt"));
            
            String tempLine = null, log = String.format("%-15s%-15s%-15s%-15s%-15s%-15s\n", "TransactionID", "Date", "CustomerID", "Name", "Price", "Count/Weight"), data[];
          
            while ((tempLine = read.readLine()) != null)
            {
                if (tempLine.toLowerCase().contains(itemName.toLowerCase()))
                {
                    data = tempLine.split(" ");
                    log += String.format("%-15s%-15s%-15s%-15s%-15s%-15s\n", data[0], data[1], data[2], data[3], data[4], data[5]);
                }
            }
            
            read.close();
            return log;
        }
        
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
        return "";
    }
    
    public void analytics()
    {
        purchases.clear();
        
        try
        {
            BufferedReader read = new BufferedReader(new FileReader("/home/kwn961/NetBeansProjects/Ass3/src/ass3/log.txt"));
            
            String tempLine = null, report = "", data[];
            boolean search = false;
            
            for (ItemTypeBarcode assign : itemTableBarcode)
                assign.count = 0;
            
            for (ItemTypeWeight assign : itemTableWeight)
                assign.weight = 0;
            
            for (ItemTypeCount assign : itemTableCount)
                assign.count = 0;
            
            while ((tempLine = read.readLine()) != null)
            {
                data = tempLine.split(" ");
                search = false;
                
                for (int index = 0; index < itemTableBarcode.size() && !search; index++)
                {
                    if (itemTableBarcode.get(index).name.equals(data[3]))
                    {
                        search = true;
                        
                        itemTableBarcode.get(index).count += (int) Double.parseDouble(data[5]);
                    }
                }
                
                for (int index = 0; index < itemTableWeight.size() && !search; index++)
                {
                    if (itemTableWeight.get(index).name.equals(data[3]))
                    {
                        search = true;
                        
                        itemTableWeight.get(index).weight += Double.parseDouble(data[5]);
                    }
                }
                
                for (int index = 0; index < itemTableCount.size() && !search; index++)
                {
                    if (itemTableCount.get(index).name.equals(data[3]))
                    {
                        search = true;
                        
                        itemTableCount.get(index).count += (int) Double.parseDouble(data[5]);
                    }
                }
            }
            
            for (ItemTypeBarcode assign : itemTableBarcode)
            {
                if (assign.count > 0)
                    purchases.add(new ItemTypeBarcode(assign));
            }
            
            for (ItemTypeWeight assign : itemTableWeight)
            {
                if (assign.weight > 0)
                    purchases.add(new ItemTypeWeight(assign));
            }
            
            for (ItemTypeCount assign : itemTableCount)
            {
                if (assign.count > 0)
                    purchases.add(new ItemTypeCount(assign));
            }
            
            read.close();
        }
        
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }
    
    public String analyticsAlphabetical()
    {
        String report = String.format("%-15s%-15s%-15s%-15s\n", "Name", "Price", "Count/Weight", "Total Value");
        
        Collections.sort(purchases);
        
        for (ItemType get : purchases)
        {
            report += get.print();
        }
        
        return report;
    }
    
    public String analyticsAmount()
    {
        String report = String.format("%-15s%-15s%-15s%-15s\n", "Name", "Price", "Count/Weight", "Total Value");
        
        Collections.sort(purchases, Comparator.comparing(ItemType::getAmount).reversed());
        
        for (ItemType get : purchases)
        {
            report += get.print();
        }
        
        return report;
    }
    
    public String analyticsValue()
    {
        String report = String.format("%-15s%-15s%-15s%-15s\n", "Name", "Price", "Count/Weight", "Total Value");
        
        Collections.sort(purchases, Comparator.comparing(ItemType::getValue).reversed());
        
        for (ItemType get : purchases)
        {
            report += get.print();
        }
        
        return report;
    }
}
