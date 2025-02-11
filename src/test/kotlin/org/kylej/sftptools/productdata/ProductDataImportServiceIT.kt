package org.kylej.sftptools.productdata

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kylej.sftptools.productdata.db.ProductDataRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(properties = arrayOf("spring.jpa.hibernate.ddl-auto=create"))
class ProductDataImportServiceIT {

    @Autowired
    private lateinit var productDataImportService: ProductDataImportService

    @Autowired
    private lateinit var productDataRepository: ProductDataRepository

    @Test
    fun testImportProductData() {
        assertThat(productDataRepository.count()).isEqualTo(0)
        productDataImportService.importProductData()

        assertThat(productDataRepository.count()).isEqualTo(8000)

        val productData = productDataRepository.findById("P0001").get()
        assertThat(productData.productId).isEqualTo("P0001")
        assertThat(productData.productName).isEqualTo("Sweater")
        assertThat(productData.category).isEqualTo("Clothing")
        assertThat(productData.subCategory).isEqualTo("Pants")
        assertThat(productData.price).isEqualTo(38.44)
        assertThat(productData.customerAge).isEqualTo(58)
        assertThat(productData.customerGender).isEqualTo("Male")
        assertThat(productData.purchaseHistory).isEqualTo(16)
        assertThat(productData.reviewRating).isEqualTo(2)
        assertThat(productData.reviewSentiment).isEqualTo("Negative")
    }
}