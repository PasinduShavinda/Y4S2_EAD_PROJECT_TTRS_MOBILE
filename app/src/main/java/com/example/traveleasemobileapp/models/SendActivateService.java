/**
 * FileName: SendActivateService.java
 * FileType: Java Interface
 * Author: IT20140298 Shavinda W.A.P
 * Description: This interface defines the API endpoint for sending an activation request to a backend officer.
 *              It specifies a single PUT request to the backend API for sending the request, with the NIC
 *              (National Identification Card) as a path parameter. The response is expected to be of type SendActivateResponse.
 */

package com.example.traveleasemobileapp.models;

import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SendActivateService {
    // Definition of a PUT request method
    @PUT("v1/traveller/account/sendreq/{nic}")
        // Method signature for sending an activation request
        // It expects a NIC (National Identification Card) as a path parameter
        // and returns a Call object with a SendActivateResponse type
    Call<SendActivateResponse> sendRequest(@Path("nic") String nic);
}
