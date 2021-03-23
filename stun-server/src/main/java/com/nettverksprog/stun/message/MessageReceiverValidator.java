package com.nettverksprog.stun.message;


import com.nettverksprog.stun.header.MessageHeaderParser;

/**
 * Validator for the processing of a STUN message following the initial parsing of a message
 * defined in {@link MessageHeaderParser#parseMessageHeader(byte[])} as specified by RFC 5389 section 7.3
 */
public class MessageReceiverValidator {

    private Message messageToValidate;

    public MessageReceiverValidator(Message messageToValidate) {
        this.messageToValidate = messageToValidate;
    }

    public void validate() {
        doCheckFingerPrint();
        doCheckErrors();
        doCheckAuthenticationMechanism();
        doCheckUnknownAttributes();
        doCheckKnownButUnexpectedAttributes();
    }

    /**
     * Check that the FINGERPRINT is present, if so check that
     * the attribute is present and contains the correct value.
     */
    private void doCheckFingerPrint() {

    }

    /**
     * If any errors are detected, silently discard the message.
     */
    private void doCheckErrors() {

    }

    /**
     * Perform checks that are required by an authentication mechanism that the usage has specified.
     *
     * Either a short-term or long-term credential mechanism to provide authentication and message integrity.
     */
    private void doCheckAuthenticationMechanism() {

    }

    /**
     * Check for unknown attributes in the message.
     *
     * Optional unknown attributes must be ignored to be processed depending on the message class.
     */
    private void doCheckUnknownAttributes() {

    }

    /**
     *
     */
    private void doCheckKnownButUnexpectedAttributes() {

    }
}
