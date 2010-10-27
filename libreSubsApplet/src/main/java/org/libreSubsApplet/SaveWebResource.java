package org.libreSubsApplet;
import java.io.InputStream;
import java.net.URL;

import javax.swing.text.html.parser.DTD;

import org.apache.commons.io.IOUtils;

public class SaveWebResource {

  public static void main(final String[] args) {

    try {
      final URL u = new URL("http://www.java2s.com");
      final InputStream in = u.openStream();
      final DTD html = DTD.getDTD("html");
      System.out.println(html.getName());
      final String string = IOUtils.toString(in);
      System.out.println(string);
      in.close();
    } catch (final Exception e) {
      System.err.println("Usage: java PageSaver url local_file");
    }

  }

}