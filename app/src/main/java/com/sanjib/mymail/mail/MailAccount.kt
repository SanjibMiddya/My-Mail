package com.sanjib.mymail.mail

data class MailAccount(
    val email: String,
    val displayName: String,
    val imapHost: String,
    val imapPort: Int = 993,
    val smtpHost: String,
    val smtpPort: Int = 465,
    val username: String = email,
    val useSsl: Boolean = true
)
