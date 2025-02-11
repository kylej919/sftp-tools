package org.kylej.sftptools.productdata.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.kylej.sftptools.productdata.csv.ProductDataCsvRecord

fun fromCsvRecord(csvRecord: ProductDataCsvRecord): ProductData {
    return ProductData().apply {
        productId = csvRecord.productId
        productName = csvRecord.productName
        category = csvRecord.category
        subCategory = csvRecord.subCategory
        price = csvRecord.price
        customerAge = csvRecord.customerAge
        customerGender = csvRecord.customerGender
        purchaseHistory = csvRecord.purchaseHistory
        reviewRating = csvRecord.reviewRating
        reviewSentiment = csvRecord.reviewSentiment
    }
}

@Entity
@Table(name = "product_data")
open class ProductData {

    @Id
    open lateinit var productId: String
    open lateinit var productName: String
    open lateinit var category: String
    open lateinit var subCategory: String
    open var price: Double = 0.0
    open var customerAge: Int = 0
    open lateinit var customerGender: String
    open var purchaseHistory: Int = 0
    open var reviewRating: Int = 0
    open lateinit var reviewSentiment: String
}