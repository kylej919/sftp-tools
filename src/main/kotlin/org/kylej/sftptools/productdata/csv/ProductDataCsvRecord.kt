package org.kylej.sftptools.productdata.csv

import com.opencsv.bean.CsvBindByName

data class ProductDataCsvRecord(
    @CsvBindByName(column = "Product_ID")
    val productId: String = "",
    @CsvBindByName(column = "Product_Name")
    val productName: String = "",
    @CsvBindByName(column = "Category")
    val category: String = "",
    @CsvBindByName(column = "Sub_Category")
    val subCategory: String = "",
    @CsvBindByName(column = "Price")
    val price: Double = 0.0,
    @CsvBindByName(column = "Customer_Age")
    val customerAge: Int = 0,
    @CsvBindByName(column = "Customer_Gender")
    val customerGender: String = "",
    @CsvBindByName(column = "Purchase_History")
    val purchaseHistory: Int = 0,
    @CsvBindByName(column = "Review_Rating")
    val reviewRating: Int = 0,
    @CsvBindByName(column = "Review_Sentiment")
    val reviewSentiment: String = ""
)