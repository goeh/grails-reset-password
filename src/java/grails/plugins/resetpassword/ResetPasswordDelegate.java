/*
 * Copyright (c) 2016 Goran Ehrsson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package grails.plugins.resetpassword;

import java.util.List;
import java.util.Map;

/**
 * Interface for reset-password delegates.
 */
public interface ResetPasswordDelegate {
    /**
     * Verify account details.
     *
     * @param params
     * @return Map with minimum keys 'username' and 'email'.
     */
    Map verifyAccount(Map params);

    /**
     * Return previously saved password hints for a user.
     *
     * @param username
     * @param questions
     * @return List of password hints.
     */
    List<String> getQuestions(String username, List<String> questions);

    /**
     * Set new password.
     *
     * @param username
     * @param password
     * @return true if password was updated
     */
    boolean resetPassword(String username, String password);

    /**
     * Disable account.
     *
     * @param username
     * @return true if account was successfully disabled.
     */
    boolean disableAccount(String username);
}
