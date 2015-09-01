/*
 * Copyright (c) 2014, Majenko Technologies
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 *
 * * Neither the name of Majenko Technologies nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.uecide;

//import gnu.io.*;
import jssc.*;

import java.io.*;
import java.util.*;

import java.lang.reflect.Method;

import java.awt.*;
import javax.swing.*;

public class Serial {
    static ArrayList<String> extraPorts = new ArrayList<String>();
    static String[] portList;
    static HashMap<String, SerialPort> serialPorts = new HashMap<String, SerialPort>();

    public static SerialPort requestPort(String name) {

        SerialPort port = new SerialPort(name);

        if(port == null) {
            JOptionPane.showMessageDialog(new Frame(), "The port could not be found.\nCheck you have the right port\nselected in the Hardware menu.", "Port not found", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {

            if(port.isOpened()) {
                port.purgePort(1);
                port.purgePort(2);
                port.closePort();
            }

            try {
                Thread.sleep(100); // Arduino has this, so I guess we should too.
            } catch(Exception e) {
                e.printStackTrace();
            }

            port.openPort();

            if(!port.isOpened()) {
                JOptionPane.showMessageDialog(new Frame(), "The port could not be opened.\nCheck you have the right port\nselected in the Hardware menu.", "Port didn't open", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            return port;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void closePort(SerialPort p) {

        if(p == null)
            return;

        if(!p.isOpened())
            return;

        try {
            p.purgePort(1);
            p.purgePort(2);
            p.closePort();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static SerialPort requestPort(String name, int baudRate) {
        SerialPort nsp = requestPort(name);

        if(nsp == null) {
            return null;
        }

        try {
            if(nsp.setParams(baudRate, 8, 1, 0)) {
                return nsp;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
