package com.sanjib.mymail.mail

import jakarta.mail.Folder
import jakarta.mail.Session
import java.util.Properties

class ImapSmtpClient {
    fun testImap(account: MailAccount, password: String): Result<Int> = runCatching {
        val props = Properties().apply {
            put("mail.store.protocol", "imaps")
            put("mail.imaps.host", account.imapHost)
            put("mail.imaps.port", account.imapPort.toString())
            put("mail.imaps.ssl.enable", account.useSsl.toString())
            put("mail.imaps.connectiontimeout", "10000")
            put("mail.imaps.timeout", "10000")
        }
        val session = Session.getInstance(props)
        val store = session.getStore("imaps")
        store.connect(account.imapHost, account.imapPort, account.username, password)
        val inbox = store.getFolder("INBOX")
        inbox.open(Folder.READ_ONLY)
        val count = inbox.messageCount
        inbox.close(false)
        store.close()
        count
    }
}
