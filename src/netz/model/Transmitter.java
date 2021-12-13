/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netz.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import netz.util.OhmLogger;

/**
 *
 * @author Danie
 */
public class Transmitter implements Runnable
{
  private static Logger lg = OhmLogger.getLogger();
  
  private ExecutorService eService;
  private SubmissionPublisher<String> iPublisher;
  
  private AtomicBoolean isServer;
  
  private BufferedReader reader;
  private PrintWriter writer;
  private Socket s;
  
  final static int PORT = 35000;
  final static String IPADRESSE = "127.0.0.1";
  private String msg = "";
  
  private InputStream is;
  private InputStreamReader isr;
  private OutputStream os;
  
  public Transmitter()
  {
    iPublisher = new SubmissionPublisher<>();
    eService = Executors.newSingleThreadExecutor();
    isServer = new AtomicBoolean(false);
  }

  public void addValueSubscription(Flow.Subscriber<String> subscriber)
  {
    iPublisher.subscribe(subscriber);
  }
  
    public void setState(boolean state)
  {
    isServer.set(state);
    eService.submit(this);
  }
    
  public void send(String text)
  {
    //Fehlermeldung wenn nicht verbunden
//    if(connected)
//    {
      this.writer.println(text);
      this.writer.flush();
//    }
  }
    
  
  @Override
  public void run()
  {
    if (isServer.get())
    {
      try //ich bin server
      {
        ServerSocket ss = new ServerSocket(PORT);
        lg.info("Server: Warte auf Verbindung ...");
        this.s = ss.accept();  
        lg.info("Server: Verbindung akzeptiert");
      }
      catch (IOException ex)
      {
        System.err.println(ex.toString());
      }
    }
    else
    {
      try //ich bin client
      {
        this.s = new Socket(IPADRESSE, PORT);  
        lg.info("Client: Verbindung aufgebaut");
      }
      catch (IOException ex)
      {
        System.err.println(ex.toString());
      }
    }
    
    try
    {
        this.is = s.getInputStream();
        this.isr = new InputStreamReader(is);
        this.reader = new BufferedReader(isr);
        
        this.os = s.getOutputStream();
        this.writer = new PrintWriter(os);
    }
    catch(IOException ex)
    {
      System.err.println(ex.toString());
    }
    
    while(true)
    {
      try
      {
        msg = this.reader.readLine();
      }
      catch (IOException ex)
      {
        System.err.println(ex.toString());
      }
      iPublisher.submit(msg);
      msg = "";
    }
  }
}
