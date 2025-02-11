package org.kylej.sftptools.util

import com.jcraft.jsch.ChannelSftp.LsEntry
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SftpConnectionIT {


    /**
     * Basic test to connect and list files
     */
    @Test
    fun testListFiles() {
        val sftp: SftpConnection = SftpConnection.SftpPasswordConnection("sftp://localhost:22", "user", "pass")
        val lsContents: List<LsEntry> = sftp.listFiles("/data")

        assertThat(lsContents).isNotEmpty().anyMatch({ it.filename.equals("refined_ecommerce_product_data.csv") })
    }
}