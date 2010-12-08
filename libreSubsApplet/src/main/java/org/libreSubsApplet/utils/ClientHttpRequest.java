package org.libreSubsApplet.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * <p>Title: Client HTTP Request class</p>
 * <p>Description: this class helps to send POST HTTP requests with various form data,
 * including files. Cookies can be added to be included in the request.</p>
 *
 * @author Vlad Patryshev
 * @version 1.0
 */
public class ClientHttpRequest {
  URLConnection connection;
  OutputStream os = null;
  Map<String,String> cookies = new HashMap<String,String>();

  protected void connect() throws IOException {
    if (os == null) os = connection.getOutputStream();
  }

  protected void write(final char c) throws IOException {
    connect();
    os.write(c);
  }

  protected void write(final String s) throws IOException {
    connect();
    os.write(s.getBytes());
  }

  protected void newline() throws IOException {
    connect();
    write("\r\n");
  }

  protected void writeln(final String s) throws IOException {
    connect();
    write(s);
    newline();
  }

  private static Random random = new Random();

  protected static String randomString() {
    return Long.toString(random.nextLong(), 36);
  }

  String boundary = "---------------------------" + randomString() + randomString() + randomString();

  private void boundary() throws IOException {
    write("--");
    write(boundary);
  }

  /**
   * Creates a new multipart POST HTTP request on a freshly opened URLConnection
   *
   * @param connection an already open URL connection
   * @throws IOException
   */
  public ClientHttpRequest(final URLConnection connection) throws IOException {
    this.connection = connection;
    connection.setDoOutput(true);
    connection.setRequestProperty("Content-Type",
                                  "multipart/form-data; boundary=" + boundary);
  }

  /**
   * Creates a new multipart POST HTTP request for a specified URL
   *
   * @param url the URL to send request to
   * @throws IOException
   */
  public ClientHttpRequest(final URL url) throws IOException {
    this(url.openConnection());
  }

  /**
   * Creates a new multipart POST HTTP request for a specified URL string
   *
   * @param urlString the string representation of the URL to send request to
   * @throws IOException
   */
  public ClientHttpRequest(final String urlString) throws IOException {
    this(new URL(urlString));
  }

  private void writeName(final String name) throws IOException {
    newline();
    write("Content-Disposition: form-data; name=\"");
    write(name);
    write('"');
  }

  /**
   * adds a string parameter to the request
   * @param name parameter name
   * @param value parameter value
   * @throws IOException
   */
  public void setParameter(final String name, final String value) throws IOException {
    boundary();
    writeName(name);
    newline(); newline();
    writeln(value);
  }

  private static void pipe(final InputStream in, final OutputStream out) throws IOException {
    byte[] buf = new byte[500000];
    int nread;
    int total = 0;
    synchronized (in) {
      while((nread = in.read(buf, 0, buf.length)) >= 0) {
        out.write(buf, 0, nread);
        total += nread;
      }
    }
    out.flush();
    buf = null;
  }

  /**
   * adds a file parameter to the request
   * @param name parameter name
   * @param filename the name of the file
   * @param is input stream to read the contents of the file from
   * @throws IOException
   */
  public void setParameter(final String name, final String filename, final InputStream is) throws IOException {
    boundary();
    writeName(name);
    write("; filename=\"");
    write(filename);
    write('"');
    newline();
    write("Content-Type: ");
    String type = URLConnection.guessContentTypeFromName(filename);
    if (type == null) type = "application/octet-stream";
    writeln(type);
    newline();
    pipe(is, os);
    newline();
  }

  /**
   * adds a file parameter to the request
   * @param name parameter name
   * @param file the file to upload
   * @throws IOException
   */
  public void setParameter(final String name, final File file) throws IOException {
    setParameter(name, file.getPath(), new FileInputStream(file));
  }

  /**
   * adds a parameter to the request; if the parameter is a File, the file is uploaded, otherwise the string value of the parameter is passed in the request
   * @param name parameter name
   * @param object parameter value, a File or anything else that can be stringified
   * @throws IOException
   */
  public void setParameter(final String name, final Object object) throws IOException {
    if (object instanceof File) {
      setParameter(name, (File) object);
    } else {
      setParameter(name, object.toString());
    }
  }

  /**
   * adds parameters to the request
   * @param parameters "name-to-value" map of parameters; if a value is a file, the file is uploaded, otherwise it is stringified and sent in the request
   * @throws IOException
   */
  public void setParameters(final Map<String,String> parameters) throws IOException {
    if (parameters == null) return;
    for (final Iterator<Entry<String, String>> i = parameters.entrySet().iterator(); i.hasNext();) {
      final Entry<String, String> entry = i.next();
      setParameter(entry.getKey().toString(), entry.getValue());
    }
  }

  /**
   * adds parameters to the request
   * @param parameters array of parameter names and values (parameters[2*i] is a name, parameters[2*i + 1] is a value); if a value is a file, the file is uploaded, otherwise it is stringified and sent in the request
   * @throws IOException
   */
  public void setParameters(final Object[] parameters) throws IOException {
    if (parameters == null) return;
    for (int i = 0; i < parameters.length - 1; i+=2) {
      setParameter(parameters[i].toString(), parameters[i+1]);
    }
  }

  /**
   * posts the requests to the server, with all the cookies and parameters that were added
   * @return input stream with the server response
   * @throws IOException
   */
  public InputStream post() throws IOException {
    boundary();
    writeln("--");
    os.close();
    return connection.getInputStream();
  }

  /**
   * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with parameters that are passed in the argument
   * @param parameters request parameters
   * @return input stream with the server response
   * @throws IOException
   * @see setParameters
   */
  public InputStream post(final Map<String,String> parameters) throws IOException {
    setParameters(parameters);
    return post();
  }

  /**
   * posts the requests to the server, with all the cookies and parameters that were added before (if any), and with parameters that are passed in the argument
   * @param parameters request parameters
   * @return input stream with the server response
   * @throws IOException
   * @see setParameters
   */
  public InputStream post(final Object[] parameters) throws IOException {
    setParameters(parameters);
    return post();
  }


  /**
   * post the POST request to the server, with the specified parameter
   * @param name parameter name
   * @param value parameter value
   * @return input stream with the server response
   * @throws IOException
   * @see setParameter
   */
  public InputStream post(final String name, final Object value) throws IOException {
    setParameter(name, value);
    return post();
  }

  /**
   * post the POST request to the server, with the specified parameters
   * @param name1 first parameter name
   * @param value1 first parameter value
   * @param name2 second parameter name
   * @param value2 second parameter value
   * @return input stream with the server response
   * @throws IOException
   * @see setParameter
   */
  public InputStream post(final String name1, final Object value1, final String name2, final Object value2) throws IOException {
    setParameter(name1, value1);
    return post(name2, value2);
  }

  /**
   * post the POST request to the server, with the specified parameters
   * @param name1 first parameter name
   * @param value1 first parameter value
   * @param name2 second parameter name
   * @param value2 second parameter value
   * @param name3 third parameter name
   * @param value3 third parameter value
   * @return input stream with the server response
   * @throws IOException
   * @see setParameter
   */
  public InputStream post(final String name1, final Object value1, final String name2, final Object value2, final String name3, final Object value3) throws IOException {
    setParameter(name1, value1);
    return post(name2, value2, name3, value3);
  }

  /**
   * post the POST request to the server, with the specified parameters
   * @param name1 first parameter name
   * @param value1 first parameter value
   * @param name2 second parameter name
   * @param value2 second parameter value
   * @param name3 third parameter name
   * @param value3 third parameter value
   * @param name4 fourth parameter name
   * @param value4 fourth parameter value
   * @return input stream with the server response
   * @throws IOException
   * @see setParameter
   */
  public InputStream post(final String name1, final Object value1, final String name2, final Object value2, final String name3, final Object value3, final String name4, final Object value4) throws IOException {
    setParameter(name1, value1);
    return post(name2, value2, name3, value3, name4, value4);
  }

  /**
   * posts a new request to specified URL, with parameters that are passed in the argument
   * @param parameters request parameters
   * @return input stream with the server response
   * @throws IOException
   * @see setParameters
   */
  public static InputStream post(final URL url, final Map<String,String> parameters) throws IOException {
    return new ClientHttpRequest(url).post(parameters);
  }

  /**
   * posts a new request to specified URL, with parameters that are passed in the argument
   * @param parameters request parameters
   * @return input stream with the server response
   * @throws IOException
   * @see setParameters
   */
  public static InputStream post(final URL url, final Object[] parameters) throws IOException {
    return new ClientHttpRequest(url).post(parameters);
  }

  /**
   * post the POST request specified URL, with the specified parameter
   * @param name parameter name
   * @param value parameter value
   * @return input stream with the server response
   * @throws IOException
   * @see setParameter
   */
  public static InputStream post(final URL url, final String name1, final Object value1) throws IOException {
    return new ClientHttpRequest(url).post(name1, value1);
  }

  /**
   * post the POST request to specified URL, with the specified parameters
   * @param name1 first parameter name
   * @param value1 first parameter value
   * @param name2 second parameter name
   * @param value2 second parameter value
   * @return input stream with the server response
   * @throws IOException
   * @see setParameter
   */
  public static InputStream post(final URL url, final String name1, final Object value1, final String name2, final Object value2) throws IOException {
    return new ClientHttpRequest(url).post(name1, value1, name2, value2);
  }

  /**
   * post the POST request to specified URL, with the specified parameters
   * @param name1 first parameter name
   * @param value1 first parameter value
   * @param name2 second parameter name
   * @param value2 second parameter value
   * @param name3 third parameter name
   * @param value3 third parameter value
   * @return input stream with the server response
   * @throws IOException
   * @see setParameter
   */
  public static InputStream post(final URL url, final String name1, final Object value1, final String name2, final Object value2, final String name3, final Object value3) throws IOException {
    return new ClientHttpRequest(url).post(name1, value1, name2, value2, name3, value3);
  }

  /**
   * post the POST request to specified URL, with the specified parameters
   * @param name1 first parameter name
   * @param value1 first parameter value
   * @param name2 second parameter name
   * @param value2 second parameter value
   * @param name3 third parameter name
   * @param value3 third parameter value
   * @param name4 fourth parameter name
   * @param value4 fourth parameter value
   * @return input stream with the server response
   * @throws IOException
   * @see setParameter
   */
  public static InputStream post(final URL url, final String name1, final Object value1, final String name2, final Object value2, final String name3, final Object value3, final String name4, final Object value4) throws IOException {
    return new ClientHttpRequest(url).post(name1, value1, name2, value2, name3, value3, name4, value4);
  }
}
