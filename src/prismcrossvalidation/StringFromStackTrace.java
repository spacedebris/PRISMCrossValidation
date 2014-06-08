/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prismcrossvalidation;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author si
 */
public class StringFromStackTrace {
    public static String exceptionToString(Throwable ex){
        if (ex ==null){
            return "";
        }
        StringWriter str = new StringWriter();
        PrintWriter writer = new PrintWriter(str);
        try{
            ex.printStackTrace(writer);
            return str.getBuffer().toString();
        }finally{
            try{
                str.close();
                writer.close();
            }catch (IOException e){
                //ignore
            }
        }
    }
}
