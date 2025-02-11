package org.kylej.sftptools.util

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.ChannelSftp.LsEntry
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.net.URI

private val logger = KotlinLogging.logger {}

sealed class SftpConnection : Closeable {

    abstract val host: String
    abstract val session: Session
    abstract val channelSftp: ChannelSftp

    /**
     * Uploads the provided file to the destination path on the SFTP server.
     */
    fun upload(file: File, destinationPath: String) {
        logger.info { "Uploading ${file.name} to $destinationPath of sftp host $host" }
        val remotePath = "$destinationPath/${file.name}"
        channelSftp.put(FileInputStream(file), remotePath)
    }

    /**
     * Lists the contents in the SFTP server at the requested path.
     */
    fun listFiles(remoteDir: String): List<LsEntry> {
        logger.info { "Listing files in $remoteDir from host $host" }
        return channelSftp.ls(remoteDir).toList()
    }

    /**
     * Downloads the file at the provided path on the SFTP server to the local file system.
     */
    fun download(remotePath: String): ByteArray {
        logger.info { "Downloading $remotePath from the SFTP server host $host" }
        return channelSftp.get(remotePath).readAllBytes()
    }

    fun delete(remotePath: String) {
        logger.info { "Deleting $remotePath" }
        channelSftp.rm(remotePath)
    }

    class SftpPasswordConnection(
        override val host: String,
        private val username: String,
        private val password: String
    ) :
        SftpConnection() {
        override val session: Session
            get() {
                logger.info { "Connecting to $host as $username" }
                val uri = URI.create(host)
                val jSch = JSch()
                val session = jSch.getSession(username, uri.host, uri.port)
                session.setPassword(password)
                session.setConfig("StrictHostKeyChecking", "no")
                session.connect()
                return session
            }
        override val channelSftp: ChannelSftp
            get() {
                val thisSession = session
                val channel: ChannelSftp = thisSession.openChannel("sftp") as ChannelSftp
                channel.connect()
                return channel
            }
    }

    class SftpPrivateKeyConnection(
        override val host: String,
        private val username: String,
        private val privateKey: File
    ) :
        SftpConnection() {
        override val session: Session
            get() {
                logger.info { "Connecting to $host as $username" }
                val uri = URI.create(host)
                val jSch = JSch()
                jSch.addIdentity(privateKey.name)
                val session = jSch.getSession(username, uri.host, uri.port)
                session.setConfig("StrictHostKeyChecking", "no")
                session.connect()
                return session
            }
        override val channelSftp: ChannelSftp
            get() {
                val channel: ChannelSftp = session.openChannel("sftp") as ChannelSftp
                channel.connect()
                return channel
            }
    }

    override fun close() {
        logger.info { "Disconnecting from $host" }
        session.disconnect()
        channelSftp.disconnect()
    }

}