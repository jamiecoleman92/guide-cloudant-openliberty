/*******************************************************************************
 * Copyright (c) 2017, 2018, 2019 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
package io.openliberty.guides.inventory;

import java.net.URL;
import java.net.UnknownHostException;
import java.net.MalformedURLException;
import javax.ws.rs.ProcessingException;
import java.util.Properties;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import io.openliberty.guides.inventory.client.SystemClient;
import io.openliberty.guides.inventory.client.UnknownUrlException;
import io.openliberty.guides.inventory.client.UnknownUrlExceptionMapper;

public class InventoryUtils {

  public Properties getProperties(String hostname, int portNumber) {
    String customURLString = "http://" + hostname + ":" + portNumber + "/system";
    URL customURL = null;
    try {
      customURL = new URL(customURLString);
      SystemClient customRestClient = RestClientBuilder.newBuilder()
                                                       .baseUrl(customURL)
                                                       .register(
                                                           UnknownUrlExceptionMapper.class)
                                                       .build(SystemClient.class);
      return customRestClient.getProperties();
    } catch (ProcessingException ex) {
      handleProcessingException(ex);
    } catch (UnknownUrlException e) {
      System.err.println("The given URL is unreachable.");
    } catch (MalformedURLException e) {
      System.err.println("The given URL is not formatted correctly.");
    }
    return null;
  }

  public void handleProcessingException(ProcessingException ex) {
    Throwable rootEx = ExceptionUtils.getRootCause(ex);
    if (rootEx != null && rootEx instanceof UnknownHostException) {
      System.err.println("The specified host is unknown.");
    } else {
      throw ex;
    }
  }

}
