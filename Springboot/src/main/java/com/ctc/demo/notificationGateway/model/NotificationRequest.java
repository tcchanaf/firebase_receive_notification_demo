package com.ctc.demo.notificationGateway.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NotificationRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2022-03-04T10:28:13.022Z")


public class NotificationRequest   {
  @JsonProperty("deviceTokens")
  private String deviceTokens = null;

  @JsonProperty("title")
  private String title = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("data")
  private String data = null;

  public NotificationRequest deviceTokens(String deviceTokens) {
    this.deviceTokens = deviceTokens;
    return this;
  }

  /**
   * Device Tokens of the Devices, seperated by \"@@\"
   * @return deviceTokens
  **/
  @ApiModelProperty(example = "ftaD82QFSQePQQlQD9IEEM:APA91bGCAeHJ2ECdIox01KXoOU93_b2pY78ZqmDlOV5k2A9S5q7I@@ftaD82QFSQePQQlQD9IEEM:APA91bGCAeHJ2ECdIox01KXoOU93_b2pY78ZqmDlOV5k2A9S5q7I", value = "Device Tokens of the Devices, seperated by \"@@\"")


  public String getDeviceTokens() {
    return deviceTokens;
  }

  public void setDeviceTokens(String deviceTokens) {
    this.deviceTokens = deviceTokens;
  }

  public NotificationRequest title(String title) {
    this.title = title;
    return this;
  }

  /**
   * The title of the message sent to the device
   * @return title
  **/
  @ApiModelProperty(example = "Sample Notification Title", value = "The title of the message sent to the device")


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public NotificationRequest message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Notification Content
   * @return message
  **/
  @ApiModelProperty(example = "You have received a message", value = "Notification Content")


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public NotificationRequest data(String data) {
    this.data = data;
    return this;
  }

  /**
   * String of JSON data, for construting an object in the device
   * @return data
  **/
  @ApiModelProperty(example = "{\"userId\":\"00001\",\"userStatus\":\"active\",\"personalDetail\": {\"gender\": \"male\",\"age\": \"12\"}...}", value = "String of JSON data, for construting an object in the device")


  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NotificationRequest notificationRequest = (NotificationRequest) o;
    return Objects.equals(this.deviceTokens, notificationRequest.deviceTokens) &&
        Objects.equals(this.title, notificationRequest.title) &&
        Objects.equals(this.message, notificationRequest.message) &&
        Objects.equals(this.data, notificationRequest.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceTokens, title, message, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NotificationRequest {\n");
    
    sb.append("    deviceTokens: ").append(toIndentedString(deviceTokens)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

