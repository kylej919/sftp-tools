package org.kylej.sftptools.productdata

import com.opencsv.bean.CsvToBeanBuilder
import org.kylej.sftptools.productdata.csv.ProductDataCsvRecord
import org.kylej.sftptools.productdata.db.ProductDataRepository
import org.kylej.sftptools.productdata.model.ProductData
import org.kylej.sftptools.productdata.model.fromCsvRecord
import org.kylej.sftptools.sftp.service.SftpService
import org.springframework.stereotype.Service
import java.io.StringReader

@Service
class ProductDataImportService(
    private val sftpService: SftpService,
    private val productDataRepository: ProductDataRepository
) {

    fun importProductData(): List<ProductData> {
        val sftpConnection = sftpService.getSftpConnection()
        val csvContent = String(sftpConnection.download("/data/refined_ecommerce_product_data.csv"))
        val productDataCsvRecords = parseCsv(csvContent)
        val dbRecords = productDataCsvRecords.map { fromCsvRecord(it) }
        return productDataRepository.saveAll(dbRecords)
    }

    fun parseCsv(csvContent: String): List<ProductDataCsvRecord> {
        StringReader(csvContent).use {
            val csvToBean = CsvToBeanBuilder<ProductDataCsvRecord>(it)
                .withType(ProductDataCsvRecord::class.java)
                .withIgnoreLeadingWhiteSpace(true)
                .build()
            return csvToBean.parse()
        }
    }
}