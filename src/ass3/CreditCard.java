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
public class CreditCard
{
    protected String num;
    protected double balance;
    
    public CreditCard(String newNum, double newBalance)
    {
        num = newNum;
        balance = newBalance;
    }
}
