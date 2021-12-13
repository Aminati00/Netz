/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netz.controller;

import java.util.concurrent.Flow;
import java.util.logging.Logger;
import netz.model.Transmitter;
import netz.util.OhmLogger;
import netz.view.ChatView;

/**
 *
 * @author Danie
 */
public class ReceiveAdapter implements Flow.Subscriber<String> //war Integer/Werte aber wir wollen doch nur Strings übermitteln
{
  private ChatView view;
  private Transmitter model;
  private Flow.Subscription subscription;
  private static Logger lg = OhmLogger.getLogger();
  
  /**
   * Constructor des Receiveadapter
   * @param model : Transmitter Objekt
   * @param view  : ChatView Objekt
   */
  public ReceiveAdapter(Transmitter model, ChatView view)
  {
    this.view = view;
    this.model = model;
  }
  
  /**
   * Methode um beim model zu subscriben
   */
  public void subscribe()
  {
    model.addValueSubscription(this);
  }
  
  
  /**
   * 
   * Setzt die subscription und fragt an beim publisher
   * @param subscription : Subscription Objekt
   */
  @Override
  public void onSubscribe(Flow.Subscription subscription)
  {
    lg.info("RA Subscribe");
    this.subscription = subscription;
    this.subscription.request(1);
  }

  /**
   * Setzt die Werte in den Labels der View und fragt beim publisher an
   * @param text : String 
   */
  @Override
  public void onNext(String text)
  {
    if(text != "")
    {
      view.getTaTextAusgabe().append("Gesprächspartner: " + text + "\n");
    }
    subscription.request(1);
  }

  @Override
  public void onError(Throwable throwable)
  {
  }

  @Override
  public void onComplete()
  {
  }

  
  
}
  

