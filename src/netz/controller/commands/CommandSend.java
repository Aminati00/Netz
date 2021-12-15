/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netz.controller.commands;

import netz.model.Transmitter;
import netz.view.ChatView;

/**
 *
 * @author 143sc
 */
public class CommandSend implements CommandInterface
{

  private ChatView view;
  private Transmitter model;
  private String text;

  public CommandSend(ChatView view, Transmitter model)
  {
    this.view = view;
    this.model = model;
  }

  @Override
  public void execute()
  {
    text = view.getTfNachricht().getText();
    model.send(text);
    view.getTfNachricht().setText("");
    view.getTaTextAusgabe().append("Ich: " + text +"\n");
  }
}
