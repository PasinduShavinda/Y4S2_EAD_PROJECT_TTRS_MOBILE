/**
 * FileName: DeactivateService.java
 * FileType: Java Interface
 * Author: IT20140298 Shavinda W.A.P
 * Description: This interface defines the API endpoints for deactivating a traveler's account. It specifies
 *              a single PUT request to the backend API for deactivation, with the NIC (National Identification
 *              Card) as a path parameter. The response is expected to be of type DeactivateResponse.
 */

package com.example.traveleasemobileapp.models;

import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DeactivateService {
    /**
     * Send a PUT request to deactivate a traveler's account.
     * @param nic The NIC (National Identification Card) of the traveler to deactivate.
     * @return A Call object representing the deactivation request and expected response.
     */
    @PUT("v1/traveller/account/deactivate/{nic}")
    Call<DeactivateResponse> deactivate(@Path("nic") String nic);
}
