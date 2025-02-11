package org.kylej.sftptools.sftp.props

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "sftp")
data class SftpProps(
    var host: String,
    var username: String,
    var password: String
)