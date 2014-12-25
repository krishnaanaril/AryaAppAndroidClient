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

package com.example.aarya.tasksendpoint.model;

/**
 * Model definition for Tasks.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the tasksendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Tasks extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Key key;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String taskCreatedUser;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String taskDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String taskDescription;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String taskTargetUser;

  /**
   * @return value or {@code null} for none
   */
  public Key getKey() {
    return key;
  }

  /**
   * @param key key or {@code null} for none
   */
  public Tasks setKey(Key key) {
    this.key = key;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTaskCreatedUser() {
    return taskCreatedUser;
  }

  /**
   * @param taskCreatedUser taskCreatedUser or {@code null} for none
   */
  public Tasks setTaskCreatedUser(java.lang.String taskCreatedUser) {
    this.taskCreatedUser = taskCreatedUser;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTaskDate() {
    return taskDate;
  }

  /**
   * @param taskDate taskDate or {@code null} for none
   */
  public Tasks setTaskDate(java.lang.String taskDate) {
    this.taskDate = taskDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTaskDescription() {
    return taskDescription;
  }

  /**
   * @param taskDescription taskDescription or {@code null} for none
   */
  public Tasks setTaskDescription(java.lang.String taskDescription) {
    this.taskDescription = taskDescription;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTaskTargetUser() {
    return taskTargetUser;
  }

  /**
   * @param taskTargetUser taskTargetUser or {@code null} for none
   */
  public Tasks setTaskTargetUser(java.lang.String taskTargetUser) {
    this.taskTargetUser = taskTargetUser;
    return this;
  }

  @Override
  public Tasks set(String fieldName, Object value) {
    return (Tasks) super.set(fieldName, value);
  }

  @Override
  public Tasks clone() {
    return (Tasks) super.clone();
  }

}