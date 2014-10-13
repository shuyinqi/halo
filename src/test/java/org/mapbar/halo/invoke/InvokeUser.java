package org.mapbar.halo.invoke;

import java.lang.reflect.Field;

public class InvokeUser extends ParameterBase{
    String userName ="aaaa";
    String passWorld = "bb";
    public String getUserName()
    {
        return userName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    public String getPassWorld()
    {
        return passWorld;
    }
    public void setPassWorld(String passWorld)
    {
        this.passWorld = passWorld;
    }
    
    public static void main(String[] args) throws Exception
    {
    	InvokeUser u = new InvokeUser();
        Field field = u.getClass().getDeclaredField("passWorld");
        field.setAccessible(true);
        field.set(u, "1");
        System.out.println(u.getPassWorld());
//        System.out.println(u.getClassInfo(.toString());
        
    }
}