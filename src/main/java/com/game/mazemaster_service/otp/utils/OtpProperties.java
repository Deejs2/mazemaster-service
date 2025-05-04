/**
 * Author: Utsab Dahal
 * User:LEGION
 * Date:2/24/2025
 * Time:4:56 PM
 */

package com.nepal.collegehub.utils.otp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "otp")
public class OtpProperties {
    private int length;
    private boolean alphanumeric;
    private int expiryInSeconds;
}
