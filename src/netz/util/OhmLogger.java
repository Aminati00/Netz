/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netz.util;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.logging.*;

/**
 *
 * @author le
 */
public class OhmLogger 
{
  private static Logger lg = null;
  private static Logger rootLogger = Logger.getLogger("");
  private static Properties props;
  static String level;
  private static String logpath;
  
  private OhmLogger()
  {
  }
  
  public static Logger getLogger()
  {
    if (lg == null)
    {
      lg = Logger.getLogger("OhmLogger");
      initLogger();
    }
    return lg;
  } 
  
  private static void getProperties()
  {
    props = new Properties();
    InputStream is = OhmLogger.class.getResourceAsStream("logger.properties");
    try
    {
      props.load(is);
    }
    catch (IOException ex)
    {
      System.err.print(ex.toString());
    }

    level = props.getProperty("LOG_LEVEL");
    logpath = props.getProperty("LOG_DATEI");
  }
  
  private static void initLogger()
  {  
    Handler[] allHandlers = rootLogger.getHandlers();
    
    rootLogger.removeHandler(allHandlers[0]);
    try
    {
      FileHandler fh = new FileHandler();
      rootLogger.addHandler(fh);
    }
    catch (IOException ex)
    {
      System.err.print(ex.toString());
    }
    catch (SecurityException ex)
    {
      System.err.print(ex.toString());
    }
    
    getProperties();
    try
    {
      FileHandler fh = new FileHandler(logpath);
      fh.setFormatter(new OhmFormatter());
      lg.addHandler(fh);
      ConsoleHandler ch = new ConsoleHandler();
      ch.setFormatter(new OhmFormatter());
      lg.addHandler(ch);
    }
    catch (IOException ex)
    {
      System.err.print(ex.toString());
    }
    catch (SecurityException ex)
    {
      System.err.print(ex.toString());
    }
  }

}

class OhmFormatter extends SimpleFormatter
{
  @Override
  public String format(LogRecord record)
  {
    String logLine = "| ";
    LocalDateTime ldt = LocalDateTime.now();
    logLine += ldt.toString();
    logLine += " | " + record.getLevel() + " | " + record.getSourceClassName() + " | " + record.getMessage() + " | ";
    logLine += "\n";
    return logLine;
  }
}
