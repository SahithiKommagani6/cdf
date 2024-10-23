/*!
 * Copyright 2002 - 2024 Webdetails, a Hitachi Vantara company. All rights reserved.
 *
 * This software was developed by Webdetails and is provided under the terms
 * of the Mozilla Public License, Version 2.0, or any later version. You may not use
 * this file except in compliance with the license. If you need a copy of the license,
 * please go to http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. Please refer to
 * the license for the specific language governing your rights and limitations.
 */

package org.pentaho.cdf.storage;

import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.pentaho.cdf.InvalidCdfOperationException;
import org.pentaho.cdf.PluginHibernateException;
import org.pentaho.cdf.storage.ImpersonationHandler.CdfStorageApiImpersonationException;
import org.pentaho.cdf.util.Parameter;
import org.pentaho.cdf.utils.CorsUtil;

import pt.webdetails.cpf.utils.CharsetHelper;

@Path( "/pentaho-cdf/api/storage" )
public class StorageApi {

  private StorageEngineInterface engine = null;

  public StorageApi() {
    this( StorageEngine.getInstance() );
  }

  public StorageApi( StorageEngineInterface engine ) {
    this.engine = engine;
  }

  @SuppressWarnings( "serial" )
  private static final Response FORBIDDEN = Response.status( Response.Status.FORBIDDEN ).entity( new JSONObject(
      new HashMap<String, String>() { { put( "error", "no session found" ); } }
    ) ).build();

  @GET
  @Path( "/store" )
  @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
  @Produces( MediaType.APPLICATION_JSON )
  public Response store( @QueryParam( Parameter.STORAGE_VALUE ) String storageValue,
                         @QueryParam( Parameter.USER ) String user,
                         @Context HttpServletRequest servletRequest,
                         @Context HttpServletResponse servletResponse )
    throws InvalidCdfOperationException, JSONException, PluginHibernateException {

    servletResponse.setContentType( MediaType.APPLICATION_JSON );
    servletResponse.setCharacterEncoding( CharsetHelper.getEncoding() );
    setCorsHeaders( servletRequest, servletResponse );

    return store( storageValue, user );
  }

  @GET
  @Path( "/read" )
  @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
  @Produces( MediaType.APPLICATION_JSON )
  public String read( @QueryParam( Parameter.USER ) String user,
                        @Context HttpServletRequest servletRequest,
                        @Context HttpServletResponse servletResponse )
    throws InvalidCdfOperationException, JSONException, PluginHibernateException, IOException {

    servletResponse.setContentType( MediaType.APPLICATION_JSON );
    servletResponse.setCharacterEncoding( CharsetHelper.getEncoding() );
    setCorsHeaders( servletRequest, servletResponse );

    try {
      return read( user );
    } catch ( CdfStorageApiImpersonationException e ) {
      servletResponse.sendError( HttpServletResponse.SC_FORBIDDEN, FORBIDDEN.getEntity().toString() );
      return null;
    }
  }

  @GET
  @Path( "/delete" )
  @Consumes( { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON } )
  @Produces( MediaType.APPLICATION_JSON )
  public Response delete( @QueryParam( Parameter.USER ) String user,
                          @Context HttpServletRequest servletRequest,
                          @Context HttpServletResponse servletResponse )
    throws InvalidCdfOperationException, JSONException, PluginHibernateException {

    servletResponse.setContentType( MediaType.APPLICATION_JSON );
    servletResponse.setCharacterEncoding( CharsetHelper.getEncoding() );
    setCorsHeaders( servletRequest, servletResponse );

    return delete( user );
  }

  protected void setCorsHeaders( HttpServletRequest servletRequest, HttpServletResponse servletResponse ) {
    CorsUtil.getInstance().setCorsHeaders( servletRequest, servletResponse );
  }

  protected Response store( String storageValue, String user )
    throws PluginHibernateException, JSONException, InvalidCdfOperationException {

    try {
      return Response
        .ok( engine.store(
            storageValue,
            impersonate( user ) ).toString( 2 ) )
        .build();
    } catch ( CdfStorageApiImpersonationException e ) {
      return FORBIDDEN;
    }
  }

  protected String read( String user ) throws PluginHibernateException, JSONException,
      InvalidCdfOperationException, CdfStorageApiImpersonationException {
    try {
      return engine.read( impersonate( user ) ).toString( 2 );
    } catch ( CdfStorageApiImpersonationException e ) {
      throw e;
    }
  }

  protected Response delete( String user )
    throws PluginHibernateException, JSONException, InvalidCdfOperationException {

    try {
      return Response
        .ok( engine.delete(
            impersonate( user ) ).toString( 2 ) )
        .build();
    } catch ( CdfStorageApiImpersonationException e ) {
      return FORBIDDEN;
    }
  }

  protected String impersonate( String user ) throws CdfStorageApiImpersonationException {
    return ImpersonationHandler.getUserName( user );
  }
}
