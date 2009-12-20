/*
 * Copyright (C) 2003-2009 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.crsh.shell;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.Script;

import java.util.HashMap;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ScriptCommand implements ShellCommand {

  /** . */
  private final Script script;

  public ScriptCommand(Script script) {
    this.script = script;
  }

  public Object execute(CommandContext context, String[] args) throws ScriptException {

    // Copy the current binding
    Binding binding = new Binding(new HashMap<String, Object>(context));

    // Set the args on the script
    binding.setProperty("args", args);

    //
    script.setBinding(binding);

    //
    Object res = script.run();

    // Evaluate the closure
    if (res instanceof Closure) {
      Closure closure = (Closure)res;
      res = closure.call(args);
    }

    //
    return res;
  }


}
