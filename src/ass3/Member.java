/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ass3;

import java.util.*;

/**
 *
 * @author kwn961
 */
public class Member
{
    protected String lastName;
    protected String firstName;
    protected String customerId;
    protected int points;
    protected ArrayList<ItemType> memberItems = new ArrayList<ItemType>();
    
    public Member()
    {
        lastName = "";
        firstName = "";
        customerId = "";
        points = 0;
    }
    
    public Member(String newLastName, String newFirstName, String newCustomerId, int newPoints)
    {
        lastName = newLastName;
        firstName = newFirstName;
        customerId = newCustomerId;
        points = newPoints;
    }
    
    public Member(Member assign)
    {
        lastName = assign.lastName;
        firstName = assign.firstName;
        customerId = assign.customerId;
        points = assign.points;
        
        for (ItemType insert : assign.memberItems)
        {
            memberItems.add(new ItemType(insert));
        }
    }
    
    public String print()
    {
        return (lastName + ":" + firstName + ":" + customerId + ":" + points);
    }
}
