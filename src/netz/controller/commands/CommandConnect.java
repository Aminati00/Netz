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
 * @author Danie
 */
public class CommandConnect implements CommandInterface
{

  private ChatView view;
  private Transmitter model;
  private boolean isServer = true;

  public CommandConnect(ChatView view, Transmitter model, Boolean isServer)
  {
    this.view = view;
    this.model = model;
    this.isServer = isServer;
  }

  @Override
  public void execute()
  {
    model.setState(this.isServer);
  }
}
