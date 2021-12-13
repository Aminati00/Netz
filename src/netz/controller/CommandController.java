/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netz.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import netz.controller.commands.CommandConnect;
import netz.controller.commands.CommandInvoker;
import netz.controller.commands.CommandSend;
import netz.model.Transmitter;
import netz.view.ChatView;

/**
 *
 * @author Danie
 */
public class CommandController implements ActionListener
{
  private ChatView view;
  private Transmitter model;
  private CommandInvoker invoker;
  private boolean isServer = true;
  private boolean notServer = false;

  /**
   * Constructor for CommandController, sets view, model and invoker to given
   * parameters
   *
   * @param view
   * @param model
   * @param invoker
   */
  public CommandController(ChatView view, Transmitter model, CommandInvoker invoker)
  {
    this.view = view;
    this.model = model;
    this.invoker = invoker;
  }

  /**
   * Registers the events for all UI elements
   */
  public void registerEvents()
  {
    view.getBtnClient().addActionListener(this);
    view.getBtnServer().addActionListener(this);
    view.getBtnSenden().addActionListener(this);
  }
  /**
   * Registers commands for UI elements
   */
  public void registerCommands()
  {
    CommandSend cmdSend = new CommandSend(view, model);
    CommandConnect cmdConnectS = new CommandConnect(view, model, isServer);
    CommandConnect cmdConnectC = new CommandConnect(view, model, notServer);

    invoker.addCommand(view.getBtnSenden(), cmdSend);
    invoker.addCommand(view.getBtnServer(), cmdConnectS); 
    invoker.addCommand(view.getBtnClient(), cmdConnectC);
  }

  /**
   * 
   *
   * @param evt
   */
  @Override
  public void actionPerformed(ActionEvent evt)
  {
    Object key = evt.getSource();
    invoker.executeCommand(key);
    if((key == view.getBtnServer()) || (key == view.getBtnClient()) )
    { //Buttons nicht mehr funktionieren lassen sobald eine geklickt wurde
      view.getBtnServer().setEnabled(false);
      view.getBtnClient().setEnabled(false);
    }
  }
}
