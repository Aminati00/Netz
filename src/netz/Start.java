/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netz;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import netz.controller.CommandController;
import netz.controller.ReceiveAdapter;
import netz.controller.commands.CommandInvoker;
import netz.model.Transmitter;
import netz.view.ChatView;

/**
 *
 * @author le
 */
public class Start 
{
  public Start()
  {
    ChatView view = new ChatView();
    Transmitter model = new Transmitter();
    CommandInvoker invoker = new CommandInvoker();
    CommandController commandctrl = new CommandController(view, model, invoker);
    commandctrl.registerCommands();
    commandctrl.registerEvents();
    ReceiveAdapter adapter = new ReceiveAdapter(model, view);
    adapter.subscribe();

    view.setVisible(true);
  }

  public static void main(String[] args)
  {
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception ex)
    {
      JOptionPane.showMessageDialog(null, ex.toString());
    }

    new Start();
  }
}
  
  

