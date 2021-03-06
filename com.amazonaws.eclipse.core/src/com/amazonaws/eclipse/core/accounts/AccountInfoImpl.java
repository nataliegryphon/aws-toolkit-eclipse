/*
 * Copyright 2008-2012 Amazon Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://aws.amazon.com/apache2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amazonaws.eclipse.core.accounts;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.amazonaws.eclipse.core.AccountInfo;

/**
 * A Java-bean compliant implementation of the AccountInfo interface. This class
 * consists of two main components - AccountCredentialsConfiguration and
 * AccountOptionalConfiguration. These two components are independent and might
 * use different source to read and persist the configurations.
 */
public class AccountInfoImpl implements AccountInfo {

    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
            this);

    /**
     * The internal account identifier associated with this account.
     */
    private final String accountId;

    /**
     * Config information related to the security credentials for this account.
     */
    private final AccountCredentialsConfiguration credentialsConfig;

    /**
     * All the optional configuration for this account.
     */
    private final AccountOptionalConfiguration optionalConfig;

    public AccountInfoImpl(
            final String accountId,
            final AccountCredentialsConfiguration credentialsConfig,
            final AccountOptionalConfiguration optionalConfig) {
        if (accountId == null)
            throw new IllegalAccessError("accountId must not be null.");
        if (credentialsConfig == null)
            throw new IllegalAccessError("credentialsConfig must not be null.");
        if (optionalConfig == null)
            throw new IllegalAccessError("optionalConfig must not be null.");

        this.accountId         = accountId;
        this.credentialsConfig = credentialsConfig;
        this.optionalConfig    = optionalConfig;
    }

    /**
     * {@inheritDoc}
     */
    public String getInternalAccountId() {
        return accountId;
    }

    /**
     * {@inheritDoc}
     */
    public String getAccountName() {
        return credentialsConfig.getAccountName();
    }

    /**
     * {@inheritDoc}
     */
    public void setAccountName(String accountName) {
        String oldValue = getAccountName();
        if ( !oldValue.equals(accountName) ) {
            credentialsConfig.setAccountName(accountName);
            firePropertyChange("accountName", oldValue, accountName);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getAccessKey() {
        return credentialsConfig.getAccessKey();
    }

    /**
     * {@inheritDoc}
     */
    public void setAccessKey(String accessKey) {
        String oldValue = getAccessKey();
        if ( !oldValue.equals(accessKey) ) {
            credentialsConfig.setAccessKey(accessKey);
            firePropertyChange("accessKey", oldValue, accessKey);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getSecretKey() {
        return credentialsConfig.getSecretKey();
    }

    /**
     * {@inheritDoc}
     */
    public void setSecretKey(String secretKey) {
        String oldValue = getSecretKey();
        if ( !oldValue.equals(secretKey) ) {
            credentialsConfig.setSecretKey(secretKey);
            firePropertyChange("secretKey", oldValue, secretKey);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getUserId() {
        return optionalConfig.getUserId();
    }

    /**
     * {@inheritDoc}
     */
    public void setUserId(String userId) {
        String oldValue = getUserId();
        if ( !oldValue.equals(userId) ) {
            optionalConfig.setUserId(userId);
            firePropertyChange("userId", oldValue, userId);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getEc2PrivateKeyFile() {
        return optionalConfig.getEc2PrivateKeyFile();
    }

    /**
     * {@inheritDoc}
     */
    public void setEc2PrivateKeyFile(String ec2PrivateKeyFile) {
        String oldValue = getEc2PrivateKeyFile();
        if ( !oldValue.equals(ec2PrivateKeyFile) ) {
            optionalConfig.setEc2PrivateKeyFile(ec2PrivateKeyFile);
            firePropertyChange("ec2PrivateKeyFile", oldValue, ec2PrivateKeyFile);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getEc2CertificateFile() {
        return optionalConfig.getEc2CertificateFile();
    }

    /**
     * {@inheritDoc}
     */
    public void setEc2CertificateFile(String ec2CertificateFile) {
        String oldValue = getEc2CertificateFile();
        if ( !oldValue.equals(ec2CertificateFile) ) {
            optionalConfig.setEc2CertificateFile(ec2CertificateFile);
            firePropertyChange("ec2CertificateFile", oldValue, ec2CertificateFile);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void save() {
        credentialsConfig.save();
        optionalConfig.save();
    }

    /**
     * {@inheritDoc}
     */
    public void delete() {
        credentialsConfig.delete();
        optionalConfig.delete();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isDirty() {
        return credentialsConfig.isDirty()
                || optionalConfig.isDirty();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid() {
        return credentialsConfig.isCredentialsValid();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isCertificateValid() {
        return optionalConfig.isCertificateValid();
    }

    /* Java Bean related interfaces */

    public void addPropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName,
                listener);
    }

    public void removePropertyChangeListener(String propertyName,
            PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName,
                listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue,
            Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue,
                newValue);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(getAccountName());
        sb.append("]: ");

        sb.append("accessKey=");
        sb.append(getAccessKey());
        sb.append(", secretKey=");
        sb.append(getSecretKey());
        sb.append(", userId=");
        sb.append(getUserId());
        sb.append(", certFile=");
        sb.append(getEc2CertificateFile());
        sb.append(", privateKey=");
        sb.append(getEc2PrivateKeyFile());

        return sb.toString();
    }
}
