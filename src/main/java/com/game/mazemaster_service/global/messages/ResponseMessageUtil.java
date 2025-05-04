/**
 * Author: Utsab Dahal
 * User:LEGION
 * Date:3/7/2025
 * Time:12:03 PM
 */

package com.nepal.collegehub.utils.messages;

public class ResponseMessageUtil {
    private ResponseMessageUtil() {}

    public static String createdSuccessfully(String entity) {
        return entity + " created successfully";
    }

    public static String allFetchedSuccessfully(String entity) {
        return "All " + entity + " fetched successfully";
    }

    public static String fetchedSuccessfully(String entity) {
        return entity + " fetched successfully";
    }

    public static String updatedSuccessfully(String entity) {
        return entity + " updated successfully";
    }

    public static String deletedSuccessfully(String entity) {
        return entity + " deleted successfully";
    }
}