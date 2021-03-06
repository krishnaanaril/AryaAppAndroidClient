/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-04-15 19:10:39 UTC)
 * on 2014-05-18 at 09:41:02 UTC 
 * Modify at your own risk.
 */

package com.example.aarya.tasksendpoint;

/**
 * Service definition for Tasksendpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link TasksendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Tasksendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.16.0-rc of the tasksendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://pulse-21.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "tasksendpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Tasksendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Tasksendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getTasks".
   *
   * This request holds the parameters needed by the the tasksendpoint server.  After setting any
   * optional parameters, call the {@link GetTasks#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetTasks getTasks(java.lang.Long id) throws java.io.IOException {
    GetTasks result = new GetTasks(id);
    initialize(result);
    return result;
  }

  public class GetTasks extends TasksendpointRequest<com.example.aarya.tasksendpoint.model.Tasks> {

    private static final String REST_PATH = "tasks/{id}";

    /**
     * Create a request for the method "getTasks".
     *
     * This request holds the parameters needed by the the tasksendpoint server.  After setting any
     * optional parameters, call the {@link GetTasks#execute()} method to invoke the remote operation.
     * <p> {@link
     * GetTasks#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetTasks(java.lang.Long id) {
      super(Tasksendpoint.this, "GET", REST_PATH, null, com.example.aarya.tasksendpoint.model.Tasks.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetTasks setAlt(java.lang.String alt) {
      return (GetTasks) super.setAlt(alt);
    }

    @Override
    public GetTasks setFields(java.lang.String fields) {
      return (GetTasks) super.setFields(fields);
    }

    @Override
    public GetTasks setKey(java.lang.String key) {
      return (GetTasks) super.setKey(key);
    }

    @Override
    public GetTasks setOauthToken(java.lang.String oauthToken) {
      return (GetTasks) super.setOauthToken(oauthToken);
    }

    @Override
    public GetTasks setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetTasks) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetTasks setQuotaUser(java.lang.String quotaUser) {
      return (GetTasks) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetTasks setUserIp(java.lang.String userIp) {
      return (GetTasks) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetTasks setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetTasks set(String parameterName, Object value) {
      return (GetTasks) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertTasks".
   *
   * This request holds the parameters needed by the the tasksendpoint server.  After setting any
   * optional parameters, call the {@link InsertTasks#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.example.aarya.tasksendpoint.model.Tasks}
   * @return the request
   */
  public InsertTasks insertTasks(com.example.aarya.tasksendpoint.model.Tasks content) throws java.io.IOException {
    InsertTasks result = new InsertTasks(content);
    initialize(result);
    return result;
  }

  public class InsertTasks extends TasksendpointRequest<com.example.aarya.tasksendpoint.model.Tasks> {

    private static final String REST_PATH = "tasks";

    /**
     * Create a request for the method "insertTasks".
     *
     * This request holds the parameters needed by the the tasksendpoint server.  After setting any
     * optional parameters, call the {@link InsertTasks#execute()} method to invoke the remote
     * operation. <p> {@link
     * InsertTasks#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.example.aarya.tasksendpoint.model.Tasks}
     * @since 1.13
     */
    protected InsertTasks(com.example.aarya.tasksendpoint.model.Tasks content) {
      super(Tasksendpoint.this, "POST", REST_PATH, content, com.example.aarya.tasksendpoint.model.Tasks.class);
    }

    @Override
    public InsertTasks setAlt(java.lang.String alt) {
      return (InsertTasks) super.setAlt(alt);
    }

    @Override
    public InsertTasks setFields(java.lang.String fields) {
      return (InsertTasks) super.setFields(fields);
    }

    @Override
    public InsertTasks setKey(java.lang.String key) {
      return (InsertTasks) super.setKey(key);
    }

    @Override
    public InsertTasks setOauthToken(java.lang.String oauthToken) {
      return (InsertTasks) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertTasks setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertTasks) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertTasks setQuotaUser(java.lang.String quotaUser) {
      return (InsertTasks) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertTasks setUserIp(java.lang.String userIp) {
      return (InsertTasks) super.setUserIp(userIp);
    }

    @Override
    public InsertTasks set(String parameterName, Object value) {
      return (InsertTasks) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listTasks".
   *
   * This request holds the parameters needed by the the tasksendpoint server.  After setting any
   * optional parameters, call the {@link ListTasks#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public ListTasks listTasks() throws java.io.IOException {
    ListTasks result = new ListTasks();
    initialize(result);
    return result;
  }

  public class ListTasks extends TasksendpointRequest<com.example.aarya.tasksendpoint.model.CollectionResponseTasks> {

    private static final String REST_PATH = "tasks";

    /**
     * Create a request for the method "listTasks".
     *
     * This request holds the parameters needed by the the tasksendpoint server.  After setting any
     * optional parameters, call the {@link ListTasks#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListTasks#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListTasks() {
      super(Tasksendpoint.this, "GET", REST_PATH, null, com.example.aarya.tasksendpoint.model.CollectionResponseTasks.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListTasks setAlt(java.lang.String alt) {
      return (ListTasks) super.setAlt(alt);
    }

    @Override
    public ListTasks setFields(java.lang.String fields) {
      return (ListTasks) super.setFields(fields);
    }

    @Override
    public ListTasks setKey(java.lang.String key) {
      return (ListTasks) super.setKey(key);
    }

    @Override
    public ListTasks setOauthToken(java.lang.String oauthToken) {
      return (ListTasks) super.setOauthToken(oauthToken);
    }

    @Override
    public ListTasks setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListTasks) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListTasks setQuotaUser(java.lang.String quotaUser) {
      return (ListTasks) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListTasks setUserIp(java.lang.String userIp) {
      return (ListTasks) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListTasks setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public ListTasks setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListTasks set(String parameterName, Object value) {
      return (ListTasks) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeTasks".
   *
   * This request holds the parameters needed by the the tasksendpoint server.  After setting any
   * optional parameters, call the {@link RemoveTasks#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveTasks removeTasks(java.lang.Long id) throws java.io.IOException {
    RemoveTasks result = new RemoveTasks(id);
    initialize(result);
    return result;
  }

  public class RemoveTasks extends TasksendpointRequest<Void> {

    private static final String REST_PATH = "tasks/{id}";

    /**
     * Create a request for the method "removeTasks".
     *
     * This request holds the parameters needed by the the tasksendpoint server.  After setting any
     * optional parameters, call the {@link RemoveTasks#execute()} method to invoke the remote
     * operation. <p> {@link
     * RemoveTasks#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveTasks(java.lang.Long id) {
      super(Tasksendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveTasks setAlt(java.lang.String alt) {
      return (RemoveTasks) super.setAlt(alt);
    }

    @Override
    public RemoveTasks setFields(java.lang.String fields) {
      return (RemoveTasks) super.setFields(fields);
    }

    @Override
    public RemoveTasks setKey(java.lang.String key) {
      return (RemoveTasks) super.setKey(key);
    }

    @Override
    public RemoveTasks setOauthToken(java.lang.String oauthToken) {
      return (RemoveTasks) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveTasks setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveTasks) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveTasks setQuotaUser(java.lang.String quotaUser) {
      return (RemoveTasks) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveTasks setUserIp(java.lang.String userIp) {
      return (RemoveTasks) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveTasks setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveTasks set(String parameterName, Object value) {
      return (RemoveTasks) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateTasks".
   *
   * This request holds the parameters needed by the the tasksendpoint server.  After setting any
   * optional parameters, call the {@link UpdateTasks#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.example.aarya.tasksendpoint.model.Tasks}
   * @return the request
   */
  public UpdateTasks updateTasks(com.example.aarya.tasksendpoint.model.Tasks content) throws java.io.IOException {
    UpdateTasks result = new UpdateTasks(content);
    initialize(result);
    return result;
  }

  public class UpdateTasks extends TasksendpointRequest<com.example.aarya.tasksendpoint.model.Tasks> {

    private static final String REST_PATH = "tasks";

    /**
     * Create a request for the method "updateTasks".
     *
     * This request holds the parameters needed by the the tasksendpoint server.  After setting any
     * optional parameters, call the {@link UpdateTasks#execute()} method to invoke the remote
     * operation. <p> {@link
     * UpdateTasks#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param content the {@link com.example.aarya.tasksendpoint.model.Tasks}
     * @since 1.13
     */
    protected UpdateTasks(com.example.aarya.tasksendpoint.model.Tasks content) {
      super(Tasksendpoint.this, "PUT", REST_PATH, content, com.example.aarya.tasksendpoint.model.Tasks.class);
    }

    @Override
    public UpdateTasks setAlt(java.lang.String alt) {
      return (UpdateTasks) super.setAlt(alt);
    }

    @Override
    public UpdateTasks setFields(java.lang.String fields) {
      return (UpdateTasks) super.setFields(fields);
    }

    @Override
    public UpdateTasks setKey(java.lang.String key) {
      return (UpdateTasks) super.setKey(key);
    }

    @Override
    public UpdateTasks setOauthToken(java.lang.String oauthToken) {
      return (UpdateTasks) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateTasks setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateTasks) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateTasks setQuotaUser(java.lang.String quotaUser) {
      return (UpdateTasks) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateTasks setUserIp(java.lang.String userIp) {
      return (UpdateTasks) super.setUserIp(userIp);
    }

    @Override
    public UpdateTasks set(String parameterName, Object value) {
      return (UpdateTasks) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Tasksendpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Tasksendpoint}. */
    @Override
    public Tasksendpoint build() {
      return new Tasksendpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link TasksendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setTasksendpointRequestInitializer(
        TasksendpointRequestInitializer tasksendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(tasksendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
