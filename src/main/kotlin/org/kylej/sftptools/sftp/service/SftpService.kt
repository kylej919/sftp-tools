package org.kylej.sftptools.sftp.service

import org.kylej.sftptools.sftp.props.SftpProps
import org.kylej.sftptools.sftp.util.SftpConnection
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(SftpProps::class)
class SftpService(
    private val sftpProps: SftpProps
) {

    fun getSftpConnection(): SftpConnection {
        return SftpConnection.SftpPasswordConnection(sftpProps.host, sftpProps.username, sftpProps.password)
    }
}