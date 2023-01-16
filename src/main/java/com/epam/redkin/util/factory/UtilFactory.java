package com.epam.redkin.util.factory;

import com.epam.redkin.util.EmailDistributorUtil;
import com.epam.redkin.util.EmailMessageLocalizationDispatcher;

public class UtilFactory {
    private final EmailDistributorUtil emailDistributorUtil;
    private final EmailMessageLocalizationDispatcher emailMessageLocalizationDispatcher;

    private UtilFactory() {
        emailDistributorUtil = new EmailDistributorUtil();
        emailMessageLocalizationDispatcher = new EmailMessageLocalizationDispatcher();
    }

    private static class UtilFactorySingletonHolder {
        static final UtilFactory INSTANCE = new UtilFactory();
    }

    public static UtilFactory getInstance() {
        return UtilFactorySingletonHolder.INSTANCE;
    }

    public EmailDistributorUtil getEmailDistributorUtil() {
        return emailDistributorUtil;
    }

    public EmailMessageLocalizationDispatcher getEmailMessageLocalizationDispatcher() {
        return emailMessageLocalizationDispatcher;
    }
}
