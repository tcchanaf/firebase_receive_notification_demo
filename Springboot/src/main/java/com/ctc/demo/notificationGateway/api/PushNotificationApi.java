
package com.ctc.demo.notificationGateway.api;

import io.swagger.annotations.*;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.ctc.demo.notificationGateway.model.BaseResult;
import com.ctc.demo.notificationGateway.model.NotificationRequest;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2022-03-04T10:28:13.022Z")

@Validated
@Api(value = "pushNotification", description = "the pushNotification API")
@RequestMapping(value = "")
public interface PushNotificationApi {

    @ApiOperation(value = "Notification interface to accept the request to sending out the notification to the device", nickname = "pushNotificationPOST", notes = "", response = BaseResult.class, authorizations = {
        @Authorization(value = "apiKey")
    }, tags={ "Notification", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Notification pushing result", response = BaseResult.class) })
    @RequestMapping(value = "/pushNotification",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<BaseResult> pushNotificationPOST(@ApiParam(value = "The criteria of the Notification to be sent and the device to be received" ,required=true )  @Valid @RequestBody NotificationRequest body);

}
