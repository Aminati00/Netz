/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netz.controller.commands;

import java.util.HashMap;

/**
 * Invoker for command design pattern
 */
public class CommandInvoker
{

  private HashMap<Object, CommandInterface> commands;

  /**
   * Constructor which creates commands HashMap
   */
  public CommandInvoker()
  {
    commands = new HashMap<>();
  }

  /**
   * Maps Object to command
   * @param key
   * @param value
   */
  public void addCommand(Object key, CommandInterface value)
  {
    commands.put(key, value);
  }

  /**
   * Calls execute for command of the key Object
   *
   * @param key
   */
  public void executeCommand(Object key)
  {
    commands.get(key).execute();
  }
}
